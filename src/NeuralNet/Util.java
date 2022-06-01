package NeuralNet;

public class Util {
    public static double map(double input, double inputLower, double inputUpper, double outputLower, double outputUpper) {
        double inputRange = inputUpper - inputLower;
        double outputRange = outputUpper - outputLower;

        return outputLower + (input - inputLower) / inputRange * outputRange;
    }
}