package sample.model;

import javafx.scene.paint.Color;

import java.util.*;
import java.util.stream.Collectors;

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

    public void selectAllGrains() {
        List<Cell> allCellsWithoutSelected = Nucleon.getGrid().getGrid().stream()
                .filter(c -> !selectedCells.contains(c)).collect(Collectors.toList());

        for (Cell cell : allCellsWithoutSelected) {
            if (cell.getState() != 1 && cell.getState() != 0) {
                selectedCells.add(cell);
                if (!removedColors.containsKey(cell.getState())) {
                    setSelectedColorToGrain(cell.getState());
                }
            }
        }
    }

    public boolean checkIfAllSelected() {
        List<Cell> grid = Nucleon.getGrid().getGrid();
        Optional<Cell> cell = grid.stream().filter(c -> c.getState() != 0 &&
                c.getState() != 1 && !selectedCells.contains(c)).findAny();
        return !cell.isPresent();
    }

    public void unselectAll() {
        ArrayList<Integer> selected = new ArrayList<>();
        for (Cell c: selectedCells
        ) {if (!selected.contains(c.getState()))
            selected.add(c.getState());
        }
        Map<Integer, Color> colors = Nucleon.getGrainsColors();
        removedColors.forEach(colors::put);
        selectedCells.clear();
        removedColors.clear();
    }

    public void removeAllDeteted() {
        List<Integer> stateNumbers = new ArrayList<>();
        stateNumbers.addAll(removedColors.keySet());
        Nucleon.getGrainsColors().keySet().removeIf(key -> key != 0 && key != 1 && !stateNumbers.contains(key));
    }

}
