package com.fungus_soft.blockgame.world;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.ArrayList;

import javax.swing.JLabel;
import com.fungus_soft.blockgame.FrameDisplayer;

public class WorldLoadingScreen extends FrameDisplayer {

    private static final long serialVersionUID = 1L;
    public boolean vis;
    public static ArrayList<JLabel> list;
    private String hover;
    private int pw;

    public WorldLoadingScreen() {
        super();
        list = new ArrayList<>();
    }

    @Override
    public void setVisible(boolean flag) {
        vis = flag;
        setSize(500,400);

        super.setVisible(flag);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g.setColor(Color.WHITE);
        g.setFont(g.getFont().deriveFont(32f));
        g.drawString(hover = "Loading world...", (getWidth()/2)-(hover.length()*6)-10, 100);

        pw++;
        if (pw >= 150) {
            setVisible(false);
            pw = 0;
        }
        this.repaint();
    }

}