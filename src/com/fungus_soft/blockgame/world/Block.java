package com.fungus_soft.blockgame.world;

import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.Locale;
import java.util.TimerTask;

import com.fungus_soft.blockgame.BlockData;
import com.fungus_soft.blockgame.ResourceManager;
import com.fungus_soft.blockgame.World;
import com.fungus_soft.blockgame.world.blocks.BlockCopper;

public abstract class Block {

    protected int id;
    private Image texture;

    public abstract String getName();

    public String getResourceName() {
        return getName().toUpperCase(Locale.ENGLISH).replace(' ', '_');
    }

    public Image getTexture(World world, BlockData dat) {
        return null == texture ? texture = toCompatibleImage(image(getResourceName().toLowerCase() + ".png")) : texture;
    }

    private BufferedImage toCompatibleImage(BufferedImage image) {
        GraphicsConfiguration gfxConfig = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();

        // if image is already compatible and optimized for current system  settings, simply return it
        if (image.getColorModel().equals(gfxConfig.getColorModel()))
            return image;

        // image is not optimized, so create a new image that is
        BufferedImage newImage = gfxConfig.createCompatibleImage(image.getWidth(), image.getHeight(), image.getTransparency());

        // get the graphics context of the new image to draw the old image on
        Graphics2D g2d = newImage.createGraphics();

        // actually draw the image and dispose of context no longer needed
        g2d.drawImage(image, 0, 0, null);
        g2d.dispose();

        return newImage; 
    }

    public BufferedImage image(String i) {
        return ResourceManager.getTexture("blocks/" + i);
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

    public int getPower(World world, int x, int y, BlockData calling) {
        return 0;
    }

    public void preSetBlock(World world, int x, int y) {
    }

    public BlockData[] getAroundData(World world, int x, int y) {
        BlockData up    = world.locationToBlock.get(x + "-" + (y - 1));
        BlockData down  = world.locationToBlock.get(x + "-" + (y + 1));
        BlockData left  = world.locationToBlock.get((x - 1) + "-" + y);
        BlockData right = world.locationToBlock.get((x + 1) + "-" + y);
        return new BlockData[]{up, down, left, right};
    }

    public int isPowered(World world, int x, int y) {
        int WireH = Blocks.byName.get("Wire Hor").getId();
        int WireV = Blocks.byName.get("Wire Vert").getId();
        int WireC = Blocks.byName.get("Wire Corner").getId();

        Block blockUp    = world.getBlockAt(x, y - 1);
        Block blockDown  = world.getBlockAt(x, y + 1);
        Block blockLeft  = world.getBlockAt(x - 1, y);
        Block blockRight = world.getBlockAt(x + 1, y);

        int power = 0;

        if (blockUp.id == WireV || blockUp.id == WireC) {
            BlockCopper c = (BlockCopper)blockUp;
            int p = c.getPower(world, x, y+1, world.locationToBlock.get(x + "-" + y));
            if (power < p) power = p;
        }

        if (blockDown.id == WireV || blockDown.id == WireC) {
            BlockCopper c = (BlockCopper)blockDown;
            int p = c.getPower(world, x, y-1, world.locationToBlock.get(x + "-" + y));
            if (power < p) power = p;
        }

        if (blockLeft.id == WireH || blockLeft.id == WireC) {
            BlockCopper c = (BlockCopper)blockLeft;
            int p = c.getPower(world, x-1, y, world.locationToBlock.get(x + "-" + y));
            if (power < p) power = p;
        }

        if (blockRight.id == WireH || blockRight.id == WireC) {
            BlockCopper c = (BlockCopper)blockRight;
            int p = c.getPower(world, x+1, y, world.locationToBlock.get(x + "-" + y));
            if (power < p) power = p;
        }

        return power;
    }

    public void tick(World world, BlockData d, TimerTask timerTask) {}

}