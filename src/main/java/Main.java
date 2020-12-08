import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Main extends Application{

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader load = new FXMLLoader(this.getClass().getResource("/FXML/artysta.fxml"));
        AnchorPane aPane = load.load();
        Scene scene = new Scene(aPane);
        stage.setScene(scene);
        stage.setTitle("title");
        stage.show();
    }
}