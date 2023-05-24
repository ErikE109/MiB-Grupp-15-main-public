/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
 * @author Linda I denna klass finns metoder som används både i Agent- och
 * adminfönstren
 */
//Fälten för klassen UnikaAdminMetoder.
public class MetoderUnikaAdmin {

    private static InfDB idb;
    private static StartSkärm ettFönster;

    /**
     *
     * @param ettFönster Konstruktorn för klassen UnikaAdminMetoder.
     */
    public MetoderUnikaAdmin(StartSkärm ettFönster) {
        this.ettFönster = ettFönster;
        idb = Main.getDB();

    }
//Metoden för att logga in som Admin.

    /**
     *
     * @param användarnamnRuta
     * @param lösenruta
     */
    public static void loggainAdmin(JTextField användarnamnRuta, JPasswordField lösenruta) {
        if (Validera.kollaTom(användarnamnRuta) && Validera.kollaTom(lösenruta)) {
            try {
                //hämtar användarnamn ifrån loginruta
                String användarnamn = användarnamnRuta.getText();

                // hämta lösenordet som matchar angivet användarnamn ifrån databasen
                String lösenord = idb.fetchSingle("Select Losenord from agent where namn ='" + användarnamn + "'");
                // hämta adminstatus från databasen
                String adminstatus = idb.fetchSingle("Select Administrator from agent where namn = '" + användarnamn + "'");
                //jämför inskrivet lösen med det som står skrivet i rutan lösenord
                if (Validera.kollaNullvärde(lösenord) && Validera.kollaLösen(lösenord, lösenruta) && Validera.kollaAdmin(adminstatus)) {

                    //om ovan villkor är true skapas en ny ruta
                    new AdminStartSkärm(användarnamn).setVisible(true);
                    ettFönster.dispose();

                }

            } catch (InfException ex) {
                Logger.getLogger(StartSkärm.class.getName()).log(Level.SEVERE, null, ex);

            }
        }
    }

    /**
     *
     * @param lista
     * @param låda // Hämtar en hashmap och visar all information om varje
     * enskild agent i en textArea.
     */
    public static void listaEnskildAgent(JTextArea lista, JComboBox låda) {
        //Sätter textfältet som tomt
        lista.setText("");

        // hämtar variabler ifrån fälten
        String valdAgent = GetMetoder.hamtaCbSträng(låda);

        //HashMapen gås igenom. Först namnges rubriker, sedan hämtas data med hjälp av nyckeln som skrivs ut i listan.
        HashMap<String, String> agentAvNamn = GetMetoder.getEnAgent(valdAgent);
        lista.append("ID\tNamn\tTelefon\tOmrade\tAdmin\tAnsDatum\tLösenord\n");

        lista.append(agentAvNamn.get("Agent_ID") + "\t");
        lista.append(valdAgent + "\t");
        lista.append(agentAvNamn.get("Telefon") + "\t");
        lista.append(agentAvNamn.get("Benamning") + "\t");
        lista.append(agentAvNamn.get("Administrator") + "\t");
        lista.append(agentAvNamn.get("Anstallningsdatum") + "\t");
        lista.append(agentAvNamn.get("Losenord"));

    }

