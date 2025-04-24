package pembayaran;
import matauang.MataUang;

public class Emoney extends NonTunai {
    private double admin = 20;

    public Emoney(double jmlh, double saldo) {
        super(jmlh);
        super.saldo = saldo;
        super.diskon = 0.07 * jmlh; // nilai diskon 7% dari jumlah yg sudah dikonversi
        super.diskonPersen = 0.07 ;
        super.nama = "Emoney";
    }

    public void setAdmin(MataUang mataUang) {
        if (mataUang != null) {
            this.admin = mataUang.konversi(admin);
        }
    }

    @Override
    public boolean bayar() {
        double total = super.jumlah - super.diskon + this.admin;
        if (super.saldo >= total) {
            super.saldo -= total;
            return true;
        } else {
            return false;
        }
    }

    public double getAdmin() {
        return this.admin;
    }
}