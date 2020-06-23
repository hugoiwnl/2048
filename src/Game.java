package igra.src;

import edu.princeton.cs.introcs.StdDraw;

import java.awt.*;
import java.util.Random;

public class Game {
    private static Random random = new Random();

    /* Mreza */
    private static final int STRANICA_KVADRATA = 10;
    private static final int DEBELINA_ROBA = 2;

    /* IGRALNA POVRŠINA */
    private static final int VELIKOST = 4 * STRANICA_KVADRATA + 5 * DEBELINA_ROBA;
    private static final int HEADER = 10;

    /* BARVE */
    private static Color BARVA_0 = Color.decode("#c1b2ab");
    private static Color BARVA_2 = Color.decode("#d66853");
    private static Color BARVA_4 = Color.decode("#7d4e57");
    private static Color BARVA_8 = Color.decode("#7e8d85");
    private static Color BARVA_16 = Color.decode("#9dbebb");
    private static Color BARVA_32 = Color.decode("#757083");
    private static Color BARVA_64 = Color.decode("#aec3b0");
    private static Color BARVA_128 = Color.decode("#9b7e46");
    private static Color BARVA_256 = Color.decode("#8896ab");
    private static Color BARVA_512 = Color.decode("#646e68");
    private static Color BARVA_1024 = Color.decode("#ffb30f");
    private static Color BARVA_2048 = Color.decode("#849324");

    /* PISAVE */
    private static Font FONT_1 = new Font("Arial", Font.BOLD, 60);
    private static Font FONT_2 = new Font("Arial", Font.BOLD, 40);
    private static Font FONT_3 = new Font("Arial", Font.BOLD, 20);
    private static Font FONT_SCORE = new Font("Arial", Font.BOLD, 30);


    /* KODE SMERNIH TIPK */
    private static final int ESC_BUTTON = 27;
    private static final int LEFT_BUTTON = 37;
    private static final int UP_BUTTON = 38;
    private static final int RIGHT_BUTTON = 39;
    private static final int DOWN_BUTTON = 40;

    /* CASOVNE KONSTANTE */
    private static final int PAUSE = 10;
    private static final int PAUSE_LONG = 100;

    /*PARAMETRI IGRE*/
    private static boolean soPraznaPolja = true;
    private static int[][] polja = new int[4][4];

    /* TOCKE */
    private static int tocke = 0;

    /**
     * Vrne true ce je se kaksno polje prazno.
     * V nasprotnem primeru izrise konec in vrne false.
     * @return
     */
    public static boolean igraj(){
        if (!soPraznaPolja){
            StdDraw.show(PAUSE);
            StdDraw.setPenColor(StdDraw.WHITE);
            StdDraw.filledRectangle(VELIKOST/2,VELIKOST/2, VELIKOST/3,HEADER/2);
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.setFont(FONT_SCORE);
            StdDraw.text(VELIKOST/2,VELIKOST/2, "GAME OVER");
            StdDraw.show();
        }
        return soPraznaPolja;
    }

    /**
     * Pogleda če je bila pritisnjena katera izmed tipk, ustrezno premakne števila
     * in doda novo stevilo. Poveča točke, če se kakšna števila združijo.
     */
    public static void naslednjaPoteza(){
        if (StdDraw.isKeyPressed(UP_BUTTON) && premakniGor()
                || StdDraw.isKeyPressed(DOWN_BUTTON) && premakniDol()
                || StdDraw.isKeyPressed(LEFT_BUTTON) && premakniLevo()
                || StdDraw.isKeyPressed(RIGHT_BUTTON) && premakniDesno()){
            dodajStevilo();
            izpisiTabelo();
        } else {
            StdDraw.pause(PAUSE);
        }
    }

    /**
     * Premakne stevila dol. Pomožna metoda za metodo void naslednjaPoteza().
     * @return
     * Vrne true če se je tabela polja pri premiku spremenila drugače false.
     */
    static boolean premakniDol(){
        int st1, st2;
        boolean premik = false;
        for (int i=0; i<4; i++){
            int j=1;
            int minJ=0;
            while (j<4){
                st1 = polja[j-1][i];
                st2 = polja[j][i];
                if (st2>0) {
                    if (st2==st1 && minJ<j){
                        tocke += 2*polja[j][i];
                        polja[j-1][i] = 2*polja[j][i];
                        polja[j][i] = 0;
                        minJ = j;
                        premik = true;
                    } else if (st1==0){
                        polja[j-1][i] = polja[j][i];
                        polja[j][i] = 0;
                        premik = true;
                        if (j>1){
                            j--;
                        }
                    } else {
                        j++;
                    }
                } else {
                    j++;
                }
            }
        }
        return premik;
    }

    /**
     * Premakne stevila gor. Pomožna metoda za metodo void naslednjaPoteza().
     * @return
     * Vrne true če se je tabela polja pri premiku spremenila drugače false.
     */
    static boolean premakniGor(){
        int st1, st2;
        boolean premik = false;
        for (int i=0; i<4; i++){
            int j=2;
            int maxJ=3;
            while (j>=0){
                st1 = polja[j+1][i];
                st2 = polja[j][i];
                if (st2>0) {
                    if (st2==st1 && maxJ>j){
                        tocke += 2*polja[i][j];
                        polja[j+1][i] = 2*polja[j][i];
                        polja[j][i] = 0;
                        maxJ = j;
                        premik = true;
                    } else if (st1==0){
                        polja[j+1][i] = polja[j][i];
                        polja[j][i] = 0;
                        premik = true;
                        if (j<2){
                            j++;
                        }
                    } else {
                        j--;
                    }
                } else {
                    j--;
                }
            }
        }
        return premik;
    }

