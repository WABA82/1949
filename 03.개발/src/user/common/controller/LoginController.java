package user.common.controller;

import java.awt.event.ActionEvent;

import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import user.common.view.FindIdView;
import user.common.view.FindPassView;
import user.common.view.LoginView;
import user.common.view.SignUpView;
import user.common.vo.EeMainVO;
import user.dao.CommonDAO;
import user.ee.view.EeMainView;
import user.er.view.ErMainView;

public class LoginController extends WindowAdapter implements ActionListener, MouseListener {
	private LoginView lv;
	private EeMainVO emv;
	public LoginController(LoginView lv) {
		this.lv = lv;
	}
	@Override
	public void mouseClicked(MouseEvent me) {
		if(me.getSource()==lv.getJlSignUp()) {
			signUp();
		}else if(me.getSource()==lv.getJlFindID()) {
			findId();
		}else if(me.getSource()==lv.getJlFindPass()) {
			findPass();
		}//end else
	}//mouseClicked

	@Override
	public void mousePressed(MouseEvent e) {

	}
	@Override
	public void actionPerformed(ActionEvent ae) {
		
		if(ae.getSource()==lv.getJbLogin()) {
			try {
				login();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}//end if
		
	}//��ư
	
	@Override
	public void windowClosing(WindowEvent we) {
		lv.dispose();
	}
	
	@Override
	public void windowClosed(WindowEvent e) {
		System.exit(0);
	}
	
	public void login() throws SQLException {
		String id=lv.getJtfId().getText().trim();
		String pass=new String(lv.getJpfPass().getPassword());
		
		if(id==null||id.equals("")) {
			JOptionPane.showMessageDialog(lv,"���̵� �Է��ϼ���");
			lv.getJtfId().requestFocus();
			return;
		}//end if
		if(pass==null||pass.equals("")) {
			JOptionPane.showMessageDialog(lv,"��й�ȣ�� �Է��ϼ���");
			lv.getJpfPass().requestFocus();
			return;
		}
		System.out.println(id + pass);
		String userType="";
		CommonDAO c_dao = CommonDAO.getInstance();
		
		userType=c_dao.login(id, pass);
		if(userType.equals("E")) {
			System.out.println("E");
			new EeMainView(emv);//������ �ȿ� �� emvo....��� �����
		}else {
			//new ErMainView();//DAO�� selecteemain...��� �����
		}
		
	}//login
	
	public void signUp() {
		new SignUpView(lv);
	}//signUp
	
	public void findId() {
		new FindIdView(lv);
	}//findId
	
	public void findPass() {
		new FindPassView(lv);
	}//findPass
	@Override
	public void mouseReleased(MouseEvent e) {}
	@Override
	public void mouseEntered(MouseEvent e) {}
	@Override
	public void mouseExited(MouseEvent e) {}
}
