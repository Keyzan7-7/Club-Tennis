/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controlador;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafxmlapplication.JavaFXMLApplication;
import model.Club;
import model.ClubDAOException;
import model.Member;

/**
 * FXML Controller class
 *
 * @author keyza
 */
public class FMXLMainController implements Initializable {
    
    Member usr = null;
    @FXML
    private TextField CampoUsuario;
    @FXML
    private Button BotonRegistro;
    @FXML
    private TextField CampoContra;
    @FXML
    private Button BotonSesion;
    @FXML
    private Text textoRegistro;
    @FXML
    private Text TextoSesión;
    Club club = null;
    /**
     * Initializes the controller class.
     * @param url
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            // TODO
            club = Club.getInstance();
        } catch (ClubDAOException | IOException ex) {
            Logger.getLogger(FMXLMainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    

    @FXML
    private void Registrar(ActionEvent event) throws ClubDAOException, IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/Registrarse.fxml"));
        Parent root = loader.load();
        
        JavaFXMLApplication.setRoot(root);
    }

    @FXML
    private void IniciarSesion(ActionEvent event) {
        try{
        String user = CampoUsuario.getText();
        String pass = CampoContra.getText();
        System.out.println(user + pass);
        usr = club.getMemberByCredentials(user, pass);
        if(usr == null){
        textoRegistro.setText("Usuario o contraseña incorrecta");
        }
        else{
        textoRegistro.setText("Todo Correcto");
        }
        }catch(Exception e){
        textoRegistro.setText("Usuario o contraseña incorrecta");
        }
    }
    
}
