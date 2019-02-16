package admin.controller;

import java.awt.FileDialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import admin.dao.AdminDAO;
import admin.view.AdminMgMtView;
import admin.view.CoModifyView;
import admin.view.UserModifyView;
import admin.vo.CoInfoVO;
import admin.vo.CoModifyVO;
import admin.vo.UserInfoVO;
import sun.applet.resources.MsgAppletViewer_zh_CN;

public class CoModifyController extends WindowAdapter implements MouseListener, ActionListener {
	private CoModifyView cmv;
	private AdminMgMtView ammv;
	private CoInfoVO civo;
	private AdminMgMtController ammc;
	
	private File newImg1, newImg2, newImg3, newImg4;
	
	private static final int DBL_CLICK = 2;
	
	public CoModifyController(CoModifyView cmv, AdminMgMtView ammv, CoInfoVO civo, AdminMgMtController ammc) {
		this.cmv= cmv;
		this.ammv = ammv;
		this.civo = civo;
		this.ammc = ammc;
	}//생성자 끝
	
	@Override
	public void windowClosing(WindowEvent e) {
		cmv.dispose();
	}
	
	private void msgCenter(String msg) {
		JOptionPane.showMessageDialog(cmv, msg);
	}
	
	@Override
	public void actionPerformed(ActionEvent ae) {
		if(ae.getSource()==cmv.getJbModify()) {
			try {
				modify();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else if(ae.getSource()==cmv.getJbRemove()){
			try {
				remove();
			} catch (UnknownHostException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else if(ae.getSource()==cmv.getJbClose()) {
			cmv.dispose();
		}//버튼
	}
	
	/**
	 * 설립일 검증 메소드
	 * @param estDate
	 * @return
	 */
	private boolean chkEstDate(String estDate) {
		boolean flag = false;

		int yyyy = 0;
		int mm = 0;
		int dd = 0;
		
		String number = estDate.replaceAll("-", "");
		
		try {
			Integer.parseInt(number);
			yyyy = Integer.parseInt(number.substring(0, 4));
			mm = Integer.parseInt(number.substring(4,6));
			dd = Integer.parseInt(number.substring(6,8));
			
		} catch (NumberFormatException npe) {
			flag = false;
		}
		
		return flag;
	}
	
	private Socket client;
	private DataOutputStream dos;
	private DataInputStream dis;
	private FileInputStream fis;
	private FileOutputStream fos;
	
	public void deleteFile(String imgName) throws UnknownHostException, IOException {
		client = new Socket("localhost", 7002);
		
		dos = new DataOutputStream(client.getOutputStream());
		dis = new DataInputStream(client.getInputStream());
		
		dos.writeUTF("coImg_delete"); 
		dos.flush();
		
		dos.writeUTF(imgName);  // 기존 이미지명 전달
		dos.flush();
		
		dis.readUTF(); // 응답 후 연결 종료
		
		closeStreams();
	}
	
	public void addNewFile(File newFile) throws IOException {
		client = new Socket("localhost", 7002);
		
		dos = new DataOutputStream(client.getOutputStream());
		dis = new DataInputStream(client.getInputStream());
		
		dos.writeUTF("coImg_register"); 
		dos.flush();
		
		dos.writeUTF(newFile.getName()); // 새로운 이미지명 전달
		dos.flush();
		
		fis = new FileInputStream(newFile);
		
		byte[] readData = new byte[512];
		int len = 0;
		int arrCnt = 0;
		while((len = fis.read(readData)) != -1) {
			arrCnt++;
		}
		
		fis.close();

		dos.writeInt(arrCnt); // 파일의 크기 전송
		dos.flush();

		fis = new FileInputStream(newFile);
		
		len = 0;
		while((len = fis.read(readData)) != -1) {
			dos.write(readData, 0, len);
			dos.flush();
		}
		
		dis.readUTF(); // 저장완료 응답 받은 후 연결 종료
		closeStreams();
	}
	
	public void reqFile(String newFileName) throws IOException {
		client = new Socket("localhost", 7002);
		
		dos = new DataOutputStream(client.getOutputStream());
		dis = new DataInputStream(client.getInputStream());
		
		dos.writeUTF("coImg_request");
		dos.flush();
		
		dos.writeUTF(newFileName);
		dos.flush();
		
		int arrCnt = dis.readInt();
		
		byte[] readData = new byte[512];
		int len = 0;
		
		fos = new FileOutputStream("C:/dev/1949/03.개발/src/admin/img/co/"+newFileName);
		
		for(int i=0; i<arrCnt; i++) {
			len = dis.read(readData);
			fos.write(readData,0,len);
			fos.flush();
		}
		
		dos.writeUTF("done");
		dos.flush();
		
		closeStreams();
	}
	
	/**
	 * no_img파일은 삭제를 막기위해 검증하는 메소드
	 * @param fileName
	 * @return
	 */
	public boolean checkNoImg(String fileName) {
		boolean flag = false;
		
		String[] noImgNames = {"no_co_img2.png", "no_co_img3.png", "no_co_img1.png", "no_co_img4.png", "no_ee_img.png" };
		for(int i=0; i<noImgNames.length; i++) {
			if(fileName.equals(noImgNames[i])) {
				flag = true;
			}
		}
		
		return flag;
	}

	public void modify() throws IOException {
		try {
			String coNum = civo.getCoNum();
			String coName = cmv.getJtfCoName().getText().trim();
			String estDate = cmv.getJtfEstDate().getText().trim();
			
			if(chkEstDate(estDate)) {
				msgCenter("설립연도 입력값을 확인해주세요.\n입력예)2019-01-01");
				return;
			} 
			
			String coDesc = cmv.getJtaCoDesc().getText().trim();
			
			String img1,img2,img3,img4 = "";
			
			// img 변경 시 새로운 이미지명으로 변경
			img1 = civo.getImg1();
			if (newImg1 != null) {
				img1 = newImg1.getName();
			}
			img2 = civo.getImg2();
			if (newImg2 != null) {
				img2 = newImg2.getName();
			}
			img3 = civo.getImg3();
			if (newImg3 != null) {
				img3 = newImg3.getName();
			}
			img4 = civo.getImg4();
			if (newImg4 != null) {
				img4 = newImg4.getName();
			}
			
			int memberNum = 0;
			try {
				memberNum = Integer.parseInt(cmv.getMemberNum().getText().trim());
			} catch (NumberFormatException npe) {
				msgCenter("사원수는 숫자만 입력가능합니다.");
				return;
			}
			
			CoModifyVO cmvo = new CoModifyVO(coNum, coName, estDate, coDesc, 
					img1, img2, img3, img4, memberNum);
		
			if(AdminDAO.getInstance().updateCo(cmvo)) {
				// 변경된 이미지는 삭제하고 새로운 이미지로 변경
				if (newImg1 != null) {
					// 1. 내가 보관하던 이미지 지우기
					File originFile = new File("C:/dev/1949/03.개발/src/admin/img/co/"+civo.getImg1());
					
					if (!checkNoImg(civo.getImg1())) { // noImg파일인지 판단, noImg가 아니면 삭제
						originFile.delete();
						// 2. 파일 서버에 존재하는 이미지도 지우기
						deleteFile(civo.getImg1());
					}
					
					// 3. 파일 서버에 새 파일을 전송
					addNewFile(newImg1);
					
					// 4. 파일 서버에 새 파일을 요청
					reqFile(newImg1.getName());
				}
				if (newImg2 != null) {
					// 1. 내가 보관하던 이미지 지우기
					File originFile = new File("C:/dev/1949/03.개발/src/admin/img/co/"+civo.getImg2());
					
					if (!checkNoImg(civo.getImg2())) {
						originFile.delete();
						// 2. 파일 서버에 존재하는 이미지도 지우기
						deleteFile(civo.getImg2());
					}
					
					// 3. 파일 서버에 새 파일을 전송
					addNewFile(newImg2);
					
					// 4. 파일 서버에 새 파일을 요청
					reqFile(newImg2.getName());
				}
				if (newImg3 != null) {
					// 1. 내가 보관하던 이미지 지우기
					File originFile = new File("C:/dev/1949/03.개발/src/admin/img/co/"+civo.getImg3());
					
					if (!checkNoImg(civo.getImg3())) {
						originFile.delete();
						// 2. 파일 서버에 존재하는 이미지도 지우기
						deleteFile(civo.getImg3());
					}
					
					// 3. 파일 서버에 새 파일을 전송
					addNewFile(newImg3);
					
					// 4. 파일 서버에 새 파일을 요청
					reqFile(newImg3.getName());
				}
				if (newImg4 != null) {
					// 1. 내가 보관하던 이미지 지우기
					File originFile = new File("C:/dev/1949/03.개발/src/admin/img/co/"+civo.getImg4());
					
					if (!checkNoImg(civo.getImg4())) {
						originFile.delete();
						// 2. 파일 서버에 존재하는 이미지도 지우기
						deleteFile(civo.getImg4());
					}
					
					// 3. 파일 서버에 새 파일을 전송
					addNewFile(newImg4);
					
					// 4. 파일 서버에 새 파일을 요청
					reqFile(newImg4.getName());
				}
				
				
				msgCenter("회사정보가 수정되었습니다.");
				cmv.dispose();
				
				CoInfoVO newCivo = AdminDAO.getInstance().selectOneCo(coNum);
				
				ammc.setCo();
				new CoModifyView(ammv, newCivo, ammc);
			} 
		} catch (SQLException e) {
			msgCenter("DB에 문제가 발생했습니다.");
			e.printStackTrace();
		}
	}//modify
	
	/**
	 * FileServer와 연결을 끊는 메소드
	 * @throws IOException
	 */
	public void closeStreams() throws IOException {
		if (fos != null) {fos.close();}
		if (fis != null) {fis.close();}
		if (dos != null) { dos.close(); }
		if (dis != null) { dis.close(); }
		if (client != null) { client.close(); }
	}
	
	/**
	 * 기업 정보를 삭제하는 메소드
	 * @throws UnknownHostException
	 * @throws IOException
	 */
	public void remove() throws UnknownHostException, IOException { 
		switch (JOptionPane.showConfirmDialog(cmv, "정말 삭제하시겠습니까?")) {
		case JOptionPane.OK_OPTION:
			try {
				if (AdminDAO.getInstance().deleteCo(civo.getCoNum())) { // DB 데이터 삭제
					
					File originFile1 = new File("C:/dev/1949/03.개발/src/admin/img/co/"+civo.getImg1());
					File originFile2 = new File("C:/dev/1949/03.개발/src/admin/img/co/"+civo.getImg2());
					File originFile3 = new File("C:/dev/1949/03.개발/src/admin/img/co/"+civo.getImg3());
					File originFile4 = new File("C:/dev/1949/03.개발/src/admin/img/co/"+civo.getImg4());
					
					// 로컬 이미지 삭제 + FS 데이터 삭제
					if (!checkNoImg(civo.getImg1())) { // noImg파일인지 판단, noImg가 아니면 삭제
						originFile1.delete();
						// 2. 파일 서버에 존재하는 이미지도 지우기
						deleteFile(civo.getImg1());
					}
					if (!checkNoImg(civo.getImg2())) { // noImg파일인지 판단, noImg가 아니면 삭제
						originFile2.delete();
						// 2. 파일 서버에 존재하는 이미지도 지우기
						deleteFile(civo.getImg2());
					}
					if (!checkNoImg(civo.getImg3())) { // noImg파일인지 판단, noImg가 아니면 삭제
						originFile3.delete();
						// 2. 파일 서버에 존재하는 이미지도 지우기
						deleteFile(civo.getImg3());
					}
					if (!checkNoImg(civo.getImg4())) { // noImg파일인지 판단, noImg가 아니면 삭제
						originFile4.delete();
						// 2. 파일 서버에 존재하는 이미지도 지우기
						deleteFile(civo.getImg4());
					}
					
					msgCenter("회사 정보가 삭제되었습니다.");
					cmv.dispose();
					ammc.setCo();
				}
			} catch (SQLException e) {
				msgCenter("DB에 문제 발생");
				e.printStackTrace();
			}
			break;
		}
		
	}//remove 
	
	public void changeImg(JLabel jl, int imgNum) {
		
		// 이미지 변경, 이전 이미지명과 새로운 이미지명을 인스턴스에 저장
		FileDialog fd = new FileDialog(cmv, "이미지 변경", FileDialog.LOAD);
		fd.setVisible(true);
		
		String[] arrExt = { "jpg", "png", "jpeg", "gif" };
		
		String selectedFileExt = "";
		try {
			selectedFileExt = fd.getFile().substring(fd.getFile().lastIndexOf(".")+1);
		} catch (NullPointerException e) {
			// 파일 선택을 취소했을 때 NullPointException 처리 
			return;
		}
		
		boolean flag = false;
		
		for(String ext : arrExt) {
			if (selectedFileExt.equals(ext)) {
				flag = true;
			}
		}
		
		File newImgFile = null;
		if (flag) {
			newImgFile = new File(fd.getDirectory()+fd.getFile());
			
			switch(imgNum) {
			case 1:
				cmv.getJlImg1().setIcon(new ImageIcon(newImgFile.getAbsolutePath()));
				newImg1 = newImgFile;
				break;
			case 2:
				cmv.getJlImg2().setIcon(new ImageIcon(newImgFile.getAbsolutePath()));
				newImg2 = newImgFile;
				break;
			case 3:
				cmv.getJlImg3().setIcon(new ImageIcon(newImgFile.getAbsolutePath()));
				newImg3 = newImgFile;
				break;
			case 4:
				cmv.getJlImg4().setIcon(new ImageIcon(newImgFile.getAbsolutePath()));
				newImg4 = newImgFile;
				break;
			}
		} else {
			msgCenter("확장자가 png, jpg, jpeg, gif인 파일만 등록가능합니다.");
		}
	}//changeImg
	
	@Override
	public void mouseClicked(MouseEvent e) {
		switch(e.getClickCount()) {
		case DBL_CLICK: // 이미지 더블클릭으로 변경
			
			if(e.getSource() == cmv.getJlImg1()) {
				changeImg(cmv.getJlImg1(), 1);
			}
			if(e.getSource() == cmv.getJlImg2()) {
				changeImg(cmv.getJlImg2(), 2);
			}
			if(e.getSource() == cmv.getJlImg3()) {
				changeImg(cmv.getJlImg3(), 3);
			}
			if(e.getSource() == cmv.getJlImg4()) {
				changeImg(cmv.getJlImg4(), 4);
			}
			
			break;
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {}
	@Override
	public void mouseReleased(MouseEvent e) {}
	@Override
	public void mouseEntered(MouseEvent e) {}
	@Override
	public void mouseExited(MouseEvent e) {}
}
