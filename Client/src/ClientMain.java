import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;

public class ClientMain extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        URL resource = ClientMain.class.getResource("view/loginForm.fxml");
        Parent load = FXMLLoader.load(resource);
        stage.setScene(new Scene(load));
        stage.setTitle("Client");
        stage.centerOnScreen();
        stage.show();
    }
}
