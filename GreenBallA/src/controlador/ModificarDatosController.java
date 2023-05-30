/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controlador;


import java.awt.Paint;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Set;
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
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.util.Duration;
import javafx.util.converter.IntegerStringConverter;
import javafxmlapplication.JavaFXMLApplication;
import model.Club;
import model.ClubDAOException;
import model.Member;

/**
 * FXML Controller class
 *
 * @author Franc
 */
public class ModificarDatosController implements Initializable {

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
    

    /**
     * Initializes the controller class.
     */
    
    private BooleanProperty nombreCorrect;
    private BooleanProperty apellidosCorrect;
    private BooleanProperty telefonoCorrect;
    private BooleanProperty nicknameCorrect;
    private BooleanProperty passwordCorrect;
    private BooleanProperty reppasswordCorrect;
    private BooleanProperty tarjetaCorrect;
    private BooleanProperty svcCorrect;
    
    Image imagen = null;
    @FXML
    private Label svcLabel;
    
    private static final String textoIncorrecto = "-fx-text-fill: red; -fx-underline: true; -fx-border-color: red; -fx-border-radius: 3px;";
    private static final String circularImageView = " -fx-background-image: url('/imagenes/prueba.jpg');";
    
    @FXML
    private Button botonImagen;
    @FXML
    private Circle circuloAvatar;
    @FXML
    private CheckBox showPass;
    @FXML
    private TextField passText;
    @FXML
    private TextField reppassText;
    @FXML
    private PasswordField reppasswordField;
    @FXML
    private CheckBox showRepPass;
    @FXML
    private Tooltip nicknameTip;
    @FXML
    private Tooltip passTextTip;
    @FXML
    private Tooltip passwordTip;
    @FXML
    private Tooltip tarjetaTip;
    @FXML
    private Tooltip svcTip;
    @FXML
    private Tooltip reppassTextTip;
    @FXML
    private Tooltip reppasswordTip;
    @FXML
    private Label nicknameLabel;
    @FXML
    private Label passwordLabel;
    @FXML
    private Label reppasswordLabel;
    @FXML
    private Label tarjetaLabel;
    @FXML
    private Label repPassFieldLabel;
    @FXML
    private Label svcFieldLabel;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //
        Image image = new Image("/imagenes/default.png");
        //Image aux = getRoundedImage(image, 120);
        //imagen.setImage(aux);
        circuloAvatar.setFill(new ImagePattern(image));
        
        nombreCorrect = new SimpleBooleanProperty();
        apellidosCorrect = new SimpleBooleanProperty();
        telefonoCorrect = new SimpleBooleanProperty();
        nicknameCorrect = new SimpleBooleanProperty();
        passwordCorrect = new SimpleBooleanProperty();
        reppasswordCorrect = new SimpleBooleanProperty();
        tarjetaCorrect = new SimpleBooleanProperty();
        svcCorrect = new SimpleBooleanProperty();
        
        nombreCorrect.setValue(Boolean.FALSE);
        apellidosCorrect.setValue(Boolean.FALSE);
        telefonoCorrect.setValue(Boolean.FALSE);
        nicknameCorrect.setValue(Boolean.FALSE);
        passwordCorrect.setValue(Boolean.FALSE);
        reppasswordCorrect.setValue(Boolean.FALSE);
        tarjetaCorrect.setValue(Boolean.TRUE);
        svcCorrect.setValue(Boolean.TRUE);
        
        BooleanBinding passwordsCorrect = passwordCorrect.and(reppasswordCorrect);
        BooleanBinding opcionalesCorrect = tarjetaCorrect.and(svcCorrect);
        BooleanBinding camposCorrect = nombreCorrect.and(apellidosCorrect).and(telefonoCorrect).and(nicknameCorrect).and(passwordsCorrect).and(opcionalesCorrect);
       
