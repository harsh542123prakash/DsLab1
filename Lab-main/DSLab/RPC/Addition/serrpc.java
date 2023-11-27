import java.io.*;
import java.net.*;
class serrpc {
    public static void main(String[] args) throws Exception {
        ServerSocket ss = new ServerSocket(3000);
        System.out.println("Server ready");
        Socket soc = ss.accept();
        DataInputStream dis = new DataInputStream(soc.getInputStream());
        PrintWriter pw = new PrintWriter(soc.getOutputStream(), true);
        DataInputStream kb = new DataInputStream(System.in);
        String receiveMessage, sendMessage, op;
        int a, b, c;

        while (true) {
            op = dis.readLine();
            if (op != null)
                System.out.println("Operation : " + op);
            a = Integer.parseInt(dis.readLine());
            System.out.println("Parameter 1 : " + a);
            b = Integer.parseInt(dis.readLine());
            System.out.println("Parameter 2 : " + b);
            if (op.compareTo("add") == 0) {
                c = a + b;
                System.out.println("Addition = " + c);
                pw.println("Addition = " + c);
            }
            if (op.compareTo("sub") == 0) {
                c = a - b;
                System.out.println("Substraction = " + c);
                pw.println("Substraction = " + c);
            }
            if (op.compareTo("mul") == 0) {
                c = a * b;
                System.out.println("Multiplication = " + c);
                pw.println("Multiplication = " + c);
            }

            if (op.compareTo("div") == 0) {
                c = a / b;
                System.out.println("Division = " + c);
                pw.println("Division = " + c);
            }
            System.out.flush();
        }
    }
}