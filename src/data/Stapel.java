package data;

/** Hochschule Hamm-Lippstadt
 * Praktikum Informatik II (ElferRaus)
 * (C) 2015 Lara Sievers, Adrian Schmidt, Fabian Schneider
 * 22.05.2015
 */

import java.util.Collections;

public class Stapel extends Holder{
	/** Enthaelt die Stapel Instanz. */
	private static Stapel stapel;
	
	/** Ctor fuer den Stapel.
	 */
	private Stapel() {}
	
	/** Getter fuer die Stapel Instanz.
	 * @return Gibt die Stapel Instanz zurueck.
	 */
	public static Stapel getInstance() {
		if (stapel == null) {
			stapel = new Stapel();
		}
		return stapel;
	}
	
	/** Mischt den Stapel.
	 */
	public void mischen() {
		Collections.shuffle(this.getKarten());
	}
	
	/** Nimmt eine Karte vom Stapel und gibt diese zurueck.
	 * Die Karte wird auf dem Stapel geloescht.
	 * @return Unterste Karte vom Stapel. null bei leerem Stapel.
	 */
	public Karte getNext() {
		Karte karte = null;
		try {
			karte = this.getKarten().get(0);
		} catch (IndexOutOfBoundsException e) {
			//e.printStackTrace();
		}
		this.remove(karte);
		return karte;
	}
}

