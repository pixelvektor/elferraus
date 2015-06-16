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
						.getHighestCard(Color.values()[i]).getNummer();
				if (cardAvailable(Color.values()[i], hiNum, true)) {
					spiel.setMove(Color.values()[i], hiNum + 1);
				}
				int loNum = spiel.getSpielfeld()
						.getLowestCard(Color.values()[i]).getNummer();
				if (cardAvailable(Color.values()[i], loNum, false)) {
					spiel.setMove(Color.values()[i], loNum - 1);
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
			for (Karte c : shortestChainCards) {
				if (!backHand.contains(c)) {
					spiel.setMove(c.getFarbe(), c.getNummer());
				}
			}
		}
		spiel.naechsterSpieler();
	}

	private boolean cardAvailable(final Color color, final int number,
			final boolean high) {
		for (Karte k : getCards()) {
			if (k.getFarbe().equals(color) && k.getNummer() == number + 1
					&& high) {
				return true;
			}
			if (k.getFarbe().equals(color) && k.getNummer() == number - 1
					&& !high) {
				return true;
			}
		}
		return false;
	}

	private ArrayList<Karte> shortestChain(final Spiel spiel) {
		ArrayList<Karte> result = new ArrayList<Karte>();
		ArrayList<Karte> temp = new ArrayList<Karte>();
		Karte tempCard = this.getCards().get(0);
		temp.add(tempCard);
		result = this.getCards();

		for (Karte c : this.getCards()) {
			// Karten in Kette bei Farben und Nummern
			if (tempCard.getFarbe().equals(c.getFarbe())
					&& tempCard.getNummer() + 1 == c.getNummer()) {
				tempCard = c;
				temp.add(tempCard);
			}
			// Zuweisen der kleinsten Reihe bei einem Sprung zwischen Farben
			// oder Nummernketten
			else if (result.size() > temp.size()) {
				// Sofern sich die Kette an die kleinste oder groesste Karte des
				// Spielfeldes anreiht
				if (c.getNummer() < 11
						&& spiel.getSpielfeld().getLowestCard(c.getFarbe())
								.getNummer() == temp.get(temp.size() - 1)
								.getNummer() + 1) {
					result = temp;
				} else if (c.getNummer() > 11
						&& spiel.getSpielfeld().getHighestCard(c.getFarbe())
								.getNummer() == temp.get(temp.size() - 1)
								.getNummer() - 1) {
					result = temp;
				}

			}
		}

		// Saeubern des Ergebnis wenn keine kuerzeste Liste gefunden wurde
		if (result.size() == this.getCards().size()) {
			result = new ArrayList<Karte>();
		}
		
		return result;
	}
}
