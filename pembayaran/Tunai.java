package pembayaran;

public class Tunai extends Pembayaran {
    public Tunai(double jmlh) {
        super(jmlh);
        super.nama = "Tunai";
    }

    @Override
    public boolean bayar() {
        return true;
    }
}