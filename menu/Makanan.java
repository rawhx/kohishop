package menu;

public class Makanan extends Menu {
    public Makanan(String kode, String nama, double harga) {
        super(kode, nama, harga);
    }

    @Override
    public double pajak() {
        double pajak;
        if (this.harga > 50) {
            pajak = 0.08 * harga;
        } else if (this.harga < 50) {
            pajak = 0.11 * harga;
        } else pajak = 0;
        return Math.round(pajak * 100.0) / 100.0;
    }
}
