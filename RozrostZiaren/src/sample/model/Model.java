package sample.model;


import javafx.scene.control.Alert;
import javafx.scene.paint.Color;
import sample.controller.Controller;
import sample.model.Neighbourhoods.*;

import java.util.*;
import java.util.List;

public class Model {

    private int height;
    private int width;
    private Embryo[][] embryos;
    private Embryo[][] embryos2;
    private boolean periodic = false;
    private int allGrains;
    public int[][] space;
    vonNeumann vonNeumann = new vonNeumann();
    Moore moore = new Moore();
    HexagonalLeft hexagonalLeft = new HexagonalLeft();
    HexagonalRight hexagonalRight = new HexagonalRight();
    Pentagonal pentagonal = new Pentagonal();


    public Model(int width, int height) {
        this.width = width;
        this.height = height;

        embryos = new Embryo[height][width];
        embryos2 = new Embryo[height][width];

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                embryos[i][j] = new Embryo();
                embryos2[i][j] = new Embryo();
            }
        }
    }


    public void randomPoints(int numberOfPoints) {
        Random randomGenerator = new Random();
        List<Color> colorList = new ArrayList<>();

        for (int i = 0; i < numberOfPoints; i++) {
            int x = randomGenerator.nextInt(width);
            int y = randomGenerator.nextInt(height);
            Color color = Color.rgb(randomGenerator.nextInt(255), randomGenerator.nextInt(255), randomGenerator.nextInt(255));
            if(!colorList.contains(color)) {
                embryos[y][x].setColor(color);
                embryos[y][x].setState(1);
                embryos[y][x].setId(numberOfPoints--);
                Embryo.putColorsMap(embryos[y][x].getId(), embryos[y][x].getColor());
                colorList.add(color);
            }
        }
    }

    public void randomRadius(int numberOfPoints, int radius){
        System.out.println(numberOfPoints);
        Random randomGenerator = new Random();
        List<Color> colorList = new ArrayList<>();
        List<Integer> xCords = new ArrayList<>();
        List<Integer> yCords = new ArrayList<>();
        System.out.println(radius);
        System.out.println(height);
       if(radius >= height/2 || radius>= width/2){
       // if( (radius >= height*0.1) || (radius>= width*0.1)){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information");
            alert.setHeaderText("Należy podać mniejszy promień");
            alert.setContentText("promień:" + radius + " jest za duży ");
            alert.showAndWait();

        }
        else {
            for (int i = 0; i < numberOfPoints; i++) {

                int x = randomGenerator.nextInt(width);
                int y = randomGenerator.nextInt(height);
                Color color = Color.rgb(randomGenerator.nextInt(255), randomGenerator.nextInt(255), randomGenerator.nextInt(255));
                //  for (int i = 0; i < numberOfPoints; i++) {
                //  if(!colorList.contains(color)) {

                if (i == 0) {
                    //   if(!colorList.contains(color)) {
                    embryos[y][x].setColor(color);
                    embryos[y][x].setState(1);
                    embryos[y][x].setId(numberOfPoints--);
                    Embryo.putColorsMap(embryos[y][x].getId(), embryos[y][x].getColor());
                    colorList.add(color);
                    xCords.add(x);
                    yCords.add(y);
                    //   }
                } else {
                   //System.out.println("weszlo do else");
                    int xSize = xCords.size();
                   // System.out.println("yCords.size();" + yCords.size());
                    int ySize = yCords.size();
                    int counter = 0;
//                    for (Integer xcoo : xCords
//                            ) {
//                        System.out.println("xcoo" + xcoo);
//
//                    }
//                    for (Integer ycoo : yCords
//                            ) {
//                        System.out.println("ycoo" + ycoo);
//
//                    }

                    //  System.out.println( xCords.get(i-1));
                    // for (int j = 0; j < xSize; j++) {
                    for (int k = 0; k < yCords.size(); k++) {
                      //  System.out.println("weszlo do fora");
                     //   System.out.println("caly if " + x + ">" + xCords.get(xSize - 1) + "+" + radius + "||" + x + "<" + xCords.get(xSize - 1) + "+" + radius + "&&" +
                         //       +y + ">" + yCords.get(ySize - 1) + "+" + radius + "||" + y + "<" + yCords.get(ySize - 1) + " +" + radius);
                        if (((x > xCords.get(xSize - 1) + radius) || (x < xCords.get(xSize - 1) - radius)) &&
                                ((y > yCords.get(ySize - 1) - radius) || (y < yCords.get(ySize - 1) + radius))) {
                          //  System.out.println("counter wzrosl");
                            counter++;
//                                embryos[y][x].setColor(color);
//                                embryos[y][x].setState(1);
//                                embryos[y][x].setId(numberOfPoints--);
//                                Embryo.putColorsMap(embryos[y][x].getId(), embryos[y][x].getColor());
//                                colorList.add(color);
//                                xCords.add(x);
//                                yCords.add(y);
//                                 xSize--;
//                                ySize--;
                        } else {
                          //  System.out.println("weszlo do else?!");
                        }
                        xSize--;
                        ySize--;
                    }
                    System.out.println(counter + "!=" + xCords.size());
                    if (counter == xCords.size()) {
                      //  System.out.println(counter + "==" + xCords.size());
                        embryos[y][x].setColor(color);
                        embryos[y][x].setState(1);
                        embryos[y][x].setId(numberOfPoints--);
                      //  System.out.println(numberOfPoints);
                        Embryo.putColorsMap(embryos[y][x].getId(), embryos[y][x].getColor());
                        colorList.add(color);
                        xCords.add(x);
                        yCords.add(y);
                    }
                    //  }
                }
                //   }
                //NIE DZIALA WCALE
//                else{
//                    for (Integer xx : xCords) {
////                        for (Integer yy: yCords) {
//
//                            if( ((x > xx+radius) || (x < xx))){// &&
//                                 //   ((y> yy) || (y < yy)) ){
//                                embryos[y][x].setColor(color);
//                                embryos[y][x].setState(1);
//                                embryos[y][x].setId(numberOfPoints--);
//                                Embryo.putColorsMap(embryos[y][x].getId(), embryos[y][x].getColor());
//                                colorList.add(color);
//                                xCords.add(x);
//                                yCords.add(y);
//                         //   }
//
//                        }
//                    }


                //   }
            }
        }
    }

    public void randomEvenPoints(int numberOfPoints){
        List<Color> colorList = new ArrayList<>();
        Random randomGenerator = new Random();
        int points = (int) Math.sqrt(numberOfPoints);
        int xLimit = height/points-1;
        int yLimit = width/points-1;
        int counter =0;

        for(int i =0; i<=points ;i++ ){
            for(int j=0; j<=points && counter<numberOfPoints; j++){
                if(embryos[i*xLimit+1][j*yLimit+1].getState() == 0){
                    int x = randomGenerator.nextInt(width);
                    int y = randomGenerator.nextInt(height);
                    Color color = Color.rgb(randomGenerator.nextInt(255), randomGenerator.nextInt(255), randomGenerator.nextInt(255));
                    if(!colorList.contains(color)) {
                        embryos[i * xLimit + 1][j * yLimit + 1].setState(1);
                        embryos[i * xLimit + 1][j * yLimit + 1].setColor(color);
                        colorList.add(color);
                    }
                }
            }
        }
    }
    public void randomSteadilyPoints(int numberOfPoints) {
        Random generator = new Random();
        int height = this.height;
        int width = this.width;
        double sqrtArg = (width / height * numberOfPoints) + (Math.pow(width - height, 2) / (4 * height * height));
        double nx = Math.ceil(Math.sqrt(sqrtArg) - ((width - height) / (2 * height)));
        double ny = Math.ceil(numberOfPoints / nx);
        double deltaX = (int) (width / nx);
        double deltaY = (int) (height / ny);
//        double nx = Math.sqrt((width / height) * numberOfPoints + ((width - height) * (width - height)) / (4 * height * height))
//                - (width - height) / (2 * height);
//        double ny = height / width * nx + 1 - height / width;
//        double deltaX = width / nx;
//        double deltaY = height / ny;

        int grainY = generator.nextInt((int) deltaY);
        int firstGrainX = generator.nextInt((int) deltaX);
//        while (grainY < height)
//        {
//            double grainX = firstGrainX;
//            while(grainX < width)
//            {
//
//                allGrains++;
//                embryos[(int) grainX][grainY].setState(1);
//                embryos[(int) grainX][grainY].setColor(Color.rgb(generator.nextInt(255), generator.nextInt(255), generator.nextInt(255)));
//                if (allGrains == numberOfPoints)
//                    break;
//                grainX += deltaX;
//            }
//            grainY += deltaY;
//            if (allGrains == numberOfPoints)
//                break;
//        }

        while (numberOfPoints > 0) {
            for (int i = 0; (i < height && numberOfPoints > 0); i += deltaX) {
                for (int j = 0; (j < width && numberOfPoints > 0); j += deltaY) {

                    embryos[i][j].setState(1);
                    embryos[i][j].setColor(Color.rgb(generator.nextInt(255), generator.nextInt(255), generator.nextInt(255)));
                    embryos[i][j].setId(numberOfPoints--);
                    Embryo.putColorsMap(embryos[i][j].getId(), embryos[i][j].getColor());
                    //  numberOfPoints--;
                }
            }
        }
    }


