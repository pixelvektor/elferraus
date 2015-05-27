package control;

/** Hochschule Hamm-Lippstadt
 * Praktikum Informatik II (ElferRaus)
 * (C) 2015 Lara Sievers, Adrian Schmidt, Fabian Schneider
 * 22.05.2015
 */

import java.awt.Color;
import java.util.ArrayList;

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
	/** Spieler-ArrayList. */
	private ArrayList<Holder> spieler = new ArrayList<Holder>();
	/** Stapel-ArrayList. */
	private Stapel stapel = new Stapel();
	/** Spielfeld-ArrayList. */
	private Spielfeld spielfeld = new Spielfeld();
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
	
	
	/**
	 * Erstellt ein Spiel.
	 */
    public Spiel(View view) {
       this.view=view;
       
       gameInit();
	}
    /**
     *  Initialisiert das Spiel.
     */
    public void gameInit(){
    	
    	kartenInit();
    }
    /**
     *  Methode um den Karten einen neuen Holder zuzuweisen. 
     */
    private boolean move(Karte karte, Holder ziel){
    	
    	if (pruefeZug(karte, ziel) == true){
    		ziel.add(karte);
        	return true;
    	}
    	else{
    		return false;
    	}
 
    }
    /**
     *  Karten werden erstellt.
     */
    public void kartenInit(){
    	
    	for(int wert=1; wert<=20; wert++){
    		Color farbe = Color.BLUE;
    		Karte karte = new Karte(farbe, wert);
    		move(karte, stapel);
    	}
    	for(int wert=1; wert<=20; wert++){
    		Color farbe = Color.YELLOW;
    		Karte karte = new Karte(farbe, wert);
    		move(karte, stapel);
    	}
    	for(int wert=1; wert<=20; wert++){
    		Color farbe = Color.GREEN;
    		Karte karte = new Karte(farbe, wert);
    		move(karte, stapel);
    	}
    	for(int wert=1; wert<=20; wert++){
    		Color farbe = Color.RED;
    		Karte karte = new Karte(farbe, wert);
    		move(karte, stapel);
    	}
    	
    }
    /** 
     * prüft ob der aktuelle Zug gültig ist.
     */
    private boolean pruefeZug(Karte karte, Holder ziel){
    	if(ziel==stapel){
    		return true;
    	}
    	else{
    	
    	 if(pruefeFarbe(karte, ziel)==true && pruefeNummer(karte, ziel)==true){
    		 return true;
    	 }
    	 else{
    		 return false;
    	 }
    	
        }
    }
    /** 
     * prüft ob die gewählte Farbe in Ordnung ist.
     */
    private boolean pruefeFarbe(Karte karte, Holder ziel){
        if(karte.getFarbe()==ziel.getObersteKarten().getFarbe()){
        	return true;
        }
        else{
        	return false;
        }
    	
    }
    /** 
     * prüft ob die gewählte Nummer in Ordnung ist.
     */
    private boolean pruefeNummer(Karte karte, Holder ziel){
    	return true;
    }
}