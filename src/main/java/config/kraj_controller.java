package config;

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

public class kraj_controller implements Initializable {

    @FXML
    private TableView<kraje> KrajFX;
    @FXML
    private TableColumn<kraje, Integer> Id_kraju;
    @FXML
    private TableColumn<kraje, String> Nazwa_kraju;

    private ObservableList<kraje> list;
    private DBConnect dbConnect;
    private Connection conn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        dbConnect = new DBConnect();

        populate();
    }

    private void populate() {
        try {
            list = FXCollections.observableArrayList();
            String query = "SELECT * from kraje GROUP BY Nazwa_kraju";
            conn = dbConnect.getConnection();
            ResultSet set = conn.createStatement().executeQuery(query);

            while (set.next()) {
                kraje kr = new kraje();
                kr.setId_kraju(set.getInt("Id_kraju"));
                kr.setNazwa_kraju(set.getString("Nazwa_kraju"));
                list.add(kr);
            }

            Id_kraju.setCellValueFactory(new PropertyValueFactory<>("Id_kraju"));
            Nazwa_kraju.setCellValueFactory(new PropertyValueFactory<>("Nazwa_kraju"));

            KrajFX.setItems(list);
            KrajFX.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
            KrajFX.setRowFactory(tv -> {
                TableRow row = new TableRow();
                row.setOnMouseClicked(e -> {
                    if(row.isEmpty() && e.getButton() == MouseButton.PRIMARY) {
                        KrajFX.getSelectionModel().clearSelection();
                    }
                });
                return row;
            });
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        KrajFX.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (keyEvent.getCode().equals(KeyCode.DELETE)) {
                    try {
                        delete();
                    } catch (IOException | SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    Timeline time;

    public void add(ActionEvent actionEvent) throws IOException, SQLException {
        main_controller mc = new main_controller();
        add_controller ad = new add_controller();
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
        if (!KrajFX.getSelectionModel().getSelectedItems().isEmpty()) {
                ObservableList selected = KrajFX.getSelectionModel().getSelectedIndices();
                String delQuery = "DELETE FROM kraje WHERE Id_kraju = ?";
                PreparedStatement stmt = conn.prepareStatement(delQuery);
                selected.forEach(e -> {
                    try {
                        stmt.setString(1, String.valueOf(KrajFX.getColumns().get(0).getCellObservableValue((Integer) e).getValue()));
                        stmt.addBatch();
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                });
                stmt.executeBatch();
                List removal = new ArrayList(KrajFX.getSelectionModel().getSelectedItems());
                list.removeAll(removal);
                String refresh = "ALTER TABLE kraje AUTO_INCREMENT=1";
                conn.createStatement().executeUpdate(refresh);
        }
        else {
            main_controller mc = new main_controller();
            mc.empty_row_dialog();
        }
    }

    public void edit(ActionEvent actionEvent) throws IOException, SQLException {
        main_controller mc = new main_controller();
        edit_controller ec = new edit_controller();
        if(!KrajFX.getSelectionModel().getSelectedItems().isEmpty()) {
            String[] arr = {String.valueOf(KrajFX.getSelectionModel().getSelectedItem().getId_kraju()),
                            String.valueOf(KrajFX.getSelectionModel().getSelectedItem().getNazwa_kraju())};
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
