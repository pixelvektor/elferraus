package data;

import java.util.ArrayList;

import control.Game;

/** Hochschule Hamm-Lippstadt
 * Praktikum Informatik II (ElferRaus)
 * (C) 2015 Lara Sievers, Adrian Schmidt, Fabian Schneider
 * 22.05.2015
 */

public abstract class Holder {
	/** Name des Holders. */
	private String name;

	/** Karten im Besitz des Holders. */
	private final ArrayList<Card> cards = new ArrayList<Card>();

	/**
	 * Getter fuer den Namen des Holders
	 * @return name Name des Holders
	 */
	public String getName() {
		return name;
	}

	/**
	 * Setter fuer den Namen
	 * @param name Zukuenftiger Name des Holders
	 */
	public void setName(final String name) {
		this.name = name;
	}

	/**
	 * Reaktion des Holders.
	 * @param spiel Das aktuelle Spiel.
	 */
	public void react(final Game spiel) {
	}

	/**
	 * Gibt die kleinste Karte unterhalb von 12 der gesuchten Farbe zurueck.
	 * @param color Die Farbe (BLUE, GREEN, ORANGE, RED) fuer welche die
	 *            kleinste Karte gesucht wird.
	 * @return Die kleinste Karte der Farbe, sofern kleiner gleich 11. Sonst
	 *         null.
	 */
	public Card getLowestCard(final Color color) {
		Card lowestCard = null;

		for (Card k : cards) {
			if (k.getColor().equals(color) && k.getNumber() < 12) {
				// Sofern noch keine Karte gesetzt wurde passt der erste Treffer
				// immer
				if (lowestCard == null) {
					lowestCard = k;
				}
				// Sofern eine kleinere Karte gefunden ist, wird sie als
				// Kleinste gesetzt
				else if (lowestCard.getNumber() > k.getNumber()) {
					lowestCard = k;
				}
			}
		}

		return lowestCard;
	}

	/**
	 * Gibt die kleinste Karte unterhalb von 12 der gesuchten Farbe zurueck.
	 * @param cards Die ArrayList in der die kleinste Karte gesucht werden soll.
	 * @param color Die Farbe (BLUE, GREEN, ORANGE, RED) fuer welche die
	 *            kleinste Karte gesucht wird.
	 * @return Die kleinste Karte der Farbe, sofern kleiner gleich 11. Sonst
	 *         null.
	 */
	public Card getLowestCard(final ArrayList<Card> cards, final Color color) {
		Card lowestCard = null;

		for (Card k : cards) {
			if (k.getColor().equals(color) && k.getNumber() < 12) {
				// Sofern noch keine Karte gesetzt wurde passt der erste Treffer
				// immer
				if (lowestCard == null) {
					lowestCard = k;
				}
				// Sofern eine kleinere Karte gefunden ist, wird sie als
				// Kleinste gesetzt
				else if (lowestCard.getNumber() > k.getNumber()) {
					lowestCard = k;
				}
			}
		}

		return lowestCard;
	}

	/**
	 * Gibt die hoechste Karte oberhalb von 10 der gesuchten Farbe zurueck.
	 * @param color Die Farbe (BLUE, GREEN, ORANGE, RED) fuer welche die
	 *            hoechste Karte gesucht wird.
	 * @return Die hoechste Karte der Farbe, sofern groesser gleich 11. Sonst
	 *         null.
	 */
	public Card getHighestCard(final Color color) {
		Card highestCard = null;

		for (Card k : cards) {
			if (k.getColor().equals(color) && k.getNumber() > 10) {
				// Sofern noch keine Karte gesetzt wurde passt der erste Treffer
				// immer
				if (highestCard == null) {
					highestCard = k;
				}
				// Sofern eine hoehere Karte gefunden ist, wird sie als Hoechste
				// gesetzt
				else if (highestCard.getNumber() < k.getNumber()) {
					highestCard = k;
				}
			}
		}

		return highestCard;
	}

