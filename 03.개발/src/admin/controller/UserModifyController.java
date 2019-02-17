package admin.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.sql.SQLException;
import java.util.List;

import javax.swing.JOptionPane;

import admin.dao.AdminDAO;
import admin.util.AdminUtil;
import admin.view.AdminMgMtView;
import admin.view.SearchAddrView;
import admin.view.UserModifyView;
import admin.vo.EeDeleteVO;
import admin.vo.EeInfoVO;
import admin.vo.UserInfoVO;
import admin.vo.UserModifyVO;

public class UserModifyController extends WindowAdapter implements ActionListener {

	private UserModifyView umv;
	private AdminMgMtView ammv;
	private AdminMgMtController ammc;
	private UserInfoVO uivo;
	private String addrSeq;
	private AdminUtil au;
	
	private Socket client;
	private DataOutputStream dos;
	private DataInputStream dis;
	private FileInputStream fis;
	private FileOutputStream fos;
	
	private boolean userTypeFlag;
	
	public UserModifyController(UserModifyView umv, AdminMgMtView ammv, UserInfoVO uivo, AdminMgMtController ammc) {
		this.umv = umv;
		this.ammv = ammv;
		this.uivo = uivo;
		this.addrSeq = uivo.getAddrSeq();
		this.ammc = ammc;
		au = new AdminUtil();
	}
	
	public void msgCenter(String msg) {
		JOptionPane.showMessageDialog(umv, msg);
	}
	
