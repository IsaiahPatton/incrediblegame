package com.fungus_soft.blockgame;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;

public class FrameDisplayer extends JPanel {

    private static final long serialVersionUID = 1L;
    private int w,h;
    private Color bg;

    public FrameDisplayer() {
        setBackground(new Color(0,0,0,220));
        setSize(430, 439);
    }

    @Override
    public void setVisible(boolean flag) {
        super.setVisible(flag);
    }

    @Override
    public void setSize(int w, int h) {
        super.setSize(w, h);
        this.w = w;
        this.h = h;
    }

    @Override
    public void setBackground(Color bg) {
        this.setOpaque(false);
        this.bg = bg;
    }

    @Override
    public void paint(Graphics g) {
        BlockGame game = BlockGame.getGame();

        this.setLocation((game.getWidth() - w)/2, (game.getHeight() - h)/2);
        super.setSize(w, h);
        Color c = g.getColor();
        g.setColor(bg);
        g.fillRect(0, 0, w, h);
        g.setColor(c);

        super.paint(g);
    }

}