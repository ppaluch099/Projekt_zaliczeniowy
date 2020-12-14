package config;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class main_controller{
    @FXML
    private Pane view;

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

    public void close(ActionEvent actionEvent) {
        Platform.exit();
    }
}
