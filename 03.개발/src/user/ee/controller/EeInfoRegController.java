package user.ee.controller;

import java.awt.FileDialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;

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

	// 생성자.
	public EeInfoRegController(EeInfoRegView eirv, String eeId) {
		this.eirv = eirv;
		this.eeId = eeId;
		eedao = EeDAO.getInstance();
	}// 생성자

	// 이미지전송도 같이 수행
	public void register() {
		boolean insertFlag =false;
		
		// 이미지 유효성 검증 등록안할시 기본사진으로 등록
		if (uploadImg == null) {
			uploadImg=new File("C:/dev/1949/03.개발/src/user/img/ee/no_ee_img.png");
			//JOptionPane.showMessageDialog(eirv, "이미지는 필수 입니다.");
		} // register(String eeid)

		String tempRank = eirv.getJcbRank().getSelectedItem().toString();
		String rank = tempRank.replace("신입", "C").replaceAll("경력", "N");
		String loc = eirv.getJcbLoc().getSelectedItem().toString();
		String education = eirv.getJcbEducation().getSelectedItem().toString();
		String tempPortfolio = eirv.getJcbPortfolio().getSelectedItem().toString();
		String portfolio = tempPortfolio.replaceAll("YES", "Y").replaceAll("NO", "N");
		String extResume = eirv.getJtfExtResume().getText();
		
			
		EeInsertVO eivo = new EeInsertVO(eeId, uploadImg.getName(), rank, loc, education, portfolio, extResume);
		System.out.println("-----------"+eivo);
			ActivationVO avo=new ActivationVO(eeId);
			System.out.println(avo);
			
			try {
				insertFlag=	eedao.updateUserInfo(eivo, avo);
				if(insertFlag) {
					JOptionPane.showMessageDialog(eirv, "개인정보가 등록 되었습니다.");
					String act=CommonDAO.getInstance().selectActivation(eeId);
					
					//창 다시 띄우기?
//					EeMainVO newEmvo=CommonDAO.getInstance().selectEeMain(eeId, act);
//					new EeMainView(newEmvo);
//					eirv.dispose();
					
				}//end if
				System.out.println("insert 도 과연 true 일까요??"+insertFlag);
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(eirv, "DB문제 발생");
				e.printStackTrace();
			}//end catch
	
	}// register

	public void changeImg() {
		boolean flag = false;
		FileDialog fd = new FileDialog(eirv, "이미지 선택", FileDialog.LOAD);
		fd.setVisible(true);

		String path = fd.getDirectory();
		String name = fd.getFile();

		if (path != null) {
			if (name.endsWith(".jpg") || name.endsWith(".jpeg") || name.endsWith(".png") || name.endsWith(".bmp")
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
			changeImg();
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
