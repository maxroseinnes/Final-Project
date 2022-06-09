import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

public class FlappyBirdPanel extends JPanel implements MouseListener {
    public FlappyBirdPanel(int width, int height) {
        setPreferredSize(new Dimension(width, height));
        addMouseListener(this);
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
            if (bird.alive) {
                g.fillRect((int) (bird.xPos - Bird.SIZE / 2), (int) (bird.yPos - Bird.SIZE / 2), (int) Bird.SIZE, (int) Bird.SIZE);
            }
        }

        g.setColor(Color.WHITE);
        g.setFont(new Font("Times", Font.PLAIN, 25));
        g.drawString("Generation: " + FinalProject.generation, 0, 25);
        g.drawString("Alive: " + FinalProject.aliveCount, 0, 50);
        g.drawString("Score: " + FinalProject.score, 0, 75);
        g.drawString("High Score: " + FinalProject.highScore, 0, 100);
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        FinalProject.slow = !FinalProject.slow;
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
