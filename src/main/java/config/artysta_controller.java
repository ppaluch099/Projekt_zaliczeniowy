package config;

import database.artysta;
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
import javafx.util.Callback;
import javafx.util.Duration;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class artysta_controller implements Initializable {
    @FXML
    private TableView<artysta> ArtistsFX;
    @FXML
    private TableColumn<artysta,Integer> Id_artysty;
    @FXML
    private TableColumn<artysta,String> Imie;
    @FXML
    private TableColumn<artysta,String> Nazwisko;
    @FXML
    private TableColumn<artysta, Date> Data_ur;
    @FXML
    private TableColumn<artysta,String> Miejsce_ur;
    @FXML
    private TableColumn<style,String> Nazwa_stylu;
    @FXML
    private Button ar_add;
    @FXML
    private Button ar_edit;
    @FXML
    private Button ar_del;
    private ObservableList<artysta> list;
    private DBConnect dbConnect;
    private Connection conn;
    main_controller mc = new main_controller();
    add_controller ad = new add_controller();
    edit_controller ec = new edit_controller();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
       populate();
    }

    public void populate() {
        try {
            dbConnect = new DBConnect();
            list = FXCollections.observableArrayList();
            String query = "SELECT * FROM artysta INNER JOIN style ON artysta.Id_stylu=style.Id_stylu GROUP BY Nazwisko";
            conn = dbConnect.getConnection();
            ResultSet set = conn.createStatement().executeQuery(query);

            while (set.next()) {
                artysta art = new artysta();
                art.setId_artysty(set.getInt("Id_artysty"));
                art.setImie(set.getString("imie"));
                art.setNazwisko(set.getString("nazwisko"));
                art.setData_ur(set.getDate("data_ur"));
                art.setMiejsce_ur(set.getString("miejsce_ur"));
                art.setNazwa_stylu(set.getString("Nazwa_stylu"));
                list.add(art);
            }

            Id_artysty.setCellValueFactory(new PropertyValueFactory<>("Id_artysty"));
            Imie.setCellValueFactory(new PropertyValueFactory<>("imie"));
            Nazwisko.setCellValueFactory(new PropertyValueFactory<>("nazwisko"));
            Data_ur.setCellValueFactory(new PropertyValueFactory<>("data_ur"));
            Miejsce_ur.setCellValueFactory(new PropertyValueFactory<>("miejsce_ur"));
            Nazwa_stylu.setCellValueFactory(new PropertyValueFactory<>("Nazwa_stylu"));

            ArtistsFX.setItems(list);
            ArtistsFX.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
            ArtistsFX.setRowFactory(tv -> {
                TableRow row = new TableRow();
                row.setOnMouseClicked(e -> {
                    if (row.isEmpty() && e.getButton() == MouseButton.PRIMARY) {
                        ArtistsFX.getSelectionModel().clearSelection();
                    }
                });
                return row;
            });
            Callback<TableColumn<artysta, String>, TableCell<artysta, String>> nazwiskoCellFactory
                    = Nazwisko.getCellFactory();
            Nazwisko.setCellFactory(c -> {
                TableCell<artysta, String> cell = nazwiskoCellFactory.call(c);

                Tooltip tooltip = new Tooltip();
                if (tooltip != null) {
                    tooltip.textProperty().bind(cell.itemProperty().asString());
                    cell.setTooltip(tooltip);
                }
                return cell;
            });
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        Tooltip t = new Tooltip("Dodaj");
        ar_add.setTooltip(t);
        t = new Tooltip("Edytuj");
        ar_edit.setTooltip(t);
        t  = new Tooltip("Usuń");
        ar_del.setTooltip(t);

        ArtistsFX.setOnKeyPressed(new EventHandler<KeyEvent>() {
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
                    ar_add.fire();
                }
            }
        });
    }

    Timeline time;

    public void add(ActionEvent actionEvent) throws IOException, SQLException{
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

    public void delete() throws IOException,SQLException {
        if (!ArtistsFX.getSelectionModel().getSelectedItems().isEmpty()) {
            ObservableList selected = ArtistsFX.getSelectionModel().getSelectedIndices();
            String delQuery = "DELETE FROM artysta WHERE Id_artysty = ?";
            PreparedStatement stmt = conn.prepareStatement(delQuery);
            selected.forEach(e -> {
                try {
                    stmt.setString(1, String.valueOf(ArtistsFX.getColumns().get(0).getCellObservableValue((Integer) e).getValue()));
                    stmt.addBatch();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            });
            stmt.executeBatch();
            List removal = new ArrayList(ArtistsFX.getSelectionModel().getSelectedItems());
            list.removeAll(removal);
            String refresh = "ALTER TABLE artysta AUTO_INCREMENT=1";
            conn.createStatement().executeUpdate(refresh);
        } else {
            mc.empty_row_dialog();
        }
    }

    public void edit(ActionEvent actionEvent) throws IOException, SQLException {
        if(!ArtistsFX.getSelectionModel().getSelectedItems().isEmpty()) {
            String[] arr = {String.valueOf(ArtistsFX.getSelectionModel().getSelectedItem().getId_artysty()),
                            String.valueOf(ArtistsFX.getSelectionModel().getSelectedItem().getImie()),
                            String.valueOf(ArtistsFX.getSelectionModel().getSelectedItem().getNazwisko()),
                            String.valueOf(ArtistsFX.getSelectionModel().getSelectedItem().getData_ur()),
                            String.valueOf(ArtistsFX.getSelectionModel().getSelectedItem().getMiejsce_ur()),
                            String.valueOf(ArtistsFX.getSelectionModel().getSelectedItem().getNazwa_stylu())};
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
