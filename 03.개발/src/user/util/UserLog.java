package user.util;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Inet4Address;
import java.net.Socket;
import java.net.UnknownHostException;

public class UserLog {
	/**
	 * 로그서버에 메시지를 보내는 메소드
	 * @param msg
	 */
	public void sendLog(String id,String msg) {
		Socket client = null;
		DataOutputStream dos = null;
		
		try {
			try {
				client = new Socket("211.63.89.144", 7001);
				dos = new DataOutputStream(client.getOutputStream());
				
				dos.writeUTF(id);
				dos.writeUTF(Inet4Address.getLocalHost().getHostAddress().toString());
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
