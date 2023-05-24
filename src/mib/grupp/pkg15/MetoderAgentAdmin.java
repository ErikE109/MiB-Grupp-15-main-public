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
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import oru.inf.InfDB;
import oru.inf.InfException;

/**
 *
 * @author erike
 */
// Fälten för klassen AgentochAdminMetoder.
public class MetoderAgentAdmin {

    private static InfDB idb;
    private static StartSkärm ettFönster;

//    Konstruktorn för klassen AgentochAdminMetoder.
    public MetoderAgentAdmin(StartSkärm ettFönster) {
        this.ettFönster = ettFönster;
        idb = Main.getDB();

    }

    /**
     *Metoden för att logga in som Agent.
     * @param användarnamnRuta
     * @param lösenruta 
     */
    public static void loggainAgent(JTextField användarnamnRuta, JPasswordField lösenruta) {

        if (Validera.kollaTom(användarnamnRuta) && Validera.kollaTom(lösenruta)) {
            try {
                //hämtar användarnamn ifrån loginruta
                String användarnamn = användarnamnRuta.getText();

                // hämta lösenordet som matchar angivet användarnamn ifrån databasen
                String lösenord = idb.fetchSingle("Select Losenord from agent where namn ='" + användarnamn + "'");

                //jämför inskrivet lösen med det som står skrivet i rutan lösenord
                if (Validera.kollaNullvärde(lösenord) && Validera.kollaLösen(lösenord, lösenruta)) {

                    //om ovan villkor är true skapas en ny ruta
                    new AgentStartSkärm(användarnamn).setVisible(true);
                    ettFönster.dispose();

                }

            } catch (InfException ex) {
                Logger.getLogger(StartSkärm.class.getName()).log(Level.SEVERE, null, ex);

            }
        }
    }