    /**
     *
     * @param användarnamn
     * @param gammaltlösen
     * @param nyttlösen
     *
     * Metod för att byta lösenord för Admin. Kontrollerar först så att det
     * gamla lösenordet stämmer överens med det som finns i Databasen Om detta
     * stämmer byts lösenordet ut till det nya.
     */
    public static void bytLösenord(String användarnamn, JPasswordField gammaltlösen, JPasswordField nyttlösen) {
        if (Validera.kollaTom(gammaltlösen) && Validera.kollaTom(nyttlösen))
        try {
            String lösenord = idb.fetchSingle("Select Losenord from AGENT where namn ='" + användarnamn + "'");
            if (Validera.kollaLösen(lösenord, gammaltlösen)) {
                String nyttLösenord = nyttlösen.getText();
                idb.update("UPDATE AGENT SET losenord='" + nyttLösenord + "' where namn ='" + användarnamn + "'");
                JOptionPane.showMessageDialog(null, "Lösenordet har ändrats!");
            }
        } catch (InfException ex) {
            Logger.getLogger(MetoderUnikaAdmin.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     *
     * @param id
     * @param namnFält
     * @param typLåda
     * @param attributFält Metod för att registrera ny utrustning, vi har valt
     * att varje utrustning måste ha unikt namn. Specialvalidering för vapen,
     * eftersom kaliberfältet endast tar ints.
     */
    public static void nyRegistreraUtrustning(JLabel id, JTextField namnFält, JComboBox typLåda, JTextField attributFält) {
        //Validering för samtliga fält görs så, om valideringen godkänns körs programmet
        if (Validera.kollaTom(namnFält) && Validera.kollaTom(attributFält) && Validera.kollaSträngBörjaStorBokstav(namnFält)) {

            String ettNamn = null;

            try {
//Först deklarerars alla variabler, text hämtas från fält och nödvändiga Stringvaribler konverteras till int
                String ettIDString = id.getText();
                int ettID = Integer.parseInt(ettIDString);
                ettNamn = namnFält.getText();
                String valdUtrustning = typLåda.getSelectedItem().toString();
                String Attribut = attributFält.getText();

                ArrayList<String> NamnLista = GetMetoder.getUtrustningsNamn();

                if (Validera.kollaOmvärdeFinnsIArrayList(NamnLista, ettNamn, "En utrustning vid namn " + ettNamn + " finns redan registerad")) {

                    if (valdUtrustning.equals("Vapen")) {
                        if (Validera.kollaIntVapen(attributFält)) {
                            int Kaliber = Integer.parseInt(Attribut);
                            idb.insert("insert into Vapen values(" + ettID + "," + Kaliber + ")");
                            idb.insert("insert into Utrustning values(" + ettID + ",'" + ettNamn + "')");
                            JOptionPane.showMessageDialog(null, ettNamn + " är nu registrerad");
                            id.setText(GetMetoder.getNextUtrustningsID());
                            namnFält.setText("");
                            attributFält.setText("");
                        }

                    }
                    if (valdUtrustning.equals("Kommunikation")) {
//                        validering krävs
                        idb.insert("insert into Kommunikation values(" + ettID + ",'" + Attribut + "')");
                        idb.insert("insert into Utrustning values(" + ettID + ",'" + ettNamn + "')");
                        JOptionPane.showMessageDialog(null, ettNamn + " är nu registrerad");
                        id.setText(GetMetoder.getNextUtrustningsID());
                        namnFält.setText("");
                        attributFält.setText("");
                    }
                    if (valdUtrustning.equals("Teknik")) {
                        //validering krävs
                        idb.insert("insert into Teknik values(" + ettID + ",'" + Attribut + "')");
                        idb.insert("insert into Utrustning values(" + ettID + ",'" + ettNamn + "')");
                        JOptionPane.showMessageDialog(null, ettNamn + " är nu registrerad");
                        id.setText(GetMetoder.getNextUtrustningsID());
                        namnFält.setText("");
                        attributFält.setText("");
                    }

                }

            } catch (InfException ex) {
                Logger.getLogger(MetoderUnikaAdmin.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

    /**
     *
     * @param enLåda Metod för att ta bort vald utrustning ur systemet. Tas bort
     * i korrekt ordning så att inga fel kastas ifrån databasen När en
     * utrustning tagits bort försvinner den även ur comboboxen
     */
    public static void taBortUtrustningUrSystemet(JComboBox enLåda) {
        try {
            String valdUtrustning = GetMetoder.hamtaCbSträng(enLåda);
            int utrustningsID = GetMetoder.hämtaUtrustningsIDFrånNamn(valdUtrustning);
            int val = JOptionPane.showConfirmDialog(null, "Vill du verkligen ta bort " + valdUtrustning);
            if (val == JOptionPane.YES_OPTION) {
                idb.delete("delete from innehar_utrustning where Utrustnings_ID =" + utrustningsID);
                idb.delete("delete from vapen where Utrustnings_ID =" + utrustningsID);
                idb.delete("delete from kommunikation where Utrustnings_ID =" + utrustningsID);
                idb.delete("delete from teknik where Utrustnings_ID =" + utrustningsID);
                idb.delete("delete from utrustning where Utrustnings_ID =" + utrustningsID);
                JOptionPane.showMessageDialog(null, "Du har tagit bort " + valdUtrustning + " ur systemet");
                enLåda.removeItem(valdUtrustning);
            }

        } catch (InfException ex) {
            Logger.getLogger(MetoderUnikaAdmin.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Hämtar agentID från ett namn och tar bort vald agent ur systemet, detta
     * görs i korrekt ordning så att inga fel kastas tar även bort vald agent ur
     * comboboxen när det är borta ur systemet Om agenten är ansvarig för någon
     * kommer felmeddelande upp.
     *
     * @param enLåda
     *
     */
    public static void taBortAgentUrSystemet(JComboBox enLåda) {
        String valdAgent = GetMetoder.hamtaCbSträng(enLåda);
        ArrayList<String> ansvarigaAgenter = GetMetoder.hämtaAnsvarigaAgenter();
        int AgentID = GetMetoder.hämtaAgentIDFrånNamn(valdAgent);
        String agentIDsomSträng = Integer.toString(AgentID);
        if (Validera.kollaArrayListContains(ansvarigaAgenter, agentIDsomSträng, "Kan inte ta bort vald agent.\n" + valdAgent + " har alienansvar.\nVänligen ändra ansvarig agent under 'Hantera aliens'.")) {
            int val = JOptionPane.showConfirmDialog(null, "Vill du verkligen ta bort " + valdAgent);
            if (val == JOptionPane.YES_OPTION) {

                try {

                    idb.delete("delete from innehar_fordon where Agent_ID =" + AgentID);
                    idb.delete("delete from innehar_utrustning where Agent_ID =" + AgentID);
                    idb.delete("delete from omradeschef where agent_id =" + AgentID);
                    idb.delete("delete from kontorschef where agent_id =" + AgentID);
                    idb.delete("delete from faltagent where agent_id =" + AgentID);
                    idb.delete("delete from agent where agent_id =" + AgentID);
                    JOptionPane.showMessageDialog(null, "Du har tagit bort " + valdAgent + " från systemet");
                    enLåda.removeItem(valdAgent);

                } catch (InfException ex) {
                    Logger.getLogger(MetoderUnikaAdmin.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    /**
     * Hämtar alien_ID och gör om till int från aliennamnet och tar bort vald
     * alien från systemet Namnet tas även bort ifrån comboboxen
     *
     * @param enLåda
     */
    public static void taBortAlienUrSystemet(JComboBox enLåda) {
        String valdAlien = GetMetoder.hamtaCbSträng(enLåda);
        int val = JOptionPane.showConfirmDialog(null, "Vill du verkligen ta bort " + valdAlien);
            if (val == JOptionPane.YES_OPTION)
            {
                
            
        try {
            int AlienID = GetMetoder.hämtaAlienIDFrånNamn(valdAlien);
            idb.delete("delete from boglodite where alien_id =" + AlienID);
            idb.delete("delete from worm where alien_id =" + AlienID);
            idb.delete("delete from squid where alien_id =" + AlienID);
            idb.delete("delete from alien where alien_id =" + AlienID);
            JOptionPane.showMessageDialog(null, "Du har tagit bort " + valdAlien + " ur systemet");
            enLåda.removeItem(valdAlien);
        } catch (InfException ex) {
            Logger.getLogger(MetoderUnikaAdmin.class.getName()).log(Level.SEVERE, null, ex);
        }
            }
    }

    /**
     * Metod för nyregistrering av agent
     *
     * @param id
     * @param datum
     * @param namnFält
     * @param lösenFält
     * @param telNrFält
     * @param adminLåda
     * @param områdesLåda
     */
    public static void nyRegistreraAgent(JLabel id, JTextField datum, JTextField namnFält, JPasswordField lösenFält, JTextField telNrFält, JComboBox adminLåda, JComboBox områdesLåda) {
        //Validering för samtliga fält görs så, om valideringen godkänns körs programmet
        if (Validera.kollaTom(namnFält) && Validera.kollaTom(lösenFält) && Validera.kollaTom(telNrFält) && Validera.kollaTelefonnummer(telNrFält) && Validera.kollaLängdLösenord(lösenFält) && Validera.kollaDatumFormat(datum)) {

            try {
//Först deklarerars alla variabler, text hämtas från fält och lådor och nödvändiga Stringvaribler konverteras till int
                String ettIDString = id.getText();
                int ettID = Integer.parseInt(ettIDString);
                String ettDatum = datum.getText();
                String ettNamn = "Agent " + namnFält.getText();
                String ettLösen = lösenFält.getText();
                String ettTelNr = telNrFält.getText();
                String adminStatus = GetMetoder.hamtaCbSträng(adminLåda);
                String ettOmråde = GetMetoder.hamtaCbSträng(områdesLåda);
                String omRådesIDSträng = idb.fetchSingle("select Omrades_ID from omrade where Benamning = '" + ettOmråde + "'");
                int områdesID = Integer.parseInt(omRådesIDSträng);

                ArrayList<String> NamnLista = GetMetoder.getAgentNamn();
                String felMeddelande = "En Agent vid namn " + ettNamn + " finns redan registrerad.";

                if (Validera.kollaOmvärdeFinnsIArrayList(NamnLista, ettNamn, felMeddelande) && Validera.kontrolleraAgentNamn(namnFält)) {
// Om alla valideringen fungerar läggs all data in i agenttabell och meddelande kommer upp.
                    idb.insert("insert into agent values(" + ettID + ",'" + ettNamn + "','" + ettTelNr + "','" + ettDatum + "','" + adminStatus + "','" + ettLösen + "'," + områdesID + ")");

                    JOptionPane.showMessageDialog(null, ettNamn + " är nu registrerad");
                    // Slutligen sätts alla fält som tomma och nästa ID hämtas ifrån databasen
                    id.setText(GetMetoder.getNextAgentID());
                    namnFält.setText("");
                    lösenFält.setText("");
                    telNrFält.setText("");

                }

            } catch (InfException ex) {
                Logger.getLogger(MetoderAgentAdmin.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

    }

    /**
     * Denna metod lägger in all information om vald agent i en liknade skärm
     * som nyregstringens skärmen En HashMap hämtas och data läggs i respektive
     * textfield.
     *
     *
     * @param valdAgentFält
     * @param IDFält
     * @param NamnFält
     * @param datumFält
     * @param TeleFält
     * @param LösenFält
     * @param adminFält
     * @param områdesFält
     *
     */
    public static void visaInformationAgent(JComboBox valdAgentFält, JLabel IDFält, JTextField NamnFält, JTextField datumFält, JTextField TeleFält, JTextField LösenFält, JComboBox adminFält, JComboBox områdesFält) {
        String valdAgent = GetMetoder.hamtaCbSträng(valdAgentFält);
        HashMap<String, String> agentAvNamn = GetMetoder.getEnAgent(valdAgent);

        IDFält.setText(agentAvNamn.get("Agent_ID"));
        datumFält.setText(agentAvNamn.get("Anstallningsdatum"));
        NamnFält.setText(valdAgent.substring(6));
        LösenFält.setText(agentAvNamn.get("Losenord"));
        TeleFält.setText(agentAvNamn.get("Telefon"));
        adminFält.setSelectedItem(agentAvNamn.get("Administrator"));
        områdesFält.setSelectedItem(agentAvNamn.get("Benamning"));

    }

    /**
     * Denna metod fungerar på liknande sätt som nyregistering av agent. Men kör
     * en set istället för insert into statement till databasen
     *
     *
     *
     * @param valdAgentFält
     * @param id
     * @param namnFält
     * @param datumFält
     * @param telNrFält
     * @param lösenFält
     * @param adminLåda
     * @param områdesLåda
     */
    public static void ändraAgent(JComboBox valdAgentFält, JLabel id, JTextField namnFält, JTextField datumFält, JTextField telNrFält, JTextField lösenFält, JComboBox adminLåda, JComboBox områdesLåda) {
        //Validering för samtliga fält görs så, om valideringen godkänns körs programmet
        if (Validera.kollaTom(namnFält) && Validera.kollaTom(lösenFält) && Validera.kollaTom(telNrFält) && Validera.kollaTelefonnummer(telNrFält) && Validera.kollaLängdLösenord(lösenFält) && Validera.kollaDatumFormat(datumFält)) {

            try {
//Först deklarerars alla variabler, text hämtas från fält och lådar och nödvändiga Stringvaribler konverteras till int
                String gammaltNamn = GetMetoder.hamtaCbSträng(valdAgentFält);
                String ettIDString = id.getText();
                int ettID = Integer.parseInt(ettIDString);
                String ettDatum = datumFält.getText();
                String agentBokstav = namnFält.getText();
                String ettNamn = "Agent " + agentBokstav;
                String ettLösen = lösenFält.getText();
                String ettTelNr = telNrFält.getText();
                String adminStatus = GetMetoder.hamtaCbSträng(adminLåda);
                String ettOmråde = GetMetoder.hamtaCbSträng(områdesLåda);
                String omRådesIDSträng = idb.fetchSingle("select Omrades_ID from omrade where Benamning = '" + ettOmråde + "'");
                int områdesID = Integer.parseInt(omRådesIDSträng);
                ArrayList<String> NamnLista = GetMetoder.getAgentNamn();
                String felMeddelande = "En Agent vid namn " + ettNamn + " finns redan registrerad.";

                // validering görs om ett nytt namn har angivits eller ej.
                // Om inget nytt namn angivits updateras alla värden.
                if (gammaltNamn.equals(ettNamn)) {
                    idb.update("Update agent set Namn ='" + ettNamn + "', Telefon = '" + ettTelNr + "', Anstallningsdatum = '" + ettDatum + "', Administrator='" + adminStatus + "', Losenord ='" + ettLösen + "', Omrade=" + områdesID + " where Agent_ID =" + ettID);

                    JOptionPane.showMessageDialog(null, gammaltNamn + " är nu omregistrerad");
                    namnFält.setText("");
                    lösenFält.setText("");
                    telNrFält.setText("");

                } else if (!gammaltNamn.equals(ettNamn)) {

                    if (Validera.kollaOmvärdeFinnsIArrayList(NamnLista, ettNamn, felMeddelande) && Validera.kontrolleraAgentNamn(namnFält)) {
// Om ett nytt namn angivits görs en kontroll om samma namn finns i tabellen.
// Om inte uppdateras tabellen, annars  kommer ett felmeddelande upp.
                        idb.update("Update agent set Namn ='" + ettNamn + "', Telefon = '" + ettTelNr + "', Anstallningsdatum = '" + ettDatum + "', Administrator='" + adminStatus + "', Losenord ='" + ettLösen + "', Omrade=" + områdesID + " Where agent_ID =" + ettID);

                        JOptionPane.showMessageDialog(null, gammaltNamn + " är nu omregistrerad");
                        valdAgentFält.removeItem(gammaltNamn);
                        valdAgentFält.addItem(ettNamn);

                    }
                }

            } catch (InfException ex) {
                Logger.getLogger(MetoderAgentAdmin.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * Metod för att lägga till utrustning på vald Agent.
     *
     * @param utrustningsLåda
     * @param agentLÅda
     */
    public static void laggTillUtrustningPåValdAgent(JComboBox utrustningsLåda, JComboBox agentLÅda) {
        //Deklarerar felmeddelande som ska användas i om utrustning redan finns på agenten
        String agentNamn = GetMetoder.hamtaCbSträng(agentLÅda);

        //Hämtar vald utrustning ifrån en Combobox och gör om String variabler till integers
        String valdUtrustning = GetMetoder.hamtaCbSträng(utrustningsLåda);
        int utrustningsID = GetMetoder.hämtaUtrustningsIDFrånNamn(valdUtrustning);
        int agentID = GetMetoder.hämtaAgentIDFrånNamn(agentNamn);
        //Här görs IDt om till strängar för att kunna jämföra om agenten har utrustningen registrerad på sig eller inte
        String utrustningsIDSomSträng = Integer.toString(utrustningsID);
        String dagensDatum = DatumHanterare.getDagensDatum();

        String felMeddlandeHarUtrustningen = "Denna utrustning är redan registrerad på " + agentNamn;
        if (Validera.kollaOmvärdeFinnsIArrayList(GetMetoder.getUtrustningsIDnFrånAgentID(agentID), utrustningsIDSomSträng, felMeddlandeHarUtrustningen)) {
// om Valideringen godkänns registreras den nya utrustningen på agenten
            try {
                idb.insert("Insert into Innehar_Utrustning values(" + agentID + "," + utrustningsID + ",'" + dagensDatum + "')");
                JOptionPane.showMessageDialog(null, "Du har lagt till " + valdUtrustning + " på " + agentNamn + "s lista!");

            } catch (InfException ex) {
                Logger.getLogger(MetoderAgentAdmin.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

    /**
     * Metod för att göra vald agent till kontorschef
     *
     * @param plats
     * @param valdAgent
     */
    public static void laggTillKontorsChef(JComboBox plats, JComboBox valdAgent) {
        try {
            String enAgent = GetMetoder.hamtaCbSträng(valdAgent);
            String enPlats = GetMetoder.hamtaCbSträng(plats);
            String ettKontor = enPlats + "kontoret";
            //Hämtar värdena i från boxarna

            ArrayList<String> agentLista = idb.fetchColumn("select namn from agent join kontorschef k on agent.Agent_ID = k.Agent_ID");
            String ettMeddelande = (enAgent + " ansvarar redan för ett kontor");
            ArrayList<String> kontorsLista = idb.fetchColumn("select kontorsbeteckning from kontorschef");
            //Hämtar lista över alla kontorschefer och en lista över alla kontor som finns

            int agentID = GetMetoder.hämtaAgentIDFrånNamn(enAgent);

            //Här görs två kontroller, dels om kontoret redan har en kontorschef
            //dels en kontroll görs om agenten redan är kontorschef
            //Om något av dessa stämmer kommer felmeddelanden upp
            if (Validera.kollaOmvärdeFinnsIArrayList(agentLista, enAgent, ettMeddelande) && Validera.kollaOmvärdeFinnsIArrayList(kontorsLista, ettKontor, ettKontor + " har redan en chef")) {
                idb.insert("insert into kontorschef values(" + agentID + ", '" + ettKontor + "')");
                JOptionPane.showMessageDialog(null, "Du har lagt till '" + enAgent + "' till kontoret '" + ettKontor + "'");

            }
        } catch (InfException ex) {
            Logger.getLogger(MetoderUnikaAdmin.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Metod för att ta bort en kontorschef från valt kontor.
     *
     * @param valdAgent
     */
    public static void taBortKontorsChef(JComboBox valdAgent) {
        try {
            String enAgent = GetMetoder.hamtaCbSträng(valdAgent);
            ArrayList<String> agentLista = idb.fetchColumn("select namn from agent join kontorschef k on agent.Agent_ID = k.Agent_ID");
            //Hämtar en namnlista på alla kontorschefer
            String ettMeddelande = (enAgent + " ansvarar inte för något kontor");
            int agentID = GetMetoder.hämtaAgentIDFrånNamn(enAgent);

            if (!agentLista.contains(enAgent)) {
                JOptionPane.showMessageDialog(null, ettMeddelande);
//Här görs en kontroll om vald agent är kontorschef eller ej. Är den inte det kommer felmeddelande upp.
//Annars tas agent bort från kontoret
            } else {
                idb.delete("delete from kontorschef where agent_ID =" + agentID);
                JOptionPane.showMessageDialog(null, enAgent + " är inte längre kontorschef!");
            }
        } catch (InfException ex) {
            Logger.getLogger(MetoderUnikaAdmin.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Lägger vald agent som områdeschef för valt område.
     *
     * @param valdAgent
     * @param områdesLåda
     */
    public static void laggTillOmrådesChef(JComboBox valdAgent, JComboBox områdesLåda) {

        String enAgent = GetMetoder.hamtaCbSträng(valdAgent);
        String ettOmråde = GetMetoder.hamtaCbSträng(områdesLåda);
        ArrayList<String> agentLista = GetMetoder.getAllaAgentIDFrånOC();
        ArrayList<String> omradesIDn = GetMetoder.getAllaOidFrånOC();
        //Hämtar listor som gås igenom för att konttrollera om området har en chef eller om agenten redan är chef
        String ettMeddelande = (enAgent + " ansvarar redan för ett område");
        int agentID = GetMetoder.hämtaAgentIDFrånNamn(enAgent);
        int områdesID = GetMetoder.hämtaOmrådesIDFrånNamn(ettOmråde);
        //Agentnamnet och områdesbenämningen görs om till int för att kunna läggas in i tabellen
        String områdesIDSträng = Integer.toString(områdesID);
        String agentIDsomSträng = Integer.toString(agentID);

        if (Validera.kollaOmvärdeFinnsIArrayList(agentLista, agentIDsomSträng, ettMeddelande) && Validera.kollaOmvärdeFinnsIArrayList(omradesIDn, områdesIDSträng, "Området " + ettOmråde + " har redan en chef")) {
            //Här valideras listora gentemot agentnamnet och områdesbenämningen
            try {
                idb.insert("insert into omradeschef values(" + agentID + ",'" + områdesID + "')");
            } catch (InfException ex) {
                Logger.getLogger(MetoderUnikaAdmin.class.getName()).log(Level.SEVERE, null, ex);
            }

            JOptionPane.showMessageDialog(null, "Du har lagt till " + enAgent + " är nu områdeschef över " + ettOmråde);
        }
    }

    /**
     * Denna metod tar bort vald områdeschef från tabellen.
     *
     * @param valdAgent
     *
     */
    public static void taBortOmrådesChef(JComboBox valdAgent) {
        String enAgent = GetMetoder.hamtaCbSträng(valdAgent);
        int agentID = GetMetoder.hämtaAgentIDFrånNamn(enAgent);
        try {
            idb.delete("delete from omradeschef where agent_ID =" + agentID);
            JOptionPane.showMessageDialog(null, enAgent + " är inte längre områdeschef");
        } catch (InfException ex) {
            Logger.getLogger(MetoderUnikaAdmin.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * Denna metod listar den utrustningen samt utkvitterings datum som vald
     * agent har utkvitterat
     *
     * @param namnLåda
     * @param enArea
     */
    public static void listaAgentsUtrustning(JComboBox namnLåda, JTextArea enArea) {
        String agentNamn = GetMetoder.hamtaCbSträng(namnLåda);
        enArea.setText("");
        enArea.append("Benämning\tUtkvitteringsdatum\n");
        ArrayList<HashMap<String, String>> utrustningsLista = GetMetoder.getUtrustningsNamnfrånAgentnamn(agentNamn);
        for (HashMap<String, String> enUtrustning : utrustningsLista) {
            enArea.append(enUtrustning.get("Benamning") + "\t");
            enArea.append(enUtrustning.get("Utkvitteringsdatum") + "\n");
        }
    }

    /**
     * Denna metod listar information om det fordon en agent har kvitterat
     *
     * @param namnLåda
     * @param enArea
     */
    public static void listaAgentsFordon(JComboBox namnLåda, JTextArea enArea) {
        String agentNamn = GetMetoder.hamtaCbSträng(namnLåda);
        enArea.setText("");
        enArea.append("Regnummer\tBeskrivning\t\tÅrsmodell\tUtkvitteringsdatum\n");
        ArrayList<HashMap<String, String>> fordonsLista = GetMetoder.getFordonsNamnFrånAgentNamn(agentNamn);
        for (HashMap<String, String> ettFordon : fordonsLista) {

            enArea.append(ettFordon.get("Fordons_ID") + "\t\t");
            enArea.append(ettFordon.get("Fordonsbeskrivning") + "\t\t");
            enArea.append(ettFordon.get("Arsmodell") + "\t");
            enArea.append(ettFordon.get("Utkvitteringsdatum") + "\n");

        }
    }

    /**
     * Metod för att nyregistrera fordon.
     *
     * @param idFält
     * @param beskrivningsFält
     * @param regFält
     * @param årsmodellsFält
     */
    public static void nyRegistreraFordon(JTextField idFält, JTextField beskrivningsFält, JTextField regFält, JTextField årsmodellsFält) {
        //Validering för samtliga fält görs så, om valideringen godkänns körs programmet
        if (Validera.kollaTom(beskrivningsFält) && Validera.kollaTom(regFält) && Validera.kollaTom(årsmodellsFält) && Validera.kollaTom(idFält) && Validera.kollaSträngBörjaStorBokstav(beskrivningsFält) && Validera.kollaDatumFormat(regFält) && Validera.kollaRegNummer(idFält) && Validera.kollaIntÅrsModell(årsmodellsFält)) {

//Först deklarerars alla variabler, text hämtas från fält och nödvändiga Stringvaribler konverteras till int
            String ettID = idFält.getText();
            String fordonsBeskrivning = beskrivningsFält.getText();
            String regDatum = regFält.getText();
            String årsModellsomSträng = årsmodellsFält.getText();
            int årsModell = Integer.parseInt(årsModellsomSträng);

            ArrayList<String> NamnLista = GetMetoder.getFordonsNamn();
            ArrayList<String> regNrLista = GetMetoder.getFordonsID();

            if (Validera.kollaOmvärdeFinnsIArrayList(regNrLista, ettID, "Ett fordon med registreringsnummer " + ettID + " finns redan registrerat i systemet") && Validera.kollaOmvärdeFinnsIArrayList(NamnLista, fordonsBeskrivning, "Ett fordon med detta namn finns redan i listan")) {
// Här koller vi om regnummer och fordonsnamn redan finns i databasen vilket inte accepteras.
// Olika felmeddeladne skrivs ut beroende på likhet
                try {

                    idb.insert("INSERT INTO fordon values('" + ettID + "','" + fordonsBeskrivning + "', '" + regDatum + "' ," + årsModell + ")");

                } catch (InfException ex) {
                    Logger.getLogger(MetoderUnikaAdmin.class.getName()).log(Level.SEVERE, null, ex);
                }
                JOptionPane.showMessageDialog(null, "Fordonet " + fordonsBeskrivning + " med registreringsnummer " + ettID + " är nu registrat i systemet");

                idFält.setText("");
                beskrivningsFält.setText("");
                regFält.setText(DatumHanterare.getDagensDatum());
                årsmodellsFält.setText("");

            }

        }
    }

    /**
     * Metod för att ta bort fordon ur systemet
     *
     * @param enLåda
     */
    public static void taBortFordon(JComboBox enLåda) {
        String valtFordon = GetMetoder.hamtaCbSträng(enLåda);
        try {

            idb.delete("Delete from innehar_fordon where Fordons_ID =" + valtFordon);
            idb.delete("Delete from fordon where Fordons_ID =" + valtFordon);
            JOptionPane.showConfirmDialog(null, "Du har nu tagit bort fordonet med registreringsnummer " + valtFordon + " från systemet!");
        } catch (InfException ex) {
            Logger.getLogger(MetoderUnikaAdmin.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * Denna metod listar aliens som vald agent är ansvarig för.
     *
     * @param enLåda
     * @param enArea
     */
    public static void visaAgentAnsvar(JComboBox enLåda, JTextArea enArea) {
        enArea.setText("");
        String enAgent = GetMetoder.hamtaCbSträng(enLåda);
        int agentID = GetMetoder.hämtaAgentIDFrånNamn(enAgent);

        ArrayList<String> aliensSomagentAnsvararFör = GetMetoder.hämtaAlienFrånAnsvarigAgent(agentID);
        for (String enAlien : aliensSomagentAnsvararFör) {
            enArea.append(enAlien + "\n");
        }
    }

}
