/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controlador;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafxmlapplication.JavaFXMLApplication;
import model.Club;
import model.ClubDAOException;
import model.Member;

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
    private BooleanProperty tarjetaCorrect;
    private BooleanProperty svcCorrect;
    @FXML
    private Label svcLabel;
    
    private static final String textoIncorrecto = "-fx-text-fill: red; -fx-underline: true; -fx-border-color: red; -fx-border-radius: 3px;";

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        nombreCorrect = new SimpleBooleanProperty();
        apellidosCorrect = new SimpleBooleanProperty();
        telefonoCorrect = new SimpleBooleanProperty();
        nicknameCorrect = new SimpleBooleanProperty();
        passwordCorrect = new SimpleBooleanProperty();
        tarjetaCorrect = new SimpleBooleanProperty();
        svcCorrect = new SimpleBooleanProperty();
        
        nombreCorrect.setValue(Boolean.FALSE);
        apellidosCorrect.setValue(Boolean.FALSE);
        telefonoCorrect.setValue(Boolean.FALSE);
        nicknameCorrect.setValue(Boolean.FALSE);
        passwordCorrect.setValue(Boolean.FALSE);
        tarjetaCorrect.setValue(Boolean.FALSE);
        svcCorrect.setValue(Boolean.FALSE);
        
        BooleanBinding camposCorrect = Bindings.and(nombreCorrect,apellidosCorrect);
        
        svcLabel.setDisable(true);
        svcField.setDisable(true);
        confirmarButton.disableProperty().bind(Bindings.not(camposCorrect));
        //svcLabel.disableProperty().bind(Bindings.not(camposCorrect));
        //svcField.disableProperty().bind(Bindings.not(camposCorrect));
        
        nicknameField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            
            if(!newValue && !nicknameField.getText().equals("")){
                if (nicknameField.getText().contains(" ")) {
                    nicknameField.setStyle(textoIncorrecto);
                }
                boolean valido;
                try {
                    valido = existeNickName(nicknameField.getText());
                    
                    if (valido) {
                    telefonoField.setStyle(textoIncorrecto);
                   //mostrarAlerta("Número de teléfono no válido");
                }
                } catch (ClubDAOException ex) {
                    Logger.getLogger(RegistrarseController.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(RegistrarseController.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            }
        });
        
        nicknameField.textProperty().addListener((observable, oldValue, newValue) -> {
            if(!newValue.equals("")){
                if(!nicknameField.getText().contains(" ")){
                    nicknameField.setStyle("");
                }
                try {
                    if(existeNickName(newValue)){
                        nicknameField.setStyle("");
                    }
                } catch (ClubDAOException ex) {
                    Logger.getLogger(RegistrarseController.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(RegistrarseController.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else{
                nicknameField.setStyle("");
            }
        });
        
        telefonoField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if(!newValue && !telefonoField.getText().equals("")){
                boolean valido = validarNumeroTelefono(telefonoField.getText());
                if (!valido) {
                    telefonoField.setStyle(textoIncorrecto);
                   //mostrarAlerta("Número de teléfono no válido");
                }
            }
        });
        
        telefonoField.textProperty().addListener((observable, oldValue, newValue) -> {
            if(!newValue.equals("")){
                if(validarNumeroTelefono(newValue)){
                    telefonoField.setStyle("");
                }
            } else{
                telefonoField.setStyle("");
            }
        });
        
        tarjetaField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if(!newValue && !tarjetaField.getText().equals("")){
                boolean valido = validarNumeroTarjeta(tarjetaField.getText());
                if (!valido) {
                    tarjetaField.setStyle(textoIncorrecto);
                   mostrarAlerta("Número de teléfono no válido");
                }
            }
        });
        
        tarjetaField.textProperty().addListener((observable, oldValue, newValue) -> {
            if(!newValue.equals("")){
                svcLabel.setDisable(false);
                svcField.setDisable(false);
                if(validarNumeroTarjeta(newValue)){
                    tarjetaField.setStyle("");
                }
            } else{
                svcLabel.setDisable(true);
                svcField.setDisable(true);
                tarjetaField.setStyle("");
            }
        });
        
        svcField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if(!newValue && !svcField.getText().equals("")){
                boolean valido = validarNumeroSVC(svcField.getText());
                if (!valido) {
                   svcField.setStyle(textoIncorrecto);
                   //mostrarAlerta("Número de teléfono no válido");
                } else {
                   svcField.setStyle("");
                }
            }
        });
        
        svcField.textProperty().addListener((observable, oldValue, newValue) -> {
            if(!newValue.equals("")){
                if(validarNumeroSVC(newValue)){
                    svcField.setStyle("");
                }
            } else{
                svcField.setStyle("");
            }
        });
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
    
    public boolean existeNickName(String nickname) throws ClubDAOException, IOException {
         ArrayList<Member> miembros = (ArrayList<Member>) Club.getInstance().getMembers();
         for(int i = 0; i < miembros.size(); i++){
             if(nickname.equals(miembros.get(i).getNickName())){
                 return true;
             }  
         }
         return false;
    }
    
    public boolean validarNumeroTelefono(String numeroTelefono) {
         String patron = "\\d*";
         return (numeroTelefono.matches(patron));
    }
    
    public boolean validarNumeroTarjeta(String numeroTelefono) {
         String patron = "\\d{4}-\\d{4}-\\d{4}-\\d{4}";
         String patron2 = "\\d{16}";
         String patron3 = "\\d{4} \\d{4} \\d{4} \\d{4}";
         String patron4 = "\\d{4}/\\d{4}/\\d{4}/\\d{4}";
         return (numeroTelefono.matches(patron) || numeroTelefono.matches(patron2)
                 || numeroTelefono.matches(patron3) || numeroTelefono.matches(patron4));
    }
     
    public boolean validarNumeroSVC(String numeroSVC) {
         String patron = "\\d{3}";
         return numeroSVC.matches(patron);
    }
     
    public void mostrarAlerta(String mensaje) {
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle("Alerta");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
    
    public void addTextLimiter(final TextField tf, final int maxLength) {
        tf.textProperty().addListener((final ObservableValue<? extends String> ov, final String oldValue, final String newValue) -> {
            if (tf.getText().length() > maxLength) {
                String s = tf.getText().substring(0, maxLength);
                tf.setText(s);
            }
        });
    }
}
