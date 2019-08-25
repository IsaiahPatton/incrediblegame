package com.zunozap.games;

import java.awt.Graphics;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.zunozap.games.entities.Entity;

public class World implements IDrawable {

    public HashMap<String, BlockData> locationToBlock;
    public List<Entity> entities = new ArrayList<>();
    public File file;

    public int width = 0, height = 0;

    public World() {
        locationToBlock = new HashMap<>();

        File saves = new File(new File(System.getProperty("user.home"), "blockgame"), "saves");
        saves.mkdirs();
        this.file = new File(saves, "world1.dat");
        if (file.exists())
            load();

        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                save();
            }
        });
    }

    public void setBlockAt(int x, int y, int blockType) {
        locationToBlock.put(x + "-" + y, new BlockData(x, y, blockType));
    }

    public void setBlockAt(Location l, int blockType) {
        setBlockAt(l.x, l.y, blockType);
    }

    @Override
    public void paint(Graphics g) {
        if (width == 0) width = BlockGame.getGame().getWidth() / 32;
        if (height == 0) height = BlockGame.getGame().getHeight() / 32;

        for (int h = 0; h < height; h++) {
            for (int w = 0; w < width; w++) {
                BlockData dat = locationToBlock.getOrDefault(w + "-" + h, null);
                int id = (h == height-1) ? Blocks.BEDROCK.getId() : 0;
                if (h == height-2) id = 3;
                if (h == height-3) id = 8;

                if (dat == null)
                    locationToBlock.put(new Location(w, h).toString(), new BlockData(w, h, id));
            }
        }

        for (BlockData i : locationToBlock.values())
            g.drawImage(Blocks.values()[i.blockType].getTexture(), i.x * 32, i.y * 32, null);

    }

    public List<Entity> getEntities() {
        return entities;
    }

    public Entity addEntity(Entity e) {
        this.entities.add(e);
        e.world = this;
        return e;
    }

    @SuppressWarnings("unchecked")
    public long load() {
        System.out.println("Loading saved map data for world ...");
        Date d = new Date();

        try {
            file.getParentFile().mkdir();
            FileInputStream fis = new FileInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(fis);
            locationToBlock = (HashMap<String, BlockData>) ois.readObject();
            ois.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return -999;
        }

        Date d2 = new Date();
        long ms = d2.getTime() - d.getTime();
        System.out.println("World Saved in " + ms + "ms");
        return ms;
    }

    public long save() {
        System.out.println("Saving world ...");
        Date d = new Date();

        try {
            file.createNewFile();
            FileOutputStream fos = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(locationToBlock);
            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
            return -999;
        }

        Date d2 = new Date();
        long ms = d2.getTime() - d.getTime();
        System.out.println("Saved world in " + ms + "ms");
        return ms;
    }

}