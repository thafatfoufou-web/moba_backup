package engine;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import data.Bot;
import data.Entite;
import data.Fountain;
import data.Lane;
import data.Minion;
import data.Player;
import data.Tower;
import view.GlobalAttr;

public class Arena {
    private List<Lane> lanes;
    private Player player;
    private List<Bot> bots;
    private List<Bot> enemyBots;
    private Fountain playerFountain;
    private Fountain Enemy_Fountain ;
    private Camera camera;
    private MinionSpawner minionSpawner;

    public Arena() {
        lanes = new ArrayList<>();
        lanes.add(new Lane(Lane.Type.top));
        lanes.add(new Lane(Lane.Type.middle));
        lanes.add(new Lane(Lane.Type.bot));

        player = new Player(0.1, 0.9);
        
        bots = new ArrayList<>();
        bots.add(new Bot(0.15, 0.87, 0.45, 0.6,0, "Bot1"));
        bots.add(new Bot(0.22, 0.97,  0.9, 0.95,0, "Bot2"));
        bots.add(new Bot(0.02, 0.85, 0.02, 0.20, 0, "Bot3"));
        bots.add(new Bot(0.05, 0.85, 0.06, 0.15,0, "Bot4"));

        enemyBots = new ArrayList<>();
        enemyBots.add(new Bot(0.85, 0.13,0.55, 0.4,1, "ENEMY_Bot1"));
        enemyBots.add(new Bot(0.78, 0.1,0.10, 0.10, 1, "ENEMY_Bot2"));
        enemyBots.add(new Bot(0.98, 0.15,0.98, 0.80,1, "ENEMY_Bot3"));
        enemyBots.add(new Bot(0.85, 0.20, 0.55, 0.5, 1, "ENEMY_Bot4"));
        enemyBots.add(new Bot(0.95, 0.15,0.94, 0.85, 1, "ENEMY_Bot5"));


        playerFountain = new Fountain(0.05, 0.95);
        Enemy_Fountain = new Fountain(0.95, 0.05);

        camera = new Camera(800, 600);
        int vW = (int)(GlobalAttr.WORLD_WIDTH  * GlobalAttr.CAMERA_ZOOM) + GlobalAttr.MAP_BORDER * 2;
        int vH = (int)(GlobalAttr.WORLD_HEIGHT * GlobalAttr.CAMERA_ZOOM) + GlobalAttr.MAP_BORDER * 2;
        camera.setWorldBounds(vW, vH);

        minionSpawner = new MinionSpawner();
    }

