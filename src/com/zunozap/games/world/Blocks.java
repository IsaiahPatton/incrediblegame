package com.zunozap.games.world;

import java.util.HashMap;

import com.zunozap.games.world.blocks.*;

public class Blocks {

    // Can store upto Integer.MAX_VALUE blocks
    public static HashMap<Integer, Block> blocks;
    public static HashMap<String, Block> byName;

    public static void addDefaultBlocks() {
        blocks = new HashMap<>();
        byName = new HashMap<>();
        Block[] arr = {
                new BlockAir(),
                new BlockStone(),
                new BlockCobblestone(),
                new BlockDirt(),
                new BlockGrass(),
                new BlockOakPlanks(),
                new BlockOakSapling(),
                new BlockBedrock(),
                new BlockFullGrass(),
                new BlockBricks(),
                new BlockOakLog(),
                new BlockBirchLog(),
                new BlockWorldPortal(),
                new BlockWater(),
                new BlockLava(),
                new BlockSlime(),
                new BlockLeaves(),
                new BlockBookshelf(),
                new BlockSand(),
        };
        for (Block b : arr)
            addBlock(b);
    }

    public static Block getBlockById(int id) {
        return blocks.get(id);
    }

    public static void addBlock(Block b) {
        System.out.println("Registering block '" + b.getName() + "' with id '" + blocks.size() + "'");
        b.id = blocks.size();
        blocks.put(blocks.size(), b);
        byName.put(b.getName(), b);
    }

}