package user.common.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;

import javax.swing.JOptionPane;
import javax.swing.JTextField;

import user.common.view.ChangeUserInfoView;
import user.common.view.RemoveUserView;
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
	
	public void modifyUser() {
		JTextField jtfId= cuiv.getJtfId();
		JTextField jtfName= cuiv.getJtfName();
		JTextField jtfPass= cuiv.getJpfOriginalPass();
		JTextField jtfNewPass1= cuiv.getJpfNewPass1();
		JTextField jtfNewPass2= cuiv.getJpfNewPass2();
		JTextField jtfTel= cuiv.getJtfTel();
		JTextField jtfEmail= cuiv.getJtfEmail();
		JTextField jtfAddr2= cuiv.getJtfAddr2();
		
		String id=jtfId.getText().trim();
		String name=jtfName.getText().trim();
		String pass=jtfPass.getText().trim();//아이디와 비밀번호먼저 검증하기
		String newPass1=jtfNewPass1.getText().trim();
		String newPass2=jtfNewPass2.getText().trim();//비밀번호 검증하기		
		String tel=jtfTel.getText().trim();
		String email=jtfEmail.getText().trim();
		String addrDetail=jtfAddr2.getText().trim();
		
		
		UserModifyVO umvo=new UserModifyVO(id, name, pass, tel, addrSeq, addrDetail, email);////////여기까지
		
		try {
			if (CommonDAO.getInstance().updateUserInfo(umvo)) {
				
				JOptionPane.showMessageDialog(cuiv, "회원정보가 수정되었습니다.");
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
	public void actionPerformed(ActionEvent ae) {
		if(ae.getSource()==cuiv.getJbModify()) {
			modifyUser();
		}
			
		if(ae.getSource()==cuiv.getJbDelete()) {
			new RemoveUserView("ooo333");
		}
		if(ae.getSource()==cuiv.getJbClose()) {
			cuiv.dispose();
		}
	}
	@Override
	public void windowClosing(WindowEvent e) {
		cuiv.dispose();
	}

}
