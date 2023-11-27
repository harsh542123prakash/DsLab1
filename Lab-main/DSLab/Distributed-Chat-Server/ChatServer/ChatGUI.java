import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import java.net.Socket;
import java.net.SocketException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class ChatGUI extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;
	Socket s;
	JButton button;
	JTextArea ta1, ta2;
	String msg = "", title;
	JScrollPane scrollPane1, scrollPane2;
	InputStream is;
	OutputStream os;

	ChatGUI(Socket x, String str) {
		s = x;
		title = str;
		button = new JButton("SEND");
		ta1 = new JTextArea(5, 20);
		ta2 = new JTextArea(5, 20);
		ta1.setEditable(false);
		scrollPane1 = new JScrollPane(ta1);
		scrollPane2 = new JScrollPane(ta2);
		setLayout(new FlowLayout());
		add(scrollPane1);
		add(scrollPane2);
		add(button);
		button.addActionListener(this);
		setSize(300, 300);
		setVisible(true);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setTitle("Messenger " + title);
		try {
			is = s.getInputStream();
			os = s.getOutputStream();
		} catch (IOException ioe) {
		}

		try {
			chat();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@SuppressWarnings("deprecation")
	public void chat() throws Exception {
		while (true) {
			try {
				byte data[] = new byte[50];
				is.read(data);
				msg = new String(data).trim();
				ta1.append(title+": " + msg + "\n");
			} catch (SocketException se) {
				JOptionPane.showMessageDialog(this, "Disconnected from "+title);
				this.dispose();
				Thread.currentThread().stop();
			}
		}
	}

	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		msg = ta2.getText();
		try {
			os.write(msg.getBytes());
		} catch (IOException ioe) {
			// TODO Auto-generated catch block
			ioe.printStackTrace();
		}
		ta1.append("I: " + msg + "\n");
		ta2.setText("");
	}
}
