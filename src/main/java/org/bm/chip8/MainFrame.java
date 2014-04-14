package org.bm.chip8;

import java.awt.BorderLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

/**
 * @author Baptiste Morin
 */
public class MainFrame extends JFrame implements Constants, KeyListener {
    private Screen screen;
    private CPU cpu;



    public MainFrame() {
        super("BC-Chip8");

        screen = new Screen();

        cpu = new CPU(screen);
        cpu.initialize();

        this.addKeyListener(this);

        this.setResizable(false);

        this.setLayout(new BorderLayout());

        this.add(screen, BorderLayout.CENTER);

        this.pack();

        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        //screen.clear();

        Thread run = new Thread(new Runnable() {
            @Override
            public void run() {
                boolean start = loadGame("BLITZ.ch8");
                boolean continuer = true;
                int counter = 0;
                if (start) {
                    do {
                        continuer = listen();
                        for (counter = 0; counter < CPU_SPEED; counter++) {
                            int opCode = cpu.getOpCode();
                            cpu.interpretOpCode(opCode);
                        }

                        try {
                            SwingUtilities.invokeAndWait(new Runnable() {
                                @Override
                                public void run() {
                                    // System.out.println("refresh screen");
                                    screen.repaint();
                                    //screen.debugPixels();
                                }
                            });
                        } catch (InterruptedException | InvocationTargetException e) {
                            e.printStackTrace();
                        }

                        cpu.countdown();
                        try {
                            Thread.sleep(FPS_SLEEP_TIME);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                    } while (continuer);
                }

                pause();
            }
        });

        run.start();


    }

    private boolean listen() {
        boolean continuer = true;
        KeyEvent event;
        while ((event = cpu.poll()) != null) {
            if (event.getKeyLocation() == KeyEvent.KEY_LOCATION_STANDARD) {
                switch (event.getKeyCode()) {
                    case 81: // q
                        continuer = false;
                        break;
                    case 39: // ->
                        handleKey(event, 12);
                        break;
                }
            } else if (event.getKeyLocation() == KeyEvent.KEY_LOCATION_NUMPAD) {
                switch (event.getKeyCode()) {
                    case 103: // 7
                        handleKey(event, 0);
                        break;

                    case 104: // 8
                        handleKey(event, 1);
                        break;

                    case 105: // 9
                        handleKey(event, 2);
                        break;

                    case 106: // *
                        handleKey(event, 3);
                        break;

                    case 100: // 4
                        handleKey(event, 4);
                        break;

                    case 101: // 5
                        handleKey(event, 5);
                        break;

                    case 102: // 6
                        handleKey(event, 6);
                        break;

                    case 109: // -
                        handleKey(event, 7);
                        break;

                    case 97: // 1
                        handleKey(event, 8);
                        break;

                    case 98: // 2
                        handleKey(event, 9);
                        break;

                    case 99: // 3
                        handleKey(event, 10);
                        break;

                    case 107: // +
                        handleKey(event, 11);
                        break;

                    case 96: // 0
                        handleKey(event, 13);
                        break;

                    case 110: // .
                        handleKey(event, 14);
                        break;

                    case 10: // \n
                        handleKey(event, 15);
                        break;


                }
            }
        }

        return continuer;

    }

    private void handleKey(KeyEvent event, int button) {
        if(event.getID() == KeyEvent.KEY_PRESSED) {
            cpu.pressButton(button);
        } else {
            cpu.releaseButton(button);
        }
    }

    public void pause() {

        cpu.waitEvent();

    }




    private boolean loadGame(final String file) {
        boolean ok;
        try {
            ok = cpu.loadGame(new FileInputStream(new File(file)));
        } catch (IOException e) {
            e.printStackTrace();
            ok = false;
        }


        return ok;


    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MainFrame();
            }
        });
    }


    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        cpu.getEventQueue().add(e);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        cpu.getEventQueue().add(e);
    }
}
