import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class ClientHandler implements Runnable {

    private static ArrayList<ClientHandler> clientArray;
    private Socket socket;
    private BufferedWriter bufferedWriter;
    private BufferedReader bufferedReader;
    private String username;

    public ClientHandler(Socket clientSocket) {

        try {
            this.socket = clientSocket;
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.username = bufferedReader.readLine();
            clientArray.add(this);
            broadcastMessage("SERVER: " + username + " has joined the chat!");
        } catch (IOException e) {
            closeLeaks(socket, bufferedReader, bufferedWriter);
        }

    }

    private void closeLeaks(Socket socket, BufferedReader bufferedReader, BufferedWriter bufferedWriter) {
        socket.close();
        bufferedReader.close();
        bufferedWriter.close();
    }

    private void broadcastMessage(String msg) {
        for (ClientHandler clients : clientArray) {
            try {
                if (!clients.getUsername().equals(this.username)){

                }
            } catch (IOException) {

            }
        }
    }

    @Override
    public void run(){
        String msgFromClient;

        while (socket.isConnected()) {
            try {
                msgFromClient = bufferedReader.readLine();
                broadcastMessage(msgFromClient);
            } catch (IOException e) {
                closeLeaks(socket, bufferedReader, bufferedWriter);
                break;
            }
        }
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public BufferedWriter getBufferedWriter() {
        return bufferedWriter;
    }

    public void setBufferedWriter(BufferedWriter bufferedWriter) {
        this.bufferedWriter = bufferedWriter;
    }

    public BufferedReader getBufferedReader() {
        return bufferedReader;
    }

    public void setBufferedReader(BufferedReader bufferedReader) {
        this.bufferedReader = bufferedReader;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}