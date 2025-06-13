import java.util.Deque;
import java.util.List;

import model.Dapur;
import model.ItemPesanan;
import utils.S;
import utils.Sort;

public class ProsesDapur {
    public static void prosesPesananDapur(Dapur dapur) {
        S.clear();S.y = 1;
        S.move(1, S.y++);System.out.println("==========================================================");
        S.move(20, S.y++);System.out.println("PROSES DAPUR");
        S.move(1, S.y++);System.out.println("==========================================================");
        
        if (!dapur.listMakanan().isEmpty() || !dapur.listMinuman().isEmpty()) {
            S.move(1, S.y);System.out.println("Menu");
            S.move(45, S.y);System.out.println("Qty");
            S.move(65, S.y++);System.out.println("Harga (IDR)");
            listItem("Makanan", dapur.listMakanan());
            listItem("Minuman", dapur.listMinuman());
        } else S.move(1, S.y++);System.out.println("Tidak ada Proses");
    }

    private void listItem(String nama, List<ItemPesanan> data) {
        if(data.isEmpty()) return;
        Sort.selectionSortHargaTerbesar(data);
        S.move(1, S.y++);System.out.println(nama + " :");
        for (ItemPesanan item : data) {
            S.move(1, S.y);System.out.println(item.menu().getNama());
            S.move(45, S.y);System.out.println(item.qty());
            S.move(65, S.y++);System.out.println(item.menu().getHarga());
        }
    }

    private void listItem(String nama, Deque<ItemPesanan> data) {
        if(data.isEmpty()) return;
        S.move(1, S.y++);System.out.println(nama + " :");
        for (ItemPesanan item : data) {
            S.move(1, S.y);System.out.println(item.menu().getNama());
            S.move(45, S.y);System.out.println(item.qty());
            S.move(65, S.y++);System.out.println(item.menu().getHarga());
        }
    }
}
