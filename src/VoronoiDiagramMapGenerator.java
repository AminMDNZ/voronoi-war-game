import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class VoronoiDiagramMapGenerator extends JPanel {

    private int countPlayer;
    private int playerCountry;

    public int getPlayerCountry() {
        return playerCountry;
    }

    private int mapWidth;
    private int mapHeight;
    private int numPartitions;

//    private boolean[] selectedColor;
    private int partitionSelected;
    private Player player1;
    private List<Partition> partitions;
    ArrayList<int[]> colorArrayList = new ArrayList<>();

    // Add color pairs


    public VoronoiDiagramMapGenerator(int mapWidth, int mapHeight, int numPartitions , int countPlayer) {
        this.mapWidth = mapWidth;
        this.mapHeight = mapHeight;
        this.countPlayer = countPlayer;
        this.numPartitions = numPartitions;
        generateVoronoiDiagram();
        for(int i = 0; i < Colors.colorArrayList.size(); i++){
            Colors.colorArrayList.get(i)[3] = 0;
        }





        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int partitionIndex = getPartitionIndex(e.getX(), e.getY());
                Country country = Country.getCountryWithpartitionId(partitionIndex);
                System.out.println("before: " + player1.getSelectedCountryForAttack().size());
                player1.clickOnCountry(country);
                System.out.println("id" + country.getId());
                System.out.println(player1.getSelectedCountryForAttack().size());

            }
        });


    }

    private void generateVoronoiDiagram() {

        partitions = new ArrayList<>();
        Random random = new Random();

        int minDistanceFromBorder = 50; // Minimum distance from the sides and borders
        colorArrayList = Colors.colorArrayList;
        Color partitionColor = new Color(255 , 0 , 255);
        for (int i = 0; i < numPartitions; i++) {
            for(int j = 0; j < Colors.colorArrayList.size(); j++){
                if(Colors.colorArrayList.get(i)[3] == 0){
                    if(j == User.color){
                        playerCountry = i;
                    }
                    System.out.println("hello");
                    new Color(colorArrayList.get(j)[0], colorArrayList.get(j)[1], colorArrayList.get(j)[2]);
                    Colors.colorArrayList.get(i)[3] = 1;
                    break;
                }
            }

            int indexColor = i + this.countPlayer;

            int x = random.nextInt(mapWidth - minDistanceFromBorder * 2) + minDistanceFromBorder;
            int y = random.nextInt(mapHeight - minDistanceFromBorder * 2) + minDistanceFromBorder;
            partitions.add(new Partition(x, y));
//            Color partitionColor = new Color(colorArrayList.get(indexColor)[0], colorArrayList.get(indexColor)[1], colorArrayList.get(indexColor)[2]);
            //Color darkerColor = new Color(Math.max(0, color[0] - darkerOffset), Math.max(0, color[1] - darkerOffset), Math.max(0, color[2] - darkerOffset));
            changePartitionColor(i , partitionColor);
        }
    }

    public void changePartitionColor(int partitionIndex, Color color) {
        partitions.get(partitionIndex).setColor(color);
        repaint();
    }

    public int getPartitionIndex(int x, int y) {
        Partition nearestPartition = getNearestPartition(x, y);
        return partitions.indexOf(nearestPartition);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        for (int x = 0; x < mapWidth; x++) {
            for (int y = 0; y < mapHeight; y++) {
                Partition nearestPartition = getNearestPartition(x, y);
                g2d.setColor(nearestPartition.getColor());
                g2d.fillRect(x, y, 1, 1);
            }
        }
    }

    private Partition getNearestPartition(int x, int y) {
        Partition nearestPartition = null;
        double minDistance = Double.MAX_VALUE;

        for (Partition partition : partitions) {
            double distance = Point2D.distance(x, y, partition.getX(), partition.getY());
            if (distance < minDistance) {
                minDistance = distance;
                nearestPartition = partition;
            }
        }

        return nearestPartition;
    }

    public static void main(String[] args) {
        int countPlayer = 2;
        int countryNumber = 9;
        for(int i = 0; i < countryNumber; i++){
            new NormalCountry();
        }
        int indexPlayerOne = 3;
        Color player1Color = new Color(Colors.colorArrayList.get(0)[0] , Colors.colorArrayList.get(0)[1], Colors.colorArrayList.get(0)[2]);
//        Player player1 = new Player(0 , indexPlayerOne);

//        Color partitionColor = new Color(colorArrayList.get(i)[0], colorArrayList.get(i)[1], colorArrayList.get(i)[2]);
        for(int i = 1; i < countPlayer; i++){
            Color playerColor = new Color(Colors.colorArrayList.get(0)[0] , Colors.colorArrayList.get(0)[1], Colors.colorArrayList.get(0)[2]);
//            new Player(i , indexPlayerOne);

        }

//        Country.countryList.get(3).setCathcerId(player1.getId());
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Voronoi Map Generator");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(800, 600);

            VoronoiDiagramMapGenerator mapGenerator = new VoronoiDiagramMapGenerator(800, 600, countryNumber , countPlayer);
            for(int i = 0; i < Player.players.size(); i++){
                mapGenerator.changePartitionColor(Player.players.get(i).getCountries().get(0).getPartitionId() , Player.players.get(i).getColor());
            }
//            mapGenerator.player1 = player1;
            mapGenerator.changePartitionColor(0, Color.RED); // Example of changing the color of partition 0

            frame.getContentPane().add(mapGenerator);
            frame.setVisible(true);
        });
    }
}

class Partition {
    private int x;
    private int y;
    private Color color;

    public Partition(int x, int y) {
        this.x = x;
        this.y = y;
        this.color = getRandomColor();
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

    public void setColor(Color color) {
        this.color = color;
    }

    private Color getRandomColor() {
        Random random = new Random();
        return new Color(random.nextInt(256), random.nextInt(256), random.nextInt(256));
    }
}