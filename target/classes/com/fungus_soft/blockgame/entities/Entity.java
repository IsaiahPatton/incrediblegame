package com.fungus_soft.blockgame.entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.fungus_soft.blockgame.BlockData;
import com.fungus_soft.blockgame.BlockGame;
import com.fungus_soft.blockgame.IDrawable;
import com.fungus_soft.blockgame.Location;
import com.fungus_soft.blockgame.Threads;
import com.fungus_soft.blockgame.World;
import com.fungus_soft.blockgame.world.Blocks;

public abstract class Entity implements IDrawable {

    public int x=8, y=32;
    public BufferedImage texture;
    public boolean isJumping;
    public World world;

    public abstract String getName();

    public abstract BufferedImage getTexture();

    public void move() {
        if (getBlockUnder() == 0)
            y += 32;
    }

    public int getBlockUnder() {
        BlockData bd = BlockGame.world.locationToBlock.getOrDefault(new Location(x/32,((y/32)+1)).toString(), new BlockData(x/32,(y/32)+1, 0));
        return bd.blockType;
    }

    public void jump() {
        if (isJumping)
            return;

        isJumping = true;
        int to = y - 32;
        Threads.runAsync(() -> {
            while (y > to) {
                y -= 1;
                sleep(2);
            }
            sleep(350);
            move();
            sleep(10);
            isJumping = false;
        });
    }

    @Override
    public void paint(Graphics g) {
        if (null == texture)
            texture = getTexture();

        g.drawString(getName(), x - 6, y - 2);
        g.drawImage(texture, x, y, null);
        int h = BlockGame.getGame().getHeight()-15;
        int under;
        while (!isJumping && y < h-50 && ((under = getBlockUnder()) == 0 || Blocks.getBlockById(under).isLiquid()))
            y += 1;
    }

    public void sleep(int mill) {
        try {
            Thread.sleep(mill);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}