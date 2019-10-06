package com.zunozap.games.menu;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JPanel;

import com.zunozap.games.ResourceManager;

public class MainMenu extends JPanel {

    private static final long serialVersionUID = 1L;

    public String title = "BlockGame";
    public BufferedImage bg;
    public BufferedImage air;
    public Color c;
    public int in = 0;
    public int move;

    public JButton sp;
    public JButton mp;
    public JButton st;
    public JButton cred;

    public MainMenu() {
        this.bg = ResourceManager.getTexture("menu.png");
        this.air = ResourceManager.getTexture("blocks/air.png");
        this.setLayout(null);
        this.setOpaque(false);
        this.c = new Color(air.getRGB(0, 0));

        sp = (JButton)add(new JButton("Singleplayer"));
        mp = (JButton)add(new JButton("Multiplayer"));
        st = (JButton)add(new JButton("Settings"));
        cred = (JButton)add(new JButton("Credits"));

        Color c = new Color(238,238,238,230);
        sp.setBackground(c);
        mp.setBackground(c);
        st.setBackground(c);
        cred.setBackground(c);

        // TODO
        mp.setEnabled(false);
        st.setEnabled(false);
    }

    Random r = new Random();
    @Override
    public void paint(Graphics g) {
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
        g.setFont(new Font("Dialog", 1, 48));
        g.drawString(title, (getWidth()/2) - (title.length()*14), 60);
        g.setFont(new Font("Dialog", 0, 12));

        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(5, getHeight()-20, 125, 28);
        g.setColor(Color.BLACK);
        g.drawString("\u00A9 2019 Isaiah Patton", 10, getHeight()-6);
        g.setColor(Color.BLACK);

        int btnWidth = 300;
        sp.setBounds((getWidth()/2) - (btnWidth/2), 150, btnWidth, 40);
        mp.setBounds((getWidth()/2) - (btnWidth/2), 200, btnWidth, 40);
        st.setBounds((getWidth()/2) - (btnWidth/2), 275, (btnWidth/2)-4, 40);
        cred.setBounds((getWidth()/2), 275, (btnWidth/2)-4, 40);
        if (move == 8) {
            in++;
            move =0;
        }
        this.repaint();
    }

}