package user.common.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import kr.co.sist.lunch.admin.model.LunchAdminDAO;
import kr.co.sist.lunch.admin.view.LunchMainView;
import kr.co.sist.lunch.admin.vo.AdminLoginVO;
import user.common.view.FindIdView;
import user.common.vo.FindIdVO;
import user.dao.CommonDAO;

public class FindIdController extends WindowAdapter implements ActionListener {

	private FindIdView fiv;
	
	public FindIdController(FindIdView fiv) {
		this.fiv = fiv;
	}
	
	public void checkUser() {
		String userId="";

		CommonDAO c_dao=CommonDAO.getInstance();
	
		try {
			userId=c_dao.selectFindId(fivo);
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(llv, "DB에서 문제가 발생했습니다.");
			e.printStackTrace();
		}//end catch
		
		return userId;
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
			
			//입력한 아이디와 비밀번호를 가지고 		
			String adminName=login(alvo);// DB로그인 인증을 수행한 결과를 받았음.
			
			if( adminName.equals("") ) {// 수행결과가 ""라면
				JOptionPane.showMessageDialog(llv, "아이디나 비밀번호를 확인하세요.");
				jtf.setText("");
				jpf.setText("");
				jtf.requestFocus();
			}else {
				new LunchMainView( adminName );
				LunchMainView.adminId=id; //로그인이 성공 했다면 id를
				//모든 객체에서 사용할 수 있도록 static 변수에 설정한다. 
				llv.dispose();
			}//end else
		}//end if
		}

		if(ae.getSource()==fiv.getJbValidate()) {
			
			JOptionPane.showMessageDialog(fiv, "입력하신 정보가 일치합니다.");
		}
	}
	
	
	@Override
	public void windowClosing(WindowEvent e) {
		fiv.dispose();
	}
}
