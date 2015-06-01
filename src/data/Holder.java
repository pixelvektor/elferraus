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

	private ArrayList<Karte> karten = new ArrayList<Karte>();
    
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public void add(Karte karte) {
		karten.add(karte);
		
	}
	
	public void remove(Karte karte) {
		
	}

	public Karte GetObersteKartenGruen() {
		// TODO Auto-generated method stub
		return null;
	}

	public Karte GetObersteKartenOrange() {
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
	
	public Karte getObersteKarte() {
		return karten.get(0);
	}

	public ArrayList<Karte> getKarten() {
		return karten;
	}

	private void sortColor() {
	
	}
	
	private void sortCount() {
		
	}
}
