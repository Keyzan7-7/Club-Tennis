package controlador;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */


import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.media.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafxmlapplication.JavaFXMLApplication;
import model.Booking;
import model.Club;
import model.ClubDAOException;
import model.Court;
import model.Member;

/**
 * FXML Controller class
 *
 * @author keyza
 */
public class MisReservasController implements Initializable {
    Member miembro = null;
    @FXML
    private Button BotonReserva;
    @FXML
    private Button BotonMisReservas;
    @FXML
    private Button BotonPerfil;
    @FXML
    private Button BotonCierreSesión;
    @FXML
    private TableColumn<?, ?> c2;

    /**
     * Initializes the controller class.
     */
    public void initUs(Member mi){
    miembro = mi;
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void toReservar(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/VistaUser.fxml"));
        Parent root = loader.load();
        FMXLUserController controladorUsuario = loader.getController();
        controladorUsuario.initUser(miembro);
        JavaFXMLApplication.setRoot(root);
    }

    @FXML
    private void cierreSesion(ActionEvent event) {
    }
    
}
