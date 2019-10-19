package sample.model.Neighbourhoods;

import sample.model.Cell;

import java.util.ArrayList;
import java.util.List;

public abstract class Neighbourhood {
    private List<Cell> neighbours = new ArrayList<>();

    protected abstract void neighbours(Cell cell);

    protected void addCellToList(Cell cell){
        if (cell != null){
            this.neighbours.add(cell);
        }
    }

    public List<Cell> getNeighbours() {
        return this.neighbours;
    }
}
