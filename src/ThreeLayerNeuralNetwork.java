import java.util.Arrays;
import java.util.Objects;

public class ThreeLayerNeuralNetwork {
    final int inputLayerNeurons, hiddenLayerNeurons, outputLayerNeurons;
    Matrix hiddenLayerWeights, outputLayerWeights;
    Matrix hiddenLayerBiases, outputLayerBiases;

    public ThreeLayerNeuralNetwork(int inputLayerNeurons, int hiddenLayerNeurons, int outputLayerNeurons, boolean initializeRandomly) {
        this.inputLayerNeurons = inputLayerNeurons;
        this.hiddenLayerNeurons = hiddenLayerNeurons;
        this.outputLayerNeurons = outputLayerNeurons;
        hiddenLayerWeights = new Matrix(hiddenLayerNeurons, inputLayerNeurons);
        outputLayerWeights = new Matrix(outputLayerNeurons, hiddenLayerNeurons);
        hiddenLayerBiases = new Matrix(hiddenLayerNeurons, 1);
        outputLayerBiases = new Matrix(outputLayerNeurons, 1);

        if (initializeRandomly) {
            hiddenLayerWeights.setContentsRandomly();
            outputLayerWeights.setContentsRandomly();
            hiddenLayerBiases.setContentsRandomly();
            outputLayerBiases.setContentsRandomly();
        }
    }

    Matrix feedForward(Matrix inputs, int layer) {
        if (inputs.getRows() != inputLayerNeurons || inputs.getColumns() != 1) {
            return null;
        }
        
        Matrix hiddenLayerOutputs = Matrix.sigmoid(Matrix.add(Matrix.multiply(hiddenLayerWeights, inputs), hiddenLayerBiases));
        if (layer == 1) {
            return hiddenLayerOutputs;
        }
        Matrix outputs = Matrix.sigmoid(Matrix.add(Matrix.multiply(outputLayerWeights, hiddenLayerOutputs), outputLayerBiases));

        return outputs;
    }

    void backPropagate(Matrix inputs, Matrix targets) {
        Matrix[] layerOutputs = new Matrix[2];
        for (int i = 0; i < layerOutputs.length; i++) {
            layerOutputs[i] = feedForward(inputs, i + 1);
        }

        if (targets.getRows() != outputLayerNeurons || targets.getColumns() != 1) {
            return;
        }
        
        Matrix errors = Matrix.subtract(targets, layerOutputs[1]);
        // outputLayerWeights = Matrix.hadamard()

        
        Matrix hiddenLayerErrors = new Matrix(hiddenLayerNeurons, 1);

        for (int i = 0; i < hiddenLayerErrors.getRows(); i++) {
            double errorContributionSum = 0;
            for (int j = 0; j < errors.getRows(); j++) {
                
            }
        }
    }
    
    ThreeLayerNeuralNetwork mutate(double mutationChance, double mutationAmount) {
        Matrix modifiedHiddenLayerWeights = new Matrix(inputLayerNeurons, hiddenLayerNeurons);
        Matrix modifiedOutputLayerWeights = new Matrix(hiddenLayerNeurons, outputLayerNeurons);
        Matrix modifiedHiddenLayerBiases = new Matrix(hiddenLayerNeurons, 1);
        Matrix modifiedOutputLayerBiases = new Matrix(outputLayerNeurons, 1);

        for (int i = 0; i < inputLayerNeurons; i++) {
            for (int j = 0; j < hiddenLayerNeurons; j++) {

                if (Math.random() < mutationChance) {
                    modifiedHiddenLayerWeights.setValue(hiddenLayerWeights.getValue(i, j) + (2 * Math.random() * mutationAmount - mutationAmount), i, j);
                } else {
                    modifiedHiddenLayerWeights.setValue(hiddenLayerWeights.getValue(i, j), i, j);
                }
            }
        }

        for (int i = 0; i < hiddenLayerNeurons; i++) {
            for (int j = 0; j < outputLayerNeurons; j++) {
                if (Math.random() < mutationChance) {
                    modifiedOutputLayerWeights.setValue(outputLayerWeights.getValue(i, j) + (2 * Math.random() * mutationAmount - mutationAmount), i, j);
                } else {
                    modifiedOutputLayerWeights.setValue(outputLayerWeights.getValue(i, j), i, j);
                }
            }
        }

        for (int i = 0; i < hiddenLayerNeurons; i++) {
            if (Math.random() < mutationChance) {
                modifiedHiddenLayerBiases.setValue(hiddenLayerBiases.getValue(i, 0) + (2 * Math.random() * mutationAmount - mutationAmount), i, 0);
            } else {
                modifiedHiddenLayerBiases.setValue(hiddenLayerBiases.getValue(i, 0), i, 0);
            }
        }

        for (int i = 0; i < outputLayerNeurons; i++) {
            if (Math.random() < mutationChance) {
                modifiedOutputLayerBiases.setValue(outputLayerBiases.getValue(i, 0) + (2 * Math.random() * mutationAmount - mutationAmount), i, 0);
            } else {
                modifiedOutputLayerBiases.setValue(outputLayerBiases.getValue(i, 0), i, 0);
            }
        }

        return null;
    }

    public void printContents() {
        System.out.println("Hidden layer weights: ");
        hiddenLayerWeights.printContents();
        System.out.println("Output layer weights: ");
        outputLayerWeights.printContents();
        System.out.println("Hidden layer biases: ");
        hiddenLayerBiases.printContents();
        System.out.println("Output layer biases: ");
        outputLayerBiases.printContents();
    }
}
