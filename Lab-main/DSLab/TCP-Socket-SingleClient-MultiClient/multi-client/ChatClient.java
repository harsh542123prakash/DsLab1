import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ChatClient {
    private static final String SERVER_IP = "localhost";
    private static final int SERVER_PORT = 8080;

    public static void main(String[] args) {
        ChatClient client = new ChatClient();
        client.startClient();
    }

    public void startClient() {
        try (Socket socket = new Socket(SERVER_IP, SERVER_PORT);
             BufferedReader serverReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));
             PrintWriter writer = new PrintWriter(socket.getOutputStream(), true)) {

            System.out.println("Connected to the server. Type your messages:");

            // Read messages from the server and display them
            Thread serverListener = new Thread(() -> {
                String serverMessage;
                try {
                    while ((serverMessage = serverReader.readLine()) != null) {
                        System.out.println(serverMessage);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            serverListener.start();

            // Read messages from the console and send them to the server
            String clientMessage;
            while ((clientMessage = consoleReader.readLine()) != null) {
                writer.println(clientMessage);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
