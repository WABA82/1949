package admin.controller;

import java.awt.FileDialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import admin.dao.AdminDAO;
import admin.view.AdminMgMtView;
import admin.view.EeModifyView;
import admin.view.ModifyExtView;
import admin.vo.EeInfoVO;
import user.ee.vo.EeModifyVO;

public class EeModifyController extends WindowAdapter implements ActionListener {

	private EeModifyView emv;
	private AdminMgMtView ammv;
	private AdminMgMtController ammc;
	private EeInfoVO eivo;
	
	private File changeImgFile; 
	private boolean changeImgFlag;
	
	public EeModifyController(EeModifyView emv, AdminMgMtView ammv, AdminMgMtController ammc, EeInfoVO eivo) {
		this.emv = emv;
		this.ammv = ammv;
		this.ammc = ammc;
		this.eivo = eivo;
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
		
		if (e.getSource() == emv.getJbRemove()) {
			switch(JOptionPane.showConfirmDialog(emv, "기본정보를 정말 삭제하시겠습니까?")) {
			case JOptionPane.OK_OPTION:
				removeEe();
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
	
	public void modifyEe() throws IOException {
		// 이미지, 이력서파일 변경되었는지 확인
		// 이미지가 변경되었다면 img패키지에 파일 전송(파일서버 완성 후 변경예정)
		// 이력서가 변경되었다면 기존 이력서파일 삭제 후 새로운 이력서파일 전송
		// 그 전에 변경 정보로 DB에서 update메소드처리
		
		EeModifyVO emvo = null;
		
		
		
		
		FileInputStream fis = null;
		FileOutputStream fos = null;
		try {
			if (changeImgFlag) { // 이미지가 변경되었다면 기존 이미지 삭제, 새 이미지 업로드
				
				// 기존 파일 삭제 oh99로 테스트 완료
				File originFile = new File("C:/dev/1949/03.개발/src/img/eeImg/"+eivo.getImg());
				originFile.delete();
				
				// 변경 파일 추가 // 파일서버 완성 후 변경 예정 ///////////////////////////////////////
				fis = new FileInputStream(changeImgFile);
				fos = new FileOutputStream("C:/dev/1949/03.개발/src/img/eeImg/"+changeImgFile.getName());
				
				byte[] readData = new byte[512];
				int len = 0;
				while((len = fis.read(readData)) != -1) { 
					fos.write(readData,0,len);
					fos.flush();
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (fos != null) { fos.close(); }
			if (fis != null) { fis.close(); }
		}
	}
	
	private void msgCenter(String msg) {
		JOptionPane.showMessageDialog(emv, msg);
	}
	
	public void removeEe() {
////////////파일서버 완성 후 파일 삭제요청처리해야 함 //////////////////////////////////////////////////
		if(AdminDAO.getInstance().deleteEe(eivo)) {
			msgCenter("기본정보가 삭제되었습니다.");
			emv.dispose();
			ammc.setEe();
		} else {
			msgCenter("기본정보 삭제에 실패했습니다.");
		}
	}
	
	public void changeImg() {
		// 이름의 경로를 저장, modifyEe가 수행될 때 이미지를 저장
		FileDialog fd = new FileDialog(emv, "이미지 변경", FileDialog.LOAD);
		fd.setVisible(true);
		

		if (fd.getFile().toLowerCase().endsWith(".png") || 
				fd.getFile().toLowerCase().endsWith(".jpg") || 
				fd.getFile().toLowerCase().endsWith(".jpeg")) {
			changeImgFile = new File(fd.getDirectory()+fd.getFile());
			changeImgFlag = true;
			
			emv.getJlImg().setIcon(new ImageIcon(changeImgFile.getAbsolutePath()));
		} else {
			msgCenter("확장자가 png, jpg, jpeg인 파일만 등록가능합니다.");
		}
	}
	
	public void changeExt() {
		new ModifyExtView(emv);
	}
	
	@Override
	public void windowClosing(WindowEvent e) {
		emv.dispose();
	}
}