//    public void randomPointsWithRadiouss(int numberOfPoints, int radious){
//        List<Coordinates> placedEmbryo = new ArrayList<>();
//        List<PeriodicPoint> periodicPlacedEmbryo = new ArrayList<>();
//        Random random = new Random();
//        for (int i = 1; i <= numberOfPoints; i++)
//        {
//            boolean embryoPlaced = false;
//            while (!embryoPlaced)
//            {
//                int x = random.nextInt(height);
//                int y = random.nextInt(width);
//                if (periodic)
//                {
//                  //  List<Coordinates> inCircle = placedEmbryo.w(g => DisctanceBetweenPoints(x, g.X, y, g.Y) <= radious).toList();
//                  //  List<Coordinates> inPeriodicCircle = periodicPlacedEmbryo.where(g => DisctanceBetweenPoints(x, g.PeriodicX, y, g.PeriodicY) <= radious).toList();
//                  //  if (inCircle.size() == 0 && inPeriodicCircle.size() == 0)
//                    {
//                        allGrains++;
//                        placedEmbryo.add(new Coordinates(x, y));
//                        if (x <= radious || x >= height - radious || y <= radious || y >= width - radious)
//                        {
//                            periodicPlacedEmbryo.add(new PeriodicPoint(x, y, width, height, radious));
//                        }
//                        space[y][x] = allGrains;
//                        embryoPlaced = true;
//                    }
//                } else
//                {
//                 //   List<Coordinates>  inCircle = placedEmbryo.Where(g => DisctanceBetweenPoints(x, g.X, y, g.Y) <= radious).toList();
//                  //  if (inCircle.size() == 0)
//                    {
//                        allGrains++;
//                        placedEmbryo.add(new Coordinates(x, y));
//                        space[y][x] = allGrains;
//                        embryoPlaced = true;
//                    }
//                }
//            }
//        }
//    }
//
//    double DisctanceBetweenPoints(int x1, int x2, int y1, int y2)
//    {
//        double xDiff = (double) (x2 - x1);
//        double yDiff = (double) (y2 - y1);
//        return Math.sqrt(Math.pow(xDiff, 2.0) + Math.pow(yDiff, 2.0));
//    }

    public void clearGrid() {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                embryos[i][j].setState(0);
                embryos[i][j].setColor(Color.WHITE);
                embryos2[i][j].setState(0);
                embryos2[i][j].setColor(Color.WHITE);
            }
        }
        Embryo.getColorsMap().clear();
    }

    public void periodic(int i, int j){
        if (i == 1) {
            if (embryos[i][j].getState() == 0) {
                if (embryos[height - 2][j].getState() == 1) {
                    embryos2[i][j].setState(1);
                    embryos2[i][j].setColor(embryos[height - 2][j].getColor());
                }
            } else {
                embryos2[i][j].setState(1);
                embryos2[i][j].setColor(embryos[i][j].getColor());
            }
        }
        if (i == height - 2) {
            if (embryos[i][j].getState() == 0) {
                if (embryos[1][j].getState() == 1) {
                    embryos2[i][j].setState(1);
                    embryos2[i][j].setColor(embryos[1][j].getColor());
                }
            } else {
                embryos2[i][j].setState(1);
                embryos2[i][j].setColor(embryos[i][j].getColor());
            }
        }
        if (j == 1) {
            if (embryos[i][j].getState() == 0) {
                if (embryos[i][width - 2].getState() == 1) {
                    embryos2[i][j].setState(1);
                    embryos2[i][j].setColor(embryos[i][width - 2].getColor());
                }
            } else {
                embryos2[i][j].setState(1);
                embryos2[i][j].setColor(embryos[i][j].getColor());
            }
        }
        if (j == width - 2) {
            if (embryos[i][j].getState() == 0) {
                if (embryos[i][1].getState() == 1) {
                    embryos2[i][j].setState(1);
                    embryos2[i][j].setColor(embryos[i][1].getColor());
                }
            } else {
                embryos2[i][j].setState(1);
                embryos2[i][j].setColor(embryos[i][j].getColor());
            }
        }
    }

