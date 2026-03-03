package data;

import java.awt.*;

import engine.Arena;
import view.GlobalAttr;

public class Player extends Personnage{
    private double CibleX, CibleY;
    private boolean isMoving;


    private double mana;
    private double MaxMana = GlobalAttr.PLAYER_MAX_MANA; 
 
    public Player(double x, double y) {
        super(x, y, GlobalAttr.PLAYER_MAX_HP, GlobalAttr.PLAYER_SPEED, 0);
        this.hp = GlobalAttr.PLAYER_START_HP;
        this.mana =GlobalAttr.PLAYER_START_MANA; 
    }

    public void update(double deltaTime,Arena arena) {
        double manaRegenRate = 2.0; 
        if (mana < MaxMana) {
            mana += manaRegenRate * deltaTime; 
            if (mana > MaxMana) mana = MaxMana;
        }
        if (isMoving) {
            updatePosition(deltaTime, arena);
        }
    }

    public void updatePosition(double deltaTime,Arena arena) {
        double dx = CibleX - x;
        double dy = CibleY - y;
        double placeholderX;
        double placeholderY;
        double distance = Math.sqrt(dx * dx + dy * dy);
        
        double moveStep = speed * deltaTime;

        if (distance < moveStep) {
        if (!arena.isCollidingWithTower(CibleX, CibleY)) {
            this.x = CibleX;
            this.y = CibleY;
        }
            isMoving = false;
        } else {
            placeholderX = x + (dx / distance) * moveStep;
            placeholderY = y + (dy / distance) * moveStep;
            if (!arena.isCollidingWithTower(placeholderX,placeholderY)) { //update it plz
            x = placeholderX;
            y = placeholderY;
            }else {   isMoving = false;  }
        }
    }

    public void render(Graphics2D g2, int width, int height) {
        int px = (int) (x * width);
        int py = (int) (y * height);
        int size = width / 40;

        double scale = size / 4.0;
        double[] rawX = {1.5, 4, 4, 3, 3, 4.5, 4.5, 3.5, 3.5, 2, 2, 1, 1, 2.5, 2.5, 1.5};
        double[] rawY = {4, 4, 2, 2, 1.5, 1.5, -2, -2, -3, -3, -2, -2, 1.5, 1.5, 2, 2};
        double cx = 0, cy = 0;
        for (int i = 0; i < rawX.length; i++) {
             cx += rawX[i];
             cy += rawY[i];
        }
        cx /= rawX.length;
        cy /= rawY.length;
        int[] xPoints = new int[16];
         int[] yPoints = new int[16];
        for (int i = 0; i < 16; i++) {
             xPoints[i] = px + (int)((rawX[i] - cx) * scale);
             yPoints[i] = py - (int)((rawY[i] - cy) * scale);
        }
         g2.setColor(Color.BLACK);
         g2.fillPolygon(xPoints, yPoints, 16);
         g2.setColor(Color.WHITE);
         g2.drawPolygon(xPoints, yPoints, 16);
        
        
       /*  g2.fillOval(px - size/2, py - size/2, size, size);
        g2.setColor(Color.WHITE);
        g2.drawOval(px - size/2, py - size/2, size, size);*/

        g2.setColor(Color.BLACK);
        g2.setFont(new Font("Arial", Font.BOLD, 12));
        g2.drawString("Hero", px - 15, py - size/2 - 10);

        //barre de HP
        g2.setColor(Color.GRAY);
        g2.fillRect(px - size/2, py - size - 8, size, 4); 
        g2.setColor(Color.GREEN);
        int hpWidth = (int)((hp / getMaxHp()) * size);
        g2.fillRect(px - size/2, py - size - 8, hpWidth, 4); 
        g2.setColor(Color.BLACK);
        g2.drawRect(px - size/2, py - size - 8, size, 4); 

        // barre de mana
        g2.setColor(Color.GRAY);
        g2.fillRect(px - size/2, py - size - 15, size, 4); 
        g2.setColor(Color.CYAN);
        int manaWidth = (int)((mana / MaxMana) * size);
        g2.fillRect(px - size/2, py - size - 15, manaWidth, 4); 
        g2.setColor(Color.BLACK);
        g2.drawRect(px - size/2, py - size - 15, size, 4); 
    }

    public void moveTo(double CibleX, double CibleY) {
        this.CibleX = CibleX;
        this.CibleY = CibleY;
        this.isMoving = true;
    }

    public void heal(double amount) {
        this.hp += amount;
        if (this.hp > getMaxHp()) {
            this.hp = getMaxHp();
        }
    }

    public void takeDamage(double dmg){
        if(hp<dmg){ this.hp=0;}
        else{
            this.hp-= dmg;
        }

    }

    public boolean isDead(){
        return(this.hp==0);
    }

    public void respawn() {
        hp = getMaxHp();
        mana = MaxMana;
        x = 0.1;
        y = 0.9;
    }

    public double getX() { return x; }
    public double getY() { return y; }

    public Rectangle getHitbox(int width, int height) {
         int size = width / 40;
         int px = (int)(x * width);
         int py = (int)(y * height);
        return new Rectangle(px - size/2, py - size/2, size, size);
}

    @Override
    public void attack(Entite target) {
        double dist = distanceTo(target);
        if (dist < 0.05) { 
            target.takeDamage(100); // placeholder METHOD
        }
    }
}