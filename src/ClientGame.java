import classes.RequestHandler;
import classes.RespondHandler;

import java.awt.*;
import java.awt.Point;
import java.awt.event.*;
import java.awt.geom.Point2D;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import javax.swing.*;

import static java.lang.Math.abs;

public class ClientGame extends JPanel {


    public int remainingTime = 120;


    private TimerPanel timerPanel;
    private int playerCountry;
    boolean initCountry = false;
    private int countClick = 0;
    private int countPlayer = 3;
    private boolean gameSatart = false;
    private boolean gameEnd = false;

    private int countAttackCountry = 2;
    private int countArcherCountry = 3;
    private int countMuinusSelf = 2;

    public int getPlayerCountry() {
        return playerCountry;
    }


    private static final int PANEL_WIDTH = 1200;
    //    private boolean[] useColor = new boolean[20];
    private static final int PANEL_HEIGHT = 700;
    private int NUM_PARTITIONS = 20;
    public List<Integer> playerCountryId = new ArrayList<>();
    private boolean isRandom = false;
    private static final int CENTER_RADIUS = 3;
    private Player player1;
    private StackedChartPanel stackedChartPanel;


    private List<Ball> balls = new ArrayList<>();
    private List<Partition> partitions;

    public int getNumberCountry(){return NUM_PARTITIONS; }

