package admin.file;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.List;

public class CheckNewFiles extends Thread {

	private Socket client;
	private DataOutputStream dos;
	private DataInputStream dis;
	private ObjectInputStream ois;
	private ObjectOutputStream oos;
	private FileOutputStream fos;
	
	@Override
	public void run() {
		
		try {
			while(true) {
				getCoImgs();
				getEeImgs();
				
				sleep(10000); // 30초마다 새로운 이미지가 있는지 확인
			}
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 파일서버로부터 admin.img.ee에 없는 이미지를 받는 메소드
	 * @throws UnknownHostException
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	@SuppressWarnings("unchecked")
	public void getEeImgs() throws UnknownHostException, IOException, ClassNotFoundException { 
		// 파일서버에 접속해서 없는 ee이미지를 내려받는 메소드
		try {
			// client = new Socket("localhost", 7002);
			client = new Socket("211.63.89.144", 7002);
			
			dos = new DataOutputStream(client.getOutputStream());
			dis = new DataInputStream(client.getInputStream());
			
			dos.writeUTF("eeImgs_list_req"); // flag - co전체 파일목록 요청
			dos.flush();

			ois = new ObjectInputStream(client.getInputStream());

			// 파일서버로부터 파일명리스트를 전달받음
			List<String> listImg = (List<String>)ois.readObject();
			
			File dir = new File("C:/dev/1949/03.개발/src/admin/img/ee");
			for(File f : dir.listFiles()) {
				listImg.remove(f.getName()); // 존재하는 파일은 제외
			}
			
			oos = new ObjectOutputStream(client.getOutputStream());
			
			// Admin에 없는 파일들, 파일서버에 전송
			oos.writeObject(listImg);
			oos.flush();
			
			byte[] readData = new byte[512];
			int arrCnt = 0;
			int len = 0;
			String fileName = "";
			for(int i=0; i<listImg.size(); i++) {
				fileName = dis.readUTF(); // 파일명 받기
				
				arrCnt = dis.readInt(); // 파일 크기 받기
				
				fos = new FileOutputStream(dir.getAbsolutePath()+"/"+fileName);
				
				
				for(int j=0; j<arrCnt; j++) {
					len = dis.read(readData);
					fos.write(readData, 0, len);
					fos.flush();
				}
				fos.close();
				dos.writeUTF("downDone");
				dos.flush();
			}
		} finally {
			closeStreams();
		}
	}
	
	/**
	 * 파일서버로부터 admin.img.co에 없는 이미지를 받는 메소드
	 * @throws UnknownHostException
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	@SuppressWarnings("unchecked")
	public void getCoImgs() throws UnknownHostException, IOException, ClassNotFoundException { 
		// 파일서버에 접속해서 없는 co이미지를 내려받는 메소드
		
		try {
			
			// client = new Socket("localhost", 7002);
			client = new Socket("211.63.89.144", 7002);
			
			dos = new DataOutputStream(client.getOutputStream());
			dis = new DataInputStream(client.getInputStream());
			
			dos.writeUTF("coImgs_list_req"); // flag - co전체 파일목록 요청
			dos.flush();
			
			ois = new ObjectInputStream(client.getInputStream());
			
			// 파일서버로부터 파일명리스트를 전달받음
			List<String> listImg = (List<String>)ois.readObject();
			
			File dir = new File("C:/dev/1949/03.개발/src/admin/img/co");
			for(File f : dir.listFiles()) {
				listImg.remove(f.getName()); // 존재하는 파일은 제외
			}
			
			oos = new ObjectOutputStream(client.getOutputStream());
			
			// Admin에 없는 파일들, 파일서버에 전송
			oos.writeObject(listImg);
			oos.flush();
			
			byte[] readData = new byte[512];
			int arrCnt = 0;
			int len = 0;
			String fileName = "";
			for(int i=0; i<listImg.size(); i++) {
				fileName = dis.readUTF(); // 파일명 받기
				
				arrCnt = dis.readInt(); // 파일 크기 받기
				
				fos = new FileOutputStream(dir.getAbsolutePath()+"/"+fileName);
				
				
				for(int j=0; j<arrCnt; j++) {
					len = dis.read(readData);
					fos.write(readData, 0, len);
					fos.flush();
				}
				fos.close();
				dos.writeUTF("downDone");
				dos.flush();
			}
		} finally {
			closeStreams();
		}
	}
	
	/**
	 * 소켓과 스트림을 닫는 메소드
	 * @throws IOException
	 */
	public void closeStreams() throws IOException {
		if (fos != null) { fos.close(); }
		if (dis != null) { dis.close(); }
		if (oos != null) { oos.close(); }
		if (ois != null) { ois.close(); }
		if (dos != null) { dos.close(); }
		if (client != null) { client.close(); }
	}
}
