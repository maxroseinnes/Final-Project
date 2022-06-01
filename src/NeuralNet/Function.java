package NeuralNet;

public class Function {
    static final int SIGMOID = 0;
    static final int D_SIGMOID = 1;

    static double sigmoid(double num) {
        return 1 / (1 + Math.exp(-num));
    }

    static double dSigmoid(double num) {
        return num * (1 - num);
    }
}
