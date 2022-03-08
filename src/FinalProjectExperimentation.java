import java.util.Arrays;
import java.util.Random;

public class FinalProjectExperimentation {
    Random random = new Random();

    public static void main(String[] args) {
        new FinalProjectExperimentation();
    }

    public FinalProjectExperimentation() {
        Perceptron perceptron = new Perceptron(2);

        for (int i = 0; i < 1000000; i++) {
            Matrix inputs = new Matrix(2, 1);
            for (int j = 0; j < 2; j++) {
                inputs.setValue(Math.random(), j, 0);
            }

            double target = Math.signum(inputs.getValue(0, 0) - inputs.getValue(1, 0));
            perceptron.backPropagate(inputs, target, 0.01);
        }

        perceptron.printData();
    }
}
