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
				
				msgCenter("로그내용을 log파일로 저장했습니다.");
				
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
			if (!serverFlag) {
				amv.getDlmLog().addElement("서버를 구동합니다..");
				serverFlag = true;
				
				// 로그서버(스레드) 시작
				threadLog = new Thread(this);   
				threadLog.start();
				
				// 파일서버(스레드) 시작
				threadFileServer = new FileServer(); 
				threadFileServer.start();
				
				try {
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
	
	private Socket client;
	private DataOutputStream dos;
	private DataInputStream dis;
	private ObjectInputStream ois;
	private ObjectOutputStream oos;
	private FileOutputStream fos;
	
	/**
	 * 파일서버로부터 admin.img.ee에 없는 이미지를 받는 메소드
	 * @throws UnknownHostException
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public void getEeImgs() throws UnknownHostException, IOException, ClassNotFoundException { 
		// 파일서버에 접속해서 없는 ee이미지를 내려받는 메소드
		try {
			client = new Socket("localhost", 7002);
			
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
	public void getCoImgs() throws UnknownHostException, IOException, ClassNotFoundException { 
		// 파일서버에 접속해서 없는 co이미지를 내려받는 메소드
		
		try {
			client = new Socket("localhost", 7002);
			
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
	
	public void closeStreams() throws IOException {
		if (fos != null) { fos.close(); }
		if (dis != null) { dis.close(); }
		if (oos != null) { oos.close(); }
		if (ois != null) { ois.close(); }
		if (dos != null) { dos.close(); }
		if (client != null) { client.close(); }
	}
}
