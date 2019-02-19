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
	private String id;
	private LoginView lv;
	private SearchAddrController sac;
	private SearchAddrView sav;
	
	public ChangeUserInfoController(ChangeUserInfoView cuiv, UserInfoVO uivo) {
		this.cuiv=cuiv;
		this.uivo=uivo;
		this.addrSeq=uivo.getSeq();
	}
	

	public void modifyUser() {
		String originPass="";
		
		String id=cuiv.getJtfId().getText().trim();
		String name= cuiv.getJtfName().getText().trim();
		String INPUToriginPass=new String(cuiv.getJpfOriginalPass().getPassword()).trim();		
		String newPass1=new String(cuiv.getJpfNewPass1().getPassword()).trim();
		String newPass2=new String(cuiv.getJpfNewPass2().getPassword()).trim();
		String tel=cuiv.getJtfTel().getText().trim();
		String email=cuiv.getJtfEmail().getText().trim();
		String addrDetail=cuiv.getJtfAddr2().getText().trim();
		
		
		
		
		System.out.println(addrSeq);
		
		
		UserModifyVO umvo=new UserModifyVO(id, name, newPass1, tel, addrSeq, addrDetail, email);////////여기까지0218
		

		
		try {
			originPass=CommonDAO.getInstance().selectFindPass(id);//아이디를 받아 DB에서 비밀번호 가져오기
			if (CommonDAO.getInstance().updateUserInfo(umvo)) {
				if(!INPUToriginPass.equals(originPass)) {//비밀번호 비교
					JOptionPane.showMessageDialog(cuiv, "비밀번호가 올바르지 않습니다.");
				}else {
					if(!newPass1.equals(newPass2)) {
						JOptionPane.showMessageDialog(cuiv, "비밀번호확인과 비밀번호가 일치하지 않습니다.");
					}else {
						JOptionPane.showMessageDialog(cuiv, "회원정보가 수정되었습니다.");
						return;					
				}
			}
		}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(cuiv, "DB에서 문제가 발생했습니다.");
			e.printStackTrace();
		}
	}//modifyUser	
	public void removeUser() {
		
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
			//id=회원정보수정페이지의 id얻기
			JTextField jtfId= cuiv.getJtfId();
			String id=jtfId.getText().trim();
			new RemoveUserView(emv, id);/////////////id????
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
