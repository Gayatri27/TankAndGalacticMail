package application;

import static java.lang.Thread.sleep;

import javax.swing.*;

public class GameFrame extends JFrame implements Runnable {

    /**
     * 
     */
    private static final long serialVersionUID = 8815532421606947532L;
    private final int DURATION = 35; // testing
    private World panel;

    public GameFrame(World panel) {
        this.panel = panel;
        setTitle("Tank Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setResizable(false);

        add(panel);
        pack();
    }

    @Override
    public void run() {
        while (true) {
            panel.repaint();
            try {
                sleep(DURATION);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
