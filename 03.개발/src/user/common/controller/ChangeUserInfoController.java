package user.common.controller;

import static javax.swing.JOptionPane.showMessageDialog;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import user.common.view.ChangeUserInfoView;
import user.common.view.RemoveUserView;
import user.common.view.SearchAddrView;
import user.common.vo.UserInfoVO;
import user.common.vo.UserModifyVO;
import user.common.vo.UserModifyWithoutPassVO;
import user.dao.CommonDAO;
import user.ee.view.EeMainView;
import user.er.view.ErMainView;
import user.run.LogTestChangeUserInfo;

public class ChangeUserInfoController extends WindowAdapter implements ActionListener,KeyListener {

	private ChangeUserInfoView cuiv;
	private UserInfoVO uivo;
	private String addrSeq;
	
	private JFrame jf;
	
	public ChangeUserInfoController(JFrame jf, ChangeUserInfoView cuiv, UserInfoVO uivo) {
		this.cuiv=cuiv;
		this.uivo=uivo;
		this.addrSeq=uivo.getSeq();
		this.jf=jf;
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
		
		if(!(pass.equals("") || pass.length() > 12)) {

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

	/**
	 *  ����� ���� ����
	 */
	public void modifyUser() {
		//�ּҺ�����ϰԸ����ֱ�
		String id=cuiv.getJtfId().getText().trim();
		String name= cuiv.getJtfName().getText().trim();
		String InputOriginPass=new String(cuiv.getJpfOriginalPass().getPassword()).trim();		
		String newPass1=new String(cuiv.getJpfNewPass1().getPassword()).trim();
		String newPass2=new String(cuiv.getJpfNewPass2().getPassword()).trim();
		String tel=cuiv.getJtfTel().getText().trim();
		String email=cuiv.getJtfEmail().getText().trim();
		String addrDetail=cuiv.getJtfAddr2().getText().trim();
		
		
		//������ ��й�ȣ �ʼ��Է� �Է��ߴٸ� ����
		//�ִ� 12�ڸ�, �빮�� �ҹ��� Ư������ ����
		if(InputOriginPass==null||InputOriginPass.equals("")) {
			JOptionPane.showMessageDialog(cuiv, "��й�ȣ�� �Է����ּ���.");
			cuiv.getJpfOriginalPass().requestFocus();
			return;
		}//end if

		//��ȭ��ȣ ���� -���� 11�ڸ�(010-0000-0000)
		try {
			String tel2=tel.replaceAll("-", "");
		
		if(tel2.length()!=11) {
			JOptionPane.showMessageDialog(cuiv, "�ùٸ� ��ȭ��ȣ ������ �ƴմϴ�\n��) 010-0000-0000");
			return;
			
		}else {//11�ڸ����  :-�ִ��� Ȯ���ϰ�, - - ���� ��ȣ �ڸ��� ����
			//-�ʼ��Է�
			if(!(tel.contains("-"))) {
				JOptionPane.showMessageDialog(cuiv, "�ùٸ� ��ȭ��ȣ ������ �ƴմϴ�\n��) 010-0000-0000");
				return;
			}//end if
			
			//010�ܿ��� ���� �ʵ���
			if(!(tel.substring(0, tel.indexOf("-")).equals("010"))) {
				JOptionPane.showMessageDialog(cuiv, "�ùٸ� ��ȭ��ȣ ������ �ƴմϴ�\n��) 010-0000-0000");
				return;
			}//end if
			
			
			//010-0000-0000
			//ù-�������ڸ���3�ڸ� , --���� 4�ڸ�, ������4�ڸ�(ù��°��������..)
			if(!(tel.substring(0, tel.indexOf("-")).length()==3)
				||!(tel.substring(tel.indexOf("-")+1, tel.lastIndexOf("-")).length()==4)) {
					 
					JOptionPane.showMessageDialog(cuiv, "�ùٸ� ��ȭ��ȣ ������ �ƴմϴ�\n��) 010-0000-0000");
					return;
			}//end if
			
				Integer.parseInt(tel2);
			}//end else
		} catch (NumberFormatException nfe) {
			showMessageDialog(cuiv, "��ȭ��ȣ�� ���ڿ��� ����ֽ��ϴ�.");
			return;
		} //end catch	
		
		
		
		//�̸��� ����
		if(email.length() <14) {//@. ���� �ּ� 14�ڸ� �̻�
			JOptionPane.showMessageDialog(cuiv, "�̸����� 14�ڸ� �̻��̾���մϴ�.");
			return;
		}else {//14�ڸ��̻��̶��
	
		if(!(email.contains("@")&& email.contains("."))) {
			JOptionPane.showMessageDialog(cuiv, "�ùٸ� �̸��� ������ �ƴմϴ�. \n��)won111@naver.com");
			return;
		
		}//end if
		}//end else


		//���ڿ� üũ
		if(addrDetail==null||addrDetail.equals("")) {
			JOptionPane.showMessageDialog(cuiv, "���ּҸ� �Է����ּ���.");
			cuiv.getJtfAddr2().requestFocus();
			return;
		}//end if
		

		//��й�ȣ ����
		if(newPass1.equals("")) {//��й�ȣ��������������
			UserModifyWithoutPassVO umvo2=new UserModifyWithoutPassVO(id, name, tel, addrSeq, addrDetail, email);

			try {			
					if(!(CommonDAO.getInstance().login(id, InputOriginPass)).equals("R")
							&&!(CommonDAO.getInstance().login(id, InputOriginPass)).equals("E")) {//null�̸�
						JOptionPane.showMessageDialog(cuiv, "��й�ȣ�� �ùٸ��� �ʽ��ϴ�.");
					}else {//R�̶��(���̵�� ��й�ȣ�� �´ٸ�) ������
						if (CommonDAO.getInstance().updateUserInfoWithoutPass(umvo2)) {
							JOptionPane.showMessageDialog(cuiv, "ȸ�������� �����Ǿ����ϴ�.");
							cuiv.dispose();
							new LogTestChangeUserInfo();
						}//end if
					}//end else
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(cuiv, "DB���� ������ �߻��߽��ϴ�.");
				e.printStackTrace();
			}
		}else {//����й�ȣ�Է½�//////////////////////////////////////////////////
		
			UserModifyVO umvo=new UserModifyVO(id, name, newPass1, tel, addrSeq, addrDetail, email);

		try {//��й�ȣ ����
			
			if(!newPass1.equals(newPass2)) {//����й�ȣȮ���� �ٸ���
				JOptionPane.showMessageDialog(cuiv, "��й�ȣȮ�ΰ� ��й�ȣ�� ��ġ���� �ʽ��ϴ�.");
			}else {//�� ��й�ȣ�� ��й�ȣ Ȯ���� ���ٸ� 
				if(!(CommonDAO.getInstance().login(id, InputOriginPass)).equals("R")
						&&!(CommonDAO.getInstance().login(id, InputOriginPass)).equals("E")) {//null�̸�(���̵�ͺ���̴ٸ��ٸ�)
					JOptionPane.showMessageDialog(cuiv, "��й�ȣ�� �ùٸ��� �ʽ��ϴ�.");
				}else {//R�̶��(���̵�� ��й�ȣ�� �´ٸ�) ������
								if(!checkPass(newPass1)) {
									JOptionPane.showMessageDialog(cuiv, "��й�ȣ�� Ȯ�����ּ���\n�빮��,�ҹ���,Ư������ �������� �Է����ּ���.");
									return;
								}else {
									if (CommonDAO.getInstance().updateUserInfo(umvo)) {
								JOptionPane.showMessageDialog(cuiv, "ȸ�������� �����Ǿ����ϴ�.");
								cuiv.dispose();
								new LogTestChangeUserInfo();
								}//end if
						}//end else
					
					}//end else
		
			}//end else
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(cuiv, "DB���� ������ �߻��߽��ϴ�.");
			e.printStackTrace();
			}//end catch
		
		}//end else
	}//modifyUser	

	public void removeUser() {
		new RemoveUserView(cuiv, jf, uivo.getId());
	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		if(ae.getSource()==cuiv.getJbModify()) {
			modifyUser();
		}

		
		if (ae.getSource() == cuiv.getJbAddr()) {
			new SearchAddrView(cuiv,null, this);
		}
			
		if(ae.getSource()==cuiv.getJbDelete()) {
			removeUser();
		
		}
		if(ae.getSource()==cuiv.getJbClose()) {
			cuiv.dispose();
		}
	}
	@Override
	public void windowClosing(WindowEvent e) {
		cuiv.dispose();
	}

	public String getAddrSeq() {
		return addrSeq;
	}

	public void setAddrSeq(String addrSeq) {
		this.addrSeq = addrSeq;
	}

	public ChangeUserInfoView getCuiv() {
		return cuiv;
	}


	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode()==10) {
			modifyUser();
			}
	}
	
	@Override
	public void keyTyped(KeyEvent e) {}

	@Override
	public void keyReleased(KeyEvent e) {}
	
}
