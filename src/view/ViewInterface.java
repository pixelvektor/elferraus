package view;

/** Hochschule Hamm-Lippstadt
 * Praktikum Informatik II (ElferRaus)
 * (C) 2015 Lara Sievers, Adrian Schmidt, Fabian Schneider
 * 22.05.2015
 */

import control.Game;

/**
 * Interface der View. Gibt die Schnittstelle zur control vor.
 */
public interface ViewInterface {

	/**
	 * Aktualisiert die View auf den momentanen Spielstatus.
	 * @param game Das Spiel welches update aufruft.
	 */
	public void update(final Game game);
}
