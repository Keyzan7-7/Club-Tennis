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
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.media.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;
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
    
    Club club = null;
    List<Booking> datos = null;
    ObservableList<Booking> reservas = null;
    @FXML
    private Button BotonReserva;
    @FXML
    private Button BotonMisReservas;
    @FXML
    private Button BotonPerfil;
    @FXML
    private Button BotonCierreSesión;
    @FXML
    private TableColumn<Booking, String> DiaCol;
    @FXML
    private TableColumn<Booking, String> pistaCol;
    @FXML
    private TableColumn<Booking, String> horaCol;
    @FXML
    private TableColumn<Booking, String> estadoCol;
    @FXML
    private TableColumn<Booking, Void> pagarCol;
    @FXML
    private TableColumn<Booking, Void> eliminarCol;
    @FXML
    private TableView<Booking> reservasTable;
    @FXML
    private BorderPane BorderPane;

    /**
     * Initializes the controller class.
     */
    public void setMember(Member x){
        miembro = x;
        datos = club.getUserBookings(miembro.getNickName());
        reservas = FXCollections.observableList(datos);
        reservasTable.setItems(reservas);
        for(int i = 0; i < datos.size(); i++){
            System.out.println(datos.get(i).getPaid());
        }
    
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            // TODO
            club = Club.getInstance();
        } catch (ClubDAOException ex) {
            Logger.getLogger(MisReservasController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(MisReservasController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
        horaCol.setCellValueFactory(data -> new SimpleStringProperty(String.valueOf(data.getValue().getFromTime()) + " - " + String.valueOf(data.getValue().getFromTime().plusHours(1))));
        pistaCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getCourt().getName()));
        DiaCol.setCellValueFactory(data -> new SimpleStringProperty(String.valueOf(data.getValue().getMadeForDay())));
        estadoCol.setCellValueFactory((TableColumn.CellDataFeatures<Booking, String> param) -> {
            if (param.getValue().getPaid()) {
                return new SimpleStringProperty("Pagado");
            } else {
                return new SimpleStringProperty("No pagado");
            }
        });
        pagarCol.setCellFactory(column -> new PayButtonCell());
        eliminarCol.setCellFactory(column -> new DeleteButtonCell());
        
        //reservasTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        //DiaCol.prefWidthProperty().bind(reservasTable.widthProperty().multiply(0.5));
        //pistaCol.prefWidthProperty().bind(reservasTable.widthProperty().multiply(0.5));
        //horaCol.prefWidthProperty().bind(reservasTable.widthProperty().multiply(0.3));
        
        double alturaInicial = 803;
double anchuraInicial = 469;
double alturaIA = reservasTable.getHeight();

BorderPane.widthProperty().addListener((obs, oldWidth, newWidth) -> {
    double ratioAux = BorderPane.getWidth() / anchuraInicial;
    double ratio = Math.min(BorderPane.getScene().getHeight() / alturaInicial, ratioAux);

    double newTableWidth = BorderPane.getWidth() - 60;
    // Cambio de ancho de columnas

    for (int i = 0; i < 6; i++) {
        double newColumnWidth = 0;

 switch (i) {
    case 0:
        newColumnWidth = 0.150 / 0.838 * newTableWidth;
        break;
    case 1:
        newColumnWidth = 0.130 / 0.838 * newTableWidth;
        break;
    case 2:
        newColumnWidth = 0.160 / 0.838 * newTableWidth;
        break;
    case 3:
        newColumnWidth = 0.130 / 0.838 * newTableWidth;
        break;
    case 4:
        newColumnWidth = 0.140 / 0.838 * newTableWidth;
        break;
    case 5:
        newColumnWidth = 0.140 / 0.838 * newTableWidth;
        break;
}

        reservasTable.getColumns().get(i).setPrefWidth(newColumnWidth);
    }
});
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
    
    private class PayButtonCell extends TableCell<Booking, Void> {
        private final Button payButton = new Button("Pagar");

        public PayButtonCell() {
            payButton.setOnAction(event -> {
                Booking booking = getTableRow().getItem();
                if (booking != null) {
                    try {
                        club.removeBooking(booking);
                        club.registerBooking(booking.getBookingDate(), booking.getMadeForDay(), booking.getFromTime(), true, booking.getCourt(), miembro);
                    } catch (ClubDAOException ex) {
                        Logger.getLogger(MisReservasController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });
        }

        @Override
        protected void updateItem(Void item, boolean empty) {
            super.updateItem(item, empty);
            setDisable(Boolean.TRUE);
            /*if (empty) {
                setGraphic(null);
            } else {
                setGraphic(new HBox(payButton));
            }*/
            
            if (empty) {
            setGraphic(null);
        } else {
            Booking booking = getTableRow().getItem();
            if (booking != null && !booking.getPaid()) {
                setDisable(false);
                setGraphic(new HBox(payButton));
            } else {
                setDisable(true);
                setGraphic(new HBox(payButton));
                //setGraphic(null);
            }
        }
        }
    }
    
     private class DeleteButtonCell extends TableCell<Booking, Void> {
        private final Button deleteButton = new Button("Eliminar");

        public DeleteButtonCell() {
            deleteButton.setOnAction(event -> {
                Booking booking = getTableRow().getItem();
                if (booking != null) {
                    try {
                        datos.remove(booking);
                        club.removeBooking(booking);
                    } catch (ClubDAOException ex) {
                        Logger.getLogger(MisReservasController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });
        }

        @Override
        protected void updateItem(Void item, boolean empty) {
            super.updateItem(item, empty);
            if (empty) {
                setGraphic(null);
            } else {
                setGraphic(new HBox(deleteButton));
            }
        }
    }
}
