package engine;

import java.util.ArrayList;
import java.util.List;
import data.*;

public class MinionSpawner {

    private static final double SPAWN_INTERVAL = 14.0;
    private static final int MINIONS_PER_WAVE = 3;

    private double timer = 0;
    private List<Minion> minions = new ArrayList<>();

    // waypoints for each lane, per team
    // team 0 goes bottom-left to top-right
    // team 1 goes top-right to bottom-left

    private static List<double[]> getWaypoints(int lane, int team) {
        List<double[]> wp = new ArrayList<>();
        if (team == 0) {
            switch (lane) {
                case 0: // top lane ally
                    wp.add(new double[]{0.02, 0.85});
                    wp.add(new double[]{0.02, 0.50});
                    wp.add(new double[]{0.02, 0.40});
                    wp.add(new double[]{0.02, 0.30});
                    wp.add(new double[]{0.02, 0.10});
                    break;
                case 1: // mid lane ally
                    wp.add(new double[]{0.10, 0.90});
                    wp.add(new double[]{0.30, 0.70});
                    wp.add(new double[]{0.50, 0.50});
                    wp.add(new double[]{0.70, 0.30});
                    wp.add(new double[]{0.90, 0.10});
                    break;
                case 2: // bot lane ally
                    wp.add(new double[]{0.10, 0.95});
                    wp.add(new double[]{0.30, 0.95});
                    wp.add(new double[]{0.60, 0.95});
                    wp.add(new double[]{0.70, 0.95});
                    wp.add(new double[]{0.95, 0.95});
                    break;
            }
        } else {
            switch (lane) {
                case 0: // top lane enemy
                    wp.add(new double[]{0.95, 0.10});
                    wp.add(new double[]{0.50, 0.02});
                    wp.add(new double[]{0.02, 0.10});
                    wp.add(new double[]{0.02, 0.50});
                    wp.add(new double[]{0.02, 0.90});
                    break;
                case 1: // mid lane enemy
                    wp.add(new double[]{0.90, 0.10});
                    wp.add(new double[]{0.70, 0.30});
                    wp.add(new double[]{0.50, 0.50});
                    wp.add(new double[]{0.30, 0.70});
                    wp.add(new double[]{0.10, 0.90});
                    break;
                case 2: // bot lane enemy
                    wp.add(new double[]{0.95, 0.10});
                    wp.add(new double[]{0.95, 0.50});
                    wp.add(new double[]{0.95, 0.85});
                    wp.add(new double[]{0.95, 0.95});
                    wp.add(new double[]{0.95, 0.95});
                    break;
            }
        }
        return wp;
    }

    public void update(double deltaTime, Player player) {
        timer += deltaTime;

        if (timer >= SPAWN_INTERVAL) {
            timer = 0;
            spawnWave();
        }   
        // remove dead minions
        minions.removeIf(m -> !m.isActive());
    }

    private void spawnWave() {
        for (int lane = 0; lane < 3; lane++) {
            for (int i = 0; i < MINIONS_PER_WAVE; i++) {
                double offset = i * 0.03;

                // ally spawn — bottom left
                List<double[]> wpAlly = getWaypoints(lane, 0);
                double[] startA = wpAlly.get(0);

                minions.add(new Minion(startA[0] + offset, startA[1],0,wpAlly));

                // enemy spawn — top right
                List<double[]> wpEnemy = getWaypoints(lane, 1);
                double[] startE = wpEnemy.get(0);

                minions.add(new Minion(startE[0],startE[1] + offset,1,wpEnemy));
            }
        }
    }

    /*private List<Minion> getByTeam(int team) {
        List<Minion> result = new ArrayList<>();
        for (Minion m : minions) {
            if (m.getTeam() == team) result.add(m);
        }
        return result;
    }*/

    public List<Minion> getMinions() { return minions; }
}