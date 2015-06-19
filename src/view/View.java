/** Hochschule Hamm-Lippstadt
 * Praktikum Informatik II (ElferRaus)
 * (C) 2015 Lara Sievers, Adrian Schmidt, Fabian Schneider
 * 22.05.2015
 */

package view;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import control.Spiel;
import data.Color;
import data.Karte;

/**
 * @author fabian
 */
public class View implements ViewInterface {
	/** Hilfetext. */
	private static final String HELP_MESSAGE = "\r\n"
			+ "Um eine Karte zu ziehen geben Sie 'pull' ein.\r\n"
			+ "Um eine Karte zu legen geben Sie 'put', den Farbbuchstaben und die Nummer ein. Bsp: put R9.\r\n"
			+ "Mit 'next' kann der Zug beendet werden."
			+ "Das Kommando 'allin' legt alle Karten sofern sie gelegt werden koennen (gegen Spielende)."
			+ "Diese Hilfe kann mit 'help' angezeigt werden.\r\n"
			+ "Die Regeln erhalten Sie ueber den Befehl 'rules'.\r\n"
			+ "Und wenn Sie dann doch keine Lust mehr haben sagen Sie 'bye'.";
	/** Regeltext. */
	private static final String RULES = "\r\n"
			+ "Spielregeln:\r\n"
			+ "Ziel des Spiels ist es alle Karten abzulegen.\r\n"
			+ "Jeder Spieler erhaelt 11 Karten. Es gibt 4 Farben mit jeweils 20 Karten, die von 1-20 nummeriert sind.\r\n"
			+ "Karten duerfen nur auf Karten mit der gleichen Farbe auf einen Stapel des Spielfelds gelegt werden.\r\n"
			+ "Bei jeder Farbe wird jeweils von 11 bis 1 ab- bzw. von 12 bis 20 aufwaerts gelegt\r\n"
			+ "Der Spieler mit einer 11 faengt an.\r\n"
			+ "Elfen muessen immer zuerst gelegt werden.\r\n"
			+ "Es ist nicht verpflichtend die anderen Karten zu legen, solange noch Karten auf dem Stapel sind.\r\n"
			+ "Es koennen so viele Karten gelegt werden wie man moechte, jedoch mindestens Eine sofern man kann.\r\n"
			+ "Es wird entweder gezogen oder gelegt, danach ist der naechste Spieler dran.";
	/** Das Spiel. */
	private Spiel spiel;
	/** Anzahl der KIs. */
	private int countKi;
	/** Schwierigkeit der KIs. */
	private boolean difficulty;

	/**
	 * Erstellt eine neue View fuer das Spiel. Muss an Spiel uebergeben werden.
	 */
	public View() {
		System.out.println("ElferRaus");
		System.out.println(HELP_MESSAGE);
		System.out.println();

		do {
			try {
				countKi = Integer.parseInt(input("Anzahl der Gegner [1-3]: "));
			} catch (NumberFormatException e) {
			}
		} while (countKi < 1 || countKi > 3);

		boolean result = false;
		do {
			String input = input("Leicht oder schwer [easy / hard]: ")
					.toLowerCase();
			if (input.equals("easy")) {
				difficulty = false;
				result = true;
			} else if (input.equals("hard")) {
				difficulty = true;
				result = true;
			}
		} while (!result);
	}

	/**
	 * Getter fuer die Anzahl der KIs.
	 * @return Gibt die Anzahl der KIs zurueck.
	 */
	public int getCountKi() {
		return countKi;
	}

	/**
	 * Getter fuer die Schwierigkeit.
	 * @return Gibt die Schwierigkeit zurueck.
	 */
	public boolean getDifficulty() {
		return difficulty;
	}

	/**
	 * Aktualisiert das Spiel auf der Kommandozeile und wartet auf neue Befehle.
	 */
	@Override
	public void update(final Spiel spiel) {
		this.spiel = spiel;

		boolean result = true;
		do {
			System.out.println();
			for (int i = 1; i < spiel.getSpieler().size(); i++) {
				System.out.println("Karten von "
						+ spiel.getSpieler().get(i).getName() + ": "
						+ spiel.getSpieler().get(i).getCards().size() + ".");
			}

			System.out.println();
			printSpielfeld();
			System.out.println();

			System.out.println("Karten auf dem Stapel: "
					+ spiel.getStapel().getCards().size());
			System.out.println();
			System.out.println("Ihre Karten ("
					+ spiel.getSpieler().get(0).getCards().size() + "):");
			printSpielerKarten(spiel.getSpieler().get(0).getCards());

			System.out.println();
			String[] input = input("Ihr Zug: ").split(" ");
			switch (input[0]) {
			case "help":
				System.out.println(HELP_MESSAGE);
				update(spiel);
				break;
			case "rules":
				System.out.println(RULES);
				update(spiel);
				break;
			case "pull":
				if (!spiel.pull()) {
					System.out.println("Keine Karten mehr auf dem Stapel.");
				}
				break;
			case "put":
				try {
					result = move(input[1]);
				} catch (Exception e) {
					System.out.println("Bitte geben Sie eine Karte an.");
				}
				break;
			case "next":
				System.out.println("Bitte Warten.");
				spiel.naechsterSpieler();
				result = true;
				break;
			case "allin":
				spiel.checkForAllIn();
				break;
			case "bye":
			case "exit":
				spiel.exit();
				break;
			default:
				System.out.println("Ich verstehe die Eingabe nicht.");
				update(spiel);
				break;
			}
		} while (!result);

		if (!spiel.isRunning()) {
			System.out.println("Gewonnen hat: " + spiel.getWinner());
		}
	}

