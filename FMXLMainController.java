/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controlador;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
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
    @FXML
    private GridPane Pane;
    @FXML
    private DatePicker calendario;
    /**
     * Initializes the controller class.
     * @param url
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            // TODO
            club = Club.getInstance();
            calendario.setValue(LocalDate.now());
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
        textoRegistro.setText("Usuario incorrecto");
        }
        else{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/VistaUser.fxml"));
        Parent root = loader.load();
        
        JavaFXMLApplication.setRoot(root);
        }
        }catch(Exception e){
        mostrarAlerta("Usuario o contraseña incorrecta");
        }
    }
    @FXML //metodo que cuando pulsas un botón del Pane te cambia el texto a reservado
     private void pulsar(ActionEvent event) {
        Node src = (Node)event.getSource();
        System.out.println("Row: "+ GridPane.getRowIndex(src));
        System.out.println("Column: "+ GridPane.getColumnIndex(src));
        int x = GridPane.getRowIndex(src);
        int y = GridPane.getColumnIndex(src);
        for(Node node : Pane.getChildren()){
        if (GridPane.getColumnIndex(node) == y && GridPane.getRowIndex(node) == x && node instanceof Button) {
        Button button = (Button) node;
        button.setText("Reservado");
        break;
    }
        }
    }

    @FXML
    private void calcularboton(MouseEvent event) {
    }
    public void mostrarAlerta(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Alerta");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
