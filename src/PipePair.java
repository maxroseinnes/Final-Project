public class PipePair {
    double xPos;
    double yPos;
    private final double SPEED = 5;
    final static double WIDTH = 100;
    final static double GAP_HEIGHT = 150;
    final static double DISTANCE_BETWEEN_PIPES = 400;

    public PipePair(double xPos) {
        this.xPos = xPos;
        this.yPos = Math.random() * (FinalProject.panel.getHeight() - GAP_HEIGHT) + GAP_HEIGHT / 2;
    }

    public void update() {
        xPos -= SPEED;
        if (xPos + WIDTH / 2 > Bird.startX - Bird.SIZE / 2 - SPEED && xPos + WIDTH / 2 <= Bird.startX - Bird.SIZE / 2) {
            FinalProject.score++;
            if (FinalProject.score > FinalProject.highScore) {
                FinalProject.highScore = FinalProject.score;
            }
        }
    }
}
