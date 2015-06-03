package data;

import java.awt.Color;
import java.util.ArrayList;

import data.Karte;

/** Hochschule Hamm-Lippstadt
 * Praktikum Informatik II (ElferRaus)
 * (C) 2015 Lara Sievers, Adrian Schmidt, Fabian Schneider
 * 22.05.2015
 */

/**
 * @author lara
 */
public abstract class Holder {
    private String name;

	private final ArrayList<Karte> karten = new ArrayList<Karte>();
    
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public void add(Karte karte) {
		karten.add(karte);
		
	}
	
	public abstract void remove(Karte karte);

	/** Gibt die kleinste Karte unterhalb von 11 der gesuchten Farbe zurueck.
	 * @param farbe Die Farbe (BLUE, GREEN, ORANGE, RED) fuer welche die kleinste Karte gesucht wird.
	 * @return Die kleinste Karte der Farbe, sofern kleiner 11. Sonst null.
	 */
	public Karte getLowestCard(final Color farbe) {
		Karte lowestCard = null;
		
		for (Karte k : karten) {
			if (k.getFarbe().equals(farbe) && k.getNummer() < 11) {
				// Sofern noch keine Karte gesetzt wurde passt der erste Treffer immer
				if (lowestCard == null) {
					lowestCard = k;
				}
				// Sofern eine kleinere Karte gefunden ist, wird sie als Kleinste gesetzt
				else if (lowestCard.getNummer() > k.getNummer()) {
					lowestCard = k;
				}
			}
		}
		
		return lowestCard;
	}

	/** Gibt die hoechste Karte oberhalb von 11 der gesuchten Farbe zurueck.
	 * @param farbe Die Farbe (BLUE, GREEN, ORANGE, RED) fuer welche die hoechste Karte gesucht wird.
	 * @return Die hoechste Karte der Farbe, sofern groesser 11. Sonst null.
	 */
	public Karte getHighestCard(final Color farbe) {
		Karte highestCard = null;
		
		for (Karte k : karten) {
			if (k.getFarbe().equals(farbe) && k.getNummer() > 11) {
				// Sofern noch keine Karte gesetzt wurde passt der erste Treffer immer
				if (highestCard == null) {
					highestCard = k;
				}
				// Sofern eine hoehere Karte gefunden ist, wird sie als Hoechste gesetzt
				else if (highestCard.getNummer() < k.getNummer()) {
					highestCard = k;
				}
			}
		}
		
		return highestCard;
	}
	
	/** Nur fuer den Stapel relevant.
	 * UNDER CONSTRUCTION!
	 * @return
	 */
	public Karte getObersteKarte() {
		return karten.get(0);
	}

	public ArrayList<Karte> getKarten() {
		return karten;
	}

	private void sortColor() {
	
	}
	
	private void sortCount() {
		
	}
}
