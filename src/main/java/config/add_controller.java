package config;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.function.UnaryOperator;

public class add_controller{

    private static add_controller ad;

    public add_controller() {
        ad=this;
    }

    public static add_controller getAd() {
        return ad;
    }

    public static boolean bool;
    private DBConnect dbConnect = new DBConnect();
    private Connection conn = dbConnect.getConnection();
    public GridPane grid_pane;
    private ResultSet rs;
    private String refresh, query;
    private TextField a = new TextField();
    private TextField b = new TextField();
    private TextField c = new TextField();
    private TextField d = new TextField();
    private DatePicker data1 = new DatePicker();
    private DatePicker data2 = new DatePicker();
    private ComboBox combo = new ComboBox();
    private Label label;
    private String x;
    private ListView<Integer> id = new ListView<>();
    private PreparedStatement prSt;
    main_controller mc = new main_controller();

    UnaryOperator<TextFormatter.Change> nums = change -> {
        String text = change.getText();
        if (text.matches("\\d?")) {
            return change;
        }
        return null;
    };

    UnaryOperator<TextFormatter.Change> text = change -> {
        String text = change.getText();
        if (text.matches("^[a-zA-Z .'-]*$")) {
            return change;
        }
        return null;
    };

    public void init_style(){
        label = new Label("Nazwa stylu");
        a.setTextFormatter(new TextFormatter<String>(text));
        grid_pane.add(label,0,0);
        grid_pane.add(a,1,0);
        x = "style_menuitem";
        bool = false;
    }
    public void init_artysta() throws SQLException{
        label = new Label("Imie");
        a.setTextFormatter(new TextFormatter<String>(text));
        grid_pane.add(label,0,0);
        grid_pane.add(a,1,0);
        label = new Label("Nazwisko");
        b.setTextFormatter(new TextFormatter<String>(text));
        grid_pane.add(label,0,1);
        grid_pane.add(b,1,1);
        label = new Label("Data urodzenia");
        grid_pane.add(label,0,2);
        grid_pane.add(data1,1,2);
        label = new Label("Miejsce urodzenia");
        c.setTextFormatter(new TextFormatter<String>(text));
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
        bool = false;
    }
    public void init_adres() throws SQLException{
        label = new Label("Nazwa galerii");
        grid_pane.add(label,0,0);
        grid_pane.add(a,1,0);
        label = new Label("Miasto");
        b.setTextFormatter(new TextFormatter<String>(text));
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
        bool = false;
    }

