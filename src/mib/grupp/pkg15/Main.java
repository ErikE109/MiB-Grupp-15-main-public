/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mib.grupp.pkg15;

import java.util.logging.Level;
import java.util.logging.Logger;
import oru.inf.InfDB;
import oru.inf.InfException;

/**
 *
 * @author erike
 */
public class Main {

    private static InfDB idb;

    public static void main(String[] args) throws InfException {
        try {
            idb = new InfDB("mibdb", "3307", "mibdba", "mibkey");
        } catch (InfException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        //Startar de metoder som behövs i programmet.
        Navigera.openStartSkärm();
        new ComboBoxar();
        new GetMetoder();
    }
//Metod som returnerar ett databasObjekt

    public static InfDB getDB() {
        return idb;
    }

}