    public ClientGame(int countPlayer , boolean isRandom , int timer) {

        remainingTime = timer;


        this.isRandom = isRandom;
        generateVoronoiDiagram();
        stackedChartPanel = new StackedChartPanel();
        stackedChartPanel.setPreferredSize(new Dimension(PANEL_WIDTH, 100));
        setLayout(new BorderLayout());
        add(stackedChartPanel, BorderLayout.SOUTH);
        // setLayout(new BorderLayout());
        timerPanel = new TimerPanel();
        add(timerPanel, BorderLayout.EAST);
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int partitionIndex = getPartitionIndex(e.getX(), e.getY());

                if (e.getButton() == MouseEvent.BUTTON3) { // Right-click
                    if(!gameSatart && !isRandom){
                        // chnage kind Country;


                        return;
                    }


                    System.out.println("size: " + player1.getSelectedCountryForAttack().size());

                    Country country = Country.getCountryWithpartitionId(partitionIndex);
                    int select = player1.rightClick(country);

                    if(select == 1){
                        System.out.println("true");
                    }
                    else{
                        System.out.println("false");
                    }

                    Color color = player1.getColor();
                    if(select == 1) color = color.darker();


                    if(select != -1) partitions.get(partitionIndex).setColor(color);
//                    System.out.println("before: " + player1.getSelectedCountryForAttack().size());
//                    player1.clickOnCountry(country);
//                    System.out.println("id" + country.getId());
//                    System.out.println(player1.getSelectedCountryForAttack().size());


//                    Partition clickedPartition = getPartitionAtPoint(e.getPoint());
//                    if (clickedPartition != null) {
//                        repaint();
//                    }
                } else if (e.getButton() == MouseEvent.BUTTON1) { // Left-

                    if(!gameSatart && !isRandom){
                        if(countClick >= countPlayer){
                            gameSatart = true;
                            startAi();
                            return;
                        }
                        playerCountryId.add(partitionIndex);
                        Color colorPlayer = partitions.get(partitionIndex).getColor();
                        if(countClick == 0) colorPlayer = partitions.get(getPlayerCountry()).getColor();
                        Player player = new Player(countClick , partitionIndex , colorPlayer , 30);

                        if(countClick == 0){
                            player1 = player;
                            Color color = partitions.get(partitionIndex).getColor();


                            partitions.get(partitionIndex).setColor(partitions.get(getPlayerCountry()).getColor());
                            partitions.get(getPlayerCountry()).setColor(color);


                        }
                        countClick++;
//                        if(countClick > countPlayer){
//                            gameSatart = true;
//                        }

                        return;
                    }
//                    System.out.println(getPartitionIndex(e.getX(), e.getY()));
                    for(int i = 0; i < player1.getSelectedCountryForAttack().size(); i++){
                        partitions.get((player1.getSelectedCountryForAttack().get(i).getId())).setColor(player1.getColor());
                    }
//                    int partitionIndex = getPartitionIndex(e.getX(), e.getY());
                    Country country = Country.getCountryWithpartitionId(partitionIndex);
//                    player1.leftClick(country);
                    for(int i = 0; i < player1.getSelectedCountryForAttack().size(); i++){
                        Country playerCountry = player1.getSelectedCountryForAttack().get(i);
                        Thread t = new Thread(new Runnable(){
                            @Override
                            public void run() {
                                while (playerCountry.power > 0){
                                    playerCountry.power--;
                                    System.out.println("power: " + playerCountry.power);

                                    Ball ball = new Ball(new Point((int) partitions.get(playerCountry.getId()).getCenter().getX() , (int) partitions.get(playerCountry.getId()).getCenter().getY()),
                                            new Point(partitions.get(partitionIndex).getCenterX(), partitions.get(partitionIndex).getCenterY()), 50, player1.getColor().brighter());
                                    ball.setCountry(country);
                                    ball.setPlayer(player1);
                                    balls.add(ball);
                                    Thread thread = new Thread(ball);
                                    thread.start();
                                    repaint();
                                    try {
                                        Thread.sleep(200);
                                    } catch (InterruptedException ex) {
                                        throw new RuntimeException(ex);
                                    }
                                }




                            }
                        });

                        t.start();




//                        Thread th = new Thread(runnable);
//                        th.start();

                    }
                    player1.getSelectedCountryForAttack().clear();

//                    Ball ball = new Ball(new Point(10, 10), new Point(500, 420), 50, Color.PINK);
//                    balls.add(ball);
//                    Ball ball1 = new Ball(new Point(10 + 20, 10 - 20), new Point(500, 420), 50, Color.GREEN);
//                    balls.add(ball1);
//                    Ball ball2 = new Ball(new Point(10 - 20, 10 + 20), new Point(500, 420), 50, Color.BLUE);
//                    balls.add(ball2);
//                    Thread thread = new Thread(ball);
//                    thread.start();
//                    Thread thread1 = new Thread(ball1);
//                    thread1.start();
//                    Thread thread2 = new Thread(ball2);
//                    thread2.start();
//
//                    repaint();
                }
            }
        });
    }

    public boolean checkGameEnd() {
        for (int i = 0; i < Player.players.size(); i++) {
            if (Player.players.get(i).getCountries().size() > 0) {
                return false;
            }
        }

        return true;
    }
    public boolean gameEnd(){

//        calculateColorPercentages();
        double max = 0;
        double playerPercentage = 0;
        for (Map.Entry<Color, Double> entry : calculateColorPercentages().entrySet()) {
            Color color = entry.getKey();
            double percentage = entry.getValue();
            if(max < percentage) max = entry.getValue();
            if(player1.getColor().equals(color)) {
                playerPercentage = entry.getValue();
            }

        }

        if(playerPercentage == max){
            System.out.println("you win");
            return true;
        }
        else {
            System.out.println("you lose");
            return  false;
        }
    }
    public void startAi(){
        System.out.println("AI Start");
        for(int i = 0; i < Player.players.size(); i++){
            Player currentPlayer = Player.players.get(i);
            new java.util.Timer().schedule(
                    new java.util.TimerTask() {
                        @Override
                        public void run() {
                            if (player1.getId() != currentPlayer.getId()){
                                Thread t = new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        while (true && initCountry) {
                                            System.out.println("player " + currentPlayer.getId());
                                            System.out.println("sizes: " + Country.countryList.size());
                                            if(currentPlayer.getCountries().size() == 0) continue;
                                            int randomCountry = abs(new Random().nextInt()) % currentPlayer.getCountries().size();
//                                            int randomCountryForAttack = (int) Math.floor(Math.random() *(Country.countryList.size() - 1 - 0 + 1) + 0);
                                            int randomCountryForAttack = abs(new Random().nextInt()) % Country.countryList.size();
                                            System.out.println("randomNumber: " + randomCountryForAttack);
                                            Country country = Country.countryList.get(randomCountryForAttack);

                                            System.out.println("randomCountry: " + randomCountry);
                                            Country playerCountry = currentPlayer.getCountries().get(randomCountry);
                                            Thread t2 = new Thread(new Runnable(){
                                                @Override
                                                public void run() {
                                                    while (playerCountry.power > 0){
                                                        playerCountry.power--;
                                                        System.out.println("power: " + playerCountry.power);
//                                                            Ball ball = new Ball();

                                                        Ball ball = new Ball(new Point((int) partitions.get(playerCountry.getId()).getCenter().getX() , (int) partitions.get(playerCountry.getId()).getCenter().getY()),
                                                                new Point(partitions.get(randomCountryForAttack).getCenterX(), partitions.get(randomCountryForAttack).getCenterY()), 50, currentPlayer.getColor().brighter());
                                                        ball.setCountry(country);
                                                        ball.setPlayer(currentPlayer);
                                                        balls.add(ball);
                                                        Thread thread = new Thread(ball);
                                                        thread.start();
                                                        repaint();
                                                        try {
                                                            Thread.sleep(200);
                                                        } catch (InterruptedException ex) {
                                                            throw new RuntimeException(ex);
                                                        }
                                                    }
                                                }
                                            });

                                            t2.start();

                                            try {
                                                Thread.sleep(5000);
                                            } catch (InterruptedException ex) {
                                                throw new RuntimeException(ex);
                                            }
                                        }


                                    }
                                });
                                t.start();
                            }
                        }
                    },
                    1000
            );
        }
    }

    private Partition getPartitionAtPoint(Point point) {
        for (Partition partition : partitions) {
            int centerX = partition.getCenterX();
            int centerY = partition.getCenterY();
            int radius = CENTER_RADIUS;
            int x = centerX - radius;
            int y = centerY - radius;

            if (point.x >= x && point.x <= x + 2 * radius && point.y >= y && point.y <= y + 2 * radius) {
                return partition;
            }
        }
        return null;
    }

    private void darkenPartitionColor(Partition partition) {
        Color color = partition.getColor();
        float[] hsb = Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), null);
        float brightness = hsb[2];
        float darkenedBrightness = Math.max(brightness - 0.1f, 0f);
        Color darkerColor = Color.getHSBColor(hsb[0], hsb[1], darkenedBrightness);
        partition.setColor(darkerColor);
    }
    private void generateVoronoiDiagram() {
        partitions = new ArrayList<>();
        Random random = new Random();
        ImageIcon icon;
        // Calculate the padding to keep partition centers away from the sides
        int paddingX = PANEL_WIDTH / 4;
        int paddingY = PANEL_HEIGHT / 4;
        paddingX = 40;
        paddingY = 40;

        // Generate random partition centers away from the sides and not too close to each other
        int minX = paddingX + CENTER_RADIUS + 1;
        int maxX = PANEL_WIDTH - paddingX - CENTER_RADIUS - 1;
        int minY = paddingY + CENTER_RADIUS + 1;
        int maxY = PANEL_HEIGHT - paddingY - CENTER_RADIUS - 1;

        for (int i = 0; i < NUM_PARTITIONS; i++) {

            Color color = new Color(255 , 255 , 255);
            if(i == 0){
                playerCountry = 0;
                color = new Color(Colors.colorArrayList.get(User.color)[0], Colors.colorArrayList.get(User.color)[1], Colors.colorArrayList.get(User.color)[2]);
                Colors.colorArrayList.get(User.color)[3] = 1;
            }
            else{
                for(int j = 0; j < Colors.colorArrayList.size(); j++){
                    if(Colors.colorArrayList.get(j)[3] == 0){
//                    if(j == User.color) playerCountry = i;
                        color = new Color(Colors.colorArrayList.get(j)[0], Colors.colorArrayList.get(j)[1], Colors.colorArrayList.get(j)[2]);
                        Colors.colorArrayList.get(j)[3] = 1;
                        break;
                    }
                    else {
                        System.out.println("same");
                    }
                }
            }

            // Generate a new partition center
            int x, y;
            boolean valid;
            do {
                x = random.nextInt(maxX - minX) + minX;
                y = random.nextInt(maxY - minY) + minY;
                valid = true;

                // Check if the new partition center is too close to any existing center
                for (Partition existingPartition : partitions) {
                    int distanceX = abs(x - existingPartition.getCenterX());
                    int distanceY = abs(y - existingPartition.getCenterY());
//                    int minDistance

                    int minDistance = CENTER_RADIUS * 40; // Minimum distance between partition centers

                    if (distanceX < minDistance && distanceY < minDistance) {
                        valid = false;
                        break;
                    }
                }
            } while (!valid);

//            Color color = getRandomColor(random);
            partitions.add(new Partition(x, y, color));
        }
    }

    public int getPartitionIndex(int x, int y) {
        Partition nearestPartition = getNearestPartition(x, y);
        return partitions.indexOf(nearestPartition);
    }

    private Color getRandomColor(Random random) {
        float r = random.nextFloat();
        float g = random.nextFloat();
        float b = random.nextFloat();
        return new Color(r, g, b);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawMap(g);

        // Draw balls
        Iterator<Ball> ballIterator = balls.iterator();
        while (ballIterator.hasNext()) {
            Ball ball = ballIterator.next();
            if(ball == null) continue;
            if (ball.shouldRemove()) {
                ballIterator.remove();
                continue;
            }
            ball.updatePosition();
            ball.draw(g);

            // Check collision with other balls
            if (checkBallCollision(ball)) {
                ballIterator.remove();
            }
        }
        repaint();
    }
    private class StackedChartPanel extends JPanel {
        private static final int CHART_HEIGHT = 50;

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            Map<Color, Integer> colorCounts = new HashMap<>();
            int totalPartitions = partitions.size();

            // Count the occurrences of each color
            for (Partition partition : partitions) {
                Color color = partition.getColor();
                colorCounts.put(color, colorCounts.getOrDefault(color, 0) + 1);
            }

            int x = 10;
            int y = 10;
            int width = getWidth() - 20;
            int height = CHART_HEIGHT;

            // Calculate the width of each color bar based on the percentage
            Map<Color, Double> colorPercentages = calculateColorPercentages();
            for (Map.Entry<Color, Double> entry : colorPercentages.entrySet()) {
                Color color = entry.getKey();
                double percentage = entry.getValue();

                int barWidth = (int) (width * (percentage / 100.0));
                g.setColor(color);
                g.fillRect(x, y, barWidth, height);

                // Update the x position for the next bar
                x += barWidth;
            }
        }

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(PANEL_WIDTH, CHART_HEIGHT + 20);
        }
    }

    private Map<Color, Double> calculateColorPercentages() {
        Map<Color, Integer> colorCounts = new HashMap<>();

        // Count the occurrences of each color
        for (Partition partition : partitions) {
            Color color = partition.getColor();
            colorCounts.put(color, colorCounts.getOrDefault(color, 0) + 1);
        }

        // Calculate the percentage for each color
        Map<Color, Double> colorPercentages = new HashMap<>();
        int totalPartitions = partitions.size();
        for (Map.Entry<Color, Integer> entry : colorCounts.entrySet()) {
            Color color = entry.getKey();
            int count = entry.getValue();
            double percentage = (count / (double) totalPartitions) * 100.0;
            colorPercentages.put(color, percentage);
        }

        return colorPercentages;
    }
    private void drawMap(Graphics g) {
//        iii++;
//        System.out.println("iii: " + iii);
//        System.out.println("helllllll");
        Graphics2D g2d = (Graphics2D) g;

        for (int x = 0; x < PANEL_WIDTH; x++) {
            for (int y = 0; y < PANEL_HEIGHT; y++) {
                Partition nearestPartition = getNearestPartition(x, y);
                g.setColor(nearestPartition.getColor());
                g.fillRect(x, y, 1, 1);
            }
        }

        // Draw partition centers and labels
        g.setColor(Color.BLACK);
        Font font = new Font("Arial", Font.BOLD, 12);
        g.setFont(font);
        FontMetrics fontMetrics = g.getFontMetrics();

        for (int i = 0; i < partitions.size(); i++) {
            Partition partition = partitions.get(i);
            int centerX = partition.getCenterX();
            int centerY = partition.getCenterY();
            int radius = CENTER_RADIUS;
            int diameter = radius * 2;
            int x = centerX - radius;
            int y = centerY - radius;

            // Draw the outline of the partition center in black
            g.fillOval(x, y, diameter, diameter);



//            // Draw partition label
//            if(iii < 100){
//
            if(initCountry){
//                   System.out.println("initCountry " + initCountry);
                String label = Integer.toString(Country.countryList.get(i).getPower());
                int labelWidth = fontMetrics.stringWidth(label);
                int labelX = centerX - labelWidth / 2;
                int labelY = centerY + radius + fontMetrics.getHeight();
                g.drawString(label, labelX, labelY);
            }
//            }
//            else {
//                String label = "AAAAAAAAA " + (i + 1);
//                int labelWidth = fontMetrics.stringWidth(label);
//                int labelX = centerX - labelWidth / 2;
//                int labelY = centerY + radius + fontMetrics.getHeight();
//                g.drawString(label, labelX, labelY);
//            }

            // Draw icon
            ImageIcon icon = partition.getPartitionIcon(); // Replace with the path to your actual icon file
            int iconX = centerX - icon.getIconWidth() / 2;
            int iconY = centerY - icon.getIconHeight() / 2;
            icon.paintIcon(this, g2d, iconX, iconY - 23);
        }
    }

    private ImageIcon getPartitionIcon(Partition partition) {
        // Replace this with your own logic to retrieve the appropriate icon for the partition
        // You can use partition information to determine which icon to display
        // Here, we're just using a placeholder icon
        ImageIcon icon = new ImageIcon(getClass().getResource("/simple.png"));
        return icon;
    }
    public void setPartitionIcon() {
//            ImageIcon icon
        // Replace this with your own logic to retrieve the appropriate icon for the partition
        // You can use partition information to determine which icon to display
        // Here, we're just using a placeholder icon
        ImageIcon icon = new ImageIcon(getClass().getResource("/javaapplication10/simple.png"));
    }


    private Partition getNearestPartition(int x, int y) {
        Partition nearestPartition = null;
        double minDistance = Double.MAX_VALUE;

        for (Partition partition : partitions) {
            double distance = partition.getCenter().distance(x, y);
            if (distance < minDistance) {
                minDistance = distance;

                nearestPartition = partition;
            }
        }

        return nearestPartition;
    }

    private boolean checkBallCollision(Ball ball) {
        for (Ball otherBall : balls) {
            if(otherBall == null) continue;
            if (ball != otherBall && ball.getColor() != otherBall.getColor()) {
                double dx = ball.startPoint.x - otherBall.startPoint.x;
                double dy = ball.startPoint.y - otherBall.startPoint.y;
                double distance = Math.sqrt(dx * dx + dy * dy);

                if (distance <= Ball.BALL_RADIUS * 2) {
                    ball.shouldRemove = true;
                    otherBall.shouldRemove = true;
                    return true;
                }
            }
        }
        return false;
    }

    public class Ball implements Runnable {
        private Point startPoint;
        private Point endPoint;
        private Color color;
        private boolean shouldRemove;
        private static final int BALL_RADIUS = 5;
        private static final int BALL_SPEED = 3;
        private static final int UPDATE_INTERVAL = 16; // Update interval in milliseconds (approx. 60 FPS)
        private Country country;
        public void  setCountry(Country country){this.country = country; }
        public void setPlayer(Player player){this.attacker = player; }
        private Player attacker;
        public void damageToCountry(){
            if(country.getCathcerId() != attacker.getId()) {
                boolean cachCountry = country.minusPower(attacker.getId(), false);
                if(cachCountry) {
                    timerPanel.addTime(30);
                    partitions.get(country.getId()).setColor(attacker.getColor());
                }

            }

            else {
                country.plusPowerFromAnotherCountry();
                gameEnd = checkGameEnd();
                System.out.println("from here");
                partitions.get(country.getId()).setColor(attacker.getColor());
            }
        }


        private long lastUpdateTime = System.currentTimeMillis();

        public Ball(Point startPoint, Point endPoint, int BALL_SPEED, Color color) {
            this.startPoint = startPoint;
            this.endPoint = endPoint;
            this.shouldRemove = false;
            this.color = color;
        }

        public void draw(Graphics g2d) {
            g2d.setColor(color);
            g2d.fillOval(startPoint.x - BALL_RADIUS, startPoint.y - BALL_RADIUS, BALL_RADIUS * 2, BALL_RADIUS * 2);
        }

        public void updatePosition() {
            long currentTime = System.currentTimeMillis();
            long elapsedTime = currentTime - lastUpdateTime;
            lastUpdateTime = currentTime;

            double dx = endPoint.x - startPoint.x;
            double dy = endPoint.y - startPoint.y;
            double distance = Math.sqrt(dx * dx + dy * dy);

            if (distance <= BALL_SPEED * (elapsedTime / (double) UPDATE_INTERVAL)) {
                startPoint = endPoint; // Set the start point to the end point when the ball reaches its destination
                shouldRemove = true;
            } else {
                double ratio = BALL_SPEED * (elapsedTime / (double) UPDATE_INTERVAL);
                double vx = dx * ratio / distance;
                double vy = dy * ratio / distance;
                startPoint.x += vx;
                startPoint.y += vy;
            }
        }

        @Override
        public void run() {
            while (!shouldRemove) {
                updatePosition();
                repaint();
                remainingTime--;
                remainingTime /= 1000;
                if(remainingTime == 0){
                    // game over
                    boolean result = gameEnd();
                }
                try {
                    Thread.sleep(UPDATE_INTERVAL);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            balls.remove(this); // Remove the ball from the list after it reaches its destination
            this.damageToCountry();
            repaint(); // Repaint the panel to update the visualization
        }

        public boolean shouldRemove() {
            return shouldRemove;
        }

        public Color getColor() {
            return color;
        }
    }

    private static class Partition {
        private Point2D center;
        private Color color;
        private ImageIcon partitionIcon;

        public Partition(int x, int y, Color color) {
            this.center = new Point2D.Double(x, y);
            this.color = color;
        }

        public Point2D getCenter() {
            return center;
        }

        public int getCenterX() {
            return (int) center.getX();
        }

        public int getCenterY() {
            return (int) center.getY();
        }

        public Color getColor() {
            return color;
        }

        public void setColor(Color color) {
            this.color = color;
        }
        public void setPartitionIcon(String imageAddress){
            partitionIcon = new ImageIcon(getClass().getResource(imageAddress));
        }
        public ImageIcon getPartitionIcon(){
            return this.partitionIcon;
        }
    }

    public static void main(String[] args) {

        int countryNumber = 2;




//        int indexPlayerOne = 3;
        Color player1Color = new Color(Colors.colorArrayList.get(0)[0] , Colors.colorArrayList.get(0)[1], Colors.colorArrayList.get(0)[2]);


//        Color partitionColor = new Color(colorArrayList.get(i)[0], colorArrayList.get(i)[1], colorArrayList.get(i)[2]);



        SwingUtilities.invokeLater(() -> {
            int player2Color = 1;
            JFrame frame = new JFrame("Voronoi Diagram Map Generator");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            ClientGame mapGenerator = new ClientGame(3 , true , 150);
            try(Socket socket = new Socket("localhost" , 5050)){
                ObjectOutputStream stream = new ObjectOutputStream(socket.getOutputStream());
                RequestHandler requestHandler = new RequestHandler("getUserColor" , "1");
                stream.writeObject(requestHandler);
                stream.flush();
                ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
                RespondHandler respondHandler = (RespondHandler) objectInputStream.readObject();
                player2Color = Integer.parseInt(respondHandler.getJsonResult());


            }  catch (IOException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }

            mapGenerator.setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));

            for(int i = 0; i < mapGenerator.countAttackCountry; i++){
                new AttackerCountry();
                int countryId = Country.countryList.size() - 1;
                mapGenerator.partitions.get(countryId).setPartitionIcon("attack.png");

            }
            for(int i = 0; i <  mapGenerator.countArcherCountry; i++){
//                Color color = mapGenerator.partitions.get(i).getColor();
                new ArcherCountry();
                int countryId = Country.countryList.size() - 1;
                mapGenerator.partitions.get(countryId).setPartitionIcon("bow1.png");
            }
            for(int i = 0; i <  mapGenerator.countMuinusSelf; i++){
//                Color color = mapGenerator.partitions.get(i).getColor();
                new MinusSelfCountry();
                int countryId = Country.countryList.size() - 1;
                mapGenerator.partitions.get(countryId).setPartitionIcon("pirate.png");
            }

            for(int i = 0; i < mapGenerator.NUM_PARTITIONS - mapGenerator.countAttackCountry - mapGenerator.countArcherCountry - mapGenerator.countMuinusSelf; i++){
//                Color color = mapGenerator.partitions.get(i).getColor();
                new NormalCountry();
                int countryId = Country.countryList.size() - 1;
                mapGenerator.partitions.get(countryId).setPartitionIcon("simple.png");
            }

//            if(mapGenerator.isRandom) {
            Player player1 = new Player(0 , mapGenerator.getPlayerCountry() , mapGenerator.partitions.get(mapGenerator.getPlayerCountry()).getColor() , 30);
                mapGenerator.player1 = player1;
//            }
            // else player1 = new Player(0 , mapGenerator.playerCountryId.get(0) , mapGenerator.partitions.get(mapGenerator.playerCountryId.get(0)).getColor() , 30);

            int countPlayer = mapGenerator.countPlayer;
//            Country.countryList.get(indexPlayerOne).setCathcerId(player1.getId());
            for(int i = 1; i < countPlayer; i++){
//            Color playerColor = new Color(Colors.colorArrayList.get(0)[0] , Colors.colorArrayList.get(0)[1], Colors.colorArrayList.get(0)[2]);

//                int randomNumber = (int) Math.floor(Math.random() *(Country.countryList.size() - 0 + 1) + 0);
//                for (Partition partition : mapGenerator.partitions) {
//
//                }
              new Player(i , i , mapGenerator.partitions.get(i).getColor() , 30);
                // else new Player(i , mapGenerator.playerCountryId.get(i) , mapGenerator.partitions.get( mapGenerator.playerCountryId.get(i)).getColor() , 30);


            }

            if(mapGenerator.isRandom) mapGenerator.startAi();
            mapGenerator.initCountry = true;

            for (int i = 0; i < Country.countryList.size(); i++){
                System.out.println(Country.countryList.get(i).getCathcerId());
            }


            frame.setContentPane(mapGenerator);
            frame.setSize(1400, 800);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
    public class TimerPanel extends JPanel {    private static final int TIMER_DELAY = 1000; // 1 second
        private static final int INITIAL_TIME = 60; // Initial time in seconds
        private JLabel timerLabel;    private int currentTime;
        public TimerPanel() {
            setLayout(new BorderLayout());
            timerLabel = new JLabel();        timerLabel.setFont(new Font("Arial", Font.BOLD, 24));
            timerLabel.setHorizontalAlignment(SwingConstants.CENTER);        add(timerLabel, BorderLayout.CENTER);
            Timer timer = new Timer(TIMER_DELAY, new ActionListener() {
                @Override            public void actionPerformed(ActionEvent e) {
                    if (currentTime > 0) {                    currentTime--;
                        updateTimerLabel();                }
                }        });
            timer.setInitialDelay(0);
            timer.start();
            currentTime = INITIAL_TIME;        updateTimerLabel();
        }
        public void addTime(int seconds) {        currentTime += seconds;
            updateTimerLabel();    }
        private void updateTimerLabel() {
            timerLabel.setText("Time: " + currentTime + "s");    }
    }

}

