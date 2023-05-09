/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PIGui.Wassim;

import java.io.FileInputStream;
import javafx.scene.image.Image;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import PIClass.Event;
import PIClass.LabelImage;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;

import PIServices.Wassim.EventServices;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import java.io.File;

import java.util.HashMap;
import java.util.Map;
import javafx.collections.ObservableList;
import javafx.scene.layout.Pane;
import javafx.scene.control.Label;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author 21651
 */
public class EventFrontController implements Initializable {

    @FXML
    private FlowPane typeCardsPane;

    @FXML
    private Button Evenements;

    @FXML
    private Label Nom;

    private int Id_event;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        EventServices eventServices = new EventServices();
        ObservableList<Event> events = eventServices.TriEventAs();

        for (Event event : events) {
            // Create the card pane
            Pane cardPane = new Pane();
            cardPane.setStyle("-fx-background-color: #C9CAB6; -fx-padding: 20px; -fx-spacing: 20px -fx-opacity= 0.17 ;");

            // Create the QR code
            String data = event.getDescription();
            try {
                // The path where the image will get saved
                String path = "C:\\Users\\21651\\Desktop\\Intégration\\PI\\QRCODE" + data + ".png";

                // Encoding charset
                String charset = "UTF-8";

                Map<EncodeHintType, ErrorCorrectionLevel> hashMap = new HashMap<EncodeHintType, ErrorCorrectionLevel>();
                hashMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);

                BitMatrix matrix = new MultiFormatWriter().encode(
                        new String(data.getBytes(charset), charset),
                        BarcodeFormat.QR_CODE, 200, 200);

                MatrixToImageWriter.writeToFile(matrix, path.substring(path.lastIndexOf('.') + 1), new File(path));
                System.out.println("QR Code Generated!!! ");
// Load the QR code image and add it to the card pane
                InputStream stream = new FileInputStream(path);
                Image image = new Image(stream);
                ImageView qrImageView = new ImageView(image);
                qrImageView.setFitWidth(100);
                qrImageView.setFitHeight(100);

//add image 
// Set the background image of the card pane
                ImageView imageView = new ImageView(new Image(new File(event.getImage()).toURI().toString()));
                imageView.setFitWidth(100);
                imageView.setFitHeight(100);
                LabelImage imageLabel = new LabelImage(imageView);

//add nom 
                Label nomLabel = new Label("Nom: " + event.getNom());
                nomLabel.getStyleClass().add("nom-label");
                nomLabel.setPrefWidth(1000);
                Label l = new Label("\n");
                Label l1 = new Label("\n");
                Label l2 = new Label("\n");

// add date 
                Label dateLabel = new Label("Date: " + event.getDate());
                dateLabel.getStyleClass().add("date-label");
                dateLabel.setPrefWidth(1000);

// add description 
                /*Label desc1Label = new Label("Description: " + event.getDescription());
                desc1Label.getStyleClass().add("desc1-label");
                desc1Label.setPrefWidth(1000);*/
// add type 
                Label typeLabel = new Label("Type: " + event.getType());
                typeLabel.getStyleClass().add("type-label");
                typeLabel.setPrefWidth(1000);
                double labelSpacing = 20; // adjust this value as needed

// add type 
                Id_event = event.getId();

//add Participer
                typeLabel.setPrefWidth(100);
                qrImageView.setTranslateY(imageView.getBoundsInParent().getMaxY() + labelSpacing);
                nomLabel.setTranslateY(qrImageView.getTranslateY() + qrImageView.getFitHeight() + labelSpacing);
                dateLabel.setTranslateY(nomLabel.getTranslateY() + nomLabel.getHeight() + labelSpacing);
                typeLabel.setTranslateY(dateLabel.getTranslateY() + dateLabel.getHeight() + labelSpacing);

                /* desc1Label.setTranslateY(desc1Label.getTranslateY() + desc1Label.getHeight() + labelSpacing);*/
                cardPane.getChildren().addAll(qrImageView, imageLabel, nomLabel, l, dateLabel, l1, typeLabel, l2);/*desc1Label*/

            } catch (UnsupportedEncodingException ex) {
                Logger.getLogger(EventFrontController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(EventFrontController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (WriterException ex) {
                Logger.getLogger(EventFrontController.class.getName()).log(Level.SEVERE, null, ex);
            }

            // Add the card pane to the flow pane
            typeCardsPane.getChildren().add(cardPane);

            // Set the style of the card pane
            cardPane.setPrefWidth(310);
            cardPane.setPrefHeight(310);
        }
        // TODO
    }

    @FXML
    private void gotoBack(ActionEvent event) throws IOException {
        Parent root;
        root = FXMLLoader.load(getClass().getResource("Back_event.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();

    }

}
