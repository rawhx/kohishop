import matauang.MataUang;
import menu.*;
import pembayaran.*;
import model.*;
import utils.*;

class Kuitansi {
    public final void showKuitansi(Pesanan pesanan, ProsesPembayaran pembayaran, Member member) {
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

        MataUang mataUang = pembayaran.getMataUang();
        String namaMataUang = (mataUang == null ? "IDR" : mataUang.getNama());

        double diskon = 0;
        double diskonPersen = 0;
        double admin = 0;

        Pembayaran metode = pembayaran.getPembayaran();

        if (metode instanceof NonTunai) {
            diskon = ((NonTunai) metode).getDiskon();
            diskonPersen = ((NonTunai) metode).getDiskonPersen();
            if (metode instanceof Emoney) {
                admin = ((Emoney) metode).getAdmin();
            }
        } 

        S.move(1, ++S.y); System.out.println("---------------------------------------------------------------------------------------------");
        S.move(1, ++S.y); System.out.println("Total sebelum pajak (IDR) : " + pesanan.totalPesananTanpaPajak());
        S.move(1, ++S.y); System.out.println("Total setelah pajak (IDR) : " + pesanan.totalPesanan());
        S.move(1, ++S.y); System.out.println("Pembayaran : " + (metode != null ? metode.getNama() : ""));
        S.move(1, ++S.y); System.out.println("Mata Uang : " + namaMataUang);
        S.move(1, ++S.y); System.out.println("Diskon : " + diskonPersen + " % (" + diskon + " " + namaMataUang + ")");
        S.move(1, ++S.y); System.out.println("Admin : " + admin);
        S.move(1, ++S.y); System.out.println("----------------------------------------------------------------------------------------------");
        
        S.move(1, ++S.y); System.out.println("Member : " + (member != null ? member.getNama() + " (" + member.getKode() + ")" : "Tidak ada member"));
        S.move(1, ++S.y); System.out.println("Point Sebelumnya : " + pembayaran.pointOld);
        S.move(1, ++S.y); System.out.println("Point Sesudah transaksi : " + (member != null ? member.getPoint() : 0));
        S.move(1, ++S.y); System.out.println("----------------------------------------------------------------------------------------------");

        double totalSebelum = (mataUang == null ? pesanan.totalPesananTanpaPajak() : mataUang.konversi(pesanan.totalPesananTanpaPajak()));
        double totalAkhir = (mataUang == null ? pesanan.totalPesanan() + admin - diskon : mataUang.konversi(pesanan.totalPesanan()) + admin - diskon);
        double totalDibayar = pembayaran.getTotal() + admin - diskon;
        
        S.move(1, ++S.y); System.out.println("Total Tagihan sebelum pajak, admin, dan diskon (" + namaMataUang + ") : " + Math.round(totalSebelum * 100.0) / 100.0);
        S.move(1, ++S.y); System.out.println("Total Tagihan sesudah pajak, admin, dan diskon (" + namaMataUang + ") : " + Math.round(totalAkhir * 100.0) / 100.0);
        S.move(1, ++S.y); System.out.println("----------------------------------------------------------------------------------------------");
        S.move(1, ++S.y); System.out.println("Total Tagihan yang dibayar (" + namaMataUang + ") : " + Math.round(totalDibayar * 100.0) / 100.0);
        
        if(member != null && member.kodeA() && mataUang == null) {
            S.move(1, ++S.y); System.out.println("----------------------------------------------------------------------------------------------");
            S.move(32, ++S.y); System.out.println("DIBEBASKAN DARI PAJAK");
            S.move(1, ++S.y); System.out.println("----------------------------------------------------------------------------------------------");
        }
        
        S.move(1, ++S.y); System.out.println("=============================================================================================");
        S.move(32, ++S.y); System.out.println("TERIMA KASIH SILAHKAN DATANG KEMBALI");
        S.move(1, ++S.y); System.out.println("=============================================================================================");
    }
}