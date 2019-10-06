package com.zunozap.games.entities;

import java.awt.image.BufferedImage;
import java.util.Random;

import com.zunozap.games.BlockGame;
import com.zunozap.games.Location;
import com.zunozap.games.ResourceManager;

public class Zombie extends Entity {

    public Random r = new Random();

    public Zombie() {
        attackPlayer(BlockGame.player);
    }

    public void attackPlayer(Player plr) {
        // TODO better AI
        //
        new Thread(() -> {
            while (!new Location(this.x, this.y).equals(new Location(plr.x, plr.y))) {
                int dx = this.x - plr.x;
                int dy = this.y - plr.y;
                new Thread(() -> {
                    if (dy != 0) {
                        if (dy > 0) {
                            while (this.y - plr.y > 0) {
                                sleep(r.nextInt(20) + 10);
                                this.y--;
                            }
                        }
                        if (dy < 0) {
                            while (this.y - plr.y < 0) {
                                sleep(r.nextInt(20) + 10);
                                this.y++;
                            }
                        }
                    }
                }).start();
                if (dx > 0) {
                    while (this.x - plr.x > 0) {
                        sleep(10);
                        this.x--;
                    }
                }
                if (dx < 0) {
                    while (this.x - plr.x < 0) {
                        sleep(10);
                        this.x++;
                    }
                }
            }
            while (plr.health > 0) {
                while (new Location(this.x, this.y).equals(new Location(plr.x, plr.y))) {
                    plr.damage(1, 800);
                }
            }
        }).start();
    }

    @Override
    public String getName() {
        return "Zombie";
    }

    @Override
    public BufferedImage getTexture() {
        return ResourceManager.getTexture("entities/zombie.png");
    }

}
