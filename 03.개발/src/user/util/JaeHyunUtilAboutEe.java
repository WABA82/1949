package user.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * ���� �������� ��� ����ڰ� �ʿ��� ������ ����,��û �����ϴ� Ŭ���� �Դϴ�.
 * @author ����
 *
 */
public class JaeHyunUtilAboutEe {

	/**
	 * ���ϼ������� �������ڰ� �ʿ��� �Ϲݻ������ �̹��� ��õ�ϱ�
	 */
	public void requestEeImg() {
		// ����
		
	}// requestEeImg()
	
//	/**
//	 * Admin�� ���� eeImg�� �����ִ� �޼ҵ�
//	 * @throws IOException
//	 * @throws ClassNotFoundException
//	 */
//	public void eeImgsListRequest() throws IOException, ClassNotFoundException {
//		// ���� ���ϼ����� ���� ���ϸ���� ����Ʈ�� ����
//		List<String> listImg = new ArrayList<String>();
//		
//		File dir = new File("C:/dev/1949/03.����/src/file/eeImg");
//		
//		for(File f : dir.listFiles()) {
//			listImg.add(f.getName());
//		}
//		
//		oos = new ObjectOutputStream(client.getOutputStream());
//		oos.writeObject(listImg);
//		oos.flush();
//		
//		ois = new ObjectInputStream(client.getInputStream());
//		
//		// Admin�� ���� ���ϸ� ����Ʈ�� ���۹���
//		listImg = (List<String>)ois.readObject(); 
//		System.out.println(listImg);
//		
//		String filePath = dir.getAbsolutePath();
//		byte[] readData = new byte[512];
//		int arrCnt = 0;
//		int len = 0;
//		String fileName = "";
//		
//		for(int i=0; i<listImg.size(); i++) { // ���� ������ ����ŭ �ݺ�����
//			fileName = listImg.get(i);
//			
//			System.out.println("fsName : "+fileName);
//			dos.writeUTF(fileName); // ���ϸ� ����
//			dos.flush();
//			
//			fis = new FileInputStream(new File(filePath+"/"+fileName));
//			while((len = fis.read(readData)) != -1) { 
//				arrCnt++;
//			}
//			
//			dos.writeInt(arrCnt); // ������ ũ��(�迭 ��) ����
//			dos.flush();
//			
//			fis.close();
//			fis = new FileInputStream(new File(filePath+"/"+fileName));
//			while((len = fis.read(readData)) != -1) {
//				dos.write(readData, 0, len);
//				dos.flush();
//			}
//			dis.readUTF();
//			arrCnt=0;
//			len=0;
//		}
//	}
	
}// class
