package user.common.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;

import javax.swing.JOptionPane;
import javax.swing.JTextField;

import user.common.view.ChangeUserInfoView;
import user.common.vo.UserInfoVO;
import user.common.vo.UserModifyVO;
import user.dao.CommonDAO;

public class ChangeUserInfoController extends WindowAdapter implements ActionListener {

	private ChangeUserInfoView cuiv;
	private UserInfoVO uivo;
	private String addrSeq;
	
	public ChangeUserInfoController(ChangeUserInfoView cuiv, UserInfoVO uivo) {
		this.cuiv=cuiv;
		this.uivo=uivo;
	}
	
	public void modifiyUser() {
		JTextField jtfId= cuiv.getJtfId();
		JTextField jtfName= cuiv.getJtfName();
		JTextField jtfPass= cuiv.getJpfOriginalPass();
		//JTextField jtfNewPass1= cuiv.getJpfNewPass1();
		//JTextField jtfNewPass2= cuiv.getJpfNewPass2();
		JTextField jtfTel= cuiv.getJtfTel();
		JTextField jtfEmail= cuiv.getJtfEmail();
		JTextField jtfAddr2= cuiv.getJtfAddr2();
		
		String id=jtfId.getText().trim();
		String name=jtfName.getText().trim();
		String pass=jtfPass.getText().trim();
		//String newPass1=jtfNewPass1.getText().trim();
		//String newPass2=jtfNewPass2.getText().trim();		
		String tel=jtfTel.getText().trim();
		String email=jtfEmail.getText().trim();
		String addrDetail=jtfAddr2.getText().trim();
		
		
		UserModifyVO umvo=new UserModifyVO(id, name, pass, tel, addrSeq, addrDetail, email);////////여기까지
		
		try {
			if (CommonDAO.getInstance().updateUserInfo(umvo)) {
				
				JOptionPane.showMessageDialog(cuiv, "새로운 비밀번호로 변경되었습니다.");
				return;
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(cuiv, "DB에서 문제가 발생했습니다.");
			e.printStackTrace();
	
	}
	}	
	public void removeUser() {
		
	}

	
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
	}
	@Override
	public void windowClosing(WindowEvent e) {
		cuiv.dispose();
	}

}
