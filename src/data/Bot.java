package data;

import java.awt.*;

import view.GlobalAttr;

public class Bot extends Personnage{
    
 
     private double targetX, targetY;
    private double mana;
    private double maxMana = GlobalAttr.BOT_MAX_MANA; 

    private String name;

    private double range=GlobalAttr.BOT_RANGE;
    private double dmg=GlobalAttr.BOT_DAMAGE;



    public Bot(double x, double y, double targetX, double targetY, int team, String name) {
        super(x, y, GlobalAttr.BOT_MAX_HP, GlobalAttr.BOT_SPEED, team);
        this.targetX = targetX;
        this.targetY = targetY;
        this.name = name;
        this.mana = 20;
    }



    public void update() {
        double dx = targetX - x;
        double dy = targetY - y;
        double distance = Math.sqrt(dx * dx + dy * dy);
        
        if (distance > speed) {
            x = x + (dx / distance) * speed;
            y = y + (dy / distance) * speed;
        }
    }

    public void render(Graphics2D g2, int width, int height) {
        int px = (int) (x * width);
        int py = (int) (y * height);
        int size = width / 40;

        if (team == 0) {
            g2.setColor(new Color(0, 150, 255));
        } else {
            g2.setColor(new Color(255, 0, 150));
        }
        g2.fillOval(px - size/2, py - size/2, size, size);
        g2.setColor(Color.BLACK);
        g2.drawOval(px - size/2, py - size/2, size, size);

        g2.setColor(Color.BLACK);
        g2.setFont(new Font("Arial", Font.BOLD, 12));
        g2.drawString(name, px - 15, py - size/2 - 10);

        //barre de vie
        g2.setColor(Color.GRAY);
        g2.fillRect(px - size/2, py - size - 8, size, 4); 
        g2.setColor(Color.GREEN);

        int hpWidth = (int)((hp / maxHp) * size);
        g2.fillRect(px - size/2, py - size - 8, hpWidth, 4); 
        g2.setColor(Color.BLACK);
        g2.drawRect(px - size/2, py - size - 8, size, 4); 

        // barre de mana
        g2.setColor(Color.GRAY);
        g2.fillRect(px - size/2, py - size - 15, size, 4); 
        g2.setColor(Color.CYAN);
        int manaWidth = (int)((mana / maxMana) * size);
        g2.fillRect(px - size/2, py - size - 15, manaWidth, 4); 
        g2.setColor(Color.BLACK);
        g2.drawRect(px - size/2, py - size - 15, size, 4); 
    }


    @Override
        public void attack(Entite target) {          
          double dist = distanceTo(target);
          if (dist < range) { 
             target.takeDamage(dmg);
          }
        }


}