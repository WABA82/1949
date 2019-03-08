package user.ee.controller;

import java.awt.FileDialog;
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
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import user.common.vo.EeMainVO;
import user.dao.CommonDAO;
import user.dao.EeDAO;
import user.ee.view.EeInfoRegView;
import user.ee.view.EeMainView;
import user.ee.view.ModifyExtView;
import user.ee.vo.EeInsertVO;
import user.util.UserLog;
import user.util.UserUtil;

public class EeInfoRegController extends WindowAdapter implements ActionListener {

	/* 인스턴스변수 */
	private EeInfoRegView eirv;
	
	private File uploadImg;
	private File uploadExt;
	
	private EeDAO eedao;
	private String eeId;// 내 아이디.
	private EeMainView emv;
	private UserUtil uu;
	private UserLog ul;

	// 생성자.
	public EeInfoRegController(EeInfoRegView eirv, String eeId, EeMainView emv) {
		this.eirv = eirv;
		this.eeId = eeId;
		this.emv=emv;
		eedao = EeDAO.getInstance();
		uu = new UserUtil();
		ul = new UserLog();
	}// 생성자

	
	public void register() {
		boolean insertFlag =false;
		
		if (uploadImg == null) {
			JOptionPane.showMessageDialog(eirv, "이미지 등록은 필수 입니다.\n(size : 150px * 200px)");
			return;
		} 

		String tempRank = eirv.getJcbRank().getSelectedItem().toString();
		String rank = tempRank.equals("신입") ? "C" : "N";
		String loc = eirv.getJcbLoc().getSelectedItem().toString();
		String education = eirv.getJcbEducation().getSelectedItem().toString();
		String tempPortfolio = eirv.getJcbPortfolio().getSelectedItem().toString();
		String portfolio = tempPortfolio.equals("YES") ? "Y" : "N";
		String extResume = "".equals(eirv.getJtfExtResume().getText()) ? "" : eirv.getJtfExtResume().getText();
		
		if (!"".equals(extResume)) {
			extResume = System.currentTimeMillis()+extResume;
		}
		
		String imgName = System.currentTimeMillis()+uploadImg.getName();
		
		EeInsertVO eivo = new EeInsertVO(eeId, imgName, rank, loc, education, portfolio, extResume);
		System.out.println("-----------"+eivo);
		// ActivationVO, FileUser(UserUtil 사용) 안써도 될듯..(영근 0302)
		
		try {
			insertFlag=	eedao.updateUserInfo(eivo);
			if(insertFlag) {
				JOptionPane.showMessageDialog(eirv, "개인정보가 등록 되었습니다.");
				//창 다시 띄우기?
				
				try {
					///////////////////////// 0301 영근 이미지 FS에 추가기능 구현 ////////////////////////////////
					Socket client = null;
					DataOutputStream dos = null;
					DataInputStream dis = null;
					FileInputStream fis = null;
					FileOutputStream fos = null;
					
					// 새로운 이미지 파일 FileServer에 추가
					uu.addNewFile(imgName, uploadImg, "ee", client, dos, dis, fis); // 새로운 이미지를 전송
					System.out.println("--- 이미지파일 등록");
					// FilerServer로부터 이미지 요청
					uu.reqFile(imgName, "ee", client, dos, dis, fos); // 새로운 이미지를 FS에게 요청, 저장
					System.out.println("--- 이미지파일 요청");
					
					// 새로운 이력서 파일이 존재하면 FileServer에 추가
					if (!"".equals(extResume)) {
						uu.addNewFile(extResume, uploadExt, "ext", client, dos, dis, fis);
						System.out.println("--- 이력서 파일 등록만 수행");
					}
					
					ul.sendLog(eeId, "신규 기본정보 등록");
				} catch (IOException e) {
					e.printStackTrace();
				}//end catch
				
				EeMainVO updateEmvo=CommonDAO.getInstance().selectEeMain(eeId, "Y");
				new EeMainView(updateEmvo);
				emv.dispose();
				eirv.dispose();
				
			}//end if
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(eirv, "DB문제 발생");
			e.printStackTrace();
		}//end catch
	}// register

	public void changeImg() throws IOException {
		boolean flag = false;
		FileDialog fd = new FileDialog(eirv, "이미지 선택", FileDialog.LOAD);
		fd.setVisible(true);

		String path = fd.getDirectory();
		String name = fd.getFile();

		if (path != null) {
			if (name.endsWith(".jpg") || name.endsWith(".jpeg") 
					|| name.endsWith(".png") || name.endsWith(".bmp")
					|| name.endsWith(".gif")) {
				flag = true;
			} // end if

			if (flag) {
				uploadImg = new File(path + name);
				eirv.getJlImage().setIcon(new ImageIcon(uploadImg.getAbsolutePath()));
			} else {
				JOptionPane.showMessageDialog(eirv, name + "은 사용할수 없습니다.");
				return;
			} // end else
		} // end if
	}// changeImg
	
	@Override
	public void windowClosing(WindowEvent e) {
		eirv.dispose();
	}

	@Override
	public void actionPerformed(ActionEvent ae) {

		if (ae.getSource() == eirv.getJbClose()) {
			eirv.dispose();
		} // end if

		// 이미지 변경 버튼 눌렀을 때.
		if (ae.getSource() == eirv.getJbRegisterImg()) {
			try {
				changeImg();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} // end if

		// 외부 이력서 등록 버튼 눌렀을 때.
		if (ae.getSource() == eirv.getJbRegisterExt()) {
			new ModifyExtView(eirv, eirv, this, null, null, "reg");
		}// end if

		// 등록 버튼 눌렀을 때.
		if (ae.getSource() == eirv.getJbRegister()) {
			switch (JOptionPane.showConfirmDialog(eirv, "기본 정보를 등록 하시겠습니까?")) {
			case JOptionPane.OK_OPTION:
				register();
			}// end switch
		} // end if

	}// actionPerformed

	public void setUploadExt(File uploadExt) {
		this.uploadExt = uploadExt;
	}
}// class
