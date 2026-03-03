package engine;

import java.awt.*;

/**
 * @author RAHARIMANANA Tianantenaina BOUKIRAT Thafat
 */
public class Tower extends Entity {
    private double radius = 0.1;
    private double damage = 0.2;

    public Tower(double positionX, double positionY, Color teamColor) {
        super(positionX, positionY, 100, 0, teamColor);
    }

    public void attack(Player player) {
        if (getDistanceTo(player) < radius) {
            player.takeDamage(damage);
        }
    }

    public boolean isAlly() {
        return getColor().equals(Color.BLUE);
    }

    public boolean isEnemy() {
        return getColor().equals(Color.RED);
    }

    @Override
    public void render(Graphics2D g2, int width, int height) {
        int px = (int) (getX() * width);
        int py = (int) (getY() * height);
        int size = width / 60;

        g2.setColor(getColor());
        g2.fillRect(px - size/2, py - size/2, size, size);

        g2.setColor(Color.BLACK);
        g2.drawRect(px - size/2, py - size/2, size, size);
        
        drawHealthBar(g2, px, py, size, 5);
    }
}