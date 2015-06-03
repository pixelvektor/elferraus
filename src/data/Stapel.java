package data;

import java.util.Collections;

public class Stapel extends Holder{

	public void mischen() {
		Collections.shuffle(this.getKarten());
		System.out.println(this.getKarten().size());
	}
}

