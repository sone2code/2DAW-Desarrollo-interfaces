package dam.alumno.filmoteca;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Pelicula {
    private final IntegerProperty id = new SimpleIntegerProperty();
    private final StringProperty title = new SimpleStringProperty();
    private final ListProperty<String> genero = new SimpleListProperty<>(FXCollections.observableArrayList()); // Cambio aquí
    private final IntegerProperty year = new SimpleIntegerProperty();
    private final StringProperty description = new SimpleStringProperty();
    private final StringProperty director = new SimpleStringProperty();
    private final FloatProperty rating = new SimpleFloatProperty();
    private final StringProperty poster = new SimpleStringProperty();

    public Pelicula() {
    }

    public Pelicula(int id, String title, List<String> genre, int year, String description, String director, float rating, String poster) {
        this.id.set(id);
        this.title.set(title);
        this.genero.set(FXCollections.observableArrayList(genre)); // Uso de observableArrayList
        this.year.set(year);
        this.description.set(description);
        this.director.set(director);
        this.rating.set(rating);
        this.poster.set(poster);
    }

    public int getId() {
        return id.get();
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public String getTitle() {
        return title.get();
    }

    public StringProperty titleProperty() {
        return title;
    }

    public void setTitle(String title) {
        this.title.set(title);
    }

    public int getYear() {
        return year.get();
    }

    public IntegerProperty yearProperty() {
        return year;
    }

    public void setYear(int year) {
        this.year.set(year);
    }

    public String getDescription() {
        return description.get();
    }

    public StringProperty descriptionProperty() {
        return description;
    }

    public void setDescription(String description) {
        this.description.set(description);
    }

    public float getRating() {
        return rating.get();
    }

    public FloatProperty ratingProperty() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating.set(rating);
    }

    public String getPoster() {
        return poster.get();
    }

    public StringProperty posterProperty() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster.set(poster);
    }

    public ObservableList<String> getGenero() {
        return genero.get();
    }

    public ListProperty<String> generoProperty() {
        return genero;
    }

    public void setGenero(List<String> genero) {
        this.genero.set(FXCollections.observableArrayList(genero)); // Convertir a observableList
    }

    public String getDirector() {
        return director.get();
    }

    public StringProperty directorProperty() {
        return director;
    }

    public void setDirector(String director) {
        this.director.set(director);
    }

    @Override
    public String toString() {
        return "Pelicula{" +
                "id=" + id +
                ", title=" + title +
                ", genero=" + genero +
                ", year=" + year +
                ", description=" + description +
                ", director=" + director +
                ", rating=" + rating +
                ", poster=" + poster +
                '}';
    }
}
