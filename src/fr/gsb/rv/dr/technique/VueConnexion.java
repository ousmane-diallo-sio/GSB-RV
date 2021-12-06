/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.gsb.rv.dr.technique;

import java.util.Optional;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Node;
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
    
    public Optional showAndWait(){
                
        Text matriculeText = new Text();
        matriculeText.setText("Matricule : ");
        
        Dialog<Pair<String, String>> modalConnexion = new Dialog();
        modalConnexion.setTitle("Connexion");
        modalConnexion.setHeaderText("Saisissez vos informations de connexion");
        modalConnexion.getDialogPane().setContent(matriculeText);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));
        
        TextField matricule = new TextField();
        matricule.setPromptText("Matricule");
        Platform.runLater(() -> matricule.requestFocus());
        PasswordField mdp = new PasswordField();
        mdp.setPromptText("Mot de passe");

        grid.add(new Label("Matricule : "), 0, 0);
        grid.add(matricule, 1, 0);
        grid.add(new Label("Mot de passe : "), 0, 1);
        grid.add(mdp, 1, 1);
        
        modalConnexion.getDialogPane().setContent(grid);
        
        ButtonType btnValider = new ButtonType("Valider", ButtonBar.ButtonData.OK_DONE);
        modalConnexion.getDialogPane().getButtonTypes().addAll(btnValider, ButtonType.CANCEL);
        
        Node loginButton = modalConnexion.getDialogPane().lookupButton(btnValider);
        loginButton.setDisable(true);
        
        matricule.textProperty().addListener((observable, oldValue, newValue) -> {
        loginButton.setDisable(newValue.trim().isEmpty());
        });
        
        modalConnexion.setResultConverter( typeButton -> {
                //System.out.println("VueConnexion::call() : typeButton = " + typeButton);
                return (typeButton == btnValider) ?
                    new Pair<String, String>(matricule.getText(), mdp.getText())
                : 
                    new Pair<String, String>("0", "Annulation de la connexion...");
            
        });
        
                
        Optional<Pair<String, String>> resultat = modalConnexion.showAndWait();
        //System.out.println("VueConnexion::resultat = " + resultat);
        
        return resultat;
    }
    
}
