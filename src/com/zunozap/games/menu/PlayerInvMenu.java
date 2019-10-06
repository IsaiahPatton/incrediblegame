package com.zunozap.games.menu;

import java.awt.Graphics;

import com.zunozap.games.IDrawable;
import com.zunozap.games.entities.Player;

public class PlayerInvMenu implements IDrawable {

    private Player p;

    public PlayerInvMenu(Player p) {
        this.p = p;
    }

    @Override
    public void paint(Graphics g) {
        g.setFont(g.getFont().deriveFont(11f));
        g.drawString(p.getHealth() + "HP", p.x + 3, p.y - 14);
    }

}