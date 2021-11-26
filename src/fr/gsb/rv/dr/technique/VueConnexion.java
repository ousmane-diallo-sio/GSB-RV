/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.gsb.rv.dr.technique;

import java.util.Optional;
import javafx.geometry.Insets;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.util.Callback;
import javafx.util.Pair;

/**
 *
 * @author etudiant
 */
public class VueConnexion {

    
    public VueConnexion(){
    }
    
    
    
    public Pair showAndWait(){
        
        Dialog<Pair<String, String>> modalConnexion = new Dialog();
        modalConnexion.setTitle("Connexion");
        modalConnexion.setHeaderText("Saisissez vos informations de connexion");
        Text matriculeText = new Text();
        matriculeText.setText("Matricule : ");
        modalConnexion.getDialogPane().setContent(matriculeText);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));
        
        TextField matricule = new TextField();
        matricule.setPromptText("Matricule");
        PasswordField mdp = new PasswordField();
        mdp.setPromptText("Mot de passe");

        grid.add(new Label("Matricule : "), 0, 0);
        grid.add(matricule, 1, 0);
        grid.add(new Label("Mot de passe : "), 0, 1);
        grid.add(mdp, 1, 1);
        
        modalConnexion.getDialogPane().setContent(grid);
        
        ButtonType btnValider = new ButtonType("Valider", ButtonBar.ButtonData.OK_DONE);
        modalConnexion.getDialogPane().getButtonTypes().addAll(btnValider, ButtonType.CANCEL);
        
        Optional<Pair<String, String>> resultat = modalConnexion.showAndWait();
        
        modalConnexion.setResultConverter(new Callback<ButtonType, Pair<String, String>>(){
            
            @Override
            public Pair<String, String> call(ButtonType btnValider){
                Callback<ButtonType, Pair<String, String>> callback = new Callback<ButtonType, Pair<String, String>>(){
                    @Override
                    public Pair<String, String> call(ButtonType typeButton){
                        return (typeButton == btnValider) ?
                            new Pair<String, String>(matricule.getText(), mdp.getText())
                        : null;
                    }
                };
                return null;
            }   
            
        });
        return null;
        
    }
    
}
