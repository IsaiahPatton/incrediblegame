package com.fungus_soft.blockgame.entities;

import java.awt.image.BufferedImage;

import com.fungus_soft.blockgame.ResourceManager;

public class Cow extends Entity {

    @Override
    public String getName() {
        return "Cow";
    }

    @Override
    public BufferedImage getTexture() {
        return ResourceManager.getTexture("entities/cow.png");
    }

}