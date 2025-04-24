package matauang;

abstract public class MataUang {
    protected String nama;
    protected double nilaiTukar; // nilai tukar rupiah

    public String getNama() {
        return nama;
    }

    public double getNilaiTukar() {
        return nilaiTukar;
    }

    public double konversi(double jumlah) {
        return  Math.round(jumlah / nilaiTukar * 100.0) / 100.0;
    }
}
