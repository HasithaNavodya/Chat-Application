package controller;

import controller.ServerFormController;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientHandler extends Thread {
    private Socket clientSocket;
    private ServerFormController serverController;
    //  private PrintWriter out;
    private DataInputStream dataInputStream;
    private DataOutputStream dataOutputStream;

    public ClientHandler(Socket clientSocket, ServerFormController serverController) throws IOException {
        this.clientSocket = clientSocket;
        this.serverController = serverController;

        dataInputStream = new DataInputStream(clientSocket.getInputStream());
        dataOutputStream = new DataOutputStream(clientSocket.getOutputStream());
    }

    @Override
    public void run() {
        try {
            while (true) {
                String message = dataInputStream.readUTF();
                serverController.listeningServer( message); //Received message from client: brodcast ekedi me message eka show karanawa
                serverController.broadcastMessage(message, this);  //anith client ta yawana eka
            }
        } catch (IOException e) {
            serverController.listeningServer("Error: " + e.getMessage());
        } finally {
            try {
                clientSocket.close();
                serverController.removeClient(this);
            } catch (IOException e) {
                serverController.listeningServer("Error closing client connection: " + e.getMessage());
            }
        }
    }

    public void sendMessage(String message) throws IOException {
        // out.println(message);
        dataOutputStream.writeUTF(message);
        dataOutputStream.flush();
    }
}
