package com.zunozap.games.world;

import java.awt.Image;
import java.util.Locale;

import com.zunozap.games.ResourceManager;

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

}