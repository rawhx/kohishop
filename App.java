import java.util.*;
import model.*;
import utils.*;

class App {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Dapur dapur = new Dapur();
        Membership membership = new Membership();
        int totalPesanan = 0;
        S.clear();
        do {
            try {
                if(totalPesanan >= 3) {
                    ProsesDapur.prosesPesananDapur(dapur);
                    break;
                }
                S.move(1, S.y++); System.out.println("=================================================================================");
                S.move(25, S.y++); System.out.println("Selamat datang di Kohishop!");
                S.move(1, S.y++); System.out.println("=================================================================================");
                S.move(1, S.y++); System.out.println("Silakan pilih menu yang tersedia:");
                S.move(1, S.y++); System.out.println("1. Pesan Menu");
                S.move(1, S.y++); System.out.println("2. Lihat Antrian");
                S.move(1, S.y++); System.out.println("=================================================================================");
                S.move(1, S.y++); System.out.print("Pilih Menu (1/2) : ");
                String menu = scanner.next();

                switch (menu) {
                    case "1":
                        Pesanan pesanan = new Pesanan();
                        Kuitansi kuitansi = new Kuitansi();
                        pesanan.prosesPesanan();

                        Member member = membership.prosesMembership();
                        
                        dapur.addPesanan(pesanan.getDaftarPesanan());
                        double total = member.kodeA() ? pesanan.totalPesananTanpaPajak() : pesanan.totalPesanan();

                        ProsesPembayaran pembayaran = new ProsesPembayaran(total);
                        pembayaran.proses(member);

                        if (pembayaran.getMataUang() == null) member.tambahPoint((int) pesanan.totalPesananTanpaPajak() / 10);

                        kuitansi.showKuitansi(pesanan, pembayaran, member);
                        totalPesanan++;
                        S.move(1, S.y++);
                        break;
                    case "2":
                        ProsesDapur.prosesPesananDapur(dapur);
                        break;
                    default:
                        S.move(1, S.y++);System.out.println("==========================================================");
                        S.move(1, S.y++);System.out.println("Pilihan tidak valid! Silakan pilih 1 atau 2.");
                        continue;
                }

            } catch (Exception e) {
                S.move(1, S.y++);
                System.out.println(e.getMessage());
            }
        } while (totalPesanan <= 3);
    }
}