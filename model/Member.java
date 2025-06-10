package model;

import java.util.Random;

public class Member {
    private String kode;
    private String nama;
    private int point = 0;

    public Member(String nama) {
        this.kode = generateKode();
        this.nama = nama;
    }

    public Member(String nama, String kode) {
        this.nama = nama;
        this.kode = kode;
    }

    public Member(String nama, String kode, int point) {
        this.nama = nama;
        this.kode = kode;
        this.point = point;
    }

    private String generateKode() {
        String chars = "ABCDEF0123456789";
        Random rand = new Random();
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < 6; i++) {
            sb.append(chars.charAt(rand.nextInt(chars.length())));
        }

        return sb.toString();
    }

    public String getKode() {
        return this.kode;
    }

    public String getNama() {
        return this.nama;
    }

    public int getPoint() {
        return this.point;
    }

    public int getPointBayar() {
        return getPoint() * 2;
    }

    public void setPoint(int nilai) {
        this.point = nilai;
    }

    public void tambahPoint(int nilai) {
        if (kodeA()) {
            this.point += nilai * 2;
        } else {
            this.point += nilai;
        }
    }

    public boolean kodeA() {
        return this.kode.toLowerCase().contains("a");
    }
}
