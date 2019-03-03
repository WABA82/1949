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
	 * 로그를 log파일로 저장하는 메소드
	 * @throws IOException
	 */
	public void saveLog() throws IOException {
		FileDialog fd = new FileDialog(amv, "로그 저장", FileDialog.SAVE);
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
				
				msgCenter("로그내용을 저장했습니다.");
				
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
	public void run() { // 로그서버 기능

		try {
			// 요청메시지는 7001포트로 전달받음
			ServerSocket serverLog = new ServerSocket(7001); 
			Socket clientLog = null;
			DataInputStream dis = null;
			DefaultListModel<String> dlm = amv.getDlmLog();
			
			String id ="";
			String ipAddr = "";
			String request = "";
			SimpleDateFormat sdf = null;
			Date date = null;
			StringBuilder msg = null; // Log화면에 보여줄 msg
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
					
					// 유저로부터 받은 msg를 로그창에 찍음
					dlm.addElement(msg.toString());
					
					jsp.getVerticalScrollBar().setValue(jsp.getVerticalScrollBar().getMaximum());
				}
			} finally {
				if (clientLog != null) { clientLog.close(); }
				if (serverLog != null) { serverLog.close(); }
			}
		} catch (IOException e) {
			msgCenter("서버 구동에 실패했습니다.");
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
					.append(Inet4Address.getLocalHost().getHostAddress()).append(") - ").append("서버 구동");
					
					amv.getDlmLog().addElement(startLog.toString());
					serverFlag = true;
					
					// 로그서버(스레드) 시작
					threadLog = new Thread(this);   
					threadLog.start();
					
					// 파일서버(스레드) 시작
					threadFileServer = new FileServer(); 
					threadFileServer.start();
					
					// 30초마다 새로운 파일이 추가되었는지 체크하고 받는 스레드
					threadCheckNewFiles = new CheckNewFiles();
					threadCheckNewFiles.start();
				
				} catch (UnknownHostException e1) {
					e1.printStackTrace();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			} else {
				msgCenter("이미 서버가 실행중입니다.");
				return;
			}
		}
		
		if (e.getSource() == amv.getJbSaveLog()) {
			switch(JOptionPane.showConfirmDialog(amv, "로그 정보를 저장하시겠습니까?")) {
			case JOptionPane.OK_OPTION:
				try {
					saveLog();
				} catch (IOException e1) {
					msgCenter("파일 저장에 실패했습니다.");
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
