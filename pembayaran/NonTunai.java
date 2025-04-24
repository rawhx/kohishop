package pembayaran;

public abstract class NonTunai extends Pembayaran{
    protected double saldo;
    protected double diskon;
    protected double diskonPersen;

    public NonTunai(double jmlh) {
        super(jmlh);
    }

    public double getSaldo() {
        return Math.round(this.saldo * 100.0) / 100.0;
    }

    public double getDiskon() {
        return Math.round(this.diskon * 100.0) / 100.0;
    }

    public double getDiskonPersen() {
        return Math.round(this.diskonPersen * 100);
    }

    @Override
    public abstract boolean bayar();
}