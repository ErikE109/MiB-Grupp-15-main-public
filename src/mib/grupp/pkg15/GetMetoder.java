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
import oru.inf.InfDB;
import oru.inf.InfException;

/**
 *I denna klass finns metoder som returnerar olika värden, främst listor ifrån databasen.
 * Den tillkom inte direkt vid påbörjan av programbyggandet därför finns det även metoder som gör egna hämtningar också.
 * @author erike
 */
//Fälten för GetMetoder.
public class GetMetoder {

    private static InfDB idb;

//    Konstruktorn för GetMetoder.
    public GetMetoder() {
        idb = Main.getDB();
    }

    /**
     * Metod för att hämta ID från Agent med användarnamnet
     * som returneras som en int
     *
     * @param användarnamn
     * @return
     */
    public static int hämtaAgentIDFrånNamn(String användarnamn) {
        String agentID = "Finns ej";
        int agentNR = 99;
        try {
            agentID = idb.fetchSingle("Select Agent_ID from Agent where namn ='" + användarnamn + "'");
            agentNR = Integer.parseInt(agentID);

        } catch (InfException ex) {
            Logger.getLogger(MetoderAgentAdmin.class.getName()).log(Level.SEVERE, null, ex);
        }

        return agentNR;
    }

    /**
     * Get-metod för att hämta ID från Alien med användarnamnet
     * som returneras som en int
     *
     * @param användarnamn
     * @return
     */
    public static int hämtaAlienIDFrånNamn(String användarnamn) {
        String alienID = "Finns ej";
        int alienNR = 99;
        try {
            alienID = idb.fetchSingle("Select Alien_ID from Alien where namn ='" + användarnamn + "'");
            alienNR = Integer.parseInt(alienID);

        } catch (InfException ex) {
            Logger.getLogger(MetoderAgentAdmin.class.getName()).log(Level.SEVERE, null, ex);
        }

        return alienNR;
    }

    /**
     * Get-metod för att hämta ID från utrustning med det inmatade namnet
     * som returneras som en int
     *
     * @param benämning
     * @return
     */
    public static int hämtaUtrustningsIDFrånNamn(String benämning) {
        String utrustningsID = "Finns ej";
        int utrustningsNR = 99;

        try {
            utrustningsID = idb.fetchSingle("Select utrustnings_ID from utrustning where benamning ='" + benämning + "'");
            utrustningsNR = Integer.parseInt(utrustningsID);

        } catch (InfException ex) {
            Logger.getLogger(MetoderAgentAdmin.class.getName()).log(Level.SEVERE, null, ex);
        }
        return utrustningsNR;
    }

    /**
     * Get-metod för att hämta områdes-ID från namnet från vald landsdel
     * returneras som en int
     * @param benämning
     * @return
     */
    public static int hämtaOmrådesIDFrånNamn(String benämning) {
        String områdesID = "Finns ej";
        int områdesNR = 99;

        try {
            områdesID = idb.fetchSingle("select omrades_ID from omrade where benamning ='" + benämning + "'");
            områdesNR = Integer.parseInt(områdesID);
        } catch (InfException ex) {
            Logger.getLogger(GetMetoder.class.getName()).log(Level.SEVERE, null, ex);
        }
        return områdesNR;
    }

    /**
     * Get-metod för att hämta utrustnings-ID från agent med det inmatade
     * Agent-ID.
     *
     * @param agentID
     * @return
     */
    public static ArrayList<String> getUtrustningsIDnFrånAgentID(int agentID) {
        ArrayList<String> utrustningslista = null;
        try {
            utrustningslista = idb.fetchColumn("Select Utrustnings_ID from innehar_utrustning where agent_ID=" + agentID);
        } catch (InfException ex) {
            Logger.getLogger(MetoderAgentAdmin.class.getName()).log(Level.SEVERE, null, ex);
        }
        return utrustningslista;
    }
//

