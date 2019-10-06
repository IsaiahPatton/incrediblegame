package com.zunozap.games.world;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JLabel;
import com.zunozap.games.BlockGame;
import com.zunozap.games.FDPanel;

public class WorldLoadingScreen extends FDPanel {

    private static final long serialVersionUID = 1L;
    public boolean vis;
    public static ArrayList<JLabel> list;
    private String hover;
    private int pw,z;

    public WorldLoadingScreen() {
        super();
        list = new ArrayList<>();
 
        this.setLayout(null);
 
        this.setMinimumSize(new Dimension(500,200));
        this.setSize(300, 300);
        this.setOpaque(false);
    }

    @Override
    public void setVisible(boolean flag) {
        vis = flag;
        BlockGame g = BlockGame.getGame();
        fd.setSize(g.getWidth()-35, g.getHeight()-100);
        fd.setLocationRelativeTo(null);
        fd.setLocation(g.getX() + 20, g.getY() + 40);
        super.setVisible(flag);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.setColor(Color.WHITE);
        g.setFont(g.getFont().deriveFont(32f));
        g.drawString(hover = "Loading world...", (getWidth()/2)-(hover.length()*6)-10, 100);
        g.fillRect(220, 150, pw*4, 15);
        if (pw < 100)
            pw++;
        if (z > 1) z=0;
        z++;
        if (pw >= 100) {
            pw = 0;
            fd.setVisible(false);
        }
        this.repaint();
    }

}