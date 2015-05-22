package control;

/** Hochschule Hamm-Lippstadt
 * Praktikum Informatik II (ElferRaus)
 * (C) 2015 Lara Sievers, Adrian Schmidt, Fabian Schneider
 * 22.05.2015
 */

import data.Holder;
import data.Karte;
import view.View;


/**
 * @author adrian
 */
public class Spiel {
	/** Holder-Array. */
	private Holder[] holder = new Holder[4];
	/** Fuer den Zug aktiver Holder. */
	private Holder activeHolder;
	/** Speichert die View. */
	private View view;
	/** true solange das Spiel laeuft. */
	private boolean isRunning = true;
	
	
	
    public Spiel(View view) {
       this.view=view;
       
       kartenInit();
	}
    /** Methode um den Karten einen neuen Holder zuzuweisen. */
    public boolean move(Karte karte, Holder ziel){
    	return true;
    }
    /** Karten werden erstellt. */
    private void kartenInit(){
    	
    }
    /** pr�ft ob der aktuelle Zug g�ltig ist. */
    private boolean pruefeZug(Karte karte, Holder ziel){
    	return true;
    }
    /** pr�ft ob die gew�hlte Farbe in Ordnung ist. */
    private boolean pruefeFarbe(Karte karte, Holder ziel){
        return true;	
    }
    /** pr�ft ob die gew�hlte Nummer in Ordnung ist. */
    private boolean pruefeNummer(Karte karte, Holder ziel){
    	return true;
    }
}