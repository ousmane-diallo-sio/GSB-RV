/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package gsb.rv.dr;

import fr.gsb.rv.dr.entites.Visiteur;
import fr.gsb.rv.dr.modeles.ModeleGsbRv;
import fr.gsb.rv.dr.technique.ConnexionBD;
import fr.gsb.rv.dr.technique.ConnexionException;
import fr.gsb.rv.dr.technique.Session;
import fr.gsb.rv.dr.technique.VueConnexion;
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
    private final String sqlGetDelegueActuels = "select t.vis_matricule, t.jjmmaa, t.tra_role from Travailler t inner join(select vis_matricule, max(jjmmaa) jjmmaa from Travailler group by vis_matricule) as s on t.vis_matricule = s.vis_matricule and t.jjmmaa = s.jjmmaa where tra_role = 'Délégué';";
    private final String sqlGetAllFromDelegue = "select * from Travailler t\n" +
                                                "inner join ( select tra_role, vis_matricule, max(jjmmaa) jjmmaa from Travailler group by vis_matricule ) s\n" +
                                                "inner join Visiteur v on s.vis_matricule = t.vis_matricule\n" +
                                                "and t.jjmmaa = s.jjmmaa and v.vis_matricule = t.vis_matricule\n" +
                                                "where t.tra_role = 'Délégué' and v.vis_matricule is not null and v.vis_mdp is not null;";
    
    
    
    private Text nomMenu;
    private MenuItem itemSeConnecter;
    private MenuItem itemSeDeconnecter;
    
    private Menu menuRapports;
    
    private Menu menuPraticiens;
    
    private boolean sessionActive = false;
    private Text sessionText;
    
    private VBox accueilLayout = new VBox(new Text("Vue Acceuil"));
    private VBox consulterLayout = new VBox(new Text("Vue Rapport / consulter"));
    private VBox hesitantLayout = new VBox(new Text("Vue Praticien / hesitant"));
    
    private HBox navBar = new HBox();

    
    private Visiteur tempVisiteur = new Visiteur("0B001", "BOUAICHI", "Oumayma");
    private static Visiteur visiteur;
    
    public static void main(String[] args) {
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
        }
        
    }
        
    
    @Override
    public void start(Stage primaryStage){
        
        MenuBar barreMenu = new MenuBar();
        
        Menu menuFichier = new Menu("Fichier");
        itemSeConnecter = new MenuItem("Se connecter");
        itemSeDeconnecter = new MenuItem("Se déconnecter");
        MenuItem itemQuitter = new MenuItem("Quitter");
        menuFichier.getItems().add(new SeparatorMenuItem());
        KeyCombination kc = new KeyCodeCombination(KeyCode.X, KeyCombination.CONTROL_DOWN);
        itemQuitter.setAccelerator(kc);
        menuFichier.getItems().addAll(itemSeConnecter, itemSeDeconnecter, itemQuitter);
        barreMenu.getMenus().add(menuFichier);
        
        primaryStage.setTitle("(Déconnecté)");
        navBar.setStyle("-fx-background-color: #333");
       
        
        Menu menuAccueil = new Menu("Acceuil");
        barreMenu.getMenus().add(menuAccueil);
        
        menuAccueil.setOnAction(
                new EventHandler<ActionEvent>(){
                    @Override
                    public void handle(ActionEvent event){
                        accueilLayout.toFront();
                    }
                }
        );
        
        itemSeConnecter.setOnAction(
                new EventHandler<ActionEvent>(){
                    @Override
                    public void handle(ActionEvent event){
                        
                        try {
                            var connexion = ConnexionBD.getConnexion();
                            visiteur = ModeleGsbRv.seConnecter("t60", "azerty");
                            System.out.println("Infos de connexion : " + visiteur.toString());
                        } catch (ConnexionException ex) {
                            System.out.println("Erreur de connexion");
                            Logger.getLogger(Appli.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        
                        VueConnexion vueConnexion = new VueConnexion();
                        var test = vueConnexion.showAndWait();
                        System.out.println("Formulaire connexion : " + test);
                        
                        Session.ouvrir(tempVisiteur);
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
                        consulterLayout.toFront();
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
                       hesitantLayout.toFront();
                        var visiteur = Session.getSession().getLeVisiteur();
                        //System.out.println("[Praticiens]" + visiteur.getPrenom() + " " + visiteur.getNom());
                    }
                }
        );  
        handleMenu();
        
        
        Button btnAccueil = new Button("Retour vers l'accueil");
        btnAccueil.setStyle("-fx-pref-height: 100%");
        btnAccueil.setOnAction(e -> {
            accueilLayout.toFront();
        });
        
        BorderPane root = new BorderPane();
        navBar.getChildren().add(btnAccueil);
        navBar.getChildren().add(barreMenu);
        root.setTop(navBar);
        Scene scene = new Scene(root, 600, 350);
        
        StackPane mainLayout = new StackPane();
        
        accueilLayout.setStyle("-fx-background-color: #f00");
        mainLayout.getChildren().add(accueilLayout);
        consulterLayout.setStyle("-fx-background-color: #0f0");
        mainLayout.getChildren().add(consulterLayout);
        hesitantLayout.setStyle("-fx-background-color: #00f");
        mainLayout.getChildren().add(hesitantLayout);
        accueilLayout.toFront();

        
        BorderPane.setAlignment(mainLayout, Pos.CENTER);
        root.setCenter(mainLayout);
        
        
        nomMenu = new Text();
        nomMenu.setText("rien");
        mainLayout.getChildren().add(nomMenu);
        mainLayout.setAlignment(nomMenu, Pos.CENTER);
        
        sessionText = new Text();
        sessionText.setText("session : null");
        mainLayout.getChildren().add(sessionText);
        StackPane.setAlignment(sessionText, Pos.BOTTOM_CENTER);
        
        primaryStage.setScene(scene);
        primaryStage.show();
        
        
    }
    
}
