package user.common.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;

import javax.swing.JOptionPane;
import javax.swing.JTextField;

import user.common.view.ChangeUserInfoView;
import user.common.view.LoginView;
import user.common.view.RemoveUserView;
import user.common.view.SearchAddrView;
import user.common.vo.UserInfoVO;
import user.common.vo.UserModifyVO;
import user.dao.CommonDAO;
import user.er.view.ErMainView;

public class ChangeUserInfoController extends WindowAdapter implements ActionListener {

	private ChangeUserInfoView cuiv;
	private UserInfoVO uivo;
	private String addrSeq;
	private ErMainView emv;
	private SearchAddrView sav;
	
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
		
		if(InputOriginPass==null||InputOriginPass.equals("")) {
			JOptionPane.showMessageDialog(cuiv, "��й�ȣ�� �Է����ּ���.");
			cuiv.getJpfOriginalPass().requestFocus();
			return;
		}

		//��й�ȣ�� �� �������ʿ�¾��µ�.......
		// ����vo ������й�ȣnull.... 
		UserModifyVO umvo=new UserModifyVO(id, name, newPass1, tel, addrSeq, addrDetail, email);
		//0220 �ּҼ����ϸ� ���ּ��ʱ�ȭ�ϱ�!!
		//ercontroller vogetter�Ἥ���̵�������!!	

		try {//��й�ȣ ����
			if(!newPass1.equals(newPass2)) {
				JOptionPane.showMessageDialog(cuiv, "��й�ȣȮ�ΰ� ��й�ȣ�� ��ġ���� �ʽ��ϴ�.");
			}else {
				if(!(CommonDAO.getInstance().login(id, InputOriginPass)).equals("R")) {
					JOptionPane.showMessageDialog(cuiv, "��й�ȣ�� �ùٸ��� �ʽ��ϴ�.");
				}else {//R�̶�� ������
					if (CommonDAO.getInstance().updateUserInfo(umvo)) {
						JOptionPane.showMessageDialog(cuiv, "ȸ�������� �����Ǿ����ϴ�.");
					}//end if
				}//end else
			}//end else
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(cuiv, "DB���� ������ �߻��߽��ϴ�.");
			e.printStackTrace();
		}
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
