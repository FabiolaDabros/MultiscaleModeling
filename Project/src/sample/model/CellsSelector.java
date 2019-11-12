package sample.model;

import javafx.scene.paint.Color;

import java.util.*;

public class CellsSelector {
    private List<Cell> selectedCells;
    private Map<Integer, Color> removedColors;
    public static final Color structureColor = Color.HOTPINK;

    public CellsSelector() {
        this.selectedCells = new ArrayList<>();
        removedColors = new HashMap<>();
    }

    public boolean checkIfGrainIsSelected(int x, int y) throws NullPointerException {
        Grid grid = Nucleon.getGrid();
        Cell cell = grid.getCell(x, y);
        return selectedCells.contains(cell);
    }

    public void selectGrain(int x, int y) {
        Grid grid = Nucleon.getGrid();
        try {
            int state = grid.getCell(x, y).getState();
            for (Cell cell : grid.getGrid()) {
                if (cell.getState() == state) {
                    selectedCells.add(cell);
                }
            }
            setSelectedColorToGrain(state);
        } catch (NullPointerException ex) {
            System.out.println("null");
        }
    }

    public void unselectGrain(int x, int y) {
        Grid grid = Nucleon.getGrid();
        try {
            int state = grid.getCell(x, y).getState();
            selectedCells.removeIf(cell -> cell.getState() == state);
            setPreviousColorToUnselectedGrain(state);
        } catch (NullPointerException ex) {
            System.out.println("null");
        }
    }

    public void unselectAll() {
        removedColors.forEach( (i, c) -> Nucleon.getGrainsColors().put(i, removedColors.get(i)));
        selectedCells.clear();
        removedColors.clear();
    }

    public ArrayList<Integer> getSelectedGrainsId() {
        Set<Integer> set = new HashSet<>();
        for (Cell c : selectedCells)
            set.add(c.getState());
        return new ArrayList<>(set);
    }

    private void setSelectedColorToGrain(int state) {
        removedColors.put(state, Nucleon.getColorForGrain(state));
        Nucleon.getGrainsColors().put(state, structureColor);
    }

    private void setPreviousColorToUnselectedGrain(int state) {
        Nucleon.getGrainsColors().put(state, removedColors.get(state));
        removedColors.remove(state);
    }

    public List<Cell> getSelectedCells() {
        return selectedCells;
    }
}
