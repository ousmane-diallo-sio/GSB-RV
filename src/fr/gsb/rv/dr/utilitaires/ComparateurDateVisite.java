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
public class ComparateurDateVisite implements Comparator<Praticien> {
    
    public int compare( Praticien p1, Praticien p2 ){
        return ( p1.getDateDerniereVisite().isEqual(p2.getDateDerniereVisite()) ) ? 0
        : ( p1.getDateDerniereVisite().isAfter(p2.getDateDerniereVisite()) ) ? 1
        : -1;
    }
}
