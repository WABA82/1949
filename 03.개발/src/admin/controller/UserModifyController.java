package admin.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
import admin.vo.UserModifyVOWithOutSsn;

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
	
	/**
	 * 유저 정보를 삭제하는 메서드 
	 * @throws UnknownHostException
	 * @throws IOException
	 */
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
				au.sendLog("["+umv.getJtfId().getText()+"] 삭제");
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
	
	/**
	 * 입력 비밀번호 검증 메소드
	 * @param pass
	 * @return
	 */
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
	 * 입력 ssn 검증 메서드
	 * @param input
	 * @return
	 */
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
	
	/**
	 * 연락처 검증 메서드
	 * @param input
	 * @return
	 */
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
	
	/**
	 * 이메일 검증 메소드
	 * @param email
	 * @return
	 */
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
	
	/**
	 * 유저 정보를 수정하는 메소드
	 * @throws UnknownHostException
	 * @throws IOException
	 */
	public void modify() throws UnknownHostException, IOException {
		boolean ssnFlag = false;
		
		String id = umv.getJtfId().getText().trim();
		String pass = new String(umv.getJpfPass().getPassword()).trim();
		
		if(!checkPass(pass)) { // 비밀번호 검증
			msgCenter("비밀번호를 확인해주세요.\n 최대 12자리, 대소문자, 특수문자 조합으로 만들어주세요.");
			umv.getJpfPass().setText("");
			umv.getJpfPass().requestFocus();
			return;
		}
		
		String name = umv.getJtfName().getText().trim();
		
		if (!"".equals(umv.getJtfSsn1().getText().trim()) && !"".equals(umv.getJtfSsn2().getText().trim())) {
			ssnFlag = true;
		}
		
		String tel = umv.getJtfTel().getText().trim();
		
		if(!checkTel(tel)) { // tel 검증
			msgCenter("올바른 연락처가 아닙니다.\n예)010-0000-0000\n다시 입력해주세요.");
			umv.getJtfTel().setText("");
			umv.getJtfTel().requestFocus();
			return;
		}
		
		String addrDetail = umv.getJtfAddr2().getText().trim();
		
		if (addrDetail.trim().length() == 0) {
			msgCenter("상세주소는 필수입력사항입니다.");
			umv.getJtfAddr2().requestFocus();
			return;
		}
		
		// email - @ . 필수, 14자리 이상(@.포함)
		String email = umv.getJtfEmail().getText().trim();
		
		if(!checkEmail(email)) { // email 검증
			msgCenter("올바른 이메일이 아닙니다. \n예)someid@domain.com\n다시 입력해주세요.");
			umv.getJtfEmail().setText("");
			umv.getJtfEmail().requestFocus();
			return;
		}
		
		String questionType = String.valueOf(umv.getJcbQuestion().getSelectedIndex());
		String answer = umv.getJtfAnswer().getText().trim();
		String userType = umv.getJcbUser().getSelectedItem().equals("일반") ? "E" : "R";
		
		if (ssnFlag) { // ssn 변경 시 
			String ssn = umv.getJtfSsn1().getText().trim()+"-"+umv.getJtfSsn2().getText().trim();
			
			int age = 0;
			String gender = "";
			try {
				age = getAge(Integer.parseInt(umv.getJtfSsn1().getText().trim().substring(0, 2)));
				gender = getGender(Integer.parseInt(umv.getJtfSsn2().getText().trim().substring(0, 1)));
			} catch (NumberFormatException nfe) {
				msgCenter("주민번호는 숫자만 입력가능합니다.");
				umv.getJtfSsn1().setText("");
				umv.getJtfSsn2().setText("");
				umv.getJtfSsn1().requestFocus();
			}
			
			String encSsn = au.shaEncoding(ssn);
			
			// 업데이트 전에 주민번호 중복을 확인하는 메소드 필요
			try {
				if(AdminDAO.getInstance().selectSsn(encSsn)) {
					msgCenter("입력하신 주민번호로 가입한 유저가 존재합니다.");
					umv.getJtfSsn1().setText("");
					umv.getJtfSsn2().setText("");
					umv.getJtfSsn1().requestFocus();
					return;
				}
			} catch (SQLException e1) {
				msgCenter("DB에 문제발생");
				e1.printStackTrace();
			}
			
			UserModifyVO umvo = new UserModifyVO(id, au.shaEncoding(pass), name, encSsn, gender, tel, addrSeq, addrDetail, email, questionType, answer, userType, age);
			
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
							AdminDAO.getInstance().deleteCo(id);
						}
					}
					
					msgCenter("회원정보가 수정되었습니다.");
					au.sendLog("["+umv.getJtfId().getText()+"] 수정");
					umv.dispose();
					UserInfoVO ulvo = AdminDAO.getInstance().selectOneUser(id);
					ammc.setUser();
					new UserModifyView(ammv, ulvo, ammc);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		} else { // ssn 변경 안할 시
			
			UserModifyVOWithOutSsn umvo = new UserModifyVOWithOutSsn(id, au.shaEncoding(pass), name, tel, addrSeq, addrDetail, email, questionType, answer, userType);
			
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
							AdminDAO.getInstance().deleteCo(id);
						}
					}
					
					msgCenter("회원정보가 수정되었습니다.");
					au.sendLog("["+umv.getJtfId().getText()+"] 수정");
					umv.dispose();
					UserInfoVO ulvo = AdminDAO.getInstance().selectOneUser(id);
					ammc.setUser();
					new UserModifyView(ammv, ulvo, ammc);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		
	}
	
	/**
	 * 주소 검색하는 창을 띄우는 메서드
	 * @return
	 */
	public void searchAddr() {
		new SearchAddrView(umv, this);
	}
	
	
	
	/**
	 * 주민번호 뒷자리 1자리를 받아와 성별을 반환하는 메서드
	 * @param genderSsn
	 * @return
	 */
	public String getGender(int genderSsn) {
		String gender = "F";
		
		int[] male = {1,3,5,7};
		boolean maleFlag = false;
		
		for(int i=0; i<male.length; i++) {
			if (genderSsn == male[i]) {
				maleFlag = true;
			}
		}
		
		if (maleFlag) {
			gender = "M";
		}
		
		return gender;
	}
	
	/**
	 * 주민번호 앞 두자리를 받아와 나이를 반환하는 메서드
	 * @param year
	 * @return
	 */
	public int getAge(int year) {
		int age = 0;
		
		if (year < 20) {
			year += 2000;
		} else {
			year += 1900;
		}
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
		int currentYear = Integer.parseInt(sdf.format(new Date()));
		
		age = currentYear - year + 1;
		
		return age;
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
