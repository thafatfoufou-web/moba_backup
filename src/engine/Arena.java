package engine;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author RAHARIMANANA Tianantenaina 
 */

public class Arena {
    private List<Lane> lanes;
    private Player player;
    private List<Bot> bots;
    private List<Bot> enemyBots;
    private Fountain playerFountain;
    private Fountain enemyFountain;

    public Arena() {
        lanes = new ArrayList<>();
        lanes.add(new Lane(Lane.Type.top));
        lanes.add(new Lane(Lane.Type.middle));
        lanes.add(new Lane(Lane.Type.bot));

        player = new Player(0.1, 0.9);
        
        bots = createBots(
            new double[][]{{0.15, 0.87, 0.45}, {0.22, 0.97, 0.9}, 
                          {0.02, 0.85, 0.02}, {0.05, 0.85, 0.06}},
            new Color(0, 150, 255),
            new String[]{"Bot1", "Bot2", "Bot3", "Bot4"}
        );

        enemyBots = createBots(
            new double[][]{{0.85, 0.13, 0.55}, {0.78, 0.1, 0.10}, 
                          {0.98, 0.15, 0.98}, {0.85, 0.20, 0.55}, {0.95, 0.15, 0.94}},
            new Color(255, 0, 150),
            new String[]{"ENEMY_Bot1", "ENEMY_Bot2", "ENEMY_Bot3", "ENEMY_Bot4", "ENEMY_Bot5"}
        );

        playerFountain = new Fountain(0.05, 0.95, new Color(50, 200, 255));
        enemyFountain = new Fountain(0.95, 0.05, new Color(202, 94, 90));
    }

    private List<Bot> createBots(double[][] positions, Color color, String[] names) {
        List<Bot> botList = new ArrayList<>();
        double[] targets = {0.6, 0.95, 0.20, 0.10, 0.80, 0.50, 0.85};
        for (int i = 0; i < names.length; i++) {
            botList.add(new Bot(positions[i][0], positions[i][1], positions[i][2], targets[i], color, names[i]));
        }
        return botList;
    }

    public void update(double deltaTime) {
        player.update(deltaTime);
        for (Bot bot : bots) {
            bot.update();
        }

        for (Bot bot : enemyBots) {
             bot.update();
             bot.attack(player);
        }
        playerFountain.update(deltaTime, player);
        enemyFountain.update(deltaTime, player);
        enemyFountain.attack(player);

        for (Lane lane : lanes) {
            for (Tower tower : lane.getEnemyTowers()) {
                tower.attack(player);
            }
        }

        if (player.isDead()) {
            player.respawn();
        }
    }

    public void render(Graphics2D g2, int width, int height) {
        g2.setColor(new Color(20, 80, 20));
        g2.fillRect(0, 0, width, height);

        int lane_width = width / 12;
        for (Lane lane : lanes) {
            lane.render(g2, width, height, lane_width);
        }

        renderTrees(g2, width, height);
        playerFountain.setColor(new Color(50, 200, 255));
        playerFountain.render(g2, width, height);
        enemyFountain.setColor(new Color(202, 94, 90));
        enemyFountain.render(g2, width, height);

        player.render(g2, width, height);
        for (Bot bot : bots) {
            bot.render(g2, width, height);
        }

        for (Bot bot : enemyBots) {
            bot.render(g2, width, height);
        }

    }

    private void renderTrees(Graphics2D g2, int width, int height) {
        g2.setColor(new Color(0, 100, 0));
        double[][] treePositions = {
            {0.16, 0.2}, {0.33, 0.25}, {0.5, 0.16}, {0.2, 0.5}, {0.5, 0.5}
        };
        
        int treeWidth = width / 60;
        int treeHeight = height / 30;

        for (double[] pos : treePositions) {
            g2.fillRect((int)(pos[0] * width), (int)(pos[1] * height), treeWidth, treeHeight);
        }
    }

    public Player getPlayer() {
        return player;
    }
}