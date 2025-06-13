package utils;

import java.util.List;
import menu.Menu;
import model.*;

public class Sort {
    public static void selectionSortKode(List<Menu> list) {
        for (int i = 0; i < list.size() - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < list.size(); j++) {
                String kodeJ = list.get(j).getKode();
                String kodeMin = list.get(minIndex).getKode();
                if (kodeJ.compareTo(kodeMin) < 0) {
                    minIndex = j;
                }
            }
            if (minIndex != i) {
                Menu temp = list.get(i);
                list.set(i, list.get(minIndex));
                list.set(minIndex, temp);
            }
        }
    }

    public static void selectionSortHarga(List<ItemPesanan> list) {
        for (int i = 0; i < list.size() - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < list.size(); j++) {
                double hargaJ = list.get(j).menu().getHarga();
                double hargaMin = list.get(minIndex).menu().getHarga();
                if (hargaJ < hargaMin) {
                    minIndex = j;
                }
            }

            if (minIndex != i) {
                ItemPesanan temp = list.get(i);
                list.set(i, list.get(minIndex));
                list.set(minIndex, temp);
            }
        }
    }

    public static void selectionSortHargaTerbesar(List<ItemPesanan> list) {
        for (int i = 0; i < list.size() - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < list.size(); j++) {
                double hargaJ = list.get(j).menu().getHarga();
                double minIdex = list.get(minIndex).menu().getHarga();
                if (hargaJ > minIdex) {
                    minIndex = j;
                }
            }

            if (minIndex != i) {
                ItemPesanan temp = list.get(i);
                list.set(i, list.get(minIndex));
                list.set(minIndex, temp);
            }
        }
    }

}
