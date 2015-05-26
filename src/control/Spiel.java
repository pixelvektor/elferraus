package control;

/** Hochschule Hamm-Lippstadt
 * Praktikum Informatik II (ElferRaus)
 * (C) 2015 Lara Sievers, Adrian Schmidt, Fabian Schneider
 * 22.05.2015
 */

import java.awt.Color;

import data.Holder;
import data.Karte;
import data.Ki;
import data.Spieler;
import data.Spielfeld;
import data.Stapel;
import view.View;


/**
 * @author adrian
 */
public class Spiel {
	/** Holder-Array. */
	private Holder[] holder = new Holder[6];
	/** Fuer den Zug aktiver Holder. */
	private Holder activeHolder;
	//private int activeHolder;
	/** Fuer den Zug aktives Ziel. */
	private Holder targetHolder;
	//private int targetHolder;
	/** Speichert die View. */
	private View view;
	/** true solange das Spiel laeuft. */
	private boolean isRunning = true;
	
	
	
    public Spiel(View view) {
       this.view=view;
       
       gameInit();
	}
    /** Initialisiert das Spiel. */
    public void gameInit(){
    	for(int x=0; x<6; x++){         //erstellt 6 Holder
    		holder[x]= new Holder(); 
    	}
    	
    	//holder[0]= new Stapel();         //erstellt jeden Holder spezifisch
    	//holder[1]= new Spieler();
    	//holder[2]= new Ki();
    	//holder[3]= new Spielfeld();
    	
    	kartenInit();
    }
    /** Methode um den Karten einen neuen Holder zuzuweisen. */
    private boolean move(Karte karte, Holder ziel){
    	
    	if (pruefeZug(karte, ziel) == true){
        	Holder.setOwnerHolder(ziel);  //ownerHolder=Karte im Besitz
        	Holder.add(karte);
        	return true;
    	}
    	else{
    		return false;
    	}
 
    }
    /** Karten werden erstellt. */
    public void kartenInit(){
    	
    	targetHolder= Holder.getTargetHolder();  //targetHolder= möchte Karte haben
    	
    	for(int wert=1; wert<=20; wert++){
    		Color farbe = Color.BLUE;
    		Karte karte = new Karte(farbe, wert);
    		move(karte, targetHolder);
    	}
    	for(int wert=1; wert<=20; wert++){
    		Color farbe = Color.YELLOW;
    		Karte karte = new Karte(farbe, wert);
    		move(karte, targetHolder);
    	}
    	for(int wert=1; wert<=20; wert++){
    		Color farbe = Color.GREEN;
    		Karte karte = new Karte(farbe, wert);
    		move(karte, targetHolder);
    	}
    	for(int wert=1; wert<=20; wert++){
    		Color farbe = Color.RED;
    		Karte karte = new Karte(farbe, wert);
    		move(karte, targetHolder);
    	}
    	
    }
    /** prüft ob der aktuelle Zug gültig ist. */
    private boolean pruefeZug(Karte karte, Holder ziel){
    	if(pruefeFarbe(karte, ziel)==true && pruefeNummer(karte, ziel)==true){
    		return true;
    	}
    	else{
    		return false;
    	}
    	
    }
    /** prüft ob die gewählte Farbe in Ordnung ist. */
    private boolean pruefeFarbe(Karte karte, Holder ziel){
        return true;	
    }
    /** prüft ob die gewählte Nummer in Ordnung ist. */
    private boolean pruefeNummer(Karte karte, Holder ziel){
    	return true;
    }
}