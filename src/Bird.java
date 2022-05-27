import NeuralNet.*;

import java.util.ArrayList;

public class Bird {
    final double xPos;
    double yPos;
    private double yVel = 0;

    static final double SIZE = 20;
    private final double JUMP_FORCE = 10;
    private final double GRAVITY = 0.7;
    private final double TERMINAL_VELOCITY = 8;

    NeuralNet brain = new NeuralNet(4, 6, 1);

    public Bird() {
        xPos = FinalProject.birdStartX;
        yPos = FinalProject.birdStartY;
        brain.randomize();
    }

    public void jump() {
        yVel = -JUMP_FORCE;
    }

    public void think() {
        PipePair nextPipe = getNextPipe(FinalProject.pipes);

        if (brain.feedForward(new Matrix(new double[][]{{yPos}, {yVel}, {nextPipe.xPos}, {nextPipe.yPos}})).getValue(0, 0) >= 0.5) {
            jump();
        }
    }

    public PipePair getNextPipe(ArrayList<PipePair> pipes) {
        int nextPipeIndex = 0;
        for (int i = 1; i < pipes.size(); i++) {
            if (xPos < pipes.get(i).xPos + PipePair.WIDTH && pipes.get(i).xPos < pipes.get(nextPipeIndex).xPos) {
                nextPipeIndex = i;
            }
        }

        return pipes.get(nextPipeIndex);
    }

    public void update() {
        yPos += yVel;
        yVel += GRAVITY;
        if (yVel > TERMINAL_VELOCITY) {
            yVel = TERMINAL_VELOCITY;
        }

        if (yPos < 0) {
            yPos = 0;
            yVel = 0;
        } else if (yPos > FinalProject.panel.getHeight() - SIZE) {
            yPos = FinalProject.panel.getHeight() - SIZE;
            yVel = 0;
        }
    }

    public boolean intersectingWithPipe(PipePair pipe) {
        if (xPos + SIZE > pipe.xPos && xPos < pipe.xPos + PipePair.WIDTH) {
            if ((yPos + SIZE > 0 && yPos < pipe.yPos - PipePair.GAP_HEIGHT / 2) ||
                    (yPos + SIZE > pipe.yPos + PipePair.GAP_HEIGHT / 2 && yPos < FinalProject.panel.getPreferredSize().getHeight())) {
                return true;
            }
        }
        return false;
    }

    public Bird copy() {
        Bird copy = new Bird();
        copy.brain = brain.copy();
        return copy;
    }

    public void mutate(double amount) {
        brain.mutate(amount);
    }

    public static Bird crossover(Bird a, Bird b) {
        Bird newBird = new Bird();
        newBird.brain = NeuralNet.crossover(a.brain, b.brain);
        return newBird;
    }

    public void printInfo() {
        System.out.println("X: " + xPos + ", Y: " + yPos + ", Y Velocity: " + yVel);
    }
}
