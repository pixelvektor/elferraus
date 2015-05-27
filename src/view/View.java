package view;

/** Hochschule Hamm-Lippstadt
 * Praktikum Informatik II (ElferRaus)
 * (C) 2015 Lara Sievers, Adrian Schmidt, Fabian Schneider
 * 22.05.2015
 */


import javax.swing.*;
import java.awt.*;

/** Stellt das Spiel in einer GUI auf dem Monitor dar.
 * @author fabian
 * @version 2205201501
 */
public class View implements ViewInterface{
    /** Beinhaltet das Fenster fuer das Spiel. */
    private final JFrame frame = new JFrame();
    /** Groesse des Fensters in X Richtung. */
    private static final int FRAME_XSIZE = 1024;
    /** Groesse des Fensters in Y Richtung. */
    private static final int FRAME_YSIZE = 800;
    /** Groesse einer Karte in X Richtung. */
    private static final int BUTTON_XSIZE = 30;
    /** Groesse einer Karte in Y Richtung. */
    private static final int BUTTON_YSIZE = 45;
    /** Kartenhalter fuer den Spieler */
    private final Container hand = new Container();

    /** Erstellt eine View fuer das Spiel.
     *
     */
    public View() {
        initializeView();
        erstelleSpielfeld();
    }

    @Override
    public void update() {

    }

    /** Initialisiert das Frame.
     *
     */
    private void initializeView() {
        // Berechnen der mittleren Anzeigeposition des Fensters
        Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
        int xPos = (screensize.width / 2) - (FRAME_XSIZE / 2);
        int yPos = (screensize.height / 2) - (FRAME_YSIZE / 2);


        frame.setTitle("ElferRaus");
        frame.getContentPane().setBackground(Color.GREEN.darker());

        frame.setBounds(xPos, yPos, FRAME_XSIZE,FRAME_YSIZE);
        frame.setResizable(false);
        frame.setLayout(new BorderLayout());

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    /** Erstellt das grundlegende Spielfeld.
     *
     */
    private void erstelleSpielfeld() {
        //hand.add(erstelleKarte(Color.BLUE, 10));
        //frame.add(hand);
        frame.add(erstelleKarte(Color.BLUE, 10));
    }

    private JButton erstelleKarte(final Color color, final int number) {
        JButton card = new JButton();
        //Anpassung fuer OS X
        card.setOpaque(true);

        card.setSize(BUTTON_XSIZE,BUTTON_YSIZE);
        card.setHorizontalAlignment(SwingConstants.LEFT);
        card.setVerticalAlignment(SwingConstants.TOP);

        card.setBackground(color);
        card.setForeground(Color.WHITE);
        card.setBorder(null);
        card.setText("" + number);

        return card;
    }
}
