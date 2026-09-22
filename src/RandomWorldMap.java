import javax.swing.*;
import java.awt.*;

public class RandomWorldMap extends JFrame {

    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;
    private static final int CELL_SIZE = 10;

    private int[][] worldMap;

    public RandomWorldMap() {
        setTitle("Random World Map");
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        generateWorldMap();

        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawWorldMap(g);
            }
        };

        add(panel);
        setVisible(true);
    }

    private void generateWorldMap() {
        // Initialize the world map array with random values
        int rows = HEIGHT / CELL_SIZE;
        int cols = WIDTH / CELL_SIZE;
        worldMap = new int[rows][cols];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                // Generate random terrain or features based on your game logic
                worldMap[i][j] = Math.random() < 0.5 ? 0 : 1;
            }
        }
    }

    private void drawWorldMap(Graphics g) {
        int rows = worldMap.length;
        int cols = worldMap[0].length;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                int x = j * CELL_SIZE;
                int y = i * CELL_SIZE;

                if (worldMap[i][j] == 0) {
                    g.setColor(Color.GREEN);
                } else {
                    g.setColor(Color.BLUE);
                }

                g.fillRect(x, y, CELL_SIZE, CELL_SIZE);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(RandomWorldMap::new);
    }
}