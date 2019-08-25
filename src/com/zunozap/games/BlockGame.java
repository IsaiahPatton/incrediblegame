package com.zunozap.games;

import java.awt.AWTException;
import java.awt.Graphics;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

import com.zunozap.games.entities.Entity;
import com.zunozap.games.entities.Player;
import com.zunozap.games.entities.Zombie;
import com.zunozap.games.menu.CreditsMenu;
import com.zunozap.games.menu.MainMenu;

public class BlockGame extends JFrame {

    private static final long serialVersionUID = 1L;
    public static long renderTime;

    public static World world;   // The only world
    public static Player player; // The only player

    public static boolean isRendering;
    public boolean debugInfo;

    private MainMenu menu;
    private static BlockGame game;
    public static BlockGame getGame() {
        return game;
    }

    public BlockGame() {
        BlockGame.game = this;

        menu = new MainMenu();
        menu.sp.addActionListener(l -> startGame());
        menu.cred.addActionListener(l -> {
            this.setContentPane(new CreditsMenu()); 
            this.validate();
        });

        this.setTitle("BlockGame");
        this.setContentPane(menu);
        this.setDefaultCloseOperation(3);
        this.setSize(864, 575);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
    
    public void backToMainMenu() {
        this.setContentPane(menu);
        this.validate();
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

                Blocks selectedBlock = Blocks.values()[player.getSelectedBlock()];
                g.setFont(g.getFont().deriveFont(11f));
                g.drawString("Selected Block: ", getWidth() - 110, 15);
                g.drawImage(selectedBlock.getTexture().getScaledInstance(16, 16, 0), getWidth() - 30, 2, null);
                g.drawString(selectedBlock.toString(), getWidth() - 90, 27);

                String lookingAt = player.getLookingAt();
                if (null != lookingAt && !lookingAt.equals("AIR"))
                    g.drawString("Looking At: " + lookingAt, getWidth() - 110, 40);

                if (debugInfo) {
                    g.drawString("Render time: " + renderTime + "ms", getWidth() - 100, 54);
                    g.drawString("BlockData: " + world.locationToBlock.size(), getWidth() - 100, 67);
                    g.drawString("Pos/32: {" + player.x/32 + "," + player.y/32 + "}", getWidth() - 100, 80);
                }

                renderTime = System.currentTimeMillis() - start;
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

                if (e.getKeyCode() == KeyEvent.VK_F2) {
                    try {
                        Robot r = new Robot();
                        Rectangle b = getBounds();
                        b.x += 9;
                        b.y += 31;
                        b.height -= 39;
                        b.width -= 20;
                        BufferedImage i = r.createScreenCapture(b);
                        File dir = new File(new File(System.getProperty("user.home")), "blockgame");
                        dir.mkdirs();
                        ImageIO.write(i, "PNG", new File(dir, System.currentTimeMillis() + ".png"));
                    } catch (AWTException | IOException e1) {
                        e1.printStackTrace();
                    }
                }

                if (e.getKeyCode() == KeyEvent.VK_F3)
                    debugInfo = !debugInfo;

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

                if (e.getButton() == 3)
                    world.setBlockAt(mx, my, player.getSelectedBlock());

                if (e.getButton() == 1)
                    world.setBlockAt(mx, my, 0);
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