//    public void allFourNeighbours(int i, int j) {
//        Map<Color, Integer> map = new HashMap<>();
//        Color color;
//        int count = 1;
//        {
//        if (embryos[i][j + 1].getState() == 1) {
//            if(!embryos[i][j + 1].getColor().equals(Color.WHITE)) {
//                if (map.containsKey(embryos[i][j + 1].getColor())) {
//                    map.put(embryos[i][j + 1].getColor(), count + 1);
//                } else {
//                    map.put(embryos[i][j + 1].getColor(), count);
//                }
//            }
//             embryos2[i][j].setState(1);
//             embryos2[i][j].setColor(embryos[i][j + 1].getColor());
//        }
//
//        if (embryos[i + 1][j].getState() == 1) {
//            if(!embryos[i + 1][j].getColor().equals(Color.WHITE)) {
//                if (map.containsKey(embryos[i + 1][j].getColor())) {
//                    map.put(embryos[i + 1][j].getColor(), count + 1);
//                } else {
//                    map.put(embryos[i + 1][j].getColor(), count);
//                }
//            }
//             embryos2[i][j].setState(1);
//             embryos2[i][j].setColor(embryos[i + 1][j].getColor());
//        }
//
//        if (embryos[i][j - 1].getState() == 1) {
//            if(!embryos[i][j - 1].getColor().equals(Color.WHITE)) {
//                if (map.containsKey(embryos[i][j - 1].getColor())) {
//                    map.put(embryos[i][j - 1].getColor(), count + 1);
//                } else {
//                    map.put(embryos[i][j - 1].getColor(), count);
//                }
//            }
//              embryos2[i][j].setState(1);
//              embryos2[i][j].setColor(embryos[i][j - 1].getColor());
//        }
//
//        if (embryos[i - 1][j].getState() == 1) {
//            if(!embryos[i - 1][j].getColor().equals(Color.WHITE)) {
//                if (map.containsKey(embryos[i - 1][j].getColor())) {
//                    map.put(embryos[i - 1][j].getColor(), count + 1);
//                } else {
//                    map.put(embryos[i - 1][j].getColor(), count);
//                }
//            }
//             embryos2[i][j].setState(1);
//             embryos2[i][j].setColor(embryos[i - 1][j].getColor());
//        }
//    }
//
//    if(map.size()>0){
//            Map.Entry<Color,Integer> maxEntry = null;
//            for(Map.Entry<Color,Integer> entry : map.entrySet()){
//                if(maxEntry == null || entry.getValue().compareTo(maxEntry.getValue()) >0){
//                    maxEntry=entry;
//                }
//            }
//         color= maxEntry.getKey();
//         embryos2[i][j].setState(1);
//         embryos2[i][j].setColor(color);
//    }
//    }

//    public void vonNeumannNeighbourhood(int i, int j){
//        if (embryos[i][j].getState() == 0) {
//            vonNeumann.allFourNeighbours(i,j,embryos,embryos2);
//        } else {
//            embryos2[i][j].setState(1);
//            embryos2[i][j].setColor(embryos[i][j].getColor());
//        }
//    }

    public void vonNeumannNeighborhoodPeriodic(){
        for (int i = 1; i < height-1; i++) {
            for (int j = 1; j < width - 1; j++) {
                    periodic(i,j);
                    vonNeumann.allFourNeighbours(i,j,embryos,embryos2);
            }
        }
        newEmbryosOnMesh();
    }

    public void vonNeumannNeighborhoodWithoutPeriodic() {
        for (int i = 1; i < height-1; i++) {
            for (int j = 1; j < width-1; j++) {
                vonNeumann.allFourNeighbours(i,j,embryos,embryos2);
            }
        }
        newEmbryosOnMesh();
    }

//    public void MooreNeighbourhood(int i, int j){
//        Map<Color, Integer> map = new HashMap<>();
//        Color color;
//        int count = 1;
//        {
//            if (embryos[i][j].getState() == 0) {
//                if (embryos[i][j + 1].getState() == 1) {
//                    if(!embryos[i][j + 1].getColor().equals(Color.WHITE)) {
//                        if (map.containsKey(embryos[i][j + 1].getColor())) {
//                            map.put(embryos[i][j + 1].getColor(), count + 1);
//                        } else {
//                            map.put(embryos[i][j + 1].getColor(), count);
//                        }
//                    }
//                    embryos2[i][j].setState(1);
//                    embryos2[i][j].setColor(embryos[i][j + 1].getColor());
//                }
//
//                if (embryos[i + 1][j].getState() == 1) {
//                    if(!embryos[i + 1][j].getColor().equals(Color.WHITE)) {
//                        if (map.containsKey(embryos[i + 1][j].getColor())) {
//                            map.put(embryos[i + 1][j].getColor(), count + 1);
//                        } else {
//                            map.put(embryos[i + 1][j].getColor(), count);
//                        }
//                    }
//                    embryos2[i][j].setState(1);
//                    embryos2[i][j].setColor(embryos[i + 1][j].getColor());
//                }
//
//                if (embryos[i][j - 1].getState() == 1) {
//                    if(!embryos[i][j - 1].getColor().equals(Color.WHITE)) {
//                        if (map.containsKey(embryos[i][j - 1].getColor())) {
//                            map.put(embryos[i][j - 1].getColor(), count + 1);
//                        } else {
//                            map.put(embryos[i][j - 1].getColor(), count);
//                        }
//                    }
//                    embryos2[i][j].setState(1);
//                    embryos2[i][j].setColor(embryos[i][j - 1].getColor());
//                }
//
//                if (embryos[i - 1][j].getState() == 1) {
//                    if(!embryos[i - 1][j].getColor().equals(Color.WHITE)) {
//                        if (map.containsKey(embryos[i - 1][j].getColor())) {
//                            map.put(embryos[i - 1][j].getColor(), count + 1);
//                        } else {
//                            map.put(embryos[i - 1][j].getColor(), count);
//                        }
//                    }
//                    embryos2[i][j].setState(1);
//                    embryos2[i][j].setColor(embryos[i - 1][j].getColor());
//                }
//                if (embryos[i - 1][j + 1].getState() == 1) {
//                    if(!embryos[i - 1][j + 1].getColor().equals(Color.WHITE)) {
//                        if (map.containsKey(embryos[i - 1][j + 1].getColor())) {
//                            map.put(embryos[i - 1][j + 1].getColor(), count + 1);
//                        } else {
//                            map.put(embryos[i - 1][j + 1].getColor(), count);
//                        }
//                    }
//                    embryos2[i][j].setState(1);
//                    embryos2[i][j].setColor(embryos[i - 1][j + 1].getColor());
//                }
//
//                if (embryos[i + 1][j + 1].getState() == 1) {
//                    if(!embryos[i + 1][j + 1].getColor().equals(Color.WHITE)) {
//                        if (map.containsKey(embryos[i + 1][j + 1].getColor())) {
//                            map.put(embryos[i + 1][j + 1].getColor(), count + 1);
//                        } else {
//                            map.put(embryos[i + 1][j + 1].getColor(), count);
//                        }
//                    }
//                    embryos2[i][j].setState(1);
//                    embryos2[i][j].setColor(embryos[i + 1][j + 1].getColor());
//                }
//
//                if (embryos[i + 1][j - 1].getState() == 1) {
//                    if(!embryos[i + 1][j - 1].getColor().equals(Color.WHITE)) {
//                        if (map.containsKey(embryos[i + 1][j - 1].getColor())) {
//                            map.put(embryos[i + 1][j - 1].getColor(), count + 1);
//                        } else {
//                            map.put(embryos[i + 1][j - 1].getColor(), count);
//                        }
//                    }
//                    embryos2[i][j].setState(1);
//                    embryos2[i][j].setColor(embryos[i + 1][j - 1].getColor());
//                }
//
//                if (embryos[i - 1][j - 1].getState() == 1) {
//                    if(!embryos[i - 1][j - 1].getColor().equals(Color.WHITE)) {
//                        if (map.containsKey(embryos[i - 1][j - 1].getColor())) {
//                            map.put(embryos[i - 1][j - 1].getColor(), count + 1);
//                        } else {
//                            map.put(embryos[i - 1][j - 1].getColor(), count);
//                        }
//                    }
//                    embryos2[i][j].setState(1);
//                    embryos2[i][j].setColor(embryos[i - 1][j - 1].getColor());
//                }
//
//            } else {
//                embryos2[i][j].setState(1);
//                embryos2[i][j].setColor(embryos[i][j].getColor());
//            }
//        }
//        if(map.size()>0){
//            Map.Entry<Color,Integer> maxEntry = null;
//            for(Map.Entry<Color,Integer> entry : map.entrySet()){
//                if(maxEntry == null || entry.getValue().compareTo(maxEntry.getValue()) >0){
//                    maxEntry=entry;
//                }
//            }
//            color= maxEntry.getKey();
//            embryos2[i][j].setState(1);
//            embryos2[i][j].setColor(color);
//        }
//    }

    public void MooreNeighbourhoodPeriodic(){
        for (int i = 1; i < height-1; i++) {
            for (int j = 1; j < width - 1; j++) {
                periodic(i,j);
                moore.MooreNeighbourhood(i,j,embryos,embryos2);
            }
        }
        newEmbryosOnMesh();
    }

    public void MooreNeighbourhoodWithoutPeriodic(){
        for (int i = 1; i < height-1; i++) {
            for (int j = 1; j < width - 1; j++) {
                moore.MooreNeighbourhood(i,j,embryos,embryos2);
            }
        }
        newEmbryosOnMesh();
    }

