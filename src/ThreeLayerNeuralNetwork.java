import java.util.Random;

public class ThreeLayerNeuralNetwork {
    private static final Random random = new Random();

    final int inputLayerNeurons, hiddenLayerNeurons, outputLayerNeurons;
    private Matrix hiddenLayerWeights, outputLayerWeights;
    private Matrix hiddenLayerBiases, outputLayerBiases;

    public ThreeLayerNeuralNetwork(int inputLayerNeurons, int hiddenLayerNeurons, int outputLayerNeurons) {
        if (inputLayerNeurons < 1 || hiddenLayerNeurons < 1 || outputLayerNeurons < 1) {
            throw new IllegalArgumentException("Invalid layer neuron counts.");
        }

        this.inputLayerNeurons = inputLayerNeurons;
        this.hiddenLayerNeurons = hiddenLayerNeurons;
        this.outputLayerNeurons = outputLayerNeurons;
        hiddenLayerWeights = new Matrix(hiddenLayerNeurons, inputLayerNeurons);
        outputLayerWeights = new Matrix(outputLayerNeurons, hiddenLayerNeurons);
        hiddenLayerBiases = new Matrix(hiddenLayerNeurons, 1);
        outputLayerBiases = new Matrix(outputLayerNeurons, 1);

        for (int i = 0; i < hiddenLayerWeights.getRows(); i++) {
            for (int j = 0; j < hiddenLayerWeights.getColumns(); j++) {
                hiddenLayerWeights.setValue(random.nextGaussian(), i, j);
            }
        }

        for (int i = 0; i < outputLayerWeights.getRows(); i++) {
            for (int j = 0; j < outputLayerWeights.getColumns(); j++) {
                outputLayerWeights.setValue(random.nextGaussian(), i, j);
            }
        }

        for (int i = 0; i < hiddenLayerBiases.getRows(); i++) {
            hiddenLayerBiases.setValue(random.nextGaussian(), i, 0);
        }

        for (int i = 0; i < outputLayerBiases.getRows(); i++) {
            outputLayerBiases.setValue(random.nextGaussian(), i, 0);
        }
    }

    Matrix feedForward(Matrix inputs, int stopLayer) {
        if (inputs.getRows() != inputLayerNeurons || inputs.getColumns() != 1 || stopLayer < 1 || stopLayer > 2) {
            throw new IllegalArgumentException("Invalid inputs or stop layer.");
        }

        Matrix hiddenLayerOutputs = Matrix.product(hiddenLayerWeights, inputs);
        hiddenLayerOutputs.add(hiddenLayerBiases);
        hiddenLayerOutputs.sigmoid();
        if (stopLayer == 1) {
            return hiddenLayerOutputs;
        }
        Matrix outputs = Matrix.product(outputLayerWeights, hiddenLayerOutputs);
        outputs.add(outputLayerBiases);
        outputs.sigmoid();
        return outputs;
    }

    void backPropagate(Matrix inputs, Matrix targets, double learningRate) {
        if (inputs.getRows() != inputLayerNeurons || inputs.getColumns() != 1 || targets.getRows() != outputLayerNeurons || targets.getColumns() != 1) {
            throw new IllegalArgumentException("Invalid input or target matrix shape.");
        }

        // Compute each layer's activation
        Matrix hiddenLayerOutputs = feedForward(inputs, 1);
        Matrix outputs = feedForward(inputs, 2);

        // Compute errors
        Matrix errors = Matrix.subtract(outputs, targets);
        Matrix hiddenLayerErrors = Matrix.product(outputLayerWeights.transcribe(), errors);

        // Compute gradient for the output layer's weights
        Matrix outputLayerWeightsGradient = Matrix.hadamard(errors, Matrix.dSigmoid(outputs));
        outputLayerWeightsGradient.multiplyBy(hiddenLayerOutputs.transcribe());
        outputLayerWeightsGradient.multiplyBy(learningRate);

        // Compute gradient for the hidden layer's weights
        Matrix hiddenLayerWeightsGradient = Matrix.hadamard(hiddenLayerErrors, Matrix.dSigmoid(hiddenLayerOutputs));
        hiddenLayerWeightsGradient.multiplyBy(inputs.transcribe());
        hiddenLayerWeightsGradient.multiplyBy(learningRate);

        // Compute gradient for the output layer's biases
        Matrix outputLayerBiasesGradient = Matrix.hadamard(errors, Matrix.dSigmoid(outputs));
        outputLayerBiasesGradient.multiplyBy(learningRate);

        // Compute gradient for the hidden layer's biases
        Matrix hiddenLayerBiasesGradient = Matrix.hadamard(hiddenLayerErrors, Matrix.dSigmoid(hiddenLayerOutputs));
        hiddenLayerBiasesGradient.multiplyBy(learningRate);

        outputLayerWeights.subtract(outputLayerWeightsGradient);
        hiddenLayerWeights.subtract(hiddenLayerWeightsGradient);
        outputLayerBiases.subtract(outputLayerBiasesGradient);
        hiddenLayerBiases.subtract(hiddenLayerBiasesGradient);
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
