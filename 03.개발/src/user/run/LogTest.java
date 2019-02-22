package user.run;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class LogTest {

	public LogTest() {
		Socket client = null;
		DataOutputStream dos = null;
		
		try {
			try {
				client = new Socket("211.63.89.144", 7001);
				dos = new DataOutputStream(client.getOutputStream());
				
				dos.writeUTF("won");
				dos.writeUTF(client.getInetAddress().toString());
				dos.writeUTF("요청내용");
				
				
			} finally {
				if (dos != null) { dos.close(); }
				if(client != null) { client.close(); }
			}
			
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		new LogTest();
	}
}
