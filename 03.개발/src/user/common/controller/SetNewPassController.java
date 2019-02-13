package user.common.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;

import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import user.common.view.FindPassView;
import user.common.view.LoginView;
import user.common.view.SetNewPassView;
import user.common.vo.FindPassVO;
import user.common.vo.SetPassVO;
import user.dao.CommonDAO;

public class SetNewPassController extends WindowAdapter implements ActionListener {

	private SetNewPassView snpv;
	private String id;

	public SetNewPassController(SetNewPassView snpv, String id) {
		this.snpv = snpv;
		this.id = id;
	}

	public void changePass() {
		JPasswordField jtfPass1 = snpv.getJpfPass1();
		JPasswordField jtfPass2 = snpv.getJpfPass2();

		String changePass1 = new String(jtfPass1.getPassword()).trim();
		String changePass2 = new String(jtfPass2.getPassword()).trim();

		if (changePass1 == null || changePass1.equals("")) {
			JOptionPane.showMessageDialog(snpv, "새 비밀번호를 입력해주세요");
			jtfPass1.requestFocus();
			return;
		}
		if (changePass2 == null || changePass2.equals("")) {
			JOptionPane.showMessageDialog(snpv, "새 비밀번호 확인을 입력해주세요");
			jtfPass2.requestFocus();
			return;
		}

		if (changePass1.equals(changePass2)) {
			
			SetPassVO spvo = new SetPassVO(id, changePass1);
			
			try {
				if (CommonDAO.getInstance().updatePass(spvo)) {
					
					JOptionPane.showMessageDialog(snpv, "새로운 비밀번호로 변경되었습니다.");
					return;
				}
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(snpv, "DB에서 문제가 발생했습니다.");
				e.printStackTrace();
			}
		} else {
			JOptionPane.showMessageDialog(snpv, "새 비밀번호와 비밀번호 확인이 일치하지 않습니다.");
		}

	}// changePass

	@Override
	public void actionPerformed(ActionEvent ae) {
		if (ae.getSource() == snpv.getJbChange()) {
			changePass();
		}
		if (ae.getSource() == snpv.getJbClose()) {
			snpv.dispose();
		}
	}

	@Override
	public void windowClosing(WindowEvent e) {
		snpv.dispose();
	}
}
