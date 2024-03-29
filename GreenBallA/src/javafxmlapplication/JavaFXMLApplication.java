/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxmlapplication;
import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Club;
import static model.Club.*;



public class JavaFXMLApplication extends Application {
    private static Scene scene;
    Club club;
        public static void setRoot(Parent root) throws IOException{
        scene.setRoot(root);
        }
    @Override
    public void start(Stage stage) throws Exception {
        
           
        //======================================================================
        // 1- creación del grafo de escena a partir del fichero FXML
            FXMLLoader loader= new  FXMLLoader(getClass().getResource("/vista/VistaMain_1.fxml"));
        Parent root = loader.load();
        //======================================================================
        // 2- creación de la escena con el nodo raiz del grafo de escena
        scene = new Scene(root);
        //======================================================================
        // 3- asiganación de la escena al Stage que recibe el metodo 
        //     - configuracion del stage
        //     - se muestra el stage de manera no modal mediante el metodo show()
        stage.setScene(scene);
        stage.setTitle("GreenBall");
        club = Club.getInstance();
        stage.setMinWidth(820);
        stage.setMinHeight(479);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
        
    }


    
}
