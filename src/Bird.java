import NeuralNet.*;

public class Bird {
    final double xPos;
    double yPos;
    private double yVel;
    final double size = 20;
    private final double jumpForce = 10;
    private final double gravity = 0.7;
    private final double terminalVelocity = 10;

    NeuralNet brain = new NeuralNet(2, 6, 1);

    public Bird(double startX, double startY) {
        xPos = startX;
        yPos = startY;
        this.yVel = 0;
        brain.randomize();
    }

    public void jump() {
        yVel = -jumpForce;
    }

    public void think() {
        if (brain.feedForward(new Matrix(new double[][] {{yPos}, {yVel}})).getValue(0, 0) >= 0.5) {
            jump();
        }
    }

    public void update() {
        yPos += yVel;
        yVel += gravity;
        if (yVel > terminalVelocity) {
            yVel = terminalVelocity;
        }

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
