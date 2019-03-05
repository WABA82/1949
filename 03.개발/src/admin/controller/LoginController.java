package admin.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JOptionPane;

import admin.view.AdminMainView;
import admin.view.LoginView;

public class LoginController extends WindowAdapter implements ActionListener, KeyListener {
	
	private LoginView lv;
	private Map<String, Integer> user;

	public LoginController(LoginView lv) {
		this.lv = lv;
		user = new HashMap<String, Integer>();
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_TAB) {
			lv.getJpfPass().requestFocus();
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == lv.getJbLogin() || e.getSource() == lv.getJtfId() 
				|| e.getSource()  == lv.getJpfPass()) {
			login();
		}
	}
	
	private void msgCenter(String msg) {
		JOptionPane.showMessageDialog(lv, msg);
	}
	
	public void login() {
		
		String id = lv.getJtfId().getText().trim();
		String pass = new String(lv.getJpfPass().getPassword()).trim();
		
		if ("".equals(id)) {
			msgCenter("아이디를 입력해주세요.");
			lv.getJtfId().requestFocus();
			return;
		}
		if ("".equals(pass)) {
			msgCenter("비밀번호를 입력해주세요.");
			lv.getJpfPass().requestFocus();
			return;
		}
		
		if ("admin".equals(id) && "4321".equals(pass)) {
			user.clear();
			new AdminMainView(lv);
		} else {
			if (!user.containsKey(id)) {
				user.put(id, 1);
			} else {
				if ((Integer)user.get(id).intValue() > 3) {
					msgCenter(id+"님 5회 이상 로그인 실패하셨습니다.\n프로그램을 종료합니다.");
					System.exit(-1);
				}
				user.put(id, (Integer)user.get(id)+1);
			}
			msgCenter(id+"님께서 입력하신 비밀번호가 틀립니다.\n"+user.get(id)+"/5번 틀리셨습니다.\n5번 이상 실패시 프로그램이 종료됩니다.");
			lv.getJtfId().setText("");
			lv.getJpfPass().setText("");
			lv.getJtfId().requestFocus();
		}
	}
	
	@Override
	public void windowClosing(WindowEvent e) {
		lv.dispose();
	}

	@Override
	public void keyTyped(KeyEvent e) {}

	@Override
	public void keyReleased(KeyEvent e) {}
}
