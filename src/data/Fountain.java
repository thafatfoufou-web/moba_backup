package data;

import java.awt.*;

import engine.Entity;
import view.GlobalAttr;
/**
 * @author RAHARIMANANA Tianantenaina BOUKIRAT Thafat 
 */

public class Fountain extends Entity {
    private double radius = GlobalAttr.FOUNTAIN_RADIUS; 
    private double healPerSecond = GlobalAttr.FOUNTAIN_HEAL_PER_SEC; //render fontain with base aka have base render fountain
    private double dmg= GlobalAttr.FOUNTAIN_DAMAGE;

    public Fountain(double x, double y) {
        super(x, y, 100);
    }

    public void update(double deltaTime, Player player) {
        if (getDistanceTo(player) < radius) {
            player.heal(healPerSecond * deltaTime);
        }
    }

    @Override
    public void render(Graphics2D g2, int width, int height) {
        int px = (int) (getX() * width);
        int py = (int) (getY() * height);
        int r = (int) (radius * width);
 
        g2.setColor(lowerTransperacy(this.getColor(), 60));
        g2.fillOval(px - r, py - r, r * 2, r * 2);

        g2.setColor(this.getColor());
        int fSize = width / 30;
        g2.fillRect(px - fSize/2, py - fSize/2, fSize, fSize);
    }

    public Color lowerTransperacy(Color color, int trans){
        return (new Color(color.getRed(),color.getGreen(),color.getBlue(),trans) );
        }

    public void attack(Entity target){  
        double dx = target.getX() - x;
        double dy = target.getY() - y;
        double dist = Math.sqrt(dx * dx + dy * dy);
        if(dist<radius){target.takeDamage(dmg);}
    }
}