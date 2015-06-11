package control;

/** Hochschule Hamm-Lippstadt
 * Praktikum Informatik II (ElferRaus)
 * (C) 2015 Lara Sievers, Adrian Schmidt, Fabian Schneider
 * 22.05.2015
 */

import java.util.ArrayList;

import view.View;
import data.Color;
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
	/** Die Farben der Karten. */
	private static final Color[] COLOR = {Color.BLUE, Color.GREEN, Color.ORANGE, Color.RED};
	/** Spieler-ArrayList. */
	private final ArrayList<Holder> spieler = new ArrayList<Holder>();
	/** Stapel fuer die Karten. */
	private final Stapel stapel = new Stapel();
	/** Spielfeld. */
	private final Spielfeld spielfeld = new Spielfeld();
	/** Index des aktiven Spielers. */
	private int activePlayer;
	/** Anzahl der vorhandenen Kis */
	private final int kiAnzahl;
	/** Name des Gewinners. */
	private String winner = "niemand";
	/** true solange das Spiel laeuft. */
	private boolean isRunning = true;
	/** Haeufigkeit des Aufraufs der Methode setMove(). */
	private int movePerformed;
	/** Haeufigkeit des Aufraufs der Methode pull(). */
	private int pullPerformed;
	
	/** Erstellt ein Spiel.
	 *
	 */
    public Spiel(final View view) {
    	kiAnzahl = view.getCountKi();
    	gameInit();
    	while (isRunning) {
			view.update(this);
		}
	}
    
    /** Getter fuer die Farben des Spiels.
	 * @return Gibt die Farben des Spiels zurueck.
	 */
	public static Color[] getColor() {
		return COLOR;
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
	
	/** Getter fuer den Namen des Gewinners.
	 * @return Gibt den Namen des Gewinners zurueck.
	 */
	public String getWinner() {
		return winner;
	}

	/** Getter fuer den Spielzustand.
	 * @return Gibt den Spielzustand zurueck. True wenn das Spiel laeuft, sonst false.
	 */
	public boolean isRunning() {
		return isRunning;
	}

	public void exit() {
		isRunning = false;
	}
    /** Wechselt bei Aufruf den aktiven Holder und startet eine neue Runde.
     * 
     */
	public void naechsterSpieler(){
		if(movePerformed == 0 &&  pullPerformed == 0 && pruefeObZugMoeglich()){
			System.out.println("Sie muessen eine Aktion ausfuehren!");			
		}else{
			if(activePlayer < kiAnzahl){
				activePlayer++;
			}
			else{
				activePlayer=0;
			}
			
			System.out.println(spieler.get(activePlayer).getName() + "NS");
			System.out.println("ActivePlayer: " + activePlayer);
			movePerformed=0;
			pullPerformed=0;
			startRound();
		}	
	}
	
    /** Der gewuenschte Zug fuer den aktiven Spieler wird gesetzt.
	 * @param color Farbe der zu bewegenden Karte.
	 * @param number Nummer der zu bewegenden Karte.
	 * @return true wenn der Zug durchgefuehrt wurde. Sonst false.
	 */
	public boolean setMove(final Color color, final int number) {
			for (Karte k : spieler.get(activePlayer).getKarten()) {
				if (k.getFarbe().equals(color) && k.getNummer() == number) {
					if(k.getNummer() == ELF){
						if (move(k, spielfeld)) {
							spieler.get(activePlayer).remove(k);
							movePerformed++;
							checkWinner();
							return true;
						}	
					}if(!pruefeElfAufHand()){
						if (move(k, spielfeld)) {
							spieler.get(activePlayer).remove(k);
							movePerformed++;
							checkWinner();
							return true;
						}	
			        }else{
			        	movePerformed++;
			        	checkWinner();
			        	return true;
			        }
		        }
		    
			}
			return false;
	}
	
	/** Der aktive Spieler zieht eine Karte vom Stapel.
	 * @return true wenn die Karte vom Stapel gezogen wurde. Sonst false (=leer).
	 */
	public boolean pull() {
		boolean result = true;
		if(movePerformed == 0){
			if(stapel.getKarten().size() != 0){
			     if(!pruefeElfAufHand()){
			    	 Karte karte = stapel.getNext();
			    	 if (karte.getNummer() == ELF) {
			    		 result = move(karte, spielfeld);
			         } else {
				         result = move(karte, spieler.get(activePlayer));
				         result = move(stapel.getNext(), spieler.get(activePlayer));
				         result = move(stapel.getNext(), spieler.get(activePlayer));
			         }
			    	 pullPerformed++;
			         naechsterSpieler();
			         return result;
			     }
			     else{
			    	 pullPerformed++;
					 naechsterSpieler();
					 return true;
			     }
		    }else{
				return false;
			}
		}else{
			System.out.println("Sie koennen nach einem Zug nicht ziehen!");
			return true; 
		}
		
	}
	
	/** Prueft ob der aktive Spieler noch einen Zug ausf�hren kann, oder ob er noch Ziehen oder Legen kann.
	 * @return true, wenn noch Karten auf dem Stapel sind, oder wenn ein Zug moeglich ist, sonst false.
	 */
	private boolean pruefeObZugMoeglich() {
		if(!stapel.getKarten().isEmpty()){
			return true;
		}
		if(spielfeld.getHighestCard(Color.BLUE) != null){
			for(Karte k : spieler.get(activePlayer).getBlaueKarten()){
				if(pruefeZug(k, spielfeld)){
					return true;
				}			
			}
		}
		if(spielfeld.getHighestCard(Color.GREEN) != null){
			for(Karte k : spieler.get(activePlayer).getGrueneKarten()){
				if(pruefeZug(k, spielfeld)){
					return true;
				}
			}
		}
		if(spielfeld.getHighestCard(Color.ORANGE) != null){
			for(Karte k : spieler.get(activePlayer).getOrangeKarten()){
				if(pruefeZug(k, spielfeld)){
					return true;
				}
			}
		}
		if(spielfeld.getHighestCard(Color.RED) != null){
			for(Karte k : spieler.get(activePlayer).getRoteKarten()){
				if(pruefeZug(k, spielfeld)){
					return true;
				}
			}
		}
		return false;
	}

	private void checkWinner() {
		if (spieler.get(activePlayer).getKarten().size() == 0) {
			winner = spieler.get(activePlayer).getName();
			exit();
		}
	}

    /** Prueft ob der aktive Spieler eine Elf auf der Hand hat, wenn ja wird die Elf automatisch auf das Spielfeld gelegt. 
     * @return true, wenn Spieler eine Elf auf der Hand hat, sonst false.
     */
	private boolean pruefeElfAufHand() {
		for (Karte k : spieler.get(activePlayer).getKarten()){
			if(k.getNummer() == ELF){
				System.out.println("Zug nicht moeglich! Elf wird automatisch gelegt!");
				move(k, spielfeld);
				spieler.get(activePlayer).remove(k);
				return true;
			}
		}
		
		return false;
	}

	/** Methode um eine Karte einem neuen Holder zu ueberschreiben.
	 *  @param karte die Karte die verschoben werden soll.
	 *  @param ziel Holder an den die Karte geht.
	 *  @return true wenn der Zug erfolgreich ist. Sonst false (Karte null).
	 */
	private boolean move(final Karte karte, final Holder ziel){
		if (karte != null) {
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
    	spieler.add(new Spieler("Human"));
    	
    	for(int y=1; y<=kiAnzahl; y++){
    		spieler.add(new Ki("KI " + y,1));	
     	}
    	
    	kartenInit();
    	gameStart();
    }
    
   /** Der Stapel wird gemischt, danach werden jeweils 11 Karten an die Spieler verteilt. Der Spieler mit einer 11 beginnt. 
    *  
    */
    private void gameStart(){
    	stapel.mischen();
    	
    	// Verteilen von je 11 Karten an jeden Spieler
    	for(int i=0; i<ELF; i++){
    		for(int y = 0; y <= kiAnzahl; y++){
    			move(stapel.getNext(), spieler.get(y));
    		}
    	}
    	
    	// Sucht den ersten Spieler mit einer 11 raus und setzt ihn als aktiven Spieler
    	for(int i=0; i<ELF; i++){
    		for(int j=0; j<=kiAnzahl; j++){
    			if(spieler.get(j).getKarten().get(i).getNummer()==ELF){
        			activePlayer = j;
        			// Beenden der Schleifen bei gefundener 11
        			i = ELF + 1;
        			j = kiAnzahl + 1;
        		} else {
					activePlayer = 0;
				}
    		}
    	}
    	
    	System.out.println("GameStart");
    	System.out.println(spieler.get(activePlayer).getName() + " GS");
    	System.out.println("ActivePlayer: " + activePlayer);
    	startRound();
    }
    
    private void startRound(){
    	if(isRunning && activePlayer > 0) {
    		spieler.get(activePlayer).react(this);
    	}
    }
    
    /** Karten werden erstellt, danach zum Stapel hinzugefuegt.
     *
     */
    private void kartenInit(){
    	for(int nummer=1; nummer<=20; nummer++){
    		Karte karte = new Karte(Color.BLUE, nummer);
    		stapel.add(karte);
    	}
    	for(int nummer=1; nummer<=20; nummer++){
    		Karte karte = new Karte(Color.GREEN, nummer);
    		stapel.add(karte);
    	}
    	for(int nummer=1; nummer<=20; nummer++){
    		Karte karte = new Karte(Color.ORANGE, nummer);
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
    private boolean pruefeZug(final Karte karte, final Holder ziel){
    	if (karte.getNummer() == ELF)
    		return true;
    	
    	boolean result = false;
    	
    	if(ziel.equals(spielfeld)){
    		for (int i = 0; i < COLOR.length; i++) {
    			if(karte.getFarbe().equals(COLOR[i])){
                	if(karte.getNummer() == ziel.getHighestCard(COLOR[i]).getNummer()+1){
                		result = true;
                	} else if(karte.getNummer() == ziel.getLowestCard(COLOR[i]).getNummer()-1){
                		result = true;
                	}
    			}
    		}
    	} else {
			result = true;
		}
    	
    	return result;
    }
}