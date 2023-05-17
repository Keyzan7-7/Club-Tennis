/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controlador;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafxmlapplication.JavaFXMLApplication;

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
    
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void confirmar(ActionEvent event) {
    }

    @FXML
    private void cancelar(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/VistaMain_1"));
        //Parent root = loader.load();
        
        cancelButton.getScene().getWindow().hide();
    }
    
}
