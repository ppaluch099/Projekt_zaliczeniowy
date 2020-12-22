package database;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.ComboBox;

import java.sql.Date;

public class wystawa {
    //Id_wystawy, Nazwa, Data_rozpoczecia, Data_zakonczenia, Id_adresu
    private SimpleIntegerProperty Id_wystawy = new SimpleIntegerProperty();
    private SimpleStringProperty Nazwa = new SimpleStringProperty();
    private SimpleObjectProperty<Date> Data_rozpoczecia= new SimpleObjectProperty<>();
    private SimpleObjectProperty<Date> Data_zakonczenia= new SimpleObjectProperty<>();
    private SimpleStringProperty Nazwa_galerii = new SimpleStringProperty();
    private ComboBox Tytul = new ComboBox();

    public int getId_wystawy() {
        return Id_wystawy.get();
    }

    public SimpleIntegerProperty id_wystawyProperty() {
        return Id_wystawy;
    }

    public void setId_wystawy(int id_wystawy) {
        this.Id_wystawy.set(id_wystawy);
    }

    public String getNazwa() {
        return Nazwa.get();
    }

    public SimpleStringProperty nazwaProperty() {
        return Nazwa;
    }

    public void setNazwa(String nazwa) {
        this.Nazwa.set(nazwa);
    }

    public Date getData_rozpoczecia() {
        return Data_rozpoczecia.get();
    }

    public SimpleObjectProperty<Date> data_rozpoczeciaProperty() {
        return Data_rozpoczecia;
    }

    public void setData_rozpoczecia(Date data_rozpoczecia) {
        this.Data_rozpoczecia.set(data_rozpoczecia);
    }

    public Date getData_zakonczenia() {
        return Data_zakonczenia.get();
    }

    public SimpleObjectProperty<Date> data_zakonczeniaProperty() {
        return Data_zakonczenia;
    }

    public void setData_zakonczenia(Date data_zakonczenia) {
        this.Data_zakonczenia.set(data_zakonczenia);
    }

    public String getNazwa_galerii() {
        return Nazwa_galerii.get();
    }

    public SimpleStringProperty nazwa_galeriiProperty() {
        return Nazwa_galerii;
    }

    public void setNazwa_galerii(String nazwa_galerii) {
        this.Nazwa_galerii.set(nazwa_galerii);
    }

    public ComboBox getTytul() {
        return Tytul;
    }

    public void setTytul(ComboBox tytul) {
        Tytul = tytul;
    }
}
