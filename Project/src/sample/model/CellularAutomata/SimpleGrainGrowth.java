package sample.model.CellularAutomata;

import sample.model.Cell;
import sample.model.Neighbourhoods.Neighbourhood;
import sample.model.Neighbourhoods.VonNeumann;
import sample.model.Nucleon;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SimpleGrainGrowth {
    public void growGrains(String neighbourhood) {
        createNeighbourhood(neighbourhood);
        List<Cell> grid = Nucleon.getGrid().getGrid();

        boolean repeat = true;

        while (repeat) {
            repeat = false;
            List<Cell> changed = new ArrayList<>();
            for (Cell c : grid) {
                if (c.getState() == 0) {
                    repeat = true;
                    checkNeighbourhood(c);
                    changed.add(c);
                }
            }
            for (Cell c : changed) {
                c.setState(c.getFutureState());
            }
        }
    }

    private void createNeighbourhood(String neighbourhood) {
        List<Cell> grid = Nucleon.getGrid().getGrid();
        for (Cell c: grid) {
            Neighbourhood n;
            n = new VonNeumann(c);
            c.setNeighbourhood(n);
        }
    }

    private void checkNeighbourhood(Cell c) {
        List<Cell> neighbours = c.getNeighbourhood().getNeighbours();
        HashMap<Integer, Integer> statesAround = new HashMap<>();
        for (Cell n : neighbours) {
            if (n.getState() != 0 && n.getState() != 1)
                statesAround.merge(n.getState(), 1, Integer::sum);
        }
        if(!statesAround.isEmpty()) {
            HashMap.Entry<Integer, Integer> maxEntry = null;
            for (HashMap.Entry<Integer, Integer> entry : statesAround.entrySet()) {
                if (maxEntry == null || entry.getValue() > maxEntry.getValue())
                    maxEntry = entry;
            }
            c.setFutureState(maxEntry.getKey());
        }
    }
}
