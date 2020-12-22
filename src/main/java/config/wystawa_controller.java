package config;

import database.adres;
import database.wystawa;
import database.obrazy;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
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
            String query = "SELECT * FROM wystawa INNER JOIN adres_wystawy ON wystawa.Id_adresu=adres_wystawy.Id_adresu";
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
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void add(ActionEvent actionEvent) throws IOException, SQLException {
        main_controller mc = new main_controller();
        mc.add(actionEvent);
    }

    public void delete(ActionEvent actionEvent) throws IOException {
        if (ExhibitionsFX.getSelectionModel().getSelectedItem() != null) {
            try {
                int selected = ExhibitionsFX.getSelectionModel().getSelectedItem().getId_wystawy();
                String delQuery = "DELETE FROM wystawa WHERE Id_wystawy = ?";
                PreparedStatement stmt = conn.prepareStatement(delQuery);
                stmt.setString(1, String.valueOf(selected));
                stmt.executeUpdate();
                int index = ExhibitionsFX.getSelectionModel().getSelectedIndex();
                list.remove(index);
                String refresh = "ALTER TABLE wystawa AUTO_INCREMENT=1";
                conn.createStatement().executeUpdate(refresh);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        else {
            main_controller mc = new main_controller();
            mc.empty_row_dialog();
        }
    }
}