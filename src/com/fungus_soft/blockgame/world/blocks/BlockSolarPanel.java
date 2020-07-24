package com.fungus_soft.blockgame.world.blocks;

import com.fungus_soft.blockgame.BlockData;
import com.fungus_soft.blockgame.World;
import com.fungus_soft.blockgame.world.Block;

public class BlockSolarPanel extends Block {

    @Override
    public String getName() {
        return "Solar Panel";
    }

    @Override
    public int getPower(World world, int x, int y, BlockData calling) {
        return 30;
    }

}