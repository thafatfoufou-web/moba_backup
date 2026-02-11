package engine;

import java.awt.*;
import java.awt.geom.Path2D;
import java.util.ArrayList;
import java.util.List;

public class Lane {
    public enum Type { top, middle, bot }
    private Type type;
    private List<Tower> towers;
    
    public Lane(Type type) {
        this.type = type;
        this.towers = new ArrayList<>();
        initTowers();
    }

    private void initTowers() {
        switch (type) {
            case top:
                towers.add(new Tower(0.04, 0.75, Color.BLUE)); 
                towers.add(new Tower(0.04, 0.50, Color.BLUE)); 
                towers.add(new Tower(0.04, 0.25, Color.BLUE)); 
                towers.add(new Tower(0.25, 0.04, Color.RED)); 
                towers.add(new Tower(0.50, 0.04, Color.RED));  
                towers.add(new Tower(0.75, 0.04, Color.RED));  
                break;

            case middle:
                towers.add(new Tower(0.20, 0.80, Color.BLUE)); 
                towers.add(new Tower(0.30, 0.70, Color.BLUE)); 
                towers.add(new Tower(0.40, 0.60, Color.BLUE)); 
                towers.add(new Tower(0.60, 0.40, Color.RED));  
                towers.add(new Tower(0.70, 0.30, Color.RED));  
                towers.add(new Tower(0.80, 0.20, Color.RED));  
                break;

            case bot:
                towers.add(new Tower(0.25, 0.95, Color.BLUE)); 
                towers.add(new Tower(0.55, 0.95, Color.BLUE)); 
                towers.add(new Tower(0.85, 0.95, Color.BLUE)); 
                towers.add(new Tower(0.96, 0.25, Color.RED));  
                towers.add(new Tower(0.96, 0.55, Color.RED));  
                towers.add(new Tower(0.96, 0.85, Color.RED));  
                break;
        }
    }

    public void render(Graphics2D g2, int width, int height, int lane_width) {
         g2.setColor(new Color(200, 200, 200));  
        switch (this.type) {
            case top:
                g2.fillRect(0, 0, width, lane_width);
                g2.fillRect(0, 0, lane_width, height);
                break;
            case bot:
                g2.fillRect(0, height - lane_width, width, lane_width);
                g2.fillRect(width - lane_width, 0, lane_width, height);
                break;
            case middle:
                Path2D mid = new Path2D.Double();
                mid.moveTo(0, height);                         
                mid.lineTo(lane_width, height);                 
                mid.lineTo(width, lane_width);                  
                mid.lineTo(width, 0);                           
                mid.lineTo(width - lane_width, 0);             
                mid.lineTo(0, height - lane_width);             
                mid.closePath();
                g2.fill(mid);
                break;
        }

        for (Tower t : towers) {
            t.render(g2, width, height);
        }
    }
}