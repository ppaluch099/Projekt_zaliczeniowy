package config;

import database.adres;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class adres_controller implements Initializable {
    @FXML
    private TableView<adres> AddressFX;
    @FXML
    private TableColumn<adres,Integer> Id_adresu;
    @FXML
    private TableColumn<adres,String> Nazwa_galerii;
    @FXML
    private TableColumn<adres,String> Miasto;
    @FXML
    private TableColumn<adres, Integer> Id_kraju;

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
            String query = "SELECT * from adres_wystawy";
            conn = dbConnect.getConnection();
            ResultSet set = conn.createStatement().executeQuery(query);

            while (set.next()) {
                adres adr = new adres();
                adr.setId_adresu(set.getInt("Id_adresu"));
                adr.setNazwa_galerii(set.getString("Nazwa_galerii"));
                adr.setMiasto(set.getString("Miasto"));
                adr.setId_kraju(set.getInt("Id_kraju"));
                list.add(adr);
            }

            Id_adresu.setCellValueFactory(new PropertyValueFactory<>("Id_adresu"));
            Nazwa_galerii.setCellValueFactory(new PropertyValueFactory<>("Nazwa_galerii"));
            Miasto.setCellValueFactory(new PropertyValueFactory<>("Miasto"));
            Id_kraju.setCellValueFactory(new PropertyValueFactory<>("Id_kraju"));

            AddressFX.setItems(list);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
