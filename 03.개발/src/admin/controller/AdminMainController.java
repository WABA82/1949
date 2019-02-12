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
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
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
	private Thread threadLog, threadServer;
	
	public AdminMainController(AdminMainView amv) {
		this.amv = amv;
	}

	private void msgCenter(String msg) {
		JOptionPane.showMessageDialog(amv, msg);
	}
	
	public void saveLog() throws IOException {
		FileDialog fd = new FileDialog(amv, "�α� ����", FileDialog.SAVE);
		fd.setVisible(true);
		
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
			
			msgCenter("�α׳����� log���Ϸ� �����߽��ϴ�.");
			
		} finally {
			if (bw != null) { bw.close(); }
			if (osw != null) { osw.close(); }
			if (fos != null) { fos.close(); }
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
	public void run() { // user�� ������ ��ûlog�� ��� ����ϴ� thread

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
					
					msg.append(sdf.format(date)).append(" ").append(id).append("(")
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
			amv.getDlmLog().addElement("������ �����մϴ�..");
			threadLog = new Thread(this);    // �α׸� ����ϴ� ������
			threadServer = new FileServer(); // ���ϼ��� ������
			threadLog.start();
			threadServer.start();
			
			try {
				// ���ϼ����� �����Ű�� ���ϼ����� �������� ���� �̹����� ������ �޴� �޼ҵ�ȣ��
				getImgFiles(); 
			} catch (UnknownHostException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			} catch (ClassNotFoundException e1) {
				e1.printStackTrace();
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
	
	public void getImgFiles() throws UnknownHostException, IOException, ClassNotFoundException { 
		// ���ϼ����� �����ؼ� ���� �̹����� �����޴� �޼ҵ�
		
		Socket client = null;
		DataOutputStream dos = null;
		DataInputStream dis = null;
		ObjectInputStream ois = null;
		ObjectOutputStream oos = null;
		FileOutputStream fos = null;
		
		try {
			client = new Socket("localhost", 7002);
			dos = new DataOutputStream(client.getOutputStream());
			dis = new DataInputStream(client.getInputStream());
			dos.writeUTF("coImgs_list_req"); // co ���ϸ�� ��û
			dos.flush();

			ois = new ObjectInputStream(client.getInputStream());

			// ���ϼ����κ��� ���ϸ��� ���޹���
			List<String> listImg = (List<String>)ois.readObject();
			
			File dir = new File("C:/Users/owner/youngRepositories/1949/03.����/src/admin/img/co");
			for(File f : dir.listFiles()) {
				listImg.remove(f.getName()); // �����ϴ� ������ ����
			}
			
			// listImg�� ��� ���ϸ���� ������ ���� ���ϵ�, ���ϼ����� ���ۿ�û
			oos = new ObjectOutputStream(client.getOutputStream());
			
			oos.writeObject(listImg);
			oos.flush();
			
			int arrCnt = 0;
			
			byte[] readData = new byte[512];
			int len = 0;
			String fileName = "";
			
			int fileNum = dis.readInt(); // ���۹��� ������ �� �ޱ�
			
			for(int i=0; i<fileNum; i++) {
				fileName = dis.readUTF(); // ���ϸ� �ޱ�
				System.out.println("--���ϸ� : "+fileName);
				
				arrCnt = dis.readInt(); // ���� ũ�� �ޱ�
				
				fos = new FileOutputStream(dir.getAbsolutePath()+"/"+fileName);
				
				for(int j=0; j<arrCnt; j++) {
					len = dis.read(readData);
					fos.write(readData, 0, len);
				}
				fos.flush();
				System.out.println("���� �ٿ�Ϸ�");
				
			}
			msgCenter("���Ϲޱ� �Ϸ�");
			
		} finally {
			if (fos != null) { fos.close(); }
			if (dis != null) { dis.close(); }
			if (oos != null) { oos.close(); }
			if (ois != null) { ois.close(); }
			if (dos != null) { dos.close(); }
			if (client != null) { client.close(); }
		}
	}
}
