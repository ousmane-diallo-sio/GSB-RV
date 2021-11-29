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
public class ComparateurCoefConfiance implements Comparator<Praticien> {
    
    @Override
    public int compare( Praticien p1, Praticien p2 ){
        return ( p1.getDernierCoefConfiance() == p2.getDernierCoefConfiance() ) ? 0
                : ( p1.getDernierCoefConfiance() > p2.getDernierCoefConfiance() ) ? 1
                : -1;
    }
}
