package data;

/**
 * Hochschule Hamm-Lippstadt Praktikum Informatik II (ElferRaus) (C) 2015 Lara
 * Sievers, Adrian Schmidt, Fabian Schneider 22.05.2015
 */

public class Card {
	/** Farbe der Karte. */
	private final Color color;
	/** Nummer der Karte. */
	private final int number;

	/**
	 * Erstellt eine Karte.
	 * @param color Farbe der Karte.
	 * @param number Nummer der Karte.
	 */
	public Card(final Color color, final int number) {
		this.color = color;
		this.number = number;
	}

	/**
	 * Getter fuer die Farbe der Karte.
	 * @return farbe Farbe der Karte.
	 */
	public Color getColor() {
		return color;
	}

	/**
	 * Getter fuer die Nummer
	 * @return nummer Nummer der Karte.
	 */
	public int getNumber() {
		return number;
	}

	/**
	 * Gibt die farbe und die Nummer der Karte als String zurueck.
	 */
	public String toString() {
		return color + " " + number + "\n";
	}

}
