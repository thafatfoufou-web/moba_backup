package engine;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Arena {
    private List<Lane> lanes;
    private Player player;

    public Arena() {
        lanes = new ArrayList<>();
        lanes.add(new Lane(Lane.Type.top));
        lanes.add(new Lane(Lane.Type.middle));
        lanes.add(new Lane(Lane.Type.bot));
        player = new Player(0.1, 0.9);
    }

    public void update(double deltaTime) {
        player.update(deltaTime);
    }

    public void render(Graphics2D g2, int width, int height) {
        // Fond
        g2.setColor(new Color(20, 80, 20));
        g2.fillRect(0, 0, width, height);

        int lane_width = width / 12;

        // Lanes
        for (Lane lane : lanes) {
            lane.render(g2, width, height, lane_width);
        }

        // Player
        player.render(g2, width, height);

        // Arbres (5 arbres simples)
        g2.setColor(new Color(0, 120, 0)); // vert
        int[][] arbres = {
            {width / 6, height / 5},
            {width / 3, height / 4},
            {width / 2, height / 6},
            {width / 5, height / 2},
            {width / 2, height / 2}
        };
        int arbreLargeur = 10;
        int arbreHauteur = 20;

        for (int i = 0; i < arbres.length; i++) {
            g2.fillRect(arbres[i][0], arbres[i][1], arbreLargeur, arbreHauteur);
        }
    }

    public Player getPlayer() {
        return player;
    }
}
