/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.gsb.rv.dr.technique;

import java.awt.Panel;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;

/**
 *
 * @author etudiant
 */
public class PanneauAccueil extends VBox{
    
    private Node contenu;
    
    
    public PanneauAccueil(Node contenu ){
        this.contenu = contenu;
        this.getChildren().add(contenu);
        
        this.setStyle("-fx-background-color: #fff");

    }
    
    public PanneauAccueil(){

    }
}
