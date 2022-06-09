import javax.swing.*;
import java.awt.*;
import java.util.*;

public class FinalProject {
    static JFrame window = new JFrame("Final Project");
    static FlappyBirdPanel panel = new FlappyBirdPanel(600, 600);

    static ArrayList<Bird> birds = new ArrayList<Bird>();
    static final int POPULATION = 100;
    static final double PARENT_RATE = 0.1;
    static ArrayList<PipePair> pipes = new ArrayList<PipePair>();
    static int aliveCount = POPULATION;
    static int score = 0;
    static int highScore = 0;
    static int generation = 1;

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

            // Update all birds and check for pipe intersections
            for (Bird bird : birds) {
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

            // If all birds are dead, generate next generation and reset
            if (aliveCount == 0) {
                ArrayList<Integer> fitnesses = new ArrayList<Integer>();
                for (Bird bird : birds) {
                    int fitness = bird.fitness;
                    if (!fitnesses.contains(fitness)) {
                        fitnesses.add(fitness);
                    }
                }
                Collections.sort(fitnesses);

                int parentCount = (int) Math.ceil(POPULATION * PARENT_RATE);
                ArrayList<Bird> parents = new ArrayList<>();
                int i = fitnesses.size() - 1;
                while (parents.size() < parentCount) {
                    ArrayList<Bird> toAdd = new ArrayList<Bird>();

                    for (Bird bird : birds) {
                        if (bird.fitness == fitnesses.get(i)) {
                            toAdd.add(bird);
                        }
                    }

                    if (toAdd.size() > parentCount - parents.size()) {
                        int temp = parentCount - parents.size();
                        for (int j = 0; j < temp; j++) {
                            int index = (int) (Math.random() * toAdd.size());
                            parents.add(toAdd.get(index).copy());
                            toAdd.remove(index);
                        }
                    } else {
                        for (Bird bird : toAdd) {
                            parents.add(bird.copy());
                        }
                    }

                    i--;
                }
                Collections.reverse(parents);

                int[] selectionWeights = new int[parents.size()];
                int tieIndexStart = 0;
                for (int j = 1; j < parents.size(); j++) {
                    if (!(parents.get(j).fitness == parents.get(tieIndexStart).fitness)) {
                        for (int k = tieIndexStart; k < j; k++) {
                            selectionWeights[k] = tieIndexStart + j + 1;
                        }
                        tieIndexStart = j;
                    }

                    if (j == parents.size() - 1) {
                        for (int k = tieIndexStart; k <= j; k++) {
                            selectionWeights[k] = tieIndexStart + j + 2;
                        }
                    }
                }

                double[] normalizedSelectionWeights = new double[selectionWeights.length];
                for (int j = 0; j < normalizedSelectionWeights.length; j++) {
                    normalizedSelectionWeights[j] = (double) selectionWeights[j] / (selectionWeights.length * (selectionWeights.length + 1));
                }

                birds.clear();
                for (int j = 0; j < POPULATION; j++) {
                    int parent1Index = -1;
                    int parent2Index = -1;
                    double lowBound = 0;
                    double random = Math.random();
                    for (int k = 0; k < normalizedSelectionWeights.length; k++) {
                        if (random >= lowBound && random < lowBound + normalizedSelectionWeights[k]) {
                            parent1Index = k;
                            break;
                        }
                        lowBound += normalizedSelectionWeights[k];
                    }

                    do {
                        lowBound = 0;
                        random = Math.random();
                        for (int k = 0; k < normalizedSelectionWeights.length; k++) {
                            if (random >= lowBound && random < lowBound + normalizedSelectionWeights[k]) {
                                parent2Index = k;
                                break;
                            }
                            lowBound += normalizedSelectionWeights[k];
                        }
                    } while (parent1Index == parent2Index);

                    Bird newBird = Bird.crossover(parents.get(parent1Index), parents.get(parent2Index));
                    newBird.mutate(0.5, 0.1);
                    birds.add(newBird);
                }

                aliveCount = POPULATION;
                score = 0;
                generation++;
                pipes.clear();
                pipes.add(new PipePair(panel.getWidth() + PipePair.WIDTH / 2));
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
                pipes.add(new PipePair(pipes.get(pipes.size() - 1).xPos + PipePair.DISTANCE_BETWEEN_PIPES));
            }
            panel.paintImmediately(0, 0, panel.getWidth(), panel.getHeight());

            long elapsed = System.nanoTime() - timeBefore;
            if (elapsed < (long) 1000000000 / 60) {
                Thread.sleep(1000 / 60 - elapsed / 1000000);
            }
        }
    }
}