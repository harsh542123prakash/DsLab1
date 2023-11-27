import java.net.*;
import java.io.*;

class TcpClient {
	public static void main(String[] args) throws Exception	{
		System.out.println("connecting to server");
		Socket cs=new Socket("localhost",8088);
		
		BufferedReader br=new BufferedReader(new InputStreamReader( System.in));

		System.out.println("The Local Port "+cs.getLocalPort()+"\nThe Remote Port"+cs.getPort());
		System.out.println("The Local socket is "+cs);
		System.out.println("Enter your name");
		String str=br.readLine();
		//SENDING DATA TO SERVER
		OutputStream os=cs.getOutputStream();
		os.write(str.getBytes());
		//READING DATA FROM SERVER
		InputStream is=cs.getInputStream();
		byte data[]=new byte[50];
		is.read(data);
		//PRINTING MESSAGE ON CLIENT CONSLOE
		String mfs=new String(data);
		mfs=mfs.trim();
		System.out.println(mfs);
	}
}
