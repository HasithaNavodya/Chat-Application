package controller;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class ClientFormController {

   LoginFormController loginFormController = new LoginFormController();

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private VBox txtVBox;

    @FXML
    private TextField txtSendMessage;

    @FXML
    private Button btnSend;

    @FXML
    private Button btnImage;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private VBox vBoxEmoji;

    @FXML
    private Label lblUser;


    Socket socket;
    DataInputStream dataInputStream;
    DataOutputStream dataOutputStream;

    @FXML
    void btnSendOnAction(ActionEvent event) throws IOException {
        String message = txtSendMessage.getText();
        dataOutputStream.writeUTF(loginFormController.getName()+" : "+message);
        dataOutputStream.flush();
        addToVBox(message,"you");
        txtSendMessage.clear();
    }

    @FXML
    void initialize() {
        lblUser.setText(loginFormController.getName());

        scrollPane.setVisible(false);
        vBoxEmoji.setVisible(false);
        new Thread(()->{
            try {
                socket = new Socket("localhost", 8888);
                //socket = new Socket("192.168.180.199", 5003);
                dataOutputStream = new DataOutputStream(socket.getOutputStream());
                dataInputStream = new DataInputStream(socket.getInputStream());
                Thread readerThread = new Thread(() -> {
                    try {
                        while (true) {
                            String message = dataInputStream.readUTF();
                            addToVBox(message,"");
                        }
                    } catch (IOException e) {
                        addToVBox("Error reading from server: " + e.getMessage(),"");
                    }
                });
                readerThread.start();
            } catch (IOException e) {
                addToVBox("Error connecting to server: " + e.getMessage(),"");
            }
        }).start();
    }

    public void addToVBox(String message,String senderShow) {
        Platform.runLater(()->{

            //  imoVBox.getChildren().add(label);
            if(message.startsWith("C:")){
                linkHandle(message,"");
            }else{
                if (senderShow.equals("you")) {
                    Label label = new Label(message);
                    //txtVBox.getChildren().add(label);
                    label.setStyle("-fx-background-color: #94F7F8;-fx-background-radius: 5;-fx-text-fill:black ");
                    label.setPadding(new Insets(5, 15, 5, 15));
                    HBox hBox = new HBox();
                    hBox.setAlignment(Pos.BASELINE_RIGHT);
                    hBox.getChildren().add(label);
                    txtVBox.getChildren().add(hBox);
                }else{
                    Label label = new Label(message);
                    label.setStyle("-fx-background-color: white;-fx-background-radius: 5;-fx-text-fill:black ");
                    label.setPadding(new Insets(5, 15, 5, 15));
                    txtVBox.getChildren().add(label);
                }
            }
        });
    }

    public void linkHandle(String path,String senderShow) {
        Image image = new Image("file:"+path);
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(200);
        imageView.setPreserveRatio(true);
        Platform.runLater(()->{
            if (senderShow.equals("you")) {
                //Label label = new Label("Ýou : ");
                //txtVBox.getChildren().add(label);
                //txtVBox.getChildren().add(imageView);
                HBox hBox = new HBox();
                hBox.setAlignment(Pos.BASELINE_RIGHT);
                hBox.getChildren().add(imageView);
                txtVBox.getChildren().add(hBox);
            }else{
                HBox hBox = new HBox();
                hBox.setAlignment(Pos.BASELINE_LEFT);
                hBox.getChildren().add(imageView);
                txtVBox.getChildren().add(hBox);
            }
        });

    }

    @FXML
    void imageOnAction(ActionEvent event) throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open File");
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", ".jpg", ".png", "*.gif")
        );
        Stage stage = (Stage) btnImage.getScene().getWindow();
        File selectedFile = fileChooser.showOpenDialog(stage);
        if(selectedFile!=null ){
            String path = selectedFile.getAbsolutePath();
            System.out.println(path);
            //dataOutputStream.writeUTF("image");
            dataOutputStream.writeUTF(loginFormController.getName()+" : ");
            // System.out.println(ClientDTO.getName());

            //dataOutputStream.writeUTF(ClientDTO.getName()+" : ");
            dataOutputStream.writeUTF(path);
            dataOutputStream.flush();
            linkHandle(path,"you");
        }
    }

    @FXML
    void emojiBtnOnAction(ActionEvent event) {
        scrollPane.setVisible(true);
        vBoxEmoji.setVisible(true);
        Platform.runLater(()->{
            Emojis();
        });
    }

    @FXML
    void emojiMouseEnter(MouseEvent event) {
        scrollPane.setVisible(true);
        vBoxEmoji.setVisible(true);

    }

    @FXML
    void emojiMouseExit(MouseEvent event) {
        scrollPane.setVisible(false);
        vBoxEmoji.setVisible(false);
    }

    private void Emojis() {
        vBoxEmoji.getChildren().clear();
        String[] emojis = {
                "\uD83C\uDF30", "\uD83C\uDF31", "\uD83C\uDF32", "\uD83C\uDF33", "\uD83C\uDF34",
                "\uD83C\uDF35", "\uD83C\uDF36", "\uD83C\uDF37", "\uD83C\uDF38", "\uD83C\uDF39",
                "\uD83C\uDF3A", "\uD83C\uDF3B", "\uD83C\uDF3C", "\uD83C\uDF3D", "\uD83C\uDF3E",
                "\uD83C\uDF3F", "\uD83C\uDF40", "\uD83C\uDF41", "\uD83C\uDF42", "\uD83C\uDF43",
                "\uD83C\uDF44", "\uD83C\uDF45", "\uD83C\uDF46", "\uD83C\uDF47", "\uD83C\uDF48",
                "\uD83C\uDF49", "\uD83C\uDF4A", "\uD83C\uDF4B", "\uD83C\uDF4C", "\uD83C\uDF4D",
                "\uD83C\uDF4E", "\uD83C\uDF4F", "\uD83C\uDF50", "\uD83C\uDF51", "\uD83C\uDF52",
                "\uD83C\uDF53", "\uD83C\uDF54", "\uD83C\uDF55", "\uD83C\uDF56", "\uD83C\uDF57",
                "\uD83C\uDF58", "\uD83C\uDF59", "\uD83C\uDF5A", "\uD83C\uDF5B", "\uD83C\uDF5C",
                "\uD83C\uDF5D", "\uD83C\uDF5E", "\uD83C\uDF5F", "\uD83C\uDF60", "\uD83C\uDF61",
                "\uD83C\uDF62", "\uD83C\uDF63", "\uD83C\uDF64", "\uD83C\uDF65", "\uD83C\uDF66",
                "\uD83C\uDF67", "\uD83C\uDF68", "\uD83C\uDF69", "\uD83C\uDF6A", "\uD83C\uDF6B",
                "\uD83C\uDF6C", "\uD83C\uDF6D", "\uD83C\uDF6E", "\uD83C\uDF6F", "\uD83C\uDF70",
                "\uD83C\uDF71", "\uD83C\uDF72", "\uD83C\uDF73", "\uD83C\uDF74", "\uD83C\uDF75",
                "\uD83C\uDF76", "\uD83C\uDF77", "\uD83C\uDF78", "\uD83C\uDF79", "\uD83C\uDF7A",
                "\uD83C\uDF7B", "\uD83C\uDF7C", "\uD83C\uDF7D", "\uD83C\uDF7E", "\uD83C\uDF7F",
                "\uD83D\uDE00", "\uD83D\uDE1A", "\uD83D\uDE0A", "\uD83D\uDE42", "\uD83D\uDE05",
                "\uD83D\uDE09", "\uD83D\uDE06", "\uD83D\uDE07", "\uD83D\uDE0D", "\uD83D\uDE18",
                "\uD83D\uDE0B", "\uD83D\uDE17", "\uD83D\uDE33", "\uD83D\uDE36", "\uD83D\uDE0F",
                "\uD83D\uDE14", "\uD83D\uDE10", "\uD83D\uDE31", "\uD83C\uDF7E", "\uD83D\uDE2A"
        };

        for (String emoji : emojis) {
            Label emojiLabel = new Label();
            emojiLabel.setText(emoji);
            emojiLabel.setStyle("-fx-font-size: 30");
            emojiLabel.setOnMouseClicked(event -> {

                String unicode = emoji;
                txtSendMessage.appendText(unicode);
                //sendTxtAreaClient.appendText(emoji);
                //sendTxtAreaClient.requestFocus();
                //sendTxtAreaClient.positionCaret(sendTxtAreaClient.getText().length());
            });
            vBoxEmoji.getChildren().add(emojiLabel);
        }
    }








}
