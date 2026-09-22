import javax.swing.*;

public class mainPannel {

    public static void main(String[] args) {
         JFrame frame  = new JFrame();
//        pannel.setLayout();
        int width = 1000;
        int hight = 1000;
        frame.setSize(width  , hight);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        DrawCanvas dc = new DrawCanvas();
//        dc.paintComponent();

        frame.setVisible(true);

        frame.add(dc);
    }

}
