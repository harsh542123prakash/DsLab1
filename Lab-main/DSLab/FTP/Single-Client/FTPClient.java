import java.io.*;
import java.net.Socket;

public class FTPClient {
    private static final String SERVER_IP = "localhost";
    private static final int SERVER_PORT = 2121;

    public static void main(String[] args) {
        FTPClient client = new FTPClient();
        client.startClient();
    }

    public void startClient() {
        try (Socket socket = new Socket(SERVER_IP, SERVER_PORT);
             BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in))) {

            String serverResponse;
            do {
                serverResponse = reader.readLine();
                System.out.println("Server: " + serverResponse);

                // Send user command to server
                System.out.print("Command: ");
                String userCommand = consoleReader.readLine();
                writer.println(userCommand);

            } while (!serverResponse.startsWith("221"));  // Continue until QUIT command is received

            System.out.println("FTP Client disconnected");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
