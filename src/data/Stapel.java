package data;

import java.util.ArrayList;
import java.util.Collections;

public class Stapel extends Holder{

	private ArrayList<Karte> karten = new ArrayList<Karte>();
	

	public void mischen() {
		for (Karte k : karten) {
			System.out.println(k.toString());
		}
		
		Collections.shuffle(karten);
		
		System.out.println("###############################################");
		
		for (Karte k : karten) {
			System.out.println("o");
			System.out.println(k.toString());
		}
		
		System.out.println("gnarf");
		 
	}
}

