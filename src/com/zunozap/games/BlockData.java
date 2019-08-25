package com.zunozap.games;

import java.io.Serializable;

public class BlockData implements Serializable {

    private static final long serialVersionUID = 4235475311864256946L;
    public int x, y, blockType;

    public BlockData(int x, int y, int blockType) {
        this.x = x;
        this.y = y;
        this.blockType = blockType;
    }

}