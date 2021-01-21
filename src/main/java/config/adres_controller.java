package config;

import database.adres;
import database.kraje;
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
import javafx.util.Duration;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class adres_controller implements Initializable {
    @FXML
    private TableView<adres> AddressFX;
    @FXML
    private TableColumn<adres, Integer> Id_adresu;
    @FXML
    private TableColumn<adres, String> Nazwa_galerii;
    @FXML
    private TableColumn<adres, String> Miasto;
    @FXML
    private TableColumn<kraje, String> Nazwa_kraju;
    @FXML
    private Button ad_add;

    private ObservableList<adres> list;
    private DBConnect dbConnect;
    private Connection conn;
    main_controller mc = new main_controller();
    add_controller ad = new add_controller();
    edit_controller ec = new edit_controller();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        dbConnect = new DBConnect();

        populate();
    }

    public void populate() {
        try {
            list = FXCollections.observableArrayList();
            String query = "SELECT * FROM adres_wystawy INNER JOIN kraje ON adres_wystawy.Id_kraju=kraje.Id_kraju GROUP BY Nazwa_galerii";
            conn = dbConnect.getConnection();
            ResultSet set = conn.createStatement().executeQuery(query);

            while (set.next()) {
                adres adr = new adres();
                adr.setId_adresu(set.getInt("Id_adresu"));
                adr.setNazwa_galerii(set.getString("Nazwa_galerii"));
                adr.setMiasto(set.getString("Miasto"));
                adr.setNazwa_kraju(set.getString("Nazwa_kraju"));
                list.add(adr);
            }

            Id_adresu.setCellValueFactory(new PropertyValueFactory<>("Id_adresu"));
            Nazwa_galerii.setCellValueFactory(new PropertyValueFactory<>("Nazwa_galerii"));
            Miasto.setCellValueFactory(new PropertyValueFactory<>("Miasto"));
            Nazwa_kraju.setCellValueFactory(new PropertyValueFactory<>("Nazwa_kraju"));

            AddressFX.setItems(list);
            AddressFX.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
            AddressFX.setRowFactory(tv -> {
                TableRow row = new TableRow();
                row.setOnMouseClicked(e -> {
                    if (row.isEmpty() && e.getButton()==MouseButton.PRIMARY) {
                        AddressFX.getSelectionModel().clearSelection();
                    }
                });
                return row ;
            });
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        AddressFX.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (keyEvent.getCode().equals(KeyCode.DELETE)) {
                    try {
                        delete();
                        System.out.println(keyEvent);
                    } catch (IOException | SQLException throwables) {
                        throwables.printStackTrace();
                    }
                }
                else if (keyEvent.getCode().equals(KeyCode.ADD)) {
                    ad_add.fire();
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
        if (!AddressFX.getSelectionModel().getSelectedItems().isEmpty()) {
                ObservableList selected = AddressFX.getSelectionModel().getSelectedIndices();
                String delQuery = "DELETE FROM adres_wystawy WHERE Id_adresu = ?";
                PreparedStatement stmt = conn.prepareStatement(delQuery);
                selected.forEach(e -> {
                    try {
                        stmt.setString(1, String.valueOf(AddressFX.getColumns().get(0).getCellObservableValue((Integer) e).getValue()));
                        stmt.addBatch();
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                });
                stmt.executeBatch();
                List removal = new ArrayList(AddressFX.getSelectionModel().getSelectedItems());
                list.removeAll(removal);
                AddressFX.getSelectionModel().clearSelection();
                String refresh = "ALTER TABLE adres_wystawy AUTO_INCREMENT=1";
                conn.createStatement().executeUpdate(refresh);
            }
         else {
            mc.empty_row_dialog();
        }
    }
    public void edit(ActionEvent actionEvent) throws IOException, SQLException {
        if(!AddressFX.getSelectionModel().getSelectedItems().isEmpty()) {
            String[] arr = {String.valueOf(AddressFX.getSelectionModel().getSelectedItem().getId_adresu()),
                            String.valueOf(AddressFX.getSelectionModel().getSelectedItem().getNazwa_galerii()),
                            String.valueOf(AddressFX.getSelectionModel().getSelectedItem().getMiasto()),
                            String.valueOf(AddressFX.getSelectionModel().getSelectedItem().getNazwa_kraju())};
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
