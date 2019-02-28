package user.file;

import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 *	������ �̹���, ��������
 *
 * @author owner
 */
public class FileUser {
	private static FileUser fu;
	
	private FileUser() { }
	
	public static FileUser getInstance() {
		if (fu == null) {
			fu = new FileUser();
		}
		
		return fu;
	}//�̱���
	
	public String uploadImgFile(File file) throws IOException{
		String fileName = "";
		
		FileInputStream fis=null;
		FileOutputStream fos=null;
		
		try {
		fis = new FileInputStream(file);
		
		byte[] readData = new byte[512];
		
		int len = 0;
		
//		C:\dev\1949\03.����\src\admin\img\ee
		
		String uploadPath= "C:/dev/1949/03.����/src/admin/img/ee/";
		fileName = file.getName();
		
		fos = new FileOutputStream(uploadPath + fileName);
		
		while( (len=fis.read(readData)) != -1) {
			fos.write(readData, 0, len);
			fos.flush();
		}//end while
		
		}finally {
			if(fis!=null) {
				fis.close();
			}//end if
			if(fos!=null) {
				fos.close();
			}//end if
			
		}//end finally
		
		return fileName;
	}//uploadImg
	
	
	
	public void eeInfoImgSend(String fileName)throws IOException {
		Socket client = null;
		
		DataOutputStream dos=null;
		DataInputStream dis=null;
		FileOutputStream fos=null;
		
		int cnt=0;
		
		try {
		client = new Socket("211.63.89.144", 7002);
		System.out.println("Ŭ���̾�Ʈ ����!");
		
		dos=new DataOutputStream(client.getOutputStream());
		
		// ������ ���� : 1��
		dos.writeUTF("eeImg_request");
		dos.flush();
		
		// ���ϸ� ����
		dos.writeUTF(fileName);
		dos.flush();
		
		//������ �������� ���� �� ����
		dis = new DataInputStream(client.getInputStream());
		
		int fileCnt=dis.readInt();
		
		int fileLen=0;
		
		String fileNames="";
		String imgPath="C:/dev/1949/03.����/src/user/img/ee/";
		
		for(int i=0; i<fileCnt; i++) {
			//���޹��� ���� ������ ����
		
			//���ϸ� �ޱ�
			
			fileNames = dis.readUTF();
			
			fos=new FileOutputStream(imgPath + fileNames);
			
			byte[] readData = new byte[512];
			
				//�������� ������ ���� ������ �о�鿩
				fileLen=dis.read(readData);
				
				//������ ���Ϸ� ����Ѵ�.
				fos.write(readData, 0, fileLen);
				fos.flush();
				
				
			}//end while
	  } finally {
	         if (fos != null) { fos.close(); } // end if
	         if (dis != null) { dis.close(); } // end if
	         if (dos != null) { dos.close(); } // end if
	         if (client != null) { client.close(); } // end if
	      } // end finally
		
	}//eeInfoImgSend
	
	public static void main(String[] args) {
		try {
			new FileUser().eeInfoImgSend(null);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
} // class
