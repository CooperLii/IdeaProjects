
// imports
import java.awt.*;
import java.util.Random;
import javax.swing.*;

/**
 * @author MingFang Li (lim)
 * Course: CSCI 242 - Computer Science II
 * Date: 11/05/2018
 * Assignment: Mondrian Art
 *
 * Project/Class Description: This project randomly split regions and randomly color them
 *
 */
public class Mondrian extends JPanel {
    private static int wid = 800;
    private static int hei = 800;
    private Graphics g;
    private JFrame frame;
    private JPanel panel;

    /**
     * the main to create the frame
     * @param args
     */
    public static void main(String[] args) {
        JFrame frame = new JFrame("Mondrian Art");
        frame.setContentPane(new Mondrian());
        frame.setSize(800, 800);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    // paint components
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        paint(g);
    }

    // create random numbers and draw lines and call the split method
    public void paint(Graphics g) {
        //  this.g = g;
        int x = (int) (Math.random() * wid);
        int y = (int) (Math.random() * hei);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.black);
        g2d.drawLine(0, y, wid, y);
        g2d.drawLine(x, 0, x, hei);

        if (x > wid / 2 && y > hei / 2) {
            split(g);
        } else paintComponent(g);
    }

    // split the regions randomly and color them randomly
    public void split(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        Random r = new Random();
        int low = 120;
        int high = (int) (hei * 1.5);
        int x = r.nextInt(high - low);
        int y = r.nextInt(high - low);

        g2d.drawLine(0, y, wid, y);
        g2d.drawLine(x, 0, x, hei);

        if (x > hei / 2 && y > wid / 2) {
            g2d.drawLine(x, y, x, y);
            g2d.drawLine(x, 0, x, hei);
            g2d.drawLine(0, y, wid, y);
            split(g);
        } else if (x > wid / 2) {
            g2d.drawLine(x, y, x, y);
            g2d.drawLine(0, y, wid, y);
            double random = Math.random();
            if (random < 0.0833) {
                g2d.setColor(Color.red);
                g2d.fillRect(0, 0, x, y);
                g2d.setColor(Color.CYAN);
                g2d.fillRect(x, 0, wid, y);
                g2d.setColor(Color.yellow);
                g2d.fillRect(0, y, x, hei);
                g2d.setColor(Color.white);
                g2d.fillRect(x, y, wid, hei);
                //split(g,x,y);
            } else if (random < 0.1667) {
                g2d.setColor(Color.CYAN);
                g2d.fillRect(0, 0, x, y);
                g2d.setColor(Color.red);
                g2d.fillRect(x, 0, wid, y);
                g2d.setColor(Color.yellow);
                g2d.fillRect(0, y, x, hei);
                g2d.setColor(Color.white);
                g2d.fillRect(x, y, wid, hei);
                //split(g,x,y);
            } else if (random < 0.25) {
                g2d.setColor(Color.yellow);
                g2d.fillRect(0, 0, x, y);
                g2d.setColor(Color.red);
                g2d.fillRect(x, 0, wid, y);
                g2d.setColor(Color.CYAN);
                g2d.fillRect(0, y, x, hei);
                g2d.setColor(Color.white);
                g2d.fillRect(x, y, wid, hei);
                //split(g,x,y);
            } else {
                g2d.setColor(Color.WHITE);
                g2d.fillRect(0, 0, x, y);
                g2d.setColor(Color.red);
                g2d.fillRect(x, 0, wid, y);
                g2d.setColor(Color.CYAN);
                g2d.fillRect(0, y, x, hei);
                g2d.setColor(Color.yellow);
                g2d.fillRect(x, y, wid, hei);
            }
            split(g);
        } else if (y > hei / 2) {
            g2d.drawLine(x, y, x, y);
            g2d.drawLine(x, 0, x, hei);
            double random = Math.random();
            if (random < 0.0833) {
                g2d.setColor(Color.red);
                g2d.fillRect(0, 0, x, y);
                g2d.setColor(Color.CYAN);
                g2d.fillRect(x, 0, wid, y);
                g2d.setColor(Color.yellow);
                g2d.fillRect(0, y, x, hei);
                g2d.setColor(Color.white);
                g2d.fillRect(x, y, wid, hei);
                //split(g);
            } else if (random < 0.1667) {
                g2d.setColor(Color.CYAN);
                g2d.fillRect(0, 0, x, y);
                g2d.setColor(Color.red);
                g2d.fillRect(x, 0, wid, y);
                g2d.setColor(Color.yellow);
                g2d.fillRect(0, y, x, hei);
                g2d.setColor(Color.white);
                g2d.fillRect(x, y, wid, hei);
                //split(g);
            } else if (random < 0.25) {
                g2d.setColor(Color.yellow);
                g2d.fillRect(0, 0, x, y);
                g2d.setColor(Color.red);
                g2d.fillRect(x, 0, wid, y);
                g2d.setColor(Color.CYAN);
                g2d.fillRect(0, y, x, hei);
                g2d.setColor(Color.white);
                g2d.fillRect(x, y, wid, hei);
                // split(g);
            } else {
                g2d.setColor(Color.WHITE);
                g2d.fillRect(0, 0, x, y);
                g2d.setColor(Color.red);
                g2d.fillRect(x, 0, wid, y);
                g2d.setColor(Color.CYAN);
                g2d.fillRect(0, y, x, hei);
                g2d.setColor(Color.yellow);
                g2d.fillRect(x, y, wid, hei);
                split(g);
            }
        } else paintComponent(g);
    }
}
