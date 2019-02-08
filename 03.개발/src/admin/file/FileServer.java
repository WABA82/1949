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
					serverFile = new ServerSocket(7002); // ������ �޴� 7002��Ʈ
					client = serverFile.accept();
					dis = new DataInputStream(client.getInputStream());
					
					flag = dis.readUTF();
////////////////////////////////////////////// 0208 ����� �����帮�� ���࿹�� ///////////////// ///////////
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
	
	public void fileSend() { // ������ �ִ� �̷¼� ������ �����ִ� �޼ҵ�

	}
	
	public void fileReceive() { // �����κ��� �̷¼� ������ �޴� �޼ҵ�
		
	}
}
