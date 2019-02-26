package user.run;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Inet4Address;
import java.net.Socket;
import java.net.UnknownHostException;

public class LogTestChangePass {

	public LogTestChangePass() {
		Socket client = null;
		DataOutputStream dos = null;
		
		try {
			try {
				client = new Socket("211.63.89.144", 7001);
				dos = new DataOutputStream(client.getOutputStream());
				
				dos.writeUTF("hyewon");
				dos.writeUTF(Inet4Address.getLocalHost().getHostAddress().toString());
				dos.writeUTF("회원 비밀번호를 수정하였습니다.");
				
				
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
		new LogTestChangePass();
	}
}

