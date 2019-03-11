package user.util;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.tomcat.util.codec.binary.Base64;

public class UserUtil {

	/**
	 * 입력받은 파일명에 해당하는 파일을 파일 서버에서 삭제하는 메소드
	 * 
	 * @param fileName
	 * @param flag
	 * @param client
	 * @param dos
	 * @param dis
	 * @throws UnknownHostException
	 * @throws IOException
	 */
	public void deleteFile(String fileName, String flag, Socket client, DataOutputStream dos, DataInputStream dis)
			throws UnknownHostException, IOException {
		// client = new Socket("localhost", 7002);
		client = new Socket("211.63.89.144", 7002);

		dos = new DataOutputStream(client.getOutputStream());
		dis = new DataInputStream(client.getInputStream());

		if (flag.equals("ee")) {
			dos.writeUTF("eeImg_delete");
		} else if (flag.equals("ext")) {
			dos.writeUTF("ee_ext_delete");
		} else if (flag.equals("co")) {
			dos.writeUTF("coImg_delete");
		}
		dos.flush();

		dos.writeUTF(fileName); // 기존 이미지명 전달
		dos.flush();

		dis.readUTF(); // 응답 후 연결 종료

		closeStreams(client, dos, dis, null, null, null, null);
	}

	/**
	 * 전달받은 파일명과 파일에 대한 정보로 파일 서버에 파일을 생성하는 메소드
	 * 
	 * @param newFileName
	 * @param newFile
	 * @param flag
	 * @param client
	 * @param dos
	 * @param dis
	 * @param fis
	 * @throws IOException
	 */
	public void addNewFile(String newFileName, File newFile, String flag, Socket client, DataOutputStream dos,
			DataInputStream dis, FileInputStream fis) throws IOException {
		// client = new Socket("localhost", 7002);
		client = new Socket("211.63.89.144", 7002);

		dos = new DataOutputStream(client.getOutputStream());
		dis = new DataInputStream(client.getInputStream());

		if (flag.equals("ee")) {
			dos.writeUTF("eeImg_register");
		} else if (flag.equals("ext")) {
			dos.writeUTF("ee_ext_register");
		} else if (flag.equals("co")) {
			dos.writeUTF("coImg_register");
		}

		dos.flush();

		dos.writeUTF(newFileName); // 새로운 이미지명 전달
		dos.flush();

		fis = new FileInputStream(newFile);

		byte[] readData = new byte[512];
		int len = 0;
		int arrCnt = 0;
		while ((len = fis.read(readData)) != -1) {
			arrCnt++;
		}

		fis.close();

		dos.writeInt(arrCnt); // 파일의 크기 전송
		dos.flush();

		fis = new FileInputStream(newFile);

		len = 0;
		while ((len = fis.read(readData)) != -1) {
			dos.write(readData, 0, len);
			dos.flush();
		}

		dis.readUTF(); // 저장완료 응답 받은 후 연결 종료
		closeStreams(client, dos, dis, null, fis, null, null);

	}
	
	/**
	 * 평문을 암호문으로 암호화시키는 method
	 * @param plainText
	 * @return
	 */
	public String shaEncoding(String plainText) {
		String cipherText = "";
		
		Base64 base64 = new Base64();
		
		if (plainText != null || !"".equals(plainText)) {
			
			try {
				MessageDigest md = MessageDigest.getInstance("SHA-1");
				md.update(plainText.getBytes());
				cipherText = new String(base64.encode(md.digest()));
				
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			}
		}
		
		return cipherText;
	}

	/**
	 * 파일 서버로 요청한 파일을 전송하는 메소드
	 * 
	 * @param newFileName
	 * @param flag
	 * @param client
	 * @param dos
	 * @param dis
	 * @param fos
	 * @throws IOException
	 */
	public void reqFile(String newFileName, String flag, Socket client, DataOutputStream dos, DataInputStream dis, FileOutputStream fos) throws IOException {
		// client = new Socket("localhost", 7002);
		client = new Socket("211.63.89.144", 7002);

		dos = new DataOutputStream(client.getOutputStream());
		dis = new DataInputStream(client.getInputStream());

		if (flag.equals("co")) {
			dos.writeUTF("coImg_request");
		} else if (flag.equals("ee")) {
			dos.writeUTF("eeImg_request");
		}
		dos.flush();

		dos.writeUTF(newFileName);
		dos.flush();

		int arrCnt = dis.readInt();

		byte[] readData = new byte[512];
		int len = 0;

		if (flag.equals("co")) {
			fos = new FileOutputStream("C:/dev/1949/03.개발/src/user/img/co/" + newFileName);
		} else if (flag.equals("ee")) {
			fos = new FileOutputStream("C:/dev/1949/03.개발/src/user/img/ee/" + newFileName);
		}

		for (int i = 0; i < arrCnt; i++) {
			len = dis.read(readData);
			fos.write(readData, 0, len);
			fos.flush();
		}

		dos.writeUTF("done");
		dos.flush();

		closeStreams(client, dos, dis, fos, null, null, null);
	}

	/**
	 * FileServer와 연결을 끊는 메소드
	 * 
	 * @throws IOException
	 */
	public void closeStreams(Socket client, DataOutputStream dos, DataInputStream dis, FileOutputStream fos,
			FileInputStream fis, ObjectOutputStream oos, ObjectInputStream ois) throws IOException {
		if (ois != null) {
			ois.close();
		}
		if (oos != null) {
			oos.close();
		}
		if (fos != null) {
			fos.close();
		}
		if (fis != null) {
			fis.close();
		}
		if (dos != null) {
			dos.close();
		}
		if (dis != null) {
			dis.close();
		}
		if (client != null) {
			client.close();
		}
	}

}
