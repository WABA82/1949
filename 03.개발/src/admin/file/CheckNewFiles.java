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
				
				sleep(10000); // 30�ʸ��� ���ο� �̹����� �ִ��� Ȯ��
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
	 * ���ϼ����κ��� admin.img.ee�� ���� �̹����� �޴� �޼ҵ�
	 * @throws UnknownHostException
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public void getEeImgs() throws UnknownHostException, IOException, ClassNotFoundException { 
		// ���ϼ����� �����ؼ� ���� ee�̹����� �����޴� �޼ҵ�
		try {
			// client = new Socket("localhost", 7002);
			client = new Socket("211.63.89.144", 7002);
			
			dos = new DataOutputStream(client.getOutputStream());
			dis = new DataInputStream(client.getInputStream());
			
			dos.writeUTF("eeImgs_list_req"); // flag - co��ü ���ϸ�� ��û
			dos.flush();

			ois = new ObjectInputStream(client.getInputStream());

			// ���ϼ����κ��� ���ϸ���Ʈ�� ���޹���
			List<String> listImg = (List<String>)ois.readObject();
			
			File dir = new File("C:/dev/1949/03.����/src/admin/img/ee");
			for(File f : dir.listFiles()) {
				listImg.remove(f.getName()); // �����ϴ� ������ ����
			}
			
			oos = new ObjectOutputStream(client.getOutputStream());
			
			// Admin�� ���� ���ϵ�, ���ϼ����� ����
			oos.writeObject(listImg);
			oos.flush();
			
			byte[] readData = new byte[512];
			int arrCnt = 0;
			int len = 0;
			String fileName = "";
			for(int i=0; i<listImg.size(); i++) {
				fileName = dis.readUTF(); // ���ϸ� �ޱ�
				
				arrCnt = dis.readInt(); // ���� ũ�� �ޱ�
				
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
	 * ���ϼ����κ��� admin.img.co�� ���� �̹����� �޴� �޼ҵ�
	 * @throws UnknownHostException
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public void getCoImgs() throws UnknownHostException, IOException, ClassNotFoundException { 
		// ���ϼ����� �����ؼ� ���� co�̹����� �����޴� �޼ҵ�
		
		try {
			
			// client = new Socket("localhost", 7002);
			client = new Socket("211.63.89.144", 7002);
			
			dos = new DataOutputStream(client.getOutputStream());
			dis = new DataInputStream(client.getInputStream());
			
			dos.writeUTF("coImgs_list_req"); // flag - co��ü ���ϸ�� ��û
			dos.flush();
			
			ois = new ObjectInputStream(client.getInputStream());
			
			// ���ϼ����κ��� ���ϸ���Ʈ�� ���޹���
			List<String> listImg = (List<String>)ois.readObject();
			
			File dir = new File("C:/dev/1949/03.����/src/admin/img/co");
			for(File f : dir.listFiles()) {
				listImg.remove(f.getName()); // �����ϴ� ������ ����
			}
			
			oos = new ObjectOutputStream(client.getOutputStream());
			
			// Admin�� ���� ���ϵ�, ���ϼ����� ����
			oos.writeObject(listImg);
			oos.flush();
			
			byte[] readData = new byte[512];
			int arrCnt = 0;
			int len = 0;
			String fileName = "";
			for(int i=0; i<listImg.size(); i++) {
				fileName = dis.readUTF(); // ���ϸ� �ޱ�
				
				arrCnt = dis.readInt(); // ���� ũ�� �ޱ�
				
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
	 * ���ϰ� ��Ʈ���� �ݴ� �޼ҵ�
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
