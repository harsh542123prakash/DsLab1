import java.io.*;
import java.net.*;

public class MatrixMultiplicationServer {
    public static void main(String[] args) throws Exception {
        ServerSocket ss = new ServerSocket(3000);
        System.out.println("Server ready");
        Socket soc = ss.accept();
        DataInputStream dis = new DataInputStream(soc.getInputStream());
        PrintWriter pw = new PrintWriter(soc.getOutputStream(), true);
        DataInputStream kb = new DataInputStream(System.in);

        while (true) {
            int[][] matrixA = receiveMatrix(dis);
            int[][] matrixB = receiveMatrix(dis);

            if (matrixA != null && matrixB != null) {
                int[][] resultMatrix = multiplyMatrices(matrixA, matrixB);
                sendMatrix(resultMatrix, pw);
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

    private static int[][] multiplyMatrices(int[][] matrixA, int[][] matrixB) {
        int rowsA = matrixA.length;
        int colsA = matrixA[0].length;
        int colsB = matrixB[0].length;

        int[][] resultMatrix = new int[rowsA][colsB];

        for (int i = 0; i < rowsA; i++) {
            for (int j = 0; j < colsB; j++) {
                for (int k = 0; k < colsA; k++) {
                    resultMatrix[i][j] += matrixA[i][k] * matrixB[k][j];
                }
            }
        }

        return resultMatrix;
    }
}
