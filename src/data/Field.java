package data;

/** Hochschule Hamm-Lippstadt
 * Praktikum Informatik II (ElferRaus)
 * (C) 2015 Lara Sievers, Adrian Schmidt, Fabian Schneider
 * 22.05.2015
 */

/**
 * Das Spielfeld auf dem die Karten abgelegt werden. Kann nur Karten aufnehmen
 * und nicht mehr abgeben.
 */
public class Field extends Holder {
	/** Enthaelt die Stapel Instanz. */
	private static Field field;

	/** Ctor fuer das Spielfeld. */
	private Field() {
	}

	/**
	 * Getter fuer die Spielfeld Instanz.
	 * @return Gibt die Spielfeld Instanz zurueck.
	 */
	public static Field getInstance() {
		if (field == null) {
			field = new Field();
		}
		return field;
	}

	/**
	 * Inaktive Methode.
	 */
	public void remove(final Card card) {
		// Leer, damit nicht geloescht werden kann.
	}
}
