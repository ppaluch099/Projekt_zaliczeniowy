package config;

import database.style;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class style_controller implements Initializable {
    @FXML
    private TableView<style> StyleFX;
    @FXML
    private TableColumn<style,Integer> Id_stylu;
    @FXML
    private TableColumn<style,String> Nazwa_stylu;

    private ObservableList<style> list;
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
            String query = "SELECT * from style";
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
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void add(ActionEvent actionEvent) throws IOException, SQLException {
        main_controller mc = new main_controller();
        mc.add(actionEvent);
    }

    public void delete(ActionEvent actionEvent) throws IOException {
        if(StyleFX.getSelectionModel().getSelectedItem() != null) {
        try {
            int selected = StyleFX.getSelectionModel().getSelectedItem().getId_stylu();
            String delQuery = "DELETE FROM style WHERE Id_stylu = ?";
            PreparedStatement stmt = conn.prepareStatement(delQuery);
            stmt.setString(1, String.valueOf(selected));
            stmt.executeUpdate();
            int index = StyleFX.getSelectionModel().getSelectedIndex();
            list.remove(index);
            String refresh = "ALTER TABLE style AUTO_INCREMENT=1";
            conn.createStatement().executeUpdate(refresh);
        }
        catch (SQLException ex){
            ex.printStackTrace();
        }
    }
        else {
            main_controller mc = new main_controller();
            mc.empty_row_dialog();
        }
    }
}