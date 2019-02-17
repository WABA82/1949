package admin.controller;

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
import java.util.List;

import javax.swing.JOptionPane;

import admin.dao.AdminDAO;
import admin.util.AdminUtil;
import admin.view.AdminMgMtView;
import admin.view.SearchAddrView;
import admin.view.UserModifyView;
import admin.vo.UserInfoVO;
import admin.vo.UserModifyVO;

public class UserModifyController extends WindowAdapter implements ActionListener {

	private UserModifyView umv;
	private AdminMgMtView ammv;
	private AdminMgMtController ammc;
	private String addrSeq;
	private AdminUtil au;
	
	private Socket client;
	private DataOutputStream dos;
	private DataInputStream dis;
	private FileInputStream fis;
	private FileOutputStream fos;
	
	public UserModifyController(UserModifyView umv, AdminMgMtView ammv, String addrSeq, AdminMgMtController ammc) {
		this.umv = umv;
		this.ammv = ammv;
		this.addrSeq = addrSeq;
		this.ammc = ammc;
		au = new AdminUtil();
	}
	
	public void msgCenter(String msg) {
		JOptionPane.showMessageDialog(umv, msg);
	}
	
	public void remove() throws UnknownHostException, IOException {
		
		String id = umv.getJtfId().getText().trim();
		
		String userType = (String)umv.getJcbUser().getSelectedItem();
		
		switch(JOptionPane.showConfirmDialog(umv, "ȸ�������� ��ϵ� ��ϵ� ��� �����˴ϴ�.\n���� �����Ͻðڽ��ϱ�?")) {
		case JOptionPane.OK_OPTION:
			try {
				
				List<String> imgList = AdminDAO.getInstance().selectUserImgs(id, userType);

				// ���� �����Ϳ� �Բ� �̹����� ���� 
				File adminImg = null;
				if (userType.equals("�Ϲ�")) {
					String extName = AdminDAO.getInstance().selectEeExt(id);
					
					if (!(extName.trim().equals(""))) {
						au.deleteFile(extName, "ext", client, dos, dis);
					}
					
					for(String imgName : imgList) {
						adminImg = new File("C:/dev/1949/03.����/src/admin/img/ee/"+imgName);
						adminImg.delete();
						au.deleteFile(imgName, "ee", client, dos, dis);
					}
				} else if (userType.equals("���")) {
					for(String imgName : imgList) {
						adminImg = new File("C:/dev/1949/03.����/src/admin/img/co/"+imgName);
						adminImg.delete();
						au.deleteFile(imgName, "co", client, dos, dis);
					}
				}
				
				AdminDAO.getInstance().deleteUser(id); // ���������͸� DB���� ����

				umv.dispose();
				msgCenter("ȸ�������� �����Ǿ����ϴ�.");
				au.sendLog(umv.getJtfId().getText()+" ȸ�� ���� ����");
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
	
	public boolean checkPass(String pass) { // ��й�ȣ ����, �ִ� 12�ڸ�, �빮�� �ҹ��� Ư������ ����
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
	
	public boolean checkSsn(String input) {
		boolean flag = false;
		
		String ssn = input.replaceAll("-", "");
		
		if (ssn.length() != 13) { // 13�ڸ�('-'����)
			flag = false;
			return flag;
		}
		
		int[] validVal = new int[12];
		int sumOfValidVal = 0;
		int j = 2;
		
		for(int i=0; i<12; i++) {
			
			if(j>9) {
				j = 2;
			}
			
			validVal[i] = Character.getNumericValue(ssn.charAt(i))*j;
			j++;
			
			sumOfValidVal += validVal[i];
		}
		
		if ((11 - (sumOfValidVal%11))%10 == Character.getNumericValue(ssn.charAt(12))) {
			flag = true;
		}
		/*-- '880101-1234567'
		-- �� �ڸ��� ������ ���� ����
		--  234567 892345
		-- ������ �ֹι�ȣ ���ڸ��� ���� ��
		-- �� �ڸ��� ����� �� ���� �� 11�� ���� �������� ����
		-- �� ����� 11���� ����
		-- �� ����� 10���� ���� �������� ����
		-- ���� ������� �ֹι�ȣ �������ڸ��� ������ ��ȿ
		-- ���� ������ ��ȿ*/
		
		return flag;
	}
	
	public boolean checkTel(String input) {
		boolean flag = false;
		
		String tel = input.replaceAll("-", ""); 
		
		try {
			Integer.parseInt(tel); // �Է°��� �������� �Ǵ�
		} catch (NumberFormatException npe) {
			return flag;
		}
		
		if(tel.length() != 11) { // 000-0000-0000 11�ڸ� ���� �ƴ϶��
			return flag;
		} else {
			flag = true;
		}
		
		return flag;
	}
	
	public boolean checkEmail(String email) { 
		boolean flag = false;
		
		if (email.length() < 13) { // �̸����� @,.���� �ּ� 14�� �̻��̾�� ��
			flag = false;
			return flag;
		}
		
		if (email.indexOf("@") != -1 && email.indexOf(".") != -1) { // @�� .�� �ִٸ�
			flag = true;
		}
		
		return flag;
	}
	
	public void modify() {
		UserModifyVO umvo = null;
		
		String id = umv.getJtfId().getText().trim();
		String pass = new String(umv.getJpfPass().getPassword()).trim();
		
		if(!checkPass(pass)) { // ��й�ȣ ����
			msgCenter("��й�ȣ�� Ȯ�����ּ���.\n �ִ� 12�ڸ�, ��ҹ���, Ư������ �������� ������ּ���.");
			return;
		}
		
		String name = umv.getJtfName().getText().trim();
		
		String ssn = umv.getJtfSsn1().getText().trim()+"-"+umv.getJtfSsn2().getText().trim();
		
		if(!checkSsn(ssn)) { // ssn ����
			msgCenter("�ùٸ� �ֹι�ȣ�� �ƴմϴ�.\n�ٽ� �Է����ּ���.");
			return;
		}
		
		String tel = umv.getJtfTel().getText().trim();
		
		if(!checkTel(tel)) { // tel ����
			msgCenter("�ùٸ� ����ó�� �ƴմϴ�.\n��)010-0000-0000\n�ٽ� �Է����ּ���.");
			return;
		}
		String addrDetail = umv.getJtfAddr2().getText().trim();
		
		// email - @ . �ʼ�, 14�ڸ� �̻�(@.����)
		String email = umv.getJtfEmail().getText().trim();
		
		if(!checkEmail(email)) { // email ����
			msgCenter("�ùٸ� �̸����� �ƴմϴ�. \n��)someid@domain.com\n�ٽ� �Է����ּ���.");
			return;
		}
		
		String questionType = String.valueOf(umv.getJcbQuestion().getSelectedIndex());
		String answer = umv.getJtfAnswer().getText().trim();
		String userType = umv.getJcbUser().getSelectedItem().equals("�Ϲ�") ? "E" : "R";
		
		umvo = new UserModifyVO(id, pass, name, ssn, tel, addrSeq, addrDetail, email, questionType, answer, userType);
		
		try {
			if(AdminDAO.getInstance().updateUser(umvo)) {
				msgCenter("ȸ�������� �����Ǿ����ϴ�.");
				au.sendLog(umv.getJtfId().getText()+" ȸ�� ���� ����");
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
		new SearchAddrView(umv, this);
		
		return addr1;
	}
	
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == umv.getJbModify()) {
			modify();
		}
		
		if (e.getSource() == umv.getJbRemove()) {
			try {
				remove();
			} catch (UnknownHostException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
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

	public void setAddrSeq(String addrSeq) {
		this.addrSeq = addrSeq;
	}
	public String getAddrSeq() {
		return addrSeq;
	}
	
}
