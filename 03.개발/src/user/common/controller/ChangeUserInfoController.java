package user.common.controller;

import static javax.swing.JOptionPane.showMessageDialog;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import user.common.view.ChangeUserInfoView;
import user.common.view.RemoveUserView;
import user.common.view.SearchAddrView;
import user.common.vo.UserInfoVO;
import user.common.vo.UserModifyVO;
import user.common.vo.UserModifyWithoutPassVO;
import user.dao.CommonDAO;
import user.er.view.ErMainView;
import user.run.LogTestChangeUserInfo;

public class ChangeUserInfoController extends WindowAdapter implements ActionListener {

	private ChangeUserInfoView cuiv;
	private UserInfoVO uivo;
	private String addrSeq;
	private ErMainView ermv;
	
	public ChangeUserInfoController(ErMainView ermv, ChangeUserInfoView cuiv, UserInfoVO uivo) {
		this.cuiv=cuiv;
		this.uivo=uivo;
		this.addrSeq=uivo.getSeq();
		this.ermv = ermv;
	}
	
	public boolean checkPass(String pass) { // 비밀번호 검증, 최대 12자리, 대문자 소문자 특수문자 조합
		boolean resultFlag = false;
		
		boolean lowerCaseFlag = false;
		boolean upperCaseFlag = false;
		boolean spSymbolFlag = false;
		
		char[] lowerCase = { 
				'a','b','c','d','e','f','g',
				'h','i','j','k','l','m','n','o','p','q','r',
				's','t','u','v','w','x','y','z'};
		
		char[] upperCase = {
				'A','B','C','D','E','F','G',
				'H','I','J','K','L','M','N','O','P','Q','R',
				'S','T','U','V','W','X','Y','Z'};
		
		char[] spSymbol = {'!','@','#','$','%','^','&','*','(',')','-','_','+','='};
		
		if(!(pass.equals("") || pass.length() > 12)) {

			for(int i=0; i<pass.length(); i++) {
				for(int j=0; j<lowerCase.length; j++) {
					if(pass.charAt(i) == lowerCase[j]) {
						lowerCaseFlag = true;
					}
				}
				for(int j=0; j<upperCase.length; j++) {
					if(pass.charAt(i) == upperCase[j]) {
						upperCaseFlag = true;
					}
				}
				for(int j=0; j<spSymbol.length; j++) {
					if(pass.charAt(i) == spSymbol[j]) {
						spSymbolFlag = true;
					}
				}
			}
			
			if(lowerCaseFlag && upperCaseFlag && spSymbolFlag) {
				resultFlag = true;
			}
		}
		return resultFlag;
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
		
		
		//수정시 비밀번호 필수입력 입력했다면 검증
		//최대 12자리, 대문자 소문자 특수문자 조합
		if(InputOriginPass==null||InputOriginPass.equals("")) {
			JOptionPane.showMessageDialog(cuiv, "비밀번호를 입력해주세요.");
			cuiv.getJpfOriginalPass().requestFocus();
			return;
		}//end if

		//전화번호 검증 -빼면 11자리(010-0000-0000)
		try {
			String tel2=tel.replaceAll("-", "");
		
		if(tel2.length()!=11) {
			JOptionPane.showMessageDialog(cuiv, "올바른 전화번호 형식이 아닙니다\n예) 010-0000-0000");
			return;
			
		}else {//11자리라면  :-있는지 확인하고, - - 사이 번호 자릿수 검증
			//-필수입력
			if(!(tel.contains("-"))) {
				JOptionPane.showMessageDialog(cuiv, "올바른 전화번호 형식이 아닙니다\n예) 010-0000-0000");
				return;
			}//end if
			
			//010외에는 되지 않도록
			if(!(tel.substring(0, tel.indexOf("-")).equals("010"))) {
				JOptionPane.showMessageDialog(cuiv, "올바른 전화번호 형식이 아닙니다\n예) 010-0000-0000");
				return;
			}//end if
			
			
			//010-0000-0000
			//첫-전까지자릿수3자리 , --사이 4자리, 나머지4자리(첫번째검증으로..)
			if(!(tel.substring(0, tel.indexOf("-")).length()==3)
				||!(tel.substring(tel.indexOf("-")+1, tel.lastIndexOf("-")).length()==4)) {
					 
					JOptionPane.showMessageDialog(cuiv, "올바른 전화번호 형식이 아닙니다\n예) 010-0000-0000");
					return;
			}//end if
			
				Integer.parseInt(tel2);
			}//end else
		} catch (NumberFormatException nfe) {
			showMessageDialog(cuiv, "전화번호에 문자열이 들어있습니다.");
			return;
		} //end catch	
		
		
		
		//이메일 검증
		if(email.length() <14) {//@. 포함 최소 14자리 이상
			JOptionPane.showMessageDialog(cuiv, "이메일은 14자리 이상이어야합니다.");
			return;
		}else {//14자리이상이라면
	
		if(!(email.contains("@")&& email.contains("."))) {
			JOptionPane.showMessageDialog(cuiv, "올바른 이메일 형식이 아닙니다. \n예)won111@naver.com");
			return;
		
		}//end if
		}//end else


		//빈문자열 체크
		if(addrDetail==null||addrDetail.equals("")) {
			JOptionPane.showMessageDialog(cuiv, "상세주소를 입력해주세요.");
			cuiv.getJtfAddr2().requestFocus();
			return;
		}//end if
		

		//비밀번호 검증
		if(newPass1.equals("")) {//비밀번호변경하지않을시
			UserModifyWithoutPassVO umvo2=new UserModifyWithoutPassVO(id, name, tel, addrSeq, addrDetail, email);

			try {			
					if(!(CommonDAO.getInstance().login(id, InputOriginPass)).equals("R")) {//null이면
						JOptionPane.showMessageDialog(cuiv, "비밀번호가 올바르지 않습니다.");
					}else {//R이라면(아이디와 비밀번호가 맞다면) 수정됨
						if (CommonDAO.getInstance().updateUserInfoWithoutPass(umvo2)) {
							JOptionPane.showMessageDialog(cuiv, "회원정보가 수정되었습니다.");
							cuiv.dispose();
							new LogTestChangeUserInfo();
						}//end if
					}//end else
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(cuiv, "DB에서 문제가 발생했습니다.");
				e.printStackTrace();
			}
		}else {//새비밀번호입력시//////////////////////////////////////////////////
		
			UserModifyVO umvo=new UserModifyVO(id, name, newPass1, tel, addrSeq, addrDetail, email);

		try {//비밀번호 검증
			
			if(!newPass1.equals(newPass2)) {//새비밀번호확인이 다를때
				JOptionPane.showMessageDialog(cuiv, "비밀번호확인과 비밀번호가 일치하지 않습니다.");
			}else {//새 비밀번호와 비밀번호 확인이 같다면 
				if(!(CommonDAO.getInstance().login(id, InputOriginPass)).equals("R")) {//null이면(아이디와비번이다르다면)
					JOptionPane.showMessageDialog(cuiv, "비밀번호가 올바르지 않습니다.");
				}else {//R이라면(아이디와 비밀번호가 맞다면) 수정됨
								if(!checkPass(newPass1)) {
									JOptionPane.showMessageDialog(cuiv, "비밀번호를 확인해주세요\n대문자,소문자,특수문자 조합으로 입력해주세요.");
									return;
								}else {
									if (CommonDAO.getInstance().updateUserInfo(umvo)) {
								JOptionPane.showMessageDialog(cuiv, "회원정보가 수정되었습니다.");
								}//end if
						}//end else
					
					}//end else
		
			}//end else
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(cuiv, "DB에서 문제가 발생했습니다.");
			e.printStackTrace();
			}//end catch
		
		}//end else
	}//modifyUser	

	public void removeUser() {
		new RemoveUserView(cuiv,ermv, uivo.getId());
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
