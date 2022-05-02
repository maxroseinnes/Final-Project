public class PipePair {
    double xPos;
    double yPos;
    private final double speed = 7;
    final double width = 100;
    final double gapHeight = 150;

    public PipePair(double xPos) {
        this.xPos = xPos;
        this.yPos = Math.random() * (FinalProject.panel.getHeight() - gapHeight) + gapHeight / 2;
    }

    public void update() {
        xPos -= speed;
    }
}
