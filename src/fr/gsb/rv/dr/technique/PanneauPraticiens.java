/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.gsb.rv.dr.technique;

import fr.gsb.rv.dr.entites.Praticien;
import fr.gsb.rv.dr.modeles.ModeleGsbRv;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

/**
 *
 * @author etudiant
 */
public class PanneauPraticiens extends VBox{
    
    private Node contenu;
    private Node critereTri;
    private List<Praticien> praticiensHesitants;
    
    private ToggleGroup groupRadioBtn = new ToggleGroup();
    private RadioButton btnCoefConfiance = new RadioButton();
    private RadioButton btnCoefNotoriete = new RadioButton();
    private RadioButton btnDateVisite = new RadioButton();
    
    private TableView<Praticien> tabPraticien = new TableView<>(this.rafraichir());
    private TableColumn<Praticien, Integer> colPraNum = new TableColumn<>("Numéro");
    private TableColumn<Praticien, String> colPraNom = new TableColumn<>("Nom");
    private TableColumn<Praticien, String> colPraVille = new TableColumn<>("Ville");
    private TableColumn<Praticien, Double> colPraCoefNotoriete = new TableColumn<>("Notoriété");
    private TableColumn<Praticien, LocalDate> colPraDateVisite = new TableColumn<>("Date visite");
    private TableColumn<Praticien, Integer> colPraCoefConfiance = new TableColumn<>("Confiance");
    
    
    public PanneauPraticiens(Node contenu ){
        this.contenu = contenu;
        this.getChildren().add(contenu);
        this.setStyle("-fx-background-color: #fff;");
        
        Text labelRadio = new Text("Sélectionner un critère de tri : ");
        labelRadio.setStyle("-fx-font-weight: bold");
        
        this.getChildren().add( labelRadio );
        
        this.btnCoefConfiance.setText("Confiance");
        this.btnCoefNotoriete.setText("Notoriété");
        this.btnDateVisite.setText("Date visite");
        
        this.btnCoefConfiance.setToggleGroup(groupRadioBtn);
        this.btnCoefNotoriete.setToggleGroup(groupRadioBtn);
        this.btnDateVisite.setToggleGroup(groupRadioBtn);
        
        this.btnCoefConfiance.setSelected(true);
        
        this.btnCoefConfiance.setOnAction( (ActionEvent event) -> {
            this.critereTri = btnCoefConfiance;
            this.rafraichir();
        } );  
        
        this.btnCoefNotoriete.setOnAction( (ActionEvent event) -> {
            this.critereTri = btnCoefNotoriete;
            this.rafraichir();
        } );
        
        this.btnCoefConfiance.setOnAction( (ActionEvent event) -> {
            this.critereTri = btnDateVisite;
            this.rafraichir();
        } );
        
        
        HBox layoutRadioBtn = new HBox( btnCoefConfiance, btnCoefNotoriete, btnDateVisite );
        this.getChildren().add(layoutRadioBtn);
        
        
        this.colPraNum.setCellValueFactory( new PropertyValueFactory<>("numero") );
        this.colPraNom.setCellValueFactory( new PropertyValueFactory<>("nom") );
        this.colPraVille.setCellValueFactory( new PropertyValueFactory<>("ville") );
        this.colPraCoefNotoriete.setCellValueFactory( new PropertyValueFactory<>("coefNotoriete") );
        this.colPraDateVisite.setCellValueFactory( new PropertyValueFactory<>("dateDerniereVisite") );
        this.colPraCoefConfiance.setCellValueFactory( new PropertyValueFactory<>("dernierCoefConfiance") );
        
        this.tabPraticien.getColumns().addAll(colPraNum, colPraNom, colPraVille, colPraCoefNotoriete, colPraDateVisite, colPraCoefConfiance);
        this.getChildren().add(tabPraticien);
    }
    
    public PanneauPraticiens(){

    }
    
    public Node getCritereTri(){
        return this.critereTri;
    }
    
    public ObservableList rafraichir(){
        try{
            this.praticiensHesitants = ModeleGsbRv.getPraticiensHesitants();
        } catch(Exception e){
            System.err.println("PanneauPraticien::rafraichir : " + e);
        }
        this.praticiensHesitants = FXCollections.observableArrayList( this.praticiensHesitants );
        return (ObservableList) this.praticiensHesitants;
    }
}
