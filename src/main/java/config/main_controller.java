package config;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.fxml.*;

import java.io.IOException;

public class main_controller{
    @FXML
    private AnchorPane anchor_main;

    public void Artists_menu(javafx.event.ActionEvent actionEvent) throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/FXML/artysta.fxml"));
        anchor_main.getChildren().setAll(pane);
    }

    public void Country_menu(javafx.event.ActionEvent actionEvent) throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/FXML/kraj.fxml"));
        anchor_main.getChildren().setAll(pane);
    }

    public void close(ActionEvent actionEvent) {
        Platform.exit();
    }
}
