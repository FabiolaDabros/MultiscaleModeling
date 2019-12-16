package sample.model.SRX;
import sample.model.Cell;
import sample.model.Neighbourhoods.Moore;
import sample.model.Neighbourhoods.Neighbourhood;
import sample.model.Neighbourhoods.VonNeumann;
import sample.model.Nucleation;
import sample.model.Nucleon;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class SRX {
    private float coefficientJ;
    private int monteCarloSteps;
    private List<Cell> monteCarloGrains;
    private Random random;

    private Nucleation nucleation;

    public SRX(String nucleationType, String nucleationLocation, int numberOfGrains, float j, int monteCarloSteps) {
        monteCarloGrains = new ArrayList<>(Nucleon.getGrid().getGrid());
        coefficientJ = j;
        this.monteCarloSteps = monteCarloSteps;
        if (nucleationLocation.equals("On boundaries")) {
            List<Cell> borderCells = lookForCellsOnBorder();
            nucleation = new Nucleation(nucleationType, borderCells, numberOfGrains);
        }
        else {
            nucleation = new Nucleation(nucleationType, Nucleon.getGrid().getGrid(), numberOfGrains);
        }
    }


    public void growGrains(String neighbourhood) {
        prepareNeighborhood(neighbourhood);
        for (int i = 0; i < monteCarloSteps; i++){
            if (nucleation.canNucleationBeContinued()) {
                List<Cell> nucleons = nucleation.prepareToNucleation();
                monteCarloGrains.removeAll(nucleons);
            }
            monteCarloStep();
        }
    }

    public void prepareNeighborhood(String neighbourhood) {
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

    protected void monteCarloStep() {
        random = new Random();
        List<Cell> allMonteCarloGrains = new ArrayList<>(this.monteCarloGrains);
        for (int i = 0; i < allMonteCarloGrains.size(); i++) {
            int randomIndex = random.nextInt(allMonteCarloGrains.size());
            checkNeighbourhood(allMonteCarloGrains.get(randomIndex));
            allMonteCarloGrains.remove(randomIndex);
        }
    }

    private boolean checkNeighbourhood(Cell cell) {
        List<Cell> neighbours = cell.getNeighbourhood().getNeighbours();
        if (neighbours.stream().anyMatch(Cell::isRecrystal)) {
            Cell recrystalizedNeighbor;
            while (true) {
                recrystalizedNeighbor = neighbours.get(random.nextInt(neighbours.size()));
                if (recrystalizedNeighbor.isRecrystal())
                    break;
            }

            int kroneckerBefore = 0;
            int kroneckerAfter = 0;
            int candidate = recrystalizedNeighbor.getState();
            for (Cell n : neighbours) {
                if (n.getState() != cell.getState()) {
                    kroneckerBefore++;
                }
                if (n.getState() != candidate) {
                    kroneckerAfter++;
                }
            }

            float energyBefore = coefficientJ * kroneckerBefore + cell.getEnergy();
            float energyAfter = coefficientJ * kroneckerAfter;
            if (energyAfter <= energyBefore) {
                cell.setState(candidate);
                cell.setEnergy(energyAfter);
                cell.setRecrystal(true);
                monteCarloGrains.remove(cell);
            }
            return true;
        }
        return false;
    }

    private static List<Cell> lookForCellsOnBorder() {
        List<Cell> cellsOnBorder = new ArrayList<>();
        for (Cell cell : Nucleon.getGrid().getGrid()) {
            if (cell.getState() == 1 || cell.getState() == 0)
                continue;
            Neighbourhood neighbourhood = new Moore(cell);
            for (Cell n : neighbourhood.getNeighbours() ) {
                if ( n.getState() != cell.getState()) {
                    cellsOnBorder.add(n);
                }
            }
        }
        return cellsOnBorder;
    }

    public static void distributeEnergy(float energyInside, float energyOnEdges, int threshold) {
        List<Cell> cellsInTheGrid = Nucleon.getGrid().getGrid();
        Random generator = new Random();
        float insideBottom = energyInside * (100 - threshold) / 100;
        float insideTop = energyInside * (100 + threshold) / 100;

        if (energyInside == energyOnEdges) {
            for (Cell c : cellsInTheGrid) {
                c.setEnergy(insideBottom + generator.nextFloat() * (insideTop - insideBottom));
            }
        }
        else {
            List<Cell> cellsOnEdges = lookForCellsOnBorder();
            float edgesBottom = energyOnEdges * (100 - threshold) / 100;
            float edgesTop = energyOnEdges * (100 + threshold) / 100;
            for (Cell c : cellsInTheGrid) {
                if (cellsOnEdges.contains(c)) {
                    c.setEnergy(edgesBottom + generator.nextFloat() * (edgesTop - edgesBottom));
                }
                else {
                    c.setEnergy(insideBottom + generator.nextFloat() * (insideTop - insideBottom));
                }
            }
        }
    }
}
