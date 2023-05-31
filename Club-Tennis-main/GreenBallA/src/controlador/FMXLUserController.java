/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controlador;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
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
public class FMXLUserController implements Initializable {
    
    Member miembro = null;
    private TextField CampoUsuario;
    private TextField CampoContra;
    Club club = null;
    @FXML
    private GridPane Pane;
    @FXML
    private DatePicker calendario;
    @FXML
    private Button BotonReserva;
    @FXML
    private Button BotonMisReservas;
    @FXML
    private Button BotonPerfil;
    @FXML
    private Button BotonCierreSesión;
    
    private String Usuario;
    private String Contra;
    @FXML
    private Button BotonAceptar;
    @FXML
    private Button BotonCancelar;
    
    private Booking reserva;
    private int[] horas = new int[14];
    private LocalTime[] seleccionadas = new LocalTime[10];
    int contador_seleccionadas = 0;
    private int[] x_seleccionadas = new int[10];
    private int[] y_seleccionadas = new int[10];
    Court pistas;
    String pista = "pista";
    //variables para mostrar las reservas al inicio
    private List<Booking> reservas;
    int fila = 0;
    int column = 0;
    ScaleTransition scaleTransition;
    @FXML
    private Text textoUsr;
    @FXML
    private ImageView imgUsr;
    Tooltip tooltip = null;
    /**
     * Initializes the controller class.
     * @param url
     */
    
    public void initUser(Member mi){
    miembro = mi;
    textoUsr.setText(miembro.getNickName());
    imgUsr.setImage(miembro.getImage());
    }
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
            tooltip = new Tooltip("Esta hora ya ha pasado");
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

