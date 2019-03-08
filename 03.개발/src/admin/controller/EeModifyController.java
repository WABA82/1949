package admin.controller;

import java.awt.FileDialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import javax.swing.JOptionPane;

import admin.dao.AdminDAO;
import admin.util.AdminUtil;
import admin.view.AdminMgMtView;
import admin.view.EeModifyView;
import admin.view.ModifyExtView;
import admin.vo.EeDeleteVO;
import admin.vo.EeInfoVO;
import admin.vo.EeModifyVO;

public class EeModifyController extends WindowAdapter implements ActionListener {

	private EeModifyView emv;
	private AdminMgMtView ammv;
	private AdminMgMtController ammc;
	private EeInfoVO eivo;
	private AdminUtil au;
	
	private File changeImgFile; 
	private File changeExtFile;
	private boolean changeImgFlag;
	private boolean changeExtFlag;

	private Socket client;
	private DataOutputStream dos;
	private DataInputStream dis;
	private FileInputStream fis;
	private FileOutputStream fos;
	
	public EeModifyController(EeModifyView emv, AdminMgMtView ammv, AdminMgMtController ammc, EeInfoVO eivo) {
		this.emv = emv;
		this.ammv = ammv;
		this.ammc = ammc;
		this.eivo = eivo;
		au = new AdminUtil();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == emv.getJbCancel()) {
			emv.dispose();
			ammc.setEe();
		}
		
