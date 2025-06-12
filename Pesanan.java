import java.util.*;
import menu.*;
import utils.*;
import model.*;

public class Pesanan {
    private final List<ItemPesanan> daftarPesanan = new LinkedList<>();
    private static final int MaxMakanan = 5;
    private static final int MaxMinuman = 5;
    private final Scanner scanner = new Scanner(System.in);

    public void addPesanan(Menu menu, int qty) {
        int jumlahMakanan = 0;
        int jumlahMinuman = 0;

        for (ItemPesanan item : daftarPesanan) {
            if (item.menu() instanceof Makanan) jumlahMakanan++;
            else if (item.menu() instanceof Minuman) jumlahMinuman++;
        }

        if (menu instanceof Makanan && jumlahMakanan >= MaxMakanan) {
            System.out.println("Jumlah pesanan makanan sudah mencapai batas maksimum (5)!");
            return;
        }

        if (menu instanceof Minuman && jumlahMinuman >= MaxMinuman) {
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

    public void showPesanan() {
        if (daftarPesanan.isEmpty()) {
            S.move(1, S.y++); System.out.println("Belum ada pesanan.");
            return;
        }

        Sort.selectionSortHarga(daftarPesanan);

        List<ItemPesanan> makananList = new ArrayList<>();
        List<ItemPesanan> minumanList = new ArrayList<>();

        for (ItemPesanan item : daftarPesanan) {
            if (item.menu() instanceof Makanan) makananList.add(item);
            else if (item.menu() instanceof Minuman) minumanList.add(item);
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

    public void prosesPesanan() {
        ListMenu.showMenu();
        inputPesanan();
    }

    private void inputPesanan() {
        while (true) {
            S.move(1, S.y++);
            System.out.println("==========================================================");
            S.move(1, S.y++);
            System.out.print("Pilih menu (kode) ~ cc untuk membatalkan pesanan : ");
            String kodeMenu = scanner.next();

            if (kodeMenu.equalsIgnoreCase("CC")) {
                throw new RuntimeException("Pesanan dibatalkan oleh pengguna");
            }

            Menu menu = ListMenu.cekMenuByKode(kodeMenu);
            if (menu == null) {
                S.move(1, S.y++); System.out.println("Menu tidak ditemukan!");
                continue;
            }

            scanner.nextLine();
            if (!inputQty(menu)) continue;

            showPesanan();

            S.move(1, S.y++);
            System.out.println("==========================================================");
            S.move(1, S.y++);
            System.out.print("Tambah Pesanan (y/n) : ");
            String tambah = scanner.nextLine().trim();
            if (!tambah.equalsIgnoreCase("y") && !tambah.isEmpty()) break;
        }
    }

    private boolean inputQty(Menu menu) {
        while (true) {
            S.move(1, S.y++); System.out.print("qty (0/s => skip menu) : ");
            String input = scanner.nextLine().trim();
            input = input.isEmpty() ? "1" : input;

            if (input.equalsIgnoreCase("s") || input.equals("0")) {
                S.move(1, ++S.y); System.out.println("Skip menu");
                return false;
            }

            try {
                int qty = Integer.parseInt(input);
                if (qty < 0) throw new NumberFormatException();

                if (menu instanceof Makanan && qty > 2) {
                    S.move(1, S.y++); System.out.println("Qty melebihi batas maksimal (2)!");
                    continue;
                }

                if (menu instanceof Minuman && qty > 3) {
                    S.move(1, S.y++); System.out.println("Qty melebihi batas maksimal (3)!");
                    continue;
                }

                addPesanan(menu, qty);
                return true;
            } catch (NumberFormatException e) {
                S.move(1, S.y++); System.out.println("Kuantitas tidak valid!");
            }
        }
    }
}