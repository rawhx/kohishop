import java.util.*;
import menu.*;
import model.*;
import utils.*;

class App {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in); 
        Pesanan pesanan = new Pesanan();
        Membership membership = new Membership();
        String akhirProses;

        try {
            do {
                ListMenu.showMenu();
    
                while (true) {
                    S.move(1, S.y++); System.out.println("==========================================================");
                    S.move(1, S.y++); System.out.print("Pilih menu (kode) ~ cc untuk membatalkan pesanan : ");
                    String kodeMenu = scanner.next();
    
                    if (kodeMenu.equalsIgnoreCase("CC")) {
                        throw new RuntimeException("Pesanan dibatalkan oleh pengguna");
                    }
    
                    Menu menuDipilih = ListMenu.cekMenuByKode(kodeMenu);
                    if (menuDipilih == null) {
                        S.move(1, S.y++); System.out.println("Menu tidak ditemukan!");
                        continue;
                    }
    
                    scanner.nextLine(); 
                    if (!inputQty(scanner, pesanan, menuDipilih)) continue;
    
                    pesanan.showPesanan();
    
                    S.move(1, S.y++); System.out.println("==========================================================");
                    S.move(1, S.y++); System.out.print("Tambah Pesanan (y/n) : ");
                    String tambah = scanner.nextLine().trim();
                    if (tambah.isEmpty() || tambah.equalsIgnoreCase("y")) continue;
                    break;
                }
    
                Member member = null;
                do {
                    member = membership.prosesMembership();
                } while (member == null);
                
    
                ProsesPembayaran bayar = new ProsesPembayaran(member.kodeA() ? pesanan.totalPesanan() : pesanan.totalPesananTanpaPajak());
                bayar.proses(member);
    
                Kuitansi kuitansi = new Kuitansi();
                kuitansi.showKuitansi(pesanan, bayar, member);
                
                S.move(1, S.y++); System.out.print("Proses lagi? (y/n) : ");
                akhirProses = scanner.nextLine().trim();
            } while (akhirProses.equalsIgnoreCase("y") || akhirProses.isEmpty());

        } catch (Exception e) {
            S.move(1, S.y++); System.out.println(e.getMessage());
        }

        scanner.close();
    }

    private static boolean inputQty(Scanner scanner, Pesanan pesanan, Menu menu) {
        while (true) {
            S.move(1, S.y++); System.out.print("qty (0/s => skip menu) : ");
            String qtyInput = scanner.nextLine().trim();
            qtyInput = qtyInput.isEmpty() ? "1" : qtyInput;

            if (qtyInput.equalsIgnoreCase("s") || qtyInput.equals("0")) {
                S.move(1, ++S.y); System.out.println("Skip menu");
                return false;
            }

            try {
                int qty = Integer.parseInt(qtyInput);
                if (qty < 0) throw new NumberFormatException();

                if (menu instanceof Makanan && qty > 2) {
                    S.move(1, S.y++); System.out.println("Qty melebihi batas maksimal (2)!");
                    continue;
                }

                if (menu instanceof Minuman && qty > 3) {
                    S.move(1, S.y++); System.out.println("Qty melebihi batas maksimal (3)!");
                    continue;
                }

                pesanan.addPesanan(menu, qty);
                return true;

            } catch (NumberFormatException e) {
                S.move(1, S.y++); System.out.println("Kuantitas tidak valid!");
            }
        }
    }
}