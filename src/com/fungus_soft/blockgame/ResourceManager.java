package com.fungus_soft.blockgame;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.ImageIO;

import jaco.mp3.player.MP3Player;

public class ResourceManager {

    public static BufferedImage getTexture(String path) {
        try {
            return ImageIO.read(ResourceManager.class.getClassLoader().getResourceAsStream("textures/" + path));
        } catch (Exception e) {
            new ResourceNotFoundException(e).printStackTrace();

            try {
                return ImageIO.read(ResourceManager.class.getClassLoader().getResourceAsStream("textures/blocks/missing.png"));
            } catch (IOException e1) {
                e1.printStackTrace();
                return null;
            }
        }
    }

    private static HashMap<String, MP3Player> mp3cache = new HashMap<>();

    public static void playSoundAsync(String path) {
        if (mp3cache.containsKey(path)) {
            mp3cache.get(path).play();
            return;
        }
        Threads.runAsync(() -> {
            MP3Player p = new MP3Player(ResourceManager.class.getClassLoader().getResource(path));
            mp3cache.put(path, p);
            p.play();
        });
    }

}

class ResourceNotFoundException extends Exception {

    public ResourceNotFoundException(Exception e) {
        this.setStackTrace(e.getStackTrace());
    }

    private static final long serialVersionUID = 1L;

}