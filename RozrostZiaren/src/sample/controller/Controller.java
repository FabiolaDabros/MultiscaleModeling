package sample.controller;

import javafx.animation.KeyFrame;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import sample.model.Simulator;
import sample.model.Model;

import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;


public class Controller implements Initializable {

    @FXML
    Button btnRand;
    @FXML
    Button btnClear;
    @FXML
    Button btnStart;
    @FXML
    TextField heightText;
    @FXML
    TextField widthText;
    @FXML
    TextField numberCellsText;
//    @FXML
//    TextField radiousText;
    @FXML
    Canvas canvas;
    @FXML
    ComboBox neighborsCombo;
    @FXML
    ComboBox pointsCombo;
    @FXML
    Button btnStop;
//    @FXML
//    Button morePoints;
    @FXML
    RadioButton periodicBtn;

    private Model model;
    public GraphicsContext gc;
    private int height;
    private int width;
    private int numberOfCells;
    private int radiousSize;
    private int embryoSize = 1;
    private int speed = 50;
    private Random randomGenerator;
    private Simulator task;
    private boolean startLoopFlag;
    private int canvasHeight;
    private int canvasWeight;
    private int counter = 0;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // tymczasowo
        pointsCombo.setVisible(false);
//        morePoints.setDisable(true);
        btnStart.setDisable(true);
        btnStop.setDisable(true);
        btnClear.setDisable(true);
        gc = canvas.getGraphicsContext2D();
        numberOfCells = Integer.parseInt(this.numberCellsText.getText());
//        radiousSize = Integer.parseInt(this.radiousText.getText());
        startLoopFlag = false;
        randomGenerator = new Random();
        canvas.setStyle("-fx-background-color: white");
//        neighborsCombo.getItems().addAll("von Neumann", "Moore'a", "Hexalosowe", "Pentalosowe",
//                "Hexagonalne (lewe)", "Hexagonalne (prawe)");
        neighborsCombo.setValue("von Neumann");
//        pointsCombo.getItems().addAll("Losowe", "Rownomierne", "Promień");
        pointsCombo.setValue("Losowe");


        final Tooltip tooltipNucleons = new Tooltip();
        tooltipNucleons.setText("WSKAZÓWKA!\n Liczba zarodków nie może  \nprzekraczać wielkości siatki ");
        numberCellsText.setTooltip(tooltipNucleons);
        final Tooltip tooltipWidth = new Tooltip();
        tooltipWidth.setText("WSKAZÓWKA!\n Nalezy podać wartość całkowitą ");
        widthText.setTooltip(tooltipWidth);
        final Tooltip tooltipHeight = new Tooltip();
        tooltipHeight.setText("WSKAZÓWKA!\n Nalezy podać wartość całkowitą ");
        heightText.setTooltip(tooltipHeight);
        final Tooltip tooltipRadius = new Tooltip();
        tooltipRadius.setText("WSKAZÓWKA!\n Dla dużej liczby zarodków \nwartość promienia powinna być nieduża ");
//        radiousText.setTooltip(tooltipRadius);
    }

    public void randCellAction(){
        if(heightText.getText().contains(",")  || heightText.getText().contains(".")
                || widthText.getText().contains(",")  || widthText.getText().contains(".")
                || numberCellsText.getText().contains(",")  || numberCellsText.getText().contains(".")){
//                || radiousText.getText().contains(",")  || radiousText.getText().contains(".")){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information");
            alert.setHeaderText("Należy podać liczbę całkowitą");
            alert.setContentText(" nieprawidłowy format w textboxie ");
            alert.showAndWait();
            heightText.setText("500");
            widthText.setText("500");
            numberCellsText.setText("100");
//            radiousText.setText("20");
        }
        else {
//            morePoints.setDisable(false);
            btnClear.setDisable(false);
            if (startLoopFlag) btnStart.setDisable(true);
            else btnStart.setDisable(false);
            randomPoints();
        }

    }

    public void startAction(){
       // randCellAction();
        btnStop.setDisable(false);
        btnStart.setDisable(true);
//        morePoints.setDisable(true);
        btnClear.setDisable(true);
        btnRand.setDisable(true);
        startLoopFlag = true;
        task = new Simulator(this);
        task.setStopFlag(false);
        Thread thread = new Thread(task);
        thread.setDaemon(true);
        thread.start();
    }

    public void stopAction(){
        btnStop.setDisable(true);
        btnStart.setDisable(false);
//        morePoints.setDisable(false);
        btnClear.setDisable(false);
        startLoopFlag = false;
        task.setStopFlag(true);
        print();
    }

