package com.fungus_soft.blockgame.world.blocks;

import java.util.TimerTask;

import com.fungus_soft.blockgame.BlockData;
import com.fungus_soft.blockgame.World;
import com.fungus_soft.blockgame.entities.Zombie;
import com.fungus_soft.blockgame.world.Block;

public class BlockZombie extends Block {

    public BlockZombie() {
        this.last = System.currentTimeMillis();
    }

    @Override
    public String getName() {
        return "Zombie Skull";
    }

    private long last;

    @Override
    public void tick(World world, BlockData d, TimerTask timerTask) {
        long current = System.currentTimeMillis();
        if (current - last > 6000) {
            Zombie zombie = new Zombie();
            zombie.x = (d.x - (int)(Math.random()*2))*32;
            zombie.y = (d.y - (int)(Math.random()*2))*32;
            world.addEntity(zombie);
            last = current;
        }
    }

}