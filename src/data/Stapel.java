
package data;

public class Stapel extends Holder{

	public void mischen() {
		         
	         Karte tmp;
	         Karte zufall;
	         Random() random = new Random()
	           for(int i = 0; i < 79; i++) {
	             zufall = random.nextInt(Karten.length);
	             temporaer = Karten[i]; 
	             Karten[i] = Karten[zufall]; 
	             Karten[zufall] =temporaer;
	}

	public Karte getObersteKarte() {
		// TODO Auto-generated method stub
		return null;
	}

}
