import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;

public class ServerMain extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        URL resource = ServerMain.class.getResource("server_form.fxml");
        Parent load = FXMLLoader.load(resource);
        stage.setScene(new Scene(load));
        stage.setTitle("Server");
        stage.centerOnScreen();
        stage.show();
    }
}