        reppasswordField.setDisable(true);
        reppassText.setDisable(true);
        repPassFieldLabel.setDisable(true);
        showRepPass.setDisable(true);
        svcFieldLabel.setDisable(true);
        svcField.setDisable(true);
        confirmarButton.disableProperty().bind(Bindings.not(camposCorrect));
       
        
        nicknameTip.setShowDelay(Duration.millis(100));
        passwordTip.setShowDelay(Duration.millis(100));
        passTextTip.setShowDelay(Duration.millis(100));
        reppasswordTip.setShowDelay(Duration.millis(100));
        reppassTextTip.setShowDelay(Duration.millis(100));
        tarjetaTip.setShowDelay(Duration.millis(100));
        svcTip.setShowDelay(Duration.millis(100));
        
        nicknameTip.setShowDuration(Duration.ZERO);
        passwordTip.setShowDuration(Duration.ZERO);
        passTextTip.setShowDuration(Duration.ZERO);
        reppasswordTip.setShowDuration(Duration.ZERO);
        reppassTextTip.setShowDuration(Duration.ZERO);
        tarjetaTip.setShowDuration(Duration.ZERO);
        svcTip.setShowDuration(Duration.ZERO);
        
        passwordTip.setText("Formato incorrecto. Introduzca una combinación \nde letras y números de mínimo 6 carácteres.");
        passTextTip.setText("Formato incorrecto. Introduzca una combinación \nde letras y números de mínimo 6 carácteres.");
        
        //soloLetras(nombreField);
        
        //soloLetras(apellidosField);
        
        soloNumeros(telefonoField, 3);
        //añadirEspacio(telefonoField, 3);
        
        soloNumeros(tarjetaField, 4);
        addTextLimiter(tarjetaField, 16);
        //añadirEspacio(tarjetaField, 4);
        //formato(tarjetaField, 0);
        
        soloNumeros(svcField, 3);
        addTextLimiter(svcField, 3);
        
        
        nombreField.textProperty().addListener((observable, oldValue, newValue) -> {
            /*if(apellidosCorrect.getValue() && telefonoCorrect.getValue() && nicknameCorrect.getValue() && 
                    passwordCorrect.getValue() && tarjetaCorrect.getValue() && svcCorrect.getValue()){
                if(!newValue.equals("")){
                    nombreCorrect.setValue(Boolean.TRUE);
                    nombreField.setStyle("");
                } else{
                    nombreCorrect.setValue(Boolean.FALSE);
                }
            } else{
                nombreCorrect.setValue(Boolean.FALSE);
            } */
            
            if(!newValue.equals("")){
                nombreCorrect.setValue(Boolean.TRUE);
            } else{
                nombreCorrect.setValue(Boolean.FALSE);
            }
        });
        
        apellidosField.textProperty().addListener((observable, oldValue, newValue) -> {
            if(!newValue.equals("")){
                apellidosCorrect.setValue(Boolean.TRUE);
            } else{
                apellidosCorrect.setValue(Boolean.FALSE);
            }
        });
        
        /*telefonoField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if(!newValue && !telefonoField.getText().equals("")){
                boolean valido = validarNumeroTelefono(telefonoField.getText());
                if (!valido) {
                    telefonoField.setStyle(textoIncorrecto);
                    telefonoCorrect.setValue(Boolean.FALSE);
                   //mostrarAlerta("Número de teléfono no válido");
                } else{
                    telefonoCorrect.setValue(Boolean.TRUE);
                }
            }
        });*/
        
        telefonoField.textProperty().addListener((observable, oldValue, newValue) -> {
            /*if(!newValue.equals("")){
                if(validarNumeroTelefono(newValue)){
                    telefonoCorrect.setValue(Boolean.TRUE);
                    telefonoField.setStyle("");
                }
            } else{
                telefonoCorrect.setValue(Boolean.FALSE);
                telefonoField.setStyle("");
            }*/
            if(!newValue.equals("")){
                telefonoCorrect.setValue(Boolean.TRUE);
            } else{
                telefonoCorrect.setValue(Boolean.FALSE);
            }
        });
        
