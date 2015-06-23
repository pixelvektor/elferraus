package control;

/** Hochschule Hamm-Lippstadt
 * Praktikum Informatik II (ElferRaus)
 * (C) 2015 Lara Sievers, Adrian Schmidt, Fabian Schneider
 * 22.05.2015
 */

import java.util.ArrayList;

import view.View;
import data.Color;
import data.Holder;
import data.Card;
import data.AI;
import data.Player;
import data.Field;
import data.Stack;

public class Game {
	/** Die Schluesselzahl 11. */
	private static final int ELEVEN = 11;
	/** Spieler-ArrayList. */
	private final ArrayList<Holder> player = new ArrayList<Holder>();
	/** Stapel fuer die Karten. */
	private final Stack stack = Stack.getInstance();
	/** Spielfeld. */
	private final Field field = Field.getInstance();
	/** Index des aktiven Spielers. */
	private int activePlayer;
	/** Anzahl der vorhandenen Kis */
	private final int countKi;
	/** Schwierigkeit der KIs. */
	private boolean difficulty;
	/** Name des Gewinners. */
	private String winner = "niemand";
	/** true solange das Spiel laeuft. */
	private boolean isRunning = true;
	/** Haeufigkeit des Aufraufs der Methode setMove(). */
	private int movePerformed;
	/** Haeufigkeit des Aufraufs der Methode pull(). */
	private int pullPerformed;

	/**
	 * Erstellt ein Spiel und updatet die View.
	 * @param view Die TUI des Spiels
	 */
	public Game(final View view) {
		countKi = view.getCountKi();
		difficulty = view.getDifficulty();
		gameInit();
		while (isRunning) {
			view.update(this);
		}
	}

	/**
	 * Getter fuer die Spieler.
	 * @return Gibt die Spieler zurueck.
	 */
	public ArrayList<Holder> getPlayer() {
		return player;
	}

	/**
	 * Getter fuer den Stapel.
	 * @return Gibt den Stapel zurueck.
	 */
	public Stack getStack() {
		return stack;
	}

	/**
	 * Getter fuer das Spielfeld.
	 * @return Gibt das Spielfeld zurueck.
	 */
	public Field getField() {
		return field;
	}

	/**
	 * Getter fuer den Namen des Gewinners.
	 * @return Gibt den Namen des Gewinners zurueck.
	 */
	public String getWinner() {
		return winner;
	}

	/**
	 * Getter fuer den Spielzustand.
	 * @return Gibt den Spielzustand zurueck. True wenn das Spiel laeuft, sonst
	 *         false.
	 */
	public boolean isRunning() {
		return isRunning;
	}

	/**
	 * Beendet das Spiel.
	 */
	public void exit() {
		isRunning = false;
	}

	/**
	 * Wechselt den aktiven Spieler, falls kein Zug mehr moeglich ist, und
	 * startet eine neue Runde.
	 */
	public void nextPlayer() {
		if (movePerformed == 0 && pullPerformed == 0 && checkForPossibleMove()) {
			System.out.println("Sie muessen eine Aktion ausfuehren!");
		} else {
			if (activePlayer < countKi) {
				activePlayer++;
			} else {
				activePlayer = 0;
			}

			movePerformed = 0;
			pullPerformed = 0;
			startRound();
		}
	}

	/**
	 * Der gewuenschte Zug fuer den aktiven Spieler wird gesetzt.
	 * @param color Farbe der zu bewegenden Karte.
	 * @param number Nummer der zu bewegenden Karte.
	 * @return true wenn der Zug durchgefuehrt wurde. Sonst false.
	 */
	public boolean setMove(final Color color, final int number) {
		for (Card k : player.get(activePlayer).getCards()) {
			if (k.getColor().equals(color) && k.getNumber() == number) {
				if (k.getNumber() == ELEVEN) {
					if (move(k, field)) {
						player.get(activePlayer).remove(k);
						movePerformed++;
						checkWinner();
						return true;
					}
				}
				if (!checkElevenOnHand()) {
					if (move(k, field)) {
						player.get(activePlayer).remove(k);
						movePerformed++;
						checkWinner();
						return true;
					}
				} else {
					movePerformed++;
					checkWinner();
					return true;
				}
			}

		}
		return false;
	}

