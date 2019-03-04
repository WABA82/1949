package user.common.controller;

import static javax.swing.JOptionPane.showMessageDialog;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;

import javax.swing.JOptionPane;
import javax.swing.JTextField;

import user.common.view.FindIdView;
import user.common.vo.FindIdVO;
import user.dao.CommonDAO;

public class FindIdController extends WindowAdapter implements ActionListener,KeyListener{

	private FindIdView fiv;
	
	public FindIdController(FindIdView fiv) {
		this.fiv = fiv;
	}
	
	public void checkUser() {
		JTextField jtfName=fiv.getJtfName();
		JTextField jtfTel=fiv.getJtfTel();
		
		String name=jtfName.getText().trim();
		String tel=jtfTel.getText().trim();
		
		if(name==null||name.equals("")) {
			JOptionPane.showMessageDialog(fiv, "�̸��� �Է��ϼ���.");
			jtfName.requestFocus();
			return;
		}
		if(tel==null||tel.equals("")) {
			JOptionPane.showMessageDialog(fiv, "����ó�� �Է��ϼ���.");
			jtfTel.requestFocus();
			return;
		}
		
		//��ȭ��ȣ ���� -���� 11�ڸ�(010-0000-0000)
		try {
			String tel2=tel.replaceAll("-", "");
		
		if(tel2.length()!=11) {
			JOptionPane.showMessageDialog(fiv, "�ùٸ� ��ȭ��ȣ ������ �ƴմϴ�\n��) 010-0000-0000");
			return;
			
		}else {//11�ڸ����  :-�ִ��� Ȯ���ϰ�, - - ���� ��ȣ �ڸ��� ����
			//-�ʼ��Է�
			if(!(tel.contains("-"))) {
				JOptionPane.showMessageDialog(fiv, "�ùٸ� ��ȭ��ȣ ������ �ƴմϴ�\n��) 010-0000-0000");
				return;
			}//end if
			
			//010�ܿ��� ���� �ʵ���
			if(!(tel.substring(0, tel.indexOf("-")).equals("010"))) {
				JOptionPane.showMessageDialog(fiv, "�ùٸ� ��ȭ��ȣ ������ �ƴմϴ�\n��) 010-0000-0000");
				return;
			}//end if
			
			
			//010-0000-0000
			//ù-�������ڸ���3�ڸ� , --���� 4�ڸ�, ������4�ڸ�(ù��°��������..)
			if(!(tel.substring(0, tel.indexOf("-")).length()==3)
				||!(tel.substring(tel.indexOf("-")+1, tel.lastIndexOf("-")).length()==4)) {
					 
					JOptionPane.showMessageDialog(fiv, "�ùٸ� ��ȭ��ȣ ������ �ƴմϴ�\n��) 010-0000-0000");
					return;
			}//end if
			
				Integer.parseInt(tel2);
			}//end else
		} catch (NumberFormatException nfe) {
			showMessageDialog(fiv, "��ȭ��ȣ�� ���ڿ��� ����ֽ��ϴ�.");
			return;
		} //end catch	

		FindIdVO fivo = new FindIdVO(name, tel);
		String searchId="";
		
		try {
			searchId=CommonDAO.getInstance().selectFindId(fivo);//DB�α��� ����
			
			if(searchId.equals("")) {
				JOptionPane.showMessageDialog(fiv, "�̸��̳� ����ó�� Ȯ���ϼ���.");
				jtfName.setText("");
				jtfTel.setText("");
				jtfName.requestFocus();
			}else {
				JOptionPane.showMessageDialog(fiv, "�Է��Ͻ� ������ ��ġ�մϴ�.");
				JOptionPane.showMessageDialog(fiv, "ȸ������ ���̵�� "+searchId+" �Դϴ�.");
				fiv.dispose();
			}
			
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(fiv, "DB���� ������ �߻��߽��ϴ�.");
			e.printStackTrace();
		}//end catch
		

	}//checkUser
	
	
	@Override
	public void actionPerformed(ActionEvent ae) {
		
		if(ae.getSource()==fiv.getJbValidate()) {
			checkUser();
		}
		if(ae.getSource()==fiv.getJbClose()) {
			fiv.dispose();
		}
	}
		
	@Override
	public void windowClosing(WindowEvent e) {
		fiv.dispose();
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode()==KeyEvent.VK_ENTER) {
		checkUser();
		}
	}
	
	
	@Override
	public void keyTyped(KeyEvent e) {}
	@Override
	public void keyReleased(KeyEvent e) {}
}
