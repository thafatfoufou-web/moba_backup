package engine;

import data.Bot;
import data.Entity;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

public class BotManager {

    private List<Bot> bots     = new ArrayList<>();
    private List<Bot> enemyBots = new ArrayList<>();
         /*bots.add(new Bot(0.15, 0.87, 0.45, 0.6,0, "Bot1"));
        bots.add(new Bot(0.22, 0.97,  0.9, 0.95,0, "Bot2"));
        bots.add(new Bot(0.02, 0.85, 0.02, 0.20, 0, "Bot3"));
        bots.add(new Bot(0.05, 0.85, 0.06, 0.15,0, "Bot4"));

        enemyBots = new ArrayList<>();
        enemyBots.add(new Bot(0.85, 0.13,0.55, 0.4,1, "ENEMY_Bot1"));
        enemyBots.add(new Bot(0.78, 0.1,0.10, 0.10, 1, "ENEMY_Bot2"));
        enemyBots.add(new Bot(0.98, 0.15,0.98, 0.80,1, "ENEMY_Bot3"));
        enemyBots.add(new Bot(0.85, 0.20, 0.55, 0.5, 1, "ENEMY_Bot4"));
        enemyBots.add(new Bot(0.95, 0.15,0.94, 0.85, 1, "ENEMY_Bot5"));*/

    public BotManager() {
        bots.add(new Bot(0.15, 0.87, getBotWaypoints("Bot1"), 0, "Bot1"));
        bots.add(new Bot(0.22, 0.97, getBotWaypoints("Bot2"), 0, "Bot2"));
        bots.add(new Bot(0.02, 0.85, getBotWaypoints("Bot3"), 0, "Bot3"));
        bots.add(new Bot(0.05, 0.85, getBotWaypoints("Bot4"), 0, "Bot4"));

        enemyBots.add(new Bot(0.85, 0.13, getBotWaypoints("ENEMY_Bot1"), 1, "ENEMY_Bot1"));
        enemyBots.add(new Bot(0.78, 0.1, getBotWaypoints("ENEMY_Bot2"), 1, "ENEMY_Bot2"));
        enemyBots.add(new Bot(0.98, 0.15, getBotWaypoints("ENEMY_Bot3"), 1, "ENEMY_Bot3"));
        enemyBots.add(new Bot(0.85, 0.20, getBotWaypoints("ENEMY_Bot4"), 1, "ENEMY_Bot4"));
        enemyBots.add(new Bot(0.95, 0.15, getBotWaypoints("ENEMY_Bot5"), 1, "ENEMY_Bot5"));
    }

    public void update(double deltaTime, List<Entity> allEnemiesForTeam0, List<Entity> allEnemiesForTeam1) {
        List<Bot> allBots = getAllBots();
        for (Bot b : bots)      { b.update(deltaTime, allEnemiesForTeam0,allBots); }
        for (Bot b : enemyBots) { b.update(deltaTime, allEnemiesForTeam1,allBots); }
    }

    public void render(Graphics2D g2, int width, int height) {
        for (Bot b : bots)      { b.render(g2, width, height); }
        for (Bot b : enemyBots) { b.render(g2, width, height); }
    }

    public List<Bot> getAllBots() {
        List<Bot> all = new ArrayList<>(bots);
        all.addAll(enemyBots);
        return all;
    }

   private List<double[]> getBotWaypoints(String botName) {
    List<double[]> wp = new ArrayList<>();
    switch (botName) {
        case "Bot1":
            wp.add(new double[]{0.02, 0.70});
            wp.add(new double[]{0.02, 0.40});
            wp.add(new double[]{0.02, 0.10});
            wp.add(new double[]{0.30, 0.04});
            wp.add(new double[]{0.70, 0.04});
            wp.add(new double[]{0.95, 0.05});
            break;
        case "Bot2":
            wp.add(new double[]{0.20, 0.80});
            wp.add(new double[]{0.40, 0.60});
            wp.add(new double[]{0.60, 0.40});
            wp.add(new double[]{0.80, 0.20});
            wp.add(new double[]{0.95, 0.05});
            break;
        case "Bot3":
            wp.add(new double[]{0.30, 0.95});
            wp.add(new double[]{0.60, 0.95});
            wp.add(new double[]{0.90, 0.95});
            wp.add(new double[]{0.95, 0.70});
            wp.add(new double[]{0.95, 0.05});
            break;
        case "Bot4":
            wp.add(new double[]{0.04, 0.70});
            wp.add(new double[]{0.04, 0.40});
            wp.add(new double[]{0.04, 0.10});
            wp.add(new double[]{0.30, 0.06});
            wp.add(new double[]{0.70, 0.06});
            wp.add(new double[]{0.95, 0.07});
            break;
        case "ENEMY_Bot1":
            wp.add(new double[]{0.98, 0.30});
            wp.add(new double[]{0.98, 0.60});
            wp.add(new double[]{0.98, 0.90});
            wp.add(new double[]{0.70, 0.96});
            wp.add(new double[]{0.30, 0.96});
            wp.add(new double[]{0.05, 0.95});
            break;
        case "ENEMY_Bot2":
            wp.add(new double[]{0.80, 0.20});
            wp.add(new double[]{0.60, 0.40});
            wp.add(new double[]{0.40, 0.60});
            wp.add(new double[]{0.20, 0.80});
            wp.add(new double[]{0.05, 0.95});
            break;
        case "ENEMY_Bot3":
            wp.add(new double[]{0.95, 0.30});
            wp.add(new double[]{0.95, 0.60});
            wp.add(new double[]{0.95, 0.90});
            wp.add(new double[]{0.70, 0.95});
            wp.add(new double[]{0.05, 0.95});
            break;
        case "ENEMY_Bot4":
            wp.add(new double[]{0.82, 0.20});
            wp.add(new double[]{0.62, 0.40});
            wp.add(new double[]{0.42, 0.60});
            wp.add(new double[]{0.22, 0.80});
            wp.add(new double[]{0.05, 0.97});
            break;
        case "ENEMY_Bot5":
            wp.add(new double[]{0.96, 0.30});
            wp.add(new double[]{0.96, 0.60});
            wp.add(new double[]{0.96, 0.90});
            wp.add(new double[]{0.70, 0.98});
            wp.add(new double[]{0.30, 0.98});
            wp.add(new double[]{0.05, 0.97});
            break;
    }
    return wp;
}
}