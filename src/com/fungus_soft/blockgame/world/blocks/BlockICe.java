package com.fungus_soft.blockgame.world.blocks;

import java.util.TimerTask;

import com.fungus_soft.blockgame.BlockData;
import com.fungus_soft.blockgame.World;
import com.fungus_soft.blockgame.world.Block;
import com.fungus_soft.blockgame.world.Blocks;

public class BlockICe extends Block {

    @Override
    public String getName() {
        return "Ice";
    }

    @Override
    public void tick(World world, BlockData d, TimerTask timerTask) {
        if (this.isPowered(world, d.x, d.y) > 10)
            world.setBlockAt(d.x, d.y, Blocks.byName.get("Water").getId());
    }

}