package com.zunozap.games;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.zunozap.games.entities.Entity;

public class World implements IDrawable {

    public HashMap<String, BlockData> locationToBlock;
    public List<Entity> entities = new ArrayList<>();

    public int width = 0, height = 0;

    public World() {
        locationToBlock = new HashMap<>();
        BlockData[] dat = {
                new BlockData(0, 2, 1),
                new BlockData(1, 2, 1),
                new BlockData(2, 2, 2)};

        for (BlockData b : dat)
            locationToBlock.put(new Location(b.x, b.y).toString(), b);
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
                if (dat == null)
                    locationToBlock.put(new Location(w, h).toString(), new BlockData(w, h, 0));
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

}