	public void remove() throws UnknownHostException, IOException {
		
		String id = umv.getJtfId().getText().trim();
		
		String userType = (String)umv.getJcbUser().getSelectedItem();
		
		switch(JOptionPane.showConfirmDialog(umv, "회원정보로 등록된 기록도 모두 삭제됩니다.\n정말 삭제하시겠습니까?")) {
		case JOptionPane.OK_OPTION:
			try {
				
				List<String> imgList = AdminDAO.getInstance().selectUserImgs(id, userType);

				// 유저 데이터와 함께 이미지도 삭제 
				File adminImg = null;
				if (userType.equals("일반")) {
					String extName = AdminDAO.getInstance().selectEeExt(id);
					
					if (!(extName.trim().equals(""))) {
						au.deleteFile(extName, "ext", client, dos, dis);
					}
					
					for(String imgName : imgList) {
						adminImg = new File("C:/dev/1949/03.개발/src/admin/img/ee/"+imgName);
						adminImg.delete();
						au.deleteFile(imgName, "ee", client, dos, dis);
					}
				} else if (userType.equals("기업")) {
					for(String imgName : imgList) {
						adminImg = new File("C:/dev/1949/03.개발/src/admin/img/co/"+imgName);
						adminImg.delete();
						au.deleteFile(imgName, "co", client, dos, dis);
					}
				}
				
				AdminDAO.getInstance().deleteUser(id); // 유저데이터를 DB에서 삭제

				umv.dispose();
				msgCenter("회원정보가 삭제되었습니다.");
				au.sendLog(umv.getJtfId().getText()+" 회원 정보 삭제");
				ammc.setUser();
			} catch (SQLException e) {
				msgCenter("DB에 문제가 발생했습니다.");
				e.printStackTrace();
			}
			break;
		case JOptionPane.NO_OPTION:
		case JOptionPane.CANCEL_OPTION:
		}
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
		
		if(!(pass.equals("") || pass.length() > 13)) {

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
	
	public boolean checkSsn(String input) {
		boolean flag = false;
		
		String ssn = input.replaceAll("-", "");
		
		if (ssn.length() != 13) { // 13자리('-'제외)
			flag = false;
			return flag;
		}
		
		int[] validVal = new int[12];
		int sumOfValidVal = 0;
		int j = 2;
		
		for(int i=0; i<12; i++) {
			
			if(j>9) {
				j = 2;
			}
			
			validVal[i] = Character.getNumericValue(ssn.charAt(i))*j;
			j++;
			
			sumOfValidVal += validVal[i];
		}
		
		if ((11 - (sumOfValidVal%11))%10 == Character.getNumericValue(ssn.charAt(12))) {
			flag = true;
		}
		/*-- '880101-1234567'
		-- 각 자리에 지정한 수를 곱함
		--  234567 892345
		-- 마지막 주민번호 한자리가 검증 값
		-- 각 자리별 결과를 다 더한 후 11로 나눈 나머지를 구함
		-- 그 결과를 11에서 뺀다
		-- 그 결과를 10으로 나눈 나머지를 구함
		-- 최종 결과값이 주민번호 최종끝자리와 같으면 유효
		-- 같지 않으면 무효*/
		
		return flag;
	}
	
	public boolean checkTel(String input) {
		boolean flag = false;
		
		String tel = input.replaceAll("-", ""); 
		
		try {
			Integer.parseInt(tel); // 입력값이 정수인지 판단
		} catch (NumberFormatException npe) {
			return flag;
		}
		
		if(tel.length() != 11) { // 000-0000-0000 11자리 수가 아니라면
			return flag;
		} else {
			flag = true;
		}
		
		return flag;
	}
	
	public boolean checkEmail(String email) { 
		boolean flag = false;
		
		if (email.length() < 13) { // 이메일은 @,.포함 최소 14자 이상이어야 함
			flag = false;
			return flag;
		}
		
		if (email.indexOf("@") != -1 && email.indexOf(".") != -1) { // @와 .이 있다면
			flag = true;
		}
		
		return flag;
	}
	
	public void modify() throws UnknownHostException, IOException {
		UserModifyVO umvo = null;
		
		String id = umv.getJtfId().getText().trim();
		String pass = new String(umv.getJpfPass().getPassword()).trim();
		
		if(!checkPass(pass)) { // 비밀번호 검증
			msgCenter("비밀번호를 확인해주세요.\n 최대 12자리, 대소문자, 특수문자 조합으로 만들어주세요.");
			return;
		}
		
		String name = umv.getJtfName().getText().trim();
		
		String ssn = umv.getJtfSsn1().getText().trim()+"-"+umv.getJtfSsn2().getText().trim();
		
		if(!checkSsn(ssn)) { // ssn 검증
			msgCenter("올바른 주민번호가 아닙니다.\n다시 입력해주세요.");
			return;
		}
		
		String tel = umv.getJtfTel().getText().trim();
		
		if(!checkTel(tel)) { // tel 검증
			msgCenter("올바른 연락처가 아닙니다.\n예)010-0000-0000\n다시 입력해주세요.");
			return;
		}
		String addrDetail = umv.getJtfAddr2().getText().trim();
		
		// email - @ . 필수, 14자리 이상(@.포함)
		String email = umv.getJtfEmail().getText().trim();
		
		if(!checkEmail(email)) { // email 검증
			msgCenter("올바른 이메일이 아닙니다. \n예)someid@domain.com\n다시 입력해주세요.");
			return;
		}
		
		String questionType = String.valueOf(umv.getJcbQuestion().getSelectedIndex());
		String answer = umv.getJtfAnswer().getText().trim();
		String userType = umv.getJcbUser().getSelectedItem().equals("일반") ? "E" : "R";
		
		umvo = new UserModifyVO(id, pass, name, ssn, tel, addrSeq, addrDetail, email, questionType, answer, userType);
		
		try {
			if(AdminDAO.getInstance().updateUser(umvo)) { // 유저타입이 변경되었다면, 이전 등록 정보를 모두 삭제필요
				
				// 일반 사용자 - 기본 정보(이력서 사진, 이력서 파일)
				// 기업 사용자 - 기업 정보(기업 사진*4)
				if (userTypeFlag) {
					if(uivo.getUserType().equals("E")) { // E->R
						// 우선 유저의 파일이름을 조회해야 함 
						EeInfoVO eivo = AdminDAO.getInstance().selectOneEe(id, "id");
						
						// 이미지, 이력서 먼저 삭제처리 
						File imgFile = new File("C:/dev/1949/03.개발/src/admin/img/ee/"+eivo.getImg());
						imgFile.delete();
						
						au.deleteFile(eivo.getImg(), "ee", client, dos, dis);
						
						if (eivo.getExtResume() != null) {
							au.deleteFile(eivo.getExtResume(), "ext", client, dos, dis);
						}
						
						EeDeleteVO edvo = AdminDAO.getInstance().selectEDVO(id);
						// 일반 사용자였을 때 기본 정보를 삭제처리
						AdminDAO.getInstance().deleteEe(edvo);
					} else if (uivo.getUserType().equals("R")) { // R->E
						
						// 이미지 삭제 처리
						List<String> listCoImgs = AdminDAO.getInstance().selectUserImgs(id, "기업");
						
						for(String imgName : listCoImgs) {
							au.deleteFile(imgName, "co", client, dos, dis);
						}
						
						// 기업 사용자였을 때 회사 정보에 올렸던 데이터를 삭제처리
						AdminDAO.getInstance().deleteCo(id, "id");
					}
				}
				
				msgCenter("회원정보가 수정되었습니다.");
				au.sendLog(umv.getJtfId().getText()+" 회원 정보 수정");
				umv.dispose();
				UserInfoVO ulvo = AdminDAO.getInstance().selectOneUser(id);
				ammc.setUser();
				new UserModifyView(ammv, ulvo, ammc);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public String searchAddr() {
		String addr1 = "";
		new SearchAddrView(umv, this);
		
		return addr1;
	}
	
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == umv.getJbModify()) {
			try {
				modify();
			} catch (UnknownHostException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		
		if (e.getSource() == umv.getJbRemove()) {
			try {
				remove();
			} catch (UnknownHostException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		
		if (e.getSource() == umv.getJbSearchAddr()) {
			searchAddr();
		}
		
		if(e.getSource() == umv.getJbClose()) {
			umv.dispose();
		}
		
		if (e.getSource() == umv.getJcbUser()) {
			switch(JOptionPane.showConfirmDialog(umv, "유저타입을 변경하시면 등록했던 모든 기본 정보가 삭제됩니다. 계속하시겠습니까?")) {
			case JOptionPane.OK_OPTION:
				userTypeFlag = true;
				break;
			case JOptionPane.NO_OPTION:
			case JOptionPane.CANCEL_OPTION:
				userTypeFlag = false;
				umv.getJcbUser().setSelectedItem(uivo.getUserType().equals("E") ? "일반" : "기업");
				break;
			}
		}
	}
	
	@Override
	public void windowClosing(WindowEvent e) {
		umv.dispose();
	}

	public UserModifyView getUmv() {
		return umv;
	}

	public void setAddrSeq(String addrSeq) {
		this.addrSeq = addrSeq;
	}
	public String getAddrSeq() {
		return addrSeq;
	}
}
