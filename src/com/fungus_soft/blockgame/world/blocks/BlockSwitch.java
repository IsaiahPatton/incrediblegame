package com.fungus_soft.blockgame.world.blocks;

import java.awt.Image;
import java.util.HashMap;

import com.fungus_soft.blockgame.BlockData;
import com.fungus_soft.blockgame.World;
import com.fungus_soft.blockgame.world.Block;

public class BlockSwitch extends BlockCopper {

    @Override
    public String getName() {
        return "Power Switch";
    }

    public HashMap<String, Boolean> map = new HashMap<>();

    @Override
    public void preSetBlock(World world, int x, int y) {
        if (world.getBlockAt(x, y) instanceof BlockSwitch) {
            boolean switched = isOn(world, x, y);
            map.put(world.NAME + "-" + x + "-" + y, !switched);
        }
    }

    public boolean isOn(World world, int x, int y) {
        return map.getOrDefault(world.NAME + "-" + x + "-" + y, false);
    }

    private Image texture_on;

    @Override
    public Image getTexture(World w, BlockData data) {
        if (w == null)
            return super.getTexture(w, data);
        return isOn(w, data.x, data.y) ? texture_on != null ? texture_on : (texture_on = image("power_switch_on.png")) : super.getTexture(w, data);
    }

}