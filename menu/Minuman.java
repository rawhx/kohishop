package menu;

public class Minuman extends Menu {
    public Minuman(String kode, String nama, double harga) {
        super(kode, nama, harga);
    }

    @Override
    public double pajak() {
        double pajak;
        if(this.harga < 50 ) {
            pajak = 0;
        } else if (this.harga >= 50 && this.harga <= 55) {
            pajak = 0.08 * harga;
        } else pajak = 0.11 * harga;
        return Math.round(pajak * 100.0) / 100.0;
    }
}
