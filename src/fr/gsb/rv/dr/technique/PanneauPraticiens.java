/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.gsb.rv.dr.technique;

import fr.gsb.rv.dr.entites.Praticien;
import fr.gsb.rv.dr.modeles.ModeleGsbRv;
import fr.gsb.rv.dr.utilitaires.ComparateurCoefConfiance;
import fr.gsb.rv.dr.utilitaires.ComparateurCoefNotoriete;
import fr.gsb.rv.dr.utilitaires.ComparateurDateVisite;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
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
    private List<Praticien> praticiensHesitants;
    
    private ToggleGroup groupRadioBtn = new ToggleGroup();
    private RadioButton btnCoefConfiance = new RadioButton();
    private RadioButton btnCoefNotoriete = new RadioButton();
    private RadioButton btnDateVisite = new RadioButton();
    
    private ComparateurCoefConfiance comparateurCoefConfiance = new ComparateurCoefConfiance();
    private ComparateurCoefNotoriete comparateurCoefNotoriete = new ComparateurCoefNotoriete();
    private ComparateurDateVisite comparateurDateVisite = new ComparateurDateVisite();
        
    private TableColumn<Praticien, Integer> colNum = new TableColumn<>("Numéro");
    private TableColumn<Praticien, String> colNom = new TableColumn<>("Nom");
    private TableColumn<Praticien, String> colVille = new TableColumn<>("Ville");
    private TableColumn<Praticien, Double> colCoefNotoriete = new TableColumn<>("Notoriété");
    private TableColumn<Praticien, LocalDate> colDateVisite = new TableColumn<>("Date visite");
    private TableColumn<Praticien, Integer> colCoefConfiance = new TableColumn<>("Confiance");
    private TableView<Praticien> tabPraticien = new TableView<>(this.rafraichir(comparateurCoefConfiance));

    
    private TableColumn critereTri = colCoefConfiance;
    
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
            this.rafraichir(comparateurCoefConfiance);
        } );  
        
        this.btnCoefNotoriete.setOnAction( (ActionEvent event) -> {
            this.rafraichir(comparateurCoefNotoriete);
        } );
        
        this.btnDateVisite.setOnAction( (ActionEvent event) -> {
            this.rafraichir(comparateurDateVisite);
        } );
        
        
        HBox layoutRadioBtn = new HBox( btnCoefConfiance, btnCoefNotoriete, btnDateVisite );
        this.getChildren().add(layoutRadioBtn);
        
        
        this.colNum.setCellValueFactory( new PropertyValueFactory<>("numero") );
        this.colNom.setCellValueFactory( new PropertyValueFactory<>("nom") );
        this.colVille.setCellValueFactory( new PropertyValueFactory<>("ville") );
        this.colCoefNotoriete.setCellValueFactory( new PropertyValueFactory<>("coefNotoriete") );
        this.colDateVisite.setCellValueFactory( new PropertyValueFactory<>("dateDerniereVisite") );
        this.colCoefConfiance.setCellValueFactory( new PropertyValueFactory<>("dernierCoefConfiance") );
        
        this.tabPraticien.getColumns().addAll(colNum, colNom, colVille, colCoefNotoriete, colDateVisite, colCoefConfiance);
        this.getChildren().add(tabPraticien);
        
        
    }
    
    public PanneauPraticiens(){

    }
    
    public TableColumn getCritereTri(){
        return this.critereTri;
    }
    
    public ObservableList rafraichir(Comparator comparateur){
        try{
            if( tabPraticien != null){
               tabPraticien.getItems().removeAll(praticiensHesitants); 
            }
            
            this.praticiensHesitants = ModeleGsbRv.getPraticiensHesitants();
            this.praticiensHesitants.sort(comparateur);
            System.out.println(praticiensHesitants);
            tabPraticien.getItems().addAll(praticiensHesitants);
            
        } catch(Exception e){
            System.err.println("PanneauPraticien::rafraichir() : " + e);
        }
        this.praticiensHesitants = FXCollections.observableArrayList( this.praticiensHesitants );
        return (ObservableList) this.praticiensHesitants;
    }
    
}
