package com.fungus_soft.blockgame.world.blocks;

import java.util.TimerTask;

import com.fungus_soft.blockgame.BlockData;
import com.fungus_soft.blockgame.World;
import com.fungus_soft.blockgame.world.Block;
import com.fungus_soft.blockgame.world.Blocks;

public class BlockWater extends Block {

    @Override
    public String getName() {
        return "Water";
    }

    @Override
    public void tick(World w, BlockData d, TimerTask t) {
        int x = d.x;
        int y = d.y;
        Block a = Blocks.getBlockById(w.locationToBlock.getOrDefault(x + "-" + y, new BlockData(0,0,1)).blockType);
        Block b = Blocks.getBlockById(w.locationToBlock.getOrDefault(x + "-" + (y+1), new BlockData(0,0,1)).blockType);
        boolean cancel = (a instanceof BlockAir);
        if (b instanceof BlockAir)
            w.setBlockAt(x, y+1, cancel ? 0 : Blocks.byName.get("Water").getId());
        if (cancel) {
            if (b instanceof BlockWater)
                w.setBlockAt(x, y+1, 0);
            t.cancel();
        }
    }

}