//    public void HexagonalLeftNeigbourhood(int i, int j){
//        Map<Color, Integer> map = new HashMap<>();
//        Color color;
//        int count = 1;
//        {
//            if (embryos[i][j].getState() == 0) {
//                if (embryos[i][j + 1].getState() == 1) {
//                    if(!embryos[i][j + 1].getColor().equals(Color.WHITE)) {
//                        if (map.containsKey(embryos[i][j + 1].getColor())) {
//                            map.put(embryos[i][j + 1].getColor(), count + 1);
//                        } else {
//                            map.put(embryos[i][j + 1].getColor(), count);
//                        }
//                    }
//                    embryos2[i][j].setState(1);
//                    embryos2[i][j].setColor(embryos[i][j + 1].getColor());
//                }
//
//                if (embryos[i + 1][j].getState() == 1) {
//                    if(!embryos[i + 1][j].getColor().equals(Color.WHITE)) {
//                        if (map.containsKey(embryos[i + 1][j].getColor())) {
//                            map.put(embryos[i + 1][j].getColor(), count + 1);
//                        } else {
//                            map.put(embryos[i + 1][j].getColor(), count);
//                        }
//                    }
//                    embryos2[i][j].setState(1);
//                    embryos2[i][j].setColor(embryos[i + 1][j].getColor());
//                }
//
//                if (embryos[i][j - 1].getState() == 1) {
//                    if(!embryos[i][j - 1].getColor().equals(Color.WHITE)) {
//                        if (map.containsKey(embryos[i][j - 1].getColor())) {
//                            map.put(embryos[i][j - 1].getColor(), count + 1);
//                        } else {
//                            map.put(embryos[i][j - 1].getColor(), count);
//                        }
//                    }
//                    embryos2[i][j].setState(1);
//                    embryos2[i][j].setColor(embryos[i][j - 1].getColor());
//                }
//
//                if (embryos[i - 1][j].getState() == 1) {
//                    if(!embryos[i - 1][j].getColor().equals(Color.WHITE)) {
//                        if (map.containsKey(embryos[i - 1][j].getColor())) {
//                            map.put(embryos[i - 1][j].getColor(), count + 1);
//                        } else {
//                            map.put(embryos[i - 1][j].getColor(), count);
//                        }
//                    }
//                    embryos2[i][j].setState(1);
//                    embryos2[i][j].setColor(embryos[i - 1][j].getColor());
//                }
//                if (embryos[i + 1][j + 1].getState() == 1) {
//                    if (!embryos[i + 1][j + 1].getColor().equals(Color.WHITE)) {
//                        if (map.containsKey(embryos[i + 1][j + 1].getColor())) {
//                            map.put(embryos[i + 1][j + 1].getColor(), count + 1);
//                        } else {
//                            map.put(embryos[i + 1][j + 1].getColor(), count);
//                        }
//                    }
//                    embryos2[i][j].setState(1);
//                    embryos2[i][j].setColor(embryos[i + 1][j + 1].getColor());
//                }
//                if (embryos[i - 1][j - 1].getState() == 1) {
//                    if (!embryos[i - 1][j - 1].getColor().equals(Color.WHITE)) {
//                        if (map.containsKey(embryos[i - 1][j - 1].getColor())) {
//                            map.put(embryos[i - 1][j - 1].getColor(), count + 1);
//                        } else {
//                            map.put(embryos[i - 1][j - 1].getColor(), count);
//                        }
//                    }
//                    embryos2[i][j].setState(1);
//                    embryos2[i][j].setColor(embryos[i - 1][j - 1].getColor());
//                }
//
//            } else {
//                embryos2[i][j].setState(1);
//                embryos2[i][j].setColor(embryos[i][j].getColor());
//            }
//        }
//        if(map.size()>0){
//            Map.Entry<Color,Integer> maxEntry = null;
//            for(Map.Entry<Color,Integer> entry : map.entrySet()){
//                if(maxEntry == null || entry.getValue().compareTo(maxEntry.getValue()) >0){
//                    maxEntry=entry;
//                }
//            }
//            color= maxEntry.getKey();
//            embryos2[i][j].setState(1);
//            embryos2[i][j].setColor(color);
//        }
//    }

    public void HexagonalLeftNeighbourhoodPeriodic(){
        for (int i = 1; i < height-1; i++) {
            for (int j = 1; j < width - 1; j++) {
                periodic(i,j);
                hexagonalLeft.HexagonalLeftNeigbourhood(i,j,embryos,embryos2);
            }
        }
        newEmbryosOnMesh();
    }
    public void HexagonalLeftNeighbourhoodWithoutPeriodic(){
        for (int i = 1; i < height-1; i++) {
            for (int j = 1; j < width - 1; j++) {
                hexagonalLeft.HexagonalLeftNeigbourhood(i,j,embryos,embryos2);
            }
        }
        newEmbryosOnMesh();
    }

