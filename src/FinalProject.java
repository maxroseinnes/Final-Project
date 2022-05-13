import NeuralNet.NeuralNet;
import NeuralNet.Util;
import javax.swing.*;
import java.awt.*;
import java.util.*;

public class FinalProject {
    static JFrame window = new JFrame("Final Project");
    static FlappyBirdPanel panel = new FlappyBirdPanel(600, 600);

    static ArrayList<Bird> birds = new ArrayList<Bird>();
    final int POPULATION = 100000;
    static ArrayList<PipePair> pipes = new ArrayList<PipePair>();
    static final double DISTANCE_BETWEEN_PIPES = 500;

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

            if (birds.size() == 1) {
                Bird winner = birds.get(0);
                birds.clear();
                for (int i = 0; i < 50; i++) {
                    Bird newBird = new Bird(100, panel.getHeight() / 2);
                    newBird.brain = winner.brain.copy();
                    newBird.brain.mutate(0.01, 0.5);
                    birds.add(newBird);
                }
            }

            for (int i = 0; i < birds.size(); i++) {
                birds.get(i).update();
                birds.get(i).think();

                boolean intersectingWithPipe = false;
                for (PipePair pipe : pipes) {
                    if (birds.get(i).intersectingWithPipe(pipe)) {
                        intersectingWithPipe = true;
                        break;
                    }
                }
                if (intersectingWithPipe) {
                    birds.remove(birds.get(i));
                }
            }

            for (int i = 0; i < pipes.size(); i++) {
                pipes.get(i).update();
                if (pipes.get(i).xPos < -PipePair.WIDTH) {
                    pipes.remove(i);
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