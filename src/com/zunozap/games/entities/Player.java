package com.zunozap.games.entities;

import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.image.BufferedImage;

import com.zunozap.games.BlockData;
import com.zunozap.games.BlockGame;
import com.zunozap.games.Blocks;
import com.zunozap.games.ResourceManager;

public class Player extends Entity {

    public int selectedBlock = 1;

    public Player() {
        respawn();
    }

    @Override
    public String getName() {
        return "Player";
    }

    @Override
    public BufferedImage getTexture() {
        return ResourceManager.getTexture("entities/player.png");
    }

    public String getLookingAt() {
        Point loc = MouseInfo.getPointerInfo().getLocation();
        Point osc = BlockGame.getGame().getLocationOnScreen();
        int mx = ((loc.x - osc.x) / 32);
        int my = ((loc.y - osc.y) / 32) - 1;
        if (mx < 0 || my < 0)
            return null;

        BlockData dat = world.locationToBlock.get(mx + "-" + my);
        return null == dat ? null : Blocks.values()[dat.blockType].toString();
    }

    public void setSelectedBlock() {
        if ((Blocks.values().length - 1) == this.getSelectedBlock())
            this.selectedBlock = 1;
        else this.selectedBlock++;
    }

    public int getSelectedBlock() {
        return this.selectedBlock;
    }

    public void respawn() {
        x = y = 32;
    }

}