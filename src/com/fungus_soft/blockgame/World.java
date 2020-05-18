package com.fungus_soft.blockgame;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TimerTask;

import javax.swing.Timer;

import com.fungus_soft.blockgame.entities.Entity;
import com.fungus_soft.blockgame.world.Block;
import com.fungus_soft.blockgame.world.Blocks;

public class World implements IDrawable {

    private static HashMap<String, World> openWorlds = new HashMap<>();
    public String NAME;

    public HashMap<String, BlockData> locationToBlock;
    public List<Entity> entities = new ArrayList<>();
    public File folder;
    public File blockFile;
    public File entityFile;

    public int width = 0, height = 0;

    public World(String name) {
        openWorlds.put(name, this);
        this.NAME = name;
        locationToBlock = new HashMap<>();

        File saves = new File(new File(System.getProperty("user.home"), "blockgame"), "saves");
        saves.mkdirs();
        this.folder = new File(saves, NAME);
        folder.mkdir();
        this.blockFile = new File(folder, "blocks.dat");
        this.entityFile = new File(folder, "entities.dat");
        if (blockFile.exists())
            load();

        Runtime.getRuntime().addShutdownHook(new Thread(() -> save()));
    }

    public static World getWorld(String name) {
        return openWorlds.getOrDefault(name, new World(name));
    }

    public void setBlockAt(int x, int y, int blockType) {
        Block b = Blocks.getBlockById(blockType);
        boolean gravity = b.hasGravity();

        if (gravity) {
            GravityAction ax = new GravityAction(x, y, blockType, gravity);
            Timer t = new Timer(40, ax);

            t.setInitialDelay(1);
            t.start();
        } else {
            BlockData d = new BlockData(x, y, blockType);
            locationToBlock.put(x + "-" + y, d);
            //b.tick(d);
            java.util.Timer t = new java.util.Timer();
            t.scheduleAtFixedRate(new TimerTask() {

                @Override
                public void run() {
                    b.tick(World.this, d, this);
                }

            }, 20, 20);
        }
    }

    public class GravityAction implements ActionListener {

        public int xA, yA, blockType;
        public boolean grav;

        public GravityAction(int x, int y, int blockType, boolean gravity) {
            this.xA = x;
            this.yA = y;
            this.blockType = blockType;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            Block b = Blocks.getBlockById(locationToBlock.getOrDefault(xA + "-" + (yA+1), new BlockData(0,0,1)).blockType);
            if ((b.isAir())) {
                grav = true;
                locationToBlock.put(xA + "-" + (yA-1), new BlockData(xA, yA-1, 0));
                locationToBlock.put(xA + "-" + yA, new BlockData(xA, yA, blockType));
                yA++;
            } else {
                if (grav)
                    locationToBlock.put(xA + "-" + (yA-1), new BlockData(xA, yA-1, 0));
                locationToBlock.put(xA + "-" + yA, new BlockData(xA, yA, blockType));
                ((Timer)e.getSource()).stop();
            }
        }
        
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
                int id = (h == height-1) ? Blocks.byName.get("Bedrock").getId() : 0;
                if (h == height-2) id = 3;
                if (h == height-3) id = 4;

                if (dat == null)
                    locationToBlock.put(new Location(w, h).toString(), new BlockData(w, h, id));
            }
        }

        for (BlockData i : locationToBlock.values())
            g.drawImage(Blocks.getBlockById(i.blockType).getTexture(), i.x * 32, i.y * 32, null);
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
    public void load() {
        System.out.println("Loading saved world ...");
        long start = System.currentTimeMillis();

        try {
            FileInputStream fis = new FileInputStream(blockFile);
            ObjectInputStream ois = new ObjectInputStream(fis);
            locationToBlock = (HashMap<String, BlockData>) ois.readObject();
            ois.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        System.out.println("Loaded world in " + (System.currentTimeMillis()-start) + "ms");
    }

    public void save() {
        System.out.println("Saving world ...");
        long start = System.currentTimeMillis();

        try {
            blockFile.createNewFile();
            FileOutputStream fos = new FileOutputStream(blockFile);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(locationToBlock);
            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Saved world in " + (System.currentTimeMillis()-start) + "ms");
    }

}