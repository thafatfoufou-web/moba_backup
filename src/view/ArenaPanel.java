package view;

import engine.Arena;
import engine.Camera;

import javax.swing.JPanel;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ArenaPanel extends JPanel {

    private static final long serialVersionUID = 1L;
    private Arena arena;
    private int windowWidth;
    private int windowHeight;

    public ArenaPanel(Arena arena, int windowWidth, int windowHeight) {
        this.arena = arena;
        this.windowWidth = windowWidth;
        this.windowHeight = windowHeight;

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int mx = e.getX();
                int my = e.getY();

                int miniWidth = (int)(windowWidth * 0.15);
                int miniHeight = (int)(windowHeight * 0.15);
                int miniX = windowWidth - miniWidth - 15; 
                int miniY = windowHeight - miniHeight - 15; 

                // clic dans la Minimap
                if (mx >= miniX && mx <= miniX + miniWidth && my >= miniY && my <= miniY + miniHeight) {
                    double relativeX = (double)(mx - miniX) / miniWidth;
                    double relativeY = (double)(my - miniY) / miniHeight;
                    arena.getPlayer().moveTo(relativeX, relativeY);
                } 
                // clic sur l'arène
                else {
                    Camera cam = arena.getCamera();
                    int innerW = (int)(GlobalAttr.WORLD_WIDTH  * GlobalAttr.CAMERA_ZOOM);
                    int innerH = (int)(GlobalAttr.WORLD_HEIGHT * GlobalAttr.CAMERA_ZOOM);
                    double worldX = (mx + cam.getX() - GlobalAttr.MAP_BORDER) / innerW;
                    double worldY = (my + cam.getY() - GlobalAttr.MAP_BORDER) / innerH;
                    arena.getPlayer().moveTo(worldX, worldY);
                }
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        windowWidth = getWidth();
        windowHeight = getHeight();

        //save how the graphics currently transformed.
        AffineTransform original = g2.getTransform();

        // Fond
        g2.setColor(Color.DARK_GRAY);
        g2.fillRect(0, 0, windowWidth, windowHeight);


        //CaMERA
        Camera cam = arena.getCamera();
        cam.setCameraView(windowWidth, windowHeight);
        int vW = (int)(GlobalAttr.WORLD_WIDTH  * GlobalAttr.CAMERA_ZOOM) + GlobalAttr.MAP_BORDER * 2;
        int vH = (int)(GlobalAttr.WORLD_HEIGHT * GlobalAttr.CAMERA_ZOOM) + GlobalAttr.MAP_BORDER * 2;

        cam.setCameraView(windowWidth, windowHeight);
        g2.translate(-cam.getX(), -cam.getY());
        arena.render(g2, vW, vH, true);   // world with border
        g2.setTransform(original);
        
        int miniWidth = (int)(windowWidth * 0.15);
        int miniHeight = (int)(windowHeight * 0.15);
        int miniX = windowWidth - miniWidth -2; 
        int miniY = windowHeight - miniHeight - 2; 

        g2.setColor(Color.BLACK);
        g2.fillRect(miniX, miniY, miniWidth, miniHeight); 
        g2.setColor(Color.BLACK);
        g2.drawRect(miniX - 2, miniY - 2, miniWidth + 4, miniHeight + 4);

        g2.translate(miniX, miniY);
        arena.render(g2, miniWidth, miniHeight, false);  // no border
        g2.setTransform(original);
    }
}