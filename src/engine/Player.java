package engine;

import java.awt.*;

/**
 * @author BOUKIRAT Thafat RAHARIMANANA Tianantenaina 
 */
public class Player extends Entity {
    private double targetX, targetY;
    private boolean isMoving;
    private double speed = 0.2;

    public Player(double x, double y) {
        super(x, y, 100, 100, Color.BLACK);
    }

    public void update(double deltaTime) {
        double manaRegenRate = 2.0;
        if (getMana() < getMaxMana()) {
            double newMana = getMana() + manaRegenRate * deltaTime;
            if (newMana > getMaxMana()) newMana = getMaxMana();
            setMana(newMana);
        }
        
        if (isMoving) {
            updatePosition(deltaTime);
        }
    }

    private void updatePosition(double deltaTime) {
        double dx = targetX - getX();
        double dy = targetY - getY();
        double distance = Math.sqrt(dx * dx + dy * dy);
        
        double moveStep = speed * deltaTime;

        if (distance < moveStep) {
            setX(targetX);
            setY(targetY);
            isMoving = false;
        } else {
            setX(getX() + (dx / distance) * moveStep);
            setY(getY() + (dy / distance) * moveStep);
        }
    }

    public void moveTo(double targetX, double targetY) {
        this.targetX = targetX;
        this.targetY = targetY;
        this.isMoving = true;
    }
    
    @Override
    public void takeDamage(double dmg){
        if(getHp() < dmg){ 
            setHp(0);
        } else {
            setHp(getHp() - dmg);
        }
    }

    public void respawn() {
        setHp(getMaxHp());
        setMana(getMaxMana());
        setX(0.1);
        setY(0.9);
    }

    @Override
    public void render(Graphics2D g2, int width, int height) {
        int px = (int) (getX() * width);
        int py = (int) (getY() * height);
        int size = width / 40;

        g2.setColor(getColor());
        g2.fillOval(px - size/2, py - size/2, size, size);
        g2.setColor(Color.WHITE);
        g2.drawOval(px - size/2, py - size/2, size, size);

        g2.setColor(Color.BLACK);
        g2.setFont(new Font("Arial", Font.BOLD, 12));
        g2.drawString("Hero", px - 15, py - size/2 - 10);

        drawHealthBar(g2, px, py, size, 8);
        drawManaBar(g2, px, py, size, 15);
    }
}