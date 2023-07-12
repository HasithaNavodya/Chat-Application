package controller;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

public class ServerFormController {

    public Button btnImage;
    public ScrollPane scrollPane;
    public VBox vBoxEmoji;
    @FXML
    private TextField txtSendMessage;
    
    @FXML
    private List<ClientHandler> clients = new ArrayList<>();

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private VBox txtVBox;

    @FXML
    private Button btnSend;

    @FXML
    void btnOnAction(ActionEvent event) {

    }

    @FXML
    void initialize() {

        new Thread(() -> {
            try {
                ServerSocket serverSocket = new ServerSocket(8888); //server socket
                listeningServer("Server started. Listening for clients...");
                while (true) {
                    Socket clientSocket = serverSocket.accept();
                    listeningServer("Client connected: " + clientSocket.getInetAddress().getHostAddress());
                    // Create a new client handler thread
                    ClientHandler clientHandler = new ClientHandler(clientSocket, this);
                    clients.add(clientHandler);
                    clientHandler.start();
                }
            } catch (IOException e) {
                listeningServer("Error: " + e.getMessage());
            }
        }).start();

    }

    public void listeningServer(String message) {
        Platform.runLater(()->{

            if(message.startsWith("C:")){
                Image image = new Image("file:"+message);
                ImageView imageView = new ImageView(image);
                imageView.setFitWidth(200);
                imageView.setPreserveRatio(true);
                Platform.runLater(()->{
                    //txtVBox.getChildren().add(label);
                    txtVBox.getChildren().add(imageView);


                });
            }else {
                Label label = new Label(message);
                txtVBox.getChildren().add(label);
            }
        });
    }

    public void broadcastMessage(String message, ClientHandler sender) throws IOException {
        for (ClientHandler client : clients) {
            if (client != sender) {
                client.sendMessage(message);
            }
        }
    }

    public void removeClient(ClientHandler client) {
        clients.remove(client);
    }

    public void btnSendOnAction(ActionEvent event) {
    }

    public void imageOnAction(ActionEvent event) {
    }

    public void emojiMouseEnter(MouseEvent mouseEvent) {
    }

    public void emojiMouseExit(MouseEvent mouseEvent) {
    }

    public void emojiBtnOnAction(ActionEvent event) {
    }
}
