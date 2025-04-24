package pembayaran;

public abstract class Pembayaran {
    protected double jumlah;
    protected String nama;

    public Pembayaran(double jmlh) {
        this.jumlah = jmlh;
    }

    public double getJumlah() {
        return this.jumlah;
    }

    public String getNama() {
        return this.nama;
    }

    public abstract boolean bayar();
}