package sample.model;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class Inclusion {

    int counter = 1;
    public void addCircleInclusion(int grain, int size){

        Grid grid = Nucleon.getGrid();
        Cell myGrain = grid.getCellAsListPosition(grain);
        int x = myGrain.getX();
        int y = myGrain.getY();
        setCircleBorder(x, y, size, counter);

        fillCircle(x, y, size, counter);
        counter++;
    }

    public void addSquareInclusion(int grain, int size){
        Grid grid = Nucleon.getGrid();
        Cell myGrain = grid.getCellAsListPosition(grain);
        int x = myGrain.getX();
        int y = myGrain.getY();
        int half = size/2;

        if (size % 2 == 0) {
            half--;
        }

        for (int i = x - half; i <= x + size/2; i++) {
            for (int j = y - size/2; j <= y + half; j++) {
                Nucleon.getGrid().getCell(i, j).setFutureState(1);
            }

        }
    }

    public void addRandomlyOnStart(int numberOfInclusions, int size, String inclusionType) {
        Nucleon.setNumberOfInclusions(numberOfInclusions);
        Set<Integer> setOfInclusions = new HashSet<>();
        Random generator = new Random();

        for (int i = 0; i < numberOfInclusions; i++) {
            if(Nucleon.checkIfAnyEmptySpaces()) {
                int random = generator.nextInt(Nucleon.getGrid().getGrid().size());
                if (setOfInclusions.contains(random)) {
                    i--;
                } else {
                    setOfInclusions.add(random);
                }

                if (inclusionType == "circle") {
                    addCircleInclusion(random, size);
                } else {
                    addSquareInclusion(random, size);
                }
            }
        }

        for (Cell c: Nucleon.getGrid().getGrid()) {
            if (c.getFutureState() == 1)
                c.setState(1);
        }
    }

    public void addOnBoundaries(int numberOfInclusions, int size, String inclusionType) {
        Nucleon.setNumberOfInclusions(numberOfInclusions);
        Set<Integer> setOfInclusions = new HashSet<>();
        Random generator = new Random();

        for (int i = 0; i < numberOfInclusions; i++) {
            int random;
            boolean againCheckIfBoundary = true;
            while (againCheckIfBoundary) {
                random = generator.nextInt(Nucleon.getGrid().getGrid().size());
                Cell cell = Nucleon.getGrid().getCellAsListPosition(random);

                for (int j = cell.getX() - 1; j < cell.getX() + 1; j++) {
                    for (int k = cell.getY() - 1; k < cell.getY() + 1; k++) { try {
                        int state = Nucleon.getGrid().getCell(j, k).getState();
                        if (state != 1 && state != cell.getState()) {
                           if(setOfInclusions.contains(random)) {
                               i--;
                           } else {
                               setOfInclusions.add(random);
                               againCheckIfBoundary = false;
                               if(inclusionType == "circle") {
                                   addCircleInclusion(random, size);
                               } else {
                                   addSquareInclusion(random, size);
                               }
                               break;
                           }
                        } } catch (NullPointerException e) { continue; }
                    }
                    if (!againCheckIfBoundary)
                        break;
                }
            }
        }

        for (Cell c: Nucleon.getGrid().getGrid()) {
            if (c.getFutureState() == 1)
                c.setState(1);
        }
    }

    void fillCircle(int circleX, int circleY, int radius, int counter) {
        Nucleon.getGrid().getCell(circleX, circleY).setFutureState(1);
        for (int i = circleY - radius + 1; i < circleY + radius; i++) {
            for (int j = circleX;  ; j--) {
                if (i == circleY && j == circleX)
                    continue;
                try { Cell cell = Nucleon.getGrid().getCell(j, i);
                    if (cell.getFutureState() == 1 && cell.getId() == counter && cell.getX() > circleX - radius-1)
                        break;
                    cell.setState(1);
                }
                catch (NullPointerException e) {
                    break;
                }
            }

            for (int j = circleX;  ; j++) {
                if (i == circleY && j == circleX)
                    continue;
                try {
                    Cell c = Nucleon.getGrid().getCell(j, i);
                    if (c.getFutureState() == 1 && c.getId() == counter && c.getX() < circleX + radius+1) {
                        break;
                    }
                    c.setState(1);
                }
                catch (NullPointerException e) {
                    break;
                }
            }

        }
    }

    void setCircleBorder ( int circleX, int circleY, int radius, int counter)
    {
        int x = 0, y = radius;
        int diameter = 3 - 2 * radius;
        drawCircle(circleX, circleY, x, y,counter);
        while (y >= x) {
            x++;
            if (diameter > 0) {
                y--;
                diameter = diameter + 4 * (x - y) + 10;
            } else
                diameter = diameter + 4 * x + 6;
            drawCircle(circleX, circleY, x, y, counter);
        }
    }

    void drawCircle ( int circleX, int circleY, int x, int y, int counter)
    {

        Nucleon.getGrid().getCell(circleX + x, circleY + y).setFutureState(1);
        Nucleon.getGrid().getCell(circleX - x, circleY + y).setFutureState(1);
        Nucleon.getGrid().getCell(circleX + x, circleY - y).setFutureState(1);
        Nucleon.getGrid().getCell(circleX - x, circleY - y).setFutureState(1);
        Nucleon.getGrid().getCell(circleX + y, circleY + x).setFutureState(1);
        Nucleon.getGrid().getCell(circleX - y, circleY + x).setFutureState(1);
        Nucleon.getGrid().getCell(circleX + y, circleY - x).setFutureState(1);
        Nucleon.getGrid().getCell(circleX - y, circleY - x).setFutureState(1);

        Nucleon.getGrid().getCell(circleX + x, circleY + y).setId(counter);
        Nucleon.getGrid().getCell(circleX - x, circleY + y).setId(counter);
        Nucleon.getGrid().getCell(circleX + x, circleY - y).setId(counter);
        Nucleon.getGrid().getCell(circleX - x, circleY - y).setId(counter);
        Nucleon.getGrid().getCell(circleX + y, circleY + x).setId(counter);
        Nucleon.getGrid().getCell(circleX - y, circleY + x).setId(counter);
        Nucleon.getGrid().getCell(circleX + y, circleY - x).setId(counter);
        Nucleon.getGrid().getCell(circleX - y, circleY - x).setId(counter);
    }

}
