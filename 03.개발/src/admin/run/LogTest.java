package admin.run;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class LogTest { // ������ ��û�� ������ ����

	public LogTest() {
		Socket client = null;
		DataOutputStream dos = null;
		
		try {
			try {
				client = new Socket("localhost", 7001);
				dos = new DataOutputStream(client.getOutputStream());
				
				dos.writeUTF("id");
				dos.writeUTF(client.getInetAddress().toString());
				dos.writeUTF("��û����");
				
				
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
