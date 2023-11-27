import java.io.*;
import java.net.*;

public class MatrixMultiplicationClient {
    public static void main(String[] args) throws Exception {
        Socket soc = new Socket("localhost", 3000);
        DataInputStream dis = new DataInputStream(soc.getInputStream());
        PrintWriter pw = new PrintWriter(soc.getOutputStream(), true);

        while (true) {
            int[][] matrixA = readMatrixFromUser();
            int[][] matrixB = readMatrixFromUser();

            sendMatrix(matrixA, pw);
            sendMatrix(matrixB, pw);

            int[][] resultMatrix = receiveMatrix(dis);
            displayMatrix(resultMatrix);
        }
    }

    private static int[][] readMatrixFromUser() throws IOException {
        BufferedReader kb = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("Enter the number of rows:");
        int rows = Integer.parseInt(kb.readLine());

        System.out.println("Enter the number of columns:");
        int cols = Integer.parseInt(kb.readLine());

        int[][] matrix = new int[rows][cols];

        System.out.println("Enter the matrix elements:");

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                matrix[i][j] = Integer.parseInt(kb.readLine());
            }
        }

        return matrix;
    }

    private static void sendMatrix(int[][] matrix, PrintWriter pw) {
        int rows = matrix.length;
        int cols = matrix[0].length;

        pw.println(rows);
        pw.println(cols);

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                pw.println(matrix[i][j]);
            }
        }
    }

    private static int[][] receiveMatrix(DataInputStream dis) throws IOException {
        int rows = Integer.parseInt(dis.readLine());
        int cols = Integer.parseInt(dis.readLine());

        int[][] matrix = new int[rows][cols];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                matrix[i][j] = Integer.parseInt(dis.readLine());
            }
        }

        return matrix;
    }

    private static void displayMatrix(int[][] matrix) {
        System.out.println("Resultant Matrix:");

        for (int[] row : matrix) {
            for (int num : row) {
                System.out.print(num + " ");
            }
            System.out.println();
        }
    }
}
