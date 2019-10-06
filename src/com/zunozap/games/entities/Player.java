package com.zunozap.games.entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

import com.zunozap.games.BlockData;
import com.zunozap.games.BlockGame;
import com.zunozap.games.ResourceManager;
import com.zunozap.games.World;
import com.zunozap.games.menu.PlayerInvMenu;
import com.zunozap.games.world.Block;
import com.zunozap.games.world.Blocks;

public class Player extends Entity {

    public PlayerInvMenu invOverlay;
    public Block selectedBlock;
    public int health;

    public Player() {
        this.selectedBlock = Blocks.getBlockById(1);
        this.invOverlay = new PlayerInvMenu(this);
        respawn();
    }

    @Override
    public String getName() {
        return "Player";
    }

    @Override
    public BufferedImage getTexture() {
        return ResourceManager.getTexture("entities/player.png").getSubimage(0, 0, 16, 32);
    }

    public String getLookingAt(Graphics g) {
        Point loc = MouseInfo.getPointerInfo().getLocation();
        Point osc = BlockGame.getGame().getLocationOnScreen();
        int mx = ((loc.x - osc.x) / 32);
        int my = ((loc.y - osc.y) / 32) - 1;
        if (mx < 0 || my < 0)
            return null;

        BlockData dat = world.locationToBlock.get(mx + "-" + my);
        if (dat == null)
            return null;

        int x = (dat.x*32);
        int y = (dat.y*32)+32;

        // Draw block outline
        g.setColor(Color.LIGHT_GRAY);
        g.drawLine(x, y, x+32, y);
        g.drawLine(x, y-32, x+32, y-32);
        g.drawLine(x, y, x, y-32);
        g.drawLine(x+32, y, x+32, y-32);
        g.setColor(Color.BLACK);

        return Blocks.getBlockById(dat.blockType).toString();
    }

    public void setSelectedBlock(Block b) {
        selectedBlock = b;
    }

    public Block getSelectedBlock() {
        return this.selectedBlock;
    }

    public void respawn() {
        health = 20;
        x = 32;
        y = 32 * 13;
    }
    
    public void damage(int amount, int speed) {
        if (this.health - amount <= 0) {
            respawn();
            return;
        }

        sleep(speed);
        this.health -= amount;
    }

    @Override
    public void paint(Graphics g) {
       super.paint(g);
       invOverlay.paint(g);
       if (this.getBlockUnder() == Blocks.byName.get("World Portal").getId()) {

           // Change World
           BlockGame game = BlockGame.getGame();
           game.wload.setVisible(!game.wload.isVisible());
           //for (int z = 0; z < 1000; z++)System.out.print(z);
           BlockGame.isChanging = true;
           //BlockGame.world.entities.remove(this);
           BlockGame.world.save();
           for (int z = 0; z < 10000; z++)System.out.print(z);
           BlockGame.world = World.getWorld(world.NAME.endsWith("1") ? "world2" : "world1");
           BlockGame.world.addEntity(this);
           BlockGame.isChanging = false;
       }
    }

    public int getHealth() {
        return health;
    }

}