	/**
	 * Der aktive Spieler zieht eine Karte vom Stapel, falls er vorher Keine
	 * gelegt hat oder noch eine Elf auf der Hand hat.
	 * @return true wenn die Karte vom Stapel gezogen wurde, wenn noch eine Elf
	 *         auf der Hand ist, wenn schon gelegt worden ist. Sonst false
	 *         (=leer).
	 */
	public boolean pull() {
		boolean result = true;
		if (movePerformed == 0) {
			if (stack.getCards().size() != 0) {
				if (!checkElevenOnHand()) {
					Card card = stack.getNext();
					if (card.getNumber() == ELEVEN) {
						result = move(card, field);
					} else {
						result = move(card, player.get(activePlayer));
						result = move(stack.getNext(), player.get(activePlayer));
						result = move(stack.getNext(), player.get(activePlayer));
					}
					pullPerformed++;
					nextPlayer();
					return result;
				} else {
					pullPerformed++;
					nextPlayer();
					return true;
				}
			} else {
				return false;
			}
		} else {
			System.out.println("Sie koennen nach einem Zug nicht ziehen!");
			return true;
		}

	}

	/**
	 * Prueft alle Farben, ob alle Karten abgelegt werden koennen.
	 * @return true, wenn alle Karten abgelegt werden koennen, sonst false.
	 */
	public boolean checkForAllIn() {
		if (checkAllIn(Color.BLUE) && checkAllIn(Color.GREEN)
				&& checkAllIn(Color.ORANGE) && checkAllIn(Color.RED)) {
			allIn();
			return true;
		}
		if (activePlayer == 0) {
			System.out.println("Sie koennen nicht alle Karten ablegen!");
		}
		return false;
	}

	/**
	 * Leert die Hand des Spielers.
	 */
	private void allIn() {
		player.get(activePlayer).getCards().clear();
		checkWinner();
	}

	/**
	 * Prueft ob gleichzeitig alle Karte <&>11 gelegt werden koennen.
	 * @param color Farbe die geprueft werden soll.
	 * @return true, wenn alle Karte <&>11 gelegt werden koennen, sonst false.
	 */
	private boolean checkAllIn(final Color color) {
		if (checkAllInLow(color) && checkAllInHigh(color)) {
			return true;
		}
		return false;
	}

	/**
	 * Prueft ob alle Karten auf der Hand (>11) legbar sind.
	 * @param color Farbe der Karten die ueberprueft werden sollen.
	 * @return true, wenn alle Karten >11 abgelegt werden koennen, sonst false
	 */
	private boolean checkAllInHigh(final Color color) {
		int temp;
		int i = 0;
		ArrayList<Card> cards = player.get(activePlayer).getCards(color);
		int max = cards.size() - 1;

		if (max + 1 != 0) {
			while (i <= max) {
				if (cards.get(i).getNumber() == field.getHighestCard(color)
						.getNumber() + 1) {
					temp = i;
					if (i == max) {
						return true;
					} else {
						for (int x = i; x < max; x++) {
							if (cards.get(temp + 1).getNumber() == cards.get(
									temp).getNumber() + 1) {
								temp++;
								i++;
								if (temp == max) {
									return true;
								}
							} else {
								return false;
							}
						}
					}
				} else {
					i++;
				}
			}
			return false;
		} else {
			return true;
		}
	}

	/**
	 * Prueft ob alle Karten auf der Hand (<11) legbar sind.
	 * @param color Farbe der Karten die ueberprueft werden sollen.
	 * @return true, wenn alle Karten <11 abgelegt werden koennen, sonst false
	 */
	private boolean checkAllInLow(final Color color) {
		int temp;
		ArrayList<Card> cards = player.get(activePlayer).getCards(color);
		int i = cards.size() - 1;
		int max = 0;

		if (i + 1 != 0) {
			while (i >= max) {
				if (cards.get(i).getNumber() == field.getLowestCard(color)
						.getNumber() - 1) {
					temp = i;
					if (i == max) {
						return true;
					} else {
						for (int x = i; x > max; x--) {
							if (cards.get(temp - 1).getNumber() == cards.get(
									temp).getNumber() - 1) {
								temp--;
								i--;
								if (temp == max) {
									return true;
								}
							} else {
								return false;
							}
						}
					}
				} else {
					i--;
				}
			}
			return false;
		} else {
			return true;
		}
	}

