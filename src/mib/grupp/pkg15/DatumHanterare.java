/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mib.grupp.pkg15;

import java.time.LocalDate;
import java.time.Year;

/**
 *
 * @author augustdelin Klassen används för att skapa och hantera olika datum och
 * årvariabler(date).
 */
//Fälten för klassen DatumHanterare.
public class DatumHanterare {

//Konstruktorn för klassen DatumHanterare.
    public DatumHanterare() {

    }

    /**
     * Hämtar och returnerar dagens datum.
     * @return
     */
    public static String getDagensDatum() {
        String dagensDatum = LocalDate.now().toString();
        return dagensDatum;
    }

    /**
     * Hämtar och returnerar nuvarande år.
     * @return
     */
    public static String getNuvarandeÅrtal() {
        String nuvarandeÅr = Year.now().toString();
        return nuvarandeÅr;

    }
}
