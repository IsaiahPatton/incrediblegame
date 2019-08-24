package com.zunozap.games;

import java.awt.Graphics;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.zunozap.games.entities.Entity;
import com.zunozap.games.entities.Player;
import com.zunozap.games.entities.Zombie;
import com.zunozap.games.menu.MainMenu;

public class BlockGame extends JFrame {

    private static final long serialVersionUID = 1L;
    public static int renderTime;

    public static World world;   // The only world
    public static Player player; // The only player

    public static boolean isRendering;

    private static BlockGame game;
    public static BlockGame getGame() {
        return game;
    }

    public BlockGame() {
        BlockGame.game = this;

        MainMenu menu = new MainMenu();
        menu.sp.addActionListener(l -> startGame());

        this.setContentPane(menu);
        this.setDefaultCloseOperation(3);
        this.setSize(864, 576);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    public void startGame() {
        this.setVisible(false);
        world = new World();
        player = (Player) world.addEntity(new Player());

        JPanel render = new JPanel() {
            private static final long serialVersionUID = 1L;

            @Override
            public void paint(Graphics g) {
                if (isRendering)
                    return;

                isRendering = true;
                long start = System.currentTimeMillis();
                super.paint(g);
                world.paint(g);

                for (Entity e : world.getEntities())
                    e.paint(g);

                g.drawString("Render time: " + renderTime + "ms", getWidth() - 120, 15);
                g.drawString("BlockData: " + world.locationToBlock.size(), getWidth() - 120, 30);
                g.drawString("Looking At: " + player.getLookingAt(), getWidth() - 120, 46);
                
                Blocks selectedBlock = Blocks.values()[player.getSelectedBlock()];
                g.drawString("Selected Block: ", getWidth() - 120, 65);
                g.drawImage(selectedBlock.getTexture().getScaledInstance(16, 16, 0), getWidth() - 32, 53, null);
                g.setFont(g.getFont().deriveFont(11f));
                g.drawString(selectedBlock.toString(), getWidth() - selectedBlock.toString().length() * 8, 84);

                long end = System.currentTimeMillis();
                renderTime = (int) (end - start);
                isRendering = false;
            }
        };

        addKeyListener(new KeyListener() {

            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_W) {
                    player.x += 16;
                    if (!player.isJumping)
                        player.move();
                }

                if (e.getKeyCode() == KeyEvent.VK_S) {
                    player.x -= 16;
                    if (!player.isJumping)
                        player.move();
                }

                if (e.getKeyCode() == KeyEvent.VK_B)
                    player.setSelectedBlock();

                if (e.getKeyCode() == KeyEvent.VK_SPACE)
                    player.jump();
                
                if (e.getKeyCode() == KeyEvent.VK_R)
                    player.respawn();

                if (e.getKeyCode() == KeyEvent.VK_Z)
                    world.addEntity(new Zombie());

                validate();
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });

        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Point loc = MouseInfo.getPointerInfo().getLocation();
                Point osc = BlockGame.getGame().getLocationOnScreen();
                int mx = ((loc.x - osc.x) / 32);
                int my = ((loc.y - osc.y) / 32) - 1;
                if (mx < 0 || my < 0)
                    return;

                System.out.println(e.getButton());
                if (e.getButton() == 3) {
                    world.setBlockAt(mx, my, player.getSelectedBlock());
                }
                if (e.getButton() == 1) {
                    world.setBlockAt(mx, my, 0);
                }
            }
        });

        this.setContentPane(render);
        this.setVisible(true);

        new Thread(() -> {
            while (true)
                 render.repaint();
        }, "GameRenderRepaint").start();
    }

    public static void main(String[] args) {
        new BlockGame();
    }

}