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
        for (int i = 0; i < pipes.size(); i++) {
            PipePair current = pipes.get(i);
            g.fillRect((int) current.xPos, 0, (int) current.width, (int) (current.yPos - current.gapHeight / 2));
            g.fillRect((int) current.xPos, (int) (current.yPos + current.gapHeight / 2), (int) current.width, FinalProject.panel.getHeight());
        }

        g.setColor(Color.YELLOW);
        for (Bird bird : birds) {
            g.fillRect((int) (bird.xPos - bird.SIZE / 2), (int) (bird.yPos - bird.SIZE / 2), (int) bird.SIZE, (int) bird.SIZE);
        }
    }



    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