   public void update(double deltaTime) {

    //player
    player.update(deltaTime, this);
    if (player.isDead()) { player.respawn(); }

    //bots
    for (Bot bot : bots) {
         bot.update();
        List<Entite> targets = getEnemiesForTeam(bot.getTeam());
        Entite closest = null;
        double closestDist = Double.MAX_VALUE;
        for (Entite e : targets) {
        double d = bot.distanceTo(e);
            if (d < closestDist) { closestDist = d; closest = e; }
         }
        if (closest != null) bot.attack(closest);
    }

    for (Bot ebot : enemyBots) {
        ebot.update();
        List<Entite> targets = getEnemiesForTeam(ebot.getTeam());
        Entite closest = null;
        double closestDist = Double.MAX_VALUE;
        for (Entite e : targets) {
            double d = ebot.distanceTo(e);
            if (d < closestDist) { closestDist = d; closest = e; }
        }
        if (closest != null) ebot.attack(closest);
    }

    //fountain
    playerFountain.update(deltaTime, player);
    Enemy_Fountain.update(deltaTime, player);
    Enemy_Fountain.attack(player);

    //towers
    for (Lane lane : lanes) {
        for (Tower t : lane.getEnemyTowers()) { t.attack(player); }
    }

    //minions
    minionSpawner.update(deltaTime, player);
    
    for (Minion m : minionSpawner.getMinions()) {
        List<Entite> enemies = getEnemiesForTeam(m.getTeam());
        m.update(deltaTime, enemies, player);
    }

    // camera target = border offset + player's position within inner area
    int innerW = (int)(GlobalAttr.WORLD_WIDTH  * GlobalAttr.CAMERA_ZOOM);
    int innerH = (int)(GlobalAttr.WORLD_HEIGHT * GlobalAttr.CAMERA_ZOOM);
    int playerPx = (int)(player.getX() * innerW);
    int playerPy = (int)(player.getY() * innerH);
    double targetX = GlobalAttr.MAP_BORDER + playerPx;
    double targetY = GlobalAttr.MAP_BORDER + playerPy;

    camera.follow(targetX, targetY);
    camera.update(deltaTime);

}

public void render(Graphics2D g2, int width, int height, boolean withBorder) {
    g2.setColor(new Color(20, 80, 20));
    g2.fillRect(0, 0, width, height);

    int drawW, drawH;
    if (withBorder) {
        g2.translate(GlobalAttr.MAP_BORDER, GlobalAttr.MAP_BORDER);
        drawW = width  - GlobalAttr.MAP_BORDER * 2;
        drawH = height - GlobalAttr.MAP_BORDER * 2;
    } else {
        drawW = width;
        drawH = height;
    }

    int lane_width = drawW / 12;
    for (Lane lane : lanes) { lane.render(g2, drawW, drawH, lane_width); }

    g2.setColor(new Color(0, 100, 0));
    double[][] positionsArbres = {
        {0.16, 0.2}, {0.33, 0.25}, {0.5, 0.16}, {0.2, 0.5}, {0.5, 0.5}
    };
    int aW = drawW / 60;
    int aH = drawH / 30;
    for (double[] pos : positionsArbres) {
        g2.fillRect((int)(pos[0] * drawW), (int)(pos[1] * drawH), aW, aH);
    }

    playerFountain.render(g2, drawW, drawH, new Color(50, 200, 255));
    Enemy_Fountain.render(g2, drawW, drawH, new Color(202, 94, 90));

    player.render(g2, drawW, drawH);
    for (Bot bot : bots)      { bot.render(g2, drawW, drawH); }
    for (Bot bot : enemyBots) { bot.render(g2, drawW, drawH); }
    for (Minion m : minionSpawner.getMinions()) {m.render(g2, drawW, drawH); }

    if (withBorder) {
        g2.translate(-GlobalAttr.MAP_BORDER, -GlobalAttr.MAP_BORDER);
    }
}

    public Player getPlayer() {
        return player;
    }
    public Camera getCamera() { return camera; }


    public boolean isCollidingWithTower(double newX, double newY) {
        int size = GlobalAttr.WORLD_WIDTH / 40;
        int px = (int)(newX * GlobalAttr.WORLD_WIDTH);
        int py = (int)(newY * GlobalAttr.WORLD_HEIGHT);
        Rectangle playerBox = new Rectangle(px - size/2, py - size/2, size, size);//player HITBOX
        for (Lane lane : lanes) {
            for (Tower t : lane.getAllTowers()) {
            Rectangle towerBox = t.getHitbox(GlobalAttr.WORLD_WIDTH, GlobalAttr.WORLD_HEIGHT); //TOWER HITBOX
                if (playerBox.intersects(towerBox)) {
                return true;
            }
           }
        }
        return false;
    }

        
    public List<Entite> getEnemiesForTeam(int team) {
        List<Entite> enemies = new ArrayList<>();

          // minions
        for (Minion m : minionSpawner.getMinions()) {
            if (m.getTeam() != team && m.isActive()) {
                enemies.add(m);}
        }  
          // player
        if (team == 1 && player.isActive()) {
            enemies.add(player);
        }
        // bots
         for (Bot b : bots) { if (b.getTeam() != team && b.isActive()) enemies.add(b); }
         for (Bot b : enemyBots) { if (b.getTeam() != team && b.isActive()) enemies.add(b); }
         
         // towers 
         for (Lane lane : lanes) {
            for (Tower t : lane.getAllTowers()) {
                if (t.getTeam() != team) {
                enemies.add(t);
            }
        }
        //Base to add later
    }

    return enemies;
}



}
