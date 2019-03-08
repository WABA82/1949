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

import user.common.view.SetNewPassView;
import user.common.vo.SetPassVO;
import user.dao.CommonDAO;
import user.util.UserLog;

public class SetNewPassController extends WindowAdapter implements ActionListener,KeyListener {

	private SetNewPassView snpv;
	private String id;

	public SetNewPassController(SetNewPassView snpv, String id) {
		this.snpv = snpv;
		this.id = id;
	}

	public boolean checkPass(String pass) { // ��й�ȣ ����, �ִ� 12�ڸ�, �빮�� �ҹ��� Ư������ ����
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
			JOptionPane.showMessageDialog(snpv, "�� ��й�ȣ�� �Է����ּ���");
			jtfPass1.requestFocus();
			return;
		}//end if
		if (changePass2 == null || changePass2.equals("")) {
			JOptionPane.showMessageDialog(snpv, "�� ��й�ȣ Ȯ���� �Է����ּ���");
			jtfPass2.requestFocus();
			return;
		}//end if

		SetPassVO spvo = new SetPassVO(id, changePass1);

		try {// ��й�ȣ ����

			if (!changePass1.equals(changePass2)) {// ����й�ȣȮ���� �ٸ���
				JOptionPane.showMessageDialog(snpv, "��й�ȣȮ�ΰ� ��й�ȣ�� ��ġ���� �ʽ��ϴ�.");
			} else {// ����й�ȣ�� Ȯ���� ���ٸ� ����
					if (!checkPass(changePass1)) {//password�� ���Ե� ���ڰ� ���ٸ�
						JOptionPane.showMessageDialog(snpv, "��й�ȣ�� Ȯ�����ּ���\n�빮��,�ҹ���,Ư������ �������� �Է����ּ���.");
						return;
					} else {//���Ե� ���ڰ� �ִٸ�
						if (CommonDAO.getInstance().updatePass(spvo)) {
							JOptionPane.showMessageDialog(snpv, "��й�ȣ�� �����Ǿ����ϴ�.");
							snpv.dispose();
							new UserLog().sendLog(id, "ȸ�� ��й�ȣ�� �����Ͽ����ϴ�.");
						return;
						} // end if
				}//end else

			} // end else
		}catch(SQLException e){
		JOptionPane.showMessageDialog(snpv, "DB���� ������ �߻��߽��ϴ�.");
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
		if(e.getKeyCode()==10) {
			changePass();
			}
	}
	@Override
	public void keyTyped(KeyEvent e) {}


	@Override
	public void keyReleased(KeyEvent e) {}
}
