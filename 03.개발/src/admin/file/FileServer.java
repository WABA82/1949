package admin.file;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileServer extends Thread {
	
	@Override
	public void run() {
		try {
			ServerSocket serverFile = null;
			Socket client = null;
			DataInputStream dis = null;
			DataOutputStream dos = null;
			ObjectOutputStream oos = null;
			ObjectInputStream ois = null;
			FileInputStream fis = null;
			String flag = "";

			try {
				serverFile = new ServerSocket(7002); // ���ϼ��� 7002��Ʈ
				
				while(true) {
					client = serverFile.accept();
					dis = new DataInputStream(client.getInputStream());
					dos = new DataOutputStream(client.getOutputStream());
					
					flag = dis.readUTF();
					
					switch(flag) {
					case "coImgs_list_req": // co ���ϸ�� ��û
						
						// ���� ���ϼ����� ���� ���ϸ���� ����Ʈ�� ����
						List<String> listImg = new ArrayList<String>();
						
						File dir = new File("C:/dev/1949/03.����/src/file/coImg");
						
						for(File f : dir.listFiles()) {
							listImg.add(f.getName());
						}

						oos = new ObjectOutputStream(client.getOutputStream());
						oos.writeObject(listImg);
						oos.flush();
						
						ois = new ObjectInputStream(client.getInputStream());
						
						// Admin�� ���� ���ϸ� ����Ʈ�� ���۹���
						listImg = (List<String>)ois.readObject(); 
						
						System.out.println("Admin�� �������� : "+listImg);
						
						String filePath = dir.getAbsolutePath();
						byte[] readData = new byte[512];;
						int arrCnt = 0;
						int len = 0;
						String fileName = "";
						
						for(int i=0; i<listImg.size(); i++) { // ���� ������ ����ŭ �ݺ�����
							fileName = listImg.get(i);
							
							System.out.println("fsName : "+fileName);
							dos.writeUTF(fileName); // ���ϸ� ����
							dos.flush();

							fis = new FileInputStream(new File(filePath+"/"+fileName));
							while((len = fis.read(readData)) != -1) { 
								arrCnt++;
							}
							
							System.out.println("fsũ�� : "+arrCnt);
							dos.writeInt(arrCnt); // ������ ũ��(�迭 ��) ����
							dos.flush();
							
							////////////// �������� //////////////////////////////////
							fis.close();
							fis = new FileInputStream(new File(filePath+"/"+fileName));
							int cn=0;
							while((len = fis.read(readData)) != -1) {
								dos.write(readData, 0, len);
								dos.flush();
								cn++;
							}
							dis.readUTF();
							arrCnt=0;
							len=0;
							
							////////////// �������� //////////////////////////////////
						}
						System.out.println("���ϼ��� ���� ���ۿϷ�");
						
						break;
					case "eeImgs_list_req": // ee ���ϸ�� ��û
						
						break;
					case "coImgs_reg": // co Imgs ���
						
						break;
					case "coImgs_chg": // co Imgs ����
						
						// ���� �̹��� ���ϸ��� ���� ����
						
						// ���޹��� ���� ���ϵ��� ����
						
						// ���ο� ���ϸ��� ���� ����
						
						// ���ο� ������ ���޹���
						
						break;
					case "coImgs_req": // co Imgs ��û
						
						break;
					case "eeImg_reg": // ee Img ���
						
						break;
					case "eeImg_chg": // ee Img ����
						
						break;
					case "eeImg_req": // ee Img ���
						
						break;
					case "ee_ext_req": // ee �ܺ��̷¼� ��û
						
						break;
					case "ee_ext_reg": // ee �ܺ��̷¼� ���
						
						break;
					}
				}
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} finally {
				
//				if (br != null) { br.close(); }
				
				if (dos != null) { dos.close(); }
				if (fis != null) { fis.close(); }
				if (oos != null) { oos.close(); }
				if (dis != null) { dis.close(); }
				if (client != null) { client.close(); }
				if (serverFile != null) { serverFile.close(); }
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void fileSend(File file) { // ��û ������ �����ִ� �޼ҵ�

		
	}
	
	public void fileReceive() { // ���ϼ����� ���� ���� ������ �޴� �޼ҵ�
		
	}
}
