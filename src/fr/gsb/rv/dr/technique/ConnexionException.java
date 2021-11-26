/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.gsb.rv.dr.technique;


public class ConnexionException extends Exception {
    
    @Override
    public String getMessage(){
        return "[Nok] Connexion BD" ;
    }
    
}
