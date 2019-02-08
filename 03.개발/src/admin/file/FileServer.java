package admin.file;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class FileServer extends Thread {
	
	@Override
	public void run() {
		try {
			ServerSocket serverFile = null;
			Socket client = null;
			DataInputStream dis = null;
			String flag = "";
			
			try {
				while(true) {
					serverFile = new ServerSocket(7002); // 파일을 받는 7002포트
					client = serverFile.accept();
					dis = new DataInputStream(client.getInputStream());
					
					flag = dis.readUTF();
////////////////////////////////////////////// 0208 강사님 질문드리고 진행예정 ///////////////// ///////////
					if (flag.equals("extRequest")) {
						
					} else if (flag.equals("extSave")) {
						
					}
					
				}
			} finally {
				if (dis != null) { dis.close(); }
				if (client != null) { client.close(); }
				if (serverFile != null) { serverFile.close(); }
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void fileSend() { // 서버에 있는 이력서 파일을 보내주는 메소드

	}
	
	public void fileReceive() { // 유저로부터 이력서 파일을 받는 메소드
		
	}
}
