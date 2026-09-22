import java.awt.*;
import java.awt.Point;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;

public class VoronoiDiagram extends JPanel {
    private ArrayList<Point> sites;
    private Point ballPosition;

    public VoronoiDiagram() {
        sites = new ArrayList<>();
        ballPosition = new Point(0, 0);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
//                Point point = e.getPoint();
                sites.add(e.getPoint());
                repaint();
            }
        });

        addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                ballPosition = e.getPoint();
                repaint();
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;
        RenderingHints rh = new RenderingHints(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON
        );
        g2d.setRenderingHints(rh);

        // Draw Voronoi diagram
        for (int x = 0; x < getWidth(); x++) {
            for (int y = 0; y < getHeight(); y++) {
                int closestSiteIndex = getClosestSiteIndex(x, y);
                g2d.setColor(getColorForSite(closestSiteIndex));
                g2d.fillRect(x, y, 1, 1);
            }
        }

        // Draw ball
        g2d.setColor(Color.RED);
        g2d.fillOval(ballPosition.x - 10, ballPosition.y - 10, 20, 20);
    }

    private int getClosestSiteIndex(int x, int y) {
        int closestSiteIndex = -1;
        double minDistance = Double.MAX_VALUE;

        for (int i = 0; i < sites.size(); i++) {
            double distance = Math.sqrt(Math.pow(x - sites.get(i).x, 2) + Math.pow(y - sites.get(i).y, 2));
            if (distance < minDistance) {
                minDistance = distance;
                closestSiteIndex = i;
            }
        }

        return closestSiteIndex;
    }

    private Color getColorForSite(int siteIndex) {
        if (siteIndex == -1) {
            return Color.WHITE;
        } else {
            return new Color(sites.get(siteIndex).hashCode());
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Voronoi Diagram");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        VoronoiDiagram diagram = new VoronoiDiagram();
        frame.add(diagram);

        frame.setVisible(true);
    }
}