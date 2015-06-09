package data;

import control.Spiel;

public class Ki extends Spieler {
	
	private int schwierigkeit;
	private Spiel spiel;
	
	public Ki(final String name, final int schwierigkeit){
		setName(name);
		this.schwierigkeit=schwierigkeit;
	}
	
	public void react(Spiel spiel){
		this.spiel=spiel;
		System.out.println("Ki macht");
		if(spiel.getStapel().getKarten().isEmpty() != true){
			spiel.pull();
		}
		else{
			spiel.naechsterSpieler();
		}
		//spiel.pull();
		//spiel.naechsterSpieler();
	}
 }
