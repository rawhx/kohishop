import java.util.*;
import menu.*;
import utils.*;
import model.*;

public class Pesanan {
    private List<ItemPesanan> daftarPesanan = new LinkedList<>();

    public void addPesanan(Menu menu, int qty) {
        int jumlahMakanan = 0;
        int jumlahMinuman = 0;

        for (ItemPesanan item : daftarPesanan) {
            if (item.menu() instanceof Makanan) jumlahMakanan++;
            else if (item.menu() instanceof Minuman) jumlahMinuman++;
        }

        if (menu instanceof Makanan && jumlahMakanan >= 5) {
            System.out.println("Jumlah pesanan makanan sudah mencapai batas maksimum (5)!");
            return;
        }

        if (menu instanceof Minuman && jumlahMinuman >= 5) {
            System.out.println("Jumlah pesanan minuman sudah mencapai batas maksimum (5)!");
            return;
        }

        daftarPesanan.add(new ItemPesanan(menu, qty));
    }

    public double totalPesanan() {
        double total = 0;
        for (ItemPesanan item : daftarPesanan) {
            total += (item.menu().getHarga() + item.menu().pajak()) * item.qty();
        }
        return Math.round(total * 100.0) / 100.0;
    }

    public double totalPesananTanpaPajak() {
        double total = 0;
        for (ItemPesanan item : daftarPesanan) {
            total += item.menu().getHarga() * item.qty();
        }
        return Math.round(total * 100.0) / 100.0;
    }

    public List<ItemPesanan> getDaftarPesanan() {
        return this.daftarPesanan;
    }

    public final void showPesanan() {
        if (daftarPesanan.isEmpty()) {
            S.move(1, S.y++); System.out.println("Belum ada pesanan.");
            return;
        }

        Sort.selectionSortHarga(daftarPesanan);

        List<ItemPesanan> minumanList = new ArrayList<>();
        List<ItemPesanan> makananList = new ArrayList<>();

        for (ItemPesanan item : daftarPesanan) {
            if (item.menu() instanceof Minuman) {
                minumanList.add(item);
            } else if (item.menu() instanceof Makanan) {
                makananList.add(item);
            }
        }

        tampilkanKategori("Makanan", makananList);
        tampilkanKategori("Minuman", minumanList);
    }

    private void tampilkanKategori(String judul, List<ItemPesanan> list) {
        if (list.isEmpty()) return;

        S.move(1, S.y); System.out.print("Kode");
        S.move(10, S.y); System.out.print(judul);
        S.move(50, S.y++); System.out.println("Kuantitas");

        for (ItemPesanan item : list) {
            S.move(1, S.y); System.out.print(item.menu().getKode());
            S.move(10, S.y); System.out.print(item.menu().getNama());
            S.move(50, S.y++); System.out.println(item.qty());
        }
    }
}