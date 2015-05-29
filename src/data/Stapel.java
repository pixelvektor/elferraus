package data;

import java.util.ArrayList;
import java.util.Random;

public class Stapel extends Holder{
	private ArrayList<Karte> karten = new ArrayList<Karte>();
	private ArrayList<Karte> zwischenKarten = new ArrayList<Karte>();

	public void mischen() {
		         
	         Karte zwischenspeicher;
	         int zufall;
	         
	         Random random = new Random();
	           for(int i = 0; i < 79; i++) {
	             zufall = random.nextInt(80);
	             zwischenspeicher = zeigeKarten().get(i); 
	             zwischenspeicher = zeigeKarten().get(zufall); 
	             public ArrayList<Karte> zeigeKarten().get(zufall) = zwischenspeicher;
	           }
    }
	           

	public Karte getObersteKarte() {
		// TODO Auto-generated method stub
		return null;
	}

}
