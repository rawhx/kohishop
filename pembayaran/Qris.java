package pembayaran;

public class Qris extends NonTunai {
    public Qris(double jmlh, double saldo) {
        super(jmlh);
        super.saldo = saldo;
        super.diskon = 0.05 * jmlh;
        super.diskonPersen = 0.05;
        super.nama = "Qris";
    }

    @Override
    public boolean bayar() {
        double total = super.jumlah - super.diskon;
        if (super.saldo >= total) {
            super.saldo -= total;
            return true;
        } else {
            return false;
        }
    }
}