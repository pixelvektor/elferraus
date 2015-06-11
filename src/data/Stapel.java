package data;

import java.util.Collections;

public class Stapel extends Holder{

	/** Mischt den Stapel.
	 */
	public void mischen() {
		Collections.shuffle(this.getKarten());
	}
	
	/** Nimmt eine Karte vom Stapel und gibt diese zurueck.
	 * Die Karte wird auf dem Stapel geloescht.
	 * @return Unterste Karte vom Stapel. null bei leerem Stapel.
	 */
	public Karte getNext() {
		Karte karte = null;
		try {
			karte = this.getKarten().get(0);
		} catch (IndexOutOfBoundsException e) {
			//e.printStackTrace();
		}
		this.remove(karte);
		return karte;
	}
}

