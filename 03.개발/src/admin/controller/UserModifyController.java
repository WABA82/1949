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
	private AdminMgMtController ammc;
	private String addrSeq;
	
	public UserModifyController(UserModifyView umv, AdminMgMtView ammv, String addrSeq, AdminMgMtController ammc) {
		this.umv = umv;
		this.ammv = ammv;
		this.addrSeq = addrSeq;
		this.ammc = ammc;
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
				ammc.setUser();
			} catch (SQLException e) {
				msgCenter("DB에 문제가 발생했습니다.");
				e.printStackTrace();
			}
			break;
		case JOptionPane.NO_OPTION:
		case JOptionPane.CANCEL_OPTION:
		}
	}
	
	public boolean checkPass(String pass) { // 비밀번호 검증, 최대 12자리, 대문자 소문자 특수문자 조합
		boolean resultFlag = false;
		
		boolean lowerCaseFlag = false;
		boolean upperCaseFlag = false;
		boolean spSymbolFlag = false;
		
		char[] lowerCase = { 
				'a','b','c','d','e','f','g',
				'h','i','j','k','l','m','n','o','p','q','r',
				's','t','u','v','w','x','y','z'};
		
		char[] upperCase = {
				'A','B','C','D','E','F','G',
				'H','I','J','K','L','M','N','O','P','Q','R',
				'S','T','U','V','W','X','Y','Z'};
		
		char[] spSymbol = {'!','@','#','$','%','^','&','*','(',')','-','_','+','='};
		
		if(!(pass.equals("") || pass.length() > 13)) {

			for(int i=0; i<pass.length(); i++) {
				for(int j=0; j<lowerCase.length; j++) {
					if(pass.charAt(i) == lowerCase[j]) {
						lowerCaseFlag = true;
					}
				}
				for(int j=0; j<upperCase.length; j++) {
					if(pass.charAt(i) == upperCase[j]) {
						upperCaseFlag = true;
					}
				}
				for(int j=0; j<spSymbol.length; j++) {
					if(pass.charAt(i) == spSymbol[j]) {
						spSymbolFlag = true;
					}
				}
			}
			
			if(lowerCaseFlag && upperCaseFlag && spSymbolFlag) {
				resultFlag = true;
			}
		}
		return resultFlag;
	}
	
	public void modify() {
		UserModifyVO umvo = null;
		
		String id = umv.getJtfId().getText().trim();
		String pass = new String(umv.getJpfPass().getPassword()).trim();
		
		if(!checkPass(pass)) { // 비밀번호 검증
			msgCenter("비밀번호를 확인해주세요.\n 최대 12자리, 대소문자, 특수문자 조합으로 만들어주세요.");
			return;
		}
		
		String name = umv.getJtfName().getText().trim();
		
		////////////////////////////////////////////// ssn 검증필요 /////////////////////////////////////////////////
		String ssn = umv.getJtfSsn1().getText().trim()+"-"+umv.getJtfSsn2().getText().trim();
		
		////////////////////////////////////////////// tel 검증필요 /////////////////////////////////////////////////
		// tel - 000-0000-0000 
		String tel = umv.getJtfTel().getText().trim();
		String addrDetail = umv.getJtfAddr2().getText().trim();
		
		///////////////////////////////////////////// addr 검증 필요 ////////////////////////////////////////////////
		// email - @ . 필수, 최대 254자리 이내(@.제외 18자리)
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
				ammc.setUser();
				new UserModifyView(ammv, ulvo, ammc);
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