    /**
     * Get-metod för att ta ur rasen från en specifik alien, baserat på namn.
     * returnerar rasnamnet
     * 
     *
     * @param ettNamn
     * @return
     */
    public static String getRasFrånNamn(String ettNamn) {
        String ras = null;
        try {

            String boglodite = idb.fetchSingle("Select Namn from alien join boglodite b on alien.Alien_ID = b.Alien_ID where namn = '" + ettNamn + "'");
            String squid = idb.fetchSingle("Select Namn from alien join squid s on alien.Alien_ID = s.Alien_ID where namn = '" + ettNamn + "'");
            String worm = idb.fetchSingle("Select Namn from alien join worm w on alien.Alien_ID = w.Alien_ID where namn = '" + ettNamn + "'");

            // Om ett nullvärde returneras går metoden vidare till nästa ras tills något värde hittas
            if (Validera.kollaNullSträng(boglodite)) {
                ras = "Boglodite";
            }
            if (Validera.kollaNullSträng(squid)) {
                ras = "Squid";
            }
            if (Validera.kollaNullSträng(worm)) {
                ras = "Worm";
            }

        } catch (InfException ex) {
            Logger.getLogger(MetoderAgentAdmin.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ras;
    }

    /**
     * Hämtar ut senaste ID:t i Alienlistan och ökar denna till ett oanvänt ID.
     *
     * @return
     */
    public static String getNextAlienID() {
        String nextId = null;
        try {
            nextId = idb.getAutoIncrement("alien", "Alien_ID");

        } catch (InfException ex) {
            Logger.getLogger(MetoderAgentAdmin.class.getName()).log(Level.SEVERE, null, ex);
        }
              
        return nextId;
    }

    /**
     * Hämtar ut senaste Agent-ID:t från listan och ökar denna till ett oanvänt ID.
     *
     * @return
     */
    public static String getNextAgentID() {
        String nextId = null;
        try {
            nextId = idb.getAutoIncrement("agent", "Agent_ID");

        } catch (InfException ex) {
            Logger.getLogger(MetoderAgentAdmin.class.getName()).log(Level.SEVERE, null, ex);
        }
        return nextId;
    }

    /**
     * Hämtar ut senaste Utrustnings-ID:t i listan och ökar denna till ett oanvänt ID.
     *
     * @return
     */
    public static String getNextUtrustningsID() {
        String nextId = null;
        try {
            nextId = idb.getAutoIncrement("Utrustning", "Utrustnings_ID");

        } catch (InfException ex) {
            Logger.getLogger(MetoderUnikaAdmin.class.getName()).log(Level.SEVERE, null, ex);
        }
        return nextId;
    }

    public static HashMap<String, String> getEnAlien(String valdAlien) {
        HashMap<String, String> alienAvNamn = null;
        try {
            alienAvNamn = idb.fetchRow("select alien.Losenord, Alien_ID, alien.Namn, Registreringsdatum, alien.Telefon, Benamning, agent.Namn from alien join agent on alien.Ansvarig_Agent = agent.Agent_ID join plats on alien.Plats = plats.Plats_ID where alien.namn = '" + valdAlien + "'");
        } catch (InfException ex) {
            Logger.getLogger(GetMetoder.class.getName()).log(Level.SEVERE, null, ex);
        }
        return alienAvNamn;

    }

    /**
     * Get-metod för att få fram rasattributen från ett ID.
     *
     * @param ettID
     * @return
     */
    public static String getRasAttributFrånID(String ettID) {
        String mängdAttribut = null;
        try {
            //Hämtar antal armar eller boogies från lista berende på ras.
            String boogies = idb.fetchSingle("Select Antal_Boogies from boglodite where Alien_ID = '" + ettID + "'");
            String armar = idb.fetchSingle("Select Antal_Armar from squid where Alien_ID = '" + ettID + "'");
            //Om den inte hittar något är går detta vidare till nästa ras.
            if (Validera.kollaNullSträng(boogies)) {
                mängdAttribut = boogies;
            }
            if (Validera.kollaNullSträng(armar)) {
                mängdAttribut = armar;
            }

        } catch (InfException ex) {
            Logger.getLogger(MetoderAgentAdmin.class.getName()).log(Level.SEVERE, null, ex);
        }
        return mängdAttribut;
    }

    public static HashMap<String, String> getEnAgent(String valdAgent) {
        HashMap<String, String> agentAvNamn = null;
        try {
            agentAvNamn = idb.fetchRow("Select Agent_ID, Namn, Telefon, Anstallningsdatum, Administrator, Losenord, Benamning from agent join omrade on omrade = omrade.Omrades_ID where namn = '" + valdAgent + "'");
        } catch (InfException ex) {
            Logger.getLogger(GetMetoder.class.getName()).log(Level.SEVERE, null, ex);
        }
        return agentAvNamn;

    }

    /**
     * Returnerar en arraylist av alla agenters namn.
     *
     * @return
     */
    public static ArrayList<String> getAgentNamn() {
        ArrayList<String> NamnListaAgent = null;
        try {
            NamnListaAgent = idb.fetchColumn("Select namn from Agent order by namn");
        } catch (InfException ex) {
            Logger.getLogger(GetMetoder.class.getName()).log(Level.SEVERE, null, ex);
        }
        return NamnListaAgent;

    }

    /**
     * Returnerar en arraylist av alla aliens namn.
     *
     * @return
     */
    public static ArrayList<String> getAlienNamn() {
        ArrayList<String> NamnListaAlien = null;
        try {
            NamnListaAlien = idb.fetchColumn("Select namn from Alien order by namn");
        } catch (InfException ex) {
            Logger.getLogger(GetMetoder.class.getName()).log(Level.SEVERE, null, ex);
        }
        return NamnListaAlien;

    }

    /**
     * Returnerar en arraylist av alla utrustningars namn.
     *
     * @return
     */
    public static ArrayList<String> getUtrustningsNamn() {
        ArrayList<String> NamnListaUtrustning = null;
        try {
            NamnListaUtrustning = idb.fetchColumn("Select benamning from Utrustning order by benamning");
        } catch (InfException ex) {
            Logger.getLogger(GetMetoder.class.getName()).log(Level.SEVERE, null, ex);
        }
        return NamnListaUtrustning;
    }

    /**
     * Visar vilken utrustning en vald agent har på sig.
     *
     * @param agentNamn
     * @return
     */
    public static ArrayList<HashMap<String, String>> getUtrustningsNamnfrånAgentnamn(String agentNamn) {
        ArrayList<HashMap<String, String>> listan = null;
        try {
            listan = idb.fetchRows("select Benamning, Utkvitteringsdatum from utrustning join innehar_utrustning iu on utrustning.Utrustnings_ID = iu.Utrustnings_ID join agent a on iu.Agent_ID = a.Agent_ID where Namn = '" + agentNamn + "'");
        } catch (InfException ex) {
            Logger.getLogger(GetMetoder.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listan;

    }

    /**
     * Visar vilka fordon en vald agent har.
     *
     * @param agentNamn
     * @return
     */
    public static ArrayList<HashMap<String, String>> getFordonsNamnFrånAgentNamn(String agentNamn) {
        ArrayList<HashMap<String, String>> fordonsNamn = null;
        try {
            fordonsNamn = idb.fetchRows("select Fordonsbeskrivning, Arsmodell, Utkvitteringsdatum from fordon join innehar_fordon i on fordon.Fordons_ID = i.Fordons_ID join agent a on a.Agent_ID = i.Agent_ID where namn ='" + agentNamn + "'");
        } catch (InfException ex) {
            Logger.getLogger(GetMetoder.class.getName()).log(Level.SEVERE, null, ex);
        }

        return fordonsNamn;
    }

    /**
     * Hämtar och returnerar strängar från komboboxar.
     *
     * @param enLåda
     * @return
     */
    public static String hamtaCbSträng(JComboBox enLåda) {
        String cbSträng = enLåda.getSelectedItem().toString();
        return cbSträng;
    }

    /**
     * Hämtar en lista från databasen och returnerar namn områdeschefer.
     *
     * @return
     */
    public static ArrayList<String> hämtaNamnFrånOmrådesChefer() {
        ArrayList<String> agentLista = null;
        try {
            agentLista = idb.fetchColumn("select namn from agent join omradeschef on Agent.Agent_ID = Omradeschef.Agent_ID");
        } catch (InfException ex) {
            Logger.getLogger(GetMetoder.class.getName()).log(Level.SEVERE, null, ex);
        }
        return agentLista;
    }

    /**
     * Hämtar en lista ifrån databsen och returnerar namn på kontorschefer.
     *
     * @return
     */
    public static ArrayList<String> hämtaNamnFrånKontorsChefer() {
        ArrayList<String> agentLista = null;
        try {
            agentLista = idb.fetchColumn("select namn from agent join kontorschef k on agent.Agent_ID = k.Agent_ID");
        } catch (InfException ex) {
            Logger.getLogger(GetMetoder.class.getName()).log(Level.SEVERE, null, ex);
        }
        return agentLista;
    }
    
    /**
     *Hämtar en lista med alla agent_ID som finns i områdeschefer
     * och returnerar denna
     * @return
     */
    public static ArrayList<String> getAllaAgentIDFrånOC()
    {
        ArrayList<String> agentIDn = null;
        
        try {
            agentIDn = idb.fetchColumn("Select agent_ID from omradeschef");
                    } catch (InfException ex) {
            Logger.getLogger(GetMetoder.class.getName()).log(Level.SEVERE, null, ex);
        }
        return agentIDn;
    }

    /**
     * Hämtar en lista med områdes id ifrån Områdeschefer och returnerar listan.
     * 
     *
     * @return
     */
    public static ArrayList<String> getAllaOidFrånOC() {
        ArrayList<String> områdesIDfrånOC = null;
        try {
            områdesIDfrånOC = idb.fetchColumn("Select omrade from omradesChef");
        } catch (InfException ex) {
            Logger.getLogger(GetMetoder.class.getName()).log(Level.SEVERE, null, ex);
        }
        return områdesIDfrånOC;
    }

    /**
     * Hämtar en lista med namn och kontorsbeteckning ifrån tabellen kontorschefer
     * och returnerar denna.
     * 
     *
     * @return
     */
    public static ArrayList<HashMap<String, String>> hämtaKontorsChefer() {
        ArrayList<HashMap<String, String>> kontorsChefer = null;

        try {
            kontorsChefer = idb.fetchRows("select namn, kontorsbeteckning from agent join kontorschef k on agent.Agent_ID = k.Agent_ID");
        } catch (InfException ex) {
            Logger.getLogger(GetMetoder.class.getName()).log(Level.SEVERE, null, ex);
        }

        return kontorsChefer;
    }

    /**
     * Hämtar lista med namn och områdes benämning och returnerar denna
     * 
     *
     * @return
     */
    public static ArrayList<HashMap<String, String>> hämtaOmrådesChefer() {
        ArrayList<HashMap<String, String>> områdesChefer = null;

        try {
            områdesChefer = idb.fetchRows("select namn, Benamning from agent join omradeschef o on agent.Agent_ID = o.Agent_ID join omrade o2 on o.Omrade = o2.Omrades_ID");
        } catch (InfException ex) {
            Logger.getLogger(GetMetoder.class.getName()).log(Level.SEVERE, null, ex);
        }
        return områdesChefer;
    }

    /**
     * Hämtar ut senaste ifrån ID:t i listan och ökar denna till ett oanvänt ID.
     *
     * @return
     */
    public static String getNextFordonsID() {
        String nextId = null;
        try {
            nextId = idb.getAutoIncrement("Fordon", "Fordons_ID");

        } catch (InfException ex) {
            Logger.getLogger(MetoderUnikaAdmin.class.getName()).log(Level.SEVERE, null, ex);
        }
        return nextId;
    }

    /**
     * Hämtar och returnerar fordonsnamnen.
     *
     * @return
     */
    public static ArrayList<String> getFordonsNamn() {
        ArrayList<String> allaFordon = null;

        try {
            allaFordon = idb.fetchColumn("select Fordonsbeskrivning from fordon");
        } catch (InfException ex) {
            Logger.getLogger(GetMetoder.class.getName()).log(Level.SEVERE, null, ex);
        }
        return allaFordon;
    }

    /**
     * Hämtar och returnerar fordons-ID.
     *
     * @return
     */
    public static ArrayList<String> getFordonsID() {
        ArrayList<String> allaFordon = null;

        try {
            allaFordon = idb.fetchColumn("select Fordons_ID from fordon");
        } catch (InfException ex) {
            Logger.getLogger(GetMetoder.class.getName()).log(Level.SEVERE, null, ex);
        }
        return allaFordon;
    }

    /**
     * Hämtar och returnerar vilka aliens som finns inom ett visst område.
     *
     * @param ettOmråde
     * @return
     */
    public static ArrayList<String> hamtaAllaAliensfrånOmråde(String ettOmråde) {
        ArrayList<String> aliensIområde = null;

        try {
            aliensIområde = idb.fetchColumn("select namn from alien join plats p on alien.Plats = p.Plats_ID join omrade o on p.Finns_I = o.Omrades_ID where o.Benamning = '" + ettOmråde + "'");
        } catch (InfException ex) {
            Logger.getLogger(GetMetoder.class.getName()).log(Level.SEVERE, null, ex);
        }
        return aliensIområde;
    }

    /**
     * Hämtar och returnerar en alien inom ett visst område.
     *
     * @param användarnamn
     * @return
     */
    public static String hamtaEnAliensOmråde(String användarnamn) {
        String aliensOmråde = null;

        try {
            aliensOmråde = idb.fetchSingle("select omrade.Benamning from omrade join plats p on omrade.Omrades_ID = p.Finns_I join alien a on p.Plats_ID = a.Plats where namn ='" + användarnamn + "'");
        } catch (InfException ex) {
            Logger.getLogger(GetMetoder.class.getName()).log(Level.SEVERE, null, ex);
        }

        return aliensOmråde;
    }

    /**
     * Hämtar och returnerar vilka tre agenter som har hand om flest aliens.
     *
     * @param ettOmråde
     * @return
     */
    public static ArrayList<String> toppListaAnsvarPerOmråde(String ettOmråde) {
        ArrayList<String> topplista = null;

        try {
            topplista = idb.fetchColumn("select agent.namn, count(Agent_ID) from agent join alien on alien.Ansvarig_Agent = agent.Agent_ID join omrade o on agent.Omrade = o.Omrades_ID where Benamning = '" + ettOmråde + "' group by agent_ID order by count(Agent_ID) desc limit 3");
        } catch (InfException ex) {
            Logger.getLogger(GetMetoder.class.getName()).log(Level.SEVERE, null, ex);
        }
        return topplista;
    }

    /**
     *Hämtar agentID från aliens kolumnen ansvarig_agent
     * @return
     */
    public static ArrayList<String> hämtaAnsvarigaAgenter() {
        ArrayList<String> listaAnsvarigaAgenter = null;

        try {
           listaAnsvarigaAgenter = idb.fetchColumn("select agent.namn from agent join alien a on agent.Agent_ID = a.Ansvarig_Agent group by namn order by namn");
        } catch (InfException ex) {

            Logger.getLogger(GetMetoder.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listaAnsvarigaAgenter;
    }
    
    /**
     * Hämtar en lista på aliennamn på de aliens som har angiven AgentID som ansvarig agent
     * @param agentID
     * @return
     */
    public static ArrayList<String> hämtaAlienFrånAnsvarigAgent(int agentID)
    {
        ArrayList<String> alienSomAgentAnsvararFör = null;
        
        try {
            alienSomAgentAnsvararFör = idb.fetchColumn("select alien.namn from alien join agent a on alien.Ansvarig_Agent = a.Agent_ID where Agent_ID =" + agentID);
        } catch (InfException ex) {
            Logger.getLogger(GetMetoder.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return alienSomAgentAnsvararFör;
    }

}
