import java.util.Scanner;
import pembayaran.*; 
import matauang.*;
import model.Member;
import utils.S;

public final class ProsesPembayaran {
    private Pembayaran pembayaran;
    private MataUang mataUang;

    private final Scanner input = new Scanner(System.in);
    private final double totalAsli;
    private double total;

    public int pointOld = 0;

    public ProsesPembayaran(double totalAsli) {
        this.totalAsli = totalAsli;
    }

    public Pembayaran getPembayaran() {
        return pembayaran;
    }

    public double getTotalAsli() {
        return totalAsli;
    }

    public double getTotal() {
        return total;
    }

    public MataUang getMataUang() {
        return mataUang;
    }

    public void proses(Member member) {
        while (true) {
            mataUang = pilihMataUang();
            total = (mataUang == null) ? totalAsli : mataUang.konversi(totalAsli);
            String namaMataUang = (mataUang == null) ? "IDR" : mataUang.getNama();
            pointOld = member.getPoint();

            // memotong point hanya pada mata uang IDR
            if (mataUang == null && member != null) {
                int pointBayar = member.getPointBayar();

                if (pointBayar > 0 && pointBayar >= total) {
                    int potongan = (int) Math.min(total, pointBayar);
                    total -= potongan;

                    int poinDipakai = potongan / 2; 
                    member.setPoint(pointOld - poinDipakai);
                }
            }

            tampilkanRingkasanTotal(namaMataUang);

            pembayaran = metodePembayaran();
            if (pembayaran == null) continue;

            S.move(1, S.y++); System.out.println("==========================================================");
            if (pembayaran.bayar()) {
                if (mataUang == null) member.tambahPoint(1);
                S.move(1, S.y++); System.out.println("Pembayaran berhasil!");
                break;
            } else {
                S.move(1, S.y++); System.out.println("Pembayaran gagal! Silakan coba lagi.");
                S.move(1, S.y++); System.out.println("==========================================================");
            }
        }
    }

    private void tampilkanRingkasanTotal(String namaMataUang) {
        S.move(1, S.y++); System.out.println("==========================================================");
        S.move(1, S.y++); System.out.println("Total Pesanan (IDR) : " + totalAsli);
        S.move(1, S.y++); System.out.println("Total Pesanan (" + namaMataUang + ") : " + total);
        S.move(1, S.y++); System.out.println("==========================================================");
    }

    private MataUang pilihMataUang() {
        S.move(1, S.y++); System.out.println("Konversi Mata Uang:");
        S.move(1, S.y++); System.out.println("1. IDR");
        S.move(1, S.y++); System.out.println("2. USD");
        S.move(1, S.y++); System.out.println("3. EUR");
        S.move(1, S.y++); System.out.println("4. JPY");
        S.move(1, S.y++); System.out.println("5. MYR");

        S.move(1, S.y++); System.out.print("Pilih mata uang (1-5): ");
        String pilihan = input.nextLine().trim();

        switch (pilihan) {
            case "2": return new USD();
            case "3": return new EUR();
            case "4": return new JPY();
            case "5": return new MYR();
            default:  return null;
        }
    }

    private Pembayaran metodePembayaran() {
        S.move(1, S.y++); System.out.println("Metode Pembayaran:");
        S.move(1, S.y++); System.out.println("1. Cash");
        S.move(1, S.y++); System.out.println("2. Emoney");
        S.move(1, S.y++); System.out.println("3. QRIS");
        S.move(1, S.y++); System.out.print("Pilih metode pembayaran (1/2/3): ");

        String metode = input.nextLine().trim();
        switch (metode) {
            case "1":
                return new Tunai(total);
            case "2":
                S.move(1, S.y++); System.out.print("Masukkan saldo Emoney: ");
                double saldoEmoney = inputSaldo();
                Emoney emoney = new Emoney(total, saldoEmoney);
                emoney.setAdmin(mataUang);
                return emoney;
            case "3":
                S.move(1, S.y++); System.out.print("Masukkan saldo QRIS: ");
                double saldoQris = inputSaldo();
                return new Qris(total, saldoQris);
            default:
                S.move(1, S.y++); System.out.println("Pilihan tidak valid!");
                return null;
        }
    }

    private double inputSaldo() {
        while (true) {
            try {
                return input.nextDouble();
            } catch (NumberFormatException e) {
                S.move(1, S.y++); System.out.print("Input tidak valid. Masukkan angka: ");
            }
        }
    }
}
