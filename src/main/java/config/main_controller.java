package config;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.*;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class main_controller {

    private Stage prime;
    public void setPrime(Stage primaryStage) {
        this.prime = primaryStage;
    }

    @FXML
    private Pane view;
    @FXML
    public MenuItem artysta_menuitem;
    @FXML
    public MenuItem style_menuitem;

    public void Artists_menu(javafx.event.ActionEvent actionEvent) throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/FXML/artysta.fxml"));
        view.getChildren().setAll(pane);
    }

    public void Country_menu(javafx.event.ActionEvent actionEvent) throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/FXML/kraj.fxml"));
        view.getChildren().setAll(pane);
    }

    public void Address_menu(javafx.event.ActionEvent actionEvent) throws IOException {
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

    public void add(ActionEvent actionEvent) throws IOException {
        FXMLLoader load = new FXMLLoader(this.getClass().getResource("/FXML/add.fxml"));
        AnchorPane root2 = load.load();
        Stage stage = new Stage();
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(prime);
        stage.setScene(new Scene(root2));
        stage.setTitle("Dodaj");
        stage.show();
        String value = actionEvent.getSource().toString();
        String x = value.substring(12,value.indexOf(','));
        switch (x)
        {
            case "artysta_menuitem" : add_controller.getAd().init_artysta();break;
            case "style_menuitem" : add_controller.getAd().init_style();break;
            case "adres_menuitem" : add_controller.getAd().init_adres();break;
            case "kraj_menuitem" : add_controller.getAd().init_kraj();break;
            case "wystawa_menuitem" : add_controller.getAd().init_wystawa();break;
            case "obrazy_menuitem" : add_controller.getAd().init_obrazy();break;

        }
    }

    public void close(ActionEvent actionEvent) {
        Platform.exit();
    }
}

