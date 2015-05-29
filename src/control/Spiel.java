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
	/** Stapel fuer die Karten. */
	private Stapel stapel = new Stapel();
	/** Spielfeld. */
	private Spielfeld spielfeld = new Spielfeld();
	/** Fuer den Zug aktiver Holder. */
	private Holder activeHolder;
	/** Fuer den Zug aktives Ziel. */
	private Holder targetHolder;
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
    	Holder spieler1 = new Spieler();
    	Holder ki1 = new Ki();
    	Holder ki2 = new Ki();
    	Holder ki3 = new Ki();
    	spieler.add(spieler1);
    	spieler.add(ki1);	
    	spieler.add(ki2);
    	spieler.add(ki3);
    	
    	kartenInit();
    	gameStart();
    }
    private void gameStart(){
    	stapel.mischen();
    	for(int i=0; i<11; i++){
    		move(stapel.getObersteKarte(), spieler.get(0));
    		move(stapel.getObersteKarte(), spieler.get(1));
    		move(stapel.getObersteKarte(), spieler.get(2));
    		move(stapel.getObersteKarte(), spieler.get(3));
    	}
    	for(int i=0; i<11; i++){
    		if(spieler.get(0).zeigeKarten().get(i).getNummer()==11){
    			activeHolder=spieler.get(0);
    		}
    		if(spieler.get(1).zeigeKarten().get(i).getNummer()==11){
    			activeHolder=spieler.get(1);
    		}
    		if(spieler.get(2).zeigeKarten().get(i).getNummer()==11){
    			activeHolder=spieler.get(2);
    		}
    		if(spieler.get(3).zeigeKarten().get(i).getNummer()==11){
    			activeHolder=spieler.get(3);
    		}
    		activeHolder=spieler.get(0);
    	}
    	startRound();
    }
    private void startRound(){
    	
    }
    /**
     *  Methode um eine Karte einem neuen Holder zu ueberschreiben.
     *  @param karte die Karte die verschoben werden soll.
     *  @param ziel Holder an den die Karte geht.
     *  @return true wenn die Nummer der Karte 11 ist.
     *  @return true wenn pruefeZug() erfolgreich. 
     */
    private boolean move(Karte karte, Holder ziel){
    	if(karte.getNummer()==11){
    		ziel.add(karte);
    		return true;
    	}
    	if (pruefeZug(karte, ziel) == true){
    		ziel.add(karte);
        	return true;
    	}
    	
    		return false;
    	
 
    }
    /**
     *  Karten werden erstellt, danach an die Methode move() uebergeben.
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
     * Prueft ob der aktuelle Zug gueltig ist.
     * @return true wenn ziel kein Spielfeld ist. 
     * @return true wenn Farbe und Nummer stimmen.
     * @param karte die Karte die verschoben werden soll.
     * @param ziel Holder an den die Karte geht. 
     */
    private boolean pruefeZug(Karte karte, Holder ziel){
    	if(!ziel.equals(spielfeld)){
    		return true;
    	}
    	else{
        if(karte.getFarbe()==Color.BLUE){
        	if(karte.getNummer()==ziel.getObersteKartenBlau().getNummer()+1){
        		return true;
        	}
        	if(karte.getNummer()==ziel.getObersteKartenBlau().getNummer()-1){
        		return true;
        	}
        	else{
        		return false;
        	}
        }
        if(karte.getFarbe()==Color.RED){
        	if(karte.getNummer()==ziel.GetObersteKartenRot().getNummer()+1){
        		return true;
        	}
        	if(karte.getNummer()==ziel.GetObersteKartenRot().getNummer()-1){
        		return true;
        	}
        	else{
        		return false;
        	}
        }
        if(karte.getFarbe()==Color.YELLOW){
        	if(karte.getNummer()==ziel.GetObersteKartenGelb().getNummer()+1){
        		return true;
        	}
        	if(karte.getNummer()==ziel.GetObersteKartenGelb().getNummer()-1){
        		return true;
        	}
        	else{
        		return false;
        	}
        }
        if(karte.getFarbe()==Color.GREEN){
        	if(karte.getNummer()==ziel.GetObersteKartenGruen().getNummer()+1){
        		return true;
        	}
        	if(karte.getNummer()==ziel.GetObersteKartenGruen().getNummer()-1){
        		return true;
        	}
        	else{
        		return false;
        	}
        }
        else{
        	return false;
        }
    	}
    	
    }
   
}