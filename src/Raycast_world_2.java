import javax.swing.JFrame;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;
import java.awt.event.ActionEvent;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JMenu;




public class Raycast_world_2 extends JFrame implements ActionListener, KeyListener {
    public int px, py;
    int velx = 0, vely = 0;
    Graphics f;
    int mapX = 8, mapY = 8, mapS = 64;
    int map[] =
            {
                    1, 1, 1, 1, 1, 1, 1, 1,
                    1, 0, 1, 0, 0, 0, 0, 1,
                    1, 0, 0, 0, 0, 0, 0, 1,
                    1, 0, 1, 0, 0, 0, 0, 1,
                    1, 0, 0, 0, 0, 0, 0, 1,
                    1, 0, 1, 0, 0, 0, 0, 1,
                    1, 0, 0, 0, 0, 0, 0, 1,
                    1, 1, 1, 1, 1, 1, 1, 1,
            };

    public Raycast_world_2() {
        px = 100;
        py = 100;
        setSize(1024, 512);
        setVisible(true);
        setDefaultCloseOperation(3);
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        setTitle("Raycast_World-Try4_Version 3.0");
        JMenuBar menuBar = new JMenuBar();
        JMenu menuFile = new JMenu("File");
        JMenuItem menuItemExit = new JMenuItem("Exit");
        menuFile.add(menuItemExit);

        menuBar.add(menuFile);

// adds menu bar to the frame
        setJMenuBar(menuBar);
        getContentPane().setBackground(Color.gray);
    }

    public void actionPerformed(ActionEvent e) {

        repaint();


    }

    public void paint(java.awt.Graphics g) {
        super.paint(g);
        g.setColor(Color.yellow);

        g.fillRect(px, py, 6, 6);
        g.dispose();
        drawMap2D(g);
        repaint();
    }

    public void drawMap2D(java.awt.Graphics g) {
        int x, y, xo, yo;
        Color currentcolor;
        for (y = 0; y < mapY; y++) {
            for (x = 0; x < mapX; x++) {
                xo = x * mapS;
                yo = y * mapS;
                if (map[y * mapX + x] == 1) {
                    super.paint(g);
                    currentcolor = Color.gray;
                    g.setColor(currentcolor);
                    g.fillRect(x, y, xo, yo);
                    System.out.println("program tries to paint me");
                } else {
                    super.paint(g);
                    currentcolor = Color.white;
                    g.setColor(currentcolor);
                    g.fillRect(x, y, xo, yo);
                    System.out.println("program tries to paint me");
                }


            }

        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
