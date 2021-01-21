package config;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class main_controller {

    private Stage prime;
    public void setPrime(Stage primaryStage) {
        this.prime = primaryStage;
    }

    @FXML
    public Pane view;
    @FXML
    public HBox main_hbox;

    public void Artists_menu() throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/FXML/artysta.fxml"));
        view.getChildren().setAll(pane);
    }

    public void Country_menu() throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/FXML/kraj.fxml"));
        view.getChildren().setAll(pane);
    }

    public void Address_menu() throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/FXML/adres.fxml"));
        view.getChildren().setAll(pane);
    }

    public void Paintings_menu() throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/FXML/obrazy.fxml"));
        view.getChildren().setAll(pane);
    }

    public void Style_menu() throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/FXML/style.fxml"));
        view.getChildren().setAll(pane);
    }

    public void Exhibitions_menu() throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/FXML/wystawa.fxml"));
        view.getChildren().setAll(pane);
    }

    public void add(ActionEvent actionEvent) throws IOException, SQLException {
        FXMLLoader load = new FXMLLoader(this.getClass().getResource("/FXML/add.fxml"));
        AnchorPane root2 = load.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root2));
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/CSS/Images/art2.png")));
        stage.show();
        String value = actionEvent.getSource().toString();
        String x = value.substring(10,value.indexOf(','));
        switch (x)
        {
            case "ar_add" : stage.setTitle("Dodaj artystę"); add_controller.getAd().init_artysta();break;
            case "st_add" : stage.setTitle("Dodaj styl"); add_controller.getAd().init_style();break;
            case "ad_add" : stage.setTitle("Dodaj adres wystawy"); add_controller.getAd().init_adres();break;
            case "kr_add" : stage.setTitle("Dodaj kraj"); add_controller.getAd().init_kraj();break;
            case "wy_add" : stage.setTitle("Dodaj wystawę"); add_controller.getAd().init_wystawa();break;
            case "ob_add" : stage.setTitle("Dodaj obraz"); add_controller.getAd().init_obrazy();break;
        }
    }

    public void edit(ActionEvent actionEvent, String[] arr) throws IOException, SQLException {
        FXMLLoader load = new FXMLLoader(this.getClass().getResource("/FXML/edit.fxml"));
        AnchorPane root2 = load.load();
        Stage stage = new Stage();
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/CSS/Images/art2.png")));
        stage.setScene(new Scene(root2));
        stage.show();
        String value = actionEvent.getSource().toString();
        String x = value.substring(10,value.indexOf(','));
            switch (x) {
            case "ad_edit" : stage.setTitle("Edytuj adres"); edit_controller.getEd().init_adres(arr);break;
            case "ar_edit" : stage.setTitle("Edytuj artystę"); edit_controller.getEd().init_artysta(arr);break;
            case "kr_edit" : stage.setTitle("Edytuj kraj"); edit_controller.getEd().init_kraj(arr);break;
            case "ob_edit" : stage.setTitle("Edytuj obraz"); edit_controller.getEd().init_obrazy(arr);break;
            case "st_edit" : stage.setTitle("Edytuj styl"); edit_controller.getEd().init_style(arr);break;
            case "wy_edit" : stage.setTitle("Edytuj wystawe"); edit_controller.getEd().init_wystawa(arr);break;
        }
    }

    public void empty_row_dialog(){
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Błąd");
        dialog.setContentText("Wybrano pusty wiersz");
        ButtonType type = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().add(type);
        dialog.showAndWait();
    }

    public void date_error_dialog() {
        Dialog d = new Dialog();
        d.setTitle("Błędna data");
        d.setContentText("Data zakończenia nie moze być przed datą rozpoczęcia");
        ButtonType b = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        d.getDialogPane().getButtonTypes().add(b);
        d.showAndWait();
    }

    public void nullValueDialog() {
        Dialog d = new Dialog();
        d.setTitle("Brak wartości");
        d.setContentText("Pola nie mogą być puste");
        ButtonType b = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        d.getDialogPane().getButtonTypes().add(b);
        d.showAndWait();
    }

}

