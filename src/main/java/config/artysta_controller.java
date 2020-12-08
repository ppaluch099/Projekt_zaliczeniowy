package config;

import database.artysta;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
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
    private TableColumn<artysta,Integer> Id_stylu;

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
            String query = "SELECT * from artysta";
            conn = dbConnect.getConnection();
            ResultSet set = conn.createStatement().executeQuery(query);

            while (set.next()) {
                artysta art = new artysta();
                art.setId_artysty(set.getInt("Id_artysty"));
                art.setImie(set.getString("imie"));
                art.setNazwisko(set.getString("nazwisko"));
                art.setData_ur(set.getDate("data_ur"));
                art.setMiejsce_ur(set.getString("miejsce_ur"));
                art.setId_stylu(set.getInt("Id_stylu"));
                list.add(art);
            }

            Id_artysty.setCellValueFactory(new PropertyValueFactory<>("Id_artysty"));
            Imie.setCellValueFactory(new PropertyValueFactory<>("imie"));
            Nazwisko.setCellValueFactory(new PropertyValueFactory<>("nazwisko"));
            Data_ur.setCellValueFactory(new PropertyValueFactory<>("data_ur"));
            Miejsce_ur.setCellValueFactory(new PropertyValueFactory<>("miejsce_ur"));
            Id_stylu.setCellValueFactory(new PropertyValueFactory<>("Id_stylu"));

            ArtistsFX.setItems(list);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
