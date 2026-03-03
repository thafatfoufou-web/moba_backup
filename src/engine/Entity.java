package engine;

import java.awt.*;

/**
 * @author RAHARIMANANA Tianantenaina BOUKIRAT Thafat
 */

public abstract class Entity {
    public double getHp() {
		return hp;
	}

	public void setHp(double hp) {
		this.hp = hp;
	}

	public double getMaxHp() {
		return maxHp;
	}

	public void setMaxHp(double maxHp) {
		this.maxHp = maxHp;
	}

	public double getMana() {
		return mana;
	}

	public void setMana(double mana) {
		this.mana = mana;
	}

	public double getMaxMana() {
		return maxMana;
	}

	public void setMaxMana(double maxMana) {
		this.maxMana = maxMana;
	}

	public void setX(double x) {
		this.x = x;
	}

	public void setY(double y) {
		this.y = y;
	}

	private double x, y;
    private double hp;
    private double maxHp;
    private double mana;
    private double maxMana;
    private Color color;

    public Entity(double x, double y, double maxHp, double maxMana, Color color) {
        this.x = x;
        this.y = y;
        this.maxHp = maxHp;
        this.hp = maxHp;
        this.maxMana = maxMana;
        this.mana = maxMana;
        this.setColor(color);
    }

    public void heal(double amount) {
        hp += amount;
        if (hp > maxHp) hp = maxHp;
    }

    public void takeDamage(double dmg) {
        hp -= dmg;
        if (hp < 0) hp = 0;
    }

    public boolean isDead() {
        return hp == 0;
    }

    public double getX() { return x; }
    public double getY() { return y; }

    protected void drawHealthBar(Graphics2D g2, int px, int py, int size, int yOffset) {
        g2.setColor(Color.GRAY);
        g2.fillRect(px - size/2, py - size - yOffset, size, 4);
        g2.setColor(Color.GREEN);
        int hpWidth = (int)((hp / maxHp) * size);
        g2.fillRect(px - size/2, py - size - yOffset, hpWidth, 4);
        g2.setColor(Color.BLACK);
        g2.drawRect(px - size/2, py - size - yOffset, size, 4);
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

    protected double getDistanceTo(Entity other) {
        double dx = other.x - this.x;
        double dy = other.y - this.y;
        return Math.sqrt(dx * dx + dy * dy);
    }

    public abstract void render(Graphics2D g2, int width, int height);

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}
}