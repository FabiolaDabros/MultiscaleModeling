package sample.model;


import javafx.scene.paint.Color;

import java.util.HashMap;
import java.util.Map;

public class Nucleon {
    private static Grid grid;
    private static int numberOfGrains;
    private static int numberOfInclusions;
    private static String neighbourhoodType;
    private static Map<Integer, Color> grainsColors = new HashMap<>();

    private Nucleon() {}
    public static void setGrid(Grid g) {
        grid = g;
    }
    public static Grid getGrid() {
        return grid;
    }
    public static int getNumberOfGrains() {
        return numberOfGrains;
    }
    public static void setNumberOfGrains(int numberOfGrains) {
        Nucleon.numberOfGrains = numberOfGrains;
    }
    public static String getNeighbourhoodType() {
        return neighbourhoodType;
    }
    public static void setNeighbourhoodType(String neighbourhoodType) {
        Nucleon.neighbourhoodType = neighbourhoodType;
    }
    public static Map<Integer, Color> getGrainsColors() {
        return grainsColors;
    }
    public static Color getColorForGrain(int i) {
        return grainsColors.getOrDefault(i, Color.WHITE);
    }
    public static void setGrainsColors(Map<Integer, Color> grainsColors) {
        Nucleon.grainsColors = grainsColors;
    }
    public static void cleanNucleonModel() {
        grid = null;
        numberOfGrains = 0;
        neighbourhoodType = null;
        grainsColors.keySet().removeIf(key -> !(key.equals(0)));
        numberOfInclusions = 0;
    }

    public static int getNumberOfInclusions() {
        return numberOfInclusions;
    }

    public static void setNumberOfInclusions(int numberOfInclusions) {
        Nucleon.numberOfInclusions = numberOfInclusions;
    }

    public static boolean checkIfAnyEmptySpaces() {
        for (Cell c: Nucleon.getGrid().getGrid()) {
            if (c.getState() == 0)
                return true;
        }
        return false;
    }
}
