package com.fungus_soft.blockgame.world;

import java.util.HashMap;

import com.fungus_soft.blockgame.world.blocks.*;

public class Blocks {

    // Can store up to Integer.MAX_VALUE blocks
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
                new BlockDarkrock(),
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
                new BlockSnow(),
                new BlockICe(),
                new BlockWireH(),
                new BlockWireV(),
                new BlockWireCorner(),
                new BlockLamp(),
                new BlockCopper(),
                new BlockGold(),
                new BlockBomb(),
                new BlockSolarPanel(),
                new BlockSwitch(),
                new BlockZombie(),
                new BlockBricksBlue(),
                new BlockBricksGold(),
                new BlockBricksGray(),
        };
        for (Block b : arr) addBlock(b);
    }

    public static Block getBlockById(int id) {
        return blocks.get(id);
    }

    public static void addBlock(Block b) {
        System.out.println("Registering block '" + b.getName() + "' with id '" + (b.id = blocks.size()) + "'");
        blocks.put(b.id, b);
        byName.put(b.getName(), b);
    }

}