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
	
	public void react(final Spiel spiel){
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

	public void add(final Karte karte) {
		karten.add(karte);
		sort();
	}

	public void remove(final Karte karte) {
		karten.remove(karte);
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
	
	/** Gibt alle blauen Karten zur�ck.
	 * @return blue, ArrayList mit den Karten der Farbe BLUE. 
	 */
	public ArrayList<Karte> getBlaueKarten() {
		ArrayList<Karte> blue = new ArrayList<Karte>();
		for (Karte k : karten){
			if (k.getFarbe().equals(Color.BLUE)) {
				blue.add(k);
			}
		}
		return blue;
		
	}
	
	/** Gibt alle gruenen Karten zur�ck.
	 * @return green, ArrayList mit den Karten der Farbe GREEN. 
	 */
	public ArrayList<Karte> getGrueneKarten() {
		ArrayList<Karte> green = new ArrayList<Karte>();
		for (Karte k : karten){
			if (k.getFarbe().equals(Color.GREEN)) {
				green.add(k);
			}
		}
		return green;
		
	}
	
	/** Gibt alle orangenen Karten zur�ck.
	 * @return orange, ArrayList mit den Karten der Farbe ORANGE. 
	 */
	public ArrayList<Karte> getOrangeKarten() {
		ArrayList<Karte> orange = new ArrayList<Karte>();
		for (Karte k : karten){
			if (k.getFarbe().equals(Color.ORANGE)) {
				orange.add(k);
			}
		}
		return orange;
		
	}
	
	/** Gibt alle roten Karten zur�ck.
	 * @return red, ArrayList mit den Karten der Farbe RED. 
	 */
	public ArrayList<Karte> getRoteKarten() {
		ArrayList<Karte> red = new ArrayList<Karte>();
		for (Karte k : karten){
			if (k.getFarbe().equals(Color.RED)) {
				red.add(k);
			}
		}
		return red;
		
	}
}
