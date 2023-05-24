/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mib.grupp.pkg15;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;
import oru.inf.InfDB;
import oru.inf.InfException;

/**
 *
 * @author erike 
 * Denna klass ansvarar för att hantera saker som händer i
 * ComboBoxar såsom att fylla dem.
 */
//Fälten för klassen ComboBoxar.
public class ComboBoxar {

    private static InfDB idb;

//    Konstruktorn för ComboBoxar.
    public ComboBoxar() {
        idb = Main.getDB();

    }

    /**
     * Hämtar en ArrayList och fyller lådan med namnen på de platserna som finns i databasen
     *
     * @param enLåda
     */
    public static void fyllCBPlats(JComboBox enLåda) {
        try {
            enLåda.removeAllItems();
            ArrayList<String> platser = idb.fetchColumn("select benamning from plats order by benamning");
            for (String enPlats : platser) {
                enLåda.addItem(enPlats);
            }

        } catch (InfException ex) {
            Logger.getLogger(MetoderAgentAdmin.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Skapar en ArrayList och fyller lådan med de tre raserna som finns.
     *
     * @param enLåda
     */
    public static void fyllCBras(JComboBox enLåda) {
        ArrayList<String> raslista = new ArrayList();
        raslista.add("Boglodite");
        raslista.add("Squid");
        raslista.add("Worm");

        for (String rasnamn : raslista) {
            enLåda.addItem(rasnamn);
        }

    }

    /**
     * Hämtar en ArrayList och fyller lådan med de namn som finns hos aliens.
     *
     * @param enLåda
     */
    public static void fyllCBAlienNamn(JComboBox enLåda) {
        try {
            ArrayList<String> namnLista = idb.fetchColumn("select namn from alien");

            for (String ettNamn : namnLista) {
                enLåda.addItem(ettNamn);
            }
        } catch (InfException ex) {
            Logger.getLogger(MetoderAgentAdmin.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Hämtar en ArrayList från databasen och fyller vald ComboBox med namnen
     * från utrustningslistan.
     *
     * @param enLåda
     */
    public static void fyllCBAgentUtrustning(JComboBox enLåda) {

        try {
            ArrayList<String> utrustningsLista = idb.fetchColumn("Select benamning from utrustning order by benamning");

            for (String enUtrustning : utrustningsLista) {
                enLåda.addItem(enUtrustning);
            }
        } catch (InfException ex) {
            Logger.getLogger(MetoderAgentAdmin.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * Hämtar en ArrayList ifrån databasen och fyller en ComboBox med med
     * samtliga namn på agenterna som finns i databasen.
     *
     * @param enLåda
     */
    public static void fyllCBAgentNamn(JComboBox enLåda) {

        try {
            ArrayList<String> namnListaAgent = idb.fetchColumn("Select Namn from Agent order by namn");

            for (String enAgent : namnListaAgent) {
                enLåda.addItem(enAgent);
            }
        } catch (InfException ex) {
            Logger.getLogger(MetoderAgentAdmin.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * Fyller vald ComboBox med värden J eller N som motsvarar adminstatus.
     *
     * @param enLåda
     */
    public static void fyllAdminStatus(JComboBox enLåda) {
        enLåda.addItem("J");
        enLåda.addItem("N");
    }

    /**
     * Hämtar en ArrayList från databasen och fyller en ComboBox med alla
     * områdesnamn som finns.
     *
     * @param enLåda
     */
    public static void fyllCBområden(JComboBox enLåda) {
        try {
            ArrayList<String> områdesLista = idb.fetchColumn("Select Benamning from Omrade order by Benamning");
            for (String ettOmråde : områdesLista) {
                enLåda.addItem(ettOmråde);
            }
        } catch (InfException ex) {
            Logger.getLogger(ComboBoxar.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Rullistan för att välja ras. Några av raserna har tillhörande attribut, i
     * de fallen så tillkommer en extra ruta genom en if-sats för att ange de
     * extra attributen.
     *
     * @param cbRaser
     * @param lblRasAttribut
     * @param txtRasAttribut
     */
    public static void CBvaldAlienRas(JComboBox cbRaser, JLabel lblRasAttribut, JTextField txtRasAttribut) {
        String valdRas = GetMetoder.hamtaCbSträng(cbRaser);
        if (valdRas.equals("Boglodite")) {
            lblRasAttribut.setText("Antal boogies");
            txtRasAttribut.setText("");
            lblRasAttribut.setVisible(true);
            txtRasAttribut.setVisible(true);
        }
        if (valdRas.equals("Squid")) {
            lblRasAttribut.setText("Antal armar");
            txtRasAttribut.setText("");
            lblRasAttribut.setVisible(true);
            txtRasAttribut.setVisible(true);
        }
        if (valdRas.equals("Worm")) {
            txtRasAttribut.setText("1");
            lblRasAttribut.setVisible(false);
            txtRasAttribut.setVisible(false);
        }
    }

    /**
     * Denna metoden används vid nyregistrering av utrustning och reagerar på
     * vald utrustningstyp, exempelvis så ändras textfält.
     *
     * @param cbTyper
     * @param lblTypAttribut
     * @param txtTypAttribut
     */
    public static void CBvaldUtrustning(JComboBox cbTyper, JLabel lblTypAttribut, JTextField txtTypAttribut) {
        String valdUtrustning = GetMetoder.hamtaCbSträng(cbTyper);
        if (valdUtrustning.equals("Vapen")) {
            lblTypAttribut.setText("Kaliber");
            txtTypAttribut.setText("");
            lblTypAttribut.setVisible(true);
            txtTypAttribut.setVisible(true);
        }
        if (valdUtrustning.equals("Kommunikation")) {
            lblTypAttribut.setText("Överföringsteknik");
            txtTypAttribut.setText("");
            lblTypAttribut.setVisible(true);
            txtTypAttribut.setVisible(true);
        }
        if (valdUtrustning.equals("Teknik")) {
            lblTypAttribut.setText("Kraftkälla");
            txtTypAttribut.setText("");
            lblTypAttribut.setVisible(true);
            txtTypAttribut.setVisible(true);
        }
    }

    /**
     * Denna metod gör en arraylist av de utrustningstyper som finns och lägger
     * in denna i en ComboBox.
     *
     * @param enLåda
     */
    public static void fyllCBtyp(JComboBox enLåda) {
        ArrayList<String> typlista = new ArrayList();
        typlista.add("Vapen");
        typlista.add("Kommunikation");
        typlista.add("Teknik");

        for (String typnamn : typlista) {
            enLåda.addItem(typnamn);
        }

    }

    
    /**
     * Denna metod hämtar en ArrayList och fyller en ComboBox med alla kontor som finns i databasen
     *
     * @param enLåda
     */
    public static void fyllCBAllaKontor(JComboBox enLåda) {

        try {
            ArrayList<String> kontorslista = idb.fetchColumn("select kontorsbeteckning from kontorschef order by kontorsbeteckning");
            for (String kontor : kontorslista) {
                enLåda.addItem(kontor);
            }
        } catch (InfException ex) {
            Logger.getLogger(MetoderUnikaAdmin.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * Denna metod hämtar en ArrayList och fyller en ComboBox med namnet på områdeschefer.
     *
     * @param enLåda
     */
    public static void fyllOmrådesChefer(JComboBox enLåda) {
        ArrayList<HashMap<String, String>> chefsLista = GetMetoder.hämtaOmrådesChefer();
        for (HashMap<String, String> enAgent : chefsLista) {
            enLåda.addItem(enAgent.get("Namn"));
        }

    }

    /**
     * Denna metod hämtar en ArrayList och fyller en ComboBox med namnet på kontorschefer.
     *
     * @param enLåda
     */
    public static void fyllKontorschefer(JComboBox enLåda) {
        ArrayList<HashMap<String, String>> chefsLista = GetMetoder.hämtaKontorsChefer();
        for (HashMap<String, String> enAgent : chefsLista) {
            enLåda.addItem(enAgent.get("Namn"));
        }

    }
    
    public static void fyllAnsvarigaAgenter(JComboBox enLåda)
    {
        ArrayList<String> ansvarigaAgenter = GetMetoder.hämtaAnsvarigaAgenter();
        for(String enAgent : ansvarigaAgenter)
        {
            enLåda.addItem(enAgent);
        }
    }
}
