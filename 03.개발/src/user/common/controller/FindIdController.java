package user.common.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;

import javax.swing.JOptionPane;
import javax.swing.JTextField;

import user.common.view.FindIdView;
import user.common.vo.FindIdVO;
import user.dao.CommonDAO;

public class FindIdController extends WindowAdapter implements ActionListener {

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
			}
			
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(fiv, "DB���� ������ �߻��߽��ϴ�.");
			e.printStackTrace();
		}//end catch
		

	}
	
	
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
}
