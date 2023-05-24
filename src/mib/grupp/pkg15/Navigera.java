/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mib.grupp.pkg15;

/**
 *
 * @author erike Används främst för att hantera vanliga navigeringsåtgärder,
 * såsom att stänga ner programmet, eller att återvända till StartSkärmen där
 * man loggar in
 *
 */
//Fälten för klassen Navigera.
public class Navigera {

    public Navigera() {

    }

    /**
     * Metod som skapar en instans av klassen StartSkärm och visar denna.
     */
    public static void openStartSkärm() {
        new StartSkärm().setVisible(true);
    }

    /**
     * Metod som skapar en instans av klassen AlienStartSkärm och visar denna.
     *
     * @param användarnamn
     */
    public static void openAlienStartSkärm(String användarnamn) {
        new AlienStartSkärm(användarnamn).setVisible(true);
    }

    /**
     * Metod som skapar en instans av klassen AgentStartSkärm och visar denna.
     *
     * @param användarnamn
     */
    public static void openAgentStartSkärm(String användarnamn) {
        new AgentStartSkärm(användarnamn).setVisible(true);
    }

    /**
     * Metod som öppnar en ny sida av AgentMinSIda.
     *
     * @param användarnamn
     */
    public static void openAgentMinSida(String användarnamn) {
        new AgentMinSida(användarnamn).setVisible(true);
    }

    /**
     * Metod som Avslutar programmet.
     */
    public static void avslutaProgram() {
        System.exit(0);
    }

    /**
     * Metod som öppnar en ny sida av AdminStartskärm
     *
     * @param användarnamn
     */
    public static void openAdminStartSkärm(String användarnamn) {
        new AdminStartSkärm(användarnamn).setVisible(true);
    }

    /**
     * Metod som öppnar en ny sida av AdminMinSida
     *
     * @param användarnamn
     */
    public static void openAdminMinSida(String användarnamn) {
        new AdminMinSida(användarnamn).setVisible(true);
    }

}
