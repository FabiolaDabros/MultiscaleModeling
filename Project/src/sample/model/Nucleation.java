package sample.model;

import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Nucleation {
    private String nucleationType;
    private List<Cell> cellsToNucleate;
    private int numberOfGrains;
    private int startPoint;
    private Random generator;
    private boolean canContinue;

    public Nucleation(String nucleationType, List<Cell> cellsToNucleate, int numberOfGrains) {
        this.cellsToNucleate = new ArrayList<>(cellsToNucleate);
        this.nucleationType = nucleationType;
        this.numberOfGrains = numberOfGrains;
        this.generator = new Random();
        startPoint = Nucleon.getNumberOfGrains() + 2;
        if (!cellsToNucleate.isEmpty())
            canContinue = true;
    }

    public boolean canNucleationBeContinued() {
        if (!canContinue) {
            return false;
        }
         cellsToNucleate.removeIf(Cell::isRecrystal);
        if (cellsToNucleate.isEmpty()) {
            canContinue = false;
        }
        return canContinue;
    }

    public List<Cell> prepareToNucleation() {
        List<Cell> recrystallizedCells = new ArrayList<>();
        if (nucleationType.equals("At the beginning")) {
            recrystallizedCells.addAll(makeNucleation());
            canContinue = false;
        }
        else if (nucleationType.equals("Constant")) {
            recrystallizedCells.addAll(makeNucleation());
        }
        else if (nucleationType.equals("Increasing")) {
            recrystallizedCells.addAll(makeNucleation());
            numberOfGrains *= 2;
        }
        return recrystallizedCells;
    }

    private List<Cell> makeNucleation() {
        List<Cell> allRecrystalCells = new ArrayList<>();
        int i;
        for (i = 1; i <= numberOfGrains; i++) {
            boolean continued = true;
            while (true) {
                if (cellsToNucleate.isEmpty()) {
                    i--;
                    canContinue = false;
                    continued = false;
                    break;
                }
                  Cell cell = cellsToNucleate.get(generator.nextInt(cellsToNucleate.size()));
                if (cell.isRecrystal()) {
                    cellsToNucleate.remove(cell);
                }
                else {
                    cell.setRecrystal(true);
                    cell.setEnergy(0f);
                    cell.setState(startPoint + i - 1);
                    allRecrystalCells.add(cell);
                    cellsToNucleate.remove(cell);
                    break;
                }
            }
            if (!continued)
                break;
        }
        startPoint += i;
        Nucleon.setNumberOfGrains(Nucleon.getNumberOfGrains() + i);
        chooseGrainsColorToSRX(i);
        return allRecrystalCells;
    }

    public static void chooseGrainsColorToSRX(int nucleatedCells) {
         Map<Integer, Color> grainsColors = Nucleon.getGrainsColors();
        int grainsColorsSize = grainsColors.size();
        Random generator = new Random();
        for (int i = grainsColorsSize; i < grainsColorsSize + nucleatedCells; i++) {
            float hueMinimum = (float)330/360;
            float hueMaximum = (float)390/360;
            float hue = generator.nextFloat() * (hueMaximum - hueMinimum) + hueMinimum;
            float saturation = generator.nextFloat() * 0.5f + 0.5f;
            float brightness = generator.nextFloat() * 0.3f + 0.3f;

            Color color = Color.hsb(hue, saturation, brightness);

            if (color.equals(Color.WHITE) || color.equals(Color.BLACK) || color.equals(Color.HOTPINK) ||
                    grainsColors.containsValue(color)) {
                --i;
                continue;
            }
            grainsColors.put(i, color);
        }
    }
}