    /**
     * Metod för att byta lösenord för Agent.
     * @param användarnamn
     * @param gammaltlösen
     * @param nyttlösen 
     */
    public static void bytLösenord(String användarnamn, JPasswordField gammaltlösen, JPasswordField nyttlösen) {
        //Kontrollerar att båda fälten är ifyllda
        if (Validera.kollaTom(gammaltlösen) && Validera.kollaTom(nyttlösen) && Validera.kollaLängdLösenord(nyttlösen))
        try {
            //Hämtar lösenord som tillhör inskrivet ID ifrån databasen
            String lösenord = idb.fetchSingle("Select Losenord from AGENT where namn ='" + användarnamn + "'");
            //Kollar så att det nya stämmer överens med det gamla
            if (Validera.kollaLösen(lösenord, gammaltlösen)) {
                String nyttLösenord = nyttlösen.getText();
                idb.update("UPDATE AGENT SET losenord='" + nyttLösenord + "' where namn ='" + användarnamn + "'");
                JOptionPane.showMessageDialog(null, "Lösenordet har ändrats!");
                gammaltlösen.setText("");
                nyttlösen.setText("");
            }
        } catch (InfException ex) {
            Logger.getLogger(MetoderAgentAdmin.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Metod för att lista aliens på specifika platser.
     * @param lista
     * @param låda
     */
    public static void listaAliensPåPlats(JTextArea lista, JComboBox låda) {
        //Nollsätter listan
        lista.setText("");
        try {
            //Hämtar Sträng och namnger denna till valdPlats ifrån Combobox
            String valdPlats = GetMetoder.hamtaCbSträng(låda);
            //Listan gås igenom och listar namnen på aliens som finns på vald plats
            ArrayList<String> aliensPåPlats = idb.fetchColumn("select namn from alien join plats on alien.Plats = plats.Plats_ID where plats.benamning = '" + valdPlats + "'");
            for (String alien : aliensPåPlats) {
                lista.append(alien + "\n");
            }
        } catch (InfException ex) {
            Logger.getLogger(MetoderAgentAdmin.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Visar alla aliens som tillhör vald ras.
     * @param lista
     * @param låda 
     */
    public static void listaAliensPerRas(JTextArea lista, JComboBox låda) {
        //Listan sätts blank
        lista.setText("");
        try {
        //Hämtar sträng ifrån COmboBox.
            String valdRas = GetMetoder.hamtaCbSträng(låda);
            //Hämtar en ArrayList av vald ras och skriver ut denna i listan.
            ArrayList<String> alienavRas = idb.fetchColumn("select Namn from alien join " + valdRas + " on alien.alien_id =" + valdRas + ".alien_id");
            for (String enAlien : alienavRas) {
                lista.append(enAlien + "\n");
            }
        } catch (InfException ex) {
            Logger.getLogger(MetoderAgentAdmin.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     *Skapar en hashmap och visar all information om varje enskild alien.
     * @param lista
     * @param låda 
     * 
     */
    public static void listaEnskildaAliens(JTextArea lista, JComboBox låda) {
        //Sätter textfältet som tomt
        lista.setText("");
        try {
            // hämtar variabler ifrån fälten
            String valdAlien = GetMetoder.hamtaCbSträng(låda);
            String ras = GetMetoder.getRasFrånNamn(valdAlien);
            //HashMapen gås igenom. Först namnges rubriker sedan hämtas data med hjälp av nyckeln som skrivs ut i listan.
            HashMap<String, String> alienAvNamn = idb.fetchRow("select alien.Losenord, Alien_ID, alien.Namn, Registreringsdatum, alien.Telefon, Benamning, agent.Namn from alien join agent on alien.Ansvarig_Agent = agent.Agent_ID join plats on alien.Plats = plats.Plats_ID where alien.namn = '" + valdAlien + "'");
            lista.append("ID\tNamn\tRas\tTelefon\tPlats\tAnsvar\tRegdatum\tLösenord\n");
            lista.append(alienAvNamn.get("Alien_ID") + "\t");
            lista.append(valdAlien + "\t");
            lista.append(ras + "\t");
            lista.append(alienAvNamn.get("Telefon") + "\t");
            lista.append(alienAvNamn.get("Benamning") + "\t");
            lista.append(alienAvNamn.get("Namn") + "\t");
            lista.append(alienAvNamn.get("Registreringsdatum") + "\t");
            lista.append(alienAvNamn.get("Losenord"));

        } catch (InfException ex) {
            Logger.getLogger(MetoderAgentAdmin.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Metod för att lägga till utrustning på en agent.
     * @param enLåda
     * @param användarnamn
     */
    public static void laggTillUtrustningPåAgent(JComboBox enLåda, String användarnamn) {
        //Deklarerar felmeddelande som ska användas i om utrustning redan finns på agenten.
        String felmeddelande = "Denna utrustning är redan registrerad på " + användarnamn;
        //Hämtar vald utrustning ifrån en Combobox och gör om String variabler till integers.
        String valdUtrustning = GetMetoder.hamtaCbSträng(enLåda);
        int utrustningsID = GetMetoder.hämtaUtrustningsIDFrånNamn(valdUtrustning);
        int agentID = GetMetoder.hämtaAgentIDFrånNamn(användarnamn);
        //Här görs IDt om till strängar för att kunna jämföra om agenten har utrustningen registrerad på sig eller inte
        String utrustningsIDSomSträng = Integer.toString(utrustningsID);
        String dagensDatum = DatumHanterare.getDagensDatum();
        if (Validera.kollaOmvärdeFinnsIArrayList(GetMetoder.getUtrustningsIDnFrånAgentID(agentID), utrustningsIDSomSträng, felmeddelande)) {
            // om Valideringen godkänns registreras den nya utrustningen på agenten.
            try {
                idb.insert("Insert into Innehar_Utrustning values(" + agentID + "," + utrustningsID + ",'" + dagensDatum + "')");
                JOptionPane.showMessageDialog(null, "Du har lagt till " + valdUtrustning + " till din lista!");

            } catch (InfException ex) {
                Logger.getLogger(MetoderAgentAdmin.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

    /**
     * Metod för att lista vem som är chef över ett valt område.
     * Denna metod används inte i systemet, vi hade med denna i början för att bokstavlingen uppfylla kravet visa chef av valt område
     * Men eftersom det bara finns tre områden valde vi att ta bort denna och istället lista alla områdeschefer med tillhörande i en och samma area
     * @param lista
     * @param låda
     */
    public static void listaChefAvOmrade(JTextArea lista, JComboBox låda) {
        //Sätter textfältet blankt
        lista.setText("");
        try {
            //Hämtar en sträng ifrån en Combobox, i detta fall valt område.
            String valtOmrade = GetMetoder.hamtaCbSträng(låda);
            //Hämtar lista ifrån DB.
            ArrayList<String> chefAvOmrade = idb.fetchColumn("select Agent.namn from agent join omradeschef on omradeschef.Agent_ID = agent.Agent_ID join omrade on omrade.Omrades_ID= omradeschef.Agent_ID where omrade.benamning= '" + valtOmrade + "'");
            //Listar alla områdeschef i valt område.
            for (String enChef : chefAvOmrade) {
                lista.append(enChef);
            }
        } catch (InfException ex) {
            Logger.getLogger(MetoderAgentAdmin.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    /**
     * Metod för att visa en lista över alla kontorschefer.
     * @param lista
     */
    public static void listaAllaKontorsChefer(JTextArea lista) {
        //Sätter textfältet blankt
        lista.setText("");
        lista.setText("Kontor\t                         Chef\n");
            //Hämtar lista ifrån DB. 
            ArrayList<HashMap<String,String>> chefAvkontor = GetMetoder.hämtaKontorsChefer();
            //Listar alla kontorschefer.
            for (HashMap<String,String> enChef : chefAvkontor) {
                lista.append(enChef.get("Kontorsbeteckning")+"\t");
                 lista.append(enChef.get("Namn")+"\n");
            }
        
    }
     
    /**
     * Listar samtliga områdeschefer.
     * @param lista
     */
    public static void listaAllaOmrådesChefer(JTextArea lista) {
        //Sätter textfältet blankt
        lista.setText("");
        lista.setText("Område\tChef\n");
            //Hämtar lista ifrån DB
            ArrayList<HashMap<String,String>> chefAvkontor = GetMetoder.hämtaOmrådesChefer();
            //Listar alla områdeschefer i valt område
            for (HashMap<String,String> enChef : chefAvkontor) {
                lista.append(enChef.get("Benamning")+"\t");
                 lista.append(enChef.get("Namn")+"\n");
            }
    }

    /**
     * Metod för att visa aliens inom två specifika registreringsdatum.
     * @param fält1
     * @param fält2
     * @param enArea
     */
    public static void visaAlienFrånRegDatum(JTextField fält1, JTextField fält2, JTextArea enArea) {
        //Nödvändiga valideringar görs innan programet körs.
        if (Validera.kollaTom(fält1) && Validera.kollaTom(fält2) && Validera.kollaDatumFormat(fält1) && Validera.kollaDatumFormat(fält2)) {
            try {
                //Variabler hämtas ifrån getfält.
                enArea.setText("");
                String datum1 = fält1.getText();
                String datum2 = fält2.getText();
                ArrayList<HashMap<String, String>> alien = idb.fetchRows("select namn from alien where Registreringsdatum between'" + datum1 + "'and'" + datum2 + "'");

                //Listan gås igenom och läggs upp i en textarea.
                for (HashMap<String, String> enRad : alien) {
                    enArea.append(enRad.get("Namn") + "\n");
                }
            } catch (InfException ex) {
                Logger.getLogger(MetoderAgentAdmin.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     *  Metod för att registrera en ny Alien.
     * @param id
     * @param datum
     * @param namnFält
     * @param rasLåda
     * @param lösenFält
     * @param telNrFält
     * @param platsLåda
     * @param agentLåda
     * @param attributFält 
     * 
     */
    public static void nyRegistreraAlien(JLabel id, JLabel datum, JTextField namnFält, JComboBox rasLåda, JPasswordField lösenFält, JTextField telNrFält, JComboBox platsLåda, JComboBox agentLåda, JTextField attributFält) {
        //Validering för samtliga fält görs så om valideringen godkänns körs programmet.
        if (Validera.kollaTom(namnFält) && Validera.kollaTom(lösenFält) && Validera.kollaTom(telNrFält) && Validera.kollaSträngBörjaStorBokstav(namnFält) && Validera.kollaMängdRasAttribut(attributFält) && Validera.kollaTelefonnummer(telNrFält) && Validera.kollaLängdLösenord(lösenFält)) {

            String ettNamn = null;

            try {
                //Först deklarerars alla variabler, text hämtas från fält och nödvändiga Stringvaribler konverteras till int.
                String ettIDString = id.getText();
                int ettID = Integer.parseInt(ettIDString);
                String ettDatum = datum.getText();
                ettNamn = namnFält.getText();
                String valdRas = rasLåda.getSelectedItem().toString();
                String mängdAttributString = attributFält.getText();
                int mängdAttribut = Integer.parseInt(mängdAttributString);
                String ettLösen = lösenFält.getText();
                String ettTelNr = telNrFält.getText();
                String enPlats = platsLåda.getSelectedItem().toString();
                String platsIDSträng = idb.fetchSingle("select Plats_ID from plats where Benamning = '" + enPlats + "'");
                int platsID = Integer.parseInt(platsIDSträng);
                String enAgent = agentLåda.getSelectedItem().toString();
                String agentIDSträng = idb.fetchSingle("select Agent_ID from agent where namn = '" + enAgent + "'");
                int agentID = Integer.parseInt(agentIDSträng);
                ArrayList<String> NamnLista = GetMetoder.getAlienNamn();

                if (Validera.kollaOmvärdeFinnsIArrayList(NamnLista, ettNamn, "En alien vid namn " + ettNamn + " finns redan registerad")) {

                    idb.insert("insert into alien values(" + ettID + ",'" + ettDatum + "','" + ettLösen + "','" + ettNamn + "','" + ettTelNr + "'," + platsID + "," + agentID + ")");
                    if (valdRas.equals("Boglodite")) {
                        idb.insert("insert into boglodite values(" + ettID + "," + mängdAttribut + ")");
                    }
                    if (valdRas.equals("Squid")) {
                        idb.insert("insert into squid values(" + ettID + "," + mängdAttribut + ")");
                    }
                    if (valdRas.equals("Worm")) {
                        idb.insert("insert into worm values(" + ettID + ")");
                    }
                    JOptionPane.showMessageDialog(null, ettNamn + " är nu registrerad");
                    id.setText(GetMetoder.getNextAlienID());
                    namnFält.setText("");
                    lösenFält.setText("");
                    telNrFält.setText("");
                    attributFält.setText("");

                }

            } catch (InfException ex) {
                Logger.getLogger(MetoderAgentAdmin.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

    }

    /**
     * Metod för att visa all information om en specifik alien.
     * Används i registreringsfönstret för omregistrering av alien
     * @param alienLåda
     * @param IDFält
     * @param RegFält
     * @param NamnFält
     * @param RasFält
     * @param LösenFält
     * @param TeleFält
     * @param PlatsFält
     * @param AnsvarigAgent
     * @param rasAttribut
     */
    public static void visaInformationAlien(JComboBox alienLåda, JLabel IDFält, JTextField RegFält, JTextField NamnFält, JComboBox RasFält, JTextField LösenFält, JTextField TeleFält, JComboBox PlatsFält, JComboBox AnsvarigAgent, JTextField rasAttribut) {
        String valdAlien = GetMetoder.hamtaCbSträng(alienLåda);
        String ras = GetMetoder.getRasFrånNamn(valdAlien);
        HashMap<String, String> alienAvNamn = GetMetoder.getEnAlien(valdAlien);

        IDFält.setText(alienAvNamn.get("Alien_ID"));
        RegFält.setText(alienAvNamn.get("Registreringsdatum"));
        NamnFält.setText(valdAlien);
        RasFält.setSelectedItem(ras);
        LösenFält.setText(alienAvNamn.get("Losenord"));
        TeleFält.setText(alienAvNamn.get("Telefon"));
        PlatsFält.setSelectedItem(alienAvNamn.get("Benamning"));
        AnsvarigAgent.setSelectedItem(alienAvNamn.get("Namn"));

        String mängd = GetMetoder.getRasAttributFrånID(alienAvNamn.get("Alien_ID"));
        rasAttribut.setText(mängd);
    }

    /**
     * Metod för att ändra information om en alien.
     * @param gammaltNamnLåda
     * @param id
     * @param datum
     * @param namnFält
     * @param rasLåda
     * @param lösenFält
     * @param telNrFält
     * @param platsLåda
     * @param agentLåda
     * @param attributFält
     */
    public static void ändraAlien(JComboBox gammaltNamnLåda, JLabel id, JTextField datum, JTextField namnFält, JComboBox rasLåda, JPasswordField lösenFält, JTextField telNrFält, JComboBox platsLåda, JComboBox agentLåda, JTextField attributFält) {
        //Validering för samtliga fält görs så om valideringen godkänns körs programmet.
        if (Validera.kollaTom(namnFält) && Validera.kollaTom(lösenFält) && Validera.kollaTom(telNrFält) && Validera.kollaSträngBörjaStorBokstav(namnFält) && Validera.kollaMängdRasAttribut(attributFält) && Validera.kollaTelefonnummer(telNrFält) && Validera.kollaLängdLösenord(lösenFält)) {
            String ettNamn = null;
            try {

                String gammaltNamn = GetMetoder.hamtaCbSträng(gammaltNamnLåda);
                String gammalRas = GetMetoder.getRasFrånNamn(gammaltNamn);

//Först deklarerars alla variabler, text hämtas från fält och nödvändiga Stringvaribler konverteras till int.
                String ettIDString = id.getText();
                int ettID = Integer.parseInt(ettIDString);
                String ettDatum = datum.getText();
                ettNamn = namnFält.getText();
                String valdRas = rasLåda.getSelectedItem().toString();
                String mängdAtributString = attributFält.getText();
                int mängdAtribut = Integer.parseInt(mängdAtributString);
                String ettLösen = lösenFält.getText();
                String ettTelNr = telNrFält.getText();
                String enPlats = platsLåda.getSelectedItem().toString();
                String platsIDSträng = idb.fetchSingle("select Plats_ID from plats where Benamning = '" + enPlats + "'");
                int platsID = Integer.parseInt(platsIDSträng);
                String enAgent = agentLåda.getSelectedItem().toString();
                String agentIDSträng = idb.fetchSingle("select Agent_ID from agent where namn = '" + enAgent + "'");
                int agentID = Integer.parseInt(agentIDSträng);
                ArrayList<String> NamnLista = GetMetoder.getAlienNamn();

                if (gammaltNamn.equals(ettNamn)) {

                    idb.update("Update alien set Registreringsdatum ='" + ettDatum + "', Losenord = '" + ettLösen + "', Namn = '" + ettNamn + "', Telefon = '" + ettTelNr + "', Plats =" + platsID + ", Ansvarig_Agent =" + agentID + " where Alien_ID =" + ettID);

                    if (gammalRas.equals("Boglodite")) {
                        idb.delete("Delete from boglodite where Alien_ID =" + ettID);
                    }
                    if (gammalRas.equals("Squid")) {
                        idb.delete("Delete from squid where Alien_ID =" + ettID);
                    }

                    if (gammalRas.equals("Worm")) {
                        idb.delete("Delete from worm where Alien_ID =" + ettID);
                    }

                    if (valdRas.equals("Boglodite")) {
                        idb.insert("insert into boglodite values(" + ettID + "," + mängdAtribut + ")");
                    }
                    if (valdRas.equals("Squid")) {
                        idb.insert("insert into squid values(" + ettID + "," + mängdAtribut + ")");
                    }
                    if (valdRas.equals("Worm")) {
                        idb.insert("insert into worm values(" + ettID + ")");
                    }
                    JOptionPane.showMessageDialog(null, ettNamn + " är nu omregistrerad");
                    namnFält.setText("");
                    lösenFält.setText("");
                    telNrFält.setText("");
                    attributFält.setText("");
                    datum.setText("");
                    gammaltNamnLåda.addItem(ettNamn);
                } else if (!gammaltNamn.equals(ettNamn)) {

                    if (Validera.kollaOmvärdeFinnsIArrayList(NamnLista, ettNamn, "En alien vid namn " + ettNamn + " finns redan registerad")) {

                        idb.update("Update alien set Registreringsdatum ='" + ettDatum + "', Losenord = '" + ettLösen + "', Namn = '" + ettNamn + "', Telefon = '" + ettTelNr + "', Plats =" + platsID + ", Ansvarig_Agent =" + agentID + " where Alien_ID =" + ettID);

                        if (gammalRas.equals("Boglodite")) {
                            idb.delete("Delete from boglodite where Alien_ID =" + ettID);
                        }
                        if (gammalRas.equals("Squid")) {
                            idb.delete("Delete from squid where Alien_ID =" + ettID);
                        }

                        if (gammalRas.equals("Worm")) {
                            idb.delete("Delete from worm where Alien_ID =" + ettID);
                        }

                        if (valdRas.equals("Boglodite")) {
                            idb.insert("insert into boglodite values(" + ettID + "," + mängdAtribut + ")");
                        }
                        if (valdRas.equals("Squid")) {
                            idb.insert("insert into squid values(" + ettID + "," + mängdAtribut + ")");
                        }
                        if (valdRas.equals("Worm")) {
                            idb.insert("insert into worm values(" + ettID + ")");
                        }
                        JOptionPane.showMessageDialog(null, ettNamn + " är nu omregistrerad");
                        namnFält.setText("");
                        lösenFält.setText("");
                        telNrFält.setText("");
                        attributFält.setText("");
                        datum.setText("");
                        gammaltNamnLåda.removeItem(gammaltNamn);
                        gammaltNamnLåda.addItem(ettNamn);

                    }
                }

            } catch (InfException ex) {
                Logger.getLogger(MetoderAgentAdmin.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

    }

    /**
     * Metod som används för att visa inloggad agents utrustning
     * En HashMap används och informationen skrivs ut i en textarea
     * @param användarnamn
     * @param enArea
     */
    public static void listaMinUtrustning(String användarnamn, JTextArea enArea) {
        enArea.setText("");
        enArea.append("Benämning\t\tUtkvitteringsdatum\n");
        ArrayList<HashMap<String, String>> utrustningsLista = GetMetoder.getUtrustningsNamnfrånAgentnamn(användarnamn);
        for (HashMap<String, String> enUtrustning : utrustningsLista) {
            enArea.append(enUtrustning.get("Benamning") + "\t\t");
            enArea.append(enUtrustning.get("Utkvitteringsdatum") + "\n");
        }
    }

    /**
     * Metod som används för att visa inloggad agents fordon
     * En HashMap används och informationen skrivs ut i en textarea
     * @param användarnamn
     * @param enArea
     */
    public static void listaMinaFordon(String användarnamn, JTextArea enArea) {
        enArea.setText("");
        enArea.append("Fordonsbeskrivning\tÅrsmodell\tUtkvitteringsdatum\n");
        ArrayList<HashMap<String, String>> fordonsLista = GetMetoder.getFordonsNamnFrånAgentNamn(användarnamn);
        for (HashMap<String, String> ettFordon : fordonsLista) {

            enArea.append(ettFordon.get("Fordonsbeskrivning") + "\t");
            enArea.append(ettFordon.get("Arsmodell") + "\t");
            enArea.append(ettFordon.get("Utkvitteringsdatum") + "\n");

        }
    }
    
    /**
     * Metod som visar de agenter som ansvarar för flest aliens i valt område(top 3)
     * @param områdesLåda
     * @param enArea
     */
    public static void toppTreAnsvarigaAgenterValtOmråde(JComboBox områdesLåda, JTextArea enArea)
    {
        enArea.setText("");
        String valtOmråde = GetMetoder.hamtaCbSträng(områdesLåda);
        ArrayList<String> toppTre = GetMetoder.toppListaAnsvarPerOmråde(valtOmråde);
        for(String enAgent : toppTre)
        {
        enArea.append(enAgent+ "\n");    
        }
        
        
    }
}