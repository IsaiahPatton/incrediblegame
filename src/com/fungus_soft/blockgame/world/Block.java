package com.fungus_soft.blockgame.world;

import java.awt.Image;
import java.util.Locale;
import java.util.TimerTask;

import com.fungus_soft.blockgame.BlockData;
import com.fungus_soft.blockgame.ResourceManager;
import com.fungus_soft.blockgame.World;

public abstract class Block {

    protected int id;
    private Image texture;

    public abstract String getName();

    public String getResourceName() {
        return getName().toUpperCase(Locale.ENGLISH).replace(' ', '_');
    }

    public Image getTexture() {
        return null == texture ? texture = image(getResourceName().toLowerCase() + ".png") : texture;
    }

    public Image image(String i) {
        return ResourceManager.getTexture("blocks/" + i).getScaledInstance(32, 32, 0);
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return getName();
    }

    public boolean isLiquid() {
        return id == Blocks.byName.get("Water").getId() || id == Blocks.byName.get("Lava").getId();
    }

    public boolean hasGravity() {
        return id == Blocks.byName.get("Sand").getId();
    }

    public boolean isAir() {
        return id == Blocks.byName.get("Air").getId();
    }

    public void tick(World world, BlockData d, TimerTask timerTask) {}

}