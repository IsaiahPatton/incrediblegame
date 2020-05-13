package com.fungus_soft.blockgame.entities;

import java.awt.image.BufferedImage;
import java.util.Random;

import com.fungus_soft.blockgame.BlockGame;
import com.fungus_soft.blockgame.ResourceManager;
import com.fungus_soft.blockgame.Threads;

public class Zombie extends Entity {

    public Random r = new Random();

    public Zombie() {
        attackPlayer(BlockGame.player);
    }

    public void attackPlayer(Player plr) {
        // TODO better AI
        //
        Threads.runAsync(() -> {
            while (!(this.x == plr.x && this.y == plr.y)) {
                int dx = this.x - plr.x;
                int dy = this.y - plr.y;
                Threads.runAsync(() -> {
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
                });

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
            while (plr.getHealth() > 0 && this.x == plr.x && this.y == plr.y) {
                    plr.damage(1, 800);
            }
        });
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
