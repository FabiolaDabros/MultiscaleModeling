package sample.model.Neighbourhoods;

import sample.model.Cell;
import sample.model.Grid;
import sample.model.Nucleon;

public class VonNeumann extends Neighbourhood {
    public VonNeumann(Cell cell) {
        this.neighbours(cell);
    }

    @Override
    protected void neighbours(Cell cell) {
        Grid grid = Nucleon.getGrid();

        int cx = cell.getX();
        int cy = cell.getY();

        super.addCellToList( grid.getCell(cx, cy - 1) );
        super.addCellToList( grid.getCell(cx - 1, cy) );
        super.addCellToList( grid.getCell(cx + 1, cy) );
        super.addCellToList( grid.getCell(cx, cy + 1) );
    }
}
