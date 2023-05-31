/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controlador;

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
import javafx.animation.ScaleTransition;
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
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.media.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
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
    private Text TextoSesión;
    Club club = null;
    @FXML
    private GridPane Pane;
    @FXML
    private DatePicker calendario;
    private MediaPlayer mediaPlayer;
    
    //variables para mostrar las reservas al inicio
    private List<Booking> reservas;
    int fila = 0;
    int column = 0;
    private Booking reserva;
    private int[] horas = new int[14];
    @FXML
    private Text bienvenida;
    @FXML
    private Text textotro;
    ScaleTransition scaleTransition;
    /**
     * Initializes the controller class.
     * @param url
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            // TODO
         
            int hora = 8;
            for(int i = 0; i < horas.length; i++){
            horas[i] = hora;
            hora++;
            System.out.println(horas[i]);
          
            }
            club = Club.getInstance();
            calendario.setValue(LocalDate.now());
            //metodo inicializar calendario con las reservas
            reservas =  club.getForDayBookings(LocalDate.now());
            for(int i = 0; i < reservas.size(); i++){
            Booking res = reservas.get(i);
            Member tocado = res.getMember();
            String Usuario = tocado.getNickName();
            LocalTime hora_reservada = res.getFromTime();
            int h = hora_reservada.getHour();
            for(int j = 0; j < horas.length; j++){
            if(horas[j] == h){
            fila = j;
            break;
            }}
            Court pist = res.getCourt();
            String nombre = pist.getName();
            String numeroString = nombre.replaceAll("[^0-9]","");
            column = Integer.parseInt(numeroString);
            for(Node node : Pane.getChildren()){
            if (GridPane.getColumnIndex(node) == column && GridPane.getRowIndex(node) == fila && node instanceof Button) {
            Button button = (Button) node;
            button.setText("Reservado " + Usuario);
            break;
            }}
            
            }
            
        } catch (ClubDAOException | IOException ex) {
            Logger.getLogger(FMXLUserController.class.getName()).log(Level.SEVERE, null, ex);
        }
        calendario.valueProperty().addListener((ob,oldV,newV) ->{
        refrescar();
        });
        calendario.setDayCellFactory((DatePicker picker) -> {
         return new DateCell() {
            @Override
         public void updateItem(LocalDate date, boolean empty) {
            super.updateItem(date, empty);
            LocalDate today = LocalDate.now();
            setDisable(empty || date.compareTo(today) < 0 );
            }
            };
            });

    }    

    @FXML
    private void Registrar(ActionEvent event) throws ClubDAOException, IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/Registrarse.fxml"));
        Parent root = loader.load();
        
        JavaFXMLApplication.setRoot(root);
    
    /*String songPath = "/vista/cancion.mp3";
    Media song = new Media(new File(songPath).toURI().toString());
    mediaPlayer = new MediaPlayer(song);
    mediaPlayer.play();
    */
    }

    @FXML
    private void IniciarSesion(ActionEvent event) throws IOException {
        //try{
        String user = CampoUsuario.getText();
        String pass = CampoContra.getText();
        System.out.println(user + pass);
        usr = club.getMemberByCredentials(user, pass);
        if(usr == null){
        mostrarAlerta("Usuario o contraseña incorrecto");
        }
        else{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/VistaUser.fxml"));
        Parent root = loader.load();
        FMXLUserController controladorUsuario = loader.getController();
        user = CampoUsuario.getText();
        controladorUsuario.initUser(usr);
        JavaFXMLApplication.setRoot(root);
 
        }
       //}catch(Exception e){
       // mostrarAlerta("Usuario o contraseña incorrecta");
        //}
    }
    @FXML //metodo que cuando pulsas un botón del Pane te cambia el texto a reservado
     private void pulsar(ActionEvent event) {
        /*Node src = (Node)event.getSource();
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
        }*/
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
     public void refrescar(){
        for(Node node : Pane.getChildren()){
        if (node instanceof Button) {
        Button button = (Button) node;
        if(button.getText().startsWith("Reservado")){
        button.setText("libre");
        }}}
        // Diferenciador
            reservas =  club.getForDayBookings(calendario.getValue());
            for(int i = 0; i < reservas.size(); i++){
            Booking res = reservas.get(i);
            Member tocado = res.getMember();
            String Usuario = tocado.getNickName();
            LocalTime hora_reservada = res.getFromTime();
            int h = hora_reservada.getHour();
            for(int j = 0; j < horas.length; j++){
            if(horas[j] == h){
            fila = j;
            break;
            }}
            Court pist = res.getCourt();
            String nombre = pist.getName();
            String numeroString = nombre.replaceAll("[^0-9]","");
            column = Integer.parseInt(numeroString);
            for(Node node : Pane.getChildren()){
            if (GridPane.getColumnIndex(node) == column && GridPane.getRowIndex(node) == fila && node instanceof Button) {
            Button button = (Button) node;
            button.setText("Reservado " + Usuario);
            break;
            }}
    
    }}

    @FXML
    private void SalirVerde(MouseEvent event) {
                        scaleTransition.stop();
                        Button src = (Button)event.getSource();
                        src.setScaleX(1.0);
                        src.setScaleY(1.0);
    }
//#85d165
    @FXML
    private void EntrarVerde(MouseEvent event) {
                Node src = (Node)event.getSource();
                scaleTransition = new ScaleTransition(Duration.millis(100), src);
                scaleTransition.setFromX(1.0);
                scaleTransition.setFromY(1.0);
                scaleTransition.setToX(1.025);
                scaleTransition.setToY(1.025); 
                scaleTransition.playFromStart();
    }
    
}
