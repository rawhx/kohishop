package model;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

import menu.*;

public class Dapur {
    private List<ItemPesanan> daftarMakanan = new ArrayList<>();
    private Deque<ItemPesanan> daftarMinuman = new ArrayDeque<>();

    public void addPesanan(List<ItemPesanan> pesanan) {
        for (ItemPesanan item : pesanan) {
            if (item.menu() instanceof Minuman) {
                daftarMinuman.addFirst(item);
            } else if (item.menu() instanceof Makanan) {
                daftarMakanan.add(item);
            }
        }
    }

    public List<ItemPesanan> listMakanan() {
        return daftarMakanan;
    }

    public Deque<ItemPesanan> listMinuman() {
        return daftarMinuman;
    }

    public void clear() {
        daftarMakanan.clear();
        daftarMinuman.clear();
    }
}
