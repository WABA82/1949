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
	
	private static final boolean CO = true; // CO이미지 변경 요청 flag변수
	private static final boolean EE = false;  // EE이미지 변경 요청 flag변수
	
	private FileInputStream fis;
	private FileOutputStream fos;
	private ObjectOutputStream oos;
	private ObjectInputStream ois;
	private Socket client;
	
	/**
	 * 요청을 처리하는 Helper클래스
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
		case "coImgs_list_req": // 없는 co 파일목록 전송
			coImgsListRequest();
			break;
		case "eeImgs_list_req": // 없는 ee 파일목록 전송
			eeImgsListRequest();
			break;
		case "coImg_register": // co Img 추가
			ImgReg(CO);
			break;
		case "coImg_delete": // co Img 삭제
			ImgDel(CO);
			break;
		case "coImg_request": // co Img 전송
			ImgReq(CO);
			break;
		case "eeImg_register": // ee Img 등록
			ImgReg(EE);
			break;
		case "eeImg_delete": // ee Img 삭제
			ImgDel(EE);
			break;
		case "eeImg_request": // ee Img 전송
			ImgReq(EE);
			break;
		case "ee_ext_req": // ee 외부이력서 전송
			
			break;
		case "ee_ext_reg": // ee 외부이력서 등록
			
			break;
		}
		
		closeStreams();
		listHelper.remove(this);
	}
	
	/**
	 * Admin이 없는 coImg를 보내주는 메소드
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
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
	 * Admin이 없는 eeImg를 보내주는 메소드
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
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
	 * FileServer에 존재하던 Img를 삭제하는 메소드
	 * @throws IOException
	 */
	public void ImgDel(boolean flag) throws IOException  {
		String originName = dis.readUTF(); // 기존 이미지 파일명을 전달 받음
		
		File originFile = null;
		
		if (flag) { // CO
			originFile = new File("C:/dev/1949/03.개발/src/file/coImg/"+originName); // 기존 파일들 삭제 
		} else { // EE
			originFile = new File("C:/dev/1949/03.개발/src/file/eeImg/"+originName); // 기존 파일들 삭제 
		}
		
		originFile.delete();
		
		dos.writeUTF("done");
		dos.flush();
	}
	
	/**
	 * 새로운 Img파일을 등록하는 메소드
	 * @throws IOException
	 */
	public void ImgReg(boolean flag) throws IOException  {
		String newFileName = dis.readUTF(); // 수정된 파일명
		
		int arrCnt = dis.readInt(); // 파일의 크기
		
		if (flag) { // CO
			fos = new FileOutputStream("C:/dev/1949/03.개발/src/file/coImg/"+newFileName);
		} else { // EE
			fos = new FileOutputStream("C:/dev/1949/03.개발/src/file/eeImg/"+newFileName);
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
	 * 요청한 Img를 전달하는 메소드
	 * @throws IOException
	 */
	public void ImgReq(boolean flag) throws IOException {
		String fileName = dis.readUTF();
		
		if (flag) { // CO
			fis = new FileInputStream("C:/dev/1949/03.개발/src/file/coImg/"+fileName);
		} else { // EE
			fis = new FileInputStream("C:/dev/1949/03.개발/src/file/eeImg/"+fileName);
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
			fis = new FileInputStream("C:/dev/1949/03.개발/src/file/coImg/"+fileName);
		} else { // EE
			fis = new FileInputStream("C:/dev/1949/03.개발/src/file/eeImg/"+fileName);
		}

		while((len = fis.read(readData)) != -1) {
			dos.write(readData, 0, len);
			dos.flush();
		}
		
		dis.readUTF(); // 응답대기
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
