package database;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class style {
    private SimpleIntegerProperty Id_stylu = new SimpleIntegerProperty();
    private SimpleStringProperty Nazwa_stylu = new SimpleStringProperty();

    public int getId_stylu() {
        return Id_stylu.get();
    }

    public SimpleIntegerProperty id_styluProperty() {
        return Id_stylu;
    }

    public void setId_stylu(int id_stylu) {
        this.Id_stylu.set(id_stylu);
    }

    public String getNazwa_stylu() {
        return Nazwa_stylu.get();
    }

    public SimpleStringProperty nazwa_styluProperty() {
        return Nazwa_stylu;
    }

    public void setNazwa_stylu(String nazwa_stylu) {
        this.Nazwa_stylu.set(nazwa_stylu);
    }
}
