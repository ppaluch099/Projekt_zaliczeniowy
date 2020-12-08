package database;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

import java.sql.Date;

public class autorzy {
    private SimpleIntegerProperty Id_autora = new SimpleIntegerProperty(this, "id_autora");
    private SimpleStringProperty imie = new SimpleStringProperty(this, "imie");
    private SimpleStringProperty nazwisko = new SimpleStringProperty(this, "nazwisko");
    private ObjectProperty<Date> data_ur = new SimpleObjectProperty<>(this, "dara_ur");
    private SimpleStringProperty miejsce_ur = new SimpleStringProperty(this, "miejsce_ur");
    private SimpleIntegerProperty Id_stylu = new SimpleIntegerProperty(this, "Id_stylu");

    public int getId_autora() {
        return Id_autora.get();
    }

    public SimpleIntegerProperty id_autoraProperty() {
        return Id_autora;
    }

    public void setId_autora(int id_autora) {
        this.Id_autora.set(id_autora);
    }

    public String getImie() {
        return imie.get();
    }

    public SimpleStringProperty imieProperty() {
        return imie;
    }

    public void setImie(String imie) {
        this.imie.set(imie);
    }

    public String getNazwisko() {
        return nazwisko.get();
    }

    public SimpleStringProperty nazwiskoProperty() {
        return nazwisko;
    }

    public void setNazwisko(String nazwisko) {
        this.nazwisko.set(nazwisko);
    }

    public Date getData_ur() {
        return data_ur.get();
    }

    public ObjectProperty<Date> data_urProperty() {
        return data_ur;
    }

    public void setData_ur(Date data_ur) {
        this.data_ur.set(data_ur);
    }

    public String getMiejsce_ur() {
        return miejsce_ur.get();
    }

    public SimpleStringProperty miejsce_urProperty() {
        return miejsce_ur;
    }

    public void setMiejsce_ur(String miejsce_ur) {
        this.miejsce_ur.set(miejsce_ur);
    }

    public int getId_stylu() {
        return Id_stylu.get();
    }

    public SimpleIntegerProperty id_styluProperty() {
        return Id_stylu;
    }

    public void setId_stylu(int id_stylu) {
        this.Id_stylu.set(id_stylu);
    }
}
