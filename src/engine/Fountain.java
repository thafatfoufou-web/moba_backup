package engine;

import java.awt.*;

/**
 * @author RAHARIMANANA Tianantenaina BOUKIRAT Thafat 
 */


public class Fountain extends Entity {
    private double radius = 0.08;
    private double healPerSecond = 20.0;
    private int dmg = 2;

    public Fountain(double x, double y) {
        super(x, y, 100, 0, new Color(50, 200, 255));
    }

    public void update(double deltaTime, Player player) {
        if (getDistanceTo(player) < radius) {
            player.heal(healPerSecond * deltaTime);
        }
    }
    
    public void attack(Player player){
        if (getDistanceTo(player) < radius) {
            player.takeDamage(dmg);
        }
    }

    @Override
    public void render(Graphics2D g2, int width, int height) {
        int px = (int) (getX() * width);
        int py = (int) (getY() * height);
        int r = (int) (radius * width);
 
        g2.setColor(lowerTransparency(this.getColor(), 60));
        g2.fillOval(px - r, py - r, r * 2, r * 2);

        g2.setColor(this.getColor());
        int fSize = width / 30;
        g2.fillRect(px - fSize/2, py - fSize/2, fSize, fSize);
    }

    private Color lowerTransparency(Color color, int trans) {
        return new Color(color.getRed(), color.getGreen(), color.getBlue(), trans);
    }
}