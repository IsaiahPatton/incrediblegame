package com.zunozap.games.menu;

import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JButton;
import javax.swing.JPanel;

public class MainMenu extends JPanel {

    private static final long serialVersionUID = 1L;

    public String title = "BlockGame";

    public JButton sp;
    public JButton mp;
    public JButton st;
    public JButton cred;

    public MainMenu() {
        this.setLayout(null);
        sp = (JButton)add(new JButton("Singleplayer"));
        mp = (JButton)add(new JButton("Multiplayer"));
        st = (JButton)add(new JButton("Settings"));
        cred = (JButton)add(new JButton("Credits"));

        // TODO
        mp.setEnabled(false);
        st.setEnabled(false);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.setFont(new Font("Dialog", 1, 48));
        g.drawString(title, (getWidth()/2) - (title.length()*14), 60);
        g.setFont(new Font("Dialog", 0, 12));
        g.drawString("(C) 2019-2020 Isaiah Patton", 10, getHeight()-10);

        int btnWidth = 300;
        sp.setBounds((getWidth()/2) - (btnWidth/2), 150, btnWidth, 40);
        mp.setBounds((getWidth()/2) - (btnWidth/2), 200, btnWidth, 40);
        st.setBounds((getWidth()/2) - (btnWidth/2), 275, (btnWidth/2)-4, 40);
        cred.setBounds((getWidth()/2), 275, (btnWidth/2)-4, 40);
    }

}