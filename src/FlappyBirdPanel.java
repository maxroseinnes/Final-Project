import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class FlappyBirdPanel extends JPanel {
    public FlappyBirdPanel(int width, int height) {
        setPreferredSize(new Dimension(width, height));
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        ArrayList<Bird> birds = FinalProject.birds;
        ArrayList<PipePair> pipes = FinalProject.pipes;

        g.setColor(Color.GREEN);
        for (PipePair pipe : pipes) {
            g.fillRect((int) (pipe.xPos - PipePair.WIDTH / 2), 0, (int) PipePair.WIDTH, (int) (pipe.yPos - PipePair.GAP_HEIGHT / 2));
            g.fillRect((int) (pipe.xPos - PipePair.WIDTH / 2), (int) (pipe.yPos + PipePair.GAP_HEIGHT / 2), (int) PipePair.WIDTH, FinalProject.panel.getHeight());
        }

        g.setColor(Color.YELLOW);
        for (Bird bird : birds) {
            g.fillRect((int) (bird.xPos - Bird.SIZE / 2), (int) (bird.yPos - Bird.SIZE / 2), (int) Bird.SIZE, (int) Bird.SIZE);
        }

        g.setColor(Color.WHITE);
        g.setFont(new Font("Times", Font.PLAIN, 25));
        g.drawString("Alive: " + birds.size(), 0, 25);
    }
}
