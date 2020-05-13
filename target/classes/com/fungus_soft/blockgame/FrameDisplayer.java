package com.fungus_soft.blockgame;

import java.awt.Color;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class FrameDisplayer extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel cp;

    public FrameDisplayer(JPanel cp) {
        if (cp instanceof FDPanel) {
            ((FDPanel)cp).fd = this;
        }
        setContentPane(this.cp = cp);
        setUndecorated(true);
        for (KeyListener l : BlockGame.getGame().getKeyListeners())
            addKeyListener(l);
        setBackground(new Color(0,0,0,220));
        setDefaultCloseOperation(2);
        setSize(430, 439);
        setLocationRelativeTo(null);
        setAlwaysOnTop(true);
    }
    
    @Override
    public void setVisible(boolean flag) {
        super.setVisible(flag);
        cp.setVisible(flag);
    }

}