package sample.model.CellularAutomata;

import sample.model.Cell;
import sample.model.Neighbourhoods.FurtherMoore;
import sample.model.Neighbourhoods.Moore;
import sample.model.Neighbourhoods.Neighbourhood;
import sample.model.Neighbourhoods.VonNeumann;
import sample.model.Nucleon;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class SimpleGrainGrowth {

    private Random generator=new Random();
    private Moore moore;
    private VonNeumann nearestMoore;
    private FurtherMoore furtherMoore;
    private int shapePercentage;

    public void shapeControlGrowth() {
        this.generator = new Random();
    }

    public void growGrainsMoore(int shapePercentage) {
        this.shapePercentage = shapePercentage;
        growGrains("Moore", true);
    }

    public void shapeControlNeighbourhood(Cell cell) {
        moore = new Moore(cell);
        nearestMoore = new VonNeumann(cell);
        furtherMoore = new FurtherMoore(cell);
    }

    public boolean checkNeighbourhoodMoore(Cell c) {
        shapeControlNeighbourhood(c);
        HashMap<Integer, Integer> statesOfCellsAround = new HashMap<>();
        for (Cell n : getMoore().getNeighbours()) {
//            if (n.getState() != 0 && n.getState() != 1)
            if (n.getState() > 1 + Nucleon.getNumberOfSubstructures())
                statesOfCellsAround.merge(n.getState(), 1, Integer::sum);
        }
        if (!statesOfCellsAround.isEmpty()) {
            HashMap.Entry<Integer, Integer> mooreEntries = null;
            for (HashMap.Entry<Integer, Integer> entry : statesOfCellsAround.entrySet()) {
                if (mooreEntries == null || entry.getValue() > mooreEntries.getValue())
                    mooreEntries = entry;
            }
            if (mooreEntries.getValue() >= 5) {
                c.setFutureState(mooreEntries.getKey());
            } else {
                HashMap.Entry<Integer, Integer> maxEntry = null;
                statesOfCellsAround.clear();
                for (Cell n : getNearestMoore().getNeighbours()) {
//                    if (n.getState() != 0 && n.getState() != 1)
                    if (n.getState() > 1 + Nucleon.getNumberOfSubstructures())
                        statesOfCellsAround.merge(n.getState(), 1, Integer::sum);
                }
                for (HashMap.Entry<Integer, Integer> entry : statesOfCellsAround.entrySet()) {
                    if (maxEntry == null || entry.getValue() > maxEntry.getValue())
                        maxEntry = entry;
                }
                if (maxEntry != null && maxEntry.getValue() >= 3) {
                    c.setFutureState(maxEntry.getKey());
                } else {
                    statesOfCellsAround.clear();
                    maxEntry = null;
                    for (Cell n : getFurtherMoore().getNeighbours()) {
//                        if (n.getState() != 0 && n.getState() != 1)
                        if (n.getState() > 1 + Nucleon.getNumberOfSubstructures())
                            statesOfCellsAround.merge(n.getState(), 1, Integer::sum);
                    }
                    for (HashMap.Entry<Integer, Integer> entry : statesOfCellsAround.entrySet()) {
                        if (maxEntry == null || entry.getValue() > maxEntry.getValue())
                            maxEntry = entry;
                    }
                    if (maxEntry != null && maxEntry.getValue() >= 3) {
                        c.setFutureState(maxEntry.getKey());
                    } else {
                        int random = generator.nextInt(100) + 1;
                        if (random <= shapePercentage) {
                            c.setFutureState(mooreEntries.getKey());
                        }
                    }
                }
            }
        }
        return true;
    }

    public void growGrains(String neighbourhood, Boolean shapeControl) {
        createNeighbourhood(neighbourhood);
        List<Cell> grid = Nucleon.getGrid().getGrid();

        boolean repeat = true;

        while (repeat) {
            repeat = false;
            List<Cell> changed = new ArrayList<>();
            for (Cell c : grid) {
                if (c.getState() == 0) {
                    if(shapeControl && neighbourhood == ("Moore")) {
                        if(checkNeighbourhoodMoore(c)){
                            changed.add(c);
                        }
                    } else {
                        if(checkNeighbourhood(c)) {
                            changed.add(c);
                        }
                    }
                }
            }
            if (!changed.isEmpty()) {
                repeat = true;
                for (Cell c : changed) {
                    c.setState(c.getFutureState());
                }
            }
        }
    }

    private void createNeighbourhood(String neighbourhood) {
        List<Cell> grid = Nucleon.getGrid().getGrid();
        for (Cell c: grid) {
            Neighbourhood n;
            if(neighbourhood == "VonNeumann") {
                n = new VonNeumann(c);
                c.setNeighbourhood(n);
            } else {
                n = new Moore(c);
                c.setNeighbourhood(n);
            }
        }
    }

    private boolean checkNeighbourhood(Cell c) {

        List<Cell> neighbours = c.getNeighbourhood().getNeighbours();
        HashMap<Integer, Integer> statesAround = new HashMap<>();
        boolean changedCell = false;
        for (Cell n : neighbours) {
            if (n.getState() > 1 + Nucleon.getNumberOfSubstructures())
                statesAround.merge(n.getState(), 1, Integer::sum);
        }
        if(!statesAround.isEmpty()) {
            HashMap.Entry<Integer, Integer> maxEntry = null;
            for (HashMap.Entry<Integer, Integer> entry : statesAround.entrySet()) {
                if (maxEntry == null || entry.getValue() > maxEntry.getValue())
                    maxEntry = entry;
            }
            c.setFutureState(maxEntry.getKey());
            changedCell = true;
        }
        return changedCell;
    }

    public FurtherMoore getFurtherMoore() {
        return furtherMoore;
    }

    public Moore getMoore() {
        return moore;
    }

    public VonNeumann getNearestMoore() {
        return nearestMoore;
    }
}
