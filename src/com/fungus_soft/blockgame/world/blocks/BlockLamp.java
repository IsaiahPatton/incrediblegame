package com.fungus_soft.blockgame.world.blocks;

import java.awt.Image;

import com.fungus_soft.blockgame.BlockData;
import com.fungus_soft.blockgame.World;
import com.fungus_soft.blockgame.world.Block;

public class BlockLamp extends Block {

    @Override
    public String getName() {
        return "Lamp";
    }

    private Image texture_on;

    @Override
    public Image getTexture(World w, BlockData data) {
        if (w == null)
            return super.getTexture(w, data);
        return isPowered(w, data.x, data.y) > 0 ? texture_on != null ? texture_on : (texture_on = image("lamp_on.png")) : super.getTexture(w, data);
    }

}