import java.util.Arrays;

public class ThreeLayerNeuralNetwork {
    final int inputLayerNeurons, hiddenLayerNeurons, outputLayerNeurons;
    double[][] hiddenLayerWeights, outputLayerWeights;
    double[] hiddenLayerBiases, outputLayerBiases;

    public ThreeLayerNeuralNetwork(int inputLayerNeurons, int hiddenLayerNeurons, int outputLayerNeurons) {
        this.inputLayerNeurons = inputLayerNeurons;
        this.hiddenLayerNeurons = hiddenLayerNeurons;
        this.outputLayerNeurons = outputLayerNeurons;
        hiddenLayerWeights = new double[inputLayerNeurons][hiddenLayerNeurons];
        outputLayerWeights = new double[hiddenLayerNeurons][outputLayerNeurons];
        hiddenLayerBiases = new double[hiddenLayerNeurons];
        outputLayerBiases = new double[outputLayerNeurons];

        for (int i = 0; i < hiddenLayerWeights.length; i++) {
            for (int j = 0; j < hiddenLayerWeights[0].length; j++) {
                hiddenLayerWeights[i][j] = 2 * Math.random() - 1;
            }
        }

        for (int i = 0; i < outputLayerWeights.length; i++) {
            for (int j = 0; j < outputLayerWeights[0].length; j++) {
                outputLayerWeights[i][j] = 2 * Math.random() - 1;
            }
        }

        for (int i = 0; i < outputLayerBiases.length; i++) {
            outputLayerBiases[i] = 2 * Math.random() - 1;
        }

        for (int i = 0; i < outputLayerBiases.length; i++) {
            outputLayerBiases[i] = 2 * Math.random() - 1;
        }
    }

    public ThreeLayerNeuralNetwork(double[][] hiddenLayerWeights, double[][] outputLayerWeights, double[] hiddenLayerBiases, double[] outputLayerBiases) {
        this.hiddenLayerWeights = hiddenLayerWeights;
        this.outputLayerWeights = outputLayerWeights;
        this.hiddenLayerBiases = hiddenLayerBiases;
        this.outputLayerBiases = outputLayerBiases;

        this.inputLayerNeurons = hiddenLayerWeights.length;
        this.hiddenLayerNeurons = hiddenLayerWeights[0].length;
        this.outputLayerNeurons = outputLayerWeights[0].length;
    }

    double[] feedForward(double[] inputs) {
        if (inputs.length != hiddenLayerNeurons) {
            System.out.println("Wrong amount of inputs");
            return new double[0];
        }

        double[] hiddenLayerOutputs = new double[hiddenLayerNeurons];

        for (int i = 0; i < hiddenLayerNeurons; i++) {
            double sum = hiddenLayerBiases[i];
            for (int j = 0; j < inputLayerNeurons; j++) {
                sum += inputs[j] * hiddenLayerWeights[j][i];
            }
            hiddenLayerOutputs[i] = sum;
        }

        double[] outputs = new double[outputLayerNeurons];
        for (int i = 0; i < outputLayerNeurons; i++) {
            double sum = outputLayerBiases[i];
            for (int j = 0; j < hiddenLayerNeurons; j++) {
                sum += hiddenLayerOutputs[j] * outputLayerWeights[j][i];
            }
            outputs[i] = sum;
        }

        return outputs;
    }

    ThreeLayerNeuralNetwork mutate(double mutationChance, double mutationAmount) {
        double[][] modifiedHiddenLayerWeights = new double[inputLayerNeurons][hiddenLayerNeurons];
        double[][] modifiedOutputLayerWeights = new double[hiddenLayerNeurons][outputLayerNeurons];
        double[] modifiedHiddenLayerBiases = new double[hiddenLayerNeurons];
        double[] modifiedOutputLayerBiases = new double[outputLayerNeurons];

        for (int i = 0; i < inputLayerNeurons; i++) {
            for (int j = 0; j < hiddenLayerNeurons; j++) {
                modifiedHiddenLayerWeights[i][j] = hiddenLayerWeights[i][j];
                if (Math.random() < mutationChance) {
                    modifiedHiddenLayerWeights[i][j] += 2 * Math.random() * mutationAmount - mutationAmount;
                }
            }
        }

        for (int i = 0; i < hiddenLayerNeurons; i++) {
            for (int j = 0; j < outputLayerNeurons; j++) {
                modifiedOutputLayerWeights[i][j] = outputLayerWeights[i][j];
                if (Math.random() < mutationChance) {
                    modifiedOutputLayerWeights[i][j] += 2 * Math.random() * mutationAmount - mutationAmount;
                }
            }
        }

        for (int i = 0; i < hiddenLayerNeurons; i++) {
            modifiedHiddenLayerBiases[i] = hiddenLayerBiases[i];
            if (Math.random() < mutationChance) {
                modifiedHiddenLayerBiases[i] += 2 * Math.random() * mutationAmount - mutationAmount;
            }
        }

        for (int i = 0; i < outputLayerNeurons; i++) {
            modifiedOutputLayerBiases[i] = outputLayerBiases[i];
            if (Math.random() < mutationChance) {
                modifiedOutputLayerBiases[i] += 2 * Math.random() * mutationAmount - mutationAmount;
            }
        }

        return new ThreeLayerNeuralNetwork(modifiedHiddenLayerWeights, modifiedOutputLayerWeights, modifiedHiddenLayerBiases, modifiedOutputLayerBiases);
    }
}
