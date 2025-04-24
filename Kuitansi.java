import menu.Menu;
import pembayaran.*;
import utils.S;

class Kuitansi {
    public final void showKuitansi(Pesanan pesanan) {
        S.clear();S.y = 1;
        S.move(1, S.y++);System.out.println("=============================================================================================");
        S.move(40, S.y++);System.out.println("KUITANSI PESANAN");
        S.move(1, S.y);System.out.println("=============================================================================================");
        S.move(1, ++S.y);System.out.println("Kode");
        S.move(10, S.y);System.out.print("Nama");
        S.move(35, S.y);System.out.print("Qty");
        S.move(45, S.y);System.out.println("Harga (Rp)");
        S.move(60, S.y);System.out.println("Pajak (Rp)");
        S.move(80, S.y);System.out.println("SubTotal (Rp)");
        S.move(1, ++S.y);System.out.println("---------------------------------------------------------------------------------------------");

        Object[][] pesananMakanan = pesanan.getPesananMakanan();
        Object[][] pesananMinuman = pesanan.getPesananMinuman();

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
            S.move(1, ++S.y); System.out.print("Minuman :");
            for (int i = 0; i < pesananMinuman.length; i++) {
                if (pesananMinuman[i][0] != null) {
                    Menu minuman = (Menu) pesananMinuman[i][0]; // polimorfisme array object -> menu
                    S.move(1, ++S.y); System.out.print(minuman.getKode());
                    S.move(10, S.y); System.out.print(minuman.getNama());
                    S.move(35, S.y); System.out.println(pesananMinuman[i][1]);
                    S.move(45, S.y); System.out.println(minuman.getHarga());
                    S.move(60, S.y); System.out.println(minuman.pajak() * (int) pesananMinuman[i][1]);
                    S.move(80, S.y); System.out.println(Math.round(((minuman.getHarga() * (int) pesananMinuman[i][1]) + (minuman.pajak() * (int) pesananMinuman[i][1])) * 100.0) / 100.0);
                }
            }
        }
        
        if (adaMakanan) {
            S.move(1, ++S.y); System.out.print("Makanan :");
            for (int i = 0; i < pesananMakanan.length; i++) {
                if (pesananMakanan[i][0] != null) {
                    Menu makanan = (Menu) pesananMakanan[i][0]; // polimorfisme array object -> menu
                    S.move(1, ++S.y); System.out.print(makanan.getKode());
                    S.move(10, S.y); System.out.print(makanan.getNama());
                    S.move(35, S.y); System.out.println(pesananMakanan[i][1]);
                    S.move(45, S.y); System.out.println(makanan.getHarga());
                    S.move(60, S.y); System.out.println(makanan.pajak() * (int) pesananMakanan[i][1]);
                    S.move(80, S.y); System.out.println(Math.round(((makanan.getHarga() * (int) pesananMakanan[i][1]) + (makanan.pajak() * (int) pesananMakanan[i][1])) * 100.0) / 100.0);
                }
            }
        }

        Pembayaran bayar = pesanan.getPembayaran();
        double admin = 0, diskon = 0, diskonPersen = 0;
        if (bayar instanceof NonTunai) {
            diskon = ((NonTunai) bayar).getDiskon();
            diskonPersen = ((NonTunai) bayar).getDiskonPersen();
        
            if (bayar instanceof Emoney) {
                admin = ((Emoney) bayar).getAdmin();
            }
        }

        S.move(1, ++S.y);System.out.println("---------------------------------------------------------------------------------------------");
        S.move(1, ++S.y);System.out.println("Total sebelum pajak (IDR) : " + pesanan.totalPesananTanpaPajak());
        S.move(1, ++S.y);System.out.println("Total setelah pajak (IDR) : " + pesanan.totalPesanan());
        S.move(1, ++S.y);System.out.println("Pembayaran : " + pesanan.getPembayaran().getNama());
        S.move(1, ++S.y);System.out.println("Mata Uang : " + (pesanan.getMataUang() == null ? "IDR" : pesanan.getMataUang().getNama()));
        S.move(1, ++S.y);System.out.println("Diskon : " + diskonPersen + " % ("+ diskon +" "+ (pesanan.getMataUang() == null ? "IDR" : pesanan.getMataUang().getNama()) +")");
        S.move(1, ++S.y);System.out.println("Admin : " + admin);
        double totalTagihan1 = pesanan.getMataUang() == null ? (pesanan.totalPesananTanpaPajak()) : (pesanan.getMataUang().konversi(pesanan.totalPesananTanpaPajak()));
        double totalTagihan = pesanan.getMataUang() == null ? (pesanan.totalPesanan() + admin - diskon) : (pesanan.getMataUang().konversi(pesanan.totalPesanan()) + admin - diskon);
        S.move(1, ++S.y);System.out.println("Total Tagihan sebelum pajak, admin, dan diskon ("+(pesanan.getMataUang() == null ? "IDR" : pesanan.getMataUang().getNama())+") : " + (Math.round(totalTagihan1 * 100.0) / 100.0));
        S.move(1, ++S.y);System.out.println("Total Tagihan sesudah pajak, admin, dan diskon ("+(pesanan.getMataUang() == null ? "IDR" : pesanan.getMataUang().getNama())+") : " + (Math.round(totalTagihan * 100.0) / 100.0));

        S.move(1, ++S.y);System.out.println("=============================================================================================");
        S.move(32, ++S.y);System.out.println("TERIMA KASIH SILAHKAN DATANG KEMBALI");
        S.move(1, ++S.y);System.out.println("=============================================================================================");
    }
}