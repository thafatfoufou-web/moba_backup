package engine;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Arena {
    private List<Lane> lanes;
    private Player player;
    private Fountain playerFountain;

    public Arena() {
        lanes = new ArrayList<>();
        lanes.add(new Lane(Lane.Type.top));
        lanes.add(new Lane(Lane.Type.middle));
        lanes.add(new Lane(Lane.Type.bot));
        player = new Player(0.1, 0.9);
        playerFountain = new Fountain(0.05, 0.95);
    }

    public void update(double deltaTime) {
        player.update( deltaTime );
        playerFountain.update(deltaTime, player);
    }

    public void render(Graphics2D g2, int width, int height) {
        // fond
        g2.setColor(new Color(20, 80, 20));
        g2.fillRect(0, 0, width, height);

        // lanes
        int lane_width = width / 12; 
        for (Lane lane : lanes) {
            lane.render(g2, width, height, lane_width);
        }

        g2.setColor(new Color(0, 100, 0));
        double[][] positionsArbres = {
            {0.16, 0.2}, {0.33, 0.25}, {0.5, 0.16}, {0.2, 0.5}, {0.5, 0.5}
        };
        
        int aW = width / 60; 
        int aH = height / 30;

        for (double[] pos : positionsArbres) {
            g2.fillRect((int)(pos[0] * width), (int)(pos[1] * height), aW, aH);
        }
        playerFountain.render(g2, width, height);
        player.render(g2, width, height);
    }

    public Player getPlayer() {
        return player;
    }
}
