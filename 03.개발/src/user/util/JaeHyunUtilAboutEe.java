package user.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * 파일 서버에게 기업 사용자가 필요한 파일을 전송,요청 삭제하는 클래스 입니다.
 * @author 재현
 *
 */
public class JaeHyunUtilAboutEe {

	/**
	 * 파일서버에게 기업사용자가 필요한 일반사용자의 이미지 요천하기
	 */
	public void requestEeImg() {
		// 파일
		
	}// requestEeImg()
	
//	/**
//	 * Admin이 없는 eeImg를 보내주는 메소드
//	 * @throws IOException
//	 * @throws ClassNotFoundException
//	 */
//	public void eeImgsListRequest() throws IOException, ClassNotFoundException {
//		// 현재 파일서버가 가진 파일명들을 리스트로 저장
//		List<String> listImg = new ArrayList<String>();
//		
//		File dir = new File("C:/dev/1949/03.개발/src/file/eeImg");
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
//		// Admin이 없는 파일명 리스트를 전송받음
//		listImg = (List<String>)ois.readObject(); 
//		System.out.println(listImg);
//		
//		String filePath = dir.getAbsolutePath();
//		byte[] readData = new byte[512];
//		int arrCnt = 0;
//		int len = 0;
//		String fileName = "";
//		
//		for(int i=0; i<listImg.size(); i++) { // 없는 파일의 수만큼 반복전송
//			fileName = listImg.get(i);
//			
//			System.out.println("fsName : "+fileName);
//			dos.writeUTF(fileName); // 파일명 전송
//			dos.flush();
//			
//			fis = new FileInputStream(new File(filePath+"/"+fileName));
//			while((len = fis.read(readData)) != -1) { 
//				arrCnt++;
//			}
//			
//			dos.writeInt(arrCnt); // 파일의 크기(배열 수) 전송
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
