import java.util.ArrayList;
import java.util.List;

import matauang.MataUang;
import menu.*;
import pembayaran.*;
import model.*;
import utils.*;

class Kuitansi {
    public final void showKuitansi(Pesanan pesanan, ProsesPembayaran pembayaran, Member member) {
        S.clear(); S.y = 1;
        S.move(1, S.y++); System.out.println("=======================================================================================================");
        S.move(40, S.y++); System.out.println("KUITANSI PESANAN");
        S.move(1, S.y); System.out.println("=======================================================================================================");
        S.move(1, ++S.y); System.out.println("Kode");
        S.move(10, S.y); System.out.print("Nama");
        S.move(45, S.y); System.out.print("Qty");
        S.move(55, S.y); System.out.print("Harga (Rp)");
        S.move(70, S.y); System.out.print("Pajak (Rp)");
        S.move(90, S.y); System.out.println("SubTotal (Rp)");
        S.move(1, ++S.y); System.out.println("-------------------------------------------------------------------------------------------------------");

        List<ItemPesanan> minumanList = new ArrayList<>();
        List<ItemPesanan> makananList = new ArrayList<>();

        for (ItemPesanan item : pesanan.getDaftarPesanan()) {
            if (item.menu() instanceof Minuman) {
                minumanList.add(item);
            } else if (item.menu() instanceof Makanan) {
                makananList.add(item);
            }
        }

        daftarPesanan("Makanan", makananList);
        daftarPesanan("Minuman", minumanList);

        MataUang mataUang = pembayaran.getMataUang();
        String namaMataUang = (mataUang == null ? "IDR" : mataUang.getNama());
        double totalTanpaPajak = (mataUang == null ? pesanan.totalPesananTanpaPajak() : mataUang.konversi(pesanan.totalPesananTanpaPajak()));

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

        S.move(1, ++S.y); System.out.println("-------------------------------------------------------------------------------------------------------");
        S.move(1, ++S.y); System.out.println("Total sebelum pajak (IDR) : " + pesanan.totalPesananTanpaPajak());
        S.move(1, ++S.y); System.out.println("Total setelah pajak (IDR) : " + pesanan.totalPesanan());
        S.move(1, ++S.y); System.out.println("-------------------------------------------------------------------------------------------------------");
        S.move(1, ++S.y); System.out.println("Pembayaran : " + (metode != null ? metode.getNama() : ""));
        S.move(1, ++S.y); System.out.println("Mata Uang : " + namaMataUang);
        S.move(1, ++S.y); System.out.println("Diskon : " + diskonPersen + " % (" + diskon + " " + namaMataUang + ")");
        S.move(1, ++S.y); System.out.println("Admin : " + admin);
        S.move(1, ++S.y); System.out.println("--------------------------------------------------------------------------------------------------------");
        
        S.move(1, ++S.y); System.out.println("Member : " + (member != null ? member.getNama() + " (" + member.getKode() + ")" : "Tidak ada member"));
        S.move(1, ++S.y); System.out.println("Point Sebelumnya : " + pembayaran.pointOld);
        S.move(1, ++S.y); System.out.println("Point Sesudah transaksi : " + (member != null ? member.getPoint() : 0));
        S.move(1, ++S.y); System.out.println("--------------------------------------------------------------------------------------------------------");

        double totalTanpaPajakDenganChanel = totalTanpaPajak + admin - diskon;
        double totalAkhir = (mataUang == null ? pesanan.totalPesanan() + admin - diskon : mataUang.konversi(pesanan.totalPesanan()) + admin - diskon);
        double totalDibayar = pembayaran.getTotal() + admin - diskon;
        
        S.move(1, ++S.y); System.out.println("Total Tagihan sebelum pajak, admin, dan diskon (" + namaMataUang + ") : " + Math.round(totalTanpaPajak * 100.0) / 100.0);
        S.move(1, ++S.y); System.out.println("Total Tagihan sebelum pajak dengan admin, dan diskon (" + namaMataUang + ") : " + Math.round(totalTanpaPajakDenganChanel * 100.0) / 100.0);
        S.move(1, ++S.y); System.out.println("Total Tagihan sesudah pajak, admin, dan diskon (" + namaMataUang + ") : " + Math.round(totalAkhir * 100.0) / 100.0);
        S.move(1, ++S.y); System.out.println("--------------------------------------------------------------------------------------------------------");
        S.move(1, ++S.y); System.out.println("Total Tagihan yang dibayar (" + namaMataUang + ") : " + Math.round(totalDibayar * 100.0) / 100.0);
        
        if (metode instanceof NonTunai) {
            S.move(1, ++S.y); System.out.println("Sisa saldo : " + ((NonTunai) metode).getSaldo());
        }

        if(member != null && member.kodeA() && mataUang == null) {
            S.move(1, ++S.y); System.out.println("--------------------------------------------------------------------------------------------------------");
            S.move(37, ++S.y); System.out.println("DIBEBASKAN DARI PAJAK");
        }
        
        S.move(1, ++S.y); System.out.println("========================================================================================================");
        S.move(32, ++S.y); System.out.println("TERIMA KASIH SILAHKAN DATANG KEMBALI");
        S.move(1, ++S.y); System.out.println("========================================================================================================");
    }

    public void daftarPesanan(String tipe, List<ItemPesanan> data) {
        if (data.isEmpty()) return;

        S.move(1, ++S.y); System.out.println(tipe + ":");
        for (ItemPesanan item : data) {
            Menu menu = item.menu();
            int qty = item.qty();
            double subtotal = (menu.getHarga() + menu.pajak()) * qty;
            S.move(1, ++S.y); System.out.print(menu.getKode());
            S.move(10, S.y); System.out.print(menu.getNama());
            S.move(45, S.y); System.out.print(qty);
            S.move(55, S.y); System.out.print(menu.getHarga());
            S.move(70, S.y); System.out.print(menu.pajak() * qty);
            S.move(90, S.y); System.out.println(Math.round(subtotal * 100.0) / 100.0);
        }
    }
}