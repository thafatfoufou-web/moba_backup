package data;

import java.awt.*;

import view.GlobalAttr;

public class Tower extends Entite {

    private int team;


    private double radius=GlobalAttr.TOWER_RANGE;
    private double dmg= GlobalAttr.TOWER_DAMAGE;
    

    public Tower(double X, double Y, int team) {
        super(X, Y, GlobalAttr.TOWER_MAX_HP);
        this.team=team;
    }

    public void render(Graphics2D g2, int width, int height) {
        int px = (int) (getX() * width);
        int py = (int) (getY() * height);
        int size = width / 60;

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
    


    
    public boolean isAlly() {
    return (this.team == 0);
    }
    public boolean isEnemy(){
        return(this.team == 1);
    }
    
    public int getTeam() {
        return team;
    }
    public Rectangle getHitbox(int width, int height) {
    int size = width / 60;
    int px = (int)(getX() * width);
    int py = (int)(getY() * height);
    return new Rectangle(px - size/2, py - size/2, size, size);
}

    @Override
    public void attack(Entite target) {
        double dist = distanceTo(target);
        if (dist < radius) { 
            target.takeDamage(dmg);
        }
    }
    
}