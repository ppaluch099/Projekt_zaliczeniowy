package config;

import database.wystawa;
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
import java.sql.Date;
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
    private TableColumn<wystawa,Integer> Id_adresu;

    private ObservableList<wystawa> list;
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
            String query = "SELECT * from wystawa";
            conn = dbConnect.getConnection();
            ResultSet set = conn.createStatement().executeQuery(query);

            while (set.next()) {
                wystawa wys = new wystawa();
                wys.setId_wystawy(set.getInt("Id_wystawy"));
                wys.setNazwa(set.getString("Nazwa"));
                wys.setData_rozpoczecia(set.getDate("Data_rozpoczecia"));
                wys.setData_zakonczenia(set.getDate("Data_zakonczenia"));
                wys.setId_adresu(set.getInt("Id_adresu"));

                list.add(wys);
            }

            Id_wystawy.setCellValueFactory(new PropertyValueFactory<>("Id_wystawy"));
            Nazwa.setCellValueFactory(new PropertyValueFactory<>("Nazwa"));
            Data_rozpoczecia.setCellValueFactory(new PropertyValueFactory<>("Data_rozpoczecia"));
            Data_zakonczenia.setCellValueFactory(new PropertyValueFactory<>("Data_zakonczenia"));
            Id_adresu.setCellValueFactory(new PropertyValueFactory<>("Id_adresu"));

            ExhibitionsFX.setItems(list);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}