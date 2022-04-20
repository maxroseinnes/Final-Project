public class PipePair {
    double xPos;
    double yPos;
    private final double speed = 5;
    final double width = 50;
    final double gapHeight = 100;

    public PipePair(double xPos) {
        this.xPos = xPos;
        this.yPos = Math.random() * (FinalProject.panel.getHeight() - gapHeight / 2) + gapHeight / 2;
    }

    public void update() {
        xPos -= speed;
    }
}
