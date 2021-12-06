/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package gsb.rv.dr;

import fr.gsb.rv.dr.entites.Praticien;
import fr.gsb.rv.dr.entites.Visiteur;
import fr.gsb.rv.dr.modeles.ModeleGsbRv;
import fr.gsb.rv.dr.technique.ConnexionBD;
import fr.gsb.rv.dr.technique.ConnexionException;
import fr.gsb.rv.dr.technique.PanneauAccueil;
import fr.gsb.rv.dr.technique.PanneauPraticiens;
import fr.gsb.rv.dr.technique.PanneauRapports;
import fr.gsb.rv.dr.technique.Session;
import fr.gsb.rv.dr.technique.VueConnexion;
import fr.gsb.rv.dr.utilitaires.ComparateurCoefConfiance;
import fr.gsb.rv.dr.utilitaires.ComparateurCoefNotoriete;
import fr.gsb.rv.dr.utilitaires.ComparateurDateVisite;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.Mnemonic;
import javafx.scene.layout.Background;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Pair;
import javafx.scene.layout.VBox;

/**
 *
 * @author etudiant
 */
public class Appli extends Application {
    
    private final String sqlGetDelegue = "select vis_nom, Visiteur.vis_matricule, tra_role from Visiteur inner join Travailler on Visiteur.vis_matricule = Travailler.vis_matricule where tra_role like 'Délégué';";
    private final String sqlGetDelegueActuels = "select * from Travailler t inner join(select vis_matricule, max(jjmmaa) jjmmaa from Travailler group by vis_matricule) as s on t.vis_matricule = s.vis_matricule and t.jjmmaa = s.jjmmaa where tra_role = 'Délégué';";
    private final String sqlGetAllFromDelegue = "select * from Travailler t\n" +
                                                "inner join ( select tra_role, vis_matricule, max(jjmmaa) jjmmaa from Travailler group by vis_matricule ) s\n" +
                                                "inner join Visiteur v on s.vis_matricule = t.vis_matricule\n" +
                                                "and t.jjmmaa = s.jjmmaa and v.vis_matricule = t.vis_matricule\n" +
                                                "where t.tra_role = 'Délégué' and v.vis_matricule is not null and v.vis_mdp is not null;";
        
    
    private Text nomMenu;
    private MenuItem itemAccueil;
    private MenuItem itemSeConnecter;
    private MenuItem itemSeDeconnecter;
    
    private Menu menuRapports;
    
    private Menu menuPraticiens;
    
    private boolean sessionActive = false;
    private Text sessionText;
    
    private PanneauAccueil vueAccueil = new PanneauAccueil(new Label("Vue accueil"));
    private PanneauRapports vueRapports = new PanneauRapports(new Label("Vue Rapport / consulter"));
    private PanneauPraticiens vuePraticiens = new PanneauPraticiens(new Label("Vue Praticien / hesitant"));
    
    private VBox navBar = new VBox();

    
    private static Visiteur visiteur;
    
