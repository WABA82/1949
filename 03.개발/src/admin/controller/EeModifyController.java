package admin.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import admin.dao.AdminDAO;
import admin.view.AdminMgMtView;
import admin.view.EeModifyView;
import admin.view.ModifyExtView;
import admin.vo.EeInfoVO;

public class EeModifyController extends WindowAdapter implements ActionListener {

	private EeModifyView emv;
	private AdminMgMtView ammv;
	private AdminMgMtController ammc;
	private EeInfoVO eivo;
	
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
			modifyEe();
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
	
	public void modifyEe() {
		// 이미지, 이력서파일 변경되었는지 확인
		// 이미지가 변경되었다면 img패키지에 파일 전송(파일서버 완성 후 변경예정)
		// 이력서가 변경되었다면 기존 이력서파일 삭제 후 새로운 이력서파일 전송
		// 그 전에 변경 정보로 DB에서 update메소드처리
		
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
		
	}
	
	public void changeExt() {
		new ModifyExtView(emv);
	}
	
	@Override
	public void windowClosing(WindowEvent e) {
		emv.dispose();
	}
	
}
