package data;

import java.awt.Graphics2D;

public abstract class Entite {

    protected double x, y;
    protected double hp;
    protected double maxHp;
    protected boolean active = true;

    public Entite(double x, double y, double maxHp) {
        this.x     = x;
        this.y     = y;
        this.maxHp = maxHp;
        this.hp    = maxHp;
    }

    public void takeDamage(double dmg) {  // will make abstract??
        hp -= dmg;
        if (hp < 0) hp = 0;
        if (hp == 0) active = false;
    }

    public abstract void attack(Entite target);

    public abstract void render(Graphics2D g2, int width, int height);

    public double distanceTo(Entite other) {
        double dx = this.x - other.x;
        double dy = this.y - other.y;
        return Math.sqrt(dx * dx + dy * dy);
    }

    public double getX() {
        return x;
    }
    public double getY() {
        return y;
    }
    public boolean isActive() {
        return active;
    }
    public double getMaxHp() {
        return maxHp;
    }
}
