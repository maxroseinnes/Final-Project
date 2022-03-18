import java.util.Arrays;
import java.util.Random;

public class FinalProjectExperimentation {
    Random random = new Random();

    public static void main(String[] args) {
        new FinalProjectExperimentation();
    }

    public FinalProjectExperimentation() {
        NeuralNet net = new NeuralNet(2, 1);
        net.randomize();
        net.printContents();

        for (int i = 0; i < 1000; i++) {
            Matrix inputs = new Matrix(2, 1);
            for (int j = 0; j < inputs.getRows(); j++) {
                inputs.setValue(Math.random(), i, 0);
            }
            Matrix targets = new Matrix(1, 1);
            targets.setValue(inputs.getValue(0, 0) - inputs.getValue(1, 0) >= 0 ? 1 : 0, 0, 0);
            System.out.println(targets.getValue(0, 0) + " " + net.feedForward(inputs, net.neuronCounts.length - 1).getValue(0, 0));

            net.backPropagate(inputs, targets, 0.1);
        }
    }
}
