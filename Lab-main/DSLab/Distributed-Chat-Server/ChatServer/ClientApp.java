import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;


public class ClientApp {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception{
		// TODO Auto-generated method stub
		
		System.out.print("Enter your name:");
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String name = br.readLine();
		Socket s = new Socket("localhost",8089);
		OutputStream os = s.getOutputStream();
		os.write(name.getBytes());
		new ChatGUI(s,"Admin");
	}

}
