package view;

import engine.Arena;
import javax.swing.JPanel;
import java.awt.*;

/**
 * Panel pour afficher l'arène et la mini-map.
 * Strictement style prof.
 */
public class ArenaPanel extends JPanel {

    private static final long serialVersionUID = 1L;

    private Arena arena;
    private int windowWidth;
    private int windowHeight;

    public ArenaPanel(Arena arena, int windowWidth, int windowHeight) {
        this.arena = arena;
        this.windowWidth = windowWidth;
        this.windowHeight = windowHeight;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        // 1. Fond de la fenêtre
        g2.setColor(Color.DARK_GRAY);
        g2.fillRect(0, 0, windowWidth, windowHeight);

        // 2. Arène principale (plein écran)
        arena.render(g2, windowWidth, windowHeight);

        // 3. Mini-map (40% de la fenêtre) en bas à droite
        int miniWidth = (int)(windowWidth * 0.4);
        int miniHeight = (int)(windowHeight * 0.4);
        int miniX = windowWidth - miniWidth - 20; // marge droite
        int miniY = windowHeight - miniHeight - 20; // bas

        // Cadre noir autour de la mini-map
        g2.setColor(Color.BLACK);
        g2.drawRect(miniX - 2, miniY - 2, miniWidth + 4, miniHeight + 4);

        // Décaler pour dessiner l'arène réduite
        g2.translate(miniX, miniY);
        arena.render(g2, miniWidth, miniHeight);
        g2.translate(-miniX, -miniY);
    }
}
