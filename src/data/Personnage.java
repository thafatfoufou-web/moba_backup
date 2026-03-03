package data;

import java.awt.*;

import engine.Entity;

public abstract class Personnage extends Entity {

    protected double speed;
    protected int team; // 0 for ALLY, 1 for ENEMY
    protected double mana;
    protected double maxMana;

    public Personnage(double x, double y, double maxHP, double speed, int team, double maxMana) {      
        super(x, y, maxHP);
        this.speed = speed;
        this.team = team;
        this.maxMana = maxMana;
        this.mana = maxMana;
    }


    public void heal(double amount) {
        hp += amount;
        if (hp > maxHp) hp = maxHp;
    }

    public boolean isDead() {
        return hp <= 0;
    }
    public double getSpeed() {
        return speed;
    }
    public int getTeam() {
        return team;
    }

    public abstract void render(Graphics2D g2, int width, int height);
}