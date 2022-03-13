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

    public void backPropagate(Matrix[] batch, double[] targets, double learningRate) {
        for (int i = 0; i < batch.length; i++) {
            if (batch[i].getRows() != inputCount || batch[i].getColumns() != 1 || batch.length != targets.length) {
                throw new IllegalArgumentException("Invalid shape in one or more of the batch input matrices, or unequal batch size and amount of provided targets.");
            }
        }

        double[] outputs = new double[batch.length];
        double[] errors = new double[batch.length];
        for (int i = 0; i < outputs.length; i++) {
            outputs[i] = feedForward(batch[i]);
            errors[i] = outputs[i] - targets[i];
        }

        Matrix[] weightsGradients = new Matrix[batch.length];
        for (int i = 0; i < weightsGradients.length; i++) {
            weightsGradients[i] = batch[i].transposition();
            weightsGradients[i].multiplyBy(learningRate * errors[i]);
        }
        double[] biasGradients = new double[batch.length];
        for (int i = 0; i < biasGradients.length; i++) {
            biasGradients[i] = learningRate * errors[i];
        }
    }

    public void printData() {
        System.out.println("Weights: ");
        weights.printContents();
        System.out.println("Bias: " + bias);
    }
}
