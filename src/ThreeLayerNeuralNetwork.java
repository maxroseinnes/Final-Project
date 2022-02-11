public class ThreeLayerNeuralNetwork {
    int inputNeurons;
    int hiddenNeurons;
    int outputNeurons;

    public ThreeLayerNeuralNetwork(int inputNeurons, int hiddenNeurons, int outputNeurons) {
        this.inputNeurons = inputNeurons;
        this.hiddenNeurons = hiddenNeurons;
        this.outputNeurons = outputNeurons;
    }

    double feedForward(double[] inputs) {
        double[] hiddenLayerOutputs = new double[hiddenNeurons];

        for (int i = 0; i < hiddenNeurons; i++) {

        }

        return 0;
    }

    double sigmoid(double input) {
        return 1 / (1 + Math.exp(input));
    }
}
