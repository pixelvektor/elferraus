/** Hochschule Hamm-Lippstadt
 * Praktikum Informatik II (ElferRaus)
 * (C) 2015 Lara Sievers, Adrian Schmidt, Fabian Schneider
 * 22.05.2015
 */

package view;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import control.Spiel;

/**
 * @author fabian
 *
 */
public class View implements ViewInterface {
	private static final String HELP_MESSAGE = "\r\n"
			+ "Um eine Karte zu ziehen geben Sie 'pull' ein.\r\n"
			+ "Um eine Karte zu legen geben Sie 'put', den Farbbuchstaben und die Nummer ein. Bsp: put R9.\r\n"
			+ "Diese Hilfe kann mit 'help' angezeigt werden.\r\n"
			+ "Die Regeln erhalten Sie ueber den Befehl 'rules'.\r\n"
			+ "Und wenn Sie dann doch keine Lust mehr haben sagen Sie 'bye'.\r\n";
	private static final String RULES = "Spielregeln:\r\n";
	private Spiel spiel;

	/** Erstellt eine neue View fuer das Spiel.
	 * Muss an Spiel uebergeben werden.
	 */
	public View() {
		System.out.println("ElferRaus");
		System.out.println(HELP_MESSAGE);
		
		int countKi = 0;
		int difficulty = 0;
		
		do {
			try {
				countKi = Integer.parseInt(input("Anzahl Gegner [1-3]: "));
			} catch (NumberFormatException e) {
			}
		} while (countKi < 1 || countKi > 3);
		
		do {
			try {
				difficulty = Integer.parseInt(input("Schwierigkeitsgrad [1-3]:  "));
			} catch (NumberFormatException e) {
			}
		} while (difficulty < 1 || difficulty > 3);
		
	}
	
	/** Aktualisiert das Spiel auf der Kommandozeile und wartet auf neue Befehle.
	 */
	@Override
	public void update(Spiel spiel) {
		this.spiel = spiel;
		System.out.println("Karten des Computers: " + spiel.getSpieler().get(1).getKarten().size() + ".");
		
		printSpielfeld();
		
		System.out.println("Sie haben " + spiel.getSpieler().get(0).getKarten().size() + " Karten auf der Hand.");
		
		boolean result = true;
		do {
			String[] input = input("Ihr Zug: ").split(" ");
			switch (input[0]) {
	        case "help":
	        	System.out.println(HELP_MESSAGE);
	        	break;
	        case "rules":
	        	System.out.println(RULES);
	        	break;
	        case "pull":
	        	if (spiel.pull()) {
	        		result = true;
	        	} else {
					result = false;
					System.out.println("Keine Karten mehr auf dem Stapel.");
				}
	        	break;
	        case "put":
	        	result = move(input[1]);
	        	break;
			case "next":
				System.out.println("Bitte Warten.");
				spiel.naechsterSpieler();
	            break;
			case "bye":
				System.out.println("Bye Bye.");
				spiel.exit();
				break;
			default:
				System.out.println("Ich verstehe '" + input + "' nicht.");
				break;
			}
		} while (!result);
	}
	
	/** Gibt eine Nachricht auf der Kommandozeile aus und liesst eine Eingabe.
	 * @param message Nachricht, welche ausgegeben werden soll.
	 * @return String mit dem eingegebenen Text.
	 */
	private String input(final String message) {
		System.out.print(message);
		String input = "";
		try {
			input = new BufferedReader(new InputStreamReader(System.in)).readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return input.toLowerCase();
	}

	private void printSpielfeld() {
		int[] blue = getNumbers(Color.BLUE);
		int[] red = getNumbers(Color.RED);
		int[] green = getNumbers(Color.GREEN);
		int[] orange = getNumbers(Color.ORANGE);
		
		System.out.print("Blau   " + blue[0] + "|" + blue[1] + "|" + blue[2]);
		System.out.println();
		System.out.print("Rot    " + red[0] + "|" + red[1] + "|" + red[2]);
		System.out.println();
		System.out.print("Gruen  " + green[0] + "|" + green[1] + "|" + green[2]);
		System.out.println();
		System.out.print("Orange " + orange[0] + "|" + orange[1] + "|" + orange[2]);
		System.out.println();
	}

	/** Gibt die Nummern der Karten auf dem Spielfeld fuer eine Farbe zurueck.
	 * @param color Die Farbe der Karten fuer die die Nummern gesucht sind.
	 * @return Ein int Array mit der niedrigsten, der elf und der hoechsten Nummer pro Farbe auf dem Spielfeld.
	 */
	private int[] getNumbers(Color color) {
		int[] result = new int[3];
		// Wert der niedrigsten Karte
		try {
			result[0] = spiel.getSpielfeld().getLowestCard(color).getNummer();
		} catch (NullPointerException e) {
			result[0] = 0;
		}
		// Die Elf
		// TODO Elf ausbauen
		result[1] = 0;
		// Wert der hoechsten Karte
		try {
			result[2] = spiel.getSpielfeld().getHighestCard(color).getNummer();
		} catch (NullPointerException e) {
			result[2] = 0;
		}
		
		return result;
	}
	
	/** Prueft den gewuenschsten Zug vor und laesst ihn ausfuehren.
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
			System.out.println("Falscher Farbcode. Moeglich sind: Rot, Gruen, Blau, Orange");
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
