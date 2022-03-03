import java.util.Arrays;

public class FinalProjectExperimentation {
    public static void main(String[] args) {
        new FinalProjectExperimentation();
    }

    public FinalProjectExperimentation() {
        ThreeLayerNeuralNetwork network = new ThreeLayerNeuralNetwork(2, 3, 1, true);

        Matrix inputs = new Matrix(network.inputLayerNeurons, 1);
        for (int i = 0; i < inputs.getRows(); i++) {
            inputs.setValue(Util.map(Math.random(), 0, 1, -1, 1), i, 0);
        }
        Matrix output = network.feedForward(inputs, 2);
    }
}
