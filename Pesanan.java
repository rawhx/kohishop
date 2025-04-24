import java.util.Scanner;
import menu.*;
import pembayaran.*;
import matauang.*;
import utils.S;

public class Pesanan {
    private Object pesananMakanan[][] = new Object[5][2];
    private Object pesananMinuman[][] = new Object[5][2];

    private Pembayaran pembayaran;
    private MataUang mataUang = null;

    public void addPesanan(Menu menu, int qty) {
        if (menu instanceof Makanan) {
            boolean added = false;
            for (int i = 0; i < pesananMakanan.length; i++) {
                if (pesananMakanan[i][0] == null) {
                    pesananMakanan[i][0] = menu;
                    pesananMakanan[i][1] = qty;
                    added = true;
                    break;
                }
            }
            if (!added) {
                System.out.println("Pesanan makanan sudah penuh!");
            }
        } else if (menu instanceof Minuman) {
            boolean added = false;
            for (int i = 0; i < pesananMinuman.length; i++) {
                if (pesananMinuman[i][0] == null) {
                    pesananMinuman[i][0] = menu;
                    pesananMinuman[i][1] = qty;
                    added = true;
                    break;
                }
            }
            if (!added) {
                System.out.println("Pesanan minuman sudah penuh!");
            }
        }
    }

    public double totalPesanan() {
        double jmlh = 0;

        for (int i = 0; i < pesananMakanan.length; i++) {
            if (pesananMakanan[i][0] != null) {
                Menu makanan = (Menu) pesananMakanan[i][0]; // polimorfisme array object -> menu
                jmlh += (makanan.getHarga() * (int) pesananMakanan[i][1]) + (makanan.pajak() * (int) pesananMakanan[i][1]);
            }
        }
        for (int i = 0; i < pesananMinuman.length; i++) {
            if (pesananMinuman[i][0] != null) {
                Menu minuman = (Menu) pesananMinuman[i][0]; // polimorfisme array object -> menu
                jmlh += (minuman.getHarga() * (int) pesananMinuman[i][1]) + (minuman.pajak() * (int) pesananMinuman[i][1]);
            }
        }
        
        return Math.round(jmlh * 100.0) / 100.0;
    }

    public double totalPesananTanpaPajak() {
        double jmlh = 0;

        for (int i = 0; i < pesananMakanan.length; i++) {
            if (pesananMakanan[i][0] != null) {
                Menu makanan = (Menu) pesananMakanan[i][0]; // polimorfisme array object -> menu
                jmlh += (makanan.getHarga() * (int) pesananMakanan[i][1]);
            }
        }
        for (int i = 0; i < pesananMinuman.length; i++) {
            if (pesananMinuman[i][0] != null) {
                Menu minuman = (Menu) pesananMinuman[i][0]; // polimorfisme array object -> menu
                jmlh += (minuman.getHarga() * (int) pesananMinuman[i][1]);
            }
        }
        
        return Math.round(jmlh * 100.0) / 100.0;
    }

    public Object[][] getPesananMakanan() {
        return this.pesananMakanan;
    }

    public Object[][] getPesananMinuman() {
        return this.pesananMinuman;
    }

    public Pembayaran getPembayaran() {
        return this.pembayaran;
    }

    public MataUang getMataUang() {
        return this.mataUang;
    }

    public final void showPesanan() {
        boolean adaMinuman = false, adaMakanan = false;
        
        for (int i = 0; i < pesananMinuman.length; i++) {
            if (pesananMinuman[i][0] != null) {
                adaMinuman = true;
            }
            if (pesananMakanan[i][0] != null) {
                adaMakanan = true;
            }
        }

        if (adaMinuman) {
            S.move(1, S.y); System.out.print("Kode");
            S.move(10, S.y); System.out.print("Menu Minuman");
            S.move(50, S.y++); System.out.println("Kuantitas");
        }

        for (int i = 0; i < pesananMinuman.length; i++) {
            if (pesananMinuman[i][0] != null) {
                Menu minuman = (Menu) pesananMinuman[i][0]; // polimorfisme array object -> menu
                S.move(1, S.y); System.out.print(minuman.getKode());
                S.move(10, S.y); System.out.print(minuman.getNama());
                S.move(50, S.y++); System.out.println(pesananMinuman[i][1]);
            }
        }

        
        if (adaMakanan) {
            S.move(1, S.y); System.out.print("Kode");
            S.move(10, S.y); System.out.print("Menu Makanan");
            S.move(50, S.y++); System.out.println("Kuantitas");    
        }

        for (int i = 0; i < pesananMakanan.length; i++) {
            if (pesananMakanan[i][0] != null) {
                Menu makanan = (Menu) pesananMakanan[i][0]; // polimorfisme array object -> menu
                S.move(1, S.y); System.out.print(makanan.getKode());
                S.move(10, S.y); System.out.print(makanan.getNama());
                S.move(50, S.y++); System.out.println(pesananMakanan[i][1]);
            }
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
        String inputMataUang;
        MataUang mataUang = null;
        while (true) {
            S.move(1, S.y++); System.out.print("Pilih mata uang (1/2/3/4/5) : ");
            inputMataUang = input.next().trim();
            switch (inputMataUang) {
                case "1":
                    mataUang = null;
                    break;
                case "2":
                    mataUang = new USD();
                    break;
                case "3":
                    mataUang = new EUR();
                    break;
                case "4":
                    mataUang = new JPY();
                    break;
                case "5":
                    mataUang = new MYR();
                    break;
                default:
                    mataUang = null;
                    break; 
            }
            break;
        }
        return mataUang;
    }

    public final void showPembayaran() {
        Scanner input = new Scanner(System.in);
    
        while (true) {
            mataUang = showMataUang(); // Pilih mata uang
            double total = mataUang == null ? totalPesanan() : mataUang.konversi(totalPesanan()); // total pesanan sudah dikonversi
            String namaMataUang = mataUang == null ? "IDR" : mataUang.getNama(); 
            S.move(1, S.y++);System.out.println("==========================================================");
            S.move(1, S.y++); System.out.println("Total Pesanan (IDR) : " + totalPesanan());
            S.move(1, S.y++); System.out.println("Total Pesanan ("+ namaMataUang +") : " + total);
            S.move(1, S.y++);System.out.println("==========================================================");

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
                    ((Emoney) pembayaran).setAdmin(mataUang); // set admin fee
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
    
            S.move(1, S.y++);System.out.println("==========================================================");
            if (pembayaran.bayar()) {
                S.move(1, S.y++); System.out.println("Pembayaran berhasil!");
                break;
            } else {
                S.move(1, S.y++); System.out.println("Pembayaran gagal! Silakan pilih mata uang dan metode lagi.");
            }
            S.move(1, S.y++);System.out.println("==========================================================");
        }
    }    
}