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
	/** Name des Holders. */
    private String name;
    
    /** Karten im Besitz des Holders. */
	private final ArrayList<Karte> karten = new ArrayList<Karte>();
    
	/** Getter fuer den Namen des Holders
	 * @return name Name des Holders
	 */
	public String getName() {
		return name;
	}
    
	/** Setter fuer den Namen
	 * @param name Zukuenftiger Name des Holders
	 */
	public void setName(final String name) {
		this.name = name;
	}
	
	/** Reaktion des Holders.
	 * @param spiel Das aktuelle Spiel.
	 */
	public void react(final Spiel spiel){
	}

	/** Gibt die kleinste Karte unterhalb von 12 der gesuchten Farbe zurueck.
	 * @param color Die Farbe (BLUE, GREEN, ORANGE, RED) fuer welche die kleinste Karte gesucht wird.
	 * @return Die kleinste Karte der Farbe, sofern kleiner gleich 11. Sonst null.
	 */
	public Karte getLowestCard(final Color color) {
		Karte lowestCard = null;
		
		for (Karte k : karten) {
			if (k.getFarbe().equals(color) && k.getNummer() < 12) {
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
	
	/** Gibt die kleinste Karte unterhalb von 12 der gesuchten Farbe zurueck.
	 * @param cards Die ArrayList in der die kleinste Karte gesucht werden soll.
	 * @param color Die Farbe (BLUE, GREEN, ORANGE, RED) fuer welche die kleinste Karte gesucht wird.
	 * @return Die kleinste Karte der Farbe, sofern kleiner gleich 11. Sonst null.
	 */
	public Karte getLowestCard(final ArrayList<Karte> cards, final Color color) {
		Karte lowestCard = null;
		
		for (Karte k : cards) {
			if (k.getFarbe().equals(color) && k.getNummer() < 12) {
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
	 * @param color Die Farbe (BLUE, GREEN, ORANGE, RED) fuer welche die hoechste Karte gesucht wird.
	 * @return Die hoechste Karte der Farbe, sofern groesser gleich 11. Sonst null.
	 */
	public Karte getHighestCard(final Color color) {
		Karte highestCard = null;
		
		for (Karte k : karten) {
			if (k.getFarbe().equals(color) && k.getNummer() > 10) {
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
	
	/** Gibt die hoechste Karte oberhalb von 10 der gesuchten Farbe zurueck.
	 * @param cards Die ArrayList in der die hoechste Karte gesucht werden soll.
	 * @param color Die Farbe (BLUE, GREEN, ORANGE, RED) fuer welche die hoechste Karte gesucht wird.
	 * @return Die hoechste Karte der Farbe, sofern groesser gleich 11. Sonst null.
	 */
	public Karte getHighestCard(final ArrayList<Karte> cards, final Color color) {
		Karte highestCard = null;
		
		for (Karte k : cards) {
			if (k.getFarbe().equals(color) && k.getNummer() > 10) {
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
	public ArrayList<Karte> getCards() {
		return karten;
	}
	
	/** Gibt alle Karten einer bestimmten Farbe zurueck.
	 * @return ArrayList mit den Karten der gesuchten Farbe. 
	 */
	public ArrayList<Karte> getCards(final Color color) {
		ArrayList<Karte> searchedColor = new ArrayList<Karte>();
		for (Karte k : karten){
			if (k.getFarbe().equals(color)) {
				searchedColor.add(k);
			}
		}
		return searchedColor;
	}
    
	/** Fuegt eine neue Karte hinzu.
	 * @param karte karte die hinzugefuegt werden soll.
	 */
	public void add(final Karte karte) {
		karten.add(karte);
		sort(karten);
	}
	
    /** Entfernt eine Karte.
     * @param card Karte die entfernt werden soll. 
     */
	public void remove(final Karte card) {
		karten.remove(card);
	}
	
    /** Sortiert die Karten nach Farbe.
     */
	protected void sort(final ArrayList<Karte> cards) {
		ArrayList<Karte> blue = new ArrayList<Karte>();
		ArrayList<Karte> green = new ArrayList<Karte>();
		ArrayList<Karte> orange = new ArrayList<Karte>();
		ArrayList<Karte> red = new ArrayList<Karte>();
		
		// Aufsplitten der Farben im Stapel
		for (Karte k : cards) {
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
		
		cards.clear();
		
		// Zusammenfuegen der Farben in einen Stapel
		for (Karte k : blue) {
			cards.add(k);
		}
		for (Karte k : green) {
			cards.add(k);
		}
		for (Karte k : orange) {
			cards.add(k);
		}
		for (Karte k : red) {
			cards.add(k);
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