    @FXML //metodo que cuando pulsas un botón del Pane te cambia el texto a reservado
     private void pulsar(ActionEvent event) {
        Node src = (Node)event.getSource();
        System.out.println("Row: "+ GridPane.getRowIndex(src));
        System.out.println("Column: "+ GridPane.getColumnIndex(src));
        int x = GridPane.getRowIndex(src);
        int y = GridPane.getColumnIndex(src);
        int hora_real = LocalTime.now().getHour();
        System.out.println(horas[x] + " " + hora_real);
        for(Node node : Pane.getChildren()){
        if (GridPane.getColumnIndex(node) == y && GridPane.getRowIndex(node) == x && node instanceof Button) {
        Button button = (Button) node;
        if(button.getText().startsWith("Reservado")){
        break;
        }else if(horas[x] < hora_real && calendario.getValue().equals(LocalDate.now())){
        mostrarAlerta("Hora ya pasada");
        break;
        }else if(comprobarseleccion(y,x)){
             Alert alert = new Alert(AlertType.ERROR);
        // ó AlertType.WARNING ó AlertType.ERROR ó AlertType.CONFIRMATIONalert.setTitle("Diálogo de información");
        alert.setHeaderText(null);
        // ó null si no queremos cabecera
        alert.setContentText("No se pueden elegir más de 2 horas seguidas en la misma pista.");
        alert.showAndWait();
            break;
        }else{
        LocalTime time = LocalTime.of(horas[x], 0);
        seleccionadas[contador_seleccionadas] = time;
        y_seleccionadas[contador_seleccionadas] = y;
        x_seleccionadas[contador_seleccionadas] = x;
        contador_seleccionadas++;
        button.setText("Seleccionado");
        BotonAceptar.setDisable(false);
        BotonCancelar.setDisable(false);
        break;
        }}
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
    public void setCadenas(String user, String pass){
    this.Usuario = user;
    this.Contra = pass;
    }

    @FXML
    private void reservar(ActionEvent event) throws ClubDAOException {
        System.out.println(Usuario);
        for(int i = 0; i < seleccionadas.length; i++){
        if(seleccionadas[i] == null){break;}
        LocalDateTime ahora = LocalDateTime.now();
        LocalDate dia = calendario.getValue();
        Court pista = club.getCourt("Pista " + y_seleccionadas[i]);
        boolean pagado = true;
        if(miembro.getCreditCard().equals("")){pagado = false;}
        club.registerBooking(ahora,dia,seleccionadas[i],pagado,pista,miembro);
        int y = y_seleccionadas[i];
        int x = x_seleccionadas[i];
        System.out.println(x + " " + y);
        for(Node node : Pane.getChildren()){
        if (GridPane.getColumnIndex(node) == y && GridPane.getRowIndex(node) == x && node instanceof Button) {
        Button button = (Button) node;
        String cosa = miembro.getNickName();
        button.setText("Reservado " + cosa);
        break;
        }}}
        y_seleccionadas = new int[10];
        x_seleccionadas = new int[10];
        seleccionadas = new LocalTime[10];
        contador_seleccionadas = 0;
        BotonAceptar.setDisable(true);
        BotonCancelar.setDisable(true);
    }

    @FXML
    private void cierreSesion(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/VistaMain_1.fxml"));
        Parent root = loader.load();
        
        JavaFXMLApplication.setRoot(root);
    }

    @FXML
    private void limpiar(ActionEvent event) {
        y_seleccionadas = new int[10];
        x_seleccionadas = new int[10];
        seleccionadas = new LocalTime[10];
        contador_seleccionadas = 0;
        for(Node node : Pane.getChildren()){
        if (node instanceof Button) {
        Button button = (Button) node;
        if(button.getText() == "Seleccionado"){
        button.setText("libre");
        }}}
        BotonAceptar.setDisable(true);
        BotonCancelar.setDisable(true);
    }
    
    public boolean comprobarseleccion(int y, int x){
    int x_anterior = x - 1;
    int x_siguiente = x + 1;
    int x_anterior2 = x - 2;
    int x_siguiente2 = x + 2;
    System.out.println(x_anterior);
    System.out.println(x_anterior2);
    System.out.println(x_siguiente);
    System.out.println(x_siguiente2);
    System.out.println(y);
    boolean x1 = false;
    boolean x2 = false;
    boolean xant = false;
    boolean xant2 = false;
     String cosa = miembro.getNickName();
     System.out.println("reservado " + cosa);
        if(x - 1 > 0){
        for(Node node : Pane.getChildren()){
        if (GridPane.getColumnIndex(node) == y && GridPane.getRowIndex(node) == x_anterior && node instanceof Button) {
        Button button = (Button) node;
        String comp = button.getText();
        System.out.println("Reservado " + cosa);
        System.out.println(button.getText());
        boolean es = button.getText().equals("Reservado " + cosa);
        if(es){
        xant = true;
        }else if(button.getText() == "Seleccionado"){
        xant = true;
        }
        break;
        }}}
    if(x - 2 > 0){
    for(Node node : Pane.getChildren()){
        if (GridPane.getColumnIndex(node) == y && GridPane.getRowIndex(node) == x_anterior2 && node instanceof Button) {
        Button button = (Button) node;
        String comp = button.getText();
        System.out.println("Reservado " + cosa);
        System.out.println(button.getText());
        boolean es = button.getText().equals("Reservado " + cosa);
        System.out.println(es);
        if(button.getText() == "Seleccionado"){
        xant2 = true;
        }else if(es){
        xant2 = true;
        }
        break;
        }}}
    if(x + 1 < 14){
    for(Node node : Pane.getChildren()){
        if (GridPane.getColumnIndex(node) == y && GridPane.getRowIndex(node) == x_siguiente && node instanceof Button) {
        Button button = (Button) node;
        String comp = button.getText();
        System.out.println("Reservado " + cosa);
        System.out.println(button.getText());
        boolean es = button.getText().equals("Reservado " + cosa);
        if(es){
        x1 = true;
        }else if(button.getText() == "Seleccionado"){
        x1 = true;
        }
        break;
        }}}
    if(x + 2 < 14){
    for(Node node : Pane.getChildren()){
        if (GridPane.getColumnIndex(node) == y && GridPane.getRowIndex(node) == x_siguiente2 && node instanceof Button) {
        Button button = (Button) node;
        String comp = button.getText();
        System.out.println("Reservado " + cosa);
        System.out.println(button.getText());
        boolean es = button.getText().equals("Reservado " + cosa);
        if(es){
        x2 = true;
        }else if(button.getText() == "Seleccionado"){
        x2 = true;
        }
        break;
        }}}
        if(xant == true && xant2 == true){
        return true;
        }else if(xant == true && x1 == true){
        return true;
        }else if(x1 == true && x2 == true){
        return true;
        }else{
        return false;
        }
        }
    
    public void refrescar(){
        y_seleccionadas = new int[10];
        x_seleccionadas = new int[10];
        seleccionadas = new LocalTime[10];
        contador_seleccionadas = 0;
        for(Node node : Pane.getChildren()){
        if (node instanceof Button) {
        Button button = (Button) node;
        if(button.getText().startsWith("Reservado")){
        button.setText("libre");
        }}}
        BotonAceptar.setDisable(true);
        BotonCancelar.setDisable(true);
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
    private void toPerfil(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/ModificarDatos.fxml"));
        Parent root = loader.load();
        ModificarDatosController controladorUsuario = loader.getController();
        controladorUsuario.setMember(miembro);
        JavaFXMLApplication.setRoot(root);
    }

    @FXML
    private void toMisReservas(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/MisReservas.fxml"));
        Parent root = loader.load();
        MisReservasController controladorUsuario = loader.getController();
        controladorUsuario.setMember(miembro);
        JavaFXMLApplication.setRoot(root);
    }
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