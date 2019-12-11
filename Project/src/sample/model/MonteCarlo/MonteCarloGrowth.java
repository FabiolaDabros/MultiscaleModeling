package sample.model.MonteCarlo;

import sample.model.Cell;
import sample.model.Neighbourhoods.Moore;
import sample.model.Neighbourhoods.Neighbourhood;
import sample.model.Nucleon;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MonteCarloGrowth {
    private Random randomGenerator;
    private float coefficientJ;
    private int startPoint;
    private int monteCarloSteps;
    List<Cell> monteCarloGrains;

    public MonteCarloGrowth(int numberOfGrains, float j, int monteCarloSteps) {
        randomGenerator = new Random();
        monteCarloGrains = new ArrayList<>();
        coefficientJ = j;
        this.monteCarloSteps = monteCarloSteps;
        startPoint = Nucleon.getNumberOfGrains() - numberOfGrains + 2;
        for (Cell c : Nucleon.getGrid().getGrid()) {
            if(c.getState() == 0) {
                c.setState(randomGenerator.nextInt(Nucleon.getNumberOfGrains() + 2 - startPoint) + startPoint);
                monteCarloGrains.add(c);
            }
        }
    }

    public void growGrains(String neighbourhood) {
        growGrainsMC(neighbourhood);
        for (int i = 0; i < monteCarloSteps; i++) {
            monteCarloStep();
        }
    }

    public void growGrainsMC(String neighbourhood) {
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

    private boolean checkNeighbourhood(Cell c) {
        List<Cell> neighbours = c.getNeighbourhood().getNeighbours();
        int kroneckerFirst = 0;
        for (Cell n : neighbours) {
            if (n.getState() != c.getState()) {
                kroneckerFirst++;
            }
        }
        int changedState = randomGenerator.nextInt(Nucleon.getNumberOfGrains() + 2 - startPoint) + startPoint;
        int kroneckerAfter = 0;
        for (Cell n : neighbours) {
            if (n.getState() != changedState) {
                kroneckerAfter++;
            }
        }
        if (kroneckerAfter <= kroneckerFirst) {
            c.setState(changedState);
        }
        return true;
    }

    private void createNeighbourhood(String neighbourhood) {
        List<Cell> grid = Nucleon.getGrid().getGrid();
        for (Cell c: grid) {
            Neighbourhood n;
            n = new Moore(c);
            c.setNeighbourhood(n);
        }
    }

    private void monteCarloStep() {
        List<Cell> allMonteCarloGrains = new ArrayList<>(monteCarloGrains);
        int numberOfAllMCGrains = allMonteCarloGrains.size();
        for (int i = 0; i < numberOfAllMCGrains; i++) {
            int randomIndex = randomGenerator.nextInt(allMonteCarloGrains.size());
            checkNeighbourhood(allMonteCarloGrains.get(randomIndex));
            allMonteCarloGrains.remove(randomIndex);
        }
    }
}
