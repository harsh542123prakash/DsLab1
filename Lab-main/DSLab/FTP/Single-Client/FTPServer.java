import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class FTPServer {
    private static final int PORT = 2121;

    public static void main(String[] args) {
        FTPServer server = new FTPServer();
        server.startServer();
    }

    public void startServer() {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("FTP Server is listening on port " + PORT);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("FTP Client connected");

                Thread clientHandler = new Thread(() -> handleClient(clientSocket));
                clientHandler.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleClient(Socket clientSocket) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
             PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true)) {

            writer.println("220 FTP Server Ready");

            String clientCommand;
            do {
                clientCommand = reader.readLine();
                System.out.println("Received command: " + clientCommand);

                // Process FTP commands
                processCommand(clientCommand, writer);

            } while (!clientCommand.toUpperCase().equals("QUIT"));

            System.out.println("FTP Client disconnected");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void processCommand(String clientCommand, PrintWriter writer) {
        // Basic command processing (add more commands as needed)
        if (clientCommand.toUpperCase().equals("SYST")) {
            writer.println("215 UNIX Type: L8");
        } else if (clientCommand.toUpperCase().equals("PWD")) {
            writer.println("257 \"/\" is the current directory");
        } else if (clientCommand.toUpperCase().equals("QUIT")) {
            writer.println("221 Goodbye");
        } else {
            writer.println("500 Unknown command");
        }
    }
}
