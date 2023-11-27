import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;


public class ServerApp implements Runnable{

	/**
	 * @param args
	 */
	public static Socket s=null;
	public static int i=1;
	public static String clientName = "";
	public static void main(String[] args) throws Exception{
		// TODO Auto-generated method stub
		ServerSocket ss = new ServerSocket(8089);
		ServerApp sa = new ServerApp();
		Thread t;
		try{
			while(true){
				System.out.println("Waiting for client "+i);
				s = ss.accept();
				i++;
				t = new Thread(sa);
				t.start();
	
			}
		}catch (Exception e) {
			// TODO: handle exception
		}
		finally{
			ss.close();
		}
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub

		try
		{
			InputStream is = s.getInputStream();
			byte[] b = new byte[1024];
			is.read(b);
			clientName="";
			clientName = new String(b).trim();	
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		new ChatGUI(s,clientName);
	}
}
