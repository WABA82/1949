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
	
	private ServerSocket serverFile;
	private Socket client;
	private DataInputStream dis;
	private DataOutputStream dos;
	private ObjectOutputStream oos;
	private ObjectInputStream ois;
	private FileInputStream fis;
	private FileOutputStream fos;
	
	public void coImgsListRequest(DataInputStream dis, DataOutputStream dos, Socket client) throws IOException, ClassNotFoundException {
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
		
		String filePath = dir.getAbsolutePath();
		byte[] readData = new byte[512];;
		int arrCnt = 0;
		int len = 0;
		String fileName = "";
		
		for(int i=0; i<listImg.size(); i++) { // ���� ������ ����ŭ �ݺ�����
			fileName = listImg.get(i);
			
			System.out.println("init fsName : "+fileName);
			dos.writeUTF(fileName); // ���ϸ� ����
			dos.flush();

			fis = new FileInputStream(new File(filePath+"/"+fileName));
			while((len = fis.read(readData)) != -1) { 
				arrCnt++;
			}
			
			dos.writeInt(arrCnt); // ������ ũ��(�迭 ��) ����
			dos.flush();
			
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
		}
	}
	
	public void eeImgsListRequest(DataInputStream dis, DataOutputStream dos, Socket client) throws IOException, ClassNotFoundException {
		// ���� ���ϼ����� ���� ���ϸ���� ����Ʈ�� ����
		List<String> listImg = new ArrayList<String>();
		
		File dir = new File("C:/dev/1949/03.����/src/file/eeImg");
		
		for(File f : dir.listFiles()) {
			listImg.add(f.getName());
		}
		
		oos = new ObjectOutputStream(client.getOutputStream());
		oos.writeObject(listImg);
		oos.flush();
		
		ois = new ObjectInputStream(client.getInputStream());
		
		// Admin�� ���� ���ϸ� ����Ʈ�� ���۹���
		listImg = (List<String>)ois.readObject(); 
		System.out.println(listImg);
		
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
			
			dos.writeInt(arrCnt); // ������ ũ��(�迭 ��) ����
			dos.flush();
			
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
		}
	}
	
	public void coImgsReg(DataInputStream dis, DataOutputStream dos, Socket client) {
		// ���ϰ� ��������(��� ��)
	}
	
	public void coImgsChg(DataInputStream dis, DataOutputStream dos, Socket client) throws IOException  {
		String originName = dis.readUTF(); // ���� �̹��� ���ϸ��� ���� ����
		
		File originFile = new File("C:/dev/1949/03.����/src/file/coImg/"+originName); // ���� ���ϵ� ���� 
		originFile.delete();
		
		String newFileName = dis.readUTF(); // ������ ���ϸ�
		System.out.println(newFileName);
		
		int arrCnt = dis.readInt(); // ������ ũ��
		
		fos = new FileOutputStream("C:/dev/1949/03.����/src/file/coImg/"+newFileName);
		
		byte[] readData = new byte[512];
		int len = 0;
		
		for(int i=0; i<arrCnt; i++) {
			len = dis.read(readData);
			fos.write(readData, 0, len);
			fos.flush();
		}
		
		dis.readUTF();
		System.out.println("�̹���1 ����Ϸ�");
	}
	
	public void coImgsReq(DataInputStream dis, DataOutputStream dos, Socket client) throws IOException {
		
		String fileName = dis.readUTF();
		
		fis = new FileInputStream("C:/dev/1949/03.����/src/file/coImg/"+fileName);
		
		byte[] readData = new byte[512];
		int len = 0;
		int arrCnt = 0;
		while((len = fis.read(readData)) != -1) {
			arrCnt++;
		}
		
		fis.close();
		
		dos.writeInt(arrCnt);
		dos.flush();
		
		fis = new FileInputStream("C:/dev/1949/03.����/src/file/coImg/"+fileName);
		
		while((len = fis.read(readData)) != -1) {
			dos.write(readData, 0, len);
			dos.flush();
		}
		System.out.println("���ϼ��� ���� �� ������ ���� �Ϸ�");
		dis.readUTF(); // ������
	}
	
	@Override
	public void run() {
		try {
			String flag = "";

			try {
				serverFile = new ServerSocket(7002); // ���ϼ��� 7002��Ʈ

				while(true) {
					client = serverFile.accept();
					
					dis = new DataInputStream(client.getInputStream());
					dos = new DataOutputStream(client.getOutputStream());
					
					flag = dis.readUTF();
					System.out.println(flag);
					
					switch(flag) {
					case "coImgs_list_req": // ���� co ���ϸ�� ����
						coImgsListRequest(dis, dos, client);
						break;
					case "eeImgs_list_req": // ���� ee ���ϸ�� ����
						eeImgsListRequest(dis, dos, client);
						break;
					case "coImgs_register": // co Imgs ���
						coImgsReg(dis, dos, client);
						break;
					case "coImgs_change": // co Imgs ����, ���ϼ��� �����, �ް�
						System.out.println("111");
						coImgsChg(dis, dos, client);
						System.out.println("222");
						break;
					case "coImgs_request": // co Imgs ����
						System.out.println("���ϼ��� �̹��� ����");
						coImgsReq(dis, dos, client);
						break;
					case "eeImg_reg": // ee Img ���
						
						break;
					case "eeImg_chg": // ee Img ����
						
						break;
					case "eeImg_req": // ee Img ���
						
						break;
					case "ee_ext_req": // ee �ܺ��̷¼� ����
						
						break;
					case "ee_ext_reg": // ee �ܺ��̷¼� ���
						
						break;
					}
				}
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} finally {
				closeStreams();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void closeStreams() throws IOException {
		if (dos != null) { dos.close(); }
		if (fis != null) { fis.close(); }
		if (fos != null) { fos.close(); }
		if (oos != null) { oos.close(); }
		if (dis != null) { dis.close(); }
		if (client != null) { client.close(); }
		if (serverFile != null) { serverFile.close(); }
	}
}
