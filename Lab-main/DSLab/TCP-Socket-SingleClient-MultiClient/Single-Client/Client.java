import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;

public class Client {
    public static void main(String[] args) {
        try {
            // Connect to the server on localhost:12345
            Socket socket = new Socket("localhost", 12345);

            // Enable SO_KEEPALIVE (TCP keep-alive)
            socket.setKeepAlive(true);

            // Enable SO_LINGER with a linger time of 5 seconds
            socket.setSoLinger(true, 5);

            // Set SO_SNDBUF to 64KB (64 * 1024 bytes)
            int sendBufferSize = 64 * 1024;
            socket.setSendBufferSize(sendBufferSize);

            // Get input and output streams
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);

            // Send data to the server
            writer.println("Hello, server!");

            // Receive response from the server
            String response = reader.readLine();
            System.out.println("Received from server: " + response);

            // Close the socket when done
            socket.close();
        } catch (SocketException e) {
            // Handle socket-related exceptions
            e.printStackTrace();
        } catch (IOException e) {
            // Handle IO-related exceptions
            e.printStackTrace();
        }
    }
}
