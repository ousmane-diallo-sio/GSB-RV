/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.gsb.rv.dr.modeles;
import fr.gsb.rv.dr.entites.Praticien;
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
        
        // Code de test à compléter
        
        Connection connexion = ConnexionBD.getConnexion() ;
        
        String requete = "select vis_nom, vis_prenom "
                + "from Visiteur "
                + "where vis_matricule = ? and vis_mdp = ?" ;
        
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
                        resultat.getString("pra_num"), 
                        resultat.getString("pra_nom"), 
                        resultat.getString("pra_ville"),
                        resultat.getDouble("pra_coefnotoriete"),
                        resultat.getDate("rap_date_visite").toLocalDate(),
                        resultat.getInt("rap_coefconfiance")
                );
                praticiens.add(praticien);
            }
            requetePreparee.close();          
        } catch (Exception e) {
            System.err.println("ModeleGsbRv::getPraticiensHesitants() : " + e);
        }
        System.out.println("ModeleGsbRv::getPraticiensHesitants() : " + praticiens.toString());
        return praticiens;
        
    }
    
    
} 

