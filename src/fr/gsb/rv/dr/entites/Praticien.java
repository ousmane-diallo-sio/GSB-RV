/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.gsb.rv.dr.entites;

import fr.gsb.rv.dr.technique.ConnexionBD;
import fr.gsb.rv.dr.technique.ConnexionException;
import java.time.LocalDate;
import java.util.List;
import java.sql.Connection;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author etudiant
 */
public class Praticien {
    
    private String numero;
    private String nom;
    private String ville;
    private double coefNotoriete;
    private LocalDate dateDerniereVisite;
    private int dernierCoefConfiance;

    public Praticien(String numero, String nom, String ville, double coefNotoriete, LocalDate dateDerniereVisite, int dernierCoefConfiance) {
        this.numero = numero;
        this.nom = nom;
        this.ville = ville;
        this.coefNotoriete = coefNotoriete;
        this.dateDerniereVisite = dateDerniereVisite;
        this.dernierCoefConfiance = dernierCoefConfiance;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public double getCoefNotoriete() {
        return coefNotoriete;
    }

    public void setCoefNotoriete(double coefNotoriete) {
        this.coefNotoriete = coefNotoriete;
    }

    public LocalDate getDateDerniereVisite() {
        return dateDerniereVisite;
    }

    public void setDateDerniereVisite(LocalDate dateDerniereVisite) {
        this.dateDerniereVisite = dateDerniereVisite;
    }

    public int getDernierCoefConfiance() {
        return dernierCoefConfiance;
    }

    public void setDernierCoefConfiance(int dernierCoefConfiance) {
        this.dernierCoefConfiance = dernierCoefConfiance;
    }

    @Override
    public String toString() {
        return "Praticien{" + "numero=" + numero + ", nom=" + nom + ", ville=" + ville + ", coefNotoriete=" + coefNotoriete + ", dateDerniereVisite=" + dateDerniereVisite + ", dernierCoefConfiance=" + dernierCoefConfiance + '}';
    }
    
    
}
