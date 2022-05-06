import javax.swing.*;
import java.awt.*;
import java.util.*;

public class FinalProject {
    static JFrame window = new JFrame("Final Project");
    static FlappyBirdPanel panel = new FlappyBirdPanel(600, 600);

    static ArrayList<Bird> birds = new ArrayList<Bird>();
    final int POPULATION = 100000;
    static ArrayList<PipePair> pipes = new ArrayList<PipePair>();
    static final double DISTANCE_BETWEEN_PIPES = 350;

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
            for (int i = 0; i < birds.size(); i++) {
                birds.get(i).think();
                birds.get(i).update();

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
            Thread.sleep(1000 / 60);
        }
    }
}