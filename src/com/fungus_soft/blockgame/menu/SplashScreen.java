package com.fungus_soft.blockgame.menu;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JPanel;

import jaco.mp3.player.MP3Player;

public class SplashScreen extends JPanel {

    private static final long serialVersionUID = 1L;
    private MP3Player intro;
    private int i = 0, y = 0;

    public SplashScreen() {
        this.setBackground(new Color(213, 17, 27));
        this.intro = new MP3Player(getClass().getClassLoader().getResource("intro.mp3"));
        intro.play();
    }

    @Override
    public void paint(Graphics g) {
        ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        super.paint(g);
        float f = 58f;
        g.setColor(Color.WHITE);
        g.setFont(g.getFont().deriveFont(f));

        String txt = "Fungus Interactive";
        if (y > 0) g.drawString(txt, (getWidth()/2) - ((txt.length()-1)*((int)f / 4)) + (int)f/6, y);

        if (y < (getHeight()/2) - 32 && i > 200)
            y += 6;
        i++;

        txt = "Copyright \u00A9 2020 Fungus Software";
        f = 16f;
        g.setFont(g.getFont().deriveFont(f));
        if (i > 500) g.drawString(txt, (getWidth()/2) - txt.length()*((int)f / 4) - 2, (getHeight()/2) + 128);
        repaint();
    }

}