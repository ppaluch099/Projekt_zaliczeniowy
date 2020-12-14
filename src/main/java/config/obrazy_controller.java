package config;

import database.obrazy;
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

public class obrazy_controller implements Initializable {
    @FXML
    private TableView<obrazy> PaintingsFX;
    @FXML
    private TableColumn<obrazy,Integer> Id_obrazu;
    @FXML
    private TableColumn<obrazy,Integer> Rok;
    @FXML
    private TableColumn<obrazy,String> Tytul;
    @FXML
    private TableColumn<obrazy,String> Opis;
    @FXML
    private TableColumn<obrazy,Integer> Id_artysty;

    private ObservableList<obrazy> list;
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
            String query = "SELECT * from obrazy";
            conn = dbConnect.getConnection();
            ResultSet set = conn.createStatement().executeQuery(query);

            while (set.next()) {
                obrazy obr = new obrazy();
                obr.setId_obrazu(set.getInt("Id_obrazu"));
                obr.setRok(set.getInt("Rok"));
                obr.setTytul(set.getString("Tytul"));
                obr.setOpis(set.getString("Opis"));
                obr.setId_artysty(set.getInt("Id_artysty"));
                list.add(obr);
            }

            Id_obrazu.setCellValueFactory(new PropertyValueFactory<>("Id_obrazu"));
            Rok.setCellValueFactory(new PropertyValueFactory<>("Rok"));
            Tytul.setCellValueFactory(new PropertyValueFactory<>("Tytul"));
            Opis.setCellValueFactory(new PropertyValueFactory<>("Opis"));
            Id_artysty.setCellValueFactory(new PropertyValueFactory<>("Id_artysty"));

            PaintingsFX.setItems(list);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
