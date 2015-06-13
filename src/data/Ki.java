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
	private ArrayList<Karte> backHand;
	/** Schwierigkeit der KI. */
	private final boolean difficult;
	
	/**
	 * Erstellt eine KI.
	 * @param name Name der KI.
	 * @param difficult Schwierigkeit der KI.
	 */
	public Ki(final String name, final boolean difficult){
		setName(name);
		this.difficult = difficult;
	}
	
	/** Reaktion der KI.
	 * @param spiel Das aktuelle Spiel.
	 */
	public void react(final Spiel spiel){
		if (difficult) {
			checkBackHand();
		}
		
		if(!spiel.getStapel().getCards().isEmpty()){
			spiel.pull();
		}
		else if (!difficult){
			easy(spiel);
		} else {
			hard(spiel);
		}
	}
	
	/** Prueft ob hohe Karten vorhanden sind und legt diese in die Hinterhand zurueck.
	 */
	private void checkBackHand() {
		
	}

	/**
	 * @param spiel
	 */
	private void easy(final Spiel spiel) {
		for (int i = 0; i < Color.values().length; i++) {
			if (spiel.getSpielfeld().getHighestCard(Color.values()[i]) != null) {
				if (cardAvailable(Color.values()[i], spiel.getSpielfeld().getHighestCard(Color.values()[i]).getNummer(), true)) {
					spiel.setMove(Color.values()[i], spiel.getSpielfeld().getHighestCard(Color.values()[i]).getNummer()+1);
				}
				if (cardAvailable(Color.values()[i], spiel.getSpielfeld().getLowestCard(Color.values()[i]).getNummer(), false)) {
					spiel.setMove(Color.values()[i], spiel.getSpielfeld().getLowestCard(Color.values()[i]).getNummer()-1);
				}
			}
		}
		spiel.naechsterSpieler();
	}
	
	private void hard(final Spiel spiel) {
		spiel.naechsterSpieler();
	}
	
	private boolean cardAvailable(final Color color, final int number, final boolean high) {
		for (Karte k : getCards()) {
			if (k.getFarbe().equals(color) && k.getNummer() == number+1 && high) {
				return true;
			}
			if (k.getFarbe().equals(color) && k.getNummer() == number-1 && !high) {
				return true;
			}
		}
		return false;
	}
 }
