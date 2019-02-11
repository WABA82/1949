package user.common.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

import user.common.view.FindIdView;
import user.common.view.FindPassView;
import user.common.view.LoginView;
import user.common.view.SignUpView;

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
		
	}//¹öÆ°
	
	@Override
	public void windowClosing(WindowEvent we) {
		lv.dispose();
	}
	
	@Override
	public void windowClosed(WindowEvent e) {
		
	}
	
	public void login() {
		
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
