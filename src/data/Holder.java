package data;

import java.util.ArrayList;
import data.Karte;

/** Hochschule Hamm-Lippstadt
 * Praktikum Informatik II (ElferRaus)
 * (C) 2015 Lara Sievers, Adrian Schmidt, Fabian Schneider
 * 22.05.2015
 */

/**
 * @author lara
 */
public abstract class Holder {
   
	private String name;
	
	private ArrayList<Karte> Karten = new ArrayList<Karte>();
	
	public void add(Karte karte) {
		// TODO Auto-generated method stub
		
	}
	
	public void remove(Karte karte) {
		
	}

	
	public Karte GetObersteKartenGruen() {
		// TODO Auto-generated method stub
		return null;
	}


	public Karte GetObersteKartenGelb() {
		// TODO Auto-generated method stub
		return null;
	}


	public Karte GetObersteKartenRot() {
		// TODO Auto-generated method stub
		return null;
	}


	public Karte getObersteKartenBlau() {
		// TODO Auto-generated method stub
		return null;
	}


	public ArrayList<Karte> zeigeKarten() {
		// TODO Auto-generated method stub
		return null;
	}

	private void sortColor() {
	
	}
	
	private void sortCount() {
		
	}
	
}