	/**
	 * Prueft ob der aktive Spieler noch einen Zug ausfuehren kann, oder ob er
	 * noch Ziehen oder Legen kann.
	 * @return true, wenn noch Karten auf dem Stapel sind, oder wenn ein Zug
	 *         moeglich ist, sonst false.
	 */
	private boolean checkForPossibleMove() {
		if (!stack.getCards().isEmpty()) {
			return true;
		}
		if (field.getHighestCard(Color.BLUE) != null) {
			for (Card k : player.get(activePlayer).getCards(Color.BLUE)) {
				if (checkMove(k, field)) {
					return true;
				}
			}
		}
		if (field.getHighestCard(Color.GREEN) != null) {
			for (Card k : player.get(activePlayer).getCards(Color.GREEN)) {
				if (checkMove(k, field)) {
					return true;
				}
			}
		}
		if (field.getHighestCard(Color.ORANGE) != null) {
			for (Card k : player.get(activePlayer).getCards(Color.ORANGE)) {
				if (checkMove(k, field)) {
					return true;
				}
			}
		}
		if (field.getHighestCard(Color.RED) != null) {
			for (Card k : player.get(activePlayer).getCards(Color.RED)) {
				if (checkMove(k, field)) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Prueft ob der aktive Spieler gewonnen hat.
	 */
	private void checkWinner() {
		if (player.get(activePlayer).getCards().size() == 0) {
			winner = player.get(activePlayer).getName();
			exit();
		}
	}

	/**
	 * Prueft ob der aktive Spieler eine Elf auf der Hand hat, wenn ja wird die
	 * Elf automatisch auf das Spielfeld gelegt.
	 * @return true, wenn Spieler eine Elf auf der Hand hat, sonst false.
	 */
	private boolean checkElevenOnHand() {
		for (Card k : player.get(activePlayer).getCards()) {
			if (k.getNumber() == ELEVEN) {
				if (activePlayer == 0) {
					System.out
							.println("Zug nicht moeglich! Elf wird automatisch gelegt!");
				}
				move(k, field);
				player.get(activePlayer).remove(k);
				return true;
			}
		}

		return false;
	}

	/**
	 * Methode um eine Karte einem neuen Holder zu ueberschreiben.
	 * @param card die Karte die verschoben werden soll.
	 * @param target Holder an den die Karte geht.
	 * @return true wenn der Zug erfolgreich ist. Sonst false (Karte null).
	 */
	private boolean move(final Card card, final Holder target) {
		if (card != null) {
			if (checkMove(card, target)) {
				target.add(card);
				return true;
			}
		}
		return false;
	}

	/**
	 * Initialisiert das Spiel.
	 */
	private void gameInit() {
		player.add(new Player("Layer 8"));

		for (int y = 1; y <= countKi; y++) {
			player.add(new AI("KI " + y, difficulty));
		}

		cardInit();
		gameStart();
	}

	/**
	 * Der Stapel wird gemischt, danach werden jeweils 11 Karten an die Spieler
	 * verteilt. Der Spieler mit einer 11 beginnt.
	 */
	private void gameStart() {
		stack.shuffle();

		// Verteilen von je 11 Karten an jeden Spieler
		for (int i = 0; i < ELEVEN; i++) {
			for (int y = 0; y <= countKi; y++) {
				move(stack.getNext(), player.get(y));
			}
		}

		// Sucht den ersten Spieler mit einer 11 raus und setzt ihn als aktiven
		// Spieler
		for (int i = 0; i < ELEVEN; i++) {
			for (int j = 0; j <= countKi; j++) {
				if (player.get(j).getCards().get(i).getNumber() == ELEVEN) {
					activePlayer = j;
					// Beenden der Schleifen bei gefundener 11
					i = ELEVEN + 1;
					j = countKi + 1;
				} else {
					activePlayer = 0;
				}
			}
		}

		startRound();
	}

	/**
	 * Startet eine neue Runde.
	 */
	private void startRound() {
		if (isRunning && activePlayer > 0) {
			player.get(activePlayer).react(this);
		}
	}

	/**
	 * Karten werden erstellt, danach zum Stapel hinzugefuegt.
	 */
	private void cardInit() {
		for (int number = 1; number <= 20; number++) {
			Card card = new Card(Color.BLUE, number);
			stack.add(card);
		}
		for (int number = 1; number <= 20; number++) {
			Card card = new Card(Color.GREEN, number);
			stack.add(card);
		}
		for (int number = 1; number <= 20; number++) {
			Card card = new Card(Color.ORANGE, number);
			stack.add(card);
		}
		for (int number = 1; number <= 20; number++) {
			Card card = new Card(Color.RED, number);
			stack.add(card);
		}
	}

	/**
	 * Prueft ob der aktuelle Zug gueltig ist.
	 * @param card die Karte die verschoben werden soll.
	 * @param target Holder an den die Karte geht.
	 * @return true wenn ziel kein Spielfeld ist, Farbe und Nummer stimmen.
	 *         Sonst false.
	 */
	private boolean checkMove(final Card card, final Holder target) {
		if (card.getNumber() == ELEVEN)
			return true;

		boolean result = false;

		if (target.equals(field)) {
			for (int i = 0; i < Color.values().length; i++) {
				if (card.getColor().equals(Color.values()[i])) {
					if (card.getNumber() == target.getHighestCard(
							Color.values()[i]).getNumber() + 1) {
						result = true;
					} else if (card.getNumber() == target.getLowestCard(
							Color.values()[i]).getNumber() - 1) {
						result = true;
					}
				}
			}
		} else {
			result = true;
		}

		return result;
	}
}