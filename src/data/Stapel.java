package data;

import java.util.Collections;

public class Stapel extends Holder{

	public void mischen() {
		Collections.shuffle(this.getKarten());
		System.out.println(this.getKarten().size());
	}
	
	/** Nimmt eine Karte vom Stapel und gibt diese zurueck.
	 * Die Karte wird auf dem Stapel geloescht.
	 * @return Unterste Karte vom Stapel. null bei leerem Stapel.
	 */
	public Karte getNext() {
		Karte karte = this.getKarten().get(0);
		this.remove(karte);
		return karte;
	}
}