//    public void morePointsAction() {
//        if(heightText.getText().contains(",")  || heightText.getText().contains(".")
//                || widthText.getText().contains(",")  || widthText.getText().contains(".")
//                || numberCellsText.getText().contains(",")  || numberCellsText.getText().contains(".")
//                || radiousText.getText().contains(",")  || radiousText.getText().contains(".")){
//            Alert alert = new Alert(Alert.AlertType.INFORMATION);
//            alert.setTitle("Information");
//            alert.setHeaderText("Należy podać liczbę całkowitą");
//            alert.setContentText(" nieprawidłowy format w textboxie ");
//            alert.showAndWait();
//            heightText.setText("500");
//            widthText.setText("500");
//            numberCellsText.setText("100");
//            radiousText.setText("20");
//        }
//        else {
//
//            Random randomGenerator = new Random();
//            int height = Integer.parseInt(heightText.getText());
//            int width = Integer.parseInt(widthText.getText());
//            int numberOfpoints = Integer.parseInt(this.numberCellsText.getText()) * 2;
//
//            for (int i = 0; i < numberOfpoints; i++) {
//                int x = randomGenerator.nextInt(width - 1);
//                int y = randomGenerator.nextInt(height - 1);
//                if (model.getEmbryos()[x][y].getState() == 0 && model.getEmbryos()[x][y].getColor().equals(Color.WHITE)) {
//                    System.out.println("weszlo");
//                    model.setEmbryos(x, y, 1);
//                    model.getEmbryos()[y][x].setColor(Color.rgb(randomGenerator.nextInt(255), randomGenerator.nextInt(255), randomGenerator.nextInt(255)));
//                    model.getEmbryos()[y][x].setId(numberOfpoints--);
//                    gc.setFill(model.getEmbryos()[y][x].getColor());
//                    gc.fillRect(x * embryoSize, y * embryoSize, embryoSize, embryoSize);
//                }
//                else{
//                    numberOfpoints++;
//                    System.out.println("nie weszlo");
//                    //gc.setFill(model.getEmbryos()[y][x].getColor());
//                   // gc.fillRect(x * embryoSize, y * embryoSize, embryoSize, embryoSize);
//                }
//            }
//        }
//
//    }

    public void clearAction(){
        btnRand.setDisable(false);
        model.clearGrid();
        gc.clearRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());
    }

    @FXML
    private void handleMouseClick(MouseEvent mouseEvent) {
        int x = (int) mouseEvent.getX();
        int y = (int) mouseEvent.getY();
        x = (x / embryoSize) * embryoSize;
        y = (y / embryoSize) * embryoSize;
        int gridX = x / embryoSize;
        int gridY = y / embryoSize;
        if (gridX > height - 1)
            gridX = width - 1;
        if (gridX < 0)
            gridX = 0;
        if (gridY > height - 1)
            gridY = height - 1;
        if (gridY < 0)
            gridY = 0;
        int xFinal = gridX;
        int yFinal = gridY;
        Platform.runLater(() -> {
            if (model.getEmbryos()[yFinal][xFinal].getState() == 0) {
                model.setEmbryos(xFinal, yFinal, 1);
                model.getEmbryos()[yFinal][xFinal].setColor(Color.rgb(randomGenerator.nextInt(255), randomGenerator.nextInt(255), randomGenerator.nextInt(255)));

                gc.setFill(model.getEmbryos()[yFinal][xFinal].getColor());
                gc.fillRect(xFinal * embryoSize, yFinal * embryoSize, embryoSize, embryoSize);
            }
        });
    }
    public void randomPoints(){
        int height = Integer.parseInt(heightText.getText());
        int width = Integer.parseInt(widthText.getText());
        canvasHeight= (int) canvas.getHeight();
        canvasWeight= (int) canvas.getWidth();

        canvas.setHeight(height);
        canvas.setWidth(width);

        this.width = (int) gc.getCanvas().getWidth() / embryoSize;
        this.height = (int) gc.getCanvas().getHeight() / embryoSize;
        model = new Model(this.width, this.height);
        clear();
        numberOfCells = Integer.parseInt(numberCellsText.getText());
//        radiousSize = Integer.parseInt(radiousText.getText());

        if (pointsCombo.getValue().equals( "Rownomierne")) {
           // model.randomSteadilyPoints(numberOfCells);
            model.randomEvenPoints(numberOfCells);
        }
        else if (pointsCombo.getValue().equals( "Promień")) {
            model.randomRadius(numberOfCells*2, radiousSize);
              //model.randomPointsWithRadious(numberOfCells*2, radiousSize);
            //  model.randomPointsWithRadious(numberOfCells*2);
        }
        else if (pointsCombo.getValue() == "Losowe") {
            model.randomPoints(numberOfCells*2);
        }

        print();
    }

    public void simulate() {
        Platform.runLater(() -> {
            if (neighborsCombo.getValue().equals("von Neumann") && !periodicBtn.isSelected()) {
               model.vonNeumannNeighborhoodWithoutPeriodic();
            }
            if (neighborsCombo.getValue().equals("von Neumann") &&  periodicBtn.isSelected()) {
                model.vonNeumannNeighborhoodPeriodic();
            }
            if (neighborsCombo.getValue().equals("Moore'a") && !periodicBtn.isSelected()) {
                model.MooreNeighbourhoodWithoutPeriodic();
            }
            if (neighborsCombo.getValue().equals("Moore'a") &&  periodicBtn.isSelected()) {
                model.MooreNeighbourhoodPeriodic();
            }
            if (neighborsCombo.getValue().equals("Hexagonalne (prawe)") && !periodicBtn.isSelected()) {
                model.HexagonalRightNeighbourhoodWithoutPeriodic();
            }
            if (neighborsCombo.getValue().equals("Hexagonalne (prawe)") &&  periodicBtn.isSelected()) {
                model.HexagonalRightNeighbourhoodPeriodic();
            }
            if (neighborsCombo.getValue().equals("Hexagonalne (lewe)") && !periodicBtn.isSelected()) {
                model.HexagonalLeftNeighbourhoodWithoutPeriodic();
            }
            if (neighborsCombo.getValue().equals("Hexagonalne (lewe)") &&  periodicBtn.isSelected()) {
                model.HexagonalLeftNeighbourhoodPeriodic();
            }
            if (neighborsCombo.getValue().equals("Hexalosowe") && !periodicBtn.isSelected()) {
                model.HexagonalRandomWithoutPeriodic();
            }
            if (neighborsCombo.getValue().equals("Hexalosowe") &&  periodicBtn.isSelected()) {
                model.HexagonalRandomPeriodic();
            }
            if (neighborsCombo.getValue().equals("Pentalosowe") && !periodicBtn.isSelected()) {
                model.PentagonalRandomWithoutPeriodic();
            }
            if (neighborsCombo.getValue().equals("Pentalosowe") &&  periodicBtn.isSelected()) {
                model.PentagonalRandomPeriodic();
            }
            counter = 0;
          //  print();
        });
    }

    public int getSpeed() {
        return speed;
    }


    public void clear() {
        model.clearGrid();
        gc.clearRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());
    }

    public void print() {
        //   double widthSize = canvasWeight/width;
        //   double heightSize =canvasHeight/height;
        //   System.out.println(canvasWeight);
        //   System.out.println(canvasHeight);

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (model.getEmbryos()[i][j].getState() == 1) {
                    gc.setFill(model.getEmbryos()[i][j].getColor());
                    gc.fillRect(j * embryoSize, i * embryoSize, embryoSize, embryoSize);
                    //  gc.fillRect(i * heightSize, j * widthSize, heightSize, widthSize);
                }
            }
        }
    }


}
