package view;

/** Hochschule Hamm-Lippstadt
 * Praktikum Informatik II (ElferRaus)
 * (C) 2015 Lara Sievers, Adrian Schmidt, Fabian Schneider
 * 22.05.2015
 */


import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.Border;

import control.Spiel;
import data.Holder;
import data.Karte;

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
    /** ContentPane des Frames. */
    private Container contentPane;
    /** Das Kartenset plus eine Rueckseite. */
    private Map<Karte, JButton> cards;

    /** Erstellt eine View fuer das Spiel.
     *
     */
    public View() {
        initializeView();
    }

    @Override
    public void update(final Spiel spiel) {
    	cards = new HashMap<Karte, JButton>();
    	Holder stapel = spiel.getStapel();
    	Holder spielfeld = spiel.getSpielfeld();
    	ArrayList<Holder> spieler = spiel.getSpieler();
    	
    	ArrayList<Karte> stapelKarten = stapel.getKarten();
    	System.out.println(stapelKarten);
    	int y = 0;
    	int x = 0;
    	Color c = stapelKarten.get(0).getFarbe();
    	for (int i = 0;  i < stapelKarten.size(); i++) {
    		Karte karte = stapelKarten.get(i);
    		if (!c.equals(karte.getFarbe())) {
				y++;
				x = 0;
				c = karte.getFarbe();
			}
    		JButton b = erstelleKarte(karte.getFarbe(), karte.getNummer(), x * (BUTTON_XSIZE + 2), y * (BUTTON_YSIZE +2));
			cards.put(karte, b);
			frame.add(b);
			x++;
			System.out.println(i);
		}
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
        
        contentPane = frame.getContentPane();
        contentPane.setBackground(Color.GREEN.darker().darker());

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
        //card.setHorizontalAlignment(SwingConstants.LEFT);
        //card.setVerticalAlignment(SwingConstants.TOP);

        card.setBackground(color);
        card.setForeground(Color.WHITE);
        Border border = BorderFactory.createLineBorder(Color.WHITE, 2);
        card.setBorder(border);
        card.setText("" + number);
        card.setVisible(true);

        return card;
    }
}
