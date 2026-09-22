import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class VoronoiMap extends JPanel {
    private List<Point> points;

    public VoronoiMap() {
        points = generateRandomPoints(100000); // Adjust the number of points as needed
    }

    private List<Point> generateRandomPoints(int numPoints) {
        List<Point> randomPoints = new ArrayList<>();
        for (int i = 0; i < numPoints; i++) {
            int x = (int) (Math.random() * getWidth());
            int y = (int) (Math.random() * getHeight());
            randomPoints.add(new Point(x, y));
        }
        return randomPoints;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // Enable anti-aliasing for smoother rendering
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Draw each point as a colored circle
        for (Point point : points) {
            g2d.setColor(point.getColor());
            g2d.fillOval(point.getX(), point.getY(), 5, 5);
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Voronoi Map");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        VoronoiMap map = new VoronoiMap();
        frame.add(map);

        frame.setVisible(true);
    }
}