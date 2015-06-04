package control;

/** Hochschule Hamm-Lippstadt
 * Praktikum Informatik II (ElferRaus)
 * (C) 2015 Lara Sievers, Adrian Schmidt, Fabian Schneider
 * 22.05.2015
 */

import java.awt.Color;
import java.util.ArrayList;

import view.View;
import data.Holder;
import data.Karte;
import data.Ki;
import data.Spieler;
import data.Spielfeld;
import data.Stapel;


/**
 * @author adrian
 */
public class Spiel {
	/** Die Schluesselzahl 11. */
	private static final int ELF = 11;
	/** Spieler-ArrayList. */
	private final ArrayList<Holder> spieler = new ArrayList<Holder>();
	/** Stapel fuer die Karten. */
	private final Stapel stapel = new Stapel();
	/** Spielfeld. */
	private final Spielfeld spielfeld = new Spielfeld();
	/** Fuer den Zug aktiver Holder. */
	private Holder activeHolder;
	/** Fuer den Zug aktives Ziel. */
	private Holder targetHolder;
	/** Index des aktiven Spielers. */
	private int activePlayer=0;
	/** Anzahl der vorhandenen Kis */
	private final int kiAnzahl;
	/** Speichert die View. */
	private final View view;
	/** true solange das Spiel laeuft. */
	private boolean isRunning = true;
	
	
	/**
	 * Erstellt ein Spiel.
	 */
    public Spiel(View view) {
    	this.view = view;
    	kiAnzahl = 1;
    	gameInit();
	}
    
    /** Getter fuer die Spieler.
	 * @return Gibt die Spieler zurueck.
	 */
	public ArrayList<Holder> getSpieler() {
		return spieler;
	}

	/** Getter fuer den Stapel.
	 * @return Gibt den Stapel zurueck.
	 */
	public Stapel getStapel() {
		return stapel;
	}

	/** Getter fuer das Spielfeld.
	 * @return Gibt das Spielfeld zurueck.
	 */
	public Spielfeld getSpielfeld() {
		return spielfeld;
	}
	
	public void exit() {
		isRunning = false;
	}

	public void naechsterSpieler(){
		if(activePlayer<kiAnzahl){
			activePlayer=activePlayer+1;
		}
		else{
			activePlayer=0;
		}
		activeHolder=spieler.get(activePlayer);
		
		
		System.out.println("test2");
		System.out.println(activeHolder.toString());
		System.out.println(activePlayer);
		
		startRound();
	}

	/** Der gewuenschte Zug fuer den aktiven Spieler wird gesetzt.
	 * @param color Farbe der zu bewegenden Karte.
	 * @param number Nummer der zu bewegenden Karte.
	 * @return true wenn der Zug durchgefuehrt wurde. Sonst false.
	 */
	public boolean setMove(final Color color, final int number) {
		return false;
	}
	
	/** Der aktive Spieler zieht eine Karte vom Stapel.
	 * @return true wenn die Karte vom Stapel gezogen wurde. Sonst false (=leer).
	 */
	public boolean pull() {
		return move(stapel.getObersteKarte(), activeHolder);
	}

	/** Methode um eine Karte einem neuen Holder zu ueberschreiben.
	 *  @param karte die Karte die verschoben werden soll. Nicht null.
	 *  @param ziel Holder an den die Karte geht.
	 *  @return true wenn der Zug erfolgreich ist. Sonst false (Karte null).
	 */
	private boolean move(Karte karte, Holder ziel){
		if (karte != null) {
			if(karte.getNummer()==ELF){
				ziel.add(karte);
				return true;
			}
			if (pruefeZug(karte, ziel)){
				ziel.add(karte);
		    	return true;
			}
		}
		return false;
	}

	/** Initialisiert das Spiel.
     *
     */
    private void gameInit(){
    	spieler.add(new Spieler());
    	for(int y=1; y<=kiAnzahl; y++){
    	 spieler.add(new Ki());	
     	}
    	kartenInit();
    	gameStart();
    }
    
