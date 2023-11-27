import java.net.*;
import java.io.*;

class UDPClient{
	public static void main(String[] args) throws Exception	{
		byte[] buff=new byte[1024];
		DatagramSocket ds = new DatagramSocket(8089);
		DatagramPacket p=new DatagramPacket(buff,buff.length);		

		BufferedReader br=new BufferedReader(new InputStreamReader(
			System.in));
		System.out.print("Enter your name:");
		String msg = br.readLine();
		buff = msg.getBytes();
		ds.send(new DatagramPacket(buff,buff.length, InetAddress.getLocalHost(),8088));
		ds.receive(p);
		msg = new String( p.getData(),0,p.getLength()).trim();
		System.out.println("Msg received "+msg);

	}
}