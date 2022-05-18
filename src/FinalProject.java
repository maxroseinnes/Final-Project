import NeuralNet.NeuralNet;
import NeuralNet.Util;
import javax.swing.*;
import java.awt.*;
import java.util.*;

public class FinalProject {
    static JFrame window = new JFrame("Final Project");
    static FlappyBirdPanel panel = new FlappyBirdPanel(600, 600);

    static ArrayList<Bird> birds = new ArrayList<Bird>();
    final int POPULATION = 50;
    static ArrayList<PipePair> pipes = new ArrayList<PipePair>();
    static final double DISTANCE_BETWEEN_PIPES = 400;

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
            birds.add(new Bird(100, panel.getHeight() / 2));
        }
        pipes.add(new PipePair(panel.getWidth()));

        while (true) {
            long temp = System.nanoTime();

            Bird winner = null;

            Iterator<Bird> birdIterator = birds.iterator();
            while (birdIterator.hasNext()) {
                Bird bird = birdIterator.next();
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
                    birdIterator.remove();
                    if (birds.size() == 0) {
                        winner = bird;
                    }
                }
            }

            if (winner != null) {
                for (int i = 0; i < POPULATION; i++) {
                    Bird newBird = new Bird(100, panel.getHeight() / 2);
                    newBird.brain = winner.brain.copy();
                    newBird.brain.mutate(1, 0.1);
                    birds.add(newBird);
                }
                pipes.clear();
                pipes.add(new PipePair(panel.getWidth()));
            }

            Iterator<PipePair> pipeIterator = pipes.iterator();
            while (pipeIterator.hasNext()) {
                PipePair pipe = pipeIterator.next();

                pipe.update();
                if (pipe.xPos < -PipePair.WIDTH) {
                    pipeIterator.remove();
                }
            }

            if (panel.getWidth() - pipes.get(pipes.size() - 1).xPos > DISTANCE_BETWEEN_PIPES) {
                PipePair newPipe = new PipePair(pipes.get(pipes.size() - 1).xPos + DISTANCE_BETWEEN_PIPES);
                pipes.add(newPipe);
            }
            panel.paintImmediately(0, 0, panel.getWidth(), panel.getHeight());

            long elapsed = System.nanoTime() - temp;
            if (elapsed < (long) 1000000000 / 60) {
                Thread.sleep(1000 / 60 - elapsed / 1000000);
            }
        }
    }
}