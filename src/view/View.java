package view;

/** Hochschule Hamm-Lippstadt
 * Praktikum Informatik II (ElferRaus)
 * (C) 2015 Lara Sievers, Adrian Schmidt, Fabian Schneider
 * 22.05.2015
 */


import javax.swing.*;
import javax.swing.border.Border;

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
        //frame.setVisible(true);
    }

    @Override
    public void update() {

    }

    /** Initialisiert das Frame.
     *
     */
    private void initializeView() {
        // Deaktivierung des plattformabhaengigen UI Design
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Berechnen der mittleren Anzeigeposition des Fensters
        Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
        int xPos = (screensize.width / 2) - (FRAME_XSIZE / 2);
        int yPos = (screensize.height / 2) - (FRAME_YSIZE / 2);

        // Aufbau des Hauptfensters
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
        frame.add(erstelleKarte(Color.BLUE, 10, 0, 0));
        frame.add(erstelleKarte(Color.RED, 11, 0, 45+2));
        frame.add(erstelleKarte(Color.GREEN, 9, 0, 90+4));
        frame.add(erstelleKarte(Color.ORANGE, 20, 0, 135+6));
    }

    /** Erstellt eine Karte als JButton.
     * @param color Farbe, welche die Karte haben soll.
     * @param number Nummer der Karte, wird sichtbar dargestellt.
     * @return Karte als JButton fuer das Spielfeld.
     */
    private JButton erstelleKarte(final Color color, final int number, final int xPos, final int yPos) {
        JButton card = new JButton();
        //Anpassung fuer OS X
        card.setOpaque(true);

        card.setBounds(xPos, yPos, BUTTON_XSIZE, BUTTON_YSIZE);
        card.setHorizontalAlignment(SwingConstants.LEFT);
        card.setVerticalAlignment(SwingConstants.TOP);

        card.setBackground(color);
        card.setForeground(Color.WHITE);
        Border border = BorderFactory.createLineBorder(Color.WHITE, 2);
        card.setBorder(border);
        card.setText("" + number);
        card.setVisible(true);

        return card;
    }
}
