package user.common.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import user.common.view.FindIdView;
import user.common.vo.FindIdVO;
import user.dao.CommonDAO;

public class FindIdController extends WindowAdapter implements ActionListener {

	private FindIdView fiv;
	
	public FindIdController(FindIdView fiv) {
		this.fiv = fiv;
	}
	
	public void checkUser(FindIdVO fivo) {
		String userId="";

		CommonDAO c_dao=CommonDAO.getInstance();
	
		try {
			userId=c_dao.selectFindId(fivo);
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(fiv, "DB에서 문제가 발생했습니다.");
			e.printStackTrace();
		}//end catch
		
//		return userId;
	}
	
	
	@Override
	public void actionPerformed(ActionEvent ae) {
		String name= fiv.getJtfName().getText().trim();
		String tel= fiv.getJtfTel().getText().trim();
		
		FindIdVO fivo = new FindIdVO(name, tel);
		if(ae.getSource()==fiv.getJbValidate()) {
			if(name==null||tel.equals("")) {
				JOptionPane.showMessageDialog(fiv, "이름을 입력하세요.");
				fiv.getJtfName().requestFocus();
				return;
			}
			if(tel==null||tel.equals("")) {
				JOptionPane.showMessageDialog(fiv, "연락처를 입력하세요.");
				fiv.getJtfTel().requestFocus();
				return;
			}
		}
	}
		
	@Override
	public void windowClosing(WindowEvent e) {
		fiv.dispose();
	}
}
