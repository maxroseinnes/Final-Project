public class PipePair {
    double xPos;
    double yPos;
    private final double speed = 3;
    final static double WIDTH = 100;
    final static double GAP_HEIGHT = 150;

    public PipePair(double xPos) {
        this.xPos = xPos;
        this.yPos = Math.random() * (FinalProject.panel.getHeight() - GAP_HEIGHT) + GAP_HEIGHT / 2;
    }

    public void update() {
        xPos -= speed;
    }
}
