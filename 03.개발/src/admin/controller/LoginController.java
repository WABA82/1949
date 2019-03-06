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
			msgCenter("���̵� �Է����ּ���.");
			lv.getJtfId().requestFocus();
			return;
		}
		if ("".equals(pass)) {
			msgCenter("��й�ȣ�� �Է����ּ���.");
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
					msgCenter(id+"�� 5ȸ �̻� �α��� �����ϼ̽��ϴ�.\n���α׷��� �����մϴ�.");
					System.exit(-1);
				}
				user.put(id, (Integer)user.get(id)+1);
			}
			msgCenter(id+"�Բ��� �Է��Ͻ� ��й�ȣ�� Ʋ���ϴ�.\n"+user.get(id)+"/5�� Ʋ���̽��ϴ�.\n5�� �̻� ���н� ���α׷��� ����˴ϴ�.");
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
