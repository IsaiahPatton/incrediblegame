package com.fungus_soft.blockgame.entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.image.BufferedImage;

import com.fungus_soft.blockgame.BlockData;
import com.fungus_soft.blockgame.BlockGame;
import com.fungus_soft.blockgame.ResourceManager;
import com.fungus_soft.blockgame.World;
import com.fungus_soft.blockgame.menu.PlayerInvMenu;
import com.fungus_soft.blockgame.world.Block;
import com.fungus_soft.blockgame.world.Blocks;

public class Player extends Entity {

    public PlayerInvMenu invOverlay;
    public Block selectedBlock;
    public int health;
    public boolean changingWorlds;

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

    public BlockData getLookingAt(Graphics g) {
        Point loc = MouseInfo.getPointerInfo().getLocation();
        Point osc = BlockGame.getGame().getLocationOnScreen();
        int mx = ((loc.x - osc.x) / 32);
        int my = ((loc.y - osc.y) / 32) - 1;
        if (mx < 0 || my < 0)
            return null;

        BlockData dat = world.locationToBlock.get(mx + "-" + my);
        if (dat == null)
            return null;

        int x = (mx*32);
        int y = (my*32);

        // Draw block outline
        g.setColor(Color.LIGHT_GRAY);
        g.drawRect(x, y, 32, 32);
        g.setColor(Color.BLACK);

        return dat;
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
       if (this.getBlockUnder() == 12) {
           if (changingWorlds)
               return;
           changingWorlds = true;
           // Change World
           BlockGame game = BlockGame.getGame();
           game.setGlassPane(game.wload);
           game.wload.setVisible(!game.wload.isVisible());
           BlockGame.isChanging = true;
           BlockGame.world.save();
           BlockGame.world = World.getWorld(world.NAME.endsWith("1") ? "world2" : "world1");
           BlockGame.world.addEntity(this);
           BlockGame.isChanging = false;
           changingWorlds = false;
       }
    }

    public int getHealth() {
        return health;
    }

}