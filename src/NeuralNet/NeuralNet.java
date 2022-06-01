package NeuralNet;

import java.util.Random;

public class NeuralNet {
    private static final Random random = new Random();

    private Matrix[] weights;
    private Matrix[] biases;
    final int[] NEURON_COUNTS;

    public NeuralNet(int... neuronCounts) {
        if (neuronCounts.length < 2) {
            throw new IllegalArgumentException("Two or more layers required.");
        }
        for (int i = 0; i < neuronCounts.length; i++) {
            if (neuronCounts[i] < 1) {
                throw new IllegalArgumentException("Layer " + (i + 1) + " must have one or more neurons.");
            }
        }

        this.NEURON_COUNTS = neuronCounts;
        weights = new Matrix[neuronCounts.length - 1];
        biases = new Matrix[neuronCounts.length - 1];

        for (int i = 1; i < neuronCounts.length; i++) {
            weights[i - 1] = new Matrix(neuronCounts[i], neuronCounts[i - 1]);
            biases[i - 1] = new Matrix(neuronCounts[i], 1);
        }
    }

    public void randomize() {
        for (int i = 0; i < weights.length; i++) {
            for (int j = 0; j < weights[i].getRows(); j++) {
                for (int k = 0; k < weights[i].getColumns(); k++) {
                    weights[i].setValue(random.nextGaussian(), j, k);
                }
            }
        }

        for (int i = 0; i < biases.length; i++) {
            for (int j = 0; j < biases[i].getRows(); j++) {
                biases[i].setValue(random.nextGaussian(), j, 0);
            }
        }
    }

    public Matrix feedForward(Matrix inputs) {
        // Make sure input matrix and stop layer is valid
        if (inputs.getRows() != NEURON_COUNTS[0] || inputs.getColumns() != 1) {
            throw new IllegalArgumentException("Invalid input matrix shape.");
        }

        // Compute each layer's outputs up until the stop layer
        Matrix previousLayerOutputs = Matrix.sum(Matrix.product(weights[0], inputs), biases[0]);
        previousLayerOutputs.modify(Function.SIGMOID);
        for (int i = 1; i < NEURON_COUNTS.length - 1; i++) {
            previousLayerOutputs = Matrix.sum(Matrix.product(weights[i], previousLayerOutputs), biases[i]);
            previousLayerOutputs.modify(Function.SIGMOID);
        }

        return previousLayerOutputs;
    }

    Matrix[] feedForwardRecall(Matrix inputs) {
        // Make sure input matrix and stop layer is valid
        if (inputs.getRows() != NEURON_COUNTS[0] || inputs.getColumns() != 1) {
            throw new IllegalArgumentException("Invalid input matrix shape.");
        }

        // Compute each layer's outputs up until the stop layer
        Matrix[] layerOutputs = new Matrix[NEURON_COUNTS.length - 1];
        layerOutputs[0] = Matrix.sum(Matrix.product(weights[0], inputs), biases[0]);
        layerOutputs[0].modify(Function.SIGMOID);
        for (int i = 1; i < NEURON_COUNTS.length - 1; i++) {
            layerOutputs[i] = Matrix.sum(Matrix.product(weights[i], layerOutputs[i - 1]), biases[i]);
            layerOutputs[i].modify(Function.SIGMOID);
        }

        return layerOutputs;
    }

    void backPropagate(Matrix inputs, Matrix targets, double learningRate) {
        if (inputs.getRows() != NEURON_COUNTS[0] || inputs.getColumns() != 1 || targets.getRows() != NEURON_COUNTS[NEURON_COUNTS.length - 1] || targets.getColumns() != 1) {
            throw new IllegalArgumentException("Invalid matrix shape of inputs or targets.");
        }

        // Store each matrix of layer outputs in an array
        Matrix[] layerOutputs = feedForwardRecall(inputs);

        // Compute each layer's errors
        Matrix[] errors = new Matrix[layerOutputs.length];
        errors[errors.length - 1] = Matrix.subtract(layerOutputs[layerOutputs.length - 1], targets);
        for (int i = errors.length - 2; i >= 0; i--) {
            errors[i] = Matrix.product(weights[i + 1].transposition(), errors[i + 1]);
        }

        for (int i = errors.length - 1; i >= 0; i--) {
            Matrix biasGradient = layerOutputs[i].copy();
            biasGradient.modify(Function.D_SIGMOID);
            biasGradient = Matrix.hadamard(biasGradient, errors[i]);
            biasGradient.scale(learningRate);
            Matrix weightsGradient;

            if (i >= 1) {
                weightsGradient = Matrix.product(biasGradient, layerOutputs[i - 1].transposition());
            } else {
                weightsGradient = Matrix.product(biasGradient, inputs.transposition());
            }

            biases[i] = Matrix.subtract(biases[i], biasGradient);
            weights[i] = Matrix.subtract(biases[i], weightsGradient);
        }
    }
    
    public void mutate(double amount) {
        for (int i = 0; i < weights.length; i++) {
            for (int j = 0; j < weights[i].getRows(); j++) {
                for (int k = 0; k < weights[i].getColumns(); k++) {
                    weights[i].setValue(weights[i].getValue(j, k) + 2 * Math.random() * amount - amount, j, k);
                }
            }
        }

        for (int i = 0; i < biases.length; i++) {
            for (int j = 0; j < biases[i].getRows(); j++) {
                for (int k = 0; k < biases[i].getColumns(); k++) {
                    biases[i].setValue(biases[i].getValue(j, k) + 2 * Math.random() * amount - amount, j, k);
                }
            }
        }
    }

    public NeuralNet copy() {
        NeuralNet newNet = new NeuralNet(NEURON_COUNTS);
        for (int i = 0; i < weights.length; i++) {
            newNet.weights[i] = weights[i].copy();
        }
        for (int i = 0; i < biases.length; i++) {
            newNet.biases[i] = biases[i].copy();
        }
        return newNet;
    }

    public static NeuralNet crossover(NeuralNet a, NeuralNet b) {
        if (a.NEURON_COUNTS.length != b.NEURON_COUNTS.length) {
            throw new IllegalArgumentException("Networks do not have the same structure.");
        }
        for (int i = 0; i < a.NEURON_COUNTS.length; i++) {
            if (a.NEURON_COUNTS[i] != b.NEURON_COUNTS[i]) {
                throw new IllegalArgumentException("Networks do not have the same structure.");
            }
        }

        NeuralNet newNet = a.copy();
        for (int i = 0; i < newNet.weights.length; i++) {
            for (int j = 0; j < newNet.weights[i].getRows(); j++) {
                for (int k = 0; k < newNet.weights[i].getColumns(); k++) {
                    if (Math.random() < 0.5) {
                        newNet.weights[i].setValue(b.weights[i].getValue(j, k), j, k);
                    }
                }
            }
        }

        return newNet;
    }

    public void printContents() {
        for (int i = 0; i < NEURON_COUNTS.length - 1; i++) {
            System.out.println("Layers " + (i + 1) + "-" + (i) + " weights:");
            System.out.println(weights[i].toString());
            System.out.println("Layer " + (i + 1) + " biases:");
            System.out.println(biases[i].toString());
            if (i != NEURON_COUNTS.length - 1) {
                System.out.println();
            }
        }
    }
}