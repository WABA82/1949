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
	
	@Override
	public void run() {
		try {
			ServerSocket serverFile = null;
			Socket client = null;
			DataInputStream dis = null;
			DataOutputStream dos = null;
			ObjectOutputStream oos = null;
			ObjectInputStream ois = null;
			FileInputStream fis = null;
			String flag = "";

//			BufferedReader br = null;
			
			try {
				serverFile = new ServerSocket(7002); // 파일서버 7002포트
				
				while(true) {
					client = serverFile.accept();
					dis = new DataInputStream(client.getInputStream());
					dos = new DataOutputStream(client.getOutputStream());
					
					flag = dis.readUTF();
					
					switch(flag) {
					case "coImgs_list_req": // co 파일목록 요청
						
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
						
						System.out.println("Admin에 없는파일 : "+listImg);
						
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

							/*br = new BufferedReader(new FileReader(filePath+"/"+fileName));
							String temp = "";
							while((temp = br.readLine()) != null) { 
								arrCnt++;
							}*/
							fis = new FileInputStream(new File(filePath+"/"+fileName));
							while((len = fis.read(readData)) != -1) { 
								arrCnt++;
							}
							
							System.out.println("fs크기 : "+arrCnt);
							dos.writeInt(arrCnt); // 파일의 크기(배열 수) 전송
							dos.flush();
							
							/*while((temp = br.readLine()) != null) {
								dos.writeUTF(temp);
								dos.flush();
							}*/
							////////////// 문제지점 //////////////////////////////////
							while((len = fis.read(readData)) != -1) {
								dos.write(readData, 0, len);
								dos.flush();
							}
							
							Thread.sleep(300);
							System.out.println((i+1)+"번째 파일 전송");
							////////////// 문제지점 //////////////////////////////////
						}
						System.out.println("파일서버 파일 전송완료");
						
						break;
					case "eeImgs_list_req": // ee 파일목록 요청
						
						break;
					case "coImgs_reg": // co Imgs 등록
						
						break;
					case "coImgs_chg": // co Imgs 변경
						
						// 기존 이미지 파일명을 전달 받음
						
						// 전달받은 기존 파일들을 삭제
						
						// 새로운 파일명을 전달 받음
						
						// 새로운 파일을 전달받음
						
						break;
					case "coImgs_req": // co Imgs 요청
						
						break;
					case "eeImg_reg": // ee Img 등록
						
						break;
					case "eeImg_chg": // ee Img 변경
						
						break;
					case "eeImg_req": // ee Img 등록
						
						break;
					case "ee_ext_req": // ee 외부이력서 요청
						
						break;
					case "ee_ext_reg": // ee 외부이력서 등록
						
						break;
					}
				}
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				// thread sleep 예외
				e.printStackTrace();
			} finally {
				
//				if (br != null) { br.close(); }
				
				if (dos != null) { dos.close(); }
				if (fis != null) { fis.close(); }
				if (oos != null) { oos.close(); }
				if (dis != null) { dis.close(); }
				if (client != null) { client.close(); }
				if (serverFile != null) { serverFile.close(); }
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void fileSend(File file) { // 요청 파일을 보내주는 메소드

		
	}
	
	public void fileReceive() { // 파일서버가 갖지 않은 파일을 받는 메소드
		
	}
}
