package com.zunozap.games;

import java.awt.Image;

public enum Blocks {

    AIR,
    STONE,
    GRASS,
    DIRT,
    COBBLESTONE,
    OAK_PLANKS,
    OAK_SAPLING,
    BEDROCK,
    GRASS_BLOCK,
    BRICK_WALL,
    BRICKS,
    OAK_LOG,
    BIRCH_LOG,
    ;

    private Image texture;

    public int getId() {
        return this.ordinal();
    }

    public Image getTexture() {
        return null == texture ? texture = image(name().toLowerCase() + ".png") : texture;
    }

    public Image image(String i) {
        return ResourceManager.getTexture("blocks/" + i).getScaledInstance(32, 32, 0);
    }

}