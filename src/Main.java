package igra.src;

public class Main {

    public static void main(String[] args) {
        Game igra = new Game();
        igra.zacni();
        while (igra.igraj()){
            igra.naslednjaPoteza();
        }
    }
}
