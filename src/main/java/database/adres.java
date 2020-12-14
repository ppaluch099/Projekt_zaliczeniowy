package database;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class adres {
    private SimpleIntegerProperty Id_adresu = new SimpleIntegerProperty();
    private SimpleStringProperty Nazwa_galerii = new SimpleStringProperty();
    private SimpleStringProperty Miasto = new SimpleStringProperty();
    private SimpleIntegerProperty Id_kraju = new SimpleIntegerProperty();

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

    public int getId_kraju() {
        return Id_kraju.get();
    }

    public SimpleIntegerProperty id_krajuProperty() {
        return Id_kraju;
    }

    public void setId_kraju(int id_kraju) {
        this.Id_kraju.set(id_kraju);
    }
}
