import java.io.*;
import java.net.*;

public class TcpServer {
	public static void main(String[] args) throws Exception	{
		ServerSocket ss=new ServerSocket(8088);
		System.out.println("server is ready!");
		Socket ls=ss.accept();
		while (true){
			System.out.println("Client Port is "+ls.getPort());
			//READING DATA FROM CLIENT
			InputStream is=ls.getInputStream();
			byte data[]=new byte[50];
			is.read(data);
			String mfc=new String(data);
			//mfc: message from client
			mfc=mfc.trim();
			String mfs="Hello:"+mfc;
			//mfs: message from server
			//SENDING MSG TO CLIENT
			OutputStream os=ls.getOutputStream();
			os.write(mfs.getBytes());
		}
	}
}