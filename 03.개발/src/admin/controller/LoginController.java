package admin.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JOptionPane;

import admin.view.AdminMainView;
import admin.view.LoginView;

public class LoginController extends WindowAdapter implements ActionListener {
	
	private LoginView lv;
	private int cnt;

	public LoginController(LoginView lv) {
		this.lv = lv;
		cnt = 0;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == lv.getJbLogin()) {
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
			new AdminMainView(lv);
		} else {
			cnt++;
			if(cnt > 4) {
				msgCenter("5ȸ �̻� �α��� �����ϼ̽��ϴ�.\n���α׷��� �����մϴ�.");
				System.exit(-1);
			}
			msgCenter("�Է������� ���� �ʽ��ϴ�.\n"+cnt+"/5�� Ʋ���̽��ϴ�.\n5�� �̻� �α��� ���н� ���α׷��� ����˴ϴ�.");
			lv.getJtfId().setText("");
			lv.getJpfPass().setText("");
			lv.getJtfId().requestFocus();
		}
	}
	
	@Override
	public void windowClosing(WindowEvent e) {
		lv.dispose();
	}
}
