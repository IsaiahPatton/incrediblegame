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

import com.zunozap.games.entities.Cow;
import com.zunozap.games.entities.Entity;
import com.zunozap.games.entities.Player;
import com.zunozap.games.entities.Zombie;
import com.zunozap.games.menu.CreditsMenu;
import com.zunozap.games.menu.MainMenu;
import com.zunozap.games.world.Block;
import com.zunozap.games.world.Blocks;
import com.zunozap.games.world.WorldLoadingScreen;

public class BlockGame extends JFrame {

    private static final long serialVersionUID = 1L;
    public static long renderTime;

    private static JPanel render;
    public static World world;   // The only world
    public static Player player; // The only player
    public FrameDisplayer inv;   // The creative inventory
    public FrameDisplayer wload; // The world load screen

    public static boolean isRendering;
    public static boolean isChanging;
    public boolean debugInfo;
    public int fps;
    private long fps2;
    private int fps3;

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
            ResourceManager.playSoundAsync("click.wav");
            this.setContentPane(new CreditsMenu()); 
            this.validate();
        });

        this.setTitle("BlockGame");
        this.setContentPane(menu);
        this.setDefaultCloseOperation(3);
        this.setSize(875, 600);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    public void backToMainMenu() {
        this.setContentPane(menu);
        this.validate();
    }

    public void startGame() {
        this.setVisible(false);
        Blocks.addDefaultBlocks();
        world = new World("world1");
        player = (Player) world.addEntity(new Player());
        inv = new FrameDisplayer(new CreativeInventoryFrame());
        wload = new FrameDisplayer(new WorldLoadingScreen());

        render = new JPanel() {
            private static final long serialVersionUID = 1L;

            @Override
            public void paint(Graphics g) {
                if (isRendering || isChanging)
                    return;

                isRendering = true;
                long start = System.currentTimeMillis();
                if (start-fps2 >= 1000) {
                    fps2=start;
                    fps=fps3;
                    fps3=0;
                }
                super.paint(g);
                world.paint(g);

                for (Entity e : world.getEntities())
                    e.paint(g);

                Block selectedBlock = player.getSelectedBlock();
                g.setFont(g.getFont().deriveFont(11f));
                g.drawString("Selected Block: ", getWidth() - 110, 15);
                g.drawImage(selectedBlock.getTexture().getScaledInstance(16, 16, 0), getWidth() - 30, 2, null);
                g.drawString(selectedBlock.toString(), getWidth() - 90, 27);

                String lookingAt = player.getLookingAt(g);
                if (null != lookingAt && !lookingAt.equals("AIR"))
                    g.drawString("Looking At: " + lookingAt, getWidth() - 110, 40);

                if (debugInfo) {
                    g.drawString("Render time: " + renderTime + "ms", getWidth() - 100, 54);
                    g.drawString("BlockData: " + world.locationToBlock.size(), getWidth() - 100, 67);
                    g.drawString("Pos/32: {" + player.x/32 + "," + player.y/32 + "}", getWidth() - 100, 80);
                    g.drawString(fps + " FPS", getWidth() - 100, 94);
                }

                renderTime = System.currentTimeMillis() - start;
                fps3++;
                isRendering = false;
            }
        };

        addKeyListener(new KeyListener() {

            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_W:
                    case KeyEvent.VK_A:
                        player.x += 16;
                        break;

                    case KeyEvent.VK_S:
                    case KeyEvent.VK_D:
                        player.x -= 16;
                        break;

                    case KeyEvent.VK_B:
                    case KeyEvent.VK_E:
                        if (inv.getKeyListeners().length < getKeyListeners().length)
                            for (KeyListener l : getKeyListeners())
                                inv.addKeyListener(l);
                        inv.setVisible(!inv.isVisible());

                        break;

                    case KeyEvent.VK_R:
                        player.respawn();
                        break;

                    case KeyEvent.VK_SPACE:
                        player.jump();
                        break;

                    case KeyEvent.VK_F2:
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
                        break;
                    case KeyEvent.VK_F3:
                        debugInfo = !debugInfo;
                        break;
                    case KeyEvent.VK_Z: {
                        world.addEntity(new Zombie());
                        world.addEntity(new Cow());
                        break;
                    }
                    case KeyEvent.VK_M:
                        if (wload.getKeyListeners().length < getKeyListeners().length)
                            for (KeyListener l : getKeyListeners())
                                wload.addKeyListener(l);
                        wload.setVisible(!wload.isVisible());
                        // Debug
                        //world.entities.remove(player);
                        //world.save();
                        //world = World.getWorld(world.NAME.endsWith("1") ? "world2" : "world1");
                        //world.addEntity(player);
                        break;
                }
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
                    world.setBlockAt(mx, my, player.getSelectedBlock().getId());

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