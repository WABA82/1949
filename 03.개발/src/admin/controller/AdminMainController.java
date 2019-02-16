package admin.controller;

import java.awt.FileDialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.net.Inet4Address;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;

import admin.file.FileServer;
import admin.view.AdminMainView;
import admin.view.AdminMgMtView;

public class AdminMainController extends WindowAdapter implements ActionListener, Runnable {

	private AdminMainView amv;
	private Thread threadLog, threadFileServer;
	private boolean serverFlag;
	
	public AdminMainController(AdminMainView amv) {
		this.amv = amv;
	}

	private void msgCenter(String msg) {
		JOptionPane.showMessageDialog(amv, msg);
	}
	
	public void saveLog() throws IOException {
		FileDialog fd = new FileDialog(amv, "�α� ����", FileDialog.SAVE);
		fd.setVisible(true);
		
		if (fd.getFile() != null && fd.getDirectory() != null) {
			String logFilePath = fd.getDirectory()+fd.getFile()+".log";
			
			DefaultListModel<String> dlm = amv.getDlmLog();
			StringBuilder logData = new StringBuilder();
			
			for(int i=0; i<dlm.size(); i++) {
				logData.append(dlm.get(i)).append("\n");
			}
			
			FileOutputStream fos = null;
			OutputStreamWriter osw = null;
			BufferedWriter bw = null;
			try {
				fos = new FileOutputStream(logFilePath);
				osw = new OutputStreamWriter(fos);
				bw = new BufferedWriter(osw);
				
				bw.write(logData.toString());
				bw.flush();
				
				msgCenter("�α׳����� �����߽��ϴ�.");
				
			} finally {
				if (bw != null) { bw.close(); }
				if (osw != null) { osw.close(); }
				if (fos != null) { fos.close(); }
			}
		}
	}
	
	@Override
	public void windowClosing(WindowEvent e) {
		amv.dispose();
	}
	
	@Override
	public void windowClosed(WindowEvent e) {
		System.exit(0);
	}
	
	@Override
	public void run() { // �α׼��� ���

		try {
			// ��û�޽����� 7001��Ʈ�� ���޹���
			ServerSocket serverLog = new ServerSocket(7001); 
			Socket clientLog = null;
			DataInputStream dis = null;
			DefaultListModel<String> dlm = amv.getDlmLog();
			
			String id ="";
			String ipAddr = "";
			String request = "";
			SimpleDateFormat sdf = null;
			Date date = null;
			StringBuilder msg = null; // Logȭ�鿡 ������ msg
			
			try {
				while(true) {
					clientLog = serverLog.accept();
					dis = new DataInputStream(clientLog.getInputStream());
					
					id = dis.readUTF();
					ipAddr = dis.readUTF();
					request = dis.readUTF();
					
					sdf = new SimpleDateFormat("yyyy-MM-dd");
					date = new Date();
					
					msg = new StringBuilder();
					
					msg.append("[").append(sdf.format(date)).append("]")
					.append(" ").append(id).append("(")
					.append(ipAddr).append(") - ").append(request);
					
					// �����κ��� ���� msg�� �α�â�� ����
					dlm.addElement(msg.toString());
				}
			} finally {
				if (clientLog != null) { clientLog.close(); }
				if (serverLog != null) { serverLog.close(); }
			}
		} catch (IOException e) {
			msgCenter("���� ������ �����߽��ϴ�.");
			e.printStackTrace();
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == amv.getJbMgmt()) {
			new AdminMgMtView(amv);
		}
		
		if (e.getSource() == amv.getJbServerOn()) {
			if (!serverFlag) {
				try {
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					Date date = new Date();
					
					StringBuilder startLog = new StringBuilder();
					startLog.append("[").append(sdf.format(date)).append("] ADMIN(")
					.append(Inet4Address.getLocalHost().getHostAddress()).append(") - ").append("���� ����");
					
					amv.getDlmLog().addElement(startLog.toString());
					serverFlag = true;
					
					// �α׼���(������) ����
					threadLog = new Thread(this);   
					threadLog.start();
					
					// ���ϼ���(������) ����
					threadFileServer = new FileServer(); 
					threadFileServer.start();
				
					getCoImgs();
					getEeImgs();
				} catch (UnknownHostException e1) {
					e1.printStackTrace();
				} catch (ClassNotFoundException e1) {
					e1.printStackTrace();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			} else {
				msgCenter("�̹� ������ �������Դϴ�.");
				return;
			}
		}
		
		if (e.getSource() == amv.getJbSaveLog()) {
			switch(JOptionPane.showConfirmDialog(amv, "�α� ������ �����Ͻðڽ��ϱ�?")) {
			case JOptionPane.OK_OPTION:
				try {
					saveLog();
				} catch (IOException e1) {
					msgCenter("���� ���忡 �����߽��ϴ�.");
					e1.printStackTrace();
				}
				break;
			case JOptionPane.NO_OPTION:
			case JOptionPane.CANCEL_OPTION:
			}
		}
		
		if (e.getSource() == amv.getJbExit()) {
			amv.dispose();
		}
	}
	
	private Socket client;
	private DataOutputStream dos;
	private DataInputStream dis;
	private ObjectInputStream ois;
	private ObjectOutputStream oos;
	private FileOutputStream fos;
	
	/**
	 * ���ϼ����κ��� admin.img.ee�� ���� �̹����� �޴� �޼ҵ�
	 * @throws UnknownHostException
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public void getEeImgs() throws UnknownHostException, IOException, ClassNotFoundException { 
		// ���ϼ����� �����ؼ� ���� ee�̹����� �����޴� �޼ҵ�
		try {
			client = new Socket("localhost", 7002);
			
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
			client = new Socket("localhost", 7002);
			
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
	
	public void closeStreams() throws IOException {
		if (fos != null) { fos.close(); }
		if (dis != null) { dis.close(); }
		if (oos != null) { oos.close(); }
		if (ois != null) { ois.close(); }
		if (dos != null) { dos.close(); }
		if (client != null) { client.close(); }
	}
}
