import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

public class FlappyBirdPanel extends JPanel implements MouseListener {
    public FlappyBirdPanel(int width, int height) {
        setPreferredSize(new Dimension(width, height));
        addMouseListener(this);
    }

    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        Bird[] birds = FinalProject.birds;
        ArrayList<PipePair> pipes = FinalProject.pipes;
        graphics.setColor(Color.YELLOW);
        for (int i = 0; i < birds.length; i++) {
            graphics.fillArc((int) (birds[i].xPos - birds[i].size / 2), (int) (birds[i].yPos - birds[i].size / 2), (int) birds[i].size, (int) birds[i].size, 0, 360);
        }

        graphics.setColor(Color.GREEN);
        for (int i = 0; i < pipes.size(); i++) {
            PipePair current = pipes.get(i);
            graphics.fillRect((int) current.xPos, 0, (int) current.width, (int) (current.yPos - current.gapHeight / 2));
            graphics.fillRect((int) current.xPos, (int) (current.yPos + current.gapHeight / 2), (int) current.width, FinalProject.panel.getHeight());
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
