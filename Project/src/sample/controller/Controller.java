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
    public TextField inclusionsSizeId;
    public ComboBox inclusionTypeId;
    public TextField inclusionsId;
    public TextField shapePercentageId;
    public RadioButton shapeControlOnOffId;
    public ComboBox structureTypeId;
    public TextField mySIZE1;
    @FXML  TextField numberCellsText;
    @FXML  TextField mySIZE;
    @FXML  Canvas canvas;
    @FXML  RadioButton periodicBtn;
    @FXML  ComboBox neighborsCombo;
    @FXML  Button btnClear;
    @FXML  Button btnStart;
    @FXML  Button btnRand;
    public Boolean isStructrureClicable = false;

    public GraphicsContext gc;
    private int numberOfGrains;
    private Set<Integer> setOfRandomCells = new HashSet<>();
    private static final int TOP_PADDING = 0;
    public static final int SCALE = 2;
    public static final String SEPARATOR = ";";
    boolean isClear = true;
    private CellsSelector cellsSelector = new CellsSelector();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        mySIZE.setVisible(false);
        mySIZE1.setVisible(false);
        btnStart.setDisable(true);
        btnClear.setDisable(true);
        gc = canvas.getGraphicsContext2D();
        numberOfGrains = Integer.parseInt(this.numberCellsText.getText());
        neighborsCombo.getItems().addAll("VonNeumann","Moore");
        neighborsCombo.setValue("VonNeumann");
        inclusionTypeId.getItems().addAll("square","circle");
        inclusionTypeId.setValue("square");
        isClear = true;
        structureTypeId.getItems().addAll("Substructure","Dual phase", "Disable");
        structureTypeId.setValue("Disable");
    }

    public void handleMouseClick(MouseEvent mouseEvent) {
        String chosenStructure = structureTypeId.getValue().toString();
        int widthInput = Integer.parseInt(widthText.getText());
        int heightInput = Integer.parseInt(heightText.getText());
        if (chosenStructure!="Disable") {
            int x = (int) mouseEvent.getX();
            int y = (int) (mouseEvent.getY() - TOP_PADDING);
            double width = canvas.getGraphicsContext2D().getCanvas().getWidth() / widthInput;
            double height = canvas.getGraphicsContext2D().getCanvas().getHeight() / heightInput;
            x = (int) ((x / width) *width);
            y = (int) ((y / height) *height);
            int canvasX = (int) (x/height);
            int canvasY = (int) (y/width);
            if(canvasX > 300 -1 )
                canvasX = 300 -1;
            if (canvasX <0)
                canvasX = 0;
            if(canvasY > 300 -1 )
                canvasY = 300 -1;
            if (canvasY <0)
                canvasY = 0;
            int Xend = canvasX;
            int Yend = canvasY;
            x = Xend;
            y = Yend;
                    try {
                        if (!cellsSelector.checkIfGrainIsSelected(x, y)) {
                            cellsSelector.selectGrain(x, y);
                        } else {
                            cellsSelector.unselectGrain(x, y);
                        }
                        print();
                    } catch (NullPointerException exc) {
                        System.out.println("null w myszy");
                    }
        } else {
            if(cellsSelector != null) {
                cellsSelector.unselectAll();
                cellsSelector = null;
            }
            print();
        }
    }

    public void clearAction(ActionEvent actionEvent) {
        btnRand.setDisable(false);
        btnStart.setDisable(true);
        cleanGrowth();
        cleanNucleation();
        Nucleon.cleanNucleonModel();
        gc.clearRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());
        print();
    }

    public void stopAction(ActionEvent actionEvent) {
    }

    public void startAction(ActionEvent actionEvent) {

        if (Nucleon.checkIfAnyEmptySpaces()) {
            String chosenNeighbourhood = neighborsCombo.getValue().toString();
            if (!shapeControlOnOffId.isSelected() || chosenNeighbourhood=="VonNeumann") {
                btnRand.setDisable(false);
                btnStart.setDisable(true);
                btnClear.setDisable(false);

                Nucleon.setNeighbourhoodType(chosenNeighbourhood);
                SimpleGrainGrowth growth = new SimpleGrainGrowth();
                growth.growGrains(chosenNeighbourhood, shapeControlOnOffId.isSelected());

                print();
            } else {
                try {
                    int numberOfGrainsToDelete = Integer.parseInt(shapePercentageId.getText());
                    if (numberOfGrainsToDelete <= 0 || numberOfGrainsToDelete > 100)
                        throw new NullPointerException();
                    Nucleon.setNeighbourhoodType(chosenNeighbourhood);
                    SimpleGrainGrowth simpleGrainGrowth = new SimpleGrainGrowth();
                    simpleGrainGrowth.shapeControlGrowth();
                    simpleGrainGrowth.growGrainsMoore(numberOfGrainsToDelete);
                    print();
                } catch (NullPointerException  e) {
                    System.out.println("null");
                }
            }
        }
    }

    public void randCellAction(ActionEvent actionEvent) {
            if (Nucleon.getGrid() == null) {
                gc.clearRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());
                prepareGrid();
            }
            setOfRandomCells.clear();
            numberOfGrains = Integer.parseInt(this.numberCellsText.getText());
            if (numberOfGrains < 2)
                throw new IllegalArgumentException("The minimum number of grains is 2");
            if (numberOfGrains > Nucleon.getGrid().getGrid().size())
                throw new IllegalArgumentException("The maximum number of grains is size of the grid!");

            Nucleon.setNumberOfGrains(Nucleon.getNumberOfGrains() + numberOfGrains);
            chooseGrainsColors();

            try {
                chooseGrainsPosition(numberOfGrains);
                setGrainsStates(numberOfGrains);
            } catch (Exception e) {
                System.out.println("error rozrost");
            }

            print();

            btnStart.setDisable(false);
            btnClear.setDisable(false);
    }

    private void prepareGrid() {
        int width = Integer.parseInt(widthText.getText());
        int height = Integer.parseInt(heightText.getText());
        canvas.setWidth(width*SCALE);
        canvas.setHeight(height*SCALE);
        if (width < 300 || height < 300) {
            throw new IllegalArgumentException("za mały rozmiar siatki");
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
                } else if(c.getState() == 1){
                    gc.setFill(Color.BLACK);
                } else if (Nucleon.getGrainsColors().get(c.getState()) == Color.HOTPINK) {
                    gc.setFill(Color.HOTPINK);
                } else {
                    gc.setFill(Nucleon.getGrainsColors().get(c.getState()));
                }
                gc.fillRect(c.getX() * SCALE, TOP_PADDING + c.getY()*SCALE, SCALE, SCALE);
            }
        }
    }

    public void chooseGrainsPosition(int numberOfGrains) {
        if (Nucleon.checkIfAnyEmptySpaces()) {
            Random generator = new Random();

            for (int i = 0; i < numberOfGrains; i++) {
                int random = generator.nextInt(Nucleon.getGrid().getGrid().size());
                if (this.setOfRandomCells.contains(random) || Nucleon.getGrid().getCellAsListPosition(random).getState() > 0){
                    i--;
                } else {
                    this.setOfRandomCells.add(random);
                }
            }
        }
    }

    public void setGrainsStates(int numberOfGrains) {
        Iterator<Integer> iterator = setOfRandomCells.iterator();
        int phase = 2 + (Nucleon.getNumberOfGrains() - numberOfGrains);
        while (iterator.hasNext()) {
            int i = iterator.next();
            Nucleon.getGrid().getCellAsListPosition(i).setState(phase);
            phase++;
        }
    }

    public void chooseGrainsColors() {
        HashMap<Integer, Color> grains = new HashMap<>();
        Random generator = new Random();

        grains.put(0, Color.WHITE);
        grains.put(1, Color.BLACK);

        for (int i = 2; i <= Nucleon.getNumberOfGrains()+1; i++) {
            Color color = (Color.rgb(generator.nextInt(255), generator.nextInt(255), generator.nextInt(255)));
            if (color.equals(Color.WHITE) || color.equals(Color.BLACK) || color.equals(Color.HOTPINK) ||grains.containsValue(color)) {
                --i;
                continue;
            }
            grains.put(i, color);
        }
        Nucleon.setGrainsColors(grains);
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
        Nucleon.getGrainsColors().keySet().removeIf(key -> !(key.equals(0)) && !(key.equals(1)));
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
            Nucleon.setNumberOfInclusions(Integer.parseInt(tokens[4]));
            Nucleon.setNeighbourhoodType(tokens[5]);

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
            grainsColors.put(Color.BLACK, 1);

            int numberOfGrains = 1;

            for (int y = 0; y < image.getHeight(); y++) {
                for (int x = 0; x < image.getWidth(); x++) {
                    Color color = INTtoRGB(image.getRGB(x, y));
                    Cell cell = new Cell(x, y);
                    if (color.equals(Color.WHITE)) {
                        cell.setState(0);
                    } else if (color.equals(Color.BLACK)) {
                        Nucleon.setNumberOfInclusions(1);
                        cell.setState(1);
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
            Nucleon.setNumberOfGrains(numberOfGrains -1);

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
                .append(Nucleon.getNumberOfInclusions())
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

    public void inclusionsAddAction(ActionEvent actionEvent) {
        int numberOfInclusions = Integer.parseInt(this.inclusionsId.getText());
        Nucleon.setNumberOfInclusions(numberOfInclusions);
        if(Nucleon.getGrid() == null) {
            prepareGrid();
            inclusionsAddAction(actionEvent);
        }
        String inclusionType = inclusionTypeId.getValue().toString();

        if(Nucleon.getNumberOfInclusions() != 0) {
            int sizeOfInclusions = Integer.parseInt(this.inclusionsSizeId.getText());
            Inclusion inclusion = new Inclusion();

            if (Nucleon.getNumberOfGrains() == 0)
                inclusion.addRandomlyOnStart(numberOfInclusions, sizeOfInclusions, inclusionType);
            else
                inclusion.addOnBoundaries(numberOfInclusions, sizeOfInclusions, inclusionType);
        }
        else {
            System.out.println("nic");
        }
        print();
    }

    public void checkWhatTypeOfStructure(ArrayList<Integer> selectedGrainsId, boolean isSubstructure) {
        if (isSubstructure)
            generateSubstructure(selectedGrainsId);
        else
            generateDualphase(selectedGrainsId);
    }

    private void generateSubstructure(ArrayList<Integer> selectedGrainsId) {
        deleteTheRestOfGrains(selectedGrainsId);
        Nucleon.setNumberOfGrains(selectedGrainsId.size());
        colorAndStateSubstructure();
    }

    private void generateDualphase(ArrayList<Integer> selectedGrainsId) {
        deleteTheRestOfGrains(selectedGrainsId);
        Nucleon.setNumberOfGrains(1);
        colorAndStateDualPhase();
    }

    private void deleteTheRestOfGrains(ArrayList<Integer> selectedGrainsId) {
        Nucleon.getGrid().getGrid().forEach(cell -> {
            if (cell.getState() != 1 && !selectedGrainsId.contains(cell.getState())) {
                cell.setState(0);
                cell.setFutureState(0);
                cell.setNeighbourhood(null);
            }
        });
    }

    private void colorAndStateSubstructure() {
        Map<Integer, Integer> indexes = new HashMap<>();
        Map<Integer, Color> newColors = new HashMap<>();
        newColors.put(0, Color.WHITE);
        newColors.put(1, Color.BLACK);
        int n = 2;
        for (Cell cell : Nucleon.getGrid().getGrid()) {
            if (cell.getState() != 0 && cell.getState() != 1) {
                if (!indexes.containsKey(cell.getState())) {
                   newColors.put(n, Nucleon.getColorForGrain(cell.getState()));
                    indexes.put(cell.getState(), n);
                    n++;
                }
                cell.setState(indexes.get(cell.getState()));
            }
        }
        Nucleon.setNumberOfSubstructures(n - 2);
        Nucleon.setGrainsColors(newColors);
    }

    private void colorAndStateDualPhase() {
        Map<Integer, Color> newColors = new HashMap<>();
        newColors.put(0, Color.WHITE);
        newColors.put(1, Color.BLACK);

        for (Cell cell : Nucleon.getGrid().getGrid()) {
            if (cell.getState() != 0 && cell.getState() != 1) {
                newColors.put(2, Nucleon.getColorForGrain(cell.getState()));
                cell.setState(2);
            }
        }
        Nucleon.setNumberOfSubstructures(1);
        Nucleon.setGrainsColors(newColors);
    }

    public void clearStructureBtn(ActionEvent actionEvent) {
        String chosenStructure = structureTypeId.getValue().toString();
                    try{
                        if(cellsSelector.getSelectedCells().isEmpty()) {
                            throw new NullPointerException();
                        }
                        if( chosenStructure == "Substructure") {
                            isStructrureClicable = true;
                            btnStart.setDisable(false);
                            checkWhatTypeOfStructure(cellsSelector.getSelectedGrainsId(), true);
                            shapeControlOnOffId.setSelected(false);
                            print();
                        } else if (chosenStructure == "Dual phase"){
                            isStructrureClicable = true;
                            btnStart.setDisable(false);
                            checkWhatTypeOfStructure(cellsSelector.getSelectedGrainsId(), false);
                            shapeControlOnOffId.setSelected(false);
                            print();
                        } else if (chosenStructure == "Disable"){
                            isStructrureClicable = false;
                        } else {
                            System.out.println("bb");
                        }
                    }
                    catch (NullPointerException exc) {
                        System.out.println("aaa");
                    }
    }
}