    public void init_kraj(){
        label = new Label("Nazwa kraju");
        a.setTextFormatter(new TextFormatter<String>(text));
        grid_pane.add(label,0,0);
        grid_pane.add(a,1,0);
        x = "kraj_menuitem";
        bool = false;
    }
    public void init_obrazy() throws SQLException{
        label = new Label("Rok");
        a.setTextFormatter(new TextFormatter<Integer>(nums));
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
        bool = false;
    }
    public void init_wystawa() throws SQLException{
        label = new Label("Nazwa");
        grid_pane.add(label,0,0);
        grid_pane.add(a,1,0);
        label = new Label("Data rozpoczecia");
        grid_pane.add(label,0,1);
        data1.setEditable(false);
        grid_pane.add(data1,1,1);
        label = new Label("Data zakonczenia");
        grid_pane.add(label,0,2);
        data2.setEditable(false);
        grid_pane.add(data2,1,2);
        label = new Label("Galeria ");
        rs = conn.createStatement().executeQuery("SELECT Nazwa_galerii, Miasto FROM adres_wystawy");
        while (rs.next()) {
            combo.getItems().addAll(rs.getString("Nazwa_galerii") + " " + rs.getString("Miasto"));
        }
        grid_pane.add(label,0,3);
        grid_pane.add(combo,1,3);
        label = new Label("Obrazy");
        rs = conn.createStatement().executeQuery("SELECT Id_obrazu, Tytul FROM obrazy");
        ListView<String> listView = new ListView();
        while (rs.next()) {
            listView.getItems().addAll(rs.getString("Tytul"));
            id.getItems().addAll(rs.getInt("Id_obrazu"));
            }
        listView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        id.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        listView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object o, Object t1) {
                int z = listView.getSelectionModel().getSelectedIndex();
                id.getSelectionModel().select(z);
            }
        });

        grid_pane.add(label,0,4);
        grid_pane.add(listView,1,4);
        x = "wystawa_menuitem";
        bool = false;
    }

    @FXML
    public void dodaj() {
        int z = 0;
        try {
            switch (x){
                case "artysta_menuitem" :
                    refresh = "ALTER TABLE artysta AUTO_INCREMENT=1";
                    if (a.getText() != "" && b.getText() != "" && c.getText() != "" && data1.getValue() != null && combo.getSelectionModel().getSelectedItem() != "") {
                        query = "INSERT INTO artysta(Imie, Nazwisko, Data_ur, Miejsce_ur, Id_stylu)" + " values('" + a.getText()
                                + "','" + b.getText() + "','"
                                + data1.getValue() + "','"
                                + c.getText() + "',"
                                + "(SELECT style.Id_stylu FROM style WHERE style.Nazwa_stylu=\"" + combo.getSelectionModel().getSelectedItem() + "\"))";
                        z = conn.createStatement().executeUpdate(query);
                    }
                    else {
                        mc.nullValueDialog();
                    }break;
                case "style_menuitem" :
                    refresh = "ALTER TABLE style AUTO_INCREMENT=1";
                    if (a.getText() != "") {
                        query = "INSERT INTO style(Nazwa_stylu)" + " values('" + a.getText() + "')";
                        z = conn.createStatement().executeUpdate(query);
                    }
                    else {
                        mc.nullValueDialog();
                    }break;
                case "adres_menuitem" :
                    refresh = "ALTER TABLE adres_wystawy AUTO_INCREMENT=1";
                    if (a.getText() != "" && b.getText() != "" && combo.getSelectionModel().getSelectedItem() != "") {
                        query = "INSERT INTO adres_wystawy(Nazwa_galerii,Miasto,Id_kraju)" + " values('" + a.getText() + "','"
                                + b.getText() + "',"
                                + "(SELECT Id_kraju FROM kraje WHERE Nazwa_kraju=\"" + combo.getSelectionModel().getSelectedItem() + "\"))";
                        z = conn.createStatement().executeUpdate(query);
                    }
                    else {
                        mc.nullValueDialog();
                    }break;
                case "kraj_menuitem" :
                    refresh = "ALTER TABLE kraje AUTO_INCREMENT=1";
                    if (a.getText() != "") {
                        query = "INSERT INTO kraje(Nazwa_kraju)" + " values('" + a.getText() + "')";
                        z = conn.createStatement().executeUpdate(query);
                    }
                    else {
                        mc.nullValueDialog();
                    }break;
                case "wystawa_menuitem" :
                    refresh = "ALTER TABLE wystawa AUTO_INCREMENT=1";
                    if (data1.getValue() != null && data2.getValue() != null && a.getText() != "" && combo.getSelectionModel().getSelectedItem() != "") {
                        if (data2.getValue().isAfter(data1.getValue())) {
                            query = "INSERT INTO wystawa(Nazwa, Data_rozpoczecia,Data_zakonczenia,Id_adresu)" + " values('" + a.getText() + "','"
                                    + data1.getValue() + "','"
                                    + data2.getValue() + "',"
                                    + "(SELECT Id_adresu FROM adres_wystawy WHERE CONCAT(Nazwa_galerii, ' ',Miasto)=\"" + combo.getSelectionModel().getSelectedItem() + "\"))";
                            z = conn.createStatement().executeUpdate(query);
                        }
                        else {
                            mc.date_error_dialog();
                        }
                    }
                    else {
                        mc.nullValueDialog();
                    }break;
                case "obrazy_menuitem" :
                    refresh = "ALTER TABLE obrazy AUTO_INCREMENT=1";
                    if (a.getText() != "" && b.getText() != "" && c.getText() != "" && combo.getSelectionModel().getSelectedItem() != "") {
                        query = "INSERT INTO obrazy(Rok, Tytul, Opis, Id_artysty)" + " values('" + a.getText() + "','"
                                + b.getText() + "','"
                                + c.getText() + "',"
                                + "(SELECT Id_artysty FROM artysta WHERE CONCAT(Imie, ' ', Nazwisko) = \"" + combo.getSelectionModel().getSelectedItem() + "\"))";
                        z = conn.createStatement().executeUpdate(query);
                    }
                    else {
                        mc.nullValueDialog();
                    }break;
            }
            conn.createStatement().executeUpdate(refresh);
            if (x=="wystawa_menuitem" && z>0) {
                ResultSet idWys = conn.createStatement().executeQuery("SELECT Id_wystawy FROM wystawa WHERE Nazwa=\"" + a.getText() + "\"");
                int wynik = 0;
                while (idWys.next()) {
                    wynik = idWys.getInt(1);
                }
                prSt = conn.prepareStatement("INSERT INTO wystawa_pom VALUES("+ wynik +", ?)");
                ObservableList ls;
                ls = id.getSelectionModel().getSelectedItems();
                ls.forEach(e -> {
                    try {
                        prSt.setString(1, String.valueOf(e));
                        prSt.addBatch();
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                });
                prSt.executeBatch();
            }
            if(z>0) dialog();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    private void dialog() {
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Success");
        dialog.setContentText("Operacja powiodła się");
        grid_pane.getScene().getWindow().hide();
        bool = true;
        ButtonType type = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().add(type);
        dialog.showAndWait();
    }

    public boolean refBool() {
        return bool;
    }
}
