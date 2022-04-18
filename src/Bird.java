import NeuralNet.NeuralNet;

public class Bird {
    final double xPos;
    double yPos;
    private double yVel;
    final double size;
    private final double jumpForce;

    NeuralNet brain = new NeuralNet(3, 5, 1);

    public Bird(double startX, double startY, double size, double jumpForce) {
        xPos = startX;
        yPos = startY;
        this.yVel = 0;
        this.size = size;
        this.jumpForce = jumpForce;
        brain.randomize();
    }

    public void jump() {
        yVel = -jumpForce;
    }

    public void update() {
        FinalProject.panel.repaint();

        yVel += FinalProject.gravity;
        yPos += yVel;

        if (yPos < size / 2) {
            yPos = size / 2;
            yVel = 0;
        } else if (yPos > FinalProject.panel.getHeight() - size / 2) {
            yPos = FinalProject.panel.getHeight() - size / 2;
            yVel = 0;
        }
    }

    public void printInfo() {
        System.out.println("X: " + xPos + ", Y: " + yPos + ", Y Velocity: " + yVel);
    }
}
