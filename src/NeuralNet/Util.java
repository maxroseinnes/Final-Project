package NeuralNet;

public class Util {
    public static double sigmoid(double x) {
        return 1 / Math.exp(-x);
    }

    public static double dSigmoid(double y) {
        return y * (1 - y);
    }

    public static double map(double input, double inputLower, double inputUpper, double outputLower, double outputUpper) {
        double inputRange = inputUpper - inputLower;
        double outputRange = outputUpper - outputLower;

        return outputLower + input / inputRange * outputRange;
    }
}