package database;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class obrazy {
    private SimpleIntegerProperty Id_obrazu = new SimpleIntegerProperty();
    private SimpleIntegerProperty Rok = new SimpleIntegerProperty();
    private SimpleStringProperty Tytul = new SimpleStringProperty();
    private SimpleStringProperty Opis = new SimpleStringProperty();
    private SimpleStringProperty Imie_Nazwisko = new SimpleStringProperty();

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

    public String getImie_Nazwisko() {
        return Imie_Nazwisko.get();
    }

    public SimpleStringProperty imie_NazwiskoProperty() {
        return Imie_Nazwisko;
    }

    public void setImie_Nazwisko(String imie_Nazwisko) {
        this.Imie_Nazwisko.set(imie_Nazwisko);
    }
}
