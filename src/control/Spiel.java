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
	/** Spieler-ArrayList. */
	private final ArrayList<Holder> spieler = new ArrayList<Holder>();
	/** Stapel fuer die Karten. */
	private final Stapel stapel = Stapel.getInstance();
	/** Spielfeld. */
	private final Spielfeld spielfeld = Spielfeld.getInstance();
	/** Index des aktiven Spielers. */
	private int activePlayer;
	/** Anzahl der vorhandenen Kis */
	private final int countKi;
	/** Schwierigkeit der KIs. */
	private boolean difficulty;
	/** Name des Gewinners. */
	private String winner = "niemand";
	/** true solange das Spiel laeuft. */
	private boolean isRunning = true;
	/** Haeufigkeit des Aufraufs der Methode setMove(). */
	private int movePerformed;
	/** Haeufigkeit des Aufraufs der Methode pull(). */
	private int pullPerformed;
	
	/** Erstellt ein Spiel und updatet die View.
	 * @param view Die TUI des Spiels
	 */
    public Spiel(final View view) {
    	countKi = view.getCountKi();
    	difficulty = view.getDifficulty();
    	gameInit();
    	while (isRunning) {
			view.update(this);
		}
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

	/** Beendet das Spiel.
	 */
	public void exit() {
		isRunning = false;
	}
	
    /** Wechselt den aktiven Spieler, falls kein Zug mehr moeglich ist, und startet eine neue Runde.
     */
	public void naechsterSpieler(){
		if(movePerformed == 0 &&  pullPerformed == 0 && pruefeObZugMoeglich()){
			System.out.println("Sie muessen eine Aktion ausfuehren!");			
		}else{
			if(activePlayer < countKi){
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
	
	/** Der aktive Spieler zieht eine Karte vom Stapel, falls er vorher Keine gelegt hat oder noch eine Elf auf der Hand hat.
	 * @return true wenn die Karte vom Stapel gezogen wurde, wenn noch eine Elf auf der Hand ist, wenn schon gelegt worden ist. Sonst false (=leer).
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
	
	public boolean pruefeAufAllIn() {
		if(pruefeObBlauAllIn() && pruefeObGruenAllIn() && pruefeObOrangeAllIn() && pruefeObRotAllIn()){
			allIn();
			return true;
		}
		System.out.println("Sie koennen nicht alle Karten ablegen!");
		return false;
	}

	private void allIn() {
		for (Karte k : spieler.get(activePlayer).getKarten()) {
			move(k,spielfeld);
			spieler.get(activePlayer).remove(k);
		}
		checkWinner();
	}
	
	private boolean pruefeObBlauAllIn(){
		if(pruefeObBlauAllInLow() && pruefeObBlauAllInHigh()){
			return true;
		}
		return false;
	}
	
	private boolean pruefeObGruenAllIn(){
		if(pruefeObGruenAllInLow() && pruefeObGruenAllInHigh()){
			return true;
		}
		return false;
	}
	
	private boolean pruefeObOrangeAllIn(){
		if(pruefeObOrangeAllInLow() && pruefeObOrangeAllInHigh()){
			return true;
		}
		return false;
	}
	
	private boolean pruefeObRotAllIn(){
		if(pruefeObRotAllInLow() && pruefeObRotAllInHigh()){
			return true;
		}
		return false;
	}

	/** Prueft ob der aktive Spieler noch einen Zug ausfuehren kann, oder ob er noch Ziehen oder Legen kann.
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

	private boolean pruefeObBlauAllInHigh(){
		int temp;
		int i=0;
		int max=spieler.get(activePlayer).getBlaueKarten().size()-1;
		
		if(spieler.get(activePlayer).getBlaueKarten().size() != 0){
			while(i <= max){
				if(spieler.get(activePlayer).getBlaueKarten().get(i).getNummer()==spielfeld.getHighestCard(Color.BLUE).getNummer()+1){
					temp=i;
					if(i==max){
						return true;
					}else{
						for(int x=i; x < max ; x++){
							if(spieler.get(activePlayer).getBlaueKarten().get(temp+1).getNummer() == spieler.get(activePlayer).getBlaueKarten().get(temp).getNummer()+1){
								temp++;
								i++;
								if(temp==max){
									return true;
								}
							}else{
								return false;
							}
						}
					}	
				}else{
					i++;
				}
			}
			return false;
		}else{
			return true;
		}
	}
	
	private boolean pruefeObGruenAllInHigh(){
		int temp;
		int i=0;
		int max=spieler.get(activePlayer).getGrueneKarten().size()-1;
		
		if(spieler.get(activePlayer).getGrueneKarten().size() != 0){
			while(i <= max){
				if(spieler.get(activePlayer).getGrueneKarten().get(i).getNummer()==spielfeld.getHighestCard(Color.GREEN).getNummer()+1){
					temp=i;
					if(i==max){
						return true;
					}else{
						for(int x=i; x < max ; x++){
							if(spieler.get(activePlayer).getGrueneKarten().get(temp+1).getNummer() == spieler.get(activePlayer).getGrueneKarten().get(temp).getNummer()+1){
								temp++;
								i++;
								if(temp==max){
									return true;
								}
							}else{
								return false;
							}
						}
					}	
				}else{
					i++;
				}
			}
			return false;
		}else{
			return true;
		}
	}
	
	private boolean pruefeObOrangeAllInHigh(){
		int temp;
		int i=0;
		int max=spieler.get(activePlayer).getOrangeKarten().size()-1;

		if(spieler.get(activePlayer).getOrangeKarten().size() != 0){
			while(i <= max){
				if(spieler.get(activePlayer).getOrangeKarten().get(i).getNummer()==spielfeld.getHighestCard(Color.ORANGE).getNummer()+1){
					temp=i;
					if(i==max){
						return true;
					}else{
						for(int x=i; x < max ; x++){
							if(spieler.get(activePlayer).getOrangeKarten().get(temp+1).getNummer() == spieler.get(activePlayer).getOrangeKarten().get(temp).getNummer()+1){
								temp++;
								i++;
								if(temp==max){
									return true;
								}
							}else{
								return false;
							}
						}
					}	
				}else{
					i++;
				}
			}
			return false;
		}else{
			return true;
		}
	}

	private boolean pruefeObRotAllInHigh(){
		int temp;
		int i=0;
		int max=spieler.get(activePlayer).getRoteKarten().size()-1;

		if(spieler.get(activePlayer).getRoteKarten().size() != 0){
			while(i <= max){
				if(spieler.get(activePlayer).getRoteKarten().get(i).getNummer()==spielfeld.getHighestCard(Color.RED).getNummer()+1){
					temp=i;
					if(i==max){
						return true;
					}else{
						for(int x=i; x < max ; x++){
							if(spieler.get(activePlayer).getRoteKarten().get(temp+1).getNummer() == spieler.get(activePlayer).getRoteKarten().get(temp).getNummer()+1){
								temp++;
								i++;
								if(temp==max){
									return true;
								}
							}else{
								return false;
							}
						}
					}	
				}else{
					i++;
				}
			}
			return false;
		}else{
			return true;
		}
	}
	
	private boolean pruefeObBlauAllInLow(){
		int temp;
		int i=spieler.get(activePlayer).getBlaueKarten().size()-1;
		int max=0;

		if(spieler.get(activePlayer).getBlaueKarten().size() != 0){
			while(i >= max){
				if(spieler.get(activePlayer).getBlaueKarten().get(i).getNummer()==spielfeld.getLowestCard(Color.BLUE).getNummer()-1){
					temp=i;
					if(i==max){
						System.out.println("AllIn2");
						return true;
					}else{
						for(int x=i; x > max ; x--){
							if(spieler.get(activePlayer).getBlaueKarten().get(temp-1).getNummer() == spieler.get(activePlayer).getBlaueKarten().get(temp).getNummer()-1){
								temp--;
								i--;
								if(temp==max){
									return true;
								}
							}else{
								return false;
							}
						}
					}	
				}else{
					i--;
				}
			}
			return false;
		}else{
			return true;
		}
	}
	
	private boolean pruefeObGruenAllInLow(){
		int temp;
		int i=spieler.get(activePlayer).getGrueneKarten().size()-1;
		int max=0;

		if(spieler.get(activePlayer).getGrueneKarten().size() != 0){
			while(i >= max){
				if(spieler.get(activePlayer).getGrueneKarten().get(i).getNummer()==spielfeld.getLowestCard(Color.GREEN).getNummer()-1){
					temp=i;
					if(i==max){
						return true;
					}else{
						for(int x=i; x > max ; x--){
							if(spieler.get(activePlayer).getGrueneKarten().get(temp-1).getNummer() == spieler.get(activePlayer).getGrueneKarten().get(temp).getNummer()-1){
								temp--;
								i--;
								if(temp==max){
									return true;
								}
							}else{
								return false;
							}
						}
					}	
				}else{
					i--;
				}
			}
			return false;
		}else{
			return true;
		}
	}
	
	private boolean pruefeObOrangeAllInLow(){
		int temp;
		int i=spieler.get(activePlayer).getOrangeKarten().size()-1;
		int max=0;

		if(spieler.get(activePlayer).getOrangeKarten().size() != 0){
			while(i >= max){
				if(spieler.get(activePlayer).getOrangeKarten().get(i).getNummer()==spielfeld.getLowestCard(Color.ORANGE).getNummer()-1){
					temp=i;
					if(i==max){
						return true;
					}else{
						for(int x=i; x > max ; x--){
							if(spieler.get(activePlayer).getOrangeKarten().get(temp-1).getNummer() == spieler.get(activePlayer).getOrangeKarten().get(temp).getNummer()-1){
								temp--;
								i--;
								if(temp==max){
									return true;
								}
							}else{
								return false;
							}
						}
					}	
				}else{
					i--;
				}
			}
			return false;
		}else{
			return true;
		}
	}
	private boolean pruefeObRotAllInLow(){
		int temp;
		int i=spieler.get(activePlayer).getRoteKarten().size()-1;
		int max=0;

		if(spieler.get(activePlayer).getRoteKarten().size() != 0){
			while(i >= max){
				if(spieler.get(activePlayer).getRoteKarten().get(i).getNummer()==spielfeld.getLowestCard(Color.RED).getNummer()-1){
					temp=i;
					if(i==max){
						return true;
					}else{
						for(int x=i; x > max ; x--){
							if(spieler.get(activePlayer).getRoteKarten().get(temp-1).getNummer() == spieler.get(activePlayer).getRoteKarten().get(temp).getNummer()-1){
								temp--;
								i--;
								if(temp==max){
									return true;
								}
							}else{;
								return false;
							}
						}
					}	
				}else{
					i--;
				}
			}
			return false;
		}else{
			return true;
		}
	}
	
	/** Prueft ob der aktive Spieler gewonnen hat.
	 */
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
     */
    private void gameInit(){
    	spieler.add(new Spieler("Sie"));
    	
    	for(int y=1; y<=countKi; y++){
    		spieler.add(new Ki("KI " + y, difficulty));	
     	}
    	
    	kartenInit();
    	gameStart();
    }
    
   /** Der Stapel wird gemischt, danach werden jeweils 11 Karten an die Spieler verteilt. Der Spieler mit einer 11 beginnt. 
    */
    private void gameStart(){
    	stapel.mischen();
    	
    	// Verteilen von je 11 Karten an jeden Spieler
    	for(int i=0; i<ELF; i++){
    		for(int y = 0; y <= countKi; y++){
    			move(stapel.getNext(), spieler.get(y));
    		}
    	}
    	
    	// Sucht den ersten Spieler mit einer 11 raus und setzt ihn als aktiven Spieler
    	for(int i=0; i<ELF; i++){
    		for(int j=0; j<=countKi; j++){
    			if(spieler.get(j).getKarten().get(i).getNummer()==ELF){
        			activePlayer = j;
        			// Beenden der Schleifen bei gefundener 11
        			i = ELF + 1;
        			j = countKi + 1;
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
    
    /** Startet eine neue Runde.
     */
    private void startRound(){
    	if(isRunning && activePlayer > 0) {
    		spieler.get(activePlayer).react(this);
    	}
    }
    
    /** Karten werden erstellt, danach zum Stapel hinzugefuegt.
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
    		for (int i = 0; i < Color.values().length; i++) {
    			if(karte.getFarbe().equals(Color.values()[i])){
                	if(karte.getNummer() == ziel.getHighestCard(Color.values()[i]).getNummer()+1){
                		result = true;
                	} else if(karte.getNummer() == ziel.getLowestCard(Color.values()[i]).getNummer()-1){
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