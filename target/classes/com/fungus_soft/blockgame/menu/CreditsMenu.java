package com.fungus_soft.blockgame.menu;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import com.fungus_soft.blockgame.BlockGame;

public class CreditsMenu extends JPanel {

    private static final long serialVersionUID = 1L;

    public String title = "Credits";

    public JScrollPane sp;
    public JButton bac;

    public CreditsMenu() {
        this.setLayout(null);
        JTextArea txt = new JTextArea();
        sp = (JScrollPane)add(new JScrollPane(txt));
        bac = ((JButton)add(new JButton("Back")));
        bac.addActionListener(r -> BlockGame.getGame().backToMainMenu());

        InputStream in = getClass().getResourceAsStream("/credits.txt"); 
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        String text = "", line;
        try {
            while ((line = reader.readLine()) != null)
                text += line + "\n";
        } catch (IOException e) {
            e.printStackTrace();
        }
        txt.setText(text);
    }

    @Override
    public void paint(Graphics g) {
        ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        super.paint(g);
        g.setFont(new Font("Dialog", 0, 48));
        g.drawString(title, (getWidth()/2) - (title.length()*14), 60);

        sp.setBounds(30, 105, getWidth() - 60, 300);
        bac.setBounds((getWidth()/2) - 75, 450, 146, 40);
    }

}