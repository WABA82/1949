package user.common.controller;

import static javax.swing.JOptionPane.showMessageDialog;

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
		
		//전화번호 검증 -빼면 11자리(010-0000-0000)
		try {
			String tel2=tel.replaceAll("-", "");
		
		if(tel2.length()!=11) {
			JOptionPane.showMessageDialog(fiv, "올바른 전화번호 형식이 아닙니다\n예) 010-0000-0000");
			return;
			
		}else {//11자리라면  :-있는지 확인하고, - - 사이 번호 자릿수 검증
			//-필수입력
			if(!(tel.contains("-"))) {
				JOptionPane.showMessageDialog(fiv, "올바른 전화번호 형식이 아닙니다\n예) 010-0000-0000");
				return;
			}//end if
			
			//010외에는 되지 않도록
			if(!(tel.substring(0, tel.indexOf("-")).equals("010"))) {
				JOptionPane.showMessageDialog(fiv, "올바른 전화번호 형식이 아닙니다\n예) 010-0000-0000");
				return;
			}//end if
			
			
			//010-0000-0000
			//첫-전까지자릿수3자리 , --사이 4자리, 나머지4자리(첫번째검증으로..)
			if(!(tel.substring(0, tel.indexOf("-")).length()==3)
				||!(tel.substring(tel.indexOf("-")+1, tel.lastIndexOf("-")).length()==4)) {
					 
					JOptionPane.showMessageDialog(fiv, "올바른 전화번호 형식이 아닙니다\n예) 010-0000-0000");
					return;
			}//end if
			
				Integer.parseInt(tel2);
			}//end else
		} catch (NumberFormatException nfe) {
			showMessageDialog(fiv, "전화번호에 문자열이 들어있습니다.");
			return;
		} //end catch	

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
				fiv.dispose();
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
