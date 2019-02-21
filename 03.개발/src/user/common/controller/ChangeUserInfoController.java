package user.common.controller;

import static javax.swing.JOptionPane.showMessageDialog;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import user.common.view.ChangeUserInfoView;
import user.common.view.RemoveUserView;
import user.common.view.SearchAddrView;
import user.common.vo.UserInfoVO;
import user.common.vo.UserModifyVO;
import user.common.vo.UserModifyWithoutPassVO;
import user.dao.CommonDAO;
import user.er.view.ErMainView;

public class ChangeUserInfoController extends WindowAdapter implements ActionListener {

	private ChangeUserInfoView cuiv;
	private UserInfoVO uivo;
	private String addrSeq;
	private ErMainView emv;
	
	public ChangeUserInfoController(ChangeUserInfoView cuiv, UserInfoVO uivo) {
		this.cuiv=cuiv;
		this.uivo=uivo;
		this.addrSeq=uivo.getSeq();
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
		
		
		//������ ��й�ȣ �ʼ��Է�
		if(InputOriginPass==null||InputOriginPass.equals("")) {
			JOptionPane.showMessageDialog(cuiv, "��й�ȣ�� �Է����ּ���.");
			cuiv.getJpfOriginalPass().requestFocus();
			return;
		}//end if
		
		
		//��ȭ��ȣ ���� -���� 11�ڸ�(010-0000-0000)
		String tel2=tel.replaceAll("-", "");
		
		
		if(tel2.length()!=11) {
			try {
				Integer.parseInt(tel2);
			} catch (NumberFormatException nfe) {
				showMessageDialog(cuiv, "��ȭ��ȣ�� ���ڿ��� ����ֽ��ϴ�.");
				return;
			} // end catch	
			JOptionPane.showMessageDialog(cuiv, "�ùٸ� ��ȭ��ȣ ������ �ƴմϴ�2.\n��)010-0000-0000");
			return;
		}else {//11�ڸ����  :-�ִ��� Ȯ���ϰ�, - - ���� ��ȣ �ڸ��� ����
			//-�ʼ��Է�
			if(!(tel.contains("-"))) {
				JOptionPane.showMessageDialog(cuiv, "�ùٸ� ��ȭ��ȣ ������ �ƴմϴ�1.\n 010-0000-0000");
				return;
			}
			//010-0000-0000
			//ù-�������ڸ���3�ڸ� , --���� 4�ڸ�, ������4�ڸ�(ù��°��������..)
			if(!(tel.substring(0, tel.indexOf("-")).length()==3)
				||!(tel.substring(tel.indexOf("-")+1, tel.lastIndexOf("-")).length()==4)) {
					 
					JOptionPane.showMessageDialog(cuiv, "�ùٸ� ��ȭ��ȣ ������ �ƴմϴ�3.\n 010-0000-0000");
					return;
					
					
			}//end if
			
		}//end else
	
		//�̸��� ����
		if(email.length() <14) {//@. ���� �ּ� 14�ڸ� �̻�
			JOptionPane.showMessageDialog(cuiv, "�̸����� 14�ڸ� �̻��̾���մϴ�.");
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
		if(newPass1.equals("")) {
			UserModifyWithoutPassVO umvo2=new UserModifyWithoutPassVO(id, name, tel, addrSeq, addrDetail, email);
			try {
				
				if(!newPass1.equals(newPass2)) {
					JOptionPane.showMessageDialog(cuiv, "��й�ȣȮ�ΰ� ��й�ȣ�� ��ġ���� �ʽ��ϴ�.");
				}else {//���ٸ�
					if(!(CommonDAO.getInstance().login(id, InputOriginPass)).equals("R")) {//null�̸�
						JOptionPane.showMessageDialog(cuiv, "��й�ȣ�� �ùٸ��� �ʽ��ϴ�.");
					}else {//R�̶��(���̵�� ��й�ȣ�� �´ٸ�) ������
						if (CommonDAO.getInstance().updateUserInfoWithoutPass(umvo2)) {
							JOptionPane.showMessageDialog(cuiv, "ȸ�������� �����Ǿ����ϴ�.");
							cuiv.dispose();
						}//end if
					}//end else
				}//end else
				
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(cuiv, "DB���� ������ �߻��߽��ϴ�.");
				e.printStackTrace();
			}
		}else {
		
			UserModifyVO umvo=new UserModifyVO(id, name, newPass1, tel, addrSeq, addrDetail, email);

		try {//��й�ȣ ����
			
			if(!newPass1.equals(newPass2)) {
				JOptionPane.showMessageDialog(cuiv, "��й�ȣȮ�ΰ� ��й�ȣ�� ��ġ���� �ʽ��ϴ�.");
			}else {//���ٸ�
				if(!(CommonDAO.getInstance().login(id, InputOriginPass)).equals("R")) {//null�̸�
					JOptionPane.showMessageDialog(cuiv, "��й�ȣ�� �ùٸ��� �ʽ��ϴ�.");
				}else {//R�̶��(���̵�� ��й�ȣ�� �´ٸ�) ������
					if (CommonDAO.getInstance().updateUserInfo(umvo)) {
						JOptionPane.showMessageDialog(cuiv, "ȸ�������� �����Ǿ����ϴ�.");
					}//end if
					}
				}//end else
			
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(cuiv, "DB���� ������ �߻��߽��ϴ�.");
			e.printStackTrace();
			}//end catch
		}//end else
	}//modifyUser	

	public void removeUser() {
		new RemoveUserView(emv, uivo.getId());
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
	
}
