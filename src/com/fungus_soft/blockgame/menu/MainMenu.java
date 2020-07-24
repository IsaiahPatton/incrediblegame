package com.fungus_soft.blockgame.menu;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JPanel;

import com.fungus_soft.blockgame.BlockGame;
import com.fungus_soft.blockgame.ResourceManager;

public class MainMenu extends JPanel {

    private static final long serialVersionUID = 1L;

    public BufferedImage bg;
    public BufferedImage air;
    public static BufferedImage title, titleS;
    public Color c;
    public int in = 0;
    public int move;

    public JButton sp;
    public JButton mp;
    public JButton st;
    public JButton cr;

    public int fps;
    private long fps2;
    private int fps3;

    public MainMenu() {
        this.bg = ResourceManager.getTexture("menu.png");
        this.air = ResourceManager.getTexture("blocks/air.png");
        MainMenu.title = ResourceManager.getTexture("title.png");
        MainMenu.titleS = ResourceManager.getTexture("title-small.png");
        this.setLayout(null);
        this.setOpaque(false);
        c = new Color(207,238,245);

        sp = (JButton)add(new JButton("Singleplayer"));
        mp = (JButton)add(new JButton("Multiplayer"));
        st = (JButton)add(new JButton("Settings"));
        cr = (JButton)add(new JButton("Credits"));
        JButton[] btns = {sp,mp,st,cr};
        for (JButton b : btns) b.setFocusable(false);

        Color bc = new Color(204,204,204,230);
        for (JButton b : btns) b.setBackground(bc);

        // TODO
        mp.setEnabled(false);
        st.setEnabled(false);
    }

    Random r = new Random();
    @Override
    public void paint(Graphics g) {
        if (BlockGame.inGame) {
            setEnabled(false);
            return;
        }
        ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        long start = System.currentTimeMillis();
        if (start-fps2 >= 1000) {
            fps2=start;
            fps=fps3;
            fps3=0;
        }

        move++;
        if (in+getWidth() >= bg.getWidth())
            in = 0;

        // Background
        BufferedImage i = bg.getSubimage(in, 0, getWidth(), bg.getHeight());
        g.drawImage(i, 0, getHeight()-bg.getHeight(), null);
        g.setColor(c);
        g.fillRect(0, 0, getWidth(), getHeight()-bg.getHeight());
        g.setColor(Color.BLACK);

        super.paint(g);
        g.drawImage(title, getWidth()/2 - title.getWidth()/2, 60, null);
        g.setFont(new Font("Dialog", 0, 12));

        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(5, getHeight()-22, 112, 30);
        g.setColor(Color.BLACK);
        g.drawString("Copyright \u00A9 2020", 12, getHeight()-6);

        int btnWidth = 300;
        sp.setBounds((getWidth()/2) - (btnWidth/2), 150, btnWidth, 40);
        mp.setBounds((getWidth()/2) - (btnWidth/2), 202, btnWidth, 40);
        st.setBounds((getWidth()/2) - (btnWidth/2), 254, btnWidth, 40);
        cr.setBounds((getWidth()/2) - (btnWidth/2), 306, btnWidth, 40);
        

        if (move >= getMoveRate()) {
            in += 1;
            move =0;
        }

        fps3++;
        this.repaint();
    }

    private int getMoveRate() {
        return fps/60; // Move 40/sec
    }

}