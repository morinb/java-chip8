package org.bm.chip8;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

/**
 * @author Baptiste Morin
 */
public class MainFrame extends JFrame implements Constants {
    private Screen screen;
    private CPU cpu;


    public MainFrame() {
        super("BC-Chip8");

        cpu = new CPU();
        screen = new Screen();

        this.setResizable(false);

        this.setLayout(new BorderLayout());

        this.add(screen, BorderLayout.CENTER);

        this.pack();

        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        screen.clear();




    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                MainFrame mf = new MainFrame();
            }
        });
    }


}
