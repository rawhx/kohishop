import java.util.*;
import menu.*;
import utils.*;

class App {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        String inputData;
        int qty;
        Pesanan pesanan = new Pesanan(); // object pesanan
        try {
            ListMenu.showMenu();
            while(true) {
                S.move(1, S.y++);System.out.println("==========================================================");
                S.move(1, S.y++);System.out.print("Pilih menu (kode) ~ cc untuk membatalkan pesanan : ");
                inputData = input.next();
                if (inputData.equalsIgnoreCase("CC")) throw new RuntimeException("Pesanan dibatalkan oleh pengguna");
                Menu menu = ListMenu.cekMenuByKode(inputData); // cek menu berdasarkan kode
                if (menu == null) {
                    S.move(1, S.y++);System.out.println("Menu tidak ditemukan!");
                    continue;
                }
                input.nextLine();
                while (true) { 
                    S.move(1, S.y++);System.out.print("qty (0/s => skip menu) : ");
                    inputData = input.nextLine().trim();
                    inputData = inputData.isEmpty() ? "1" : inputData;
                    if (inputData.equalsIgnoreCase("S") || inputData.equalsIgnoreCase("-0") || inputData.equalsIgnoreCase("0")) {
                        S.move(1, ++S.y);System.out.print("Skip menu");
                        break;
                    }

                    try {
                        qty = Integer.parseInt(inputData); //ubah string ke int 
                        if (qty < 0) {
                           throw new NumberFormatException();
                        }

                        if(menu instanceof Makanan) {
                            if (qty > 2) {
                                S.move(1, S.y++);System.out.println("Qty melebihi batas!");
                                continue;
                            }
                            pesanan.addPesanan(menu, qty); // menambahkan pesanan
                            break;
                        } else if(menu instanceof Minuman) {
                            if (qty > 3) {
                                S.move(1, S.y++);System.out.println("Qty melebihi batas!");
                                continue;
                            }
                            pesanan.addPesanan(menu, qty); // menambahkan pesanan
                            break;
                        }
                    } catch (NumberFormatException e) {
                        S.move(1, S.y++);System.out.println("Kuantitas tidak valid!");
                    }
                }

                pesanan.showPesanan(); // preview pesanan

                S.move(1, S.y++);System.out.println("==========================================================");
                S.move(1, S.y++);System.out.print("Tambah Pesanan (y/n) : "); //
                inputData = input.nextLine().trim();
                if (inputData.isEmpty() || inputData.equalsIgnoreCase("y") || inputData.equalsIgnoreCase("Y")) continue;
                break;
            };

            pesanan.showPembayaran(); // menampilkan metode pembayaran

            Kuitansi kuitansi = new Kuitansi(); // kuitansi
            kuitansi.showKuitansi(pesanan);
        } catch (Exception e) {
            S.move(1, S.y++);System.out.println(e.getMessage());
        } 

        input.close();
    }
}