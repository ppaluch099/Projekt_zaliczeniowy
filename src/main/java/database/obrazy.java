package database;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class obrazy {
    private SimpleIntegerProperty Id_obrazu = new SimpleIntegerProperty();
    private SimpleIntegerProperty Rok = new SimpleIntegerProperty();
    private SimpleStringProperty Tytul = new SimpleStringProperty();
    private SimpleStringProperty Opis = new SimpleStringProperty();
    private SimpleIntegerProperty Id_artysty = new SimpleIntegerProperty();

    public int getId_obrazu() {
        return Id_obrazu.get();
    }

    public SimpleIntegerProperty id_obrazuProperty() {
        return Id_obrazu;
    }

    public void setId_obrazu(int id_obrazu) {
        this.Id_obrazu.set(id_obrazu);
    }

    public int getRok() {
        return Rok.get();
    }

    public SimpleIntegerProperty rokProperty() {
        return Rok;
    }

    public void setRok(int rok) {
        this.Rok.set(rok);
    }

    public String getTytul() {
        return Tytul.get();
    }

    public SimpleStringProperty tytulProperty() {
        return Tytul;
    }

    public void setTytul(String tytul) {
        this.Tytul.set(tytul);
    }

    public String getOpis() {
        return Opis.get();
    }

    public SimpleStringProperty opisProperty() {
        return Opis;
    }

    public void setOpis(String opis) {
        this.Opis.set(opis);
    }

    public int getId_artysty() {
        return Id_artysty.get();
    }

    public SimpleIntegerProperty id_artystyProperty() {
        return Id_artysty;
    }

    public void setId_artysty(int id_artysty) {
        this.Id_artysty.set(id_artysty);
    }
}