    private void gameStart(){
    	stapel.mischen();
    	for(int i=0; i<ELF; i++){
    		move(stapel.getObersteKarte(), spieler.get(0));
    		for(int y=1; y<=kiAnzahl; y++){
    		 move(stapel.getObersteKarte(), spieler.get(y));
    		}
    	}
    	for(int i=0; i<ELF; i++){
    		if(spieler.get(0).getKarten().get(i).getNummer()==ELF){
    			activeHolder=spieler.get(0);
    			activePlayer=0;
    		}
    		for(int x=1; x<=kiAnzahl; x++){
    		if(spieler.get(x).getKarten().get(i).getNummer()==ELF){
    			activeHolder=spieler.get(x);
    			activePlayer=x;
    		 }
    		
    		}
    		activeHolder=spieler.get(activePlayer);
    	}
    	
    	System.out.println("test3");
    	System.out.println(activeHolder.toString());
    	System.out.println(activePlayer);
    	startRound();
    }
    
    private void startRound(){
    	if(isRunning=true){
    		if(activePlayer!= 0){
    		//hier wird die ki aufgefordert etwas zu tun
    			System.out.println("Ki macht");
    			
    		}
    		view.update(this);
    		
    	}
    	
    }
    
    /** Karten werden erstellt, danach an die Methode move() uebergeben.
     *
     */
    private void kartenInit(){
    	for(int nummer=1; nummer<=20; nummer++){
    		Karte karte = new Karte(Color.BLUE, nummer);
    		stapel.add(karte);
    	}
    	for(int nummer=1; nummer<=20; nummer++){
    		Karte karte = new Karte(Color.ORANGE, nummer);
    		stapel.add(karte);
    	}
    	for(int nummer=1; nummer<=20; nummer++){
    		Karte karte = new Karte(Color.GREEN, nummer);
    		stapel.add(karte);
    	}
    	for(int nummer=1; nummer<=20; nummer++){
    		Karte karte = new Karte(Color.RED, nummer);
    		stapel.add(karte);
    	}
    	
    	
    }
    
    /** Prueft ob der aktuelle Zug gueltig ist.
     * @param karte die Karte die verschoben werden soll.
     * @param ziel Holder an den die Karte geht.
     * @return true wenn ziel kein Spielfeld ist, Farbe und Nummer stimmen. Sonst false.
     */
    private boolean pruefeZug(Karte karte, Holder ziel){
    	if(!ziel.equals(spielfeld)){
    		return true;
    	}
    	else{
        if(karte.getFarbe()==Color.BLUE){
        	if(karte.getNummer()==ziel.getHighestCard(Color.BLUE).getNummer()+1){
        		return true;
        	}
        	if(karte.getNummer()==ziel.getLowestCard(Color.BLUE).getNummer()-1){
        		return true;
        	}
        	else{
        		return false;
        	}
        }
        if(karte.getFarbe()==Color.RED){
        	if(karte.getNummer()==ziel.getHighestCard(Color.RED).getNummer()+1){
        		return true;
        	}
        	if(karte.getNummer()==ziel.getLowestCard(Color.RED).getNummer()-1){
        		return true;
        	}
        	else{
        		return false;
        	}
        }
        if(karte.getFarbe()==Color.ORANGE){
        	if(karte.getNummer()==ziel.getHighestCard(Color.ORANGE).getNummer()+1){
        		return true;
        	}
        	if(karte.getNummer()==ziel.getLowestCard(Color.ORANGE).getNummer()-1){
        		return true;
        	}
        	else{
        		return false;
        	}
        }
        if(karte.getFarbe()==Color.GREEN){
        	if(karte.getNummer()==ziel.getHighestCard(Color.GREEN).getNummer()+1){
        		return true;
        	}
        	if(karte.getNummer()==ziel.getLowestCard(Color.GREEN).getNummer()-1){
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