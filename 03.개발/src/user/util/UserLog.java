package user.util;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class UserLog {
	/**
	 * �α׼����� �޽����� ������ �޼ҵ�
	 * @param msg
	 */
	public void sendLog(String id,String msg) {
		Socket client = null;
		DataOutputStream dos = null;
		
		try {
			try {
				client = new Socket("localhost", 7001);
				dos = new DataOutputStream(client.getOutputStream());
				
				dos.writeUTF(id);
				dos.writeUTF(client.getInetAddress().getHostAddress().toString());
				dos.writeUTF(msg);
				
			} finally {
				if(dos != null) { dos.close(); }
				if(client != null) { client.close(); }
			}
			
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
