package menu;

public abstract class Menu {
    protected String nama, kode;
    protected double harga;

    public Menu(String kode, String nama, double harga) {
        this.nama = nama;
        this.kode = kode;
        this.harga = harga;
    }

    public String getNama() {
        return this.nama;
    }

    public String getKode() {
        return this.kode;
    }

    public double getHarga() {
        return this.harga;
    }

    public abstract double pajak();
}
