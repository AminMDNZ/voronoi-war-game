import java.awt.*;

public class Point {
    private int x;
    private int y;
    private Color color;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
        this.color = generateRandomColor();
    }

    private Color generateRandomColor() {
        float r = (float) Math.random();
        float g = (float) Math.random();
        float b = (float) Math.random();
        return new Color(r, g, b);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Color getColor() {
        return color;
    }
}