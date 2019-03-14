package admin.file;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * FileServer�� ����Ǵ� ���ϵ��� ��û�� ó�����ִ� ���� Ŭ����
 * @author owner
 */
public class FileServerHelper {
	
	private DataOutputStream dos;
	private DataInputStream dis;
	
	private static final int CO_IMG = 0; // CO�̹��� �߰� ��û flag����
	private static final int EE_IMG = 1;  // EE�̹��� �߰� ��û flag����
	private static final int EE_EXT = 2;  // EE�̷¼� �߰� ��û flag����
	
	private FileInputStream fis;
	private FileOutputStream fos;
	private ObjectOutputStream oos;
	private ObjectInputStream ois;
	private Socket client;
	
	/**
	 * ��û�� ���� �б� ó��
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
		
		try {
			switch(flag) {
			case "coImgs_list_req": // ���� co ���ϸ�� ����
				coImgsListRequest();
				break;
			case "eeImgs_list_req": // ���� ee ���ϸ�� ����
				eeImgsListRequest();
				break;
			case "coImg_register": // co Img �߰�
				fileReg(CO_IMG);
				break;
			case "coImg_delete": // co Img ����
				fileDel(CO_IMG);
				break;
			case "coImg_request": // co Img ����
				fileReq(CO_IMG);
				break;
			case "eeImg_register": // ee Img ���
				fileReg(EE_IMG);
				break;
			case "eeImg_delete": // ee Img ����
				fileDel(EE_IMG);
				break;
			case "eeImg_request": // ee Img ����
				fileReq(EE_IMG);
				break;
			case "ee_ext_request": // ee �ܺ��̷¼� ����
				fileReq(EE_EXT);
				break;
			case "ee_ext_register": // ee �ܺ��̷¼� ���
				fileReg(EE_EXT);
				break;
			case "ee_ext_delete": // ee �ܺ��̷¼� ����
				fileDel(EE_EXT);
				break;
			}
		} catch (FileNotFoundException e) {
			// File��� ������ ���� �ʵ��� ����ó�� 
			System.err.println("FileServer Error! : "+e.getMessage());
		}
		
		closeAll();
		listHelper.remove(this);
	}
	
	/**
	 * Admin�� ���� coImg�� �����ִ� �޼ҵ�
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	@SuppressWarnings("unchecked")
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
	 * Admin�� ���� eeImg�� �����ִ� �޼ҵ�
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	@SuppressWarnings("unchecked")
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
		
		String filePath = dir.getAbsolutePath();
		byte[] readData = new byte[512];
		int arrCnt = 0;
		int len = 0;
		String fileName = "";
		
		for(int i=0; i<listImg.size(); i++) { // ���� ������ ����ŭ �ݺ�����
			fileName = listImg.get(i);
			
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
	 * FileServer�� �����ϴ� file�� �����ϴ� �޼ҵ�
	 * @throws IOException
	 */
	public void fileDel(int flag) throws IOException  {
		String originName = dis.readUTF(); // ���� �̹��� ���ϸ��� ���� ����
		
		File originFile = null;
		
		if (flag == CO_IMG) { // CO
			originFile = new File("C:/dev/1949/03.����/src/file/coImg/"+originName); 
		} else if (flag == EE_IMG ){ // EE
			originFile = new File("C:/dev/1949/03.����/src/file/eeImg/"+originName); 
		} else if (flag == EE_EXT) {
			originFile = new File("C:/dev/1949/03.����/src/file/resume/"+originName);  
		}
		
		originFile.delete();
		
		dos.writeUTF("done");
		dos.flush();
	}
	
	/**
	 * ���ο� ������ ����ϴ� �޼ҵ�
	 * @throws IOException
	 */
	public void fileReg(int flag) throws IOException  {
		String newFileName = dis.readUTF(); // ������ ���ϸ�
		
		int arrCnt = dis.readInt(); // ������ ũ��
		
		if (flag == CO_IMG) { // CO
			fos = new FileOutputStream("C:/dev/1949/03.����/src/file/coImg/"+newFileName);
		} else if (flag == EE_IMG){ // EE
			fos = new FileOutputStream("C:/dev/1949/03.����/src/file/eeImg/"+newFileName);
		} else if (flag == EE_EXT) {
			fos = new FileOutputStream("C:/dev/1949/03.����/src/file/resume/"+newFileName);
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
	 * ��û�� file�� �����ϴ� �޼ҵ�
	 * @throws IOException
	 */
	public void fileReq(int flag) throws IOException,FileNotFoundException {
		String fileName = dis.readUTF();
		
		if (flag == CO_IMG) { // CO_IMG
			fis = new FileInputStream("C:/dev/1949/03.����/src/file/coImg/"+fileName);
		} else if (flag == EE_IMG){ // EE_IMG
			fis = new FileInputStream("C:/dev/1949/03.����/src/file/eeImg/"+fileName);
		} else if (flag == EE_EXT) { // EE_EXT
			fis = new FileInputStream("C:/dev/1949/03.����/src/file/resume/"+fileName);
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
		
		if (flag == CO_IMG) { // CO_IMG
			fis = new FileInputStream("C:/dev/1949/03.����/src/file/coImg/"+fileName);
		} else if (flag == EE_IMG){ // EE_IMG
			fis = new FileInputStream("C:/dev/1949/03.����/src/file/eeImg/"+fileName);
		} else if (flag == EE_EXT) { // EE_EXT
			fis = new FileInputStream("C:/dev/1949/03.����/src/file/resume/"+fileName);
		}

		while((len = fis.read(readData)) != -1) {
			dos.write(readData, 0, len);
			dos.flush();
		}
		
		dis.readUTF(); // ������
	}

	/**
	 * ���ϰ� ��Ʈ���� ���� �޼ҵ� 
	 * @throws IOException
	 */
	public void closeAll() throws IOException {
		if (fis != null) { fis.close(); }
		if (fos != null) { fos.close(); }
		if (oos != null) { oos.close(); }
		if (dos != null) { dos.close(); }
		if (dis != null) { dis.close(); }
		if (client != null) { client.close(); }
	}
}
