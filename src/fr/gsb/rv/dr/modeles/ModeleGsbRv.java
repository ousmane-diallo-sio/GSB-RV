/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.gsb.rv.dr.modeles;
import fr.gsb.rv.dr.entites.Praticien;
import fr.gsb.rv.dr.entites.RapportVisite;
import fr.gsb.rv.dr.entites.Visiteur;
import fr.gsb.rv.dr.technique.ConnexionBD;
import fr.gsb.rv.dr.technique.ConnexionException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ModeleGsbRv {
    
    public static Visiteur seConnecter( String matricule , String mdp ) throws ConnexionException{
        Connection connexion = ConnexionBD.getConnexion() ;
        
        String requete = "select vis_nom, vis_prenom from Travailler as t" 
                +" inner join (select tra_role,vis_matricule,max(jjmmaa) as jjmmaa  from Travailler group by vis_matricule)  as s" 
                +" inner join Visiteur as V on s.vis_matricule = t.vis_matricule and t.jjmmaa = s.jjmmaa and V.vis_matricule = t.vis_matricule "
                +" where t.tra_role = 'Délégué' and V.vis_matricule = ? and V.vis_mdp = ? ;";
        
        try {
            PreparedStatement requetePreparee = (PreparedStatement) connexion.prepareStatement( requete ) ;
            requetePreparee.setString( 1 , matricule );
            requetePreparee.setString( 2 , mdp );
            ResultSet resultat = requetePreparee.executeQuery() ;
            if( resultat.next() ){
                Visiteur visiteur = new Visiteur();
                visiteur.setMatricule( matricule );
                visiteur.setNom( resultat.getString( "vis_nom" ) ) ;
                visiteur.setPrenom( resultat.getString( "vis_prenom" ) );
                
                requetePreparee.close() ;
                return visiteur ;
            }
            else {
                return null ;
            }
        }
        catch( Exception e ){
            return null ;
        } 
    }
    
    public static List<Praticien> getPraticiensHesitants() throws ConnexionException{   
        Connection connexion = ConnexionBD.getConnexion();

        String requete = "select * from Praticien p inner join RapportVisite r on p.pra_num = r.pra_num where rap_coefconfiance != 5 ;";
        
        List<Praticien> praticiens = new ArrayList<>();
        
        try {    
            PreparedStatement requetePreparee = (PreparedStatement) connexion.prepareStatement(requete);
            ResultSet resultat = requetePreparee.executeQuery();
            while(resultat.next()){
                Praticien praticien = new Praticien( 
                        resultat.getInt("pra_num"), 
                        resultat.getString("pra_nom"), 
                        resultat.getString("pra_ville"),
                        resultat.getDouble("pra_coefnotoriete"),
                        resultat.getDate("rap_date_visite").toLocalDate(),
                        resultat.getInt("rap_coefconfiance"),
                        resultat.getString("pra_adresse"),
                        resultat.getString("pra_cp"),
                        resultat.getString("pra_prenom")
                );
                praticiens.add(praticien);
            }
            requetePreparee.close();          
        } catch (Exception e) {
            System.err.println("ModeleGsbRv::getPraticiensHesitants() : " + e);
        }
        //System.out.println("ModeleGsbRv::getPraticiensHesitants() : " + praticiens.toString());
        return praticiens;       
    }
    
    public static List<Visiteur> getVisiteurs() throws ConnexionException{
        Connection connexion = ConnexionBD.getConnexion();
        String requete = "select vis_matricule, vis_nom, vis_prenom from Visiteur;";
        
        List<Visiteur> visiteurs = new ArrayList<>();
        try {    
            PreparedStatement requetePreparee = (PreparedStatement) connexion.prepareStatement(requete);
            ResultSet resultat = requetePreparee.executeQuery();
            while(resultat.next()){
                Visiteur visiteur = new Visiteur( 
                    resultat.getString("vis_matricule"),
                    resultat.getString("vis_nom"),
                    resultat.getString("vis_prenom")
                );
                visiteurs.add(visiteur);
            }
            requetePreparee.close();          
        } catch (Exception e) {
            System.err.println("ModeleGsbRv::getPraticiensHesitants() : " + e);
        }
        //System.out.println("ModeleGsbRv::getVisteurs()" + visiteurs.toString());
        return visiteurs;
    }
    
    public static List<RapportVisite> getRapportVisite(String matricule, int mois, int annee) throws ConnexionException{
        Connection connexion = ConnexionBD.getConnexion();
        String requete = "select * from RapportVisite where vis_matricule = ? and year(rap_date_visite) = ? and month(rap_date_visite) = ? ";
        
        List<RapportVisite> rapports = new ArrayList<>();
        try {    
            PreparedStatement requetePreparee = (PreparedStatement) connexion.prepareStatement(requete);
            requetePreparee.setString(1, matricule);
            requetePreparee.setString(2, Integer.toString(annee));
            requetePreparee.setString(3, Integer.toString(mois));
            ResultSet resultat = requetePreparee.executeQuery();
            while(resultat.next()){
                RapportVisite rapport = new RapportVisite( 
                        resultat.getInt("rap_num"),
                        resultat.getDate("rap_date_visite").toLocalDate(),
                        resultat.getDate("rap_date_saisie").toLocalDate(),
                        resultat.getString("rap_bilan"),
                        resultat.getString("rap_motif"),
                        resultat.getInt("rap_coefconfiance"),
                        resultat.getBoolean("pra_lu")
                );
                rapports.add(rapport);
            }
            requetePreparee.close();          
        } catch (Exception e) {
            System.err.println("ModeleGsbRv::getRapportsVisite() : " + e);
        }
        
        //System.out.println("ModeleGsbRv::getRapportVisite() : " + rapports.toString());
        return rapports;
    }
    
    public static void setRapportVisiteLu(String matricule, int numRapport) throws ConnexionException{
        Connection connexion = ConnexionBD.getConnexion();
        String requete = "update RapportVisite set pra_lu = true where vis_matricule = ? and rap_num = ?;";
        
        try {    
            PreparedStatement requetePreparee = (PreparedStatement) connexion.prepareStatement(requete);
            requetePreparee.setString(1, matricule);
            requetePreparee.setInt(2, numRapport);
            ResultSet resultat = requetePreparee.executeQuery();        
            requetePreparee.close();          
        } catch (Exception e) {
            System.err.println("ModeleGsbRv::setRapportVisiteLu() : " + e);
        }
        
        System.out.println("ModeleGsbRv::setRapportVisiteLu()");
    }
} 

