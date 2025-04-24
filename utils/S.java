package utils;

public class S {
    public static int y=1;
    public static void clear() {
      try {
            String os = System.getProperty("os.name");
            if (os.contains("Windows")) {
              new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        } catch (Exception e) {
            System.out.println("Gagal membersihkan layar: " + e.getMessage());
        }
    }
    public static void move(int x, int y) {
        System.out.print(String.format("%c[%d;%df",0x1B,y,x));
    }
    public static void delay(int ms) {
        try { Thread.sleep(ms); }
        catch(Exception e) {}
    }
  }