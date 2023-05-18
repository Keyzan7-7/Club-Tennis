/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controlador;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafxmlapplication.JavaFXMLApplication;
import model.Club;
import model.ClubDAOException;

/**
 * FXML Controller class
 *
 * @author Franc
 */
public class RegistrarseController implements Initializable {

    @FXML
    private Button confirmarButton;
    @FXML
    private Button cancelButton;
    @FXML
    private TextField nombreField;
    @FXML
    private TextField apellidosField;
    @FXML
    private TextField telefonoField;
    @FXML
    private TextField nicknameField;
    @FXML
    private TextField passwordField;
    @FXML
    private TextField tarjetaField;
    @FXML
    private TextField svcField;
    @FXML
    private ImageView imagen;
    

    /**
     * Initializes the controller class.
     */
    
    private BooleanProperty nombreCorrect;
    private BooleanProperty apellidosCorrect;
    private BooleanProperty telefonoCorrect;
    private BooleanProperty nicknameCorrect;
    private BooleanProperty passwordCorrect;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        //camposCorrect = new SimpleBooleanProperty();
        nombreCorrect = new SimpleBooleanProperty();
        apellidosCorrect = new SimpleBooleanProperty();
        telefonoCorrect = new SimpleBooleanProperty();
        nicknameCorrect = new SimpleBooleanProperty();
        passwordCorrect = new SimpleBooleanProperty();
        
        //camposCorrect.setValue(Boolean.FALSE);
        nombreCorrect.setValue(Boolean.FALSE);
        apellidosCorrect.setValue(Boolean.FALSE);
        telefonoCorrect.setValue(Boolean.FALSE);
        nicknameCorrect.setValue(Boolean.FALSE);
        passwordCorrect.setValue(Boolean.FALSE);
        
        
        BooleanBinding camposCorrect = Bindings.and(nombreCorrect,apellidosCorrect);
        
        confirmarButton.disableProperty().bind(Bindings.not(camposCorrect));
    }    

    @FXML
    private void confirmar(ActionEvent event) throws IOException, ClubDAOException {
        
        Club.getInstance().registerMember(nombreField.getText(),apellidosField.getText(),telefonoField.getText(),nicknameField.getText()
                ,passwordField.getText(),tarjetaField.getText(),Integer.parseInt(svcField.getText()),imagen.getImage());
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/VistaMain_1.fxml"));
        Parent root = loader.load();
        
        JavaFXMLApplication.setRoot(root);
    }

    @FXML
    private void cancelar(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/VistaMain_1.fxml"));
        Parent root = loader.load();
        
        JavaFXMLApplication.setRoot(root);
        //cancelButton.getScene().getWindow().hide();
    }
    
}

