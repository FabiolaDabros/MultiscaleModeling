package sample.model.Neighbourhoods;

import javafx.scene.paint.Color;
import sample.model.Embryo;

import java.util.HashMap;
import java.util.Map;

public class HexagonalRight {
    public void HexagonalRightNeigbourhood(int i, int j, Embryo embryos[][], Embryo embryos2[][]){
        Map<Color, Integer> map = new HashMap<>();
        Color color;
        int count = 1;
        {
            if (embryos[i][j].getState() == 0) {
                if (embryos[i][j + 1].getState() == 1) {
                    if(!embryos[i][j + 1].getColor().equals(Color.WHITE)) {
                        if (map.containsKey(embryos[i][j + 1].getColor())) {
                            map.put(embryos[i][j + 1].getColor(), count + 1);
                        } else {
                            map.put(embryos[i][j + 1].getColor(), count);
                        }
                    }
                 //   embryos2[i][j].setState(1);
                 //   embryos2[i][j].setColor(embryos[i][j + 1].getColor());
                }

                if (embryos[i + 1][j].getState() == 1) {
                    if(!embryos[i + 1][j].getColor().equals(Color.WHITE)) {
                        if (map.containsKey(embryos[i + 1][j].getColor())) {
                            map.put(embryos[i + 1][j].getColor(), count + 1);
                        } else {
                            map.put(embryos[i + 1][j].getColor(), count);
                        }
                    }
                 //   embryos2[i][j].setState(1);
                 //   embryos2[i][j].setColor(embryos[i + 1][j].getColor());
                }

                if (embryos[i][j - 1].getState() == 1) {
                    if(!embryos[i][j - 1].getColor().equals(Color.WHITE)) {
                        if (map.containsKey(embryos[i][j - 1].getColor())) {
                            map.put(embryos[i][j - 1].getColor(), count + 1);
                        } else {
                            map.put(embryos[i][j - 1].getColor(), count);
                        }
                    }
                 //   embryos2[i][j].setState(1);
                 //   embryos2[i][j].setColor(embryos[i][j - 1].getColor());
                }

                if (embryos[i - 1][j].getState() == 1) {
                    if(!embryos[i - 1][j].getColor().equals(Color.WHITE)) {
                        if (map.containsKey(embryos[i - 1][j].getColor())) {
                            map.put(embryos[i - 1][j].getColor(), count + 1);
                        } else {
                            map.put(embryos[i - 1][j].getColor(), count);
                        }
                    }
                 //   embryos2[i][j].setState(1);
                 //   embryos2[i][j].setColor(embryos[i - 1][j].getColor());
                }

                if (embryos[i - 1][j + 1].getState() == 1) {
                    if (!embryos[i - 1][j + 1].getColor().equals(Color.WHITE)) {
                        if (map.containsKey(embryos[i - 1][j + 1].getColor())) {
                            map.put(embryos[i - 1][j + 1].getColor(), count + 1);
                        } else {
                            map.put(embryos[i - 1][j + 1].getColor(), count);
                        }
                    }
                //    embryos2[i][j].setState(1);
                //    embryos2[i][j].setColor(embryos[i - 1][j + 1].getColor());
                }
                if (embryos[i + 1][j - 1].getState() == 1) {
                    if (!embryos[i + 1][j - 1].getColor().equals(Color.WHITE)) {
                        if (map.containsKey(embryos[i + 1][j - 1].getColor())) {
                            map.put(embryos[i + 1][j - 1].getColor(), count + 1);
                        } else {
                            map.put(embryos[i + 1][j - 1].getColor(), count);
                        }
                    }
                //    embryos2[i][j].setState(1);
                //    embryos2[i][j].setColor(embryos[i + 1][j - 1].getColor());
                }

            } else {
                embryos2[i][j].setState(1);
                embryos2[i][j].setColor(embryos[i][j].getColor());
            }
        }
        if(map.size()>0){
            Map.Entry<Color,Integer> maxEntry = null;
            for(Map.Entry<Color,Integer> entry : map.entrySet()){
                if(maxEntry == null || entry.getValue().compareTo(maxEntry.getValue()) >0){
                    maxEntry=entry;
                }
            }
            color= maxEntry.getKey();
            embryos2[i][j].setState(1);
            embryos2[i][j].setColor(color);
        }
    }
}
