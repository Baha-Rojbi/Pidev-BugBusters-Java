/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PIGui.Wassim;

import PIClass.Event;
import PIServices.Wassim.EventServices;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javax.swing.JOptionPane;
import javafx.scene.Node;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.TableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Window;

/**
 * FXML Controller class
 *
 * @author 21651
 */
public class EventBController implements Initializable {

    int c;
    int file;
    File pDir;
    File pfile;
    String lien;
    @FXML
    private TableView<Event> tblViewCurrentStore;
    @FXML
    private MenuItem miSellSelected;
    @FXML
    private TableColumn<Event, String> tblClmProductId;

    @FXML
    private TextField tfSearch;
    @FXML
    private ComboBox<String> combo;
    @FXML
    private Button btnAddNew;
    @FXML
    private Button btnUpdate;
    @FXML
    private Button btnDelete;
    private Button btnAddNew1;
    @FXML
    private TextArea description;
    @FXML
    private TextField nomm;
    @FXML
    private TextField datee;
    private TextField id;
    @FXML
    private TableColumn<Event, String> nom1;
    private TableColumn<Event, String> description1;
    @FXML
    private TableColumn<Event, String> date1;
    @FXML
    private TableColumn<Event, String> type1;
    private ObservableList<Event> eventList = FXCollections.observableArrayList();
    @FXML
    private TableColumn<Event, String> desc1;
    @FXML
    private TableColumn<Event, String> colImage;
    @FXML
    private ImageView imv;
    @FXML
    private Button refresh;
    @FXML
    private Button closeb;
    @FXML
    private Circle circle;
    @FXML
    private Button nom_u;

    public void affiche() {

        ObservableList<Event> l = (ObservableList<Event>) new EventServices().getAll();

        /* ID.setCellValueFactory(new PropertyValueFactory<Event, Integer>("id"));*/
        nom1.setCellValueFactory(new PropertyValueFactory<>("nom"));
        date1.setCellValueFactory(new PropertyValueFactory<>("date"));
        desc1.setCellValueFactory(new PropertyValueFactory<>("description"));

        type1.setCellValueFactory(new PropertyValueFactory<>("type"));
        colImage.setCellValueFactory(new PropertyValueFactory<>("image"));

        tblViewCurrentStore.setItems(l);
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        c = (int) (Math.random() * (300000 - 2 + 1)) + 2;
        pDir = new File("src/image" + c + ".jpg");
        lien = "src/image" + c + ".jpg";
        EventServices produitC = new EventServices();
        /*ID.setCellValueFactory(new PropertyValueFactory<Event, Integer>("id"));*/

        combo.setItems(FXCollections.observableArrayList(produitC.getAllType()));
        ObservableList<Event> l = (ObservableList<Event>) new EventServices().getAll();

        nom1.setCellValueFactory(new PropertyValueFactory<>("nom"));
        desc1.setCellValueFactory(new PropertyValueFactory<>("description"));

        date1.setCellValueFactory(new PropertyValueFactory<>("date"));

        
        colImage.setCellValueFactory(new PropertyValueFactory<>("image"));
        // configure la colonne de l'image pour afficher les images
        colImage.setCellFactory(column -> {
            return new TableCell<Event, String>() {
                private final ImageView imageView = new ImageView();

                {
                    setContentDisplay(ContentDisplay.GRAPHIC_ONLY); // afficher uniquement l'image sans le texte
                }

                @Override
                protected void updateItem(String imagePath, boolean empty) {
                    super.updateItem(imagePath, empty);
                    if (imagePath == null || empty) {
                        imageView.setImage(null);
                        setGraphic(null);
                    } else {
                        Image image = new Image(new File(imagePath).toURI().toString());
                        imageView.setImage(image);
                        imageView.setFitWidth(100); // définit la largeur de l'image à 100 pixels
                        imageView.setFitHeight(100);
                        setGraphic(imageView);
                    }
                }
            };
        });

        tblViewCurrentStore.setItems(l);
    }

    @FXML
    private void miSellSelectedOnAction(ActionEvent event) {
    }

    @FXML
    private void tblViewCurrentStoreOnClick(MouseEvent event) {
    }

    @FXML
    private void tblViewCurrentStoreOnScroll(ScrollEvent event) {
    }

    private void tfSearchOnKeyRelese(KeyEvent event) {
        EventServices produitC = new EventServices();

        ObservableList<Event> l = (ObservableList<Event>) new EventServices().RECHERCHE(tfSearch.getText());

        /* ID.setCellValueFactory(new PropertyValueFactory<Event, Integer>("id"));*/
        nom1.setCellValueFactory(new PropertyValueFactory<>("nom"));
        date1.setCellValueFactory(new PropertyValueFactory<>("date"));
        desc1.setCellValueFactory(new PropertyValueFactory<>("description"));

        type1.setCellValueFactory(new PropertyValueFactory<>("type"));
        colImage.setCellValueFactory(new PropertyValueFactory<>("image"));

        tblViewCurrentStore.setItems(l);
    }



