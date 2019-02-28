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
 *	서버로 이미지, 파일전송
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
	}//싱글톤
	
	public String uploadImgFile(File file) throws IOException{
		String fileName = "";
		
		FileInputStream fis=null;
		FileOutputStream fos=null;
		
		try {
		fis = new FileInputStream(file);
		
		byte[] readData = new byte[512];
		
		int len = 0;
		
//		C:\dev\1949\03.개발\src\admin\img\ee
		
		String uploadPath= "C:/dev/1949/03.개발/src/admin/img/ee/";
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
		System.out.println("클라이언트 접속!");
		
		dos=new DataOutputStream(client.getOutputStream());
		
		// 파일의 개수 : 1개
		dos.writeUTF("eeImg_request");
		dos.flush();
		
		// 파일명 전송
		dos.writeUTF(fileName);
		dos.flush();
		
		//서버가 보내오는 파일 수 저장
		dis = new DataInputStream(client.getInputStream());
		
		int fileCnt=dis.readInt();
		
		int fileLen=0;
		
		String fileNames="";
		String imgPath="C:/dev/1949/03.개발/src/user/img/ee/";
		
		for(int i=0; i<fileCnt; i++) {
			//전달받을 파일 조각의 개수
		
			//파일명 받기
			
			fileNames = dis.readUTF();
			
			fos=new FileOutputStream(imgPath + fileNames);
			
			byte[] readData = new byte[512];
			
				//서버에서 전송한 파일 조각을 읽어들여
				fileLen=dis.read(readData);
				
				//생성한 파일로 기록한다.
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
