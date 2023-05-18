/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controlador;

import com.sun.javafx.scene.control.skin.Utils;
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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafxmlapplication.JavaFXMLApplication;
import model.Club;
import model.ClubDAOException;
import model.Member;
import java.util.*;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;

/**
 * FXML Controller class
 *
 * @author keyza
 */
public class prueba implements Initializable {
    
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
    private Button boton1;
    @FXML
    private Button boton11;
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

    @FXML
    private void calcularboton(MouseEvent event) {
    double x_ini = event.getSceneX();
    double  y_ini = event.getSceneY();
    System.out.println("x= " + x_ini + " y " + y_ini);
    System.out.println("column= " + Pane.getColumnCount()+ " row " + Pane.getRowCount());
    int x = columnCalc(Pane, (int) x_ini);
    int y = rowCalc(Pane, (int) y_ini);
    System.out.println( x + " " + y);
    }
    public int columnCalc(GridPane grid, int x) {
        int celdaWidth = (int)Pane.getWidth() / Pane.getColumnCount();
        return (int) ( x / celdaWidth);
    }
    public int rowCalc(GridPane grid, int y) {
    int celdaHeigth = (int)Pane.getHeight() / Pane.getRowCount();
    return (int) ( y / celdaHeigth);
}

    @FXML
    private void pulsar(ActionEvent event) {
        Node src = (Node)event.getSource();
        System.out.println("Row: "+ GridPane.getRowIndex(src));
        System.out.println("Column: "+ GridPane.getColumnIndex(src));
        
    }
}
