package user.common.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;

import javax.swing.JOptionPane;
import javax.swing.JPasswordField;

import user.common.view.RemoveUserView;
import user.dao.CommonDAO;

public class RemoveUserController extends WindowAdapter implements ActionListener {
	
	private RemoveUserView ruv;
	private String id;

	public RemoveUserController(RemoveUserView ruv, String id) {
		this.ruv=ruv;
		this.id=id;
	}
	public void removeUser(String id) {
		JPasswordField jpPass1=ruv.getJpfPass1();
		JPasswordField jpPass2=ruv.getJpfPass1();
		
		String pass1=new  String(jpPass1.getPassword()).trim();
		String pass2=new  String(jpPass2.getPassword()).trim();
		
		//if(pass1.equals(pass2)) {
			try {//유효성검증
				if(CommonDAO.getInstance().deleteUserInfo(id))//id입력
				JOptionPane.showMessageDialog(ruv, "정상탈퇴처리되었습니다.");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		//}
	}
	
	public void checkPass(String pass1,String pass2) {
		
	}
	
	
	@Override
	public void actionPerformed(ActionEvent ae) {
		if(ae.getSource()==ruv.getJbDelete()) {
			//checkPass();넣어주기
			removeUser(id);
		}
		if(ae.getSource()==ruv.getJbClose()) {
			ruv.dispose();
		}
	}
	@Override
	public void windowClosing(WindowEvent e) {
	}

}
