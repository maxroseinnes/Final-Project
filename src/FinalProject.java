import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class FinalProject {
    Random random = new Random();
    public static final double gravity = 0.5;
    public static final double birdJumpForce = 10;
    public static final double birdSize = 20;

    static JFrame window = new JFrame("Final Project");
    static FlappyBirdPanel panel = new FlappyBirdPanel(400, 400);

    static Bird bird;

    public static void main(String[] args) throws InterruptedException {
        new FinalProject();
    }

    public FinalProject() throws InterruptedException {
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setSize(400, 400);
        panel.setBackground(new Color(50, 50, 50));
        window.add(panel);
        window.setVisible(true);

        bird = new Bird(100, panel.getHeight() / 2, birdSize, birdJumpForce);
        while (true) {
            //bird.printInfo();
            bird.update();
            Thread.sleep(1000 / 60);
        }
    }
}