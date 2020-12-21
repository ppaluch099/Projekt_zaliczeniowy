package database;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class adres {
    private SimpleIntegerProperty Id_adresu = new SimpleIntegerProperty();
    private SimpleStringProperty Nazwa_galerii = new SimpleStringProperty();
    private SimpleStringProperty Miasto = new SimpleStringProperty();
    private SimpleStringProperty Nazwa_kraju = new SimpleStringProperty();

    public int getId_adresu() {
        return Id_adresu.get();
    }

    public SimpleIntegerProperty id_adresuProperty() {
        return Id_adresu;
    }

    public void setId_adresu(int id_adresu) {
        this.Id_adresu.set(id_adresu);
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

    public String getMiasto() {
        return Miasto.get();
    }

    public SimpleStringProperty miastoProperty() {
        return Miasto;
    }

    public void setMiasto(String miasto) {
        this.Miasto.set(miasto);
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
