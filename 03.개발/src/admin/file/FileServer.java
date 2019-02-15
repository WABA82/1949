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
	
	public void eeImgsListRequest(DataInputStream dis, DataOutputStream dos, Socket client) throws IOException, ClassNotFoundException {
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
		byte[] readData = new byte[512];;
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
		// 건하가 구현예정(등록 시)
	}
	
	public void coImgsChg(DataInputStream dis, DataOutputStream dos, Socket client) throws IOException  {
		String originName = dis.readUTF(); // 기존 이미지 파일명을 전달 받음
		
		File originFile = new File("C:/dev/1949/03.개발/src/file/coImg/"+originName); // 기존 파일들 삭제 
		originFile.delete();
		
		String newFileName = dis.readUTF(); // 수정된 파일명
		System.out.println(newFileName);
		
		int arrCnt = dis.readInt(); // 파일의 크기
		
		fos = new FileOutputStream("C:/dev/1949/03.개발/src/file/coImg/"+newFileName);
		
		byte[] readData = new byte[512];
		int len = 0;
		
		for(int i=0; i<arrCnt; i++) {
			len = dis.read(readData);
			fos.write(readData, 0, len);
			fos.flush();
		}
		
		dis.readUTF();
		System.out.println("이미지1 변경완료");
	}
	
	public void coImgsReq(DataInputStream dis, DataOutputStream dos, Socket client) throws IOException {
		
		String fileName = dis.readUTF();
		
		fis = new FileInputStream("C:/dev/1949/03.개발/src/file/coImg/"+fileName);
		
		byte[] readData = new byte[512];
		int len = 0;
		int arrCnt = 0;
		while((len = fis.read(readData)) != -1) {
			arrCnt++;
		}
		
		fis.close();
		
		dos.writeInt(arrCnt);
		dos.flush();
		
		fis = new FileInputStream("C:/dev/1949/03.개발/src/file/coImg/"+fileName);
		
		while((len = fis.read(readData)) != -1) {
			dos.write(readData, 0, len);
			dos.flush();
		}
		System.out.println("파일서버 변경 후 새파일 전송 완료");
		dis.readUTF(); // 응답대기
	}
	
	@Override
	public void run() {
		try {
			String flag = "";

			try {
				serverFile = new ServerSocket(7002); // 파일서버 7002포트

				while(true) {
					client = serverFile.accept();
					
					dis = new DataInputStream(client.getInputStream());
					dos = new DataOutputStream(client.getOutputStream());
					
					flag = dis.readUTF();
					System.out.println(flag);
					
					switch(flag) {
					case "coImgs_list_req": // 없는 co 파일목록 전송
						coImgsListRequest(dis, dos, client);
						break;
					case "eeImgs_list_req": // 없는 ee 파일목록 전송
						eeImgsListRequest(dis, dos, client);
						break;
					case "coImgs_register": // co Imgs 등록
						coImgsReg(dis, dos, client);
						break;
					case "coImgs_change": // co Imgs 변경, 파일서버 지우고, 받고
						System.out.println("111");
						coImgsChg(dis, dos, client);
						System.out.println("222");
						break;
					case "coImgs_request": // co Imgs 전송
						System.out.println("파일서버 이미지 전송");
						coImgsReq(dis, dos, client);
						break;
					case "eeImg_reg": // ee Img 등록
						
						break;
					case "eeImg_chg": // ee Img 변경
						
						break;
					case "eeImg_req": // ee Img 등록
						
						break;
					case "ee_ext_req": // ee 외부이력서 전송
						
						break;
					case "ee_ext_reg": // ee 외부이력서 등록
						
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
