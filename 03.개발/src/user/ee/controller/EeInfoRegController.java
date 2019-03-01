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
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import sun.rmi.server.Activation;
import user.common.vo.EeMainVO;
import user.dao.CommonDAO;
import user.dao.EeDAO;
import user.ee.view.EeInfoRegView;
import user.ee.view.EeMainView;
import user.ee.view.ModifyExtView;
import user.ee.vo.ActivationVO;
import user.ee.vo.EeInsertVO;
import user.ee.vo.EeInterestAndAppVO;
import user.ee.vo.EeRegVO;
import user.file.FileUser;
import user.util.UserUtil;

public class EeInfoRegController extends WindowAdapter implements ActionListener {

	/* 인스턴스변수 */
	private EeInfoRegView eirv;
	private File uploadImg;
	private EeDAO eedao;
	private EeInfoRegController eirc;
	private Connection con;
	private String eeId;// 내 아이디.
	private EeRegVO ervo;
	private EeMainVO emvo;
	private EeMainView emv;
	private UserUtil uu;

	// 생성자.
	public EeInfoRegController(EeInfoRegView eirv, String eeId, EeMainView emv) {
		this.eirv = eirv;
		this.eeId = eeId;
		this.emv=emv;
		eedao = EeDAO.getInstance();
		uu = new UserUtil();
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
		String extResume = eirv.getJtfExtResume().getText();
		
		///////////////////////////////// 작업중
		
		String imgName = System.currentTimeMillis()+uploadImg.getName();
		
		EeInsertVO eivo = new EeInsertVO(eeId, imgName, rank, loc, education, portfolio, extResume);
		System.out.println("-----------"+eivo);
		ActivationVO avo = new ActivationVO(eeId);
		System.out.println(avo);
		
		try {
			insertFlag=	eedao.updateUserInfo(eivo, avo);
			if(insertFlag) {
				JOptionPane.showMessageDialog(eirv, "개인정보가 등록 되었습니다.");
				//창 다시 띄우기?
				
				EeMainVO updateEmvo=CommonDAO.getInstance().selectEeMain(eeId, "Y");
				new EeMainView(updateEmvo);
				emv.dispose();
				eirv.dispose();
				
			}//end if
			System.out.println("insert 도 과연 true 일까요??"+insertFlag);
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(eirv, "DB문제 발생");
			e.printStackTrace();
		}//end catch

		//이미지 user.ee로 전송
		if( !uploadImg.getName().equals("")) {// 변경한 이미지가 존재하는 경우
			
			File original = new File("C:/dev/1949/03.개발/src/file/eeImg/"+eirv.getJlImage());
			original.delete();
			try {
				FileUser.getInstance().uploadImgFile(uploadImg);
				FileUser.getInstance().eeInfoImgSend(uploadImg.getName());
				
				// 여기 맞나? 확인할 것..
				///////////////////////// 0301 영근 이미지 FS에 추가기능 구현 ////////////////////////////////
				Socket client = null;
				DataOutputStream dos = null;
				DataInputStream dis = null;
				FileInputStream fis = null;
				FileOutputStream fos = null;
				
				// 새로운 파일 FileServer에 추가
				uu.addNewFile(uploadImg.getName(), uploadImg, "ee", client, dos, dis, fis); // 새로운 이미지를 전송
				// FilerServer로부터 이미지 요청
				uu.reqFile(uploadImg.getName(), "ee", client, dos, dis, fos); // 새로운 이미지를 FS에게 요청, 저장

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}//end catch
		}//end if
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
				
				///////////////////// 이미지 변경했으면 플레그를 변경 ////////////////////
				
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
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} // end if

		// 외부 이력서 등록 버튼 눌렀을 때.
		if (ae.getSource() == eirv.getJbRegisterExt()) {
			new ModifyExtView(eirv, eirc);
			}// end if

		// 등록 버튼 눌렀을 때.
		if (ae.getSource() == eirv.getJbRegister()) {
			switch (JOptionPane.showConfirmDialog(eirv, "기본 정보를 등록 하시겠습니까?")) {
			case JOptionPane.OK_OPTION:
				register();
			}// end switch
		} // end if

	}// actionPerformed

}// class
