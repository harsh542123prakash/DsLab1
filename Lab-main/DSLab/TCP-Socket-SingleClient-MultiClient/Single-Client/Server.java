import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

public class Server {
    public static void main(String[] args) {
        try {
            // Create a server socket on port 12345
            ServerSocket serverSocket = new ServerSocket(12345);

            System.out.println("Server listening on port 12345...");

            // Accept client connection
            Socket clientSocket = serverSocket.accept();
            System.out.println("Client connected: " + clientSocket.getInetAddress().getHostAddress());

            // Enable SO_KEEPALIVE (TCP keep-alive)
            clientSocket.setKeepAlive(true);

            // Enable SO_LINGER with a linger time of 5 seconds
            clientSocket.setSoLinger(true, 5);

            // Set SO_SNDBUF to 64KB (64 * 1024 bytes)
            int sendBufferSize = 64 * 1024;
            clientSocket.setSendBufferSize(sendBufferSize);

            // Get input and output streams
            BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true);

            // Read and send data
            String message;
            while ((message = reader.readLine()) != null) {
                System.out.println("Received from client: " + message);

                // Send response back to the client
                writer.println("Server response: " + message);
            }

            // Close the socket and server socket when done
            clientSocket.close();
            serverSocket.close();
        } catch (SocketException e) {
            // Handle socket-related exceptions
            e.printStackTrace();
        } catch (IOException e) {
            // Handle IO-related exceptions
            e.printStackTrace();
        }
    }
}
