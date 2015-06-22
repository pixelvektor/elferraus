package data;

/** Hochschule Hamm-Lippstadt
 * Praktikum Informatik II (ElferRaus)
 * (C) 2015 Lara Sievers, Adrian Schmidt, Fabian Schneider
 * 22.05.2015
 */

public class Player extends Holder {
	
	/** Erstellt einen neuen Spieler.
	 */
	public Player() {
		
	}
	
	/** Erstellt einen neuen Spieler.
	 * @param Name des Spielers.
	 */
	public Player(final String name) {
		setName(name);
	}
}
