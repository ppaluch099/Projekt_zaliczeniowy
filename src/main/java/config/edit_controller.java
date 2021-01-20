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
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.function.UnaryOperator;

public class edit_controller {

    public static ArrayList ar;
    private static edit_controller ed;

    public edit_controller() {
        ed=this;
    }

    public static edit_controller getEd() {
        return ed;
    }
    public static boolean bool;
    public DBConnect dbConnect = new DBConnect();
    public Connection conn = dbConnect.getConnection();
    public GridPane grid_pane;
    public ResultSet rs;
    public String query;
    public TextField a = new TextField();
    public TextField b = new TextField();
    public TextField c = new TextField();
    public TextField d = new TextField();
    public DatePicker data1 = new DatePicker();
    public DatePicker data2 = new DatePicker();
    public ComboBox combo = new ComboBox();
    public Label label;
    public String x;
    public ListView<Integer> id = new ListView<>();
    public PreparedStatement prSt;
    public String arr[];
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

    public void init_adres(String[] adres) throws SQLException {
        label = new Label("Nazwa galerii");
        grid_pane.add(label,0,0);
        a.setText(adres[1]);
        grid_pane.add(a,1,0);
        label = new Label("Miasto");
        grid_pane.add(label,0,1);
        b.setText(adres[2]);
        b.setTextFormatter(new TextFormatter<String>(text));
        grid_pane.add(b,1,1);
        label = new Label("Kraj");
        rs = conn.createStatement().executeQuery("SELECT Nazwa_kraju FROM kraje");
        while (rs.next()) {
            combo.getItems().addAll(rs.getString("Nazwa_kraju"));
        }
        grid_pane.add(label,0,2);
        combo.setValue(adres[3]);
        grid_pane.add(combo,1,2);
        x = "adres_edit";
        arr = adres;
        bool = false;
    }

    public void init_artysta(String[] artysta) throws SQLException {
        label = new Label("Imie");
        grid_pane.add(label,0,0);
        a.setText(artysta[1]);
        a.setTextFormatter(new TextFormatter<String>(text));
        grid_pane.add(a, 1, 0);
        label = new Label("Nazwisko");
        grid_pane.add(label,0,1);
        b.setText(artysta[2]);
        b.setTextFormatter(new TextFormatter<String>(text));
        grid_pane.add(b,1,1);
        label = new Label("Data urodzenia");
        grid_pane.add(label,0,2);
        data1.setValue(LocalDate.parse(artysta[3]));
        grid_pane.add(data1,1,2);
        label = new Label("Miejsce urodzenia");
        grid_pane.add(label,0,3);
        c.setText(artysta[4]);
        c.setTextFormatter(new TextFormatter<String>(text));
        grid_pane.add(c,1,3);
        label = new Label("Styl");
        rs = conn.createStatement().executeQuery("SELECT Nazwa_stylu FROM style");
        while (rs.next()) {
            combo.getItems().addAll(rs.getString("Nazwa_stylu"));
        }
        grid_pane.add(label,0,4);
        combo.setValue(artysta[5]);
        grid_pane.add(combo,1,4);
        x = "artysta_edit";
        arr = artysta;
        bool = false;
    }

    public void init_kraj(String[] kraj) {
        label = new Label("Nazwa kraju");
        grid_pane.add(label,0,0);
        a.setText(kraj[1]);
        a.setTextFormatter(new TextFormatter<String>(text));
        grid_pane.add(a,1,0);
        x = "kraj_edit";
        arr = kraj;
        bool = false;
    }

    public void init_obrazy(String[] obrazy) throws SQLException {
        label = new Label("Rok");
        a.setText(obrazy[1]);
        a.setTextFormatter(new TextFormatter<Integer>(nums));
        grid_pane.add(label,0,0);
        grid_pane.add(a,1,0);
        label = new Label("Tytul");
        grid_pane.add(label,0,1);
        b.setText(obrazy[2]);
        grid_pane.add(b,1,1);
        label = new Label("Opis");
        grid_pane.add(label,0,2);
        c.setText(obrazy[3]);
        grid_pane.add(c,1,2);
        label = new Label("Imie i nazwisko autora");
        rs = conn.createStatement().executeQuery("SELECT Imie, Nazwisko FROM artysta");
        while (rs.next()) {
            combo.getItems().addAll(rs.getString("Imie") + " " +rs.getString("Nazwisko"));
        }
        grid_pane.add(label,0,3);
        combo.setValue(obrazy[4]);
        grid_pane.add(combo,1,3);
        x = "obrazy_edit";
        arr = obrazy;
        bool = false;
    }

    public void init_style(String[] style) {
        label = new Label("Nazwa stylu");
        grid_pane.add(label,0,0);
        a.setText(style[1]);
        a.setTextFormatter(new TextFormatter<String>(text));
        grid_pane.add(a,1,0);
        x = "style_edit";
        arr = style;
        bool = false;
    }

