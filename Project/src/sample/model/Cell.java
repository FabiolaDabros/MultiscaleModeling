package sample.model;

import sample.model.Neighbourhoods.Neighbourhood;

public class Cell {
    private int x;
    private int y;
    private int state;
    private int id;
    private int futureState;
    private Neighbourhood neighbourhood;

    public Cell(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public void setNeighbourhood(Neighbourhood neighbourhood) {
        this.neighbourhood = neighbourhood;
    }

    public Neighbourhood getNeighbourhood() {
        return neighbourhood;
    }

    public int getFutureState() {
        return futureState;
    }

    public void setFutureState(int futureState) {
        this.futureState = futureState;
    }

    }
