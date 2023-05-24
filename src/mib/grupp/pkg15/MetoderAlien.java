/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mib.grupp.pkg15;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import oru.inf.InfException;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import oru.inf.InfDB;

/**
 *  Denna klass innehåller metoder som används när man är inloggad som Alien
 * @author augustdelin
 */
//Fälten för klassen AlienMetoder.
public class MetoderAlien {

    private static InfDB idb;
    private static StartSkärm ettFönster;

//    Konstruktorn för klassen AlienMetoder.
    public MetoderAlien(StartSkärm ettFönster) {
        idb = Main.getDB();
        this.ettFönster = ettFönster;

    }

    /**
     * Metoden för att logga in som Alien.
     *
     * @param användarnamnRuta
     * @param lösenruta
     */
    public static void loggaInAlien(JTextField användarnamnRuta, JPasswordField lösenruta) {
        if (Validera.kollaTom(användarnamnRuta) && Validera.kollaTom(lösenruta)) {
            try {
                //hämtar användarnamn ifrån loginruta
                String användarnamn = användarnamnRuta.getText();

                // hämta lösenordet som matchar angivet användarnamn ifrån databasen
                String lösenord = idb.fetchSingle("Select Losenord from alien where namn ='" + användarnamn + "'");

                //jämför inskrivet lösen med det som står skrivet i rutan lösenord
                if (Validera.kollaNullvärde(lösenord) && Validera.kollaLösen(lösenord, lösenruta)) {

                    //om ovan villkor är true skapas en ny ruta
                    new AlienStartSkärm(användarnamn).setVisible(true);
                    ettFönster.dispose();
                }

            } catch (InfException ex) {
                Logger.getLogger(StartSkärm.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * Metoden för att visa områdeschef.
     *
     * @param ettFönster
     * @param användarnamn
     */
    public static void visaMinOC(JLabel ettFönster, String användarnamn) {
        try {
            String OC = idb.fetchSingle("Select agent.namn from agent join omradeschef on agent.Agent_ID = omradeschef.Agent_ID join omrade on omradeschef.omrade = omrade.Omrades_ID join plats on omrade.Omrades_ID = plats.Finns_I join alien on plats.Plats_ID = alien.Plats where alien.namn = '" + användarnamn + "'");
            ettFönster.setText("Din områdeschef är: " + OC + ".");
        } catch (InfException ex) {
            Logger.getLogger(MetoderAlien.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * Metoden för att byta lösenord för Alien.
     *
     * @param användarnamn
     * @param gammaltlösen
     * @param nyttlösen
     */
    public static void bytLösenord(String användarnamn, JPasswordField gammaltlösen, JPasswordField nyttlösen) {
        if (Validera.kollaTom(gammaltlösen) && Validera.kollaTom(nyttlösen) && Validera.kollaLängdLösenord(nyttlösen))
        try {
            String lösenord = idb.fetchSingle("Select Losenord from alien where namn ='" + användarnamn + "'");
            if (Validera.kollaLösen(lösenord, gammaltlösen)) {
                String nyttLösenord = nyttlösen.getText();
                idb.update("UPDATE alien SET losenord='" + nyttLösenord + "' where namn ='" + användarnamn + "'");
                JOptionPane.showMessageDialog(null, "Lösenordet har ändrats.");
                gammaltlösen.setText("");
                nyttlösen.setText("");
            }
        } catch (InfException ex) {
            Logger.getLogger(MetoderAlien.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void listaAliensIMittområde(String användarnamn, JTextArea enArea, JLabel enLabel) {
        String aliensOmråde = GetMetoder.hamtaEnAliensOmråde(användarnamn);
        ArrayList<String> aliensIMittOmråde = GetMetoder.hamtaAllaAliensfrånOmråde(aliensOmråde);
        enLabel.setText("Aliens i " + aliensOmråde);
        
        for(String enAlien : aliensIMittOmråde)
        {
            enArea.append(enAlien + "\n");
        }

        
    }
}
