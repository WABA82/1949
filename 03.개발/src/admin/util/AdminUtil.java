package admin.util;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class AdminUtil {
	public void sendLog(String msg) {
		Socket client = null;
		DataOutputStream dos = null;
		
		try {
			try {
				client = new Socket("localhost", 7001);
				dos = new DataOutputStream(client.getOutputStream());
				
				dos.writeUTF("ADMIN");
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
	
	public void deleteFile(String fileName, String flag, Socket client, 
			DataOutputStream dos, DataInputStream dis) 
					throws UnknownHostException, IOException {
		client = new Socket("localhost", 7002);
		
		dos = new DataOutputStream(client.getOutputStream());
		dis = new DataInputStream(client.getInputStream());
		
		if (flag.equals("ee")) {
			dos.writeUTF("eeImg_delete"); 
		} else if (flag.equals("ext")) {
			dos.writeUTF("ee_ext_delete");
		} else if (flag.equals("co")) {
			dos.writeUTF("coImg_delete"); 
		}
		dos.flush();
		
		dos.writeUTF(fileName);  // 기존 이미지명 전달
		dos.flush();
		
		dis.readUTF(); // 응답 후 연결 종료
		
		closeStreams(client, dos, dis, null, null, null, null);
	}
	
	public void addNewFile(File newFile,String flag, Socket client, 
			DataOutputStream dos, DataInputStream dis, 
			FileInputStream fis) 
					throws IOException {
		client = new Socket("localhost", 7002);
		
		dos = new DataOutputStream(client.getOutputStream());
		dis = new DataInputStream(client.getInputStream());
		
		if (flag.equals("ee")) {
			dos.writeUTF("eeImg_register"); 
		} else if (flag.equals("ext")) {
			dos.writeUTF("ee_ext_register"); 
		} else if (flag.equals("co")) {
			dos.writeUTF("coImg_register"); 
		}
		
		dos.flush();
		
		dos.writeUTF(newFile.getName()); // 새로운 이미지명 전달
		dos.flush();
		
		fis = new FileInputStream(newFile);
		
		byte[] readData = new byte[512];
		int len = 0;
		int arrCnt = 0;
		while((len = fis.read(readData)) != -1) {
			arrCnt++;
		}
		
		fis.close();

		dos.writeInt(arrCnt); // 파일의 크기 전송
		dos.flush();

		fis = new FileInputStream(newFile);
		
		len = 0;
		while((len = fis.read(readData)) != -1) {
			dos.write(readData, 0, len);
			dos.flush();
		}
		
		dis.readUTF(); // 저장완료 응답 받은 후 연결 종료
		closeStreams(client, dos, dis, null, fis, null, null);
		
	}
	
	public void reqFile(String newFileName,String flag, Socket client, 
			DataOutputStream dos, DataInputStream dis, 
			FileOutputStream fos) 
					throws IOException {
		client = new Socket("localhost", 7002);
		
		dos = new DataOutputStream(client.getOutputStream());
		dis = new DataInputStream(client.getInputStream());
		
		if (flag.equals("co")) {
			dos.writeUTF("coImg_request");
		} else if (flag.equals("ee")) {
			dos.writeUTF("eeImg_request");
		}
		dos.flush();
		
		dos.writeUTF(newFileName);
		dos.flush();
		
		int arrCnt = dis.readInt();
		
		byte[] readData = new byte[512];
		int len = 0;
		
		if (flag.equals("co")) {
			fos = new FileOutputStream("C:/dev/1949/03.개발/src/admin/img/co/"+newFileName);
		} else if (flag.equals("ee")) {
			fos = new FileOutputStream("C:/dev/1949/03.개발/src/admin/img/ee/"+newFileName);
		}
		
		for(int i=0; i<arrCnt; i++) {
			len = dis.read(readData);
			fos.write(readData,0,len);
			fos.flush();
		}
		
		dos.writeUTF("done");
		dos.flush();
		
		closeStreams(client, dos, dis, fos, null, null, null);
	}
	
	/**
	 * FileServer와 연결을 끊는 메소드
	 * @throws IOException
	 */
	public void closeStreams(Socket client, DataOutputStream dos, 
			DataInputStream dis, FileOutputStream fos, 
			FileInputStream fis, ObjectOutputStream oos, ObjectInputStream ois) 
			throws IOException {
		if (ois != null) { ois.close(); }
		if (oos != null) { oos.close(); }
		if (fos != null) { fos.close(); }
		if (fis != null) { fis.close(); }
		if (dos != null) { dos.close(); }
		if (dis != null) { dis.close(); }
		if (client != null) { client.close(); }
	}
	
}
