package config;

import database.adres;
import database.kraje;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

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

    private ObservableList<adres> list;
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
            String query = "SELECT * FROM adres_wystawy INNER JOIN kraje ON adres_wystawy.Id_kraju=kraje.Id_kraju";
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
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void add(ActionEvent actionEvent) throws IOException, SQLException {
        main_controller mc = new main_controller();
        mc.add(actionEvent);
    }

    public void delete(ActionEvent actionEvent) throws IOException, SQLException {
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
            main_controller mc = new main_controller();
            mc.empty_row_dialog();
        }
    }
}
