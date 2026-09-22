import javax.swing.*;
import java.awt.*;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main  extends JPanel {
    public static void main(String[] args) {

        int countryNumber = 9;
        for(int i = 0; i < countryNumber; i++){
            NormalCountry normalCountry = new NormalCountry();
        }
        int indexPlayerOne = 3;
//        Player player1 = new Player(1 , indexPlayerOne);

//        Country.countryList.get(3).setCathcerId(player1.getId());

//        MovingBall movingBall = new MovingBall(10 , 20  , 300 , 400 , 100);
        JFrame frame = new JFrame("Voronoi Diagram Map Generator");
        SwingUtilities.invokeLater(() -> {

            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);




            VoronoiDiagramMapGenerator mapPanel = new VoronoiDiagramMapGenerator(500 , 600 , countryNumber , 3);

            frame.getContentPane().add(mapPanel);
            frame.pack();
            frame.setSize(500 , 600);
//            frame.add(movingBall);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
//        frame.add(movingBall);
    }
}