        nicknameField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if(!newValue && !nicknameField.getText().equals("")){
                try {
                    boolean existe;
                    //existe = existeNickName(nicknameField.getText());
                    existe = Club.getInstance().existsLogin(nicknameField.getText());
                    if (nicknameField.getText().contains(" ")) {
                        nicknameField.setStyle(textoIncorrecto);
                        nicknameCorrect.setValue(Boolean.FALSE);
                    } else if (existe) {
                        nicknameTip.setText("El Nickname ya está usado.");
                        nicknameTip.setShowDuration(Duration.INDEFINITE);
                        nicknameField.setStyle(textoIncorrecto);
                        nicknameCorrect.setValue(Boolean.FALSE);
                        //mostrarAlerta("Número de teléfono no válido");
                    } else{
                        nicknameCorrect.setValue(Boolean.TRUE);
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
                if(newValue.contains(" ")){
                    nicknameField.setStyle(textoIncorrecto);
                    nicknameLabel.setText("Formato incorrecto. El Nickname no \npuede contener espacios.");
                    nicknameLabel.setPrefHeight(35);
                    nicknameTip.setText("Formato incorrecto. El Nickname no puede contener espacios.");
                    nicknameTip.setShowDuration(Duration.INDEFINITE);
               /* } else if(existeNickName(newValue)){
                    nicknameTip.setText("El Nickname ya está usado.");
                    nicknameTip.setShowDuration(Duration.INDEFINITE);
                    nicknameField.setStyle(textoIncorrecto);*/
                } else{
                    nicknameLabel.setPrefHeight(0);
                    nicknameTip.setShowDuration(Duration.ZERO);
                    nicknameCorrect.setValue(Boolean.TRUE);
                    nicknameField.setStyle("");
                }
            } else{
                nicknameLabel.setPrefHeight(0);
                nicknameTip.setShowDuration(Duration.ZERO);
                nicknameCorrect.setValue(Boolean.FALSE);
                nicknameField.setStyle("");
            }
        });
        
