import java.util.Arrays;
import java.util.Objects;

public class ThreeLayerNeuralNetwork {
    final int inputLayerNeurons, hiddenLayerNeurons, outputLayerNeurons;
    Matrix hiddenLayerWeights, outputLayerWeights;
    Matrix hiddenLayerBiases, outputLayerBiases;

    public ThreeLayerNeuralNetwork(int inputLayerNeurons, int hiddenLayerNeurons, int outputLayerNeurons) {
        this.inputLayerNeurons = inputLayerNeurons;
        this.hiddenLayerNeurons = hiddenLayerNeurons;
        this.outputLayerNeurons = outputLayerNeurons;
        hiddenLayerWeights = new Matrix(inputLayerNeurons, hiddenLayerNeurons);
        outputLayerWeights = new Matrix(hiddenLayerNeurons, outputLayerNeurons);
        hiddenLayerBiases = new Matrix(hiddenLayerNeurons, 0);
        outputLayerBiases = new Matrix(outputLayerNeurons, 0);
    }

    Matrix feedForward(Matrix inputs) {
        if (inputs.getColumns() != 1 || inputs.getRows() != inputLayerNeurons) {
            System.out.println("Input matrix must have one column and the same number of rows as the number of inputs to the network.");
            return null;
        }

        Matrix hiddenLayerOutputs = Matrix.sigmoid(Objects.requireNonNull(Matrix.add(Objects.requireNonNull(Matrix.multiply(hiddenLayerWeights, inputs)), hiddenLayerBiases)));
        Matrix outputs = Matrix.sigmoid(Objects.requireNonNull(Matrix.add(Objects.requireNonNull(Matrix.multiply(outputLayerWeights, hiddenLayerOutputs)), outputLayerBiases)));

        return outputs;
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
}