//    public void HexagonalRightNeigbourhood(int i, int j){
//        Map<Color, Integer> map = new HashMap<>();
//        Color color;
//        int count = 1;
//        {
//            if (embryos[i][j].getState() == 0) {
//                if (embryos[i][j + 1].getState() == 1) {
//                    if(!embryos[i][j + 1].getColor().equals(Color.WHITE)) {
//                        if (map.containsKey(embryos[i][j + 1].getColor())) {
//                            map.put(embryos[i][j + 1].getColor(), count + 1);
//                        } else {
//                            map.put(embryos[i][j + 1].getColor(), count);
//                        }
//                    }
//                    embryos2[i][j].setState(1);
//                    embryos2[i][j].setColor(embryos[i][j + 1].getColor());
//                }
//
//                if (embryos[i + 1][j].getState() == 1) {
//                    if(!embryos[i + 1][j].getColor().equals(Color.WHITE)) {
//                        if (map.containsKey(embryos[i + 1][j].getColor())) {
//                            map.put(embryos[i + 1][j].getColor(), count + 1);
//                        } else {
//                            map.put(embryos[i + 1][j].getColor(), count);
//                        }
//                    }
//                    embryos2[i][j].setState(1);
//                    embryos2[i][j].setColor(embryos[i + 1][j].getColor());
//                }
//
//                if (embryos[i][j - 1].getState() == 1) {
//                    if(!embryos[i][j - 1].getColor().equals(Color.WHITE)) {
//                        if (map.containsKey(embryos[i][j - 1].getColor())) {
//                            map.put(embryos[i][j - 1].getColor(), count + 1);
//                        } else {
//                            map.put(embryos[i][j - 1].getColor(), count);
//                        }
//                    }
//                    embryos2[i][j].setState(1);
//                    embryos2[i][j].setColor(embryos[i][j - 1].getColor());
//                }
//
//                if (embryos[i - 1][j].getState() == 1) {
//                    if(!embryos[i - 1][j].getColor().equals(Color.WHITE)) {
//                        if (map.containsKey(embryos[i - 1][j].getColor())) {
//                            map.put(embryos[i - 1][j].getColor(), count + 1);
//                        } else {
//                            map.put(embryos[i - 1][j].getColor(), count);
//                        }
//                    }
//                    embryos2[i][j].setState(1);
//                    embryos2[i][j].setColor(embryos[i - 1][j].getColor());
//                }
//
//                if (embryos[i - 1][j + 1].getState() == 1) {
//                    if (!embryos[i - 1][j + 1].getColor().equals(Color.WHITE)) {
//                        if (map.containsKey(embryos[i - 1][j + 1].getColor())) {
//                            map.put(embryos[i - 1][j + 1].getColor(), count + 1);
//                        } else {
//                            map.put(embryos[i - 1][j + 1].getColor(), count);
//                        }
//                    }
//                    embryos2[i][j].setState(1);
//                    embryos2[i][j].setColor(embryos[i - 1][j + 1].getColor());
//                }
//                if (embryos[i + 1][j - 1].getState() == 1) {
//                    if (!embryos[i + 1][j - 1].getColor().equals(Color.WHITE)) {
//                        if (map.containsKey(embryos[i + 1][j - 1].getColor())) {
//                            map.put(embryos[i + 1][j - 1].getColor(), count + 1);
//                        } else {
//                            map.put(embryos[i + 1][j - 1].getColor(), count);
//                        }
//                    }
//                    embryos2[i][j].setState(1);
//                    embryos2[i][j].setColor(embryos[i + 1][j - 1].getColor());
//                }
//
//            } else {
//                embryos2[i][j].setState(1);
//                embryos2[i][j].setColor(embryos[i][j].getColor());
//            }
//        }
//        if(map.size()>0){
//            Map.Entry<Color,Integer> maxEntry = null;
//            for(Map.Entry<Color,Integer> entry : map.entrySet()){
//                if(maxEntry == null || entry.getValue().compareTo(maxEntry.getValue()) >0){
//                    maxEntry=entry;
//                }
//            }
//            color= maxEntry.getKey();
//            embryos2[i][j].setState(1);
//            embryos2[i][j].setColor(color);
//        }
//    }

    public void HexagonalRightNeighbourhoodPeriodic(){
        for (int i = 1; i < height-1; i++) {
            for (int j = 1; j < width - 1; j++) {
                periodic(i,j);
                hexagonalRight.HexagonalRightNeigbourhood(i,j,embryos,embryos2);
            }
        }
        newEmbryosOnMesh();
    }
    public void HexagonalRightNeighbourhoodWithoutPeriodic(){
        for (int i = 1; i < height-1; i++) {
            for (int j = 1; j < width - 1; j++) {
                hexagonalRight.HexagonalRightNeigbourhood(i,j,embryos,embryos2);
            }
        }
        newEmbryosOnMesh();
    }

    public void HexagonalRandomWithoutPeriodic(){
        for (int i = 1; i < height-1; i++) {
            for (int j = 1; j < width - 1; j++) {
                Random rand = new Random();
                if (0 == rand.nextInt(2)) {
                    hexagonalLeft.HexagonalLeftNeigbourhood(i,j,embryos,embryos2);
                } else {
                    hexagonalRight.HexagonalRightNeigbourhood(i,j,embryos,embryos2);
                }

            }
        }
        newEmbryosOnMesh();

        //STARA WERSJA
//        Random rand = new Random();
//        if (0 == rand.nextInt(2)) {
//            HexagonalLeftNeighbourhoodWithoutPeriodic();
//        } else {
//            HexagonalRightNeighbourhoodWithoutPeriodic();
//        }


    }

    public void HexagonalRandomPeriodic(){
        for (int i = 1; i < height-1; i++) {
            for (int j = 1; j < width - 1; j++) {
                Random rand = new Random();
                if (0 == rand.nextInt(2)) {
                    periodic(i,j);
                    hexagonalLeft.HexagonalLeftNeigbourhood(i,j,embryos,embryos2);
                } else {
                    periodic(i,j);
                    hexagonalRight.HexagonalRightNeigbourhood(i,j,embryos,embryos2);
                }
            }
        }
        newEmbryosOnMesh();

        //STARA WERSJA
//        Random rand = new Random();
//        System.out.println(rand.nextInt(2));
//        if (0 == rand.nextInt(2)) {
//            HexagonalLeftNeighbourhoodPeriodic();
//        } else {
//            HexagonalRightNeighbourhoodPeriodic();
//        }
    }

