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
			JOptionPane.showMessageDialog(fiv, "이름을 입력하세요.");
			jtfName.requestFocus();
			return;
		}
		if(tel==null||tel.equals("")) {
			JOptionPane.showMessageDialog(fiv, "연락처를 입력하세요.");
			jtfTel.requestFocus();
			return;
		}
		FindIdVO fivo = new FindIdVO(name, tel);
		String searchId="";
		
		try {
			searchId=CommonDAO.getInstance().selectFindId(fivo);//DB로그인 인증
			
			if(searchId.equals("")) {
				JOptionPane.showMessageDialog(fiv, "이름이나 연락처를 확인하세요.");
				jtfName.setText("");
				jtfTel.setText("");
				jtfName.requestFocus();
			}else {
				JOptionPane.showMessageDialog(fiv, "입력하신 정보가 일치합니다.");
				JOptionPane.showMessageDialog(fiv, "회원님의 아이디는 "+searchId+" 입니다.");
			}
			
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(fiv, "DB에서 문제가 발생했습니다.");
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
