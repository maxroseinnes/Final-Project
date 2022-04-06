import java.util.Random;

public class FinalProjectExperimentation {
    Random random = new Random();

    public static void main(String[] args) {
        new FinalProjectExperimentation();
    }

    public FinalProjectExperimentation() {
        Matrix[] xorDatasetInputs = new Matrix[4];
        Matrix[] xorDatasetTargets = new Matrix[4];
        xorDatasetInputs[0] = new Matrix(new double[][]{{0}, {0}});
        xorDatasetInputs[1] = new Matrix(new double[][]{{0}, {1}});
        xorDatasetInputs[2] = new Matrix(new double[][]{{1}, {0}});
        xorDatasetInputs[3] = new Matrix(new double[][]{{1}, {1}});
        xorDatasetTargets[0] = new Matrix(new double[][]{{0}});
        xorDatasetTargets[1] = new Matrix(new double[][]{{1}});
        xorDatasetTargets[2] = new Matrix(new double[][]{{1}});
        xorDatasetTargets[3] = new Matrix(new double[][]{{0}});

        NeuralNet net = new NeuralNet(2, 3, 1);
        net.randomize();

        /*long timeBefore = System.nanoTime();
        for (int i = 0; i < 1000000; i++) {
            int trainingExampleIndex = (int) (Math.random() * 4);
            Matrix inputs = xorDatasetInputs[trainingExampleIndex];
            Matrix targets = xorDatasetTargets[trainingExampleIndex];

            Matrix guess = net.feedForward(inputs);
            boolean correct = Math.round(guess.getValue(0, 0)) == targets.getValue(0, 0);

            //System.out.println("iteration " + (i + 1) + "  |  " + inputs.transposition().toString() + "  |  " + guess.getValue(0, 0) + "  |  " + correct);

            net.backPropagate(inputs, targets, 0.1);
        }
        System.out.println("Finished in " + (System.nanoTime() - timeBefore) + " nanoseconds.");*/

        for (int i = 0; i < 1000000; i++) {
            Matrix inputs = new Matrix(new double[][]{{Math.random()}, {Math.random()}});
            double x = inputs.getValue(0, 0) * 2 - 1;
            double y = inputs.getValue(1, 0) * 2 - 1;
            Matrix targets = new Matrix(new double[][]{{x * x + y * y <= 1 ? 1 : 0}});


            Matrix guess = net.feedForward(inputs);
            boolean correct = Math.round(guess.getValue(0, 0)) == targets.getValue(0, 0);
            System.out.println("iteration " + (i + 1) + " " + inputs.transposition().toString() + " " + guess.getValue(0, 0) + " " + correct);

            net.backPropagate(inputs, targets, 0.01);
        }
        net.printContents();
    }
}