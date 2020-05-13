package com.fungus_soft.blockgame;

import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class ResourceManager {

    public static BufferedImage getTexture(String path) {
        try {
            return ImageIO.read(ResourceManager.class.getClassLoader().getResourceAsStream("textures/" + path));
        } catch (Exception e) {
            new ResourceNotFoundException(e).printStackTrace();
            return null;
        }
    }

    public static void playSound(String path) {
    }

    public static void playSoundAsync(String path) {
        //new Thread(() -> playSound(path)).start();
    }

}

class ResourceNotFoundException extends Exception {

    public ResourceNotFoundException(Exception e) {
        this.setStackTrace(e.getStackTrace());
    }

    private static final long serialVersionUID = 1L;

}