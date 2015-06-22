package data;

/** Hochschule Hamm-Lippstadt
 * Praktikum Informatik II (ElferRaus)
 * (C) 2015 Lara Sievers, Adrian Schmidt, Fabian Schneider
 * 22.05.2015
 */

public class Karte {
	/** Farbe der Karte. */
	private final Color farbe;
	/** Nummer der Karte. */
	private final int nummer;
	
	/** Erstellt eine Karte.
	 * @param farbe Farbe der Karte.
	 * @param nummer Nummer der Karte.
	 */
	public Karte(final Color farbe, final int nummer){
		this.farbe = farbe;
		this.nummer = nummer;
	}

	/** Getter fuer die Farbe der Karte.
	 * @return farbe Farbe der Karte.
	 */
	public Color getFarbe() {
		return farbe;
	}

	/** Getter fuer die Nummer
	 * @return nummer Nummer der Karte.
	 */
	public int getNumber() {
		return nummer;
	}
	
	/** Gibt die farbe und die Nummer der Karte als String zurueck.
	 */
	public String toString() {
		return farbe + " " + nummer + "\n";
	}

}
