import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FTPServer {
    private static final int PORT = 2121;
    private ExecutorService executorService;

    public static void main(String[] args) {
        FTPServer server = new FTPServer();
        server.startServer();
    }

    public void startServer() {
        executorService = Executors.newFixedThreadPool(10);

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("FTP Server is listening on port " + PORT);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("FTP Client connected");

                FTPClientHandler clientHandler = new FTPClientHandler(clientSocket);
                executorService.execute(clientHandler);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            executorService.shutdown();
        }
    }

    private static class FTPClientHandler implements Runnable {
        private Socket clientSocket;

        public FTPClientHandler(Socket clientSocket) {
            this.clientSocket = clientSocket;
        }

        @Override
        public void run() {
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
}
