package data;

/** Hochschule Hamm-Lippstadt
 * Praktikum Informatik II (ElferRaus)
 * (C) 2015 Lara Sievers, Adrian Schmidt, Fabian Schneider
 * 22.05.2015
 */

import java.util.ArrayList;

import control.Game;

public class AI extends Player {
	/** Schwierigkeit der KI. */
	private final boolean difficult;

	/**
	 * Erstellt eine KI.
	 * @param name Name der KI.
	 * @param difficult Schwierigkeit der KI.
	 */
	public AI(final String name, final boolean difficult) {
		setName(name);
		this.difficult = difficult;
	}

	/**
	 * Reaktion der KI.
	 * @param spiel Das aktuelle Spiel.
	 */
	public void react(final Game spiel) {
		if (!spiel.getStack().getCards().isEmpty()) {
			spiel.pull();
		} else if (!difficult) {
			easy(spiel);
		} else {
			hard(spiel);
		}
	}

	/**
	 * Die KI als leichter Gegner.
	 * @param game Das Spiel.
	 */
	private void easy(final Game game) {
		for (int i = 0; i < Color.values().length; i++) {
			// Fuer Jede Farbe wird geprueft ob auf der Hand eine Karte groesser
			// oder kleiner als die auf dem Spielfeld vorhanden ist. Diese wird
			// dann gelegt.
			if (game.getField().getHighestCard(Color.values()[i]) != null) {
				int hiNum = game.getField().getHighestCard(Color.values()[i])
						.getNumber() + 1;
				if (cardAvailable(Color.values()[i], hiNum)) {
					game.setMove(Color.values()[i], hiNum);
				}
				int loNum = game.getField().getLowestCard(Color.values()[i])
						.getNumber() - 1;
				if (cardAvailable(Color.values()[i], loNum)) {
					game.setMove(Color.values()[i], loNum);
				}
			}
		}
		game.nextPlayer();
	}

	/**
	 * Die KI als schwerer Gegner.
	 * @param game Das Spiel.
	 */
	private void hard(final Game game) {
		if (!game.checkForAllIn()) {
			ArrayList<Card> shortestChainCards = shortestChain(game);
			// Legen der kuerzesten Kette sofern eine Karte nicht auf der
			// Backhand liegt
			for (Card c : shortestChainCards) {
				game.setMove(c.getColor(), c.getNumber());
			}
		}
		game.nextPlayer();
	}

	/**
	 * Prueft ob eine bestimmte Karte auf der Hand verfuegbar ist.
	 * @param color Die Farbe der gesuchten Karte.
	 * @param number Die Nummer der gesuchten Karte.
	 * @return true wenn die Karte vorhanden ist, sonst false.
	 */
	private boolean cardAvailable(final Color color, final int number) {
		for (Card k : getCards()) {
			// Pruefen auf alle Suchparameter
			if (k.getColor().equals(color) && k.getNumber() == number) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Gibt die kuerzeste Kartenkette zurueck die auf der Hand liegt und auf dem
	 * Spielfeld gelegt werden kann.
	 * @param game Das Spiel.
	 * @return ArrayList mit den Karten der kuersesten Kette.
	 */
	private ArrayList<Card> shortestChain(final Game game) {
		ArrayList<Card> result = new ArrayList<Card>();
		ArrayList<Card> tempLo = new ArrayList<Card>();
		ArrayList<Card> tempHi = new ArrayList<Card>();
		Card tempCard;
		result.addAll(this.getCards());

		for (int i = 0; i < Color.values().length; i++) {
			Color c = Color.values()[i];

			Card hiCard = game.getField().getHighestCard(c);
			if (hiCard != null) {
				// Suche nach allen Karten < 11 die an das Spielfeld passen
				if (cardAvailable(c, game.getField().getHighestCard(c)
						.getNumber() + 1)) {
					// Rueckwaerts zusamenfuegen der Karten solange keine
					// Unterbrechung der Kette vorhanden ist
					int j = getCards().indexOf(
							getCard(c, game.getField().getHighestCard(c)
									.getNumber() + 1));
					do {
						tempCard = getCards().get(j++);
						tempHi.add(tempCard);
					} while (cardAvailable(c, tempCard.getNumber() + 1));
				}
			}

			Card loCard = game.getField().getLowestCard(c);
			if (loCard != null) {
				// Suche nach allen Karten > 11 die an das Spielfeld passen
				if (cardAvailable(c, game.getField().getLowestCard(c)
						.getNumber() - 1)) {
					// Vorwaerts zusamenfuegen der Karten solange keine
					// Unterbrechung der Kette vorhanden ist
					int j = getCards().indexOf(
							getCard(c, game.getField().getLowestCard(c)
									.getNumber() - 1));
					do {
						tempCard = getCards().get(j--);
						tempLo.add(tempCard);
					} while (cardAvailable(c, tempCard.getNumber() - 1));
				}
			}

			// Wenn keine HiKarten vorhanden sind
			int tempHiSize = tempHi.size() == 0 ? tempLo.size() + 1 : tempHi
					.size();
			// Zuweisen des kleinsten Stapels
			if (tempLo.size() > 0 && tempHiSize > tempLo.size()
					&& result.size() > tempLo.size()) {
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
			result = new ArrayList<Card>();
		}

		return result;
	}
}
