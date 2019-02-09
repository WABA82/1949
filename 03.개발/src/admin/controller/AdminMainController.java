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
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

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
		FileDialog fd = new FileDialog(amv, "로그 저장", FileDialog.SAVE);
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
			
			msgCenter("로그내용을 log파일로 저장했습니다.");
			
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
	public void run() { // user가 보내온 요청log를 계속 기록하는 thread

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
					
					// 유저로부터 받은 msg를 로그창에 찍음
					dlm.addElement(msg.toString());
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
			amv.getDlmLog().addElement("서버를 구동합니다..");
			threadLog = new Thread(this);    // 로그를 기록하는 스레드
			threadServer = new FileServer(); // 보내는 외부이력서를 저장, 전송하는 스레드
			threadLog.start();
			threadServer.start();
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
