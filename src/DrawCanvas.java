import javax.swing.*;
import java.awt.*;
import java.awt.geom.Path2D;
import java.nio.DoubleBuffer;
import java.time.Instant;
import java.time.YearMonth;
import java.util.ArrayList;

public class DrawCanvas extends JComponent {


    @Override
    protected void paintComponent(Graphics g){
       Graphics2D g2d = (Graphics2D) g;
       RenderingHints rh = new RenderingHints(
               RenderingHints.KEY_ANTIALIASING,
               RenderingHints.VALUE_ANTIALIAS_ON
       );
        g2d.setRenderingHints(rh);

        ArrayList<ArrayList<Double>> allXcordinates = new ArrayList<>();
        ArrayList<ArrayList<Double>> allYCordinates = new ArrayList<>();


       int countCountry = 5;
        Double yMin = Double.valueOf(110);
        Double yMax = Double.valueOf(140);
        Double xMin = Double.valueOf(110);
        Double xMax = Double.valueOf(150);
        int countUp = 5;
        int countDw = 5;
       Double xRightesPoint  = Double.valueOf(5);
       Double yRightestPoint =  Math.floor(Math.random() *(yMax - yMin + 1) + yMin);
        Double xFirstPoint = Double.valueOf(xMin - 5);
        Double yFirstPoint = Double.valueOf(yMax);
       for(int j = 0; j < countCountry; j++){
           Path2D.Double countryShape = new Path2D.Double();

           ArrayList<Double> xCordinates = new ArrayList<>();
           ArrayList<Double> yCordinates = new ArrayList<>();
           xCordinates.add(xFirstPoint);
           yCordinates.add(yFirstPoint);

           countryShape.moveTo(xCordinates.get(0) , yCordinates.get(0));

           if(j != 0){
               yMin = yFirstPoint - Double.valueOf(100);
               yMax = yFirstPoint;
               xMin = xRightesPoint;
//             xMin = xFirstPoint;
               xMax = xMin + Double.valueOf(20);
               xCordinates.add(xRightesPoint);
               yCordinates.add(yRightestPoint);

           }

           for(int i = 0; i < countUp; i++){
               Double newX = Math.floor(Math.random() *(xMax - xMin + 1) + xMin);
               Double newY = Math.floor(Math.random() *(yMax - yMin + 1) + yMin);
               xCordinates.add(newX);
               yCordinates.add(newY);
               xMin += 41;
               xMax += 41;
           }
           xCordinates.add(Double.valueOf(xMax + 1));
           xRightesPoint = xMax + 1;
           yRightestPoint = yMax;
           yCordinates.add(yMax);

           yMin += 101;
           yMax += 101;
           for(int i = 0; i < countDw; i++){

               Double newX = Math.floor(Math.random() *(xMax - xMin + 1) + xMin);
               Double newY = Math.floor(Math.random() *(yMax - yMin + 1) + yMin);
               if(i == 0){
                   xFirstPoint = newX;
                   yFirstPoint = newY;

               }
               xCordinates.add(newX);
               yCordinates.add(newY);
               xMin -= 41;
               xMax -= 41;
           }
           xCordinates.add(xCordinates.get(0));
           yCordinates.add(yCordinates.get(0));

           allXcordinates.add(xCordinates);
           allYCordinates.add(yCordinates);

           for(int i = 1; i < xCordinates.size(); i++){
               countryShape.lineTo(xCordinates.get(i) , yCordinates.get(i));
           }

           g2d.setColor(new Color((int) Math.floor(Math.random() *(255 - 0 + 1) + 0) , (int) Math.floor(Math.random() *(255 - 0 + 1) + 0) , (int)Math.floor(Math.random() *(255 - 0 + 1) + 0)));

           g2d.fill(countryShape);

       }

       for(int i = 0; i < countCountry; i++){
           Path2D.Double countryShape = new Path2D.Double();
           ArrayList<Double> xCordinates = new ArrayList<>();
           ArrayList<Double> yCordinates = new ArrayList<>();
           int index = allXcordinates.get(i).size() - 1;
           Double maxX = Double.valueOf(0);
           Double maxY = Double.valueOf(0);
           for(int j = 0; j < 4; j++){
             xCordinates.add(allXcordinates.get(i).get(index));
             yCordinates.add(allYCordinates.get(i).get(index));
             if(j == 0){
                 countryShape.moveTo(xCordinates.get(0) , yCordinates.get(0));
             }
             if(maxY < allYCordinates.get(i).get(index)) maxY = allYCordinates.get(i).get(index);
             if(maxX < allXcordinates.get(i).get(index)) maxX = allXcordinates.get(i).get(index);
             index--;
           }
           yMin = maxY;
           yMax = maxY + 100;

           xMax = maxX;
           xMin = xMax - 40;
           for(int j = 0; j < countDw; j++){
               Double newX = Math.floor(Math.random() *(xMax - xMin + 1) + xMin);
               Double newY = Math.floor(Math.random() *(yMax - yMin + 1) + yMin);
               xCordinates.add(newX);
               yCordinates.add(newY);
               xMin -= 31;
               xMax -= 31;
           }
           for(int j = 1; j < xCordinates.size(); j++){
               countryShape.lineTo(xCordinates.get(j) , yCordinates.get(j));
           }

           g2d.setColor(new Color((int) Math.floor(Math.random() *(255 - 0 + 1) + 0) , (int) Math.floor(Math.random() *(255 - 0 + 1) + 0) , (int)Math.floor(Math.random() *(255 - 0 + 1) + 0)));

           g2d.fill(countryShape);

       }






//       countryShape.moveTo((xCordinates.get(0) , yCordinates.get(0));







//        countryShape.moveTo(100 , 150);
//        countryShape.lineTo(150  , 200);
//        countryShape.lineTo(130  , 210);
//        countryShape.lineTo(110  , 280);
//        countryShape.closePath();
    }
}