	/**
	 * Gibt eine Nachricht auf der Kommandozeile aus und liesst eine Eingabe.
	 * @param message Nachricht, welche ausgegeben werden soll.
	 * @return String mit dem eingegebenen Text.
	 */
	private String input(final String message) {
		System.out.print(message);
		String input = "";
		try {
			input = new BufferedReader(new InputStreamReader(System.in))
					.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return input.toLowerCase();
	}

	private void printSpielfeld() {
		System.out.println("Spielfeld:");
		int[] blue = getNumbers(Color.BLUE);
		int[] orange = getNumbers(Color.ORANGE);
		int[] green = getNumbers(Color.GREEN);
		int[] red = getNumbers(Color.RED);

		System.out.print("Blau   " + blue[0] + "|" + blue[1]);
		System.out.println();
		System.out.print("Gruen  " + green[0] + "|" + green[1]);
		System.out.println();
		System.out.print("Orange " + orange[0] + "|" + orange[1]);
		System.out.println();
		System.out.print("Rot    " + red[0] + "|" + red[1]);
		System.out.println();
	}

	/**
	 * Gibt die Nummern der Karten auf dem Spielfeld fuer eine Farbe zurueck.
	 * @param color Die Farbe der Karten fuer die die Nummern gesucht sind.
	 * @return Ein int Array mit der niedrigsten, der elf und der hoechsten
	 *         Nummer pro Farbe auf dem Spielfeld.
	 */
	private int[] getNumbers(final Color color) {
		int[] result = new int[2];
		// Wert der niedrigsten Karte
		try {
			result[0] = spiel.getSpielfeld().getLowestCard(color).getNumber();
		} catch (NullPointerException e) {
			result[0] = 0;
		}

		// Wert der hoechsten Karte
		try {
			result[1] = spiel.getSpielfeld().getHighestCard(color).getNumber();
		} catch (NullPointerException e) {
			result[1] = 0;
		}

		return result;
	}

	private void printSpielerKarten(final ArrayList<Karte> karten) {
		String output = "";
		if (karten.size() > 0) {
			Karte temp = karten.get(0);
			String[] farbe = { "B", "G", "O", "R" };

			boolean firstRound = true;
			for (Karte k : karten) {
				if (firstRound) {
					for (int i = 0; i < farbe.length; i++) {
						if (k.getFarbe().equals(Color.values()[i])) {
							output += farbe[i];
						}
					}
					firstRound = false;
				}
				if (!temp.getFarbe().equals(k.getFarbe())) {
					for (int i = 0; i < farbe.length; i++) {
						if (k.getFarbe().equals(Color.values()[i])) {
							output += "\r\n" + farbe[i];
						}
					}
				}
				output += " " + k.getNumber();
				temp = k;
			}
		}

		System.out.println(output);
	}

	/**
	 * Prueft den gewuenschsten Zug vor und laesst ihn ausfuehren.
	 * @param card Kartencode fuer die Karte die bewegt werden soll. Bsp: r9
	 * @return true wenn der Zug durchgefuehrt wurde. Sonst false.
	 */
	private boolean move(final String card) {
		String colorCode = card.substring(0, 1);
		int number;

		// Konvertieren der Nummer
		try {
			number = Integer.parseInt(card.substring(1));
		} catch (NumberFormatException e) {
			System.out.println("Keine gueltige Zahl eingegeben.");
			return false;
		}

		// Check ob Nummer im gueltigen Bereich liegt
		if (number < 1 && number > 20) {
			System.out.println("Zahl nicht im gueltigen Wertebereich.");
			return false;
		}

		// Zuweisen der Farbe passend zum Code
		Color color = null;

		switch (colorCode) {
		case "r":
			color = Color.RED;
			break;
		case "g":
			color = Color.GREEN;
			break;
		case "b":
			color = Color.BLUE;
			break;
		case "o":
			color = Color.ORANGE;
			break;

		default:
			System.out
					.println("Falscher Farbcode. Moeglich sind: Rot, Gruen, Blau, Orange");
			return false;
		}

		// Ausfuehren des Zugs
		if (spiel.setMove(color, number)) {
			return true;
		} else {
			System.out.println("Zug nicht moeglich!");
			return false;
		}
	}
}