    /**
     * Premakne stevila levo. Pomožna metoda za metodo void naslednjaPoteza().
     * @return
     * Vrne true če se je tabela polja pri premiku spremenila drugače false.
     */
    static boolean premakniLevo(){
        int st1, st2;
        boolean premik = false;
        for (int i=0; i<4; i++){
            int j=1;
            int minJ=0;
            while (j<4){
                st1 = polja[i][j-1];
                st2 = polja[i][j];
                if (st2>0) {
                    if (st2==st1 && minJ<j){
                        tocke += 2*polja[i][j];
                        polja[i][j-1] = 2*polja[i][j];
                        polja[i][j] = 0;
                        minJ = j;
                        premik = true;
                    } else if (st1==0){
                        polja[i][j-1] = polja[i][j];
                        polja[i][j] = 0;
                        premik = true;
                        if (j>1){
                            j--;
                        }
                    } else {
                        j++;
                    }
                } else {
                    j++;
                }
            }
        }
        return premik;
    }

    /**
     * Premakne stevila desno. Pomožna metoda za metodo void naslednjaPoteza().
     * @return
     * Vrne true če se je tabela polja pri premiku spremenila drugače false.
     */
    static boolean premakniDesno(){
        int st1, st2;
        boolean premik = false;
        for (int i=0; i<4; i++){
            int j=2;
            int maxJ=3;
            while (j>=0){
                st1 = polja[i][j+1];
                st2 = polja[i][j];
                if (st2>0) {
                    if (st2==st1 && maxJ>j){
                        tocke += 2*polja[i][j];
                        polja[i][j+1] = 2*polja[i][j];
                        polja[i][j] = 0;
                        maxJ = j;
                        premik = true;
                    } else if (st1==0){
                        polja[i][j+1] = polja[i][j];
                        polja[i][j] = 0;
                        premik = true;
                        if (j<2){
                            j++;
                        }
                    } else {
                        j--;
                    }
                } else {
                    j--;
                }
            }
        }
        return premik;
    }

    /**
     * Preveri ce je kaksno polje prazno. Ce ni postavi soPraznaPolja = false
     * Vrne tabelo praznih polj. Pomožna metoda za void dodajStevilo().
     * @return
     */
    static int[] preveriPraznaPolja(){
        return new int[0];
    }

    /**
     * Doda 2 na nakljucno prazno mesto v tabeli.
     * Če ni prostih mest postavi soPraznaPolja na false.
     */
    static void dodajStevilo(){
        int[] praznaPolja = new int[16];
        int k = 0;
        for (int i=0; i<4; i++) {
            for (int j=0; j<4; j++){
                if(polja[i][j]==0) {
                    praznaPolja[k] = 4*i + j;
                    k++;
                }
            }
        }
        if (k>0) {
            int pozicija = praznaPolja[random.nextInt(k)];
            int y = pozicija / 4;
            int x = pozicija % 4;
            polja[y][x] = 2;
        }
        if (k<=1) {
            soPraznaPolja = false;
        }
    }

    /**
     * Inicializira parametre igre.
     */
    public static void zacni(){
        StdDraw.setXscale(0,VELIKOST);
        StdDraw.setYscale(0,VELIKOST+HEADER);
        dodajStevilo();
    }


    /**
     * Izrise tabelo
     */
    public static void izpisiTabelo(){
        StdDraw.show(PAUSE);
        StdDraw.clear();
        for (int i=0; i<4; i++){
            for (int j=0; j<4; j++){
                int x = (STRANICA_KVADRATA + DEBELINA_ROBA) * i +STRANICA_KVADRATA/2 + DEBELINA_ROBA;
                int y = (STRANICA_KVADRATA + DEBELINA_ROBA) * j +STRANICA_KVADRATA/2 + DEBELINA_ROBA;
                if (polja[j][i]>0){
                    StdDraw.setPenColor(izberiBarvo(polja[j][i]));
                    StdDraw.filledSquare(x,y, STRANICA_KVADRATA/2);
                    switch ((int)Math.log10(polja[j][i])){
                        case 0:
                            StdDraw.setFont(FONT_1);
                            break;
                        case 1:
                            StdDraw.setFont(FONT_1);
                            break;
                        case 2:
                            StdDraw.setFont(FONT_2);
                            break;
                        case 3:
                            StdDraw.setFont(FONT_3);
                            break;
                        default:
                            StdDraw.setFont(FONT_3);
                            break;
                    }
                    StdDraw.setPenColor(StdDraw.WHITE);
                    StdDraw.text(x,y-DEBELINA_ROBA/2,Integer.toString(polja[j][i]));
                } else {
                    StdDraw.setPenColor(BARVA_0);
                    StdDraw.filledSquare(x,y, STRANICA_KVADRATA/2);
                }
            }
        }
        StdDraw.setFont(FONT_SCORE);
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.text(VELIKOST/2,VELIKOST + HEADER/2, Integer.toString(tocke));
        StdDraw.show();
        StdDraw.pause(PAUSE_LONG);
    }
    public static Color izberiBarvo(int stevilo){
        switch (stevilo){
            case 2:
                return BARVA_2;
            case 4:
                return BARVA_4;
            case 8:
                return BARVA_8;
            case 16:
                return BARVA_16;
            case 32:
                return BARVA_32;
            case 64:
                return BARVA_64;
            case 128:
                return BARVA_128;
            case 256:
                return BARVA_256;
            case 512:
                return BARVA_512;
            case 1024:
                return BARVA_1024;
            case 2048:
                return BARVA_2048;
            default:
                return BARVA_2048;

        }
    }
}
