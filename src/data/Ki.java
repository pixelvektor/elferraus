package data;

import control.Spiel;

public class Ki extends Spieler {
	
	/** Schwierigkeit der KI. */
	private int schwierigkeit;
	
	/**
	 * Erstellt eine KI.
	 * @param name Name der KI.
	 * @param schwierigkeit Schwierigkeit der KI.
	 */
	public Ki(final String name, final int schwierigkeit){
		setName(name);
		this.schwierigkeit = schwierigkeit;
	}
	
	public void react(final Spiel spiel){
		
		if(!spiel.getStapel().getKarten().isEmpty()){
			spiel.pull();
		}
		else{
			for (int i = 0; i < Color.values().length; i++) {
				if (cardAvailable(Color.values()[i], spiel.getSpielfeld().getHighestCard(Color.values()[i]).getNummer(), true)) {
					spiel.setMove(Color.values()[i], spiel.getSpielfeld().getHighestCard(Color.values()[i]).getNummer()+1);
				}
				if (cardAvailable(Color.values()[i], spiel.getSpielfeld().getLowestCard(Color.values()[i]).getNummer(), false)) {
					spiel.setMove(Color.values()[i], spiel.getSpielfeld().getLowestCard(Color.values()[i]).getNummer()-1);
				}
			}
			spiel.naechsterSpieler();
		}
	}
	
	private boolean cardAvailable(final Color color, final int number, final boolean high) {
		for (Karte k : getKarten()) {
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
