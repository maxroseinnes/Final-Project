import NeuralNet.*;

import java.util.ArrayList;

public class Bird {
    final double xPos;
    double yPos;
    private double yVel = 0;

    final double SIZE = 20;
    private final double JUMP_FORCE = 10;
    private final double GRAVITY = 0.7;
    private final double TERMINAL_VELOCITY = 8;

    NeuralNet brain = new NeuralNet(4, 6, 1);

    public Bird(double startX, double startY) {
        xPos = startX;
        yPos = startY;
        this.yVel = 0;
        brain.randomize();
    }

    public void jump() {
        yVel = -JUMP_FORCE;
    }

    public void think() {
        ArrayList<PipePair> pipes = FinalProject.pipes;
        int nearestPipeIndex = 0;
        for (int i = 1; i < pipes.size(); i++) {
            if (xPos < pipes.get(i).xPos + pipes.get(i).width && pipes.get(i).xPos < pipes.get(nearestPipeIndex).xPos) {
                nearestPipeIndex = i;
            }
        }

        double xPosNearestPipe = pipes.get(nearestPipeIndex).xPos;
        double yPosNearestPipe = pipes.get(nearestPipeIndex).yPos;

        if (brain.feedForward(new Matrix(new double[][]{{yPos}, {yVel}, {xPosNearestPipe}, {yPosNearestPipe}})).getValue(0, 0) >= 0.5) {
            jump();
        }
    }

    public void update() {
        yPos += yVel;
        yVel += GRAVITY;
        if (yVel > TERMINAL_VELOCITY) {
            yVel = TERMINAL_VELOCITY;
        }

        if (yPos < SIZE / 2) {
            yPos = SIZE / 2;
            yVel = 0;
        } else if (yPos > FinalProject.panel.getHeight() - SIZE / 2) {
            yPos = FinalProject.panel.getHeight() - SIZE / 2;
            yVel = 0;
        }
    }

    public boolean intersectingWithPipe(PipePair pipe) {
        return xPos + SIZE / 2 > pipe.xPos && xPos - SIZE / 2 < pipe.xPos + pipe.width &&
                yPos + SIZE / 2 > 0 && yPos - SIZE / 2 < pipe.yPos - pipe.gapHeight / 2 ||
                xPos + SIZE / 2 > pipe.xPos && xPos - SIZE / 2 < pipe.xPos + pipe.width &&
                        yPos + SIZE / 2 > pipe.yPos + pipe.gapHeight / 2 && yPos - SIZE / 2 < FinalProject.panel.getHeight();
    }

    public void printInfo() {
        System.out.println("X: " + xPos + ", Y: " + yPos + ", Y Velocity: " + yVel);
    }
}
