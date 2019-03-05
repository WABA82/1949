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
 * FileServer와 연결되는 소켓들의 요청을 처리해주는 헬퍼 클래스
 * @author owner
 */
public class FileServerHelper {
	
	private DataOutputStream dos;
	private DataInputStream dis;
	
	private static final int CO_IMG = 0; // CO이미지 추가 요청 flag변수
	private static final int EE_IMG = 1;  // EE이미지 추가 요청 flag변수
	private static final int EE_EXT = 2;  // EE이력서 추가 요청 flag변수
	
	private FileInputStream fis;
	private FileOutputStream fos;
	private ObjectOutputStream oos;
	private ObjectInputStream ois;
	private Socket client;
	
	/**
	 * 요청에 따라 분기 처리
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
		
		try {
			switch(flag) {
			case "coImgs_list_req": // 없는 co 파일목록 전송
				coImgsListRequest();
				break;
			case "eeImgs_list_req": // 없는 ee 파일목록 전송
				eeImgsListRequest();
				break;
			case "coImg_register": // co Img 추가
				fileReg(CO_IMG);
				break;
			case "coImg_delete": // co Img 삭제
				fileDel(CO_IMG);
				break;
			case "coImg_request": // co Img 전송
				fileReq(CO_IMG);
				break;
			case "eeImg_register": // ee Img 등록
				fileReg(EE_IMG);
				break;
			case "eeImg_delete": // ee Img 삭제
				fileDel(EE_IMG);
				break;
			case "eeImg_request": // ee Img 전송
				fileReq(EE_IMG);
				break;
			case "ee_ext_request": // ee 외부이력서 전송
				fileReq(EE_EXT);
				break;
			case "ee_ext_register": // ee 외부이력서 등록
				fileReg(EE_EXT);
				break;
			case "ee_ext_delete": // ee 외부이력서 삭제
				fileDel(EE_EXT);
				break;
			}
		} catch (FileNotFoundException e) {
			// File없어도 서버가 죽지 않도록 예외처리 
			System.err.println("FileServer Error! : "+e.getMessage());
		}
		
		closeAll();
		listHelper.remove(this);
	}
	
	/**
	 * Admin이 없는 coImg를 보내주는 메소드
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	@SuppressWarnings("unchecked")
	public void coImgsListRequest() throws IOException, ClassNotFoundException {
		// 현재 파일서버가 가진 파일명들을 리스트로 저장
		List<String> listImg = new ArrayList<String>();
		
		File dir = new File("C:/dev/1949/03.개발/src/file/coImg");
		
		for(File f : dir.listFiles()) {
			listImg.add(f.getName());
		}

		oos = new ObjectOutputStream(client.getOutputStream());
		oos.writeObject(listImg);
		oos.flush();
		
		ois = new ObjectInputStream(client.getInputStream());
		
		// Admin이 없는 파일명 리스트를 전송받음
		listImg = (List<String>)ois.readObject(); 
		
		String filePath = dir.getAbsolutePath();
		byte[] readData = new byte[512];;
		int arrCnt = 0;
		int len = 0;
		String fileName = "";
		
		for(int i=0; i<listImg.size(); i++) { // 없는 파일의 수만큼 반복전송
			fileName = listImg.get(i);
			
			System.out.println("init fsName : "+fileName);
			dos.writeUTF(fileName); // 파일명 전송
			dos.flush();

			fis = new FileInputStream(new File(filePath+"/"+fileName));
			while((len = fis.read(readData)) != -1) { 
				arrCnt++;
			}
			
			dos.writeInt(arrCnt); // 파일의 크기(배열 수) 전송
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
	 * Admin이 없는 eeImg를 보내주는 메소드
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	@SuppressWarnings("unchecked")
	public void eeImgsListRequest() throws IOException, ClassNotFoundException {
		// 현재 파일서버가 가진 파일명들을 리스트로 저장
		List<String> listImg = new ArrayList<String>();
		
		File dir = new File("C:/dev/1949/03.개발/src/file/eeImg");
		
		for(File f : dir.listFiles()) {
			listImg.add(f.getName());
		}
		
		oos = new ObjectOutputStream(client.getOutputStream());
		oos.writeObject(listImg);
		oos.flush();
		
		ois = new ObjectInputStream(client.getInputStream());
		
		// Admin이 없는 파일명 리스트를 전송받음
		listImg = (List<String>)ois.readObject(); 
		System.out.println(listImg);
		
		String filePath = dir.getAbsolutePath();
		byte[] readData = new byte[512];
		int arrCnt = 0;
		int len = 0;
		String fileName = "";
		
		for(int i=0; i<listImg.size(); i++) { // 없는 파일의 수만큼 반복전송
			fileName = listImg.get(i);
			
			System.out.println("fsName : "+fileName);
			dos.writeUTF(fileName); // 파일명 전송
			dos.flush();
			
			fis = new FileInputStream(new File(filePath+"/"+fileName));
			while((len = fis.read(readData)) != -1) { 
				arrCnt++;
			}
			
			dos.writeInt(arrCnt); // 파일의 크기(배열 수) 전송
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
	 * FileServer에 존재하던 file을 삭제하는 메소드
	 * @throws IOException
	 */
	public void fileDel(int flag) throws IOException  {
		String originName = dis.readUTF(); // 기존 이미지 파일명을 전달 받음
		
		File originFile = null;
		
		if (flag == CO_IMG) { // CO
			originFile = new File("C:/dev/1949/03.개발/src/file/coImg/"+originName); 
		} else if (flag == EE_IMG ){ // EE
			originFile = new File("C:/dev/1949/03.개발/src/file/eeImg/"+originName); 
		} else if (flag == EE_EXT) {
			originFile = new File("C:/dev/1949/03.개발/src/file/resume/"+originName);  
		}
		
		originFile.delete();
		
		dos.writeUTF("done");
		dos.flush();
	}
	
