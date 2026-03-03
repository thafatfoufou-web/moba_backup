package engine;

public class Camera {
    private double x, y;
    private double cameraWidth;
    private double cameraHeight;
    private double targetX;
    private double targetY;
    private double worldWidth;
    private double worldHeight;

    public Camera(double cameraWidth, double cameraHeight) {
        this.cameraWidth = cameraWidth;
        this.cameraHeight = cameraHeight;
        this.x = 0;
        this.y = 0;
    }

    public void setCameraView(double width, double height) {
        this.cameraWidth = width;
        this.cameraHeight = height;
    }

    public void follow(double targetX, double targetY) {
        this.targetX = targetX;
        this.targetY = targetY;
    }

    public void setWorldBounds(double worldWidth, double worldHeight) {
        this.worldWidth = worldWidth;
        this.worldHeight = worldHeight;
    }

    public void update(double deltaTime) {
        x = targetX - cameraWidth / 2;
        y = targetY - cameraHeight / 2;
        clampToWorld();
    }

    private void clampToWorld() {
        if (worldWidth <= 0 || worldHeight <= 0) return;
        if (x < 0) x = 0;
        if (y < 0) y = 0;
        if (x > worldWidth - cameraWidth) {x = worldWidth - cameraWidth;}
        if (y > worldHeight - cameraHeight){ y = worldHeight - cameraHeight;}
    }

    public double getX() { return x; }
    public double getY() { return y; }
}