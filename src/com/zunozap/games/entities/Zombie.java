package com.zunozap.games.entities;

import java.awt.image.BufferedImage;

import com.zunozap.games.ResourceManager;

public class Zombie extends Entity {

    @Override
    public String getName() {
        return "Zombie";
    }

    @Override
    public BufferedImage getTexture() {
        return ResourceManager.getTexture("entities/zombie.png");
    }

}
