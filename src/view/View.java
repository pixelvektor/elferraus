/** Hochschule Hamm-Lippstadt
 * Praktikum Informatik II (ElferRaus)
 * (C) 2015 Lara Sievers, Adrian Schmidt, Fabian Schneider
 * 22.05.2015
 */

package view;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import control.Spiel;

/**
 * @author fabian
 *
 */
public class View implements ViewInterface {
	private static final String HELP_MESSAGE = "Um eine Karte zu ziehen geben Sie 'pull' ein.\r\n"
			+ "Um eine Karte zu legen geben Sie den Farbbuchstaben und die Nummer ein. Bsp: R9.\r\n"
			+ "Und wenn Sie dann doch keine Lust mehr haben sagen Sie 'bye'.\r\n";

	/** Erstellt eine neue View fuer das Spiel.
	 * Muss an Spiel uebergeben werden.
	 */
	public View() {
		System.out.println("ElferRaus");
		System.out.println(HELP_MESSAGE);
	}
	
	@Override
	public void update(Spiel spiel) {
		System.out.println("Karten des Computers: " + spiel.getSpieler().get(1).getKarten().size() + ".");
		
		printSpielfeld();
		
		System.out.println("Sie haben " + spiel.getSpieler().get(0).getKarten().size() + " auf der Hand.");
		
		String input = (input("Ihr Zug: "));
		switch (input) {
		case "bye":
			System.out.println("Bye Bye.");
			spiel.exit();
			break;
		case "next":
			System.out.println("Bitte Warten.");
			spiel.naechsterSpieler();
            break;
		default:
			System.out.println("Ich verstehe '" + input + "' nicht.");
			break;
		}
	}
	
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
		System.out.print("Blau   ");
		System.out.println();
		System.out.print("Rot    ");
		System.out.println();
		System.out.print("Gruen  ");
		System.out.println();
		System.out.print("Orange ");
		System.out.println();
	}
}