    public void init_wystawa(String[] wystawy) throws SQLException{
        label = new Label("Nazwa");
        grid_pane.add(label,0,0);
        a.setText(wystawy[1]);
        grid_pane.add(a,1,0);
        label = new Label("Data rozpoczecia");
        grid_pane.add(label,0,1);
        data1.setValue(LocalDate.parse(wystawy[2]));
        data1.setEditable(false);
        grid_pane.add(data1,1,1);
        label = new Label("Data zakonczenia");
        grid_pane.add(label,0,2);
        data2.setValue(LocalDate.parse(wystawy[3]));
        data2.setEditable(false);
        grid_pane.add(data2,1,2);
        label = new Label("Galeria ");
        rs = conn.createStatement().executeQuery("SELECT Nazwa_galerii FROM adres_wystawy");
        while (rs.next()) {
            combo.getItems().addAll(rs.getString("Nazwa_galerii"));
        }
        grid_pane.add(label,0,3);
        combo.setValue(wystawy[4]);
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
        for (int i = 0; i < ar.size(); i++) {
            listView.getSelectionModel().select(String.valueOf(ar.get(i)));
        }
        listView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object o, Object t1) {
                int z = listView.getSelectionModel().getSelectedIndex();
                id.getSelectionModel().select(z);
            }
        });
        grid_pane.add(label,0,4);
        grid_pane.add(listView,1,4);
        x = "wystawa_edit";
        arr = wystawy;
        bool = false;
    }

    public void setPaintings(ArrayList array) {
        ar = array;
    }

    private int counter = 0;
    @FXML
    public void edit() throws SQLException {
        int z = 0;
        switch (x) {
            case "adres_edit":
                if (a.getText() != "" && b.getText() != "" && combo.getSelectionModel().getSelectedItem() != "") {
                    query = "UPDATE adres_wystawy SET Nazwa_galerii =\'" +
                            a.getText() +
                            "\', Miasto = \'" +
                            b.getText() + "\', Id_kraju = " +
                            "(SELECT Id_kraju FROM kraje WHERE Nazwa_kraju= \'" +
                            combo.getSelectionModel().getSelectedItem() +
                            "\') WHERE Id_adresu = " +
                            arr[0];
                    z = conn.createStatement().executeUpdate(query);
                }
                else {
                    mc.nullValueDialog();
                }
                break;
            case "artysta_edit":
                if (a.getText() != "" && b.getText() != "" && data1.getValue() != null && c.getText() != "" && combo.getSelectionModel().getSelectedItem() != ""){
                query = "UPDATE artysta SET Imie =\'" +
                        a.getText() + "\', Nazwisko = \'" +
                        b.getText() + "\', Data_ur = \'" +
                        data1.getValue() + "\', Miejsce_ur = \'" +
                        c.getText() + "\', Id_stylu = " +
                        "(SELECT Id_stylu FROM style WHERE Nazwa_stylu =\'" +
                        combo.getSelectionModel().getSelectedItem() +
                        "\') WHERE ID_artysty = " +
                        arr[0];
                    z = conn.createStatement().executeUpdate(query);
                }
                else {
                    mc.nullValueDialog();
                }
                break;
            case "kraj_edit":
                if (a.getText() != "") {
                    query = "UPDATE kraje SET Nazwa_kraju =\'" +
                            a.getText() +
                            "\' WHERE Id_kraju =" +
                            arr[0];
                    z = conn.createStatement().executeUpdate(query);
                }
                else {
                    mc.nullValueDialog();
                }
                break;
            case "obrazy_edit":
                if (a.getText() != "" && b.getText() != "" && c.getText() != "" && combo.getSelectionModel().getSelectedItem() != "") {
                    query = "UPDATE obrazy SET Rok =\'" +
                            a.getText() +
                            "\', Tytul=\'" +
                            b.getText() +
                            "\', Opis =\'" +
                            c.getText() +
                            "\', ID_artysty =" +
                            "(SELECT ID_artysty FROM artysta WHERE CONCAT(Imie,' ',Nazwisko)=\'" +
                            combo.getSelectionModel().getSelectedItem() +
                            "\') WHERE ID_obrazu =" +
                            arr[0];
                    z = conn.createStatement().executeUpdate(query);
                }
                else {
                    mc.nullValueDialog();
                }
                break;
            case "style_edit":
                if (a.getText() != "") {
                    query = "UPDATE style SET Nazwa_stylu =\'" +
                            a.getText() +
                            "\' WHERE Id_stylu =" +
                            arr[0];
                    z = conn.createStatement().executeUpdate(query);
                }
                else {
                    mc.nullValueDialog();
                }
                break;
            case "wystawa_edit":
                if (a.getText() != "" && data1.getValue() != null && data2.getValue() != null && combo.getSelectionModel().getSelectedItem() != "") {
                if (data2.getValue().isAfter(data1.getValue())) {
                    query = "UPDATE wystawa SET Nazwa=\'" +
                            a.getText() +
                            "\', Data_rozpoczecia = \'" +
                            data1.getValue() +
                            "\', Data_zakonczenia = \'" +
                            data2.getValue() +
                            "\', Id_adresu = " +
                            "(SELECT Id_adresu FROM adres_wystawy WHERE Nazwa_galerii=\'" +
                            combo.getSelectionModel().getSelectedItem() +
                            "\') WHERE Id_wystawy =" +
                            arr[0];
                    z = conn.createStatement().executeUpdate(query);
                }
                else {
                    mc.date_error_dialog();
                }
                }
                else {
                    mc.nullValueDialog();
                }
                break;
        }
        try {
            if (x == "wystawa_edit") {
                int idWys = Integer.parseInt(arr[0]);
                prSt = conn.prepareStatement("INSERT INTO wystawa_pom VALUES(" + idWys + ", ?)");
                ObservableList ls;
                ls = id.getSelectionModel().getSelectedItems();
                ls.forEach(e -> {
                    try {
                        prSt.setString(1, String.valueOf(e));
                        prSt.addBatch();
                        counter++;
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                });
                if (counter > 0)
                    conn.createStatement().executeUpdate("DELETE FROM wystawa_pom WHERE Id_wystawy =" + idWys);
                prSt.executeBatch();
            }
            if (z > 0) dialog();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    public void dialog() {
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
