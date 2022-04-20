import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class FinalProject {
    Random random = new Random();

    static JFrame window = new JFrame("Final Project");
    static FlappyBirdPanel panel = new FlappyBirdPanel(600, 600);

    static Bird[] birds = new Bird[1000];
    static PipePair[] pipes = new PipePair[2];
    static final double distanceBetweenPipes = 300;

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

        for (int i = 0; i < birds.length; i++) {
            birds[i] = new Bird(100, panel.getHeight() / 2);
        }
        for (int i = 0; i < pipes.length; i++) {
            pipes[i] = new PipePair(distanceBetweenPipes * i + panel.getWidth());
        }
        while (true) {
            for (int i = 0; i < birds.length; i++) {
                birds[i].think();
                birds[i].update();
            }
            for (int i = 0; i < pipes.length; i++) {
                pipes[i].update();
            }
            panel.repaint();
            Thread.sleep(1000 / 60);
        }
    }
}