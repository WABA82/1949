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
		
		switch(JOptionPane.showConfirmDialog(umv, "ȸ�������� ��ϵ� ��ϵ� ��� �����˴ϴ�.\n���� �����Ͻðڽ��ϱ�?")) {
		case JOptionPane.OK_OPTION:
			try {
				AdminDAO.getInstance().deleteUser(id);
				umv.dispose();
				msgCenter("ȸ�������� �����Ǿ����ϴ�.");
				ammc.setUser();
			} catch (SQLException e) {
				msgCenter("DB�� ������ �߻��߽��ϴ�.");
				e.printStackTrace();
			}
			break;
		case JOptionPane.NO_OPTION:
		case JOptionPane.CANCEL_OPTION:
		}
	}
	
	public void modify() {
		UserModifyVO umvo = null;
		msgCenter("�ڵ��ض� ����, ����");
		////////////////////////////////// ����, ���� �˰��� ����� ���� �޼ҵ�� ���� ���࿹�� /////////////////////////////////
		
		String id = umv.getJtfId().getText().trim();
		String pass = new String(umv.getJpfPass().getPassword()).trim();
		String name = umv.getJtfName().getText().trim();
		String ssn = umv.getJtfSsn1().getText().trim()+"-"+umv.getJtfSsn2().getText().trim();
		String tel = umv.getJtfTel().getText().trim();
		String addrDetail = umv.getJtfAddr2().getText().trim();
		String email = umv.getJtfEmail().getText().trim();
		String questionType = String.valueOf(umv.getJcbQuestion().getSelectedIndex());
		String answer = umv.getJtfAnswer().getText().trim();
		String userType = umv.getJcbUser().getSelectedItem().equals("�Ϲ�") ? "E" : "R";
		
		umvo = new UserModifyVO(id, pass, name, ssn, tel, addrSeq, addrDetail, email, questionType, answer, userType);
		
		try {
			if(AdminDAO.getInstance().updateUser(umvo)) {
				msgCenter("ȸ�������� �����Ǿ����ϴ�.");
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
		msgCenter("�ڵ��ض� ����");
		/////////////////////// ���� ȸ�����Կ��� �����ϸ� ������ �� ���� ////////////////////////////////
		
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
