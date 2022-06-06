import javax.swing.*;
import java.awt.*;
import java.util.*;

public class FinalProject {
    static JFrame window = new JFrame("Final Project");
    static FlappyBirdPanel panel = new FlappyBirdPanel(600, 600);

    static ArrayList<Bird> birds = new ArrayList<Bird>();
    static final int POPULATION = 50;
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
                ArrayList<Integer> fitnesses = new ArrayList<Integer>();
                for (int i = 0; i < birds.size(); i++) {
                    int fitness = birds.get(i).fitness;
                    if (!fitnesses.contains(fitness)) {
                        fitnesses.add(fitness);
                    }
                }
                int[] fitnessesArray = new int[fitnesses.size()];
                for (int i = 0; i < fitnesses.size(); i++) {
                    fitnessesArray[i] = fitnesses.get(i);
                    System.out.println(fitnessesArray[i]);
                }
                Arrays.sort(fitnessesArray);

                System.out.println("\n\n\n\n\n\n\n");

                ArrayList<Bird> bestBirds = new ArrayList<>();
                int i = fitnessesArray.length - 1;
                while (bestBirds.size() < POPULATION * PARENT_RATE) {
                    for (int j = 0; j < birds.size(); j++) {
                        Bird bird = birds.get(j);
                        if (bird.fitness == fitnessesArray[i]) {
                            bestBirds.add(bird.copy());
                        }
                    }
                    i--;
                }

                birds.clear();
                for (int j = 0; j < POPULATION; j++) {
                    int parent1Index = (int) (Math.random() * bestBirds.size());
                    int parent2Index = (int) (Math.random() * bestBirds.size());
                    System.out.println(parent1Index + ", " + parent2Index);
                    while (parent1Index == parent2Index) {
                        parent2Index = (int) (Math.random() * bestBirds.size());
                    }

                    Bird newBird = Bird.crossover(bestBirds.get(parent1Index), bestBirds.get(parent2Index));
                    newBird.mutate(0.5, 0.1);
                    birds.add(newBird);
                }

                System.out.println(birds.size());

                pipes.clear();
                pipes.add(new PipePair(panel.getWidth() + PipePair.WIDTH / 2));
                score = 0;
                aliveCount = POPULATION;
                continue;
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