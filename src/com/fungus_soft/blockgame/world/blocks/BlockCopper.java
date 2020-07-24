package com.fungus_soft.blockgame.world.blocks;

import com.fungus_soft.blockgame.BlockData;
import com.fungus_soft.blockgame.World;
import com.fungus_soft.blockgame.world.Block;
import com.fungus_soft.blockgame.world.Blocks;

public class BlockCopper extends Block {

    @Override
    public String getName() {
        return "Copper Block";
    }

    @Override
    public int getPower(World w, int x, int y, BlockData calling) {
        try {
        int power = -1;

        BlockData[] around = this.getAroundData(w, x, y);
        for (BlockData b : around) {
            Block bl = Blocks.getBlockById(b.blockType);
            if (bl instanceof BlockAir) continue;

            if (bl instanceof BlockSolarPanel) {
                power = bl.getPower(w, -1, -1, w.locationToBlock.get(x + "-" + y));
                if (power > 0)
                    return power;
            }
            if (bl instanceof BlockCopper) {
                if (b.x == calling.x && b.y == calling.y) continue;

                power = bl.getPower(w, b.x, b.y, w.locationToBlock.get(x + "-" + y));
                if (power > 0)
                    return power;
            }
        }
        } catch (StackOverflowError e) {}

        return 0;
    }

}
