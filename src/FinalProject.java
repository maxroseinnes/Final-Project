import javax.swing.*;
import java.awt.*;
import java.util.*;

public class FinalProject {
    static JFrame window = new JFrame("Final Project");
    static FlappyBirdPanel panel = new FlappyBirdPanel(600, 600);

    static ArrayList<Bird> birds = new ArrayList<Bird>();
    static final int POPULATION = 10000;
    static final double PARENT_RATE = 0.1;
    static ArrayList<PipePair> pipes = new ArrayList<PipePair>();
    static int aliveCount = POPULATION;
    static int score = 0;

    public static void main(String[] args) throws InterruptedException {
        new FinalProject();
    }

    public FinalProject() throws InterruptedException {
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        panel.setBackground(new Color(50, 50, 50));
        window.add(panel);
        window.pack();
        window.setVisible(true);

        for (int i = 0; i < POPULATION; i++) {
            birds.add(new Bird());
        }
        pipes.add(new PipePair(panel.getWidth() + PipePair.WIDTH / 2));

        while (true) {
            long timeBefore = System.nanoTime();

            Iterator<Bird> birdIterator = birds.iterator();
            while (birdIterator.hasNext()) {
                Bird bird = birdIterator.next();
                if (!bird.alive) {
                    continue;
                }
                bird.update();
                bird.think();

                boolean intersectingWithPipe = false;
                for (PipePair pipe : pipes) {
                    if (bird.intersectingWithPipe(pipe)) {
                        intersectingWithPipe = true;
                        break;
                    }
                }
                if (intersectingWithPipe) {
                    bird.alive = false;
                    aliveCount--;
                } else {
                    bird.fitness++;
                }
            }

            if (aliveCount == 0) {
                int[] fitnesses = new int[birds.size()];
                for (int i = 0; i < fitnesses.length; i++) {
                    fitnesses[i] = birds.get(i).fitness;
                }
                Arrays.sort(fitnesses);

                int[] selectionWeights = new int[fitnesses.length];
                int tieIndexStart = 0;
                for (int i = 1; i < fitnesses.length; i++) {
                    if (!(fitnesses[i] == fitnesses[tieIndexStart])) {
                        for (int j = tieIndexStart; j < i; j++) {
                            selectionWeights[j] = tieIndexStart + i + 1;
                        }
                        tieIndexStart = i;
                    }

                    if (i == fitnesses.length - 1) {
                        for (int j = tieIndexStart; j <= i; j++) {
                            selectionWeights[j] = tieIndexStart + i + 2;
                        }
                    }
                }

                double[] normalizedSelectionWeights = new double[selectionWeights.length];
                for (int i = 0; i < normalizedSelectionWeights.length; i++) {
                    normalizedSelectionWeights[i] = (double) selectionWeights[i] / (selectionWeights.length * (selectionWeights.length + 1));
                }

                break;
            }

            Iterator<PipePair> pipeIterator = pipes.iterator();
            while (pipeIterator.hasNext()) {
                PipePair pipe = pipeIterator.next();

                pipe.update();
                if (pipe.xPos < -PipePair.WIDTH) {
                    pipeIterator.remove();
                }
            }

            if (panel.getWidth() + PipePair.WIDTH / 2 - pipes.get(pipes.size() - 1).xPos > PipePair.DISTANCE_BETWEEN_PIPES) {
                PipePair newPipe = new PipePair(pipes.get(pipes.size() - 1).xPos + PipePair.DISTANCE_BETWEEN_PIPES);
                pipes.add(newPipe);
            }
            panel.paintImmediately(0, 0, panel.getWidth(), panel.getHeight());

            long elapsed = System.nanoTime() - timeBefore;
            if (elapsed < (long) 1000000000 / 60) {
                Thread.sleep(1000 / 60 - elapsed / 1000000);
            }
        }
    }
}