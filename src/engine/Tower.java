package engine;

import java.awt.*;

public class Tower {
    private double positionX; 
    private double positionY; 
    private Color teamColor;
    private int hp = 100;
    private int maxHp = 100;


    private double radius=0.1;
    private double dmg=0.2;
    

    public Tower(double positionX, double positionY, Color teamColor) {
        this.positionX = positionX;
        this.positionY = positionY;
        this.teamColor = teamColor;
    }

    public void render(Graphics2D g2, int width, int height) {
        int x = (int) (positionX * width);
        int y = (int) (positionY * height);
        int size = width / 60;

        g2.setColor(teamColor);
        g2.fillRect(x - size/2, y - size/2, size, size);

        g2.setColor(Color.BLACK);
        g2.drawRect(x - size/2, y - size/2, size, size); 
        
        g2.setColor(Color.GREEN);

        int hpWidth = (int)((double)hp/maxHp * size);
        g2.fillRect(x - size/2, y - size - 5, hpWidth, 3);

        g2.setColor(Color.BLACK);
        g2.drawRect(x - size/2, y - size - 5, size, 3);
    }
        public void attack(Player player){  /*will change to hero later  */

        double dx = player.getX() - positionX;
        double dy = player.getY() - positionY;
        double dist = Math.sqrt(dx * dx + dy * dy);

        if(dist< radius){player.takeDamage(dmg);}
    }
    


    public boolean isAlly() {
    return (this.teamColor.equals(Color.BLUE));
    }
    public boolean isEnemy(){
        return(this.teamColor.equals(Color.RED));
    }
}