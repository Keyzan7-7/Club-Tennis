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
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import model.Club;
import model.ClubDAOException;
import model.Member;

/**
 * FXML Controller class
 *
 * @author keyza
 */
public class FMXLMainController implements Initializable {

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
    String nickName = "user99";
    String password = "123456X";
    String name = "user";
    String surname = "99";
    String credit = "0000111122223333";
    int svc=123;
    String tel= "666666666";
    
    Member result = club.registerMember(name, surname, tel, nickName, password, credit, svc, null);
    textoRegistro.setText("Me he fumado un pedazo de porro " + nickName);
    }

    @FXML
    private void IniciarSesion(ActionEvent event) {
        String user = CampoUsuario.getText();
        String pass = CampoContra.getText();
        Member usr = club.getMemberByCredentials(user, pass);
        if(usr == null){
        textoRegistro.setText("Usuario o contraseña incorrecta");
        }
        else{
        textoRegistro.setText("Todo Correcto");
        }
    }
    
}
