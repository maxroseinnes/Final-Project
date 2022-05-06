import javax.swing.*;
import java.awt.*;

public class FlappyBirdPanel extends JPanel {
    public FlappyBirdPanel(int width, int height) {
        setPreferredSize(new Dimension(width, height));
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Bird[] birds = FinalProject.birds.toArray(new Bird[0]);
        PipePair[] pipes = FinalProject.pipes.toArray(new PipePair[0]);

        g.setColor(Color.GREEN);
        for (PipePair pipe : pipes) {
            g.fillRect((int) pipe.xPos, 0, (int) pipe.WIDTH, (int) (pipe.yPos - pipe.GAP_HEIGHT / 2));
            g.fillRect((int) pipe.xPos, (int) (pipe.yPos + pipe.GAP_HEIGHT / 2), (int) pipe.WIDTH, FinalProject.panel.getHeight());
        }

        g.setColor(Color.YELLOW);
        for (Bird bird : birds) {
            g.fillRect((int) (bird.xPos - bird.SIZE / 2), (int) (bird.yPos - bird.SIZE / 2), (int) bird.SIZE, (int) bird.SIZE);
        }

        g.setColor(Color.WHITE);
        g.setFont(new Font("Times", Font.PLAIN, 25));
        g.drawString("Alive: " + birds.length, 0, 25);
    }
}
