package user.run;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Inet4Address;
import java.net.Socket;
import java.net.UnknownHostException;

public class LogTestSignUp {

	public LogTestSignUp() {
		Socket client = null;
		DataOutputStream dos = null;
		
		try {
			try {
				client = new Socket("localhost", 7001);
				// client = new Socket("211.63.89.144", 7001);
				dos = new DataOutputStream(client.getOutputStream());
				
				dos.writeUTF("jeongmi"); // 정미가 아니라 유저 아이디지...수정필요
				dos.writeUTF(Inet4Address.getLocalHost().getHostAddress().toString());
				dos.writeUTF("회원가입을 완료하였습니다.");
				
				
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