//    public void PentagonalLeftNeigbourhood(int i, int j){
//        Map<Color, Integer> map = new HashMap<>();
//        Color color;
//        int count = 1;
//        {
//            if (embryos[i][j].getState() == 0) {
//                if (embryos[i][j + 1].getState() == 1) {
//                    if (!embryos[i][j + 1].getColor().equals(Color.WHITE)) {
//                        if (map.containsKey(embryos[i][j + 1].getColor())) {
//                            map.put(embryos[i][j + 1].getColor(), count + 1);
//                        } else {
//                            map.put(embryos[i][j + 1].getColor(), count);
//                        }
//                    }
//                    embryos2[i][j].setState(1);
//                    embryos2[i][j].setColor(embryos[i][j + 1].getColor());
//                }
//                if (embryos[i - 1][j + 1].getState() == 1) {
//                    if (!embryos[i - 1][j + 1].getColor().equals(Color.WHITE)) {
//                        if (map.containsKey(embryos[i - 1][j + 1].getColor())) {
//                            map.put(embryos[i - 1][j + 1].getColor(), count + 1);
//                        } else {
//                            map.put(embryos[i - 1][j + 1].getColor(), count);
//                        }
//                    }
//                    embryos2[i][j].setState(1);
//                    embryos2[i][j].setColor(embryos[i - 1][j + 1].getColor());
//                }
//                if (embryos[i - 1][j].getState() == 1) {
//                    if (!embryos[i - 1][j].getColor().equals(Color.WHITE)) {
//                        if (map.containsKey(embryos[i - 1][j].getColor())) {
//                            map.put(embryos[i - 1][j].getColor(), count + 1);
//                        } else {
//                            map.put(embryos[i - 1][j].getColor(), count);
//                        }
//                    }
//                    embryos2[i][j].setState(1);
//                    embryos2[i][j].setColor(embryos[i - 1][j].getColor());
//                }
//                if (embryos[i - 1][j - 1].getState() == 1) {
//                    if (!embryos[i - 1][j - 1].getColor().equals(Color.WHITE)) {
//                        if (map.containsKey(embryos[i - 1][j - 1].getColor())) {
//                            map.put(embryos[i - 1][j - 1].getColor(), count + 1);
//                        } else {
//                            map.put(embryos[i - 1][j - 1].getColor(), count);
//                        }
//                    }
//                    embryos2[i][j].setState(1);
//                    embryos2[i][j].setColor(embryos[i - 1][j - 1].getColor());
//                }
//                if (embryos[i][j - 1].getState() == 1) {
//                    if (!embryos[i][j - 1].getColor().equals(Color.WHITE)) {
//                        if (map.containsKey(embryos[i][j - 1].getColor())) {
//                            map.put(embryos[i][j - 1].getColor(), count + 1);
//                        } else {
//                            map.put(embryos[i][j - 1].getColor(), count);
//                        }
//                    }
//                    embryos2[i][j].setState(1);
//                    embryos2[i][j].setColor(embryos[i][j - 1].getColor());
//                }
//
//            } else {
//                embryos2[i][j].setState(1);
//                embryos2[i][j].setColor(embryos[i][j].getColor());
//            }
//        }
//        if(map.size()>0){
//            Map.Entry<Color,Integer> maxEntry = null;
//            for(Map.Entry<Color,Integer> entry : map.entrySet()){
//                if(maxEntry == null || entry.getValue().compareTo(maxEntry.getValue()) >0){
//                    maxEntry=entry;
//                }
//            }
//            color= maxEntry.getKey();
//            embryos2[i][j].setState(1);
//            embryos2[i][j].setColor(color);
//        }
//    }

    public void PentagonalLeftNeighbourhoodPeriodic(){
        for (int i = 1; i < height-1; i++) {
            for (int j = 1; j < width - 1; j++) {
                periodic(i,j);
                pentagonal.PentagonalLeftNeigbourhood(i,j,embryos,embryos2);
            }
        }
        newEmbryosOnMesh();
    }
    public void PentagonalLeftNeighbourhoodWithoutPeriodic(){
        for (int i = 1; i < height-1; i++) {
            for (int j = 1; j < width - 1; j++) {
                pentagonal.PentagonalLeftNeigbourhood(i,j,embryos,embryos2);
            }
        }
        newEmbryosOnMesh();
    }

//    public void PentagonalRightNeigbourhood(int i, int j){
//        Map<Color, Integer> map = new HashMap<>();
//        Color color;
//        int count = 1;
//        {
//            if (embryos[i][j].getState() == 0) {
//                if (embryos[i][j - 1].getState() == 1) {
//                    if (!embryos[i][j - 1].getColor().equals(Color.WHITE)) {
//                        if (map.containsKey(embryos[i][j - 1].getColor())) {
//                            map.put(embryos[i][j - 1].getColor(), count + 1);
//                        } else {
//                            map.put(embryos[i][j - 1].getColor(), count);
//                        }
//                    }
//                    embryos2[i][j].setState(1);
//                    embryos2[i][j].setColor(embryos[i][j - 1].getColor());
//                }
//                if (embryos[i + 1][j - 1].getState() == 1) {
//                    if (!embryos[i + 1][j - 1].getColor().equals(Color.WHITE)) {
//                        if (map.containsKey(embryos[i + 1][j - 1].getColor())) {
//                            map.put(embryos[i + 1][j - 1].getColor(), count + 1);
//                        } else {
//                            map.put(embryos[i + 1][j - 1].getColor(), count);
//                        }
//                    }
//                    embryos2[i][j].setState(1);
//                    embryos2[i][j].setColor(embryos[i + 1][j - 1].getColor());
//                }
//                if (embryos[i + 1][j].getState() == 1) {
//                    if (!embryos[i + 1][j].getColor().equals(Color.WHITE)) {
//                        if (map.containsKey(embryos[i + 1][j].getColor())) {
//                            map.put(embryos[i + 1][j].getColor(), count + 1);
//                        } else {
//                            map.put(embryos[i + 1][j].getColor(), count);
//                        }
//                    }
//                    embryos2[i][j].setState(1);
//                    embryos2[i][j].setColor(embryos[i + 1][j].getColor());
//                }
//                if (embryos[i + 1][j + 1].getState() == 1) {
//                    if (!embryos[i + 1][j + 1].getColor().equals(Color.WHITE)) {
//                        if (map.containsKey(embryos[i + 1][j + 1].getColor())) {
//                            map.put(embryos[i + 1][j + 1].getColor(), count + 1);
//                        } else {
//                            map.put(embryos[i + 1][j + 1].getColor(), count);
//                        }
//                    }
//                    embryos2[i][j].setState(1);
//                    embryos2[i][j].setColor(embryos[i + 1][j + 1].getColor());
//                }
//                if (embryos[i][j + 1].getState() == 1) {
//                    if (!embryos[i][j + 1].getColor().equals(Color.WHITE)) {
//                        if (map.containsKey(embryos[i][j + 1].getColor())) {
//                            map.put(embryos[i][j + 1].getColor(), count + 1);
//                        } else {
//                            map.put(embryos[i][j + 1].getColor(), count);
//                        }
//                    }
//                    embryos2[i][j].setState(1);
//                    embryos2[i][j].setColor(embryos[i][j + 1].getColor());
//                }
//
//            } else {
//                embryos2[i][j].setState(1);
//                embryos2[i][j].setColor(embryos[i][j].getColor());
//            }
//        }
//        if(map.size()>0){
//            Map.Entry<Color,Integer> maxEntry = null;
//            for(Map.Entry<Color,Integer> entry : map.entrySet()){
//                if(maxEntry == null || entry.getValue().compareTo(maxEntry.getValue()) >0){
//                    maxEntry=entry;
//                }
//            }
//            color= maxEntry.getKey();
//            embryos2[i][j].setState(1);
//            embryos2[i][j].setColor(color);
//        }
//    }

    public void PentagonalRightNeighbourhoodPeriodic(){
        for (int i = 1; i < height-1; i++) {
            for (int j = 1; j < width - 1; j++) {
                periodic(i,j);
                pentagonal.PentagonalRightNeigbourhood(i,j,embryos,embryos2);
            }
        }
        newEmbryosOnMesh();
    }
    public void PentagonalRightNeighbourhoodWithoutPeriodic(){
        for (int i = 1; i < height-1; i++) {
            for (int j = 1; j < width - 1; j++) {
                pentagonal.PentagonalRightNeigbourhood(i,j,embryos,embryos2);
            }
        }
        newEmbryosOnMesh();
    }

