package data;

import java.awt.*;
import java.awt.Graphics2D;

import view.GlobalAttr;

public class Base extends Entite {

    Fountain fountain;
    int team; // 0 for ALLY, 1 for ENEMY
    private double radius = GlobalAttr.BASE_RANGE;
    private double dmg = GlobalAttr.BASE_DAMAGE; 


    public Base(double x, double y, int team) {
        super(x, y, GlobalAttr.BASE_MAX_HP);
        this.team = team;
    }

    @Override
    public void attack(Entite target) {
        double dist = distanceTo(target);
        if (dist < radius) { 
            target.takeDamage(dmg);
        }
    }
    

    @Override
    public void render(Graphics2D g2, int width, int height) { // modify later

        int px = (int)(x * width);
         int py = (int)(y * height);
        int size = width / 20;

        if(team == 0) {
            g2.setColor(Color.BLUE);
        } else {
            g2.setColor(Color.RED);
        }
        g2.fillRect(px - size/2, py - size/2, size, size);

        g2.setColor(Color.BLACK);
        g2.drawRect(px - size/2, py - size/2, size, size); 
        
        g2.setColor(Color.GREEN);

        int hpWidth = (int)((double)hp/maxHp * size);
        g2.fillRect(px - size/2, py - size - 5, hpWidth, 3);

        g2.setColor(Color.BLACK);
        g2.drawRect(px - size/2, py - size - 5, size, 3);
    }
    
}
