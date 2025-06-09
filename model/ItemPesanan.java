package model;

import menu.Menu;

public class ItemPesanan {
    private final Menu menu;
    private final int qty;

    public ItemPesanan(Menu menu, int qty) {
        this.menu = menu;
        this.qty = qty;
    }

    public Menu menu() {
        return menu;
    }

    public int qty() {
        return qty;
    }
}
