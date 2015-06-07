package data;

import control.Spiel;

public class Ki extends Spieler {
	
	private int schwierigkeit;
	private Spiel spiel;
	
	public Ki(int schwierigkeit){
		this.schwierigkeit=schwierigkeit;
	}
	
	public void react(Spiel spiel){
		this.spiel=spiel;
		System.out.println("Ki macht");
		spiel.naechsterSpieler();
	}
 }
