import java.util.Arrays;
import java.util.Random;

public class FinalProjectExperimentation {
    Random random = new Random();

    public static void main(String[] args) {
        new FinalProjectExperimentation();
    }

    public FinalProjectExperimentation() {
        Perceptron perceptron = new Perceptron(2);
        perceptron.printData();
        for (int i = 0; i < 100; i++) {
            Matrix inputs = new Matrix(perceptron.inputCount, 1);
            for (int j = 0; j < inputs.getRows(); j++) {
                inputs.setValue(Math.random(), j, 0);
            }

            System.out.println(perceptron.feedForward(inputs));
        }
    }
}
