package user.common.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
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
import user.run.LogTestChangePass;

public class SetNewPassController extends WindowAdapter implements ActionListener,KeyListener {

	private SetNewPassView snpv;
	private String id;

	public SetNewPassController(SetNewPassView snpv, String id) {
		this.snpv = snpv;
		this.id = id;
	}

	public boolean checkPass(String pass) { // 비밀번호 검증, 최대 12자리, 대문자 소문자 특수문자 조합
		boolean resultFlag = false;

		boolean lowerCaseFlag = false;
		boolean upperCaseFlag = false;
		boolean spSymbolFlag = false;

		char[] lowerCase = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r',
				's', 't', 'u', 'v', 'w', 'x', 'y', 'z' };

		char[] upperCase = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R',
				'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };

		char[] spSymbol = { '!', '@', '#', '$', '%', '^', '&', '*', '(', ')', '-', '_', '+', '=' };

		if (!(pass.equals("") || pass.length() > 12)) {

			for (int i = 0; i < pass.length(); i++) {
				for (int j = 0; j < lowerCase.length; j++) {
					if (pass.charAt(i) == lowerCase[j]) {
						lowerCaseFlag = true;
					}
				}
				for (int j = 0; j < upperCase.length; j++) {
					if (pass.charAt(i) == upperCase[j]) {
						upperCaseFlag = true;
					}
				}
				for (int j = 0; j < spSymbol.length; j++) {
					if (pass.charAt(i) == spSymbol[j]) {
						spSymbolFlag = true;
					}
				}
			}

			if (lowerCaseFlag && upperCaseFlag && spSymbolFlag) {
				resultFlag = true;
			}
		}
		return resultFlag;
	}// checkPass

	public void changePass() {
		JPasswordField jtfPass1 = snpv.getJpfPass1();
		JPasswordField jtfPass2 = snpv.getJpfPass2();

		String changePass1 = new String(jtfPass1.getPassword()).trim();
		String changePass2 = new String(jtfPass2.getPassword()).trim();

		if (changePass1 == null || changePass1.equals("")) {
			JOptionPane.showMessageDialog(snpv, "새 비밀번호를 입력해주세요");
			jtfPass1.requestFocus();
			return;
		}//end if
		if (changePass2 == null || changePass2.equals("")) {
			JOptionPane.showMessageDialog(snpv, "새 비밀번호 확인을 입력해주세요");
			jtfPass2.requestFocus();
			return;
		}//end if

		SetPassVO spvo = new SetPassVO(id, changePass1);

		try {// 비밀번호 검증

			if (!changePass1.equals(changePass2)) {// 새비밀번호확인이 다를때
				JOptionPane.showMessageDialog(snpv, "비밀번호확인과 비밀번호가 일치하지 않습니다.");
			} else {// 새비밀번호와 확인이 같다면 검증
					if (!checkPass(changePass1)) {//password에 포함된 문자가 없다면
						JOptionPane.showMessageDialog(snpv, "비밀번호를 확인해주세요\n대문자,소문자,특수문자 조합으로 입력해주세요.");
						return;
					} else {//포함된 문자가 있다면
						if (CommonDAO.getInstance().updatePass(spvo)) {
							JOptionPane.showMessageDialog(snpv, "비밀번호가 수정되었습니다.");
							snpv.dispose();
							new LogTestChangePass();
						return;
						} // end if
				}//end else

			} // end else
		}catch(SQLException e){
		JOptionPane.showMessageDialog(snpv, "DB에서 문제가 발생했습니다.");
		e.printStackTrace();
	} // end catch

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

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode()==KeyEvent.VK_ENTER) {
			changePass();
			}
	}
	@Override
	public void keyTyped(KeyEvent e) {}


	@Override
	public void keyReleased(KeyEvent e) {}
}
