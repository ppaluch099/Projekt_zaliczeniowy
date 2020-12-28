package config;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
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
    private Pane view;
    @FXML
    public HBox main_hbox;

    public void Artists_menu(javafx.event.ActionEvent actionEvent) throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/FXML/artysta.fxml"));
        view.getChildren().setAll(pane);
    }

    public void Country_menu(javafx.event.ActionEvent actionEvent) throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/FXML/kraj.fxml"));
        view.getChildren().setAll(pane);
    }

    public void Address_menu() throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/FXML/adres.fxml"));
        view.getChildren().setAll(pane);
    }

    public void Paintings_menu(javafx.event.ActionEvent actionEvent) throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/FXML/obrazy.fxml"));
        view.getChildren().setAll(pane);
    }

    public void Style_menu(javafx.event.ActionEvent actionEvent) throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/FXML/style.fxml"));
        view.getChildren().setAll(pane);
    }

    public void Exhibitions_menu(javafx.event.ActionEvent actionEvent) throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/FXML/wystawa.fxml"));
        view.getChildren().setAll(pane);
    }

    public void add(ActionEvent actionEvent) throws IOException, SQLException {
        FXMLLoader load = new FXMLLoader(this.getClass().getResource("/FXML/add.fxml"));
        AnchorPane root2 = load.load();
        Stage stage = new Stage();
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(prime);
        stage.setScene(new Scene(root2));
        stage.show();
        String value = actionEvent.getSource().toString();
        String x = value.substring(12,value.indexOf(','));
        switch (x)
        {
            case "artysta_menuitem" : stage.setTitle("Dodaj artystę"); add_controller.getAd().init_artysta();break;
            case "style_menuitem" : stage.setTitle("Dodaj styl"); add_controller.getAd().init_style();break;
            case "adres_menuitem" : stage.setTitle("Dodaj adres wystawy"); add_controller.getAd().init_adres();break;
            case "kraj_menuitem" : stage.setTitle("Dodaj kraj"); add_controller.getAd().init_kraj();break;
            case "wystawa_menuitem" : stage.setTitle("Dodaj wystawę"); add_controller.getAd().init_wystawa();break;
            case "obrazy_menuitem" : stage.setTitle("Dodaj obraz"); add_controller.getAd().init_obrazy();break;

        }
    }

    public void edit(ActionEvent actionEvent, String[] adres) throws IOException, SQLException {
        FXMLLoader load = new FXMLLoader(this.getClass().getResource("/FXML/edit.fxml"));
        AnchorPane root2 = load.load();
        Stage stage = new Stage();
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(prime);
        stage.setScene(new Scene(root2));
        stage.show();
        String value = actionEvent.getSource().toString();
        String x = value.substring(12,value.indexOf(','));
        stage.setTitle("Edytuj adres");
        edit_controller.getEd().init_adres(adres);
    }

    public void empty_row_dialog(){
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Błąd");
        dialog.setContentText("Wybrano pusty wiersz");
        ButtonType type = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().add(type);
        dialog.showAndWait();
    }

    public void hid(ActionEvent actionEvent) {
//        main_hbox.managedProperty().bind(main_hbox.visibleProperty());
//        if(main_hbox)
    }

    public void close(ActionEvent actionEvent) {
        Platform.exit();
    }
}

