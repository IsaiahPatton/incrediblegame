package com.fungus_soft.blockgame.world.blocks;

import java.util.TimerTask;

import com.fungus_soft.blockgame.BlockData;
import com.fungus_soft.blockgame.World;
import com.fungus_soft.blockgame.world.Block;

public class BlockBomb extends Block {

    long last;
    public BlockBomb() {
        last = System.currentTimeMillis();
    }

    @Override
    public String getName() {
        return "Bomb";
    }

    @Override
    public void tick(World world, BlockData d, TimerTask timerTask) {
        long current = System.currentTimeMillis();
        if (current - last > 1000) {
            if (isPowered(world, d.x, d.y) > 0) {
                for (int i = 0; i < 1 + (int)(Math.random()*10)/2; i++) {
                    world.setBlockAt(d.x, d.y - i, 0);
                    world.setBlockAt(d.x, d.y + i, 0);
                    world.setBlockAt(d.x - i, d.y, 0);
                    world.setBlockAt(d.x + i, d.y, 0);
                    world.setBlockAt(d.x - i, d.y + i, 0);
                    world.setBlockAt(d.x + i, d.y - i, 0);
                    world.setBlockAt(d.x - i, d.y - i, 0);
                    world.setBlockAt(d.x + i, d.y + i, 0);
                }
                world.setBlockAt(d.x, d.y, 0);
                last = current;
            }
        }
    }

}