/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.gsb.rv.dr.technique;

import fr.gsb.rv.dr.entites.Visiteur;

/**
 *
 * @author etudiant
 */
public class Session {

    private static Session session = null;
    private Visiteur leVisiteur;

    private Session(Visiteur leVisiteur) {
        this.leVisiteur = leVisiteur;
    }

    
    
    public static void ouvrir(Visiteur leVisiteur) {
        session = new Session(leVisiteur);
    }

    public static void fermer() {
        session = null;
    }

    public static Session getSession() {
        return session;
    }

    public Visiteur getLeVisiteur() {
        return this.leVisiteur;
    }

    public static boolean estOuverte() {
        return (session != null) ? true : false;
    }

}
