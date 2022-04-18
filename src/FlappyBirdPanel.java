import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class FlappyBirdPanel extends JPanel implements MouseListener {
    public FlappyBirdPanel(int width, int height) {
        setSize(width, height);
        addMouseListener(this);
    }

    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        Bird bird = FinalProject.bird;
        graphics.setColor(Color.YELLOW);
        graphics.fillRect((int) (bird.xPos - bird.size / 2), (int) (bird.yPos - bird.size / 2), (int) bird.size, (int) bird.size);
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        FinalProject.bird.jump();
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
