package user.common.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import user.common.view.LoginView;

public class LoginController extends WindowAdapter implements ActionListener, MouseListener {
	private LoginView lv;
	public LoginController(LoginView lv) {
		this.lv = lv;
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		lv.dispose();
	}

	@Override
	public void mousePressed(MouseEvent e) {

	}
	@Override
	public void actionPerformed(ActionEvent e) {}
	
	@Override
	public void windowClosing(WindowEvent we) {
		
	}
	@Override
	public void mouseReleased(MouseEvent e) {}
	@Override
	public void mouseEntered(MouseEvent e) {}
	@Override
	public void mouseExited(MouseEvent e) {}
}
