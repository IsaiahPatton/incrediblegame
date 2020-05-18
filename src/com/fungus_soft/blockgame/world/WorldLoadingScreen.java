package com.fungus_soft.blockgame.world;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JLabel;
import com.fungus_soft.blockgame.FrameDisplayer;

public class WorldLoadingScreen extends FrameDisplayer {

    private static final long serialVersionUID = 1L;
    public boolean vis;
    public static ArrayList<JLabel> list;
    private String hover;
    private int pw,z;

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
        g.setColor(Color.WHITE);
        g.setFont(g.getFont().deriveFont(32f));
        g.drawString(hover = "Loading world...", (getWidth()/2)-(hover.length()*6)-10, 100);
        g.fillRect(20, 150, pw/2, 15);
        if (pw < 800)
            pw++;
        if (z > 1) z=0;
        z++;
        if (pw >= 800) {
            pw = 0;
            setVisible(false);
        }
        this.repaint();
    }

}