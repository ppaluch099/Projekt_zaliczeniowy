package database;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class kraje {
    private SimpleIntegerProperty Id_kraju = new SimpleIntegerProperty();
    private SimpleStringProperty Nazwa_kraju = new SimpleStringProperty();

    public int getId_kraju() {
        return Id_kraju.get();
    }

    public SimpleIntegerProperty id_krajuProperty() {
        return Id_kraju;
    }

    public void setId_kraju(int id_kraju) {
        this.Id_kraju.set(id_kraju);
    }

    public String getNazwa_kraju() {
        return Nazwa_kraju.get();
    }

    public SimpleStringProperty nazwa_krajuProperty() {
        return Nazwa_kraju;
    }

    public void setNazwa_kraju(String nazwa_kraju) {
        this.Nazwa_kraju.set(nazwa_kraju);
    }
}
