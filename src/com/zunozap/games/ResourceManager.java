package com.zunozap.games;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.advanced.AdvancedPlayer;

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
        InputStream in = ResourceManager.class.getClassLoader().getResourceAsStream("sounds/" + path);
        System.out.println((in != null) + "=" + in.toString());
        AudioInputStream audioInputStream;
        try {
            //audioInputStream = AudioSystem.getAudioInputStream(in);
            //Clip clip = AudioSystem.getClip();
            //clip.open(audioInputStream);
            //clip.start();
            
            URL in2 = ResourceManager.class.getClassLoader().getResource("sounds/" + path);
            java.applet.AudioClip clip2 = java.applet.Applet.newAudioClip(in2);
            clip2.play();
        } catch (Exception e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

        try {
            AdvancedPlayer plr = new AdvancedPlayer(in);
            plr.play(0, 6);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void playSoundAsync(String path) {
        new Thread(() -> {
            playSound(path);
        }).start();
    }

}

class ResourceNotFoundException extends Exception {

    String msg;
    public ResourceNotFoundException(Exception e) {
        msg = e.getMessage();
        this.setStackTrace(e.getStackTrace());
    }

    private static final long serialVersionUID = 1L;

}