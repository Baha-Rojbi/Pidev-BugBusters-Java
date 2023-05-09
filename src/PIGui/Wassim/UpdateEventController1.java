/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PIGui.Wassim;

import PIUtils.MyConnection;
import java.io.File;
import java.net.URL;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author 21651
 */
public class UpdateEventController1 implements Initializable {

    @FXML
    private TextField date;
    @FXML
    private TextArea description;
    @FXML
    private TextField nom;
    @FXML
    private Button image;

    /**
     * Initializes the controller class.
     */
    int id;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void Update(ActionEvent event) throws SQLException {
        String requette = "UPDATE event SET `date`=?, `description`=? ,`nom`=?  where `id_event`= " + id;
        System.out.println("PIGui.Wassim.UpdateEventController1.Update()" + requette);

        Connection cn = MyConnection.getInstance().getCnx();
        PreparedStatement ps = cn.prepareStatement(requette);
        ps.setString(1, date.getText());
        ps.setString(2, description.getText());

        ps.setString(3, nom.getText());

        System.out.println("PIGui.Wassim.UpdateEventController1.Update()" + requette);

        ps.executeUpdate();
        System.out.println("Modifier avec succees !");

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();

    }

    @FXML
    private void upload(ActionEvent event) {
        Stage primary = new Stage();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Selectionner une image");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("image Files", "*.png", "*.jpg", "*.gif", "*.jpeg"));
        File file = fileChooser.showOpenDialog(primary);
/*changer path suivant dossier */
        String path = "C:\\Users\\21651\\Desktop\\PIDevajout\\jiji\\jiji\\pi\\PIDev\\src\\image";
        image.setText(file.getPath());
        String m = file.getPath();

        if (file != null) {
            try {
                Files.copy(file.toPath(), new File(path + "\\" + file.getName()).toPath());

                System.out.println(m);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    void fonction(int id, String date, String description, String nom, String image) {
        this.id = id;
        this.date.setText(date);
        this.description.setText(description);

        this.nom.setText(nom);

        this.image.setText(image);

    }

}
