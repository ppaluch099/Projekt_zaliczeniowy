package config;

import database.adres;
import database.wystawa;
import database.obrazy;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.util.Callback;
import javafx.util.Duration;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class wystawa_controller implements Initializable {
    @FXML
    private TableView<wystawa> ExhibitionsFX;
    @FXML
    private TableColumn<wystawa,Integer> Id_wystawy;
    @FXML
    private TableColumn<wystawa,String> Nazwa;
    @FXML
    private TableColumn<wystawa, Date> Data_rozpoczecia;
    @FXML
    private TableColumn<wystawa, Date> Data_zakonczenia;
    @FXML
    private TableColumn<adres,String> Nazwa_galerii;
    @FXML
    private TableColumn<obrazy, String> Tytul;
    @FXML
    private Button wy_add;
    @FXML
    private Button wy_edit;
    @FXML
    private Button wy_del;

    private ObservableList<wystawa> list;
    private DBConnect dbConnect;
    private Connection conn;
    main_controller mc = new main_controller();
    add_controller ad = new add_controller();
    edit_controller ec = new edit_controller();

    private static ArrayList ar;

    public static ArrayList getAr() {
        return ar;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        dbConnect = new DBConnect();

        populate();
    }

    private void populate() {
        try {
            list = FXCollections.observableArrayList();
            String query = "SELECT * FROM wystawa INNER JOIN adres_wystawy ON wystawa.Id_adresu=adres_wystawy.Id_adresu GROUP BY Data_rozpoczecia";
            conn = dbConnect.getConnection();
            ResultSet set = conn.createStatement().executeQuery(query);
            int id;
            ResultSet obrazy;
            while (set.next()) {
                wystawa wys = new wystawa();
                wys.setId_wystawy(set.getInt("Id_wystawy"));
                id = Integer.valueOf(set.getInt("Id_wystawy"));
                obrazy = conn.createStatement().executeQuery("SELECT Tytul FROM obrazy INNER JOIN wystawa_pom ON obrazy.ID_obrazu=wystawa_pom.Id_obrazu INNER JOIN wystawa ON wystawa_pom.Id_wystawy=wystawa.Id_wystawy WHERE wystawa_pom.Id_wystawy=" + id);
                wys.setNazwa(set.getString("Nazwa"));
                wys.setData_rozpoczecia(set.getDate("Data_rozpoczecia"));
                wys.setData_zakonczenia(set.getDate("Data_zakonczenia"));
                wys.setNazwa_galerii(set.getString("Nazwa_galerii"));
                while (obrazy.next()) {
                    wys.getTytul().getItems().addAll(obrazy.getString("Tytul"));
                }
                wys.getTytul().getSelectionModel().selectFirst();
                wys.getTytul().setPrefWidth(200);
                if(wys.getTytul().getSelectionModel().isEmpty()){
                    wys.getTytul().setVisible(false);
                }
                list.add(wys);
            }

            Id_wystawy.setCellValueFactory(new PropertyValueFactory<>("Id_wystawy"));
            Nazwa.setCellValueFactory(new PropertyValueFactory<>("Nazwa"));
            Data_rozpoczecia.setCellValueFactory(new PropertyValueFactory<>("Data_rozpoczecia"));
            Data_zakonczenia.setCellValueFactory(new PropertyValueFactory<>("Data_zakonczenia"));
            Nazwa_galerii.setCellValueFactory(new PropertyValueFactory<>("Nazwa_galerii"));
            Tytul.setCellValueFactory(new PropertyValueFactory<>("Tytul"));

            ExhibitionsFX.setItems(list);
            ExhibitionsFX.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
            ExhibitionsFX.setRowFactory(tv -> {
                TableRow row = new TableRow();
                row.setOnMouseClicked(e -> {
                    if(row.isEmpty() && e.getButton() == MouseButton.PRIMARY) {
                        ExhibitionsFX.getSelectionModel().clearSelection();
                    }
                });
                return row;
            });
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        Tooltip t = new Tooltip("Dodaj");
        wy_add.setTooltip(t);
        t = new Tooltip("Edytuj");
        wy_edit.setTooltip(t);
        t  = new Tooltip("Usuń");
        wy_del.setTooltip(t);

        ExhibitionsFX.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (keyEvent.getCode().equals(KeyCode.DELETE)) {
                    try {
                        delete();
                    } catch (IOException | SQLException e) {
                        e.printStackTrace();
                    }
                }
                else if (keyEvent.getCode().equals(KeyCode.ADD)) {
                    wy_add.fire();
                }
            }
        });
    }

    Timeline time;

    public void add(ActionEvent actionEvent) throws IOException, SQLException {
        mc.add(actionEvent);
        time = new Timeline(new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if (ad.refBool()) {
                    refresh();
                    time.stop();
                }
            }
        }));
        time.setCycleCount(Timeline.INDEFINITE);
        time.play();
    }

    public void delete() throws IOException, SQLException {
        if (!ExhibitionsFX.getSelectionModel().getSelectedItems().isEmpty()) {
            ObservableList selected = ExhibitionsFX.getSelectionModel().getSelectedIndices();
            String delQuery = "DELETE FROM wystawa WHERE Id_wystawy = ?";
            PreparedStatement stmt = conn.prepareStatement(delQuery);
            selected.forEach(e -> {
                try {
                    stmt.setString(1,String.valueOf(ExhibitionsFX.getColumns().get(0).getCellObservableValue((Integer) e).getValue()));
                    stmt.addBatch();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            });
            stmt.executeBatch();
            String refresh = "ALTER TABLE wystawa AUTO_INCREMENT=1";
            conn.createStatement().executeUpdate(refresh);
            refresh();
        }
        else {
            mc.empty_row_dialog();
        }
    }

    public void edit(ActionEvent actionEvent) throws IOException, SQLException {
        if (!ExhibitionsFX.getSelectionModel().getSelectedItems().isEmpty()) {
            String[] arr = {String.valueOf(ExhibitionsFX.getSelectionModel().getSelectedItem().getId_wystawy()),
                            String.valueOf(ExhibitionsFX.getSelectionModel().getSelectedItem().getNazwa()),
                            String.valueOf(ExhibitionsFX.getSelectionModel().getSelectedItem().getData_rozpoczecia()),
                            String.valueOf(ExhibitionsFX.getSelectionModel().getSelectedItem().getData_zakonczenia()),
                            String.valueOf(ExhibitionsFX.getSelectionModel().getSelectedItem().getNazwa_galerii())};
            ObservableList o = ExhibitionsFX.getSelectionModel().getSelectedItem().getTytul().getItems();
            ArrayList obr = new ArrayList();
            o.forEach(e -> {
                obr.add(String.valueOf(e));
            });
            ec.setPaintings(obr);
            mc.edit(actionEvent, arr);
            time = new Timeline(new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    if (ec.refBool()) {
                        refresh();
                        time.stop();
                    }
                }
            }));
            time.setCycleCount(Timeline.INDEFINITE);
            time.play();
        }
        else {
            mc.empty_row_dialog();
        }
    }

    public void refresh() {
        list.clear();
        populate();
    }
}