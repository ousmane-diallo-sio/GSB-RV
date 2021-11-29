/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.gsb.rv.dr.technique;

import java.sql.Connection;
import java.sql.DriverManager;


public class ConnexionBD {
    
    private static String dbURL = "jdbc:mysql://localhost:3306/gsbrv" ;
    private static String user = "sanayabio" ;
    private static String password = "azerty" ;
    
    private static Connection connexion = null ;
    
    private ConnexionBD() throws ConnexionException {
        try {
            System.out.println("ConnexionBD::ConnexionBD()");
            Class.forName( "org.mariadb.jdbc.Driver" ) ;
            connexion = (Connection) DriverManager.getConnection(dbURL, user, password) ;
        }
        catch( Exception e ){
            throw new ConnexionException() ;
        }
    }
    
    public static Connection getConnexion() throws ConnexionException {
        if( connexion == null ){
            new ConnexionBD() ;
        }
        System.out.println("ConnexionBD::getConnexion() : " + connexion);
        return connexion ;
    }
}