	/**
	 * 새로운 파일을 등록하는 메소드
	 * @throws IOException
	 */
	public void fileReg(int flag) throws IOException  {
		String newFileName = dis.readUTF(); // 수정된 파일명
		
		int arrCnt = dis.readInt(); // 파일의 크기
		
		if (flag == CO_IMG) { // CO
			fos = new FileOutputStream("C:/dev/1949/03.개발/src/file/coImg/"+newFileName);
		} else if (flag == EE_IMG){ // EE
			fos = new FileOutputStream("C:/dev/1949/03.개발/src/file/eeImg/"+newFileName);
		} else if (flag == EE_EXT) {
			fos = new FileOutputStream("C:/dev/1949/03.개발/src/file/resume/"+newFileName);
		}
		
		byte[] readData = new byte[512];
		int len = 0;
		
		for(int i=0; i<arrCnt; i++) {
			len = dis.read(readData);
			fos.write(readData, 0, len);
			fos.flush();
		}
		
		// 저장하는 쪽이 완료됐으면 완료메시지를 보내 동기화를 시켜야 함
		dos.writeUTF("done");
		dos.flush();
	}
	
	/**
	 * 요청한 file를 전달하는 메소드
	 * @throws IOException
	 */
	public void fileReq(int flag) throws IOException,FileNotFoundException {
		String fileName = dis.readUTF();
		
		if (flag == CO_IMG) { // CO_IMG
			fis = new FileInputStream("C:/dev/1949/03.개발/src/file/coImg/"+fileName);
		} else if (flag == EE_IMG){ // EE_IMG
			fis = new FileInputStream("C:/dev/1949/03.개발/src/file/eeImg/"+fileName);
		} else if (flag == EE_EXT) { // EE_EXT
			fis = new FileInputStream("C:/dev/1949/03.개발/src/file/resume/"+fileName);
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
			fis = new FileInputStream("C:/dev/1949/03.개발/src/file/coImg/"+fileName);
		} else if (flag == EE_IMG){ // EE_IMG
			fis = new FileInputStream("C:/dev/1949/03.개발/src/file/eeImg/"+fileName);
		} else if (flag == EE_EXT) { // EE_EXT
			fis = new FileInputStream("C:/dev/1949/03.개발/src/file/resume/"+fileName);
		}

		while((len = fis.read(readData)) != -1) {
			dos.write(readData, 0, len);
			dos.flush();
		}
		
		dis.readUTF(); // 응답대기
	}

	/**
	 * 소켓과 스트림을 끊는 메소드 
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
