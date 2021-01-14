package config;

import database.artysta;
import database.obrazy;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
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
    private TableColumn<artysta,String> Imie_Nazwisko;

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
            String query = "SELECT Id_obrazu, Rok, Tytul, Opis, CONCAT(Imie, ' ', Nazwisko) as Imie_Nazwisko FROM `obrazy` INNER JOIN artysta ON obrazy.ID_artysty=artysta.ID_artysty";
            conn = dbConnect.getConnection();
            ResultSet set = conn.createStatement().executeQuery(query);

            while (set.next()) {
                obrazy obr = new obrazy();
                obr.setId_obrazu(set.getInt("Id_obrazu"));
                obr.setRok(set.getInt("Rok"));
                obr.setTytul(set.getString("Tytul"));
                obr.setOpis(set.getString("Opis"));
                obr.setImie_Nazwisko(set.getString("Imie_Nazwisko"));
                list.add(obr);
            }

            Id_obrazu.setCellValueFactory(new PropertyValueFactory<>("Id_obrazu"));
            Rok.setCellValueFactory(new PropertyValueFactory<>("Rok"));
            Tytul.setCellValueFactory(new PropertyValueFactory<>("Tytul"));
            Opis.setCellValueFactory(new PropertyValueFactory<>("Opis"));
            Imie_Nazwisko.setCellValueFactory(new PropertyValueFactory<>("Imie_Nazwisko"));

            PaintingsFX.setItems(list);
            PaintingsFX.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
            PaintingsFX.setRowFactory(tv -> {
                TableRow row = new TableRow();
                row.setOnMouseClicked(e -> {
                    if(row.isEmpty() && e.getButton() == MouseButton.PRIMARY) {
                        PaintingsFX.getSelectionModel().clearSelection();
                    }
                });
                return row;
            });
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void add(ActionEvent actionEvent) throws IOException,SQLException {
        main_controller mc = new main_controller();
        mc.add(actionEvent);
    }

    public void delete(ActionEvent actionEvent) throws IOException,SQLException {
        if (!PaintingsFX.getSelectionModel().getSelectedItems().isEmpty()) {
            ObservableList selected = PaintingsFX.getSelectionModel().getSelectedIndices();
            String delQuery = "DELETE FROM obrazy WHERE Id_obrazu = ?";
            PreparedStatement stmt = conn.prepareStatement(delQuery);
            selected.forEach(e -> {
                try {
                    stmt.setString(1,String.valueOf(PaintingsFX.getColumns().get(0).getCellObservableValue((Integer) e).getValue()));
                    stmt.addBatch();
                }
                catch (SQLException throwables){
                    throwables.printStackTrace();
                }
            });
            stmt.executeBatch();
            List removal = PaintingsFX.getSelectionModel().getSelectedItems();
            list.removeAll(removal);
            String refresh = "ALTER TABLE obrazy AUTO_INCREMENT=1";
            conn.createStatement().executeUpdate(refresh);
        }
        else {
            main_controller mc = new main_controller();
            mc.empty_row_dialog();
        }
    }

    public void edit(ActionEvent actionEvent) throws IOException, SQLException {
        main_controller mc = new main_controller();
        if(!PaintingsFX.getSelectionModel().getSelectedItems().isEmpty()) {
            String[] arr = {String.valueOf(PaintingsFX.getSelectionModel().getSelectedItem().getId_obrazu()),
                            String.valueOf(PaintingsFX.getSelectionModel().getSelectedItem().getRok()),
                            String.valueOf(PaintingsFX.getSelectionModel().getSelectedItem().getTytul()),
                            String.valueOf(PaintingsFX.getSelectionModel().getSelectedItem().getOpis()),
                            String.valueOf(PaintingsFX.getSelectionModel().getSelectedItem().getImie_Nazwisko())};
            mc.edit(actionEvent, arr);
        }
        else {
            mc.empty_row_dialog();
        }
    }
}
