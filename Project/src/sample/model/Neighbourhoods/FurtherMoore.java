package sample.model.Neighbourhoods;

import sample.model.Cell;
import sample.model.Grid;
import sample.model.Nucleon;

public class FurtherMoore extends Neighbourhood {
    public FurtherMoore(Cell c) {
        this.neighbours(c);
    }

    @Override
    protected void neighbours(Cell cell) {
        Grid grid = Nucleon.getGrid();

        int cx = cell.getX();
        int cy = cell.getY();

        super.addCellToList(grid.getCell(cx - 1, cy - 1) );
        super.addCellToList(grid.getCell(cx + 1, cy - 1) );
        super.addCellToList(grid.getCell(cx - 1, cy + 1) );
        super.addCellToList(grid.getCell(cx + 1, cy + 1) );

    }
}