//    public void PentagonalTopNeigbourhood(int i, int j){
//        Map<Color, Integer> map = new HashMap<>();
//        Color color;
//        int count = 1;
//        {
//            if (embryos[i][j].getState() == 0) {
//
//                if (embryos[i - 1][j].getState() == 1) {
//                    if (!embryos[i - 1][j].getColor().equals(Color.WHITE)) {
//                        if (map.containsKey(embryos[i - 1][j].getColor())) {
//                            map.put(embryos[i - 1][j].getColor(), count + 1);
//                        } else {
//                            map.put(embryos[i - 1][j].getColor(), count);
//                        }
//                    }
//                    embryos2[i][j].setState(1);
//                    embryos2[i][j].setColor(embryos[i - 1][j].getColor());
//                }
//                if (embryos[i - 1][j + 1].getState() == 1) {
//                    if (!embryos[i - 1][j + 1].getColor().equals(Color.WHITE)) {
//                        if (map.containsKey(embryos[i - 1][j + 1].getColor())) {
//                            map.put(embryos[i - 1][j + 1].getColor(), count + 1);
//                        } else {
//                            map.put(embryos[i - 1][j + 1].getColor(), count);
//                        }
//                    }
//                    embryos2[i][j].setState(1);
//                    embryos2[i][j].setColor(embryos[i - 1][j + 1].getColor());
//                }
//                if (embryos[i][j + 1].getState() == 1) {
//                    if (!embryos[i][j + 1].getColor().equals(Color.WHITE)) {
//                        if (map.containsKey(embryos[i][j + 1].getColor())) {
//                            map.put(embryos[i][j + 1].getColor(), count + 1);
//                        } else {
//                            map.put(embryos[i][j + 1].getColor(), count);
//                        }
//                    }
//                    embryos2[i][j].setState(1);
//                    embryos2[i][j].setColor(embryos[i][j + 1].getColor());
//                }
//                if (embryos[i + 1][j + 1].getState() == 1) {
//                    if (!embryos[i + 1][j + 1].getColor().equals(Color.WHITE)) {
//                        if (map.containsKey(embryos[i + 1][j + 1].getColor())) {
//                            map.put(embryos[i + 1][j + 1].getColor(), count + 1);
//                        } else {
//                            map.put(embryos[i + 1][j + 1].getColor(), count);
//                        }
//                    }
//                    embryos2[i][j].setState(1);
//                    embryos2[i][j].setColor(embryos[i + 1][j + 1].getColor());
//                }
//                if (embryos[i + 1][j].getState() == 1) {
//                    if (!embryos[i + 1][j].getColor().equals(Color.WHITE)) {
//                        if (map.containsKey(embryos[i + 1][j].getColor())) {
//                            map.put(embryos[i + 1][j].getColor(), count + 1);
//                        } else {
//                            map.put(embryos[i + 1][j].getColor(), count);
//                        }
//                    }
//                    embryos2[i][j].setState(1);
//                    embryos2[i][j].setColor(embryos[i + 1][j].getColor());
//                }
//
//            } else {
//                embryos2[i][j].setState(1);
//                embryos2[i][j].setColor(embryos[i][j].getColor());
//            }
//        }
//        if(map.size()>0){
//            Map.Entry<Color,Integer> maxEntry = null;
//            for(Map.Entry<Color,Integer> entry : map.entrySet()){
//                if(maxEntry == null || entry.getValue().compareTo(maxEntry.getValue()) >0){
//                    maxEntry=entry;
//                }
//            }
//            color= maxEntry.getKey();
//            embryos2[i][j].setState(1);
//            embryos2[i][j].setColor(color);
//        }
//    }

    public void PentagonalTopNeighbourhoodPeriodic(){
        for (int i = 1; i < height-1; i++) {
            for (int j = 1; j < width - 1; j++) {
                periodic(i,j);
                pentagonal.PentagonalTopNeigbourhood(i,j,embryos,embryos2);
            }
        }
        newEmbryosOnMesh();
    }
    public void PentagonalTopNeighbourhoodWithoutPeriodic(){
        for (int i = 1; i < height-1; i++) {
            for (int j = 1; j < width - 1; j++) {
                pentagonal.PentagonalTopNeigbourhood(i,j,embryos,embryos2);
            }
        }
        newEmbryosOnMesh();
    }

