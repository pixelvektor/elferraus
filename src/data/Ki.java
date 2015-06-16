package data;

/** Hochschule Hamm-Lippstadt
 * Praktikum Informatik II (ElferRaus)
 * (C) 2015 Lara Sievers, Adrian Schmidt, Fabian Schneider
 * 22.05.2015
 */

import java.util.ArrayList;

import control.Spiel;

public class Ki extends Spieler {
	/** Hoechste Karten, die zurueck gehalten werden. */
	private ArrayList<Karte> backHand = new ArrayList<Karte>();
	/** Schwierigkeit der KI. */
	private final boolean difficult;

	/**
	 * Erstellt eine KI.
	 * @param name Name der KI.
	 * @param difficult Schwierigkeit der KI.
	 */
	public Ki(final String name, final boolean difficult) {
		setName(name);
		this.difficult = difficult;
	}

	/**
	 * Reaktion der KI.
	 * @param spiel Das aktuelle Spiel.
	 */
	public void react(final Spiel spiel) {
		if (difficult) {
			checkBackHand();
		}

		if (!spiel.getStapel().getCards().isEmpty()) {
			spiel.pull();
		} else if (!difficult) {
			easy(spiel);
		} else {
			hard(spiel);
		}
	}

	/**
	 * Prueft ob hohe Karten vorhanden sind und legt diese in die Hinterhand
	 * zurueck.
	 */
	private void checkBackHand() {
		for (int i = 0; i < Color.values().length; i++) {
			Karte hiCardYet = getHighestCard(backHand, Color.values()[i]);
			Karte loCardYet = getLowestCard(backHand, Color.values()[i]);
			Karte hiCard = this.getHighestCard(Color.values()[i]);
			Karte loCard = this.getLowestCard(Color.values()[i]);

			if (hiCard != null && hiCardYet != null) {
				if (hiCard.getNummer() < 20 && !backHand.contains(hiCard)) {
					if (hiCardYet.getNummer() < hiCard.getNummer()) {
						backHand.remove(hiCardYet);
					}
					backHand.add(hiCard);
				}
			}
			if (loCard != null && loCardYet != null) {
				if (loCard.getNummer() > 1 && !backHand.contains(loCard)) {
					if (loCardYet.getNummer() > loCard.getNummer()) {
						backHand.remove(loCardYet);
					}
					backHand.add(loCard);
				}
			}
		}
		sort(backHand);
	}

	/**
	 * Die KI als leichter Gegner.
	 * @param spiel Das Spiel.
	 */
	private void easy(final Spiel spiel) {
		for (int i = 0; i < Color.values().length; i++) {
			// Fuer Jede Farbe wird geprueft ob auf der Hand eine Karte groesser
			// oder kleiner als die auf dem Spielfeld vorhanden ist. Diese wird
			// dann gelegt.
			if (spiel.getSpielfeld().getHighestCard(Color.values()[i]) != null) {
				int hiNum = spiel.getSpielfeld()
						.getHighestCard(Color.values()[i]).getNummer() + 1;
				if (cardAvailable(Color.values()[i], hiNum)) {
					spiel.setMove(Color.values()[i], hiNum);
				}
				int loNum = spiel.getSpielfeld()
						.getLowestCard(Color.values()[i]).getNummer() - 1;
				if (cardAvailable(Color.values()[i], loNum)) {
					spiel.setMove(Color.values()[i], loNum);
				}
			}
		}
		spiel.naechsterSpieler();
	}

	/**
	 * Die KI als schwerer Gegner.
	 * @param spiel Das Spiel.
	 */
	private void hard(final Spiel spiel) {
		if (!spiel.checkForAllIn()) {
			ArrayList<Karte> shortestChainCards = shortestChain(spiel);
			// Legen der kuerzesten Kette sofern eine Karte nicht auf der
			// Backhand liegt
			for (Karte c : shortestChainCards) {
				if (!backHand.contains(c)) {
					spiel.setMove(c.getFarbe(), c.getNummer());
				}
			}
		}
		spiel.naechsterSpieler();
	}

	/**
	 * Prueft ob eine bestimmte Karte auf der Hand verfuegbar ist.
	 * @param color Die Farbe der gesuchten Karte.
	 * @param number Die Nummer der gesuchten Karte.
	 * @return true wenn die Karte vorhanden ist, sonst false.
	 */
	private boolean cardAvailable(final Color color, final int number) {
		for (Karte k : getCards()) {
			// Pruefen auf alle Suchparameter
			if (k.getFarbe().equals(color) && k.getNummer() == number) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Gibt die kuerzeste Kartenkette zurueck die auf der Hand liegt und auf dem
	 * Spielfeld gelegt werden kann.
	 * @param spiel Das Spiel.
	 * @return ArrayList mit den Karten der kuersesten Kette.
	 */
	private ArrayList<Karte> shortestChain(final Spiel spiel) {
		ArrayList<Karte> result = new ArrayList<Karte>();
		ArrayList<Karte> tempLo = new ArrayList<Karte>();
		ArrayList<Karte> tempHi = new ArrayList<Karte>();
		Karte tempCard;
		result.addAll(this.getCards());

		for (int i = 0; i < Color.values().length; i++) {
			Color c = Color.values()[i];
			
			// Suche nach allen Karten < 11 die an das Spielfeld passen
			if (cardAvailable(c, spiel.getSpielfeld().getHighestCard(c)
					.getNummer() + 1)) {
				// Rueckwaerts zusamenfuegen der Karten solange keine
				// Unterbrechung der Kette vorhanden ist
				int j = getCards().indexOf(
						getCard(c, spiel.getSpielfeld().getHighestCard(c)
								.getNummer() + 1));
				do {
					tempCard = getCards().get(j++);
					tempHi.add(tempCard);
				} while (cardAvailable(c, tempCard.getNummer() + 1));
			}
			
			// Suche nach allen Karten > 11 die an das Spielfeld passen
			if (cardAvailable(c, spiel.getSpielfeld().getLowestCard(c)
					.getNummer() - 1)) {
				// Vorwaerts zusamenfuegen der Karten solange keine
				// Unterbrechung der Kette vorhanden ist
				int j = getCards().indexOf(
						getCard(c, spiel.getSpielfeld().getLowestCard(c)
								.getNummer() - 1));
				do {
					tempCard = getCards().get(j--);
					tempLo.add(tempCard);
				} while (cardAvailable(c, tempCard.getNummer() - 1));
			}
			
			// Wenn keine HiKarten vorhanden sind
			int tempHiSize = tempHi.size() == 0 ? tempLo.size() + 1 : tempHi.size();
			// Zuweisen des kleinsten Stapels
			if (tempLo.size() > 0 && tempHiSize > tempLo.size() && result.size() > tempLo.size()) {
				result.clear();
				result.addAll(tempLo);
			} else if (tempHi.size() > 0 && result.size() > tempHi.size()) {
				result.clear();
				result.addAll(tempHi);
			}
			
			tempHi.clear();
			tempLo.clear();
		}

		// Leeren des Ergebnisses wenn keine Kette gefunden wurde
		if (result.size() == getCards().size()) {
			result = new ArrayList<Karte>();
		}

		return result;
	}
}
