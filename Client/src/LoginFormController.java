import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class LoginFormController {

    @FXML
    private static String name;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField txtName;

    @FXML
    private Button btn;

    @FXML
    private AnchorPane adminAncPane;

    @FXML
    void btnOnAction(ActionEvent event) throws IOException {

        if (txtName.getText().isEmpty()) {

        }else{
            name=txtName.getText();
            Stage stage = new Stage();
            Parent root = null;
            stage.setTitle("Client");

            root = FXMLLoader.load(getClass().getResource("/client_form.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
            adminAncPane.getScene().getWindow().hide();
        }
    }

    public String getName(){
        return name;
    }

    @FXML
    void initialize() {


    }
}
