package engine;

import java.awt.*;

/**
 * @author BOUKIRAT Thafat
 */

public class Bot extends Entity {
    private double targetX, targetY;
    private double speed = 0.001;
    private double range = 0.08;
    private double damage = 0.2;
    private String name;

    public Bot(double x, double y, double targetX, double targetY, Color color, String name) {
        super(x, y, 100, 100, color);
        this.targetX = targetX;
        this.targetY = targetY;
        this.name = name;
    }

    public void update() {
        double dx = targetX - getX();
        double dy = targetY - getY();
        double distance = Math.sqrt(dx * dx + dy * dy);
        
        if (distance > speed) {
            setX(getX() + (dx / distance) * speed);
            setY(getY() + (dy / distance) * speed);
        }
    }

    public void attack(Player player) {
        if (getDistanceTo(player) < range) {
            player.takeDamage(damage);
        }
    }

    @Override
    public void render(Graphics2D g2, int width, int height) {
        int px = (int) (getX() * width);
        int py = (int) (getY() * height);
        int size = width / 40;

        g2.setColor(getColor());
        g2.fillOval(px - size/2, py - size/2, size, size);
        g2.setColor(Color.WHITE);
        g2.drawOval(px - size/2, py - size/2, size, size);

        if (name != null) {
            g2.setColor(Color.BLACK);
            g2.setFont(new Font("Arial", Font.BOLD, 12));
            g2.drawString(name, px - 15, py - size/2 - 10);
        }

        drawHealthBar(g2, px, py, size, 8);
        
        if (getMaxMana() > 0) {
            drawManaBar(g2, px, py, size, 15);
        }
    }
}