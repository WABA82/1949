package admin.file;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class FileServerHelper {
	
	private DataOutputStream dos;
	private DataInputStream dis;
	
	private static final boolean CO = true; // CO�̹��� ���� ��û flag����
	private static final boolean EE = false;  // EE�̹��� ���� ��û flag����
	
	private FileInputStream fis;
	private FileOutputStream fos;
	private ObjectOutputStream oos;
	private ObjectInputStream ois;
	private Socket client;
	
	/**
	 * ��û�� ó���ϴ� HelperŬ����
	 * @param listHelper
	 * @param client
	 * @param dis
	 * @param dos
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public FileServerHelper(List<FileServerHelper> listHelper, Socket client, DataInputStream dis, DataOutputStream dos) throws IOException, ClassNotFoundException {
		listHelper.add(this);
		
		this.client = client;
		this.dis = dis;
		this.dos = dos;
		
		String flag = dis.readUTF();
		System.out.println(flag);
		
		switch(flag) {
		case "coImgs_list_req": // ���� co ���ϸ�� ����
			coImgsListRequest();
			break;
		case "eeImgs_list_req": // ���� ee ���ϸ�� ����
			eeImgsListRequest();
			break;
		case "coImg_register": // co Img �߰�
			ImgReg(CO);
			break;
		case "coImg_delete": // co Img ����
			ImgDel(CO);
			break;
		case "coImg_request": // co Img ����
			ImgReq(CO);
			break;
		case "eeImg_register": // ee Img ���
			ImgReg(EE);
			break;
		case "eeImg_delete": // ee Img ����
			ImgDel(EE);
			break;
		case "eeImg_request": // ee Img ����
			ImgReq(EE);
			break;
		case "ee_ext_req": // ee �ܺ��̷¼� ����
			
			break;
		case "ee_ext_reg": // ee �ܺ��̷¼� ���
			
			break;
		}
		
		closeStreams();
		listHelper.remove(this);
	}
	
	/**
	 * Admin�� ���� coImg�� �����ִ� �޼ҵ�
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public void coImgsListRequest() throws IOException, ClassNotFoundException {
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
	
	/**
	 * Admin�� ���� eeImg�� �����ִ� �޼ҵ�
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public void eeImgsListRequest() throws IOException, ClassNotFoundException {
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
		byte[] readData = new byte[512];
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
			while((len = fis.read(readData)) != -1) {
				dos.write(readData, 0, len);
				dos.flush();
			}
			dis.readUTF();
			arrCnt=0;
			len=0;
		}
	}

	/**
	 * FileServer�� �����ϴ� Img�� �����ϴ� �޼ҵ�
	 * @throws IOException
	 */
	public void ImgDel(boolean flag) throws IOException  {
		String originName = dis.readUTF(); // ���� �̹��� ���ϸ��� ���� ����
		
		File originFile = null;
		
		if (flag) { // CO
			originFile = new File("C:/dev/1949/03.����/src/file/coImg/"+originName); // ���� ���ϵ� ���� 
		} else { // EE
			originFile = new File("C:/dev/1949/03.����/src/file/eeImg/"+originName); // ���� ���ϵ� ���� 
		}
		
		originFile.delete();
		
		dos.writeUTF("done");
		dos.flush();
	}
	
	/**
	 * ���ο� Img������ ����ϴ� �޼ҵ�
	 * @throws IOException
	 */
	public void ImgReg(boolean flag) throws IOException  {
		String newFileName = dis.readUTF(); // ������ ���ϸ�
		
		int arrCnt = dis.readInt(); // ������ ũ��
		
		if (flag) { // CO
			fos = new FileOutputStream("C:/dev/1949/03.����/src/file/coImg/"+newFileName);
		} else { // EE
			fos = new FileOutputStream("C:/dev/1949/03.����/src/file/eeImg/"+newFileName);
		}
		
		byte[] readData = new byte[512];
		int len = 0;
		
		for(int i=0; i<arrCnt; i++) {
			len = dis.read(readData);
			fos.write(readData, 0, len);
			fos.flush();
		}
		
		// �����ϴ� ���� �Ϸ������ �Ϸ�޽����� ���� ����ȭ�� ���Ѿ� ��
		dos.writeUTF("done");
		dos.flush();
	}
	
	/**
	 * ��û�� Img�� �����ϴ� �޼ҵ�
	 * @throws IOException
	 */
	public void ImgReq(boolean flag) throws IOException {
		String fileName = dis.readUTF();
		
		if (flag) { // CO
			fis = new FileInputStream("C:/dev/1949/03.����/src/file/coImg/"+fileName);
		} else { // EE
			fis = new FileInputStream("C:/dev/1949/03.����/src/file/eeImg/"+fileName);
		}
		
		byte[] readData = new byte[512];
		int len = 0;
		int arrCnt = 0;
		while((len = fis.read(readData)) != -1) {
			arrCnt++;
		}
		
		fis.close();
		
		dos.writeInt(arrCnt);
		dos.flush();
		
		if (flag) { // CO
			fis = new FileInputStream("C:/dev/1949/03.����/src/file/coImg/"+fileName);
		} else { // EE
			fis = new FileInputStream("C:/dev/1949/03.����/src/file/eeImg/"+fileName);
		}

		while((len = fis.read(readData)) != -1) {
			dos.write(readData, 0, len);
			dos.flush();
		}
		
		dis.readUTF(); // ������
	}

	public void closeStreams() throws IOException {
		if (fis != null) { fis.close(); }
		if (fos != null) { fos.close(); }
		if (oos != null) { oos.close(); }
		if (dos != null) { dos.close(); }
		if (dis != null) { dis.close(); }
		if (client != null) { client.close(); }
	}
}
