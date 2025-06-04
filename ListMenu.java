import java.util.*;
import menu.*;
import utils.*;

public final class ListMenu {
    private static final LinkedList<Menu> dataMenu = new LinkedList<>(Arrays.asList(
        // Makanan
        new Makanan("M1", "Petemania Pizza", 112),
        new Makanan("M2", "Mie Rebus Super Mario", 35),
        new Makanan("M3", "Ayam Bakar Goreng Rebus Spesial", 72),
        new Makanan("M4", "Soto Kambing Iga Guling", 124),
        new Makanan("S1", "Singkong Bakar A La Carte", 37),
        new Makanan("S2", "Ubi Cilembu Bakar Arang", 58),
        new Makanan("S3", "Tempe Mendoan", 18),
        new Makanan("S4", "Tahu Bakso Extra Telur", 28),
        // Minuman
        new Minuman("A1", "Caffe Latte", 46),
        new Minuman("A2", "Cappuccino", 46),
        new Minuman("E1", "Caffe Americano", 37),
        new Minuman("E2", "Caffe Mocha", 55),
        new Minuman("E3", "Caramel Macchiato", 59),
        new Minuman("E4", "Asian Dolce Latte", 55),
        new Minuman("E5", "Double Shots Iced Shaken Espresso", 50),
        new Minuman("B1", "Freshly Brewed Coffee", 23),
        new Minuman("B2", "Vanilla Sweet Cream Cold Brew", 50),
        new Minuman("B3", "Cold Brew", 44)
    ));

    public static final void showMenu() {
        S.clear();

        S.move(1, S.y); System.out.print("Menu Kedai Kohisop : ");
        S.move(1, ++S.y); System.out.print("Kode");
        S.move(10, S.y); System.out.print("Menu Minuman");
        S.move(50, S.y++); System.out.print("Harga (Rp)");

        for (Menu m : dataMenu) {
            if (m instanceof Minuman) {
                S.move(1, S.y); System.out.print(m.getKode());
                S.move(10, S.y); System.out.print(m.getNama());
                S.move(50, S.y++); System.out.println(m.getHarga());
            }
        }

        S.move(1, ++S.y); System.out.print("Kode");
        S.move(10, S.y); System.out.print("Menu Makanan");
        S.move(50, S.y++); System.out.print("Harga (Rp)");

        for (Menu m : dataMenu) {
            if (m instanceof Makanan) {
                S.move(1, ++S.y); System.out.print(m.getKode());
                S.move(10, S.y); System.out.print(m.getNama());
                S.move(50, S.y++); System.out.println(m.getHarga());
            }
        }
    }

    public static Menu cekMenuByKode(String kode) {
        for (Menu menu : dataMenu) {
            if (menu.getKode().equalsIgnoreCase(kode)) return menu;
        }
        return null;
    }
}