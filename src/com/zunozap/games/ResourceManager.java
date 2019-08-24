package com.zunozap.games;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ResourceManager {

    public static BufferedImage getTexture(String path) {
        try {
            return ImageIO.read(ResourceManager.class.getClassLoader().getResourceAsStream("textures/" + path));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}