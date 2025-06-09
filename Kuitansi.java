import menu.Menu;
import menu.Makanan;
import menu.Minuman;
import pembayaran.*;
import model.*;
import utils.S;

class Kuitansi {
    public final void showKuitansi(Pesanan pesanan) {
        S.clear(); S.y = 1;
        S.move(1, S.y++); System.out.println("=============================================================================================");
        S.move(40, S.y++); System.out.println("KUITANSI PESANAN");
        S.move(1, S.y); System.out.println("=============================================================================================");
        S.move(1, ++S.y); System.out.println("Kode");
        S.move(10, S.y); System.out.print("Nama");
        S.move(35, S.y); System.out.print("Qty");
        S.move(45, S.y); System.out.print("Harga (Rp)");
        S.move(60, S.y); System.out.print("Pajak (Rp)");
        S.move(80, S.y); System.out.println("SubTotal (Rp)");
        S.move(1, ++S.y); System.out.println("---------------------------------------------------------------------------------------------");

        boolean adaMinuman = false, adaMakanan = false;

        for (ItemPesanan item : pesanan.getDaftarPesanan()) {
            if (item.menu() instanceof Minuman) {
                if (!adaMinuman) {
                    S.move(1, ++S.y); System.out.println("Minuman:");
                    adaMinuman = true;
                }
                Menu minuman = item.menu();
                int qty = item.qty();
                double subtotal = (minuman.getHarga() + minuman.pajak()) * qty;
                S.move(1, ++S.y); System.out.print(minuman.getKode());
                S.move(10, S.y); System.out.print(minuman.getNama());
                S.move(35, S.y); System.out.print(qty);
                S.move(45, S.y); System.out.print(minuman.getHarga());
                S.move(60, S.y); System.out.print(minuman.pajak() * qty);
                S.move(80, S.y); System.out.println(Math.round(subtotal * 100.0) / 100.0);
            }
        }

        for (ItemPesanan item : pesanan.getDaftarPesanan()) {
            if (item.menu() instanceof Makanan) {
                if (!adaMakanan) {
                    S.move(1, ++S.y); System.out.println("Makanan:");
                    adaMakanan = true;
                }
                Menu makanan = item.menu();
                int qty = item.qty();
                double subtotal = (makanan.getHarga() + makanan.pajak()) * qty;
                S.move(1, ++S.y); System.out.print(makanan.getKode());
                S.move(10, S.y); System.out.print(makanan.getNama());
                S.move(35, S.y); System.out.print(qty);
                S.move(45, S.y); System.out.print(makanan.getHarga());
                S.move(60, S.y); System.out.print(makanan.pajak() * qty);
                S.move(80, S.y); System.out.println(Math.round(subtotal * 100.0) / 100.0);
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

        S.move(1, ++S.y); System.out.println("---------------------------------------------------------------------------------------------");
        S.move(1, ++S.y); System.out.println("Total sebelum pajak (IDR) : " + pesanan.totalPesananTanpaPajak());
        S.move(1, ++S.y); System.out.println("Total setelah pajak (IDR) : " + pesanan.totalPesanan());
        S.move(1, ++S.y); System.out.println("Pembayaran : " + pesanan.getPembayaran().getNama());
        S.move(1, ++S.y); System.out.println("Mata Uang : " + (pesanan.getMataUang() == null ? "IDR" : pesanan.getMataUang().getNama()));
        S.move(1, ++S.y); System.out.println("Diskon : " + diskonPersen + " % (" + diskon + " " + (pesanan.getMataUang() == null ? "IDR" : pesanan.getMataUang().getNama()) + ")");
        S.move(1, ++S.y); System.out.println("Admin : " + admin);

        double totalSebelum = pesanan.getMataUang() == null ? pesanan.totalPesananTanpaPajak() : pesanan.getMataUang().konversi(pesanan.totalPesananTanpaPajak());
        double totalAkhir = pesanan.getMataUang() == null ? (pesanan.totalPesanan() + admin - diskon) : (pesanan.getMataUang().konversi(pesanan.totalPesanan()) + admin - diskon);

        S.move(1, ++S.y); System.out.println("Total Tagihan sebelum pajak, admin, dan diskon (" + (pesanan.getMataUang() == null ? "IDR" : pesanan.getMataUang().getNama()) + ") : " + Math.round(totalSebelum * 100.0) / 100.0);
        S.move(1, ++S.y); System.out.println("Total Tagihan sesudah pajak, admin, dan diskon (" + (pesanan.getMataUang() == null ? "IDR" : pesanan.getMataUang().getNama()) + ") : " + Math.round(totalAkhir * 100.0) / 100.0);

        S.move(1, ++S.y); System.out.println("=============================================================================================");
        S.move(32, ++S.y); System.out.println("TERIMA KASIH SILAHKAN DATANG KEMBALI");
        S.move(1, ++S.y); System.out.println("=============================================================================================");
    }
}