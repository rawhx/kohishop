import java.util.*;
import matauang.*;
import menu.*;
import pembayaran.*;
import utils.*;
import model.*;

public class Pesanan {
    private List<ItemPesanan> daftarPesanan = new LinkedList<>();
    private Pembayaran pembayaran;
    private MataUang mataUang = null;

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

    public Pembayaran getPembayaran() {
        return this.pembayaran;
    }

    public MataUang getMataUang() {
        return this.mataUang;
    }

    public final void showPesanan() {
        if (daftarPesanan.isEmpty()) {
            S.move(1, S.y++); System.out.println("Belum ada pesanan.");
            return;
        }

        List<ItemPesanan> sortedList = new ArrayList<>(daftarPesanan);
        Sort.selectionSortHarga(sortedList);

        List<ItemPesanan> minumanList = new ArrayList<>();
        List<ItemPesanan> makananList = new ArrayList<>();

        for (ItemPesanan item : sortedList) {
            if (item.menu() instanceof Minuman) {
                minumanList.add(item);
            } else if (item.menu() instanceof Makanan) {
                makananList.add(item);
            }
        }

        tampilkanKategori("Minuman", minumanList);
        tampilkanKategori("Makanan", makananList);
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

    public final MataUang showMataUang() {
        Scanner input = new Scanner(System.in);
        S.move(1, S.y++); System.out.println("==========================================================");
        S.move(1, S.y++); System.out.println("Konversi Mata Uang : "); 
        S.move(1, S.y++); System.out.println("1. IDR");
        S.move(1, S.y++); System.out.println("2. USD");
        S.move(1, S.y++); System.out.println("3. EUR");
        S.move(1, S.y++); System.out.println("4. JPY");
        S.move(1, S.y++); System.out.println("5. MYR");
        MataUang mataUang = null;
        S.move(1, S.y++); System.out.print("Pilih mata uang (1/2/3/4/5) : ");
        String inputMataUang = input.next().trim();
        switch (inputMataUang) {
            case "1": mataUang = null; break;
            case "2": mataUang = new USD(); break;
            case "3": mataUang = new EUR(); break;
            case "4": mataUang = new JPY(); break;
            case "5": mataUang = new MYR(); break;
            default: mataUang = null; break;
        }
        return mataUang;
    }

    public final void showPembayaran() {
        Scanner input = new Scanner(System.in);
        while (true) {
            mataUang = showMataUang();
            double total = mataUang == null ? totalPesanan() : mataUang.konversi(totalPesanan());
            String namaMataUang = mataUang == null ? "IDR" : mataUang.getNama();

            S.move(1, S.y++); System.out.println("==========================================================");
            S.move(1, S.y++); System.out.println("Total Pesanan (IDR) : " + totalPesanan());
            S.move(1, S.y++); System.out.println("Total Pesanan (" + namaMataUang + ") : " + total);
            S.move(1, S.y++); System.out.println("==========================================================");

            S.move(1, S.y++); System.out.println("Metode Pembayaran : ");
            S.move(1, S.y++); System.out.println("1. Cash");
            S.move(1, S.y++); System.out.println("2. Emoney");
            S.move(1, S.y++); System.out.println("3. QRIS");
            S.move(1, S.y++); System.out.print("Pilih metode pembayaran (1/2/3) : ");
            String inputPembayaran = input.next();

            switch (inputPembayaran) {
                case "1":
                    pembayaran = new Tunai(total);
                    break;
                case "2":
                    S.move(1, S.y++); System.out.print("Masukkan saldo emoney : ");
                    double saldoEmoney = input.nextDouble();
                    pembayaran = new Emoney(total, saldoEmoney);
                    ((Emoney) pembayaran).setAdmin(mataUang);
                    break;
                case "3":
                    S.move(1, S.y++); System.out.print("Masukkan saldo QRIS : ");
                    double saldoQris = input.nextDouble();
                    pembayaran = new Qris(total, saldoQris);
                    break;
                default:
                    S.move(1, S.y++); System.out.println("Metode tidak valid, ulangi.");
                    continue;
            }

            S.move(1, S.y++); System.out.println("==========================================================");
            if (pembayaran.bayar()) {
                S.move(1, S.y++); System.out.println("Pembayaran berhasil!");
                break;
            } else {
                S.move(1, S.y++); System.out.println("Pembayaran gagal! Silakan pilih ulang.");
                S.move(1, S.y++); System.out.println("==========================================================");
            }
        }
    }
}