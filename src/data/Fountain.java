package data;

import java.awt.*;

import view.GlobalAttr;
/**
 * @author RAHARIMANANA Tianantenaina BOUKIRAT Thafat 
 */

public class Fountain extends Entity {
    private double radius = GlobalAttr.FOUNTAIN_RADIUS; 
    private double healPerSecond = GlobalAttr.FOUNTAIN_HEAL_PER_SEC; //render fontain with base aka have base render fountain
    private double dmg= GlobalAttr.FOUNTAIN_DAMAGE;
    int team;

    public Fountain(double x, double y,int team) {
        super(x, y, 100);
        this.team=team;
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
 
        if(this.team==0) {
        	g2.setColor(new Color(50, 200, 255,60));
        }else {
        	g2.setColor(new Color(202, 94, 90,60));
        }
        g2.fillOval(px - r, py - r, r * 2, r * 2);

        if(this.team==0) {
        	g2.setColor(new Color(50, 200, 255));
        }else {
        	g2.setColor(new Color(202, 94, 90));
        }
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