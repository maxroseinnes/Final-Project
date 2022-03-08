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

        return Math.signum(Matrix.product(weights, inputs).getValue(0, 0) + bias);
    }

    public void backPropagate(Matrix inputs, double target, double learningRate) {
        if (inputs.getRows() != inputCount || inputs.getColumns() != 1) {
            throw new IllegalArgumentException("Invalid input matrix shape.");
        }

        double output = feedForward(inputs);
        double error = output - target;

        //System.out.println(inputs.getValue(0, 0) + ", " + inputs.getValue(1, 0) + ": " + error);
        Matrix weightsGradient = inputs.transposition();
        weightsGradient.multiplyBy(learningRate * error);
        double biasGradient = learningRate * error;

        weights.subtract(weightsGradient);
        bias -= biasGradient;
    }

    public void printData() {
        System.out.println("Weights: ");
        weights.printContents();
        System.out.println("Bias: " + bias);
    }
}
