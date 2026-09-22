import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MovingBall extends JPanel {
    private static final int BALL_SIZE = 20;

    private int ballX;
    private int ballY;
    private int startX;
    private int startY;
    private int endX;
    private int endY;
    private double speed;

    public MovingBall(int startX, int startY, int endX, int endY, double speed) {
        this.startX = startX;
        this.startY = startY;
        this.endX = endX;
        this.endY = endY;
        this.speed = speed;

        ballX = startX;
        ballY = startY;

        double distance = Math.sqrt(Math.pow(endX - startX, 2) + Math.pow(endY - startY, 2));
        double totalTime = distance / speed;
        double deltaX = (endX - startX) / totalTime;
        double deltaY = (endY - startY) / totalTime;

        Timer timer = new Timer(10, new ActionListener() {
            private double elapsedTime = 0;

            @Override
            public void actionPerformed(ActionEvent e) {
                elapsedTime += 0.01;
                ballX = (int) (startX + (deltaX * elapsedTime));
                ballY = (int) (startY + (deltaY * elapsedTime));

                if (elapsedTime >= totalTime) {
                    ((Timer) e.getSource()).stop();
                }

                repaint();
            }
        });
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.RED);
        g.fillOval(ballX, ballY, BALL_SIZE, BALL_SIZE);
    }

    @Override
    public Dimension getPreferredSize() {
        int maxX = Math.max(startX, endX) + BALL_SIZE;
        int maxY = Math.max(startY, endY) + BALL_SIZE;
        return new Dimension(maxX, maxY);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                JFrame frame = new JFrame("Moving Ball");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


                MovingBall ball = new MovingBall(50, 50, 100, 100, 200);
                frame.setSize(800 , 900);

                frame.getContentPane().add(ball);
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });
    }
}