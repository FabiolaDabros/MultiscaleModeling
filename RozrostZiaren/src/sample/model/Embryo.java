package sample.model;
import javafx.scene.paint.Color;
import java.util.HashMap;
import java.util.Map;

public class Embryo {

    private int id;
    private int state;
    private Color color;
    private static int staticId;
    private static Map<Integer, Color> colorsMap = new HashMap<Integer, Color>();

    public Embryo() {
        state = 0;
        id = staticId;
        staticId++;
       // Color.color(Color.WHITE);
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public static int getStaticId() {
        return staticId;
    }

    public static void setStaticId(int staticId) {
        Embryo.staticId = staticId;
    }

    public static Map<Integer, Color> getColorsMap() {
        return colorsMap;
    }

    public static void setColorsMap(Map<Integer, Color> colorsMap) {
        Embryo.colorsMap = colorsMap;
    }
    public static void putColorsMap(int id, Color color) {
        Embryo.colorsMap.put(id,color);
    }
}