    public static boolean copier(File source, File dest) {
        try (InputStream sourceFile = new java.io.FileInputStream(source);
                OutputStream destinationFile = new FileOutputStream(dest)) {
            // Lecture par segment de 0.5Mo  
            byte buffer[] = new byte[512 * 1024];
            int nbLecture;
            while ((nbLecture = sourceFile.read(buffer)) != -1) {
                destinationFile.write(buffer, 0, nbLecture);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return false; // Erreur 
        }
        return true; // Résultat OK   
    }

    @FXML
    private void ajouterEvent(ActionEvent event) throws Exception {
        copier(pfile, pDir);

        String dat = datee.getText();
        String descrip = description.getText();
        String nom = nomm.getText();

        String typee = combo.getValue();

        EventServices formation = new EventServices();

        Event e = new Event(dat, descrip, typee, nom);
        e.setImage(lien);
        if (typee.equals("")) {
            JOptionPane.showMessageDialog(null, "Veuillez remplir tous les champs");
            return;
        } else if (!datee.getText().matches("\\d{4}-\\d{2}-\\d{2}")) {
            JOptionPane.showMessageDialog(null, "Veuillez saisir une date valide au format YYYY-MM-DD");
            return;
        } else {

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Etes vous sur de vouloir ajouter cette event " + " ?", ButtonType.YES, ButtonType.NO);
            alert.showAndWait();

            if (alert.getResult() == ButtonType.YES) {
                formation.ajouterEvent(e);

                affiche();

            }
        }

    }

    @FXML
    private void btnUpdateOnAction(ActionEvent event) throws SQLException {

        boolean choix = tblViewCurrentStore.getSelectionModel().isEmpty();

        if (!choix) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation de votre modification ");
            alert.setHeaderText(null);
            alert.setContentText("Voulez-vous vraiment de modifier cette ligne ??");

            Optional<ButtonType> action = alert.showAndWait();

            if (action.get() == ButtonType.OK) {

                try {

                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(getClass().getResource("updateEvent_1.fxml"));
                    loader.load();
                    /*FXMLLoader loader= new FXMLLoader(getClass().getResource("Back_type.fxml"));*/
                    Event tab2 = tblViewCurrentStore.getSelectionModel().getSelectedItem();
                    UpdateEventController1 UpdateEventController = loader.getController();

                    UpdateEventController.fonction(tab2.getId(), tab2.getDate(), tab2.getDescription(), tab2.getNom(), tab2.getImage());

                    Parent parent = loader.getRoot();
                    Scene scene = new Scene(parent);
                    Stage stage = new Stage();
                    stage.setScene(scene);
                    stage.initStyle(StageStyle.UTILITY);
                    stage.show();

                } catch (IOException ex) {
                    System.out.println(ex);
                }
            }
        } else if (choix) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Selectionner");
            alert.setHeaderText(null);
            alert.setContentText("Selectionner une ligne ");
            alert.showAndWait();
        }

        affiche();

    }

    @FXML
    private void btnDeleteOnAction(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Voulez vous suprimer ce Event ?");
        Optional<ButtonType> result = alert.showAndWait();
        Event eventToDelete = tblViewCurrentStore.getSelectionModel().getSelectedItem();

        if (eventToDelete != null) {
            EventServices crs = new EventServices();
            crs.deleteEvent(eventToDelete);
            eventList.remove(eventToDelete);

            if (result.get() == ButtonType.OK) {

                JOptionPane.showMessageDialog(null, "Event supprimé");

            }

            System.out.println(result.get().getText().equals("Annuler"));
            if (result.get().getText().equals("Annuler")) {
                System.out.println("qqqqq");
                JOptionPane.showMessageDialog(null, "Type Event n'a pas été supprimé");

                return;
            }

            affiche();

        }
    }

    private void selectOnAction(ActionEvent event) {
        Event s = tblViewCurrentStore.getSelectionModel().getSelectedItem();
        id.setText(String.valueOf(s.getId()));

        nomm.setText(s.getNom());

        datee.setText(s.getDate());

        description.setText(s.getDescription());

    }

  
    @FXML
    private void upload(ActionEvent event)
            throws MalformedURLException {

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choisir une image: ");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                new FileChooser.ExtensionFilter("JPEG", "*.jpeg"),
                new FileChooser.ExtensionFilter("PNG", "*.png"),
                new FileChooser.ExtensionFilter("BMP", "*.bmp")
        );
        Window stage = null;
        pfile = fileChooser.showOpenDialog(stage);

        /* - draw image */
        if (pfile != null) {
            file = 1;
            Image image = new Image(pfile.toURI().toURL().toExternalForm());
            imv.setImage(image);
        }
    }

    @FXML
    private void refreshaction(ActionEvent event) {

        Node node = (Node) event.getSource();
        Stage thiStage = (Stage) node.getScene().getWindow();
        System.out.println("PIGui.Wassim.EventBController.refreshaction()" + thiStage.getWidth());
        thiStage.setWidth(thiStage.getWidth() + 0.001);

        System.out.println("PIGui.Wassim.EventBController.refreshaction()" + thiStage.getWidth());

        affiche();
    }

    @FXML
    private void Ajouter_type(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("Back_type.fxml"));
        Parent root = loader.load();
        btnUpdate.getScene().setRoot(root);

    }

    @FXML
    private void Front(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("Event_final.fxml"));
        Parent root = loader.load();
        btnUpdate.getScene().setRoot(root);

    }

}
