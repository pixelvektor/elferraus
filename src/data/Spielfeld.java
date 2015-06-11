package data;

/** Hochschule Hamm-Lippstadt
 * Praktikum Informatik II (ElferRaus)
 * (C) 2015 Lara Sievers, Adrian Schmidt, Fabian Schneider
 * 22.05.2015
 */

public class Spielfeld extends Holder {
	/** Enthaelt die Stapel Instanz. */
	private static Spielfeld spielfeld;
	
	/** Ctor fuer das Spielfeld. */
	private Spielfeld() {}
	
	/** Getter fuer die Spielfeld Instanz.
	 * @return Gibt die Spielfeld Instanz zurueck.
	 */
	public static Spielfeld getInstance() {
		if (spielfeld == null) {
			spielfeld = new Spielfeld();
		}
		return spielfeld;
	}
	
	/** Inaktive Methode. 
	 */
	public void remove(final Karte karte) {
		// Leer, damit nicht geloescht werden kann.
	}
}
