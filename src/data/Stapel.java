package data;

import java.util.ArrayList;
import java.util.Random;

public class Stapel extends Holder{
	private ArrayList<Karte> zwischenKarten = new ArrayList<Karte>();

	public void mischen() {
		         
	         Karte zwischenspeicher;
	         int zufall;
	         
	         Random random = new Random();
	           for(int i = 0; i < 79; i++) {
	             zufall = random.nextInt(80);
	             zwischenspeicher = getKarten().get(i);
	             zwischenspeicher = getKarten().get(zufall);
	           }
    }
}
