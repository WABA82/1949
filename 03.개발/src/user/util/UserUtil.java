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

public class UserUtil {

	/**
	 * �Է¹��� ���ϸ� �ش��ϴ� ������ ���� �������� �����ϴ� �޼ҵ�
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

		dos.writeUTF(fileName); // ���� �̹����� ����
		dos.flush();

		dis.readUTF(); // ���� �� ���� ����

		closeStreams(client, dos, dis, null, null, null, null);
	}

	/**
	 * ���޹��� ���ϸ�� ���Ͽ� ���� ������ ���� ������ ������ �����ϴ� �޼ҵ�
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

		dos.writeUTF(newFileName); // ���ο� �̹����� ����
		dos.flush();

		fis = new FileInputStream(newFile);

		byte[] readData = new byte[512];
		int len = 0;
		int arrCnt = 0;
		while ((len = fis.read(readData)) != -1) {
			arrCnt++;
		}

		fis.close();

		dos.writeInt(arrCnt); // ������ ũ�� ����
		dos.flush();

		fis = new FileInputStream(newFile);

		len = 0;
		while ((len = fis.read(readData)) != -1) {
			dos.write(readData, 0, len);
			dos.flush();
		}

		dis.readUTF(); // ����Ϸ� ���� ���� �� ���� ����
		closeStreams(client, dos, dis, null, fis, null, null);

	}

	/**
	 * ���� ������ ��û�� ������ �����ϴ� �޼ҵ�
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
			fos = new FileOutputStream("C:/dev/1949/03.����/src/user/img/co/" + newFileName);
		} else if (flag.equals("ee")) {
			fos = new FileOutputStream("C:/dev/1949/03.����/src/user/img/ee/" + newFileName);
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
	 * FileServer�� ������ ���� �޼ҵ�
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
