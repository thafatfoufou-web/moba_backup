package data;

import java.awt.*;
import java.util.List;

import view.GlobalAttr;

public class Minion extends Entite {

    private double speed;
    private int team; // 0 = ALLY, 1 = ENEMY

    private double Dmg   = GlobalAttr.MINION_DMG;
    private double attackRange    = GlobalAttr.MINION_RANGE;
    private double attackCooldown = GlobalAttr.MINION_ATTACK_COOLDOWN;
    private double attackTimer    = 0;

    //path xy
    private List<double[]> waypoints;
    private int waypointIndex = 0;

    public Minion(double x, double y, int team, List<double[]> waypoints) {
        super(x, y, GlobalAttr.MINION_MAX_HP);
        this.speed     = 0.0008;
        this.team      = team;
        this.waypoints = waypoints;
    }

    public void update(double deltaTime, List<Entite> enemies, Player player) {
        if (!active) return;
        
        attackTimer -= deltaTime;
        Entite target = findTarget(enemies, player);

        if (target != null && isInRange(target)) {
            // attack instead of moving
            if (attackTimer <= 0) {
                attack(target);
            }
        } else {
            // follow waypoints
            followWaypoints();
        }
    }

    private Entite findTarget(List<Entite> enemies, Player player) {
        Entite closest = null;
        double closestDist = Double.MAX_VALUE;
        for (Entite m : enemies) {
            if (!m.active) continue;
            double dist = distanceTo(m);
            if (dist < closestDist) {
                closestDist = dist;
                closest = m;
            }
        }
        //for player
        if (team == 1) {
            double dist = distanceTo(player);
            if (dist < closestDist) {
                closest = player;
            }
        }

        return closest;
    }

    private boolean isInRange(Entite target) {
        return( this.distanceTo(target) <= attackRange);
    }

    private double dist(double tx, double ty) {
        double dx = tx - x;
        double dy = ty - y;
        return Math.sqrt(dx*dx + dy*dy);
    }

    private void moveTowards(double tx, double ty) {
        double dx = tx - x;
        double dy = ty - y;
        double distance = Math.sqrt(dx*dx + dy*dy);
        if (distance > 0) {
            x += (dx / distance) * speed;
            y += (dy / distance) * speed;
        }
    }

    private void followWaypoints() {
        if (waypointIndex >= waypoints.size()) return;
        
        double[] wp = waypoints.get(waypointIndex);
        double dist = dist(wp[0], wp[1]);
        if (dist < 0.01) {
            waypointIndex++; // reached waypoint, go to next
        } else {
            moveTowards(wp[0], wp[1]);
        }
    }

    @Override
    public void render(Graphics2D g2, int width, int height) {
        if (!active) return;
        int px = (int)(x * width);
        int py = (int)(y * height);
        int size = width / 100;

        if(team == 0) {
            g2.setColor(new Color(0, 150, 255));
        } else {
            g2.setColor(new Color(255, 80, 80));
        }
        int[] xPoints = {px - size, px + size, px};
        int[] yPoints = {py + size, py + size, py - size};

        g2.fillPolygon(xPoints, yPoints, 3);
        g2.setColor(Color.BLACK);
        g2.drawPolygon(xPoints, yPoints, 3);

        // HP bar
        g2.setColor(Color.GRAY);
        g2.fillRect(px - size, py - size - 5, size * 2, 3);
        g2.setColor(Color.GREEN);
        g2.fillRect(px - size, py - size - 5, (int)((hp / maxHp) * size * 2), 3);
        g2.setColor(Color.BLACK);
        g2.drawRect(px - size, py - size - 5, size * 2, 3);
    }
    public void attack(Entite target) {
        if (attackTimer <= 0) {
            target.takeDamage(Dmg);
            attackTimer = attackCooldown;
        }
    }

    public int getTeam()    { return team; }
    public boolean isTooCloseTo(Minion other) { return dist(other.getX(), other.getY()) < 0.02; }
}