package sample.model;

public class PeriodicPoint
{
    public int X;
    public int Y;
    public int PeriodicX;
    public int PeriodicY;

    public PeriodicPoint(int x, int y, int width, int height, int radius)
    {
        X = x;
        Y = y;
        if (x <= radius)
            PeriodicX = width + x;
        else if (width - radius <= x)
            PeriodicX = x - width;
        else
            PeriodicX = x;
        if (y <= radius)
            PeriodicY = height + y;
        else if (height - radius <= y)
            PeriodicY = y - height;
        else
            PeriodicY = y;
    }

    public int getX() {
        return X;
    }

    public void setX(int x) {
        X = x;
    }

    public int getY() {
        return Y;
    }

    public void setY(int y) {
        Y = y;
    }

    public int getPeriodicX() {
        return PeriodicX;
    }

    public void setPeriodicX(int periodicX) {
        PeriodicX = periodicX;
    }

    public int getPeriodicY() {
        return PeriodicY;
    }

    public void setPeriodicY(int periodicY) {
        PeriodicY = periodicY;
    }
}
