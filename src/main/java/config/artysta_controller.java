package config;

import database.artysta;
import database.style;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
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

    private ObservableList<artysta> list;
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
            String query = "SELECT * FROM artysta INNER JOIN style ON artysta.Id_stylu=style.Id_stylu";
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
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void add(ActionEvent actionEvent) throws IOException, SQLException{
        main_controller mc = new main_controller();
        mc.add(actionEvent);
    }

    public void delete(ActionEvent actionEvent) throws IOException {
        if (ArtistsFX.getSelectionModel().getSelectedItem() != null) {
            try {
                int selected = ArtistsFX.getSelectionModel().getSelectedItem().getId_artysty();
                String delQuery = "DELETE FROM artysta WHERE Id_artysty = ?";
                PreparedStatement stmt = conn.prepareStatement(delQuery);
                stmt.setString(1, String.valueOf(selected));
                stmt.executeUpdate();
                int index = ArtistsFX.getSelectionModel().getSelectedIndex();
                list.remove(index);
                String refresh = "ALTER TABLE artysta AUTO_INCREMENT=1";
                conn.createStatement().executeUpdate(refresh);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } else {
            main_controller mc = new main_controller();
            mc.empty_row_dialog();
        }
    }
}
