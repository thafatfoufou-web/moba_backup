package view;

import engine.Arena;
import javax.swing.JPanel;
import java.awt.*;

public class GamePanel extends JPanel {
	private static final long serialVersionUID = 1L;
	private Arena arena;

    public GamePanel(Arena arena) {
        this.arena = arena;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        
        int w = getWidth();
        int h = getHeight();
        arena.render(g2, w, h);

        drawMiniMap(g2, w, h);
    }

    private void drawMiniMap(Graphics2D g2, int w, int h) {
        int miniW = (int) (w * 0.4);
        int miniH = (int) (h * 0.4);
        int miniX = w - miniW - 10;
        int miniY = h - miniH - 10;
        g2.setColor(new Color(0, 0, 0, 150)); 
        g2.fillRect(miniX, miniY, miniW, miniH);
        g2.setColor(Color.WHITE);
        g2.drawRect(miniX, miniY, miniW, miniH);

        g2.translate(miniX, miniY);
        
        arena.render(g2, miniW, miniH);

        g2.translate(-miniX, -miniY);
    }
}