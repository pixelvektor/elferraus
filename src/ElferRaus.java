/** Hochschule Hamm-Lippstadt
 * Praktikum Informatik II (ElferRaus)
 * (C) 2015 Lara Sievers, Adrian Schmidt, Fabian Schneider
 * 22.05.2015
 */

import control.Spiel;
import view.View;

/** Hauptklasse des Spiels ElferRaus.
 * Startet das Spiel.
 * @author fabian
 * @version 2205201501
 */
public final class ElferRaus {

    /** Hauptmethode des Spiels.
     * Erstellt eine View und weist diese dem Spiel zu.
     * @param args nicht genutzt.
     */
    public static void main(String... args) {
        View view = new View();
        new Spiel(view);
    }
    
    private ElferRaus() {}
}
