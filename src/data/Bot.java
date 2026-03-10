package data;

import java.awt.*;
import java.util.List;

import view.GlobalAttr;

public class Bot extends Personnage {


    private  double spawnX ; 
    private  double spawnY ;

    private String name;

    private double range = GlobalAttr.BOT_RANGE;
    private double dmg = GlobalAttr.BOT_DAMAGE;

    private double attackCooldown = 1.0;
    private double attackTimer    = 0;
    private double respawnTimer   = 15;  //placeholder
    
    private List<double[]> waypoints; //path
    private int waypointIndex=0;
    
    private enum State { MOVING, FIGHTING,RETREATING } //for animations
    private State state = State.MOVING;
    

    public Bot(double x, double y, List<double[]> waypoints , int team, String name) {

        super(x, y, GlobalAttr.BOT_MAX_HP, GlobalAttr.BOT_SPEED, team);
        this.spawnX = x;
        this.spawnY = y;
        this.waypoints = waypoints;
        this.name = name;
        this.maxMana = GlobalAttr.BOT_MAX_MANA;
        this.mana = this.maxMana;
    }

    // Movement
    public void update(double deltaTime,List<Entity> enemies,List<Bot> allBots) {

        // this checks if the bot is dead will change it eventually
        if (!active) { 
            respawnTimer -= deltaTime;
            if(respawnTimer <= 0) {  respawn();  }
            return;
        }

        attackTimer -= deltaTime;
        Entity target = findClosestEnemy(enemies);
         if (target != null && getDistanceTo(target) <= range) {
            state = State.FIGHTING; //will need for animation

            if (attackTimer <= 0) {
                attack(target);
                 attackTimer = attackCooldown;
             }  
        }else {
                state = State.MOVING;
                followWaypoints(allBots);
      }
    }
 
    @Override
    public void attack(Entity target) {
        double dist = getDistanceTo(target);

        if (dist < range && target.isActive()) {
            target.takeDamage(dmg);
        }
    }



    @Override
    public void render(Graphics2D g2, int width, int height) {


        if (!active) {return;}

        int px = (int) (getX() * width);
        int py = (int) (getY() * height);
        int size = width / 40;

        //team color
        if (this.team == 0) {
            g2.setColor(new Color(0, 150, 255));
        } else {
            g2.setColor(new Color(255, 0, 150));
        }

        // BOT but place holder will be using the hero render
        g2.fillOval(px - size / 2, py - size / 2, size, size);
        g2.setColor(Color.BLACK);
        g2.drawOval(px - size / 2, py - size / 2, size, size);

        // Nom Same
        g2.setFont(new Font("Arial", Font.BOLD, 12));
        g2.drawString(name, px - 15, py - size / 2 - 10);

        // Hp
        drawHealthBar(g2, px, py, size, 8);

        // mana 
        if (maxMana > 0) {
            drawManaBar(g2, px, py, size, 15);
        }
        //if (state == State.FIGHTING) {} wanna add a visual angry icon when fighting
        //P2 = polygon(65,66,67,68)
                //P1 = polygon(42,43,44,45)
                //p1 = (6,1.15)
waypointIndex++;
    }

    private Entity findClosestEnemy(List<Entity> enemies) {
        Entity closest = null;
        double closestDist = Double.MAX_VALUE;
        for (Entity e : enemies) {
        if (!e.isActive()) continue;
        double d = getDistanceTo(e);
        if (d < closestDist) {
            closestDist = d;
            closest = e; }}
        return closest;
    }
    
    private void followWaypoints(List<Bot> allBots) {
        if (waypointIndex >= waypoints.size()) {return;}
        double[] wp = waypoints.get(waypointIndex);

        for (Bot other : allBots) {
            if (other == this || !other.isActive()) continue;
            double dx = other.getX() - wp[0];
            double dy = other.getY() - wp[1];
            double dist = Math.sqrt(dx * dx + dy * dy);
            if (dist < 0.03) return; //wait its ocuupied not working rn 
        }

        double dx = wp[0] - x;
        double dy = wp[1] - y;
        double dist = Math.sqrt(dx * dx + dy * dy);

        if (dist < 0.015) { // basically epsilon cus it may not reach the exact point 
        } else {
            x += (dx / dist) * speed;
            y += (dy / dist) * speed;
        }
  }

    @Override
    public void respawn() {
        x = spawnX;  
        y = spawnY;  
        hp = getMaxHp();
        mana = maxMana;
        active = true;
        state = State.MOVING;
        waypointIndex = 0;
    }
}