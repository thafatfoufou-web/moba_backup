package data;

import java.awt.*;

import view.GlobalAttr;

public class Bot extends Personnage {

    private double targetX, targetY;
    private String name;

    private double range = GlobalAttr.BOT_RANGE;
    private double dmg = GlobalAttr.BOT_DAMAGE;

    public Bot(double x, double y, double targetX, double targetY, int team, String name) {
        super(x, y, GlobalAttr.BOT_MAX_HP, GlobalAttr.BOT_SPEED, team);

        this.targetX = targetX;
        this.targetY = targetY;
        this.name = name;
        this.maxMana = GlobalAttr.BOT_MAX_MANA;
        this.mana = this.maxMana;
    }

    // ======================
    // Movement
    // ======================
    public void update() {
        double dx = targetX - getX();
        double dy = targetY - getY();
        double distance = Math.sqrt(dx * dx + dy * dy);

        if (distance > speed) {
            setX(getX() + (dx / distance) * speed);
            setY(getY() + (dy / distance) * speed);
        }
    }

 
    // Attack
   
    @Override
    public void attack(Entity target) {
        double dist = getDistanceTo(target);

        if (dist < range && target.isActive()) {
            target.takeDamage(dmg);
        }
    }


    // Render

    @Override
    public void render(Graphics2D g2, int width, int height) {

        int px = (int) (getX() * width);
        int py = (int) (getY() * height);
        int size = width / 40;

        // Couleur selon team
        if (this.team == 0) {
            g2.setColor(new Color(0, 150, 255));
        } else {
            g2.setColor(new Color(255, 0, 150));
        }

        // Dessin du bot
        g2.fillOval(px - size / 2, py - size / 2, size, size);
        g2.setColor(Color.BLACK);
        g2.drawOval(px - size / 2, py - size / 2, size, size);

        // Nom
        g2.setFont(new Font("Arial", Font.BOLD, 12));
        g2.drawString(name, px - 15, py - size / 2 - 10);

        // Barre de vie 
        drawHealthBar(g2, px, py, size, 8);

        // Barre de mana 
        if (maxMana > 0) {
            drawManaBar(g2, px, py, size, 15);
        }
    }
}