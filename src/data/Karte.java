package data;

import java.awt.Color;

/** Hochschule Hamm-Lippstadt
 * Praktikum Informatik II (ElferRaus)
 * (C) 2015 Lara Sievers, Adrian Schmidt, Fabian Schneider
 * 22.05.2015
 */

/**
 * @author lara
 */
public class Karte {
	/** Farbe der Karte. */
	private final Color farbe;
	/** Nummer der Karte. */
	private final int nummer;
	
	public Karte(final Color farbe, final int nummer){
		this.farbe = farbe;
		this.nummer = nummer;
	}

	public Color getFarbe() {
		return farbe;
	}

	public int getNummer() {
		return nummer;
	}

}
