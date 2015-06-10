package data;

import java.util.ArrayList;

import control.Spiel;

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

	public void setName(final String name) {
		this.name = name;
	}
	
	public void add(final Karte karte) {
		karten.add(karte);
		sort();
	}
	
	public void remove(final Karte karte) {
		karten.remove(karte);
	}
	
	public String[] getMove(){
		String[] result = {};
		return result;
	}

	/** Gibt die kleinste Karte unterhalb von 12 der gesuchten Farbe zurueck.
	 * @param farbe Die Farbe (BLUE, GREEN, ORANGE, RED) fuer welche die kleinste Karte gesucht wird.
	 * @return Die kleinste Karte der Farbe, sofern kleiner gleich 11. Sonst null.
	 */
	public Karte getLowestCard(final Color farbe) {
		Karte lowestCard = null;
		
		for (Karte k : karten) {
			if (k.getFarbe().equals(farbe) && k.getNummer() < 12) {
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

	/** Gibt die hoechste Karte oberhalb von 10 der gesuchten Farbe zurueck.
	 * @param farbe Die Farbe (BLUE, GREEN, ORANGE, RED) fuer welche die hoechste Karte gesucht wird.
	 * @return Die hoechste Karte der Farbe, sofern groesser gleich 11. Sonst null.
	 */
	public Karte getHighestCard(final Color farbe) {
		Karte highestCard = null;
		
		for (Karte k : karten) {
			if (k.getFarbe().equals(farbe) && k.getNummer() > 10) {
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
	
	/** Getter fuer die Karten des Holders.
	 * @return Gibt die Karten des Holders zurueck.
	 */
	public ArrayList<Karte> getKarten() {
		return karten;
	}

	private void sort() {
		ArrayList<Karte> blue = new ArrayList<Karte>();
		ArrayList<Karte> green = new ArrayList<Karte>();
		ArrayList<Karte> orange = new ArrayList<Karte>();
		ArrayList<Karte> red = new ArrayList<Karte>();
		
		// Aufsplitten der Farben im Stapel
		for (Karte k : karten) {
			if (k.getFarbe().equals(Color.BLUE)) {
				blue.add(k);
			} else if (k.getFarbe().equals(Color.GREEN)) {
				green.add(k);
			} else if (k.getFarbe().equals(Color.ORANGE)) {
				orange.add(k);
			} else {
				red.add(k);
			}
		}
		
		// Sortieren der Nummern
		sortNumber(blue);
		sortNumber(green);
		sortNumber(orange);
		sortNumber(red);
		
		karten.clear();
		
		// Zusammenfuegen der Farben in einen Stapel
		for (Karte k : blue) {
			karten.add(k);
		}
		for (Karte k : green) {
			karten.add(k);
		}
		for (Karte k : orange) {
			karten.add(k);
		}
		for (Karte k : red) {
			karten.add(k);
		}
	}

	/** Sortiert die Nummern einer Kartenfarbe aufsteigend.
	 * @param farbe ArrayList der zu sortierenden Kartenfarbe.
	 */
	private void sortNumber(final ArrayList<Karte> farbe) {
		for (int i = 0; i < farbe.size(); i++) {
			Karte temp = farbe.get(i);
			int j = i;
			while (j > 0 && farbe.get(j-1).getNummer() > temp.getNummer()) {
				farbe.add(j, farbe.get(j-1));
				farbe.remove(j+1);
				j--;
			}
			farbe.add(j, temp);
			farbe.remove(j+1);
		}
	}
}
