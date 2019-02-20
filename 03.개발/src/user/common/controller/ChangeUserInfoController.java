package user.common.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;

import javax.swing.JOptionPane;
import javax.swing.JTextField;

import user.common.view.ChangeUserInfoView;
import user.common.view.LoginView;
import user.common.view.RemoveUserView;
import user.common.view.SearchAddrView;
import user.common.vo.UserInfoVO;
import user.common.vo.UserModifyVO;
import user.dao.CommonDAO;
import user.er.view.ErMainView;

public class ChangeUserInfoController extends WindowAdapter implements ActionListener {

	private ChangeUserInfoView cuiv;
	private UserInfoVO uivo;
	private String addrSeq;
	private ErMainView emv;
	private SearchAddrView sav;
	
	public ChangeUserInfoController(ChangeUserInfoView cuiv, UserInfoVO uivo) {
		this.cuiv=cuiv;
		this.uivo=uivo;
		this.addrSeq=uivo.getSeq();
	}
	
	

	/**
	 *  사용자 정보 수정
	 */
	public void modifyUser() {
		//주소변경못하게막아주기
		String id=cuiv.getJtfId().getText().trim();
		String name= cuiv.getJtfName().getText().trim();
		String InputOriginPass=new String(cuiv.getJpfOriginalPass().getPassword()).trim();		
		String newPass1=new String(cuiv.getJpfNewPass1().getPassword()).trim();
		String newPass2=new String(cuiv.getJpfNewPass2().getPassword()).trim();
		String tel=cuiv.getJtfTel().getText().trim();
		String email=cuiv.getJtfEmail().getText().trim();
		String addrDetail=cuiv.getJtfAddr2().getText().trim();
		
		if(InputOriginPass==null||InputOriginPass.equals("")) {
			JOptionPane.showMessageDialog(cuiv, "비밀번호를 입력해주세요.");
			cuiv.getJpfOriginalPass().requestFocus();
			return;
		}

		//비밀번호를 꼭 수정할필요는없는데.......
		// 수정vo 수정비밀번호null.... 
		UserModifyVO umvo=new UserModifyVO(id, name, newPass1, tel, addrSeq, addrDetail, email);
		//0220 주소수정하면 상세주소초기화하기!!
		//ercontroller vogetter써서아이디가져오기!!	

		try {//비밀번호 검증
			if(!newPass1.equals(newPass2)) {
				JOptionPane.showMessageDialog(cuiv, "비밀번호확인과 비밀번호가 일치하지 않습니다.");
			}else {
				if(!(CommonDAO.getInstance().login(id, InputOriginPass)).equals("R")) {
					JOptionPane.showMessageDialog(cuiv, "비밀번호가 올바르지 않습니다.");
				}else {//R이라면 수정됨
					if (CommonDAO.getInstance().updateUserInfo(umvo)) {
						JOptionPane.showMessageDialog(cuiv, "회원정보가 수정되었습니다.");
					}//end if
				}//end else
			}//end else
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(cuiv, "DB에서 문제가 발생했습니다.");
			e.printStackTrace();
		}
	}//modifyUser	
	
	
	
	
	
	
	public void removeUser() {
		new RemoveUserView(emv, uivo.getId());
	}

	
	
	
	@Override
	public void actionPerformed(ActionEvent ae) {
		if(ae.getSource()==cuiv.getJbModify()) {
			modifyUser();
		}

		
		if (ae.getSource() == cuiv.getJbAddr()) {
			new SearchAddrView(cuiv,null, this);
		}
			
		if(ae.getSource()==cuiv.getJbDelete()) {
			removeUser();
		
		}
		if(ae.getSource()==cuiv.getJbClose()) {
			cuiv.dispose();
		}
	}
	@Override
	public void windowClosing(WindowEvent e) {
		cuiv.dispose();
	}

	public String getAddrSeq() {
		return addrSeq;
	}

	public void setAddrSeq(String addrSeq) {
		this.addrSeq = addrSeq;
	}

	public ChangeUserInfoView getCuiv() {
		return cuiv;
	}
	
}
