package dam.alumno.filmoteca;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.Arrays;
import java.util.List;

public class PeliculaView {
    public TextField fieldTitulo;
    public TextField fieldGenero;
    public TextField fieldAño;
    public TextField fieldDirector;
    public TextField fieldRating;
    public TextField fieldDescripcion;
    public Text textoTitulo;
    public Slider sliderRating;
    public Text textoRating;
    private Boolean onClick = false;
    private DatosFilmoteca datosFilmoteca = DatosFilmoteca.getInstancia();
    private ObservableList<Pelicula> listaPeliculas = DatosFilmoteca.getListaPeliculas();
    private Pelicula peliculaEditando = null; // Variable para almacenar la película en edición

    public void setTextoTitulo(String texto) {
        textoTitulo.setText(texto);
    }

    public void setPelicula(Pelicula pelicula) {
        if (pelicula != null) {
            peliculaEditando = pelicula; // Guardamos la película que se está editando
            textoTitulo.setText("Editar Pelicula");

            // Titulo
            fieldTitulo.setText(pelicula.getTitle());

            // Genero: Convertir la lista de géneros en un texto separado por comas
            String generosString = String.join(", ", pelicula.getGenero());
            fieldGenero.setText(generosString);

            // Año
            fieldAño.setText(String.valueOf(pelicula.getYear()));

            // Director
            fieldDirector.setText(pelicula.getDirector());

            // Rating
            sliderRating.setValue(pelicula.getRating());
            textoRating.setText(String.valueOf((int) pelicula.getRating()));

            // Listener para actualizar el texto cuando el slider cambia
            sliderRating.valueProperty().addListener((obs, oldVal, newVal) -> {
                textoRating.setText(String.format("%.0f", newVal));
            });

            // Descripción
            fieldDescripcion.setText(pelicula.getDescription());
        } else {
            textoTitulo.setText("Nueva Pelicula");
            peliculaEditando = null; // Si no se pasa película, es para crear una nueva
        }
    }

    public void handlerAceptar(ActionEvent actionEvent) {
        Pelicula pelicula;

        // Si estamos editando, buscamos la película y la modificamos
        if (peliculaEditando != null) {
            pelicula = peliculaEditando; // Usamos la película que estamos editando
        } else {
            pelicula = new Pelicula(); // Si no es edición, creamos una nueva
            // ID
            int maxId = 0;
            for (Pelicula p : listaPeliculas) {
                if (p.getId() > maxId) {
                    maxId = p.getId();
                }
            }
            pelicula.setId(maxId + 1);
        }

        // Actualizamos los valores
        pelicula.setTitle(fieldTitulo.getText());

        // Genero: Convertir el texto en una lista de géneros
        String[] generosArray = fieldGenero.getText().split(",");
        List<String> generosList = Arrays.asList(generosArray);
        pelicula.setGenero(generosList);

        // Año: Convertir el texto a int
        try {
            int año = Integer.parseInt(fieldAño.getText());
            pelicula.setYear(año);
        } catch (NumberFormatException e) {
            System.out.println("Error: Año inválido.");
        }

        // Director
        pelicula.setDirector(fieldDirector.getText());

        // Rating: Convertir el texto a float
        try {
            float rating = (float) sliderRating.getValue();
            pelicula.setRating(rating);
        } catch (NumberFormatException e) {
            System.out.println("Error: Rating inválido.");
        }

        // Descripción
        pelicula.setDescription(fieldDescripcion.getText());

        // Si estamos en modo edición, no agregamos una nueva película, solo actualizamos
        if (peliculaEditando == null) {
            listaPeliculas.add(pelicula); // Si es nueva, la añadimos a la lista
        }

        // Cerrar la ventana
        Stage stage = (Stage)((Node)(actionEvent.getSource())).getScene().getWindow();
        stage.close();
    }

    public void handlerCancelar(ActionEvent actionEvent) {
        Stage stage = (Stage)((Node)(actionEvent.getSource())).getScene().getWindow();
        stage.close();
    }
}
