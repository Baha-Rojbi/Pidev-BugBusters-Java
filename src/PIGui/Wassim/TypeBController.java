/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PIGui.Wassim;

import PIClass.Type;
import PIServices.Wassim.TypeServices;
import PIUtils.MyConnection;
import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javax.swing.JOptionPane;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import static PIGui.Wassim.PIDev.createQR;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.image.Image;

/**
 * FXML Controller class
 *
 * @author 21651
 */
public class TypeBController implements Initializable {

    public ObservableList<Type> currentProduct = FXCollections.observableArrayList();
    @FXML
    private TextField typec;
    @FXML
    private Button btnAddNew;
    @FXML
    private Button btnUpdate;
    @FXML
    private Button btnDelete;
    @FXML
    private TableView<Type> tblViewCurrentStore;

    @FXML
    private TableColumn<Type, String> category;

    @FXML
    private TableColumn<Type, Integer> ID;
    @FXML
    private Button showbtn1;
    @FXML
    private TextField id;
    @FXML
    private TextField tfSearch;
    private Button btnAddNew1;
    @FXML
    private Button excel;
    @FXML
    private ImageView qrcode;
    @FXML
    private TextField desc1;
    @FXML
    private TableColumn<Type, String> description;


    public void affiche() {

        ObservableList<Type> c = (ObservableList<Type>) new TypeServices().getAll();

        /*ID.setCellValueFactory(new PropertyValueFactory<>("id"));*/
        category.setCellValueFactory(new PropertyValueFactory<>("category"));
        description.setCellValueFactory(new PropertyValueFactory<>("description"));
        tblViewCurrentStore.setItems(c);

    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        String query = null;
        Connection connection = null;
        TypeServices cr = new TypeServices();
        ObservableList<Type> listtype = cr.getAll();
        /*ID.setCellValueFactory(new PropertyValueFactory<Type, Integer>("id"));*/
        category.setCellValueFactory(new PropertyValueFactory<>("category"));
        description.setCellValueFactory(new PropertyValueFactory<>("description"));
        tblViewCurrentStore.setItems(listtype);

        btnAddNew.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                Type p = new Type();

                p.setCategory(typec.getText());
                String categ = p.getCategory();
                p.setDescription(desc1.getText());
                String des = p.getDescription();
                if (categ.equals("")) {
                    JOptionPane.showMessageDialog(null, "Veuillez remplir le champ");

                    return;
                } else if (des.equals("")) {
                    JOptionPane.showMessageDialog(null, "Veuillez remplir le champ");

                    return;
                } else {

                    JOptionPane.showMessageDialog(null, "Ajout avec succès");

                }

                new TypeServices().ajouter(p);
                affiche();
            }
        });
    }

    @FXML
    private void btnAddNewOnAction(ActionEvent event) {

    }

    @FXML
    private void btnUpdateOnAction(ActionEvent event) {

        int idd = Integer.parseInt(id.getText());

        String cat = typec.getText();
        String descc = desc1.getText();
        TypeServices s = new TypeServices();

        s.update(idd, cat, descc);

        affiche();

    }

    @FXML
    private void btnDeleteOnAction(ActionEvent event) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Voulez vous suprimer ce Type Event ?");
        Optional<ButtonType> result = alert.showAndWait();

        System.out.println(result.get().getText().equals("Annuler"));
        if (result.get().getText().equals("Annuler")) {
            System.out.println("qqqqq");
            JOptionPane.showMessageDialog(null, "Type Event n'a pas été supprimé");

            return;
        }

        Type c = tblViewCurrentStore.getSelectionModel().getSelectedItem();
        TypeServices crs = new TypeServices();

        crs.supprimer(c);
        affiche();

    }

    @FXML
    private void btnselect(ActionEvent event) {
        Type s = tblViewCurrentStore.getSelectionModel().getSelectedItem();
        desc1.setText(s.getDescription());
        typec.setText(s.getCategory());
        id.setText(String.valueOf(s.getId()));
    }

    private void tfSearchOnKeyRelese(KeyEvent event) {
        String t = tfSearch.getText();

        search(t);
    }

    public void search(String t) {
        System.out.println("CLCKED");
        ArrayList<Type> l;
        TypeServices cp = new TypeServices();

        l = cp.getTypesByCategoryOrID(t);
        currentProduct.clear();
        currentProduct.addAll(l);
        tblViewCurrentStore.setItems(currentProduct);

        category.setCellValueFactory(new PropertyValueFactory<>("category"));
        description.setCellValueFactory(new PropertyValueFactory<>("description"));
    }

    private void events(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("EventB.fxml"));
        try {
            Parent root = loader.load();
            EventBController apc = loader.getController();
            tblViewCurrentStore.getScene().setRoot(root);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private void types(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("TypeB.fxml"));
        try {
            Parent root = loader.load();
            TypeBController apc = loader.getController();
            tblViewCurrentStore.getScene().setRoot(root);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

  

    @FXML
    private void excelmth(ActionEvent event) {
        Connection connection = MyConnection.getInstance().getCnx();

        try {

            String filename = "C:\\Users\\21651\\Desktop\\Intégration\\PI\\excel\\data.xls";
            HSSFWorkbook hwb = new HSSFWorkbook();
            HSSFSheet sheet = hwb.createSheet("new sheet");

            HSSFRow rowhead = sheet.createRow((short) 0);
            rowhead.createCell((short) 0).setCellValue("Category");

            Connection cnx = MyConnection.getInstance().getCnx();
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery("select * from type");
            int i = 1;
            while (rs.next()) {
                HSSFRow row = sheet.createRow((short) i);

                row.createCell((short) 0).setCellValue(rs.getString("category"));
                row.createCell((short) 1).setCellValue(rs.getString("description"));
                i++;
            }
            FileOutputStream fileOut = new FileOutputStream(filename);
            hwb.write(fileOut);
            fileOut.close();
            System.out.println("Your excel file has been generated!");
            File file = new File(filename);
            if (file.exists()) {
                if (Desktop.isDesktopSupported()) {
                    Desktop.getDesktop().open(file);
                }
            }

        } catch (Exception ex) {
            System.out.println(ex);

        }

    }

    @FXML
    private void triDSC(ActionEvent event) throws WriterException, FileNotFoundException {
        TypeServices eventC = new TypeServices();

        ObservableList<Type> l = (ObservableList<Type>) new TypeServices().TriTypeAs();
        ID.setCellValueFactory(new PropertyValueFactory<Type, Integer>("id"));

        category.setCellValueFactory(new PropertyValueFactory<Type, String>("category"));

        description.setCellValueFactory(new PropertyValueFactory<Type, String>("description"));

        tblViewCurrentStore.setItems(l);

    }

    @FXML
    private void QR(ActionEvent event) throws WriterException, FileNotFoundException {
        Type pt = tblViewCurrentStore.getSelectionModel().getSelectedItem();

        System.out.println(pt.getCategory());
        String value = pt.getCategory();

        String data = value;
//     // The path where the image will get saved
        String path = "C:\\Users\\21651\\Desktop\\Intégration\\PI\\QRCODE\\" + data + ".png";

//  // Encoding charset
        String charset = "UTF-8";

        Map<EncodeHintType, ErrorCorrectionLevel> hashMap
                = new HashMap<>();

        BitMatrix matrix;
        try {
            matrix = new MultiFormatWriter().encode(
                    new String(data.getBytes(charset), charset),
                    BarcodeFormat.QR_CODE, 200, 200);
            MatrixToImageWriter.writeToFile(
                    matrix,
                    path.substring(path.lastIndexOf('.') + 1),
                    new File(path));
            hashMap.put(EncodeHintType.ERROR_CORRECTION,
                    ErrorCorrectionLevel.L);

            // Create the QR code and save
            // in the specified folder
            // as a jpg file
            createQR(data, path, charset, hashMap, 200, 200);
            System.out.println("QR Code Generated!!! ");
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(TypeBController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(TypeBController.class.getName()).log(Level.SEVERE, null, ex);
        }

        InputStream stream;
        stream = new FileInputStream("C:\\Users\\21651\\Desktop\\Intégration\\PI\\QRCODE\\" + data + ".png");
        Image image = new Image(stream);
////Creating the image view
////Setting image to the image view
        qrcode.setImage(image);

    }

    @FXML
    private void Back(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("Back_event.fxml"));
        Parent root = loader.load();
        btnUpdate.getScene().setRoot(root);

    }

}
