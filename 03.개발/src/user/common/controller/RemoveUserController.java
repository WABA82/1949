package user.common.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;

import javax.swing.JOptionPane;
import javax.swing.JPasswordField;

import user.common.view.ChangeUserInfoView;
import user.common.view.RemoveUserView;
import user.dao.CommonDAO;
import user.er.view.ErMainView;

public class RemoveUserController extends WindowAdapter implements ActionListener {
	
	private ChangeUserInfoView cuiv;
	private RemoveUserView ruv;
	private String id;
	

	public RemoveUserController(ChangeUserInfoView cuiv ,RemoveUserView ruv, String id) {//view받기....
		this.ruv=ruv;
		this.id=id;
		this.cuiv=cuiv;
	}
	

	public void removeUser(String id) {
	
		String pass1=new  String(ruv.getJpfPass1().getPassword()).trim();
		String pass2=new  String(ruv.getJpfPass2().getPassword()).trim();
		
		if(pass1==null||pass1.equals("")) {
			JOptionPane.showMessageDialog(ruv, "비밀번호를 입력해주세요");
			ruv.getJpfPass1().requestFocus();
			return;
		}
			if(pass2==null||pass2.equals("")) {
				JOptionPane.showMessageDialog(ruv, "비밀번호 확인을 입력해주세요");
			ruv.getJpfPass2().requestFocus();
			return;
			}//end if
			
		try {
			if(pass1.equals(pass2)) {
				if(!(CommonDAO.getInstance().login(id, pass1)).equals("R")) {
					JOptionPane.showMessageDialog(ruv, "비밀번호가 올바르지 않습니다.");
				}else {
					if(CommonDAO.getInstance().deleteUserInfo(id)) {
						JOptionPane.showMessageDialog(ruv, "정상탈퇴처리되었습니다.");
						ruv.dispose();
						cuiv.dispose();
											
					}//end if
				}//end else
			}else {
				JOptionPane.showMessageDialog(ruv, "비밀번호확인과 비밀번호가 일치하지 않습니다.");
			}//end else
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(ruv, "DB에서 문제가 발생했습니다.");
			e.printStackTrace();
		}
	}//removeUser	
	
	
	
	public void checkPass(String pass1,String pass2) {
		
	
		
		
		
	}
	@Override
	public void actionPerformed(ActionEvent ae) {
		if(ae.getSource()==ruv.getJbDelete()) {
			//checkPass();
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
