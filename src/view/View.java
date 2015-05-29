package view;

/** Hochschule Hamm-Lippstadt
 * Praktikum Informatik II (ElferRaus)
 * (C) 2015 Lara Sievers, Adrian Schmidt, Fabian Schneider
 * 22.05.2015
 */


import javax.swing.*;
import javax.swing.border.Border;

import data.Karte;
import data.Holder;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

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
    private final Container handPlayer = new Container();
    /** Das Kartenset plus eine Rueckseite. */
    private final Map<Karte, JButton> cards = new HashMap<Karte, JButton>();
    private Holder stapel;

    /** Erstellt eine View fuer das Spiel.
     *
     */
    public View() {
        //initializeView();
        //erstelleSpielfeld();
        //frame.setVisible(true);
    }

    @Override
    public void update() {

    }
    
    @Override
    public void update(Holder stapel) {
    	this.stapel = stapel;
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

        frame.setBounds(xPos, yPos, FRAME_XSIZE,FRAME_YSIZE);
        frame.setResizable(false);
        //frame.setLayout(new BorderLayout());

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    /** Erstellt das grundlegende Spielfeld.
     *
     */
    private void erstelleSpielfeld() {
    	Container contentPane = frame.getContentPane();
    	// Einrichten des Hintergrundes.
    	contentPane.setBackground(Color.GREEN.darker());
    	contentPane.setLayout(null);
    	
        // Einrichten des Handbereiches des Spielers
    	handPlayer.setBounds(100, 100, 100, 100);
    	handPlayer.setBackground(Color.PINK);
    	handPlayer.setVisible(true);
    	contentPane.add(handPlayer);
        
    	for (int i = 0; i < 3; i++) {
    		Karte karte = stapel.zeigeKarten().get(i);
        	cards.put(karte, erstelleKarte(karte.getFarbe(), karte.getNummer(), i*31, 0));
        	JButton bCard = cards.get(karte);
        	frame.add(bCard);
    	}
    	
    	/*
        cards[0] = erstelleKarte(Color.BLUE, 1, 0, 0);
        cards[1] = erstelleKarte(Color.RED, 1, 0, 45+2);
        cards[2] = erstelleKarte(Color.GREEN, 1, 0, 90+4);
        cards[3] = erstelleKarte(Color.ORANGE, 1, 0, 135+6);
        
        frame.add(cards[0]);
        frame.add(cards[1]);
        frame.add(cards[2]);
        frame.add(cards[3]);
        */
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
