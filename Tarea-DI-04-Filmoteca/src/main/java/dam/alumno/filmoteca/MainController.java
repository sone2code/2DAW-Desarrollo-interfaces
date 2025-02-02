package dam.alumno.filmoteca;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MainController {

    public Text textoId;
    public Text textoTitulo;
    public Text textoGenero;
    public Text textoAño;
    public Text textoDescripcion;
    public Text textoDirector;
    @FXML
    private Slider sliderRating;
    @FXML
    public Text textoRating;
    @FXML
    private ImageView imagePelicula;
    @FXML
    public TableView<Pelicula> tablaPeliculas;
    @FXML
    public TableColumn<Pelicula, Integer>columnaId;
    @FXML
    public TableColumn<Pelicula, String>columnaTitle;
    @FXML
    public TableColumn<Pelicula, String>columnaGenero;
    @FXML
    public TableColumn<Pelicula, Integer>columnaYear;
    @FXML
    public TableColumn<Pelicula, String>columnaDescription;
    @FXML
    public TableColumn<Pelicula, String> columnaDirector;
    @FXML
    public TableColumn<Pelicula, Integer> columnaRating;

    @FXML
    private DatosFilmoteca datosFilmoteca = DatosFilmoteca.getInstancia();
    private ObservableList<Pelicula> listaPeliculas = DatosFilmoteca.getListaPeliculas();

    public void initialize() {
        listaPeliculas = datosFilmoteca.getListaPeliculas();

        columnaId.setCellValueFactory(new PropertyValueFactory<Pelicula, Integer>("id"));
        columnaTitle.setCellValueFactory(new PropertyValueFactory<Pelicula, String>("title"));
        columnaGenero.setCellValueFactory(cellData -> {
            ObservableList<String> generos = cellData.getValue().getGenero();
            return new SimpleStringProperty(String.join(", ", generos)); // Unir los géneros con coma
        });

        columnaYear.setCellValueFactory(new PropertyValueFactory<Pelicula, Integer>("year"));
        columnaDescription.setCellValueFactory(new PropertyValueFactory<Pelicula, String>("description"));
        columnaDirector.setCellValueFactory(new PropertyValueFactory<Pelicula, String>("director"));
        columnaRating.setCellValueFactory(new PropertyValueFactory<Pelicula, Integer>("rating"));

        tablaPeliculas.setItems(listaPeliculas);

        tablaPeliculas.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
           if (newValue != null) {
               textoId.setText(String.valueOf(newValue.getId()));
               textoTitulo.setText(newValue.getTitle());
               textoDescripcion.setText(newValue.getDescription());
               textoAño.setText(String.valueOf(newValue.getYear()));
               textoDirector.setText(newValue.getDirector());
               textoRating.setText(String.valueOf(newValue.getRating()));
               textoGenero.setText(String.join(", ", newValue.getGenero()));
               Image imagen = new Image(newValue.getPoster(), true);
               imagePelicula.setImage(imagen);

           }
        });
    }

    public void handlerNuevo(ActionEvent actionEvent) {
        Scene escena = null;

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("PeliculaView.fxml"));
        try {
            escena = new Scene(fxmlLoader.load());
        } catch (IOException e){
            System.out.println("Error al intentar crear un nuevo pelicula");
            e.printStackTrace();
        }

        Stage stage = new Stage();
        stage.setTitle("Nueva pelicula");
        stage.setScene(escena);
        PeliculaView controlador = fxmlLoader.getController();
        controlador.setPelicula(null);
        controlador.setTextoTitulo("Añadir nueva pelicula");
        stage.showAndWait();
    }

    public void handlerModificar(ActionEvent actionEvent) {
        int indice = tablaPeliculas.getSelectionModel().getSelectedIndex();

        if (indice < 0) {
            Alert alerta = new Alert(Alert.AlertType.WARNING);
            alerta.setTitle("ERROR");
            alerta.setHeaderText("Ninguna película seleccionada");
            alerta.setContentText("Para editar una película, selecciona una de la lista");
            alerta.showAndWait();
            return;
        }

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("PeliculaView.fxml"));
        Scene escena = null;
        try {
            escena = new Scene(fxmlLoader.load());
        } catch (IOException e) {
            System.out.println("Error al intentar editar una película");
            e.printStackTrace();
        }

        Stage stage = new Stage();
        stage.setTitle("Modificar Pelicula");
        stage.setScene(escena);

        // Obtener el controlador y pasar la película seleccionada
        PeliculaView controlador = fxmlLoader.getController();
        controlador.setPelicula(tablaPeliculas.getSelectionModel().getSelectedItem());
        controlador.setTextoTitulo("Editar película");

        stage.showAndWait();
    }

    public void handlerBorrar(ActionEvent actionEvent) {
        int indice = tablaPeliculas.getSelectionModel().getSelectedIndex();
        if (indice >= 0) {
            tablaPeliculas.getItems().remove(indice);
        } else {
            Alert alerta = new Alert(Alert.AlertType.WARNING);
            alerta.setTitle("ERROR");
            alerta.setHeaderText(null);
            alerta.setContentText("Debe seleccionar una persona para borrarla");
            alerta.showAndWait();
        }
    }

    public void handlerExit(ActionEvent actionEvent) {
        Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
        alerta.setTitle("Confirmar cerrar aplicacion");
        alerta.setHeaderText("¿Estas seguro que quieres cerrar la aplicacion sin guardar los cambios?");
        alerta.setContentText("Elige una opcion para continuar");

        Optional<ButtonType> resulado = alerta.showAndWait();

        if (resulado.isPresent() && resulado.get() == ButtonType.OK) {
            System.exit(0);
        }

    }
}