    public static void main(String[] args) {
        
        try{
            List<Praticien> praticiens = ModeleGsbRv.getPraticiensHesitants();
            
            Collections.sort( praticiens, new ComparateurCoefConfiance() );
            for(Praticien unPraticien : praticiens){
                //System.out.println(unPraticien);
            }
            
            Collections.sort( praticiens, new ComparateurCoefNotoriete() );
            for(Praticien unPraticien : praticiens){
                //System.out.println(unPraticien);
            }
            
            Collections.sort( praticiens, new ComparateurDateVisite() );
            for(Praticien unPraticien : praticiens){
                //System.out.println(unPraticien);
            }
            
            //System.out.println(ModeleGsbRv.getVisiteurs());
            //System.out.println(ModeleGsbRv.getRapportVisite("t60", 11, 2021));
            //ModeleGsbRv.setRapportVisiteLu("c14", 2);
            
        } catch(Exception e){
            //System.err.println("Appli::main() : " + e);
        }
        
        
        launch(args);        
    }
    
    
    public void handleMenu(){
        
        if( sessionActive ){
            itemSeConnecter.setDisable(true);
            itemSeDeconnecter.setDisable(false);
            menuRapports.setDisable(false);
            menuPraticiens.setDisable(false);
        } else{     
            itemSeConnecter.setDisable(false);
            itemSeDeconnecter.setDisable(true);
            menuRapports.setDisable(true);
            menuPraticiens.setDisable(true);            
            vueAccueil.toFront();
        }
        
    }
        
    
    @Override
    public void start(Stage primaryStage){
        
        MenuBar barreMenu = new MenuBar();
        
        Menu menuFichier = new Menu("Fichier");
        itemAccueil = new MenuItem("Accueil");
        itemSeConnecter = new MenuItem("Se connecter");
        itemSeDeconnecter = new MenuItem("Se déconnecter");
        MenuItem itemQuitter = new MenuItem("Quitter");
        menuFichier.getItems().add(new SeparatorMenuItem());
        KeyCombination kc = new KeyCodeCombination(KeyCode.X, KeyCombination.CONTROL_DOWN);
        itemQuitter.setAccelerator(kc);
        menuFichier.getItems().addAll(itemSeConnecter, itemSeDeconnecter, itemAccueil, itemQuitter);
        barreMenu.getMenus().add(menuFichier);
        
        primaryStage.setTitle("(Déconnecté)");
       
        itemAccueil.setOnAction(
                new EventHandler<ActionEvent>(){
                    @Override
                    public void handle(ActionEvent event){
                        vueAccueil.toFront(); 
                    }
                }
        );
        
        itemSeConnecter.setOnAction(
                new EventHandler<ActionEvent>(){
                    @Override
                    public void handle(ActionEvent event){
                        
                        VueConnexion vueConnexion = new VueConnexion();
                        Optional<Pair<String, String>> reponse = vueConnexion.showAndWait();
                        
                        if(reponse.isPresent()){
                            try {
                                var connexion = ConnexionBD.getConnexion();
                                visiteur = ModeleGsbRv.seConnecter(reponse.get().getKey(), reponse.get().getValue());
                                Alert alertQuitter = new Alert(Alert.AlertType.INFORMATION);
                                if( visiteur == null && reponse.get().getKey() != "0" ){
                                    alertQuitter.setTitle("Problème de connexion");
                                    alertQuitter.setHeaderText("Identifiants incorrects, réessayer.");
                                    alertQuitter.showAndWait();
                                    reponse = vueConnexion.showAndWait();
                                } else if( reponse.get().getKey() == "0" ){
                                    try{
                                        alertQuitter.setTitle("Connexion");
                                        alertQuitter.setHeaderText("Se connecter");
                                        alertQuitter.setContentText("Annulation...");
                                        Thread thread = new Thread(() -> {
                                            try {
                                                Thread.sleep(500);
                                                if (alertQuitter.isShowing()) {
                                                    Platform.runLater(() -> alertQuitter.close());
                                                }
                                            } catch (Exception exp) {
                                                exp.printStackTrace();
                                            }
                                        });
                                        thread.setDaemon(true);
                                        thread.start();
                                        alertQuitter.showAndWait();
                                    } catch(Exception e){
                                        System.err.println("Appli::itemSeConnecter : "+ e);
                                    }
                                }
                            } catch (ConnexionException ex) {
                                Logger.getLogger(Appli.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                        
                        Session.ouvrir(visiteur);
                        primaryStage.setTitle(visiteur.getNom() + " " + visiteur.getPrenom());
                        sessionActive = true;
                        sessionText.setText("Connecté");
                        nomMenu.setText("Se connecter");
                        handleMenu();
                    }
                }
        );
        
        itemSeDeconnecter.setOnAction(
                new EventHandler<ActionEvent>(){
                    @Override
                    public void handle(ActionEvent event){
                        primaryStage.setTitle("(Déconnecté)");
                        Session.fermer();
                        sessionActive = false;
                        sessionText.setText("Déconnecté");
                        nomMenu.setText("Se déconnecter");
                        handleMenu(); 
                    }
                }
        );
        
        itemQuitter.setOnAction(
                new EventHandler<ActionEvent>(){
                    @Override
                    public void handle(ActionEvent event){
                        nomMenu.setText("Quitter");
                        
                        Alert alertQuitter = new Alert(Alert.AlertType.CONFIRMATION);
                        alertQuitter.setTitle("Quitter");
                        alertQuitter.setHeaderText("Demande de confirmation");
                        alertQuitter.setContentText("Voulez-vous quitter l'application");
                        
                        ButtonType btnOui = new ButtonType("Quitter", ButtonBar.ButtonData.OK_DONE);
                        ButtonType btnNon = new ButtonType("Annuler", ButtonBar.ButtonData.CANCEL_CLOSE);
                        alertQuitter.getButtonTypes().setAll(btnOui, btnNon);
                        
                        System.out.println("Fermer l'application ?");
                        
                        Optional<ButtonType> reponse = alertQuitter.showAndWait();
                        
                        System.out.println("reponse : " + reponse.get());
                        if(reponse.get() == btnOui){
                            System.out.println("fermeture de l'application.");
                            Platform.exit();
                        }
                    }
                }
        );
        
        
        menuRapports = new Menu("Rapports");
        MenuItem itemConsulter = new MenuItem("Consulter");
        menuRapports.getItems().add(itemConsulter);
        barreMenu.getMenus().add(menuRapports);
        
        itemConsulter.setOnAction(
                new EventHandler<ActionEvent>(){
                    @Override
                    public void handle(ActionEvent event){
                        nomMenu.setText("Consulter");
                        vueRapports.toFront();
                        var visiteur = Session.getSession().getLeVisiteur();
                        //System.out.println("[Rapports]" + visiteur.getPrenom() + " " + visiteur.getNom());
                    }
                }
        );
        
        menuPraticiens = new Menu("Praticiens");
        MenuItem itemHesistants = new MenuItem("Hésitants");
        menuPraticiens.getItems().add(itemHesistants);
        barreMenu.getMenus().add(menuPraticiens);
        
        itemHesistants.setOnAction(
                new EventHandler<ActionEvent>(){
                    @Override
                    public void handle(ActionEvent event){
                        nomMenu.setText("Hésitants");
                       vuePraticiens.toFront();
                        var visiteur = Session.getSession().getLeVisiteur();
                        //System.out.println("[Praticiens]" + visiteur.getPrenom() + " " + visiteur.getNom());
                    }
                }
        );  
        handleMenu();

        
        BorderPane root = new BorderPane();
        navBar.getChildren().add(barreMenu);
        root.setTop(navBar);
        Scene scene = new Scene(root, 750, 400);
        
        StackPane mainLayout = new StackPane();
        
        mainLayout.getChildren().add(vueAccueil);       
        mainLayout.getChildren().add(vueRapports);
        mainLayout.getChildren().add(vuePraticiens);
        vueAccueil.toFront();

        
        BorderPane.setAlignment(mainLayout, Pos.CENTER);
        root.setCenter(mainLayout);
        
        
        nomMenu = new Text();
        nomMenu.setText("rien");
        vueAccueil.getChildren().add(nomMenu);
        
        sessionText = new Text();
        sessionText.setText("session : null");
        vueAccueil.getChildren().add(sessionText);
        
        primaryStage.setScene(scene);
        primaryStage.show();
        
        
    }
    
}
