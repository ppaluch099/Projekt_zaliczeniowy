package config;

import database.style;
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
import java.util.List;
import java.util.ResourceBundle;

public class style_controller implements Initializable {
    @FXML
    private TableView<style> StyleFX;
    @FXML
    private TableColumn<style,Integer> Id_stylu;
    @FXML
    private TableColumn<style,String> Nazwa_stylu;
    @FXML
    private Button st_add;
    @FXML
    private Button st_edit;
    @FXML
    private Button st_del;

    private ObservableList<style> list;
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

    private void populate() {
        try {
            list = FXCollections.observableArrayList();
            String query = "SELECT * from style GROUP BY Nazwa_stylu";
            conn = dbConnect.getConnection();
            ResultSet set = conn.createStatement().executeQuery(query);

            while (set.next()) {
                style sty = new style();
                sty.setId_stylu(set.getInt("Id_stylu"));
                sty.setNazwa_stylu(set.getString("Nazwa_stylu"));
                list.add(sty);
            }

            Id_stylu.setCellValueFactory(new PropertyValueFactory<>("Id_stylu"));
            Nazwa_stylu.setCellValueFactory(new PropertyValueFactory<>("Nazwa_stylu"));

            StyleFX.setItems(list);
            StyleFX.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
            StyleFX.setRowFactory(tv -> {
                TableRow row = new TableRow();
                row.setOnMouseClicked(e -> {
                    if(row.isEmpty() && e.getButton() == MouseButton.PRIMARY) {
                        StyleFX.getSelectionModel().clearSelection();
                    }
                });
                return row;
            });
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        Tooltip t = new Tooltip("Dodaj");
        st_add.setTooltip(t);
        t = new Tooltip("Edytuj");
        st_edit.setTooltip(t);
        t  = new Tooltip("Usuń");
        st_del.setTooltip(t);

        StyleFX.setOnKeyPressed(new EventHandler<KeyEvent>() {
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
                    st_add.fire();
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
        if(!StyleFX.getSelectionModel().getSelectedItems().isEmpty()) {
            ObservableList selected = StyleFX.getSelectionModel().getSelectedIndices();
            String delQuery = "DELETE FROM style WHERE Id_stylu = ?";
            PreparedStatement stmt = conn.prepareStatement(delQuery);
            selected.forEach(e -> {
                try {
                    stmt.setString(1,String.valueOf(StyleFX.getColumns().get(0).getCellObservableValue((Integer) e).getValue()));
                    stmt.addBatch();
                }
                catch (SQLException throwables){
                    throwables.printStackTrace();
                }
            });
            stmt.executeBatch();
            List removal = StyleFX.getSelectionModel().getSelectedItems();
            list.removeAll(removal);
            String refresh = "ALTER TABLE style AUTO_INCREMENT=1";
            conn.createStatement().executeUpdate(refresh);
        }
        else {
            mc.empty_row_dialog();
        }
    }

    public void edit(ActionEvent actionEvent) throws IOException, SQLException {
        if(!StyleFX.getSelectionModel().getSelectedItems().isEmpty()) {
            String[] arr = {String.valueOf(StyleFX.getSelectionModel().getSelectedItem().getId_stylu()),
                            String.valueOf(StyleFX.getSelectionModel().getSelectedItem().getNazwa_stylu())};
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