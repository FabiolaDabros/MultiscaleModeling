package sample.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import sample.model.*;
import sample.model.CellularAutomata.SimpleGrainGrowth;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Random;

import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

public class Controller implements Initializable {
    public TextField widthText;
    public TextField heightText;
    @FXML  TextField numberCellsText;
    @FXML  Canvas canvas;
    @FXML  RadioButton periodicBtn;
    @FXML  ComboBox neighborsCombo;
    @FXML  Button btnClear;
    @FXML  Button btnStart;
    @FXML  Button btnRand;

    public GraphicsContext gc;
    private int numberOfGrains;
    private Set<Integer> setOfRandomCells = new HashSet<>();
    private static final int TOP_PADDING = 0;
    public static final int SCALE = 2;
    public static final String SEPARATOR = ";";

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        btnStart.setDisable(true);
        btnClear.setDisable(true);
        gc = canvas.getGraphicsContext2D();
        numberOfGrains = Integer.parseInt(this.numberCellsText.getText());
        neighborsCombo.setValue("VonNeumann");
    }

    public void handleMouseClick(MouseEvent mouseEvent) {
    }

    public void clearAction(ActionEvent actionEvent) {
        btnRand.setDisable(false);
        btnStart.setDisable(true);
        cleanGrowth();
        cleanNucleation();
        Nucleon.cleanNucleonModel();
        ActionEvent actionEvsent = new ActionEvent();
        gc.clearRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());
        print();
       // model.clearGrid();

    }

    public void stopAction(ActionEvent actionEvent) {
    }

    public void startAction(ActionEvent actionEvent) {
        btnRand.setDisable(false);
        btnStart.setDisable(true);
        btnClear.setDisable(false);
      //  cleanGrowth();
        String chosenNeighbourhood = neighborsCombo.getValue().toString();
        Nucleon.setNeighbourhoodType(chosenNeighbourhood);
        SimpleGrainGrowth growth = new SimpleGrainGrowth();
        growth.growGrains(chosenNeighbourhood);

        print();
    }

    public void randCellAction(ActionEvent actionEvent) {
        gc.clearRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());
        setOfRandomCells.clear();
        numberOfGrains = Integer.parseInt(this.numberCellsText.getText());
        prepareGrid();
        if (numberOfGrains < 2)
            throw new IllegalArgumentException("The minimum number of grains is 2");
        if (numberOfGrains > Nucleon.getGrid().getGrid().size())
            throw new IllegalArgumentException("The maximum number of grains is size of the grid!");

        cleanNucleation();
        Nucleon.setNumberOfGrains(numberOfGrains);
        chooseGrainsColors();

        try {
            chooseGrainsPosition();
            setGrainsStates();
        }
        catch (Exception e) {
            System.out.println("Aj aj. Coś zrypałam w rozrodzie ziarn");
        }

        print();

        btnStart.setDisable(false);
        btnClear.setDisable(false);
    }

    private void prepareGrid() {
        int width = Integer.parseInt(widthText.getText());
        int height = Integer.parseInt(heightText.getText());
        if (width < 300 || height < 300) {
            throw new IllegalArgumentException("Minimum grid size is 300x300");
        }
        boolean isPeriodic = periodicBtn.isSelected();

        Nucleon.cleanNucleonModel();
        Nucleon.setGrid(new Grid(width, height, isPeriodic));

    }

    private void print() {
        if (Nucleon.getGrid() != null) {
            for (Cell c : Nucleon.getGrid().getGrid()) {
                if(c.getState() == 0) {
                    gc.setFill(Color.WHITE);
                }
                else {
                    gc.setFill(Nucleon.getGrainsColors().get(c.getState()));
                }
                gc.fillRect(c.getX() * SCALE, TOP_PADDING + c.getY()*SCALE, SCALE, SCALE);
            }
        }
    }

    public void chooseGrainsPosition() {
        for (Cell c : Nucleon.getGrid().getGrid()) {
            c.setState(0);
        }

        Random generator = new Random();

        for (int i = 0; i < Nucleon.getNumberOfGrains(); i++) {

            int random = generator.nextInt(Nucleon.getGrid().getGrid().size());
            if (this.setOfRandomCells.contains(random)) {
                i--;
            } else {
                this.setOfRandomCells.add(random);
            }
        }
    }

    public void setGrainsStates() {
        Iterator<Integer> iterator = setOfRandomCells.iterator();
        int phase = 1;
        while (iterator.hasNext()) {
            int i = iterator.next();
            Nucleon.getGrid().getCellAsListPosition(i).setState(phase);
            phase++;
        }
    }
    public void chooseGrainsColors() {
        HashMap<Integer, Color> grains = new HashMap<>();
        Random generator = new Random();

        for (int i = 1; i <= Nucleon.getNumberOfGrains(); i++) {
            Color color = (Color.rgb(generator.nextInt(255), generator.nextInt(255), generator.nextInt(255)));
            if (color.equals(Color.WHITE) || color.equals(Color.BLACK) || grains.containsValue(color)) {
                --i;
                continue;
            }
            grains.put(i, color);
            Nucleon.setGrainsColors(grains);
        }
    }

    public int RGBtoINT(float red, float green, float blue) {
        int r = Math.round(255*red);
        int g = Math.round(255*green);
        int b = Math.round(255*blue);

        r = (r<<16) & 0x00ff0000;
        g = (g<<8) & 0x0000ff00;
        b = b & 0x000000ff;

        return 0xff000000 | r | g | b;
    }

    public Color INTtoRGB(int rgb) {
        int b =  rgb & 255;
        int g = (rgb >> 8) & 255;
        int r =   (rgb >> 16) & 255;
        Color color = (Color.rgb(b, g, r));

        return color;
    }

    public static void cleanNucleation() {
        Nucleon.getGrainsColors().keySet().removeIf(key -> !(key.equals(0)));
        Nucleon.setNumberOfGrains(0);
        Nucleon.setNeighbourhoodType(null);
        Nucleon.getGrid().getGrid().forEach(cell -> {
            cell.setState(0);
            cell.setFutureState(0);
            cell.setNeighbourhood(null);
        });
    }

    public static void cleanGrowth() {
        Nucleon.setNeighbourhoodType(null);

        Iterator<Cell> iterator = Nucleon.getGrid().getGrid().iterator();
        while (iterator.hasNext()) {
            Cell c = iterator.next();
            c.setNeighbourhood(null);
        }
    }


    private File prepareFile(String type) {
        Stage stage = new Stage();
        FileChooser fileChooser = new FileChooser();
        if(type == "txt") {
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("TXT", "*.txt")
            );
        } else {
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("BMP", "*.bmp")
            );
        }

        File file = fileChooser.showOpenDialog(stage);
        return file;

    }
    public void ImportTxtButton(ActionEvent actionEvent) {
       File file = prepareFile("txt");
        try(BufferedReader br = new BufferedReader(new FileReader(file))) {

            Nucleon.cleanNucleonModel();

            String currentLine = br.readLine();
            String[] tokens = currentLine.split(SEPARATOR);

            Nucleon.setNumberOfGrains(Integer.parseInt(tokens[3]));
            Nucleon.setNeighbourhoodType(tokens[4]);

            Grid grid = new Grid(Integer.parseInt(tokens[0]),Integer.parseInt(tokens[1]),Boolean.parseBoolean(tokens[2]));

            br.readLine();

            while ((currentLine = br.readLine()) != null) {
                tokens = currentLine.split(SEPARATOR);
                Cell cell = new Cell(Integer.parseInt(tokens[0]),Integer.parseInt(tokens[1]));
                cell.setState(Integer.parseInt(tokens[2]));
                grid.setCell(Integer.parseInt(tokens[0]), Integer.parseInt(tokens[1]), cell);
            }

            Nucleon.setGrid(grid);
            chooseGrainsColors();
            print();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void ImportBmpButton(ActionEvent actionEvent) {
        File file = prepareFile("bmp");

        try {
            BufferedImage image = ImageIO.read(file);
            Grid grid = new Grid(image.getWidth(), image.getHeight(), false);

            Nucleon.cleanNucleonModel();

            HashMap<Color, Integer> grainsColors = new HashMap<>();
            grainsColors.put(Color.WHITE, 0);

            int numberOfGrains = 0;

            for (int y = 0; y < image.getHeight(); y++) {
                for (int x = 0; x < image.getWidth(); x++) {
                    Color color = INTtoRGB(image.getRGB(x, y));
                    Cell cell = new Cell(x, y);
                    if (color.equals(Color.WHITE) || color.equals(Color.BLACK)) {
                        cell.setState(0);
                    }
                    else if (grainsColors.containsKey(color)) {
                        cell.setState(grainsColors.get(color));
                    }
                    else {
                        numberOfGrains++;
                        grainsColors.put(color, numberOfGrains);
                        cell.setState(numberOfGrains);
                    }
                    grid.setCell(cell.getX(), cell.getY(), cell);
                }
            }

            Nucleon.setGrid(grid);
            Nucleon.setNumberOfGrains(numberOfGrains);

            Map<Integer, Color> colorsInversed = grainsColors.entrySet()
                    .stream()
                    .collect(Collectors.toMap(Map.Entry::getValue, Map.Entry::getKey));
            Nucleon.setGrainsColors(colorsInversed);

            print();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void ExportTxtButton(ActionEvent actionEvent) {
        File file = prepareFile("txt");

        Grid grid = Nucleon.getGrid();

        StringBuilder gridText = new StringBuilder();

        String neighbourhoodType = "null";

        try {
            neighbourhoodType = Nucleon.getNeighbourhoodType();
        }
        catch (NullPointerException e) {
            e.printStackTrace();
        }

        gridText
                .append(grid.getWidth())
                .append(SEPARATOR)
                .append(grid.getHeight())
                .append(SEPARATOR)
                .append(grid.isPeriodic())
                .append(SEPARATOR)
                .append(Nucleon.getNumberOfGrains())
                .append(SEPARATOR)
                .append(neighbourhoodType)
                .append(System.lineSeparator())
                .append("X")
                .append(SEPARATOR)
                .append("Y")
                .append(SEPARATOR)
                .append("ID")
                .append(SEPARATOR)
                .append(System.lineSeparator());

        grid.getGrid().forEach(cell ->
                gridText.append(cell.getX())
                        .append(SEPARATOR)
                        .append(cell.getY())
                        .append(SEPARATOR)
                        .append(cell.getState())
                        .append(SEPARATOR)
                        .append(System.lineSeparator())
        );

        try(PrintWriter printWriter = new PrintWriter(file)) {
            printWriter.write(gridText.toString());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void ExportBmpButton(ActionEvent actionEvent) {
        File file = prepareFile("bmp");

        Grid grid = Nucleon.getGrid();

        int width = grid.getWidth();
        int height = grid.getHeight();

        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        grid.getGrid().forEach(c -> {
            int x = c.getX();
            int y = c.getY();
            float blue = (float) Nucleon.getColorForGrain(c.getState()).getBlue();
            float red = (float) Nucleon.getColorForGrain(c.getState()).getRed();
            float green = (float) Nucleon.getColorForGrain(c.getState()).getGreen();
            int color = RGBtoINT(red,green,blue);

            image.setRGB(x, y, color);

        });

        try{
            ImageIO.write(image, "bmp", file);
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
