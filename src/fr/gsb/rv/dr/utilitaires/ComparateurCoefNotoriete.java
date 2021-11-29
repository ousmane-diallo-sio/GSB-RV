/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.gsb.rv.dr.utilitaires;

import fr.gsb.rv.dr.entites.Praticien;
import java.util.Comparator;

/**
 *
 * @author etudiant
 */
public class ComparateurCoefNotoriete implements Comparator<Praticien> {
    
    public int compare( Praticien p1, Praticien p2 ){
        return ( p1.getCoefNotoriete()== p2.getCoefNotoriete()) ? 0
        : ( p1.getCoefNotoriete()> p2.getCoefNotoriete()) ? 1
        : -1;
    }
}
