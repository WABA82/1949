package admin.controller;

import java.awt.FileDialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Inet4Address;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;

import admin.file.CheckNewFiles;
import admin.file.FileServer;
import admin.view.AdminMainView;
import admin.view.AdminMgMtView;

public class AdminMainController extends WindowAdapter implements ActionListener, Runnable {

	private AdminMainView amv;
	private Thread threadLog, threadFileServer, threadCheckNewFiles;
	private boolean serverFlag;
	
	public AdminMainController(AdminMainView amv) {
		this.amv = amv;
	}

	private void msgCenter(String msg) {
		JOptionPane.showMessageDialog(amv, msg);
	}
	
	/**
	 * �α׸� log���Ϸ� �����ϴ� �޼ҵ�
	 * @throws IOException
	 */
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
			JScrollPane jsp = amv.getJspLog();
			
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
					
					jsp.getVerticalScrollBar().setValue(jsp.getVerticalScrollBar().getMaximum());
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
					
					// 30�ʸ��� ���ο� ������ �߰��Ǿ����� üũ�ϰ� �޴� ������
					threadCheckNewFiles = new CheckNewFiles();
					threadCheckNewFiles.start();
				
				} catch (UnknownHostException e1) {
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
}
