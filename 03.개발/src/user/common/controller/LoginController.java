package user.common.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import user.common.view.FindIdView;
import user.common.view.FindPassView;
import user.common.view.LoginView;
import user.common.view.SignUpView;
import user.dao.CommonDAO;
import user.ee.view.EeMainView;
import user.er.view.ErMainView;

public class LoginController extends WindowAdapter implements ActionListener, MouseListener {
	private LoginView lv;
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
			login();
		}//end if
		
	}//버튼
	
	@Override
	public void windowClosing(WindowEvent we) {
		lv.dispose();
	}
	
	@Override
	public void windowClosed(WindowEvent e) {
		System.exit(0);
	}
	
	public void login() {
		String id=lv.getJtfId().getText().trim();
		String pass=new String(lv.getJpfPass().getPassword());
		
		if(id==null||id.equals("")) {
			JOptionPane.showMessageDialog(lv,"아이디를 입력하세요");
			lv.getJtfId().requestFocus();
			return;
		}//end if
		if(pass==null||pass.equals("")) {
			JOptionPane.showMessageDialog(lv,"비밀번호를 입력하세요");
			lv.getJpfPass().requestFocus();
			return;
		}
		String userType="";
		CommonDAO c_dao = CommonDAO.getInstance();
		
		userType=c_dao.login(id, pass );
		if(userType=="E") {
			new EeMainView();//생성자 안에 들어갈 emvo....등등 만들기
		}else {
			new ErMainView();//DAO의 selecteemain...등등 만들기
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
