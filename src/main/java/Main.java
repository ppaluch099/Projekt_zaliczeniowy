import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Main extends Application{

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader load = new FXMLLoader(this.getClass().getResource("/FXML/main_fxml.fxml"));
        AnchorPane aPane = load.load();
        config.main_controller mc = load.getController();
        mc.setPrime(stage);
        Scene scene = new Scene(aPane);
        stage.setScene(scene);
        stage.setTitle("Zarzadzanie galeriami");
        mc.main_hbox.requestFocus();
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/CSS/Images/art2.png")));
        stage.show();
    }
}