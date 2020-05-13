package com.fungus_soft.blockgame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.fungus_soft.blockgame.world.Block;
import com.fungus_soft.blockgame.world.Blocks;

public class CreativeInventoryFrame extends JPanel {

    private static final long serialVersionUID = 1L;
    public boolean vis;
    public static ArrayList<JLabel> list;
    private String hover;

    public CreativeInventoryFrame() {
        super();
        list = new ArrayList<>();
        int pos = 38;
        int ypos = 32;
        this.setLayout(null);
        for (Block b : Blocks.blocks.values()) {
            JLabel l = new JLabel();
            l.setIcon(new ImageIcon(b.getTexture()));
            l.setBounds(pos, ypos, 32, 32);
            MouseAdapter ma = new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    BlockGame.player.selectedBlock = b;
                    BlockGame.getGame().inv.setVisible(false);
                    super.mouseEntered(e);
                }

                @Override
                public void mouseEntered(MouseEvent e) {
                    hover = b.toString();
                    repaint();
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    hover = null;
                    repaint();
                }
            };
            l.setToolTipText(b.toString());
            l.addMouseListener(ma);
            l.addMouseMotionListener(ma);
            l.setOpaque(false);
            if (pos > (38*8)) {
                pos = 0;
                ypos += 38;
            }
            pos += 38;
            list.add(l);
            this.add(l);
        }
        this.setMinimumSize(new Dimension(200,200));
        this.setSize(300, 300);
        this.setOpaque(false);
    }

    @Override
    public void setVisible(boolean flag) {
        vis = flag;
        super.setVisible(flag);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.setColor(Color.WHITE);
        if (null != hover)
            g.drawString(hover, (getWidth()/2)-(hover.length()*4), 20);
    }

}