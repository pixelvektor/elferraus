package data;

/** Hochschule Hamm-Lippstadt
 * Praktikum Informatik II (ElferRaus)
 * (C) 2015 Lara Sievers, Adrian Schmidt, Fabian Schneider
 * 22.05.2015
 */

import java.util.Collections;

public class Stack extends Holder{
	/** Enthaelt die Stapel Instanz. */
	private static Stack stack;
	
	/** Ctor fuer den Stapel.
	 */
	private Stack() {}
	
	/** Getter fuer die Stapel Instanz.
	 * @return Gibt die Stapel Instanz zurueck.
	 */
	public static Stack getInstance() {
		if (stack == null) {
			stack = new Stack();
		}
		return stack;
	}
	
	/** Mischt den Stapel.
	 */
	public void shuffle() {
		Collections.shuffle(this.getCards());
	}
	
	/** Nimmt eine Karte vom Stapel und gibt diese zurueck.
	 * Die Karte wird auf dem Stapel geloescht.
	 * @return Unterste Karte vom Stapel. null bei leerem Stapel.
	 */
	public Card getNext() {
		Card karte = null;
		try {
			karte = this.getCards().get(0);
		} catch (IndexOutOfBoundsException e) {
			//e.printStackTrace();
		}
		this.remove(karte);
		return karte;
	}
}

