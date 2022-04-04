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

    Matrix feedForward(Matrix inputs) {
        // Make sure input matrix and stop layer is valid
        if (inputs.getRows() != NEURON_COUNTS[0] || inputs.getColumns() != 1) {
            throw new IllegalArgumentException("Invalid input matrix shape.");
        }

        // Compute each layer's outputs up until the stop layer
        Matrix previousLayerOutputs = Matrix.product(weights[0], inputs);
        previousLayerOutputs.add(biases[0]);
        previousLayerOutputs.sigmoid();
        for (int i = 1; i < NEURON_COUNTS.length - 1; i++) {
            previousLayerOutputs = Matrix.product(weights[i], previousLayerOutputs);
            previousLayerOutputs.add(biases[i]);
            previousLayerOutputs.sigmoid();
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
        layerOutputs[0] = Matrix.product(weights[0], inputs);
        layerOutputs[0].add(biases[0]);
        layerOutputs[0].sigmoid();
        for (int i = 1; i < NEURON_COUNTS.length - 1; i++) {
            layerOutputs[i] = Matrix.product(weights[i], layerOutputs[i - 1]);
            layerOutputs[i].add(biases[i]);
            layerOutputs[i].sigmoid();
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
        errors[errors.length - 1] = Matrix.difference(layerOutputs[layerOutputs.length - 1], targets);
        for (int i = errors.length - 2; i >= 0; i--) {
            errors[i] = Matrix.product(weights[i + 1].transposition(), errors[i + 1]);
        }

        for (int i = errors.length - 1; i >= 0; i--) {
            Matrix biasGradient = Matrix.hadamard(errors[i], Matrix.dSigmoid(layerOutputs[i]));
            biasGradient.multiplyBy(learningRate);
            Matrix weightsGradient;

            if (i >= 1) {
                weightsGradient = Matrix.product(biasGradient, layerOutputs[i - 1].transposition());
            } else {
                weightsGradient = Matrix.product(biasGradient, inputs.transposition());
            }

            biases[i].subtract(biasGradient);
            weights[i].subtract(weightsGradient);
        }
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