//    public void PentagonalBottomNeigbourhood(int i, int j){
//        Map<Color, Integer> map = new HashMap<>();
//        Color color;
//        int count = 1;
//        {
//            if (embryos[i][j].getState() == 0) {
//                if (embryos[i + 1][j].getState() == 1) {
//                    if (!embryos[i + 1][j].getColor().equals(Color.WHITE)) {
//                        if (map.containsKey(embryos[i + 1][j].getColor())) {
//                            map.put(embryos[i + 1][j].getColor(), count + 1);
//                        } else {
//                            map.put(embryos[i + 1][j].getColor(), count);
//                        }
//                    }
//                    embryos2[i][j].setState(1);
//                    embryos2[i][j].setColor(embryos[i + 1][j].getColor());
//                }
//                if (embryos[i + 1][j - 1].getState() == 1) {
//                    if (!embryos[i + 1][j - 1].getColor().equals(Color.WHITE)) {
//                        if (map.containsKey(embryos[i + 1][j - 1].getColor())) {
//                            map.put(embryos[i + 1][j - 1].getColor(), count + 1);
//                        } else {
//                            map.put(embryos[i + 1][j - 1].getColor(), count);
//                        }
//                    }
//                    embryos2[i][j].setState(1);
//                    embryos2[i][j].setColor(embryos[i + 1][j - 1].getColor());
//                }
//                if (embryos[i][j - 1].getState() == 1) {
//                    if (!embryos[i][j - 1].getColor().equals(Color.WHITE)) {
//                        if (map.containsKey(embryos[i][j - 1].getColor())) {
//                            map.put(embryos[i][j - 1].getColor(), count + 1);
//                        } else {
//                            map.put(embryos[i][j - 1].getColor(), count);
//                        }
//                    }
//                    embryos2[i][j].setState(1);
//                    embryos2[i][j].setColor(embryos[i][j - 1].getColor());
//                }
//                if (embryos[i - 1][j - 1].getState() == 1) {
//                    if (!embryos[i - 1][j - 1].getColor().equals(Color.WHITE)) {
//                        if (map.containsKey(embryos[i - 1][j - 1].getColor())) {
//                            map.put(embryos[i - 1][j - 1].getColor(), count + 1);
//                        } else {
//                            map.put(embryos[i - 1][j - 1].getColor(), count);
//                        }
//                    }
//                    embryos2[i][j].setState(1);
//                    embryos2[i][j].setColor(embryos[i - 1][j - 1].getColor());
//                }
//                if (embryos[i - 1][j].getState() == 1) {
//                    if (!embryos[i - 1][j].getColor().equals(Color.WHITE)) {
//                        if (map.containsKey(embryos[i - 1][j].getColor())) {
//                            map.put(embryos[i - 1][j].getColor(), count + 1);
//                        } else {
//                            map.put(embryos[i - 1][j].getColor(), count);
//                        }
//                    }
//                    embryos2[i][j].setState(1);
//                    embryos2[i][j].setColor(embryos[i - 1][j].getColor());
//                }
//
//            } else {
//                embryos2[i][j].setState(1);
//                embryos2[i][j].setColor(embryos[i][j].getColor());
//            }
//        }
//        if(map.size()>0){
//            Map.Entry<Color,Integer> maxEntry = null;
//            for(Map.Entry<Color,Integer> entry : map.entrySet()){
//                if(maxEntry == null || entry.getValue().compareTo(maxEntry.getValue()) >0){
//                    maxEntry=entry;
//                }
//            }
//            color= maxEntry.getKey();
//            embryos2[i][j].setState(1);
//            embryos2[i][j].setColor(color);
//        }
//    }

    public void PentagonalBottomNeighbourhoodPeriodic(){
        for (int i = 1; i < height-1; i++) {
            for (int j = 1; j < width - 1; j++) {
                periodic(i,j);
                pentagonal.PentagonalBottomNeigbourhood(i,j,embryos,embryos2);
            }
        }
        newEmbryosOnMesh();
    }
    public void PentagonalBottomNeighbourhoodWithoutPeriodic(){
        for (int i = 1; i < height-1; i++) {
            for (int j = 1; j < width - 1; j++) {
                pentagonal.PentagonalBottomNeigbourhood(i,j,embryos,embryos2);
            }
        }
        newEmbryosOnMesh();
    }

    public void PentagonalRandomWithoutPeriodic(){
        for (int i = 1; i < height-1; i++) {
            for (int j = 1; j < width - 1; j++) {
                Random rand = new Random();
                if (0 == rand.nextInt(4)) {
                    pentagonal.PentagonalLeftNeigbourhood(i,j,embryos,embryos2);
                }
                else if(1 == rand.nextInt(4)){
                    pentagonal.PentagonalRightNeigbourhood(i,j,embryos,embryos2);
                }
                else if(2 == rand.nextInt(4)) {
                    pentagonal.PentagonalTopNeigbourhood(i,j,embryos,embryos2);
                }
                else if(3 == rand.nextInt(4)){
                    pentagonal.PentagonalBottomNeigbourhood(i,j,embryos,embryos2);
                }

            }
        }
        newEmbryosOnMesh();

        //STARA WERSJA
//        Random rand = new Random();
//        if (0 == rand.nextInt(4)) {
//           PentagonalLeftNeighbourhoodWithoutPeriodic();
//        }
//        else if(1 == rand.nextInt(4)){
//            PentagonalRightNeighbourhoodWithoutPeriodic();
//        }
//        else if(2 == rand.nextInt(4)){
//            PentagonalBottomNeighbourhoodWithoutPeriodic();
//        }
//        else if(3 == rand.nextInt(4)){
//            PentagonalTopNeighbourhoodWithoutPeriodic();
//        }
    }

    public void PentagonalRandomPeriodic(){

        for (int i = 1; i < height-1; i++) {
            for (int j = 1; j < width - 1; j++) {
                Random rand = new Random();
                int x = rand.nextInt(4);
                if(x==0){
                    periodic(i,j);
                        pentagonal.PentagonalLeftNeigbourhood(i,j,embryos,embryos2);
                }
               else if(x==1){
                    periodic(i,j);
                        pentagonal.PentagonalRightNeigbourhood(i,j,embryos,embryos2);
                }
                else if(x==2){
                    periodic(i,j);
                        pentagonal.PentagonalTopNeigbourhood(i,j,embryos,embryos2);
                }
               else if(x==3){
                    periodic(i,j);
                        pentagonal.PentagonalBottomNeigbourhood(i,j,embryos,embryos2);
                }
            }
        }
        newEmbryosOnMesh();


//        for (int i = 1; i < height-1; i++) {
//            for (int j = 1; j < width - 1; j++) {
//                Random rand = new Random();
//                if (0 == rand.nextInt(4)) {
//                    periodic(i,j);
//                    pentagonal.PentagonalLeftNeigbourhood(i,j,embryos,embryos2);
//                }
//                else if(1 == rand.nextInt(4)){
//                    periodic(i,j);
//                    pentagonal.PentagonalRightNeigbourhood(i,j,embryos,embryos2);
//                }
//                else if(2 == rand.nextInt(4)) {
//                    periodic(i,j);
//                    pentagonal.PentagonalTopNeigbourhood(i,j,embryos,embryos2);
//                }
//                else if(3 == rand.nextInt(4)){
//                    periodic(i,j);
//                    pentagonal.PentagonalBottomNeigbourhood(i,j,embryos,embryos2);
//                }
//
//            }
//        }
//        newEmbryosOnMesh();

        //STARA WERSJA
//        Random rand = new Random();
//    //    System.out.println(rand.nextInt(4));
//        if (0 == rand.nextInt(4)) {
//            PentagonalLeftNeighbourhoodPeriodic();
//        }
//        else if(1 == rand.nextInt(4)){
//            PentagonalRightNeighbourhoodPeriodic();
//        }
//        else if(2 == rand.nextInt(4)){
//            PentagonalBottomNeighbourhoodPeriodic();
//        }
//        else if(3 == rand.nextInt(4)){
//            PentagonalTopNeighbourhoodPeriodic();
//        }
    }

    private void newEmbryosOnMesh() {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                embryos[i][j].setState(embryos2[i][j].getState());
                embryos[i][j].setColor(embryos2[i][j].getColor());
                embryos2[i][j].setState(0);
            }
        }
    }

    public Embryo[][] getEmbryos() {
        return embryos;
    }

    public void setEmbryos(int x, int y, int state) {
        embryos[y][x].setState(state);
    }

    public void setPeriodic(boolean periodic) {
        this.periodic = periodic;
    }
}
