import java.util.Random;

public class Perceptron {
    public final int inputCount;
    private Matrix weights;
    private double bias;

    private static Random random = new Random();

    public Perceptron(int inputCount) {
        this.inputCount = inputCount;
        weights = new Matrix(1, inputCount);

        for (int i = 0; i < weights.getColumns(); i++) {
            weights.setValue(random.nextGaussian(), 0, i);
        }
        bias = random.nextGaussian();
    }

    public double feedForward(Matrix inputs) {
        if (weights.getColumns() != inputs.getRows() || inputs.getColumns() != 1) {
            throw new IllegalArgumentException("Incompatible matrix shapes.");
        }

        return sigmoid(Matrix.product(weights, inputs).getValue(0, 0) + bias);
    }

    public static double sigmoid(double x) {
        return 1 / (1 + Math.exp(-x));
    }

    public void printData() {
        System.out.println("Weights: ");
        weights.printContents();
        System.out.println("Bias: " + bias);
    }
}
