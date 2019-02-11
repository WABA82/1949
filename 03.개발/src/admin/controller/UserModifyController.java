package admin.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import admin.dao.AdminDAO;
import admin.view.AdminMgMtView;
import admin.view.UserModifyView;
import admin.vo.UserInfoVO;
import admin.vo.UserModifyVO;

public class UserModifyController extends WindowAdapter implements ActionListener {

	private UserModifyView umv;
	private AdminMgMtView ammv;
	private String addrSeq;
	
	public UserModifyController(UserModifyView umv, AdminMgMtView ammv, String addrSeq) {
		this.umv = umv;
		this.ammv = ammv;
		this.addrSeq = addrSeq;
	}
	
	public void msgCenter(String msg) {
		JOptionPane.showMessageDialog(umv, msg);
	}
	
	public void remove() {
		
		String id = umv.getJtfId().getText().trim();
		
		switch(JOptionPane.showConfirmDialog(umv, "회원정보로 등록된 기록도 모두 삭제됩니다.\n정말 삭제하시겠습니까?")) {
		case JOptionPane.OK_OPTION:
			try {
				AdminDAO.getInstance().deleteUser(id);
				umv.dispose();
				msgCenter("회원정보가 삭제되었습니다.");
			} catch (SQLException e) {
				msgCenter("DB에 문제가 발생했습니다.");
				e.printStackTrace();
			}
			break;
		case JOptionPane.NO_OPTION:
		case JOptionPane.CANCEL_OPTION:
		}
	}
	
	public void modify() {
		UserModifyVO umvo = null;
		msgCenter("코딩해라 혜원, 정미");
		////////////////////////////////// 혜원, 정미 알고리즘 만들면 같은 메소드로 검증 수행예정 /////////////////////////////////
		
		String id = umv.getJtfId().getText().trim();
		String pass = new String(umv.getJpfPass().getPassword()).trim();
		String name = umv.getJtfName().getText().trim();
		String ssn = umv.getJtfSsn1().getText().trim()+"-"+umv.getJtfSsn2().getText().trim();
		String tel = umv.getJtfTel().getText().trim();
		String addrDetail = umv.getJtfAddr2().getText().trim();
		String email = umv.getJtfEmail().getText().trim();
		String questionType = String.valueOf(umv.getJcbQuestion().getSelectedIndex());
		String answer = umv.getJtfAnswer().getText().trim();
		String userType = umv.getJcbUser().getSelectedItem().equals("일반") ? "E" : "R";
		
		umvo = new UserModifyVO(id, pass, name, ssn, tel, addrSeq, addrDetail, email, questionType, answer, userType);
		
		try {
			if(AdminDAO.getInstance().updateUser(umvo)) {
				msgCenter("회원정보가 수정되었습니다.");
				umv.dispose();
				UserInfoVO ulvo = AdminDAO.getInstance().selectOneUser(id);
				new UserModifyView(ammv, ulvo);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public String searchAddr() {
		String addr1 = "";
		msgCenter("코딩해라 정미");
		/////////////////////// 정미 회원가입에서 구현하면 가져다 쓸 예정 ////////////////////////////////
		
		return addr1;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == umv.getJbModify()) {
			modify();
		}
		
		if (e.getSource() == umv.getJbRemove()) {
			remove();
		}
		
		if (e.getSource() == umv.getJbSearchAddr()) {
			searchAddr();
		}
		
		if(e.getSource() == umv.getJbClose()) {
			umv.dispose();
		}
	}
	
	@Override
	public void windowClosing(WindowEvent e) {
		umv.dispose();
	}

	public UserModifyView getUmv() {
		return umv;
	}
}
