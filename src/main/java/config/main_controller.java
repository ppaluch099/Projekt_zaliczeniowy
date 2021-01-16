package config;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
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
    @FXML
    public VBox main_vbox;

    public void Artists_menu() throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/FXML/artysta.fxml"));
        main_vbox.setVisible(false);
        main_hbox.setVisible(true);
        view.getChildren().setAll(pane);
    }

    public void Country_menu() throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/FXML/kraj.fxml"));
        main_vbox.setVisible(false);
        main_hbox.setVisible(true);
        view.getChildren().setAll(pane);
    }

    public void Address_menu() throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/FXML/adres.fxml"));
        main_vbox.setVisible(false);
        main_hbox.setVisible(true);
        view.getChildren().setAll(pane);
    }

    public void Paintings_menu() throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/FXML/obrazy.fxml"));
        main_vbox.setVisible(false);
        main_hbox.setVisible(true);
        view.getChildren().setAll(pane);
    }

    public void Style_menu() throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/FXML/style.fxml"));
        main_vbox.setVisible(false);
        main_hbox.setVisible(true);
        view.getChildren().setAll(pane);
    }

    public void Exhibitions_menu() throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("/FXML/wystawa.fxml"));
        main_vbox.setVisible(false);
        main_hbox.setVisible(true);
        view.getChildren().setAll(pane);
    }

    public void add(ActionEvent actionEvent) throws IOException, SQLException {
        FXMLLoader load = new FXMLLoader(this.getClass().getResource("/FXML/add.fxml"));
        AnchorPane root2 = load.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root2));
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(prime);
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

    public void edit(ActionEvent actionEvent, String[] arr) throws IOException, SQLException {
        FXMLLoader load = new FXMLLoader(this.getClass().getResource("/FXML/edit.fxml"));
        AnchorPane root2 = load.load();
        Stage stage = new Stage();
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(prime);
        stage.setScene(new Scene(root2));
        stage.show();
        String value = actionEvent.getSource().toString();
        String x = value.substring(12,value.indexOf(','));
            switch (x) {
            case "adres_edit" : stage.setTitle("Edytuj adres"); edit_controller.getEd().init_adres(arr);break;
            case "artysta_edit" : stage.setTitle("Edytuj artystę"); edit_controller.getEd().init_artysta(arr);break;
            case "kraj_edit" : stage.setTitle("Edytuj kraj"); edit_controller.getEd().init_kraj(arr);break;
            case "obrazy_edit" : stage.setTitle("Edytuj obraz"); edit_controller.getEd().init_obrazy(arr);break;
            case "style_edit" : stage.setTitle("Edytuj styl"); edit_controller.getEd().init_style(arr);break;
            case "wystawa_edit" : stage.setTitle("Edytuj wystawe"); edit_controller.getEd().init_wystawa(arr);break;
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
}