        passwordField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if(!newValue && !passwordField.getText().equals("")){
                if (validarPassword(passwordField.getText())) {
                    passwordCorrect.setValue(Boolean.TRUE);
                    passwordField.setStyle("");
                } else{
                    passwordCorrect.setValue(Boolean.FALSE);
                    passwordLabel.setPrefHeight(35);
                    passwordTip.setShowDuration(Duration.INDEFINITE);
                    passTextTip.setShowDuration(Duration.INDEFINITE);
                    passwordField.setStyle(textoIncorrecto);
                    passText.setStyle(textoIncorrecto);
                }
                
            }
        });
        
        passwordField.textProperty().addListener((observable, oldValue, newValue) -> {
            passText.setText(newValue);
            if(!newValue.equals("")){
                repPassFieldLabel.setDisable(false);
                reppassText.setDisable(false);
                reppasswordField.setDisable(false);
                showRepPass.setDisable(false);
            }
            
            if(validarPassword(newValue)){
                passwordLabel.setPrefHeight(0);
                passwordTip.setShowDuration(Duration.ZERO);
                passTextTip.setShowDuration(Duration.ZERO);
                passwordCorrect.setValue(Boolean.TRUE);
                passwordField.setStyle("");
                passText.setStyle("");
            } else if(newValue.equals("")){
                passwordLabel.setPrefHeight(0);
                passTextTip.setShowDuration(Duration.ZERO);
                passwordTip.setShowDuration(Duration.ZERO);
                passwordCorrect.setValue(Boolean.FALSE);
                passwordField.setStyle("");
                passText.setStyle("");
                
                reppasswordLabel.setPrefHeight(0);
                reppasswordTip.setShowDuration(Duration.ZERO);
                reppassTextTip.setShowDuration(Duration.ZERO);
                reppasswordField.setStyle("");
                reppassText.setStyle("");
                repPassFieldLabel.setDisable(true);
                reppassText.setDisable(true);
                reppasswordField.setDisable(true);
                showRepPass.setDisable(true);
            }
            
            if((!reppasswordField.getText().equals("") || !reppassText.getText().equals("")) &&
                (newValue.equals(reppasswordField.getText()) || newValue.equals(reppassText.getText()))){
                    reppasswordCorrect.setValue(Boolean.TRUE);
                    reppasswordLabel.setPrefHeight(0);
                    reppasswordTip.setShowDuration(Duration.ZERO);
                    reppassTextTip.setShowDuration(Duration.ZERO);
                    reppasswordField.setStyle("");
                    reppassText.setStyle("");
            } else if((!reppasswordField.getText().equals("") || !reppassText.getText().equals("")) && !newValue.equals("")){
                reppasswordCorrect.setValue(Boolean.FALSE);
                reppasswordLabel.setPrefHeight(20);
                reppasswordTip.setShowDuration(Duration.INDEFINITE);
                reppassTextTip.setShowDuration(Duration.INDEFINITE);
                reppasswordField.setStyle(textoIncorrecto);
                reppassText.setStyle(textoIncorrecto);
            }
        });
        
        passText.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if(!newValue && !passText.getText().equals("")){
                if (validarPassword(passText.getText())) {
                    passwordCorrect.setValue(Boolean.TRUE);
                    passwordField.setStyle("");
                } else{
                    passwordCorrect.setValue(Boolean.FALSE);
                    passwordLabel.setPrefHeight(35);
                    passwordTip.setShowDuration(Duration.INDEFINITE);
                    passTextTip.setShowDuration(Duration.INDEFINITE);
                    passwordField.setStyle(textoIncorrecto);
                    passText.setStyle(textoIncorrecto);
                }
                
            }
        });
        
        passText.textProperty().addListener((observable, oldValue, newValue) -> {
            passwordField.setText(newValue);
            if(validarPassword(newValue)){
                passwordLabel.setPrefHeight(0);
                passwordTip.setShowDuration(Duration.ZERO);
                passTextTip.setShowDuration(Duration.ZERO);
                passwordCorrect.setValue(Boolean.TRUE);
                passwordField.setStyle("");
                passText.setStyle("");
            } else if(newValue.equals("")){
                passwordLabel.setPrefHeight(0);
                passTextTip.setShowDuration(Duration.ZERO);
                passwordTip.setShowDuration(Duration.ZERO);
                passwordCorrect.setValue(Boolean.FALSE);
                passwordField.setStyle("");
                passText.setStyle("");
                
                reppasswordLabel.setPrefHeight(0);
                reppasswordTip.setShowDuration(Duration.ZERO);
                reppassTextTip.setShowDuration(Duration.ZERO);
                reppasswordField.setStyle("");
                reppassText.setStyle("");
            }
            
            if(!reppasswordField.getText().equals("") &&
                newValue.equals(reppasswordField.getText())){
                reppasswordCorrect.setValue(Boolean.TRUE);
                reppasswordLabel.setPrefHeight(0);
                reppasswordTip.setShowDuration(Duration.ZERO);
                reppassTextTip.setShowDuration(Duration.ZERO);
                reppasswordField.setStyle("");
                reppassText.setStyle("");
            } else if(!reppasswordField.getText().equals("") && !newValue.equals("")){
                reppasswordCorrect.setValue(Boolean.FALSE);
                reppasswordLabel.setPrefHeight(0);
                reppasswordTip.setShowDuration(Duration.INDEFINITE);
                reppassTextTip.setShowDuration(Duration.INDEFINITE);
                reppasswordField.setStyle(textoIncorrecto);
                reppassText.setStyle(textoIncorrecto);
            }
        });
        
        reppasswordField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if(!newValue && !reppasswordField.getText().equals("") && !passwordField.getText().equals("")){
                if (reppasswordField.getText().equals(passwordField.getText())) {
                    reppasswordCorrect.setValue(Boolean.TRUE);
                    
                } else{
                    reppasswordCorrect.setValue(Boolean.FALSE);
                    reppasswordLabel.setPrefHeight(20);
                    reppasswordTip.setShowDuration(Duration.INDEFINITE);
                    reppassTextTip.setShowDuration(Duration.INDEFINITE);
                    reppasswordField.setStyle(textoIncorrecto);
                    reppassText.setStyle(textoIncorrecto);
                }
                
            }
        });
        
        reppasswordField.textProperty().addListener((observable, oldValue, newValue) -> {
           //reppassText.setText(newValue);
            if(newValue.equals(passwordField.getText())){
                reppasswordCorrect.setValue(Boolean.TRUE);
                reppasswordLabel.setPrefHeight(0);
                reppasswordTip.setShowDuration(Duration.ZERO);
                reppassTextTip.setShowDuration(Duration.ZERO);
                reppasswordField.setStyle("");
                reppassText.setStyle("");
            } else if(newValue.equals("") && reppassText.getText().equals("")){
                reppasswordCorrect.setValue(Boolean.FALSE);
                reppasswordLabel.setPrefHeight(0);
                reppasswordTip.setShowDuration(Duration.ZERO);
                reppassTextTip.setShowDuration(Duration.ZERO);
                reppasswordField.setStyle("");
                reppassText.setStyle("");
            }
        });
        
        reppassText.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if(!newValue){
                if(reppassText.getText().equals(passwordField.getText()) && !passwordField.getText().equals("")){
                    reppasswordCorrect.setValue(Boolean.TRUE);
                } else if (!passwordField.getText().equals("")){
                    reppasswordCorrect.setValue(Boolean.FALSE);
                    reppasswordLabel.setPrefHeight(20);
                    reppasswordTip.setShowDuration(Duration.INDEFINITE);
                    reppassTextTip.setShowDuration(Duration.INDEFINITE);
                    reppasswordField.setStyle(textoIncorrecto);
                    reppassText.setStyle(textoIncorrecto);
                }
            }
        });
        
        reppassText.textProperty().addListener((observable, oldValue, newValue) -> {
            //reppasswordField.setText(newValue);
            if(newValue.equals("") && reppasswordField.getText().equals("")){
                reppasswordCorrect.setValue(Boolean.FALSE);
                reppasswordLabel.setPrefHeight(0);
                reppasswordTip.setShowDuration(Duration.ZERO);
                reppassTextTip.setShowDuration(Duration.ZERO);
                reppasswordField.setStyle("");
                reppassText.setStyle("");
            } else if(!newValue.equals("") && newValue.equals(passwordField.getText())){
                reppasswordCorrect.setValue(Boolean.TRUE);
                reppasswordLabel.setPrefHeight(0);
                reppasswordTip.setShowDuration(Duration.ZERO);
                reppassTextTip.setShowDuration(Duration.ZERO);
                reppasswordField.setStyle("");
                reppassText.setStyle("");
            }
        });
        
        tarjetaField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if(!newValue && !tarjetaField.getText().equals("")){
                boolean valido = validarNumeroTarjeta(tarjetaField.getText());
                if (!valido) {
                    tarjetaLabel.setPrefHeight(20);
                    tarjetaTip.setShowDuration(Duration.INDEFINITE);
                    tarjetaField.setStyle(textoIncorrecto);
                    tarjetaCorrect.setValue(Boolean.FALSE);
                   //mostrarAlerta("Número de teléfono no válido");
                } else{
                    tarjetaCorrect.setValue(Boolean.TRUE);
                }
            }
        });
        
        tarjetaField.textProperty().addListener((observable, oldValue, newValue) -> {
            if(!newValue.equals("")){
                svcFieldLabel.setDisable(false);
                svcField.setDisable(false);
                if(svcField.getText().equals("")){
                    svcCorrect.setValue(Boolean.FALSE);
                } else{
                    if(validarNumeroSVC(svcField.getText())){
                        svcCorrect.setValue(Boolean.TRUE);
                        svcLabel.setPrefHeight(0);
                        svcTip.setShowDuration(Duration.ZERO);
                        svcField.setStyle("");
                    } else{
                        svcCorrect.setValue(Boolean.FALSE);
                        svcLabel.setPrefHeight(20);
                        svcTip.setShowDuration(Duration.INDEFINITE);
                        svcField.setStyle(textoIncorrecto);
                    }
                }
                /*if(!validarNumeroSVC(svcField.getText()) && !svcField.getText().equals("")){
                    svcField.setStyle(textoIncorrecto);
                }*/
                //añadirEspacio(tarjetaField);
                if(validarNumeroTarjeta(newValue)){
                    tarjetaCorrect.setValue(Boolean.TRUE);
                    tarjetaLabel.setPrefHeight(0);
                    tarjetaTip.setShowDuration(Duration.ZERO);
                    tarjetaField.setStyle("");
                }
            } else{
                svcFieldLabel.setDisable(true);
                svcField.setDisable(true);
                tarjetaCorrect.setValue(Boolean.TRUE);
                svcCorrect.setValue(Boolean.TRUE);
                tarjetaLabel.setPrefHeight(0);
                svcLabel.setPrefHeight(0);
                tarjetaTip.setShowDuration(Duration.ZERO);
                svcTip.setShowDuration(Duration.ZERO);
                svcField.setStyle("");
                tarjetaField.setStyle("");
            }
        });
        
        svcField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if(!newValue && !svcField.getText().equals("")){
                boolean valido = validarNumeroSVC(svcField.getText());
                if (!valido) {
                   svcField.setStyle(textoIncorrecto);
                   svcCorrect.setValue(Boolean.FALSE);
                   svcLabel.setPrefHeight(20);
                   svcTip.setShowDuration(Duration.INDEFINITE);
                   //mostrarAlerta("Número de teléfono no válido");
                } else {
                   svcCorrect.setValue(Boolean.TRUE);
                }
            }
        });
        
        svcField.textProperty().addListener((observable, oldValue, newValue) -> {
            if(!newValue.equals("")){
                if(validarNumeroSVC(newValue)){
                    svcField.setStyle("");
                    svcLabel.setPrefHeight(0);
                    svcTip.setShowDuration(Duration.ZERO);
                    svcCorrect.setValue(Boolean.TRUE);
                }
            } else{
                svcField.setStyle("");
                svcLabel.setPrefHeight(0);
                svcTip.setShowDuration(Duration.ZERO);
                svcCorrect.setValue(Boolean.FALSE);
            }
        });
    }    

    @FXML
    private void confirmar(ActionEvent event) throws IOException, ClubDAOException, InterruptedException {
        if(tarjetaField.getText().equals("")){
            Club.getInstance().registerMember(nombreField.getText(),apellidosField.getText(),telefonoField.getText(),nicknameField.getText()
                ,passwordField.getText(),null, 0,imagen);
        } else{
        Club.getInstance().registerMember(nombreField.getText(),apellidosField.getText(),telefonoField.getText(),nicknameField.getText()
                ,passwordField.getText(),tarjetaField.getText(),Integer.parseInt(svcField.getText()),imagen);
        }
        
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Registrarse");
        //alert.setHeaderText(null);
        alert.setHeaderText("¡Usuario registrado!");
        alert.setContentText("¡Operación realizado con éxito! Se ha registrado \ncorrectamente en el sistema.");
        alert.showAndWait();
        
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
    
    public boolean existeNickName(String nickname)  {
        ArrayList<Member> miembros;
        try {
            miembros = (ArrayList<Member>) Club.getInstance().getMembers();
            
            for(int i = 0; i < miembros.size(); i++){
                if(nickname.equals(miembros.get(i).getNickName())){
                    return true;
                }  
            }  
        } catch (Exception ex) {
        }
         return false;
    }
    
    public boolean validarNumeroTelefono(String numeroTelefono) {
         //String patron = "(\\d{3}\\s?)+";
         String patron = "\\d*";
         return (numeroTelefono.matches(patron));
    }
    
    public boolean validarPassword(String password){
        String patron =  "^[A-Za-z0-9]{6,}$"; 
         return (password.matches(patron));
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
    
    public void soloLetras(TextField textField) {
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("[a-zA-Z ]*")) {
                textField.setText(oldValue);
            }
        });
    }
    
    public void soloNumeros(final TextField tf, int frec) {
        tf.textProperty().addListener((final ObservableValue<? extends String> ov, final String oldValue, final String newValue) -> {
            if(!newValue.equals("")){
                /*boolean found = false;
                String res = "";
                String input = String.valueOf(newValue.charAt(newValue.length() - 1));
                if((newValue.length() == frec + 1) || newValue.length() % (frec + 1) == 0){   
                } else{
                    if (!input.matches("^[0-9]$") || newValue.contains("  ")) {
                    tf.setText(oldValue);
                }
                } */
                
                /*for(int i = 1; i <= newValue.length(); i++){
                    String aux = String.valueOf(newValue.charAt(i - 1));
                    if(aux.matches("^[0-9]$")){
                        
                            res = res + aux;
                        
                    } else if (i % (frec + 1) == 0 && aux.equals(" ")){
                            res = res + aux;
                    }
                }
                tf.setText(res); */
                tf.setText(newValue.replaceAll("[^0-9]", ""));
            }    
        });
    }
    
    public void addTextLimiter(final TextField tf, final int maxLength) {
        tf.textProperty().addListener((final ObservableValue<? extends String> ov, final String oldValue, final String newValue) -> {
            if (tf.getText().length() > maxLength) {
                String s = tf.getText().substring(0, maxLength);
                tf.setText(s);
            }
        });
    }
    
    public void añadirEspacio(final TextField tf, final int frec) {
        tf.textProperty().addListener((final ObservableValue<? extends String> ov, final String oldValue, final String newValue) -> {
            String oldTexto = oldValue;
            String newTexto = newValue;
            
            if(oldTexto.length() < newTexto.length()){
               if (newTexto.lastIndexOf(" ") != -1 && newTexto.length() == newTexto.lastIndexOf(" ") + frec + 1) {
                    newTexto = newTexto + " ";
                    tf.setText(newTexto);
                } else if (newTexto.length() == frec){
                    newTexto = newTexto + " ";
                    tf.setText(newTexto);
                } 
            } 
        });
    }  
    
    public void formato(final TextField tf, final int frec) {
        tf.setTextFormatter(new TextFormatter<>(change -> {
            String newText = change.getControlNewText();
            newText = newText.replaceAll("\\D", ""); // Eliminar todos los caracteres no numéricos
            newText = newText.replaceAll("(.{4})(?=.{1})", "$1 "); // Añadir espacio cada 4 números
            int maxLength = 19; // Límite de 19 caracteres
            if (newText.length() <= maxLength) {
                return change;
            } else {
                return null; // Ignorar el cambio
            }


        }));
    }  

    
    private static Image getRoundedImage(Image image, double radius) {
    Circle clip = new Circle(image.getWidth() / 2, image.getHeight() / 2, radius);
    ImageView imageView = new ImageView(image);
    imageView.setClip(clip);
    SnapshotParameters parameters = new SnapshotParameters();
    parameters.setFill(Color.TRANSPARENT);
    return imageView.snapshot(parameters, null);
    }

    @FXML
    private void cambiarImagen(MouseEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Elegir Imagen");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Imágenes", "*.png", "*.jpg", "*.jpeg"));

        // Mostrar el diálogo de selección de archivos
        File selectedFile = fileChooser.showOpenDialog(null);

        if (selectedFile != null) {
            // Crear un objeto Image con la imagen seleccionada
            imagen = new Image(selectedFile.toURI().toString());

            // Asignar la imagen al ImageView
            circuloAvatar.setFill(new ImagePattern(imagen));
            //Image imagenSeleccionado2 = new Image(selectedFile.toURI().toString(),240,240,false,false);
            //Image aux2 = getRoundedImage(imagenSeleccionado2, 120);
            //imagen.setImage(aux2);
        }
    }

    @FXML
    private void showPass(ActionEvent event) {
            if (showPass.isSelected()) {
                passwordField.visibleProperty().setValue(Boolean.FALSE);
                passText.visibleProperty().setValue(Boolean.TRUE);
                passText.setText(passwordField.getText());
                passText.setStyle(passwordField.getStyle());
            } else {
                passText.visibleProperty().setValue(Boolean.FALSE);
                passwordField.visibleProperty().setValue(Boolean.TRUE);
                passwordField.setText(passText.getText());
            }
    }

    @FXML
    private void showPass2(ActionEvent event) {
        if (showRepPass.isSelected()) {
                reppasswordField.visibleProperty().setValue(Boolean.FALSE);
                reppassText.visibleProperty().setValue(Boolean.TRUE);
                reppassText.setText(reppasswordField.getText());
                reppassText.setStyle(reppasswordField.getStyle());
                reppasswordField.setText("");
                
            } else {
                reppassText.visibleProperty().setValue(Boolean.FALSE);
                reppasswordField.visibleProperty().setValue(Boolean.TRUE);
                reppasswordField.setText(reppassText.getText());
                reppassText.setText("");
            }
    }
}

