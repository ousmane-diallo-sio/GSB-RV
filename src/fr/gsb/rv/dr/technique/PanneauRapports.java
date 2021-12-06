/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.gsb.rv.dr.technique;

import fr.gsb.rv.dr.entites.RapportVisite;
import fr.gsb.rv.dr.entites.Visiteur;
import fr.gsb.rv.dr.modeles.ModeleGsbRv;
import java.time.LocalDate;
import java.util.List;
import java.util.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 *
 * @author etudiant
 */
public class PanneauRapports extends VBox {
            
    private Node contenu;
    private ComboBox<Visiteur> cbVisiteurs = new ComboBox<>();
    private ComboBox<Mois> cbMois = new ComboBox<>();
    private ComboBox<Integer> cbAnnee = new ComboBox<>();
    private LocalDate aujourdhui = LocalDate.now();
    private int anneeCourante = aujourdhui.getYear();
    
    private Button btnValider = new Button("Valider");
    
    private List rapportVisite;
    private String matricule;
    private int mois;
    private int annee;
    
    private TableView<RapportVisite> tabRapport = new TableView<>();
    private TableColumn<RapportVisite, Integer> colNumero = new TableColumn<>("Numéro");
    private TableColumn<RapportVisite, String> colNom = new TableColumn<>("Praticien");
    private TableColumn<RapportVisite, String> colVille = new TableColumn<>("Ville");
    private TableColumn<RapportVisite, LocalDate> colDateVisite = new TableColumn<>("Date visite");
    private TableColumn<RapportVisite, LocalDate> colDateRedaction = new TableColumn<>("Rédaction");
    
    public PanneauRapports(Node contenu ){
        this.contenu = contenu;
        this.getChildren().add(contenu);
        this.setStyle("-fx-background-color: #fff");
      
        
        try{
            this.cbVisiteurs.getItems().addAll(ModeleGsbRv.getVisiteurs());
        } catch(Exception e){
            System.err.println("PanneauRapport::PanneauRapport() : " + e);
        }
        
        this.cbMois.getItems().addAll(Mois.values());
        this.cbAnnee.getItems().addAll(anneeCourante -5, anneeCourante -4, anneeCourante-3, anneeCourante -2, anneeCourante -1, anneeCourante);
        
        this.cbVisiteurs.getSelectionModel().selectFirst();
        this.cbMois.getSelectionModel().selectFirst();
        this.cbAnnee.getSelectionModel().selectLast();
        
        HBox formulaire = new HBox();
        formulaire.getChildren().addAll(cbVisiteurs, cbMois, cbAnnee);
        this.getChildren().add(formulaire);
        
        btnValider.setOnAction(e -> {
            this.rafraichir();
        });
        
        this.getChildren().add(this.btnValider);
        
        this.colNumero.setCellValueFactory( new PropertyValueFactory<>("rap_num") );
        this.colNom.setCellValueFactory( new PropertyValueFactory<>("pra_num") ); //ajouter requete pour obtenir le nom du Praticien
        this.colVille.setCellValueFactory( new PropertyValueFactory<>("pra_num") ); //idem pour la ville du Praticien
        this.colDateVisite.setCellValueFactory( new PropertyValueFactory<>("rap_date_visite") );
        this.colDateRedaction.setCellValueFactory( new PropertyValueFactory<>("rap_date_saisie") );
        
        tabRapport.getColumns().addAll(colNumero, colNom, colVille, colDateVisite, colDateRedaction);
        this.getChildren().add(tabRapport);
        
    }
    
    
    public PanneauRapports(){
        
    }
    
    
    public ObservableList rafraichir(){
        try{
            this.rapportVisite = ModeleGsbRv.getRapportVisite(this.matricule, this.mois, this.annee);
            System.out.println(cbVisiteurs.getValue() + " | " + cbMois.getValue() + " | " + cbAnnee.getValue());
        } catch(Exception e){
            System.err.println("PanneauPraticien::rafraichir : " + e);
        }
        this.rapportVisite = FXCollections.observableArrayList( this.rapportVisite );
        return (ObservableList) this.rapportVisite;
    }
}
