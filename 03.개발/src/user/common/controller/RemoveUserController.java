package user.common.controller;

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
import user.common.view.LoginView;
import user.common.view.RemoveUserView;
import user.dao.CommonDAO;
import user.util.UserLog;

public class RemoveUserController extends WindowAdapter implements ActionListener,KeyListener {
	
	private ChangeUserInfoView cuiv;
	private RemoveUserView ruv;
	private String id;
	
	private JFrame jf;
	
	public RemoveUserController(JFrame jf, ChangeUserInfoView cuiv ,RemoveUserView ruv, String id) {
		this.cuiv=cuiv;
		this.ruv=ruv;
		this.id=id;
		this.jf=jf;
	}
	

	public void removeUser(String id) {
	
		String pass1=new  String(ruv.getJpfPass1().getPassword()).trim();
		String pass2=new  String(ruv.getJpfPass2().getPassword()).trim();
		
		if(pass1==null||pass1.equals("")) {
			JOptionPane.showMessageDialog(ruv, "��й�ȣ�� �Է����ּ���");
			ruv.getJpfPass1().requestFocus();
			return;
		}
			if(pass2==null||pass2.equals("")) {
				JOptionPane.showMessageDialog(ruv, "��й�ȣ Ȯ���� �Է����ּ���");
			ruv.getJpfPass2().requestFocus();
			return;
			}//end if
			
		try {
			if(pass1.equals(pass2)) {
				if(!(CommonDAO.getInstance().login(id, pass1)).equals("R")&&
						!(CommonDAO.getInstance().login(id, pass1)).equals("E")) {
					JOptionPane.showMessageDialog(ruv, "��й�ȣ�� �ùٸ��� �ʽ��ϴ�.");
				}else {
					if(CommonDAO.getInstance().deleteUserInfo(id)) {
						JOptionPane.showMessageDialog(ruv, "����Ż��ó���Ǿ����ϴ�.");
						ruv.dispose();
						cuiv.dispose();
						jf.dispose();
						
						new LoginView();
						new UserLog().sendLog(id, "ȸ��Ż���߽��ϴ�.");
					}//end if
				}//end else
			}else {
				JOptionPane.showMessageDialog(ruv, "��й�ȣȮ�ΰ� ��й�ȣ�� ��ġ���� �ʽ��ϴ�.");
			}//end else
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(ruv, "DB���� ������ �߻��߽��ϴ�.");
			e.printStackTrace();
		}
	}//removeUser	

	@Override
	public void actionPerformed(ActionEvent ae) {
		if(ae.getSource()==ruv.getJbDelete()) {
			removeUser(id);
		}
		if(ae.getSource()==ruv.getJbClose()) {
			ruv.dispose();
		}
	}
	@Override
	public void windowClosing(WindowEvent e) {
		ruv.dispose();
	}




	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode()==10) {
			removeUser(id);
			}
	}

	@Override
	public void keyTyped(KeyEvent e) {}

	@Override
	public void keyReleased(KeyEvent e) {}

}