	/**
	 * Gibt die hoechste Karte oberhalb von 10 der gesuchten Farbe zurueck.
	 * @param cards Die ArrayList in der die hoechste Karte gesucht werden soll.
	 * @param color Die Farbe (BLUE, GREEN, ORANGE, RED) fuer welche die
	 *            hoechste Karte gesucht wird.
	 * @return Die hoechste Karte der Farbe, sofern groesser gleich 11. Sonst
	 *         null.
	 */
	public Card getHighestCard(final ArrayList<Card> cards, final Color color) {
		Card highestCard = null;

		for (Card k : cards) {
			if (k.getColor().equals(color) && k.getNumber() > 10) {
				// Sofern noch keine Karte gesetzt wurde passt der erste Treffer
				// immer
				if (highestCard == null) {
					highestCard = k;
				}
				// Sofern eine hoehere Karte gefunden ist, wird sie als Hoechste
				// gesetzt
				else if (highestCard.getNumber() < k.getNumber()) {
					highestCard = k;
				}
			}
		}

		return highestCard;
	}

	/**
	 * Getter fuer eine bestimmte Karte.
	 * @param color Farbe der gesuchten Karte.
	 * @param number Nummer der gesuchten Karte.
	 * @return Die Karte zu den gesuchten Parametern, sonst null.
	 */
	public Card getCard(final Color color, final int number) {
		for (Card k : getCards()) {
			// Pruefen auf alle Suchparameter
			if (k.getColor().equals(color) && k.getNumber() == number) {
				return k;
			}
		}
		return null;
	}

	/**
	 * Getter fuer die Karten des Holders.
	 * @return Gibt die Karten des Holders zurueck.
	 */
	public ArrayList<Card> getCards() {
		return cards;
	}

	/**
	 * Gibt alle Karten einer bestimmten Farbe zurueck.
	 * @return ArrayList mit den Karten der gesuchten Farbe.
	 */
	public ArrayList<Card> getCards(final Color color) {
		ArrayList<Card> searchedColor = new ArrayList<Card>();
		for (Card k : cards) {
			if (k.getColor().equals(color)) {
				searchedColor.add(k);
			}
		}
		return searchedColor;
	}

	/**
	 * Fuegt eine neue Karte hinzu.
	 * @param card karte die hinzugefuegt werden soll.
	 */
	public void add(final Card card) {
		cards.add(card);
		sort(cards);
	}

	/**
	 * Entfernt eine Karte.
	 * @param card Karte die entfernt werden soll.
	 */
	public void remove(final Card card) {
		cards.remove(card);
	}

	/**
	 * Sortiert die Karten nach Farbe.
	 */
	protected void sort(final ArrayList<Card> cards) {
		ArrayList<Card> blue = new ArrayList<Card>();
		ArrayList<Card> green = new ArrayList<Card>();
		ArrayList<Card> orange = new ArrayList<Card>();
		ArrayList<Card> red = new ArrayList<Card>();

		// Aufsplitten der Farben im Stapel
		for (Card k : cards) {
			if (k.getColor().equals(Color.BLUE)) {
				blue.add(k);
			} else if (k.getColor().equals(Color.GREEN)) {
				green.add(k);
			} else if (k.getColor().equals(Color.ORANGE)) {
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
		for (Card k : blue) {
			cards.add(k);
		}
		for (Card k : green) {
			cards.add(k);
		}
		for (Card k : orange) {
			cards.add(k);
		}
		for (Card k : red) {
			cards.add(k);
		}
	}
	
	/**
	 * Sortiert die Nummern einer Kartenfarbe aufsteigend.
	 * @param cards ArrayList der zu sortierenden Kartenfarbe.
	 */
	private void sortNumber(final ArrayList<Card> cards) {
		for (int i = 0; i < cards.size(); i++) {
			Card temp = cards.get(i);
			int j = i;
			while (j > 0 && cards.get(j - 1).getNumber() > temp.getNumber()) {
				cards.add(j, cards.get(j - 1));
				cards.remove(j + 1);
				j--;
			}
			cards.add(j, temp);
			cards.remove(j + 1);
		}
	}
}
