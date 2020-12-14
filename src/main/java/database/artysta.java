package database;

import javafx.beans.property.*;

import java.sql.Date;

public class artysta {
    private SimpleIntegerProperty Id_artysty = new SimpleIntegerProperty(this, "id_artysty");
    private SimpleStringProperty imie = new SimpleStringProperty(this, "imie");
    private SimpleStringProperty nazwisko = new SimpleStringProperty(this, "nazwisko");
    private ObjectProperty<Date> data_ur = new SimpleObjectProperty<>(this, "data_ur");
    private SimpleStringProperty miejsce_ur = new SimpleStringProperty(this, "miejsce_ur");
    private SimpleIntegerProperty Id_stylu = new SimpleIntegerProperty(this, "Id_stylu");

    public int getId_artysty() {
        return Id_artysty.get();
    }

    public SimpleIntegerProperty id_artystyProperty() {
        return Id_artysty;
    }

    public void setId_artysty(int id_artysty) {
        this.Id_artysty.set(id_artysty);
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