		if (e.getSource() == emv.getJbModify()) {
			try {
				modifyEe();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		
		if (e.getSource() == emv.getJbDownExt()) {
			try {
				downExt();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		
		if (e.getSource() == emv.getJbRemove()) {
			switch(JOptionPane.showConfirmDialog(emv, "기본정보를 정말 삭제하시겠습니까?")) {
			case JOptionPane.OK_OPTION:
				try {
					removeEe();
				} catch (UnknownHostException e1) {
					e1.printStackTrace();
				} catch (IOException e1) {
					e1.printStackTrace();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				break;
			}
		}
		
		if (e.getSource() == emv.getJbChangeExt()) {
			changeExt();
		}
		
		if (e.getSource() == emv.getJbChangeImg()) {
			changeImg();
		}
 	}
	
	/**
	 * 외부이력서를 다운로드하는 메소드 0304 추가
	 * @throws IOException 
	 */
	public void downExt() throws IOException {
		FileDialog fd = new FileDialog(emv, "외부이력서 저장", FileDialog.SAVE);
		fd.setVisible(true);
		
		String dir = fd.getDirectory();
		String inputName = fd.getFile();
		
		if(dir == null || inputName == null) {
			return;
		}

		String extName = emv.getJtfExtRsm().getText().trim();
		String ext = extName.substring(extName.indexOf("."));
		
		try {
			client = new Socket("211.63.89.144", 7002);
			
			dos = new DataOutputStream(client.getOutputStream());
			dis = new DataInputStream(client.getInputStream());
			
			dos.writeUTF("ee_ext_request");
			dos.flush();
			
			dos.writeUTF(extName); 
			dos.flush();
			
			int fileSize = dis.readInt();
			
			fos = new FileOutputStream(dir+inputName+ext);
			
			byte[] readData = new byte[512];
			int len = 0;
			while(fileSize > 0) {
				len = dis.read(readData);
				fos.write(readData, 0, len);
				fos.flush();
				fileSize--;
			}
			
			dos.writeUTF("done");
			dos.flush();
			
			msgCenter("이력서 다운로드를 완료했습니다.");
			
		} finally {
			au.closeStreams(client, dos, dis, fos, fis, null, null);
		}
	}
	
	
	/**
	 * 회원 정보 수정 메소드
	 * @throws IOException
	 */
	public void modifyEe() throws IOException {
		// 이미지, 이력서파일 변경되었는지 확인 - flag변수
		String rank = emv.getJcbRank().getSelectedItem().equals("신입") ? "N" : "C";
		String loc = (String)emv.getJcbLoc().getSelectedItem();
		String education = (String)emv.getJcbEducation().getSelectedItem();
		String portfolio = emv.getJcbPortfolio().getSelectedItem().equals("YES") ? "Y" : "N";
		
		String img = eivo.getImg();
		if (changeImgFlag) { 
			img = System.currentTimeMillis()+changeImgFile.getName(); // 이름을 유니크하게 변경
		}
		
		String extResume = eivo.getExtResume();
		if (changeExtFlag) {
			extResume = System.currentTimeMillis()+emv.getJtfExtRsm().getText().trim(); // 이름을 유니크하게 변경
		}
		
		EeModifyVO emvo = new EeModifyVO(eivo.getEeNum(), img, rank, loc, education, portfolio, extResume);
		
		try {
			if(AdminDAO.getInstance().updateEe(emvo)) { // 데이터 수정완료
				
				if (changeImgFlag) {
					// ee는 no_img일리가 없으므로 삭제할 이미지가 no_img인지 체크 안해도 됨
					File originImg = new File("C:/dev/1949/03.개발/src/admin/img/ee/"+eivo.getImg());
					originImg.delete();
					au.deleteFile(eivo.getImg(), "ee", client, dos, dis); // 기존 이미지를 FS에서 삭제
					au.addNewFile(img, changeImgFile, "ee", client, dos, dis, fis); // 새로운 이미지를 전송
					au.reqFile(img, "ee", client, dos, dis, fos); // 새로운 이미지를 FS에게 요청, 저장
				}
				
				if (changeExtFlag) { // 이력서는 Admin Server에 저장할 필요 없음
					// 기존 이력서를 FS에서 삭제
					if (eivo.getExtResume() != null) {
						au.deleteFile(eivo.getExtResume(), "ext", client, dos, dis);
					}
					// 변경된 이력서만 FS에 추가 
					au.addNewFile(extResume, changeExtFile, "ext", client, dos, dis, fis);
				}
				
				msgCenter("기본정보를 수정했습니다.");
				au.sendLog("["+eivo.getEeNum()+"] 수정");
				emv.dispose();
				
				EeInfoVO newEivo = AdminDAO.getInstance().selectOneEe(eivo.getEeNum(), "eeNum");
				
				ammc.setEe();
				new EeModifyView(ammv, newEivo, ammc);
			}
		} catch (SQLException e) {
			msgCenter("DB에 문제 발생");
			e.printStackTrace();
		}
	}
	
	private void msgCenter(String msg) {
		JOptionPane.showMessageDialog(emv, msg);
	}
	
	/**
	 * 회원 정보 삭제 메소드
	 * @throws UnknownHostException
	 * @throws IOException
	 * @throws SQLException 
	 */
	public void removeEe() throws UnknownHostException, IOException, SQLException {
		EeDeleteVO edvo = AdminDAO.getInstance().selectEDVO(eivo.getId());
		if(AdminDAO.getInstance().deleteEe(edvo)) {
			
			File originImg = new File("C:/dev/1949/03.개발/src/admin/img/ee/"+eivo.getImg());
			originImg.delete(); 
			au.deleteFile(eivo.getExtResume(), "ext", client, dos, dis);
			au.deleteFile(eivo.getImg(), "ee", client, dos, dis);
			
			msgCenter(eivo.getEeNum()+"기본정보가 삭제되었습니다.");
			au.sendLog("["+eivo.getEeNum()+"] 삭제");
			emv.dispose();
			ammc.setEe();
		}
	}
	
	/**
	 * 이미지를 변경하는 메소드
	 * 변경 후 changeImgFlag를 true로 변경, changeImgFile을 생성
	 */
	public void changeImg() {
		// 이름의 경로를 저장, modifyEe가 수행될 때 이미지를 저장
		FileDialog fd = new FileDialog(emv, "이미지 변경", FileDialog.LOAD);
		fd.setVisible(true);
		
		if (fd.getDirectory() != null && fd.getName() != null) {
			if (fd.getFile().toLowerCase().endsWith(".png") || 
					fd.getFile().toLowerCase().endsWith(".jpg") || 
					fd.getFile().toLowerCase().endsWith(".jpeg") ||
					fd.getFile().toLowerCase().endsWith(".gif")) {
				changeImgFile = new File(fd.getDirectory()+fd.getFile());
				changeImgFlag = true;
				
				emv.getJlImg().setIcon(new ImageIcon(changeImgFile.getAbsolutePath()));
			} else {
				msgCenter("확장자가 png, jpg, jpeg, gif인 파일만 등록가능합니다.");
			}
		}
	}
	
	/**
	 * 이력서를 변경하는 메소드
	 */
	public void changeExt() {
		new ModifyExtView(emv, this);
	}
	
	@Override
	public void windowClosing(WindowEvent e) {
		emv.dispose();
	}

	public File getChangeExtFile() {
		return changeExtFile;
	}

	public void setChangeExtFile(File changeExtFile) {
		this.changeExtFile = changeExtFile;
	}

	public boolean isChangeExtFlag() {
		return changeExtFlag;
	}
	public void setChangeExtFlag(boolean changeExtFlag) {
		this.changeExtFlag = changeExtFlag;
	}
}
