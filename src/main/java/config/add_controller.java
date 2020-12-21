package config;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;

public class add_controller{

    private static add_controller ad;

    public add_controller() {
        ad=this;
    }

    public static add_controller getAd() {
        return ad;
    }

    public DBConnect dbConnect = new DBConnect();
    public Connection conn = dbConnect.getConnection();
    public GridPane grid_pane;
    public ResultSet rs;
    public String refresh, query;
    public TextField a = new TextField();
    public TextField b = new TextField();
    public TextField c = new TextField();
    public TextField d = new TextField();
    public DatePicker data1 = new DatePicker();
    public DatePicker data2 = new DatePicker();
    public ComboBox combo = new ComboBox();
    public Label label;
    public String x;

    public void init_style(){
        label = new Label("Nazwa stylu");
        grid_pane.add(label,0,0);
        grid_pane.add(a,1,0);
        x = "style_menuitem";
    }
    public void init_artysta() throws SQLException{
        label = new Label("Imie");
        grid_pane.add(label,0,0);
        grid_pane.add(a,1,0);
        label = new Label("Nazwisko");
        grid_pane.add(label,0,1);
        grid_pane.add(b,1,1);
        label = new Label("Data urodzenia");
        grid_pane.add(label,0,2);
        grid_pane.add(data1,1,2);
        label = new Label("Miejsce urodzenia");
        grid_pane.add(label,0,3);
        grid_pane.add(c,1,3);
        label = new Label("Styl");
        rs = conn.createStatement().executeQuery("SELECT Nazwa_stylu FROM style");
        while (rs.next()) {
            combo.getItems().addAll(rs.getString("Nazwa_stylu"));
        }
        grid_pane.add(label,0,4);
        grid_pane.add(combo,1,4);
        x = "artysta_menuitem";
    }
    public void init_adres() throws SQLException{
        label = new Label("Nazwa galerii");
        grid_pane.add(label,0,0);
        grid_pane.add(a,1,0);
        label = new Label("Miasto");
        grid_pane.add(label,0,1);
        grid_pane.add(b,1,1);
        label = new Label("Kraj");
        rs = conn.createStatement().executeQuery("SELECT Nazwa_kraju FROM kraje");
        while (rs.next()) {
            combo.getItems().addAll(rs.getString("Nazwa_kraju"));
        }
        grid_pane.add(label,0,2);
        grid_pane.add(combo,1,2);
        x = "adres_menuitem";
    }
    public void init_kraj(){
        label = new Label("Nazwa kraju");
        grid_pane.add(label,0,0);
        grid_pane.add(a,1,0);
        x = "kraj_menuitem";
    }
    public void init_obrazy() throws SQLException{
        label = new Label("Rok");
        a.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                int year = Calendar.getInstance().get(Calendar.YEAR);
                try {
                    if (!t1.matches("\\d{0,4}?") || !(Integer.valueOf(t1) <= year)) {
                        a.setText(s);
                    }
                }
                catch (NumberFormatException e) {
                }
            }
        });
        grid_pane.add(label,0,0);
        grid_pane.add(a,1,0);
        label = new Label("Tytul");
        grid_pane.add(label,0,1);
        grid_pane.add(b,1,1);
        label = new Label("Opis");
        grid_pane.add(label,0,2);
        grid_pane.add(c,1,2);
        label = new Label("Imie i nazwisko autora");
        rs = conn.createStatement().executeQuery("SELECT Imie, Nazwisko FROM artysta");
        while (rs.next()) {
            combo.getItems().addAll(rs.getString("Imie") + " " +rs.getString("Nazwisko"));
        }
        grid_pane.add(label,0,3);
        grid_pane.add(combo,1,3);
        x = "obrazy_menuitem";
    }
    public void init_wystawa() throws SQLException{
        label = new Label("Nazwa");
        grid_pane.add(label,0,0);
        grid_pane.add(a,1,0);
        label = new Label("Data rozpoczecia");
        grid_pane.add(label,0,1);
        grid_pane.add(data1,1,1);
        label = new Label("Data zakonczenia");
        grid_pane.add(label,0,2);
        grid_pane.add(data2,1,2);
        label = new Label("Galeria ");
        rs = conn.createStatement().executeQuery("SELECT Nazwa_galerii, Miasto FROM adres_wystawy");
        while (rs.next()) {
            combo.getItems().addAll(rs.getString("Nazwa_galerii") + " " + rs.getString("Miasto"));
        }
        grid_pane.add(label,0,3);
        grid_pane.add(combo,1,3);
        x = "wystawa_menuitem";
    }

    @FXML
    public void dodaj() {
        try {
            switch (x){
                case "artysta_menuitem" :
                    query = "INSERT INTO artysta(Imie, Nazwisko, Data_ur, Miejsce_ur, Id_stylu)" + " values('" + a.getText() + "','" + b.getText() + "','" + data1.getValue() + "','" + c.getText() + "'," + "(SELECT style.Id_stylu FROM style WHERE style.Nazwa_stylu=\"" + combo.getSelectionModel().getSelectedItem() + "\"))";
                    refresh = "ALTER TABLE artysta AUTO_INCREMENT=1";break;
                case "style_menuitem" :
                    query = "INSERT INTO style(Nazwa_stylu)" + " values('" + a.getText() + "')";
                    refresh = "ALTER TABLE style AUTO_INCREMENT=1";break;
                case "adres_menuitem" :
                    query = "INSERT INTO adres_wystawy(Nazwa_galerii,Miasto,Id_kraju)" + " values('" + a.getText() + "','" + b.getText() + "'," + "(SELECT Id_kraju FROM kraje WHERE Nazwa_kraju=\"" + combo.getSelectionModel().getSelectedItem() + "\"))";
                    refresh = "ALTER TABLE adres_wystawy AUTO_INCREMENT=1";break;
                case "kraj_menuitem" :
                    query = "INSERT INTO kraje(Nazwa_kraju)" + " values('" + a.getText() + "')";
                    refresh = "ALTER TABLE kraje AUTO_INCREMENT=1";break;
                case "wystawa_menuitem" :
                    query = "INSERT INTO wystawa(Nazwa, Data_rozpoczecia,Data_zakonczenia,Id_adresu)" + " values('" + a.getText() + "','" + data1.getValue() + "','" + data2.getValue() + "'," + "(SELECT Id_adresu FROM adres_wystawy WHERE CONCAT(Nazwa_galerii, ' ',Miasto)=\"" + combo.getSelectionModel().getSelectedItem() + "\"))";
                    refresh = "ALTER TABLE wystawa AUTO_INCREMENT=1";break;
                case "obrazy_menuitem" :
                    query = "INSERT INTO obrazy(Rok, Tytul, Opis, Id_artysty)" + " values('" + a.getText() + "','" + b.getText() + "','" + c.getText() + "',(" + "SELECT Id_artysty FROM artysta WHERE CONCAT(Imie, ' ', Nazwisko) = \"" + combo.getSelectionModel().getSelectedItem() +"\"))";
                    refresh = "ALTER TABLE obrazy AUTO_INCREMENT=1";break;
            }
            conn.createStatement().executeUpdate(refresh);
            int z = conn.createStatement().executeUpdate(query);
            int x = 0;
            if(z>0) x=1;
            dialog(x);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    public void dialog(int x) {
        Dialog<String> dialog = new Dialog<>();
        if(x==1){
            dialog.setTitle("Success");
            dialog.setContentText("Operacja powiodła się");
            grid_pane.getScene().getWindow().hide();
        }
        else {
            dialog.setTitle("Failure");
            dialog.setContentText("Operacja nie powiodło się");
        }
        ButtonType type = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().add(type);
        dialog.showAndWait();
    }
}
