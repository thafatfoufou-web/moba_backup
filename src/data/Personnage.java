package data;

import java.awt.*;

public abstract class Personnage extends Entity {

    protected double speed;
    protected int team; // 0 for ALLY, 1 for ENEMY
    protected double mana;
    protected double maxMana;

    public Personnage(double x, double y, double maxHP, double speed, int team) {      
        super(x, y, maxHP);
        this.speed = speed;
        this.team = team;
    }


    protected void drawManaBar(Graphics2D g2, int px, int py, int size, int yOffset) {
        g2.setColor(Color.GRAY);
        g2.fillRect(px - size/2, py - size - yOffset, size, 4);
        g2.setColor(Color.CYAN);
        int manaWidth = (int)((mana / maxMana) * size);
        g2.fillRect(px - size/2, py - size - yOffset, manaWidth, 4);
        g2.setColor(Color.BLACK);
        g2.drawRect(px - size/2, py - size - yOffset, size, 4);
    }

    public void heal(double amount) {
        hp += amount;
        if (hp > maxHp) hp = maxHp;
    }

    public double getSpeed() {
        return speed;
    }
    public int getTeam() {
        return team;
    }

    public abstract void render(Graphics2D g2, int width, int height);

}