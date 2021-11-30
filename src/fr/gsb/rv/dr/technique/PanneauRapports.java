/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.gsb.rv.dr.technique;

import javafx.scene.Node;
import javafx.scene.layout.VBox;

/**
 *
 * @author etudiant
 */
public class PanneauRapports extends VBox {
        
    private Node contenu;
    
    
    public PanneauRapports(Node contenu ){
        this.contenu = contenu;
        this.getChildren().add(contenu);
        
        this.setStyle("-fx-background-color: #fff");
    }
    
    public PanneauRapports(){

    }
}
