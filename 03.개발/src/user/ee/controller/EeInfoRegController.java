package user.ee.controller;

import java.awt.FileDialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import user.common.vo.EeMainVO;
import user.dao.EeDAO;
import user.ee.view.EeInfoRegView;
import user.ee.view.ModifyExtView;
import user.ee.vo.EeInsertVO;
import user.ee.vo.EeRegVO;

public class EeInfoRegController extends WindowAdapter implements ActionListener {

	/* 인스턴스변수 */
	private EeInfoRegView eirv;
	private String uploadImg;
	private EeDAO eedao;
	private String eeid; // 로그인 한 접속자의 아이디.
	private EeInfoRegController eirc;

	private boolean regFlag = false; // 기본정보 등록 성공시 activation 'N' -> 'Y'.

	// 테스트용 아이디
	private String eeId;// 내 아이디.
	private EeRegVO ervo;
	private EeMainVO emvo;
	// 생성자.
	public EeInfoRegController(EeInfoRegView eirv, String eeId) {
		this.eirv = eirv;
		this.eeId = eeId;
		uploadImg = "";
		eedao = EeDAO.getInstance();
	}// 생성자

	// 이미지전송도 같이 수행
	public void register(String eeid) {

		// 이미지 유효성 검증
		if (uploadImg.isEmpty()) {
			JOptionPane.showMessageDialog(eirv, "이미지는 필수 입니다.");
			return;
		} // register(String eeid)

		String tempRank = eirv.getJcbRank().getSelectedItem().toString();
		String rank = tempRank.replace("신입", "C").replaceAll("경력", "N");

		String loc = eirv.getJcbLoc().getSelectedItem().toString();
		String education = (String) eirv.getJcbEducation().getSelectedItem();
		String tempPortfolio = eirv.getJcbPortfolio().getSelectedItem().toString();
		String portfolio = tempPortfolio.replaceAll("YES", "Y").replaceAll("NO", "N");
		String extResume = eirv.getJtfExtResume().getText();
		File file = new File(uploadImg);

		//////////////////////////////////////////// 여기서 부터 진행하기
		//////////////////////////////////////////// ////////////////////////////////////////////

		try { // 디비에 들어갈때 순서에 맞춰서 입력
			EeInsertVO eivo = new EeInsertVO(eeid, file.getName(), rank, loc, education, portfolio, extResume);

			if (!eedao.insertEeinfo(eivo)) { // 기본정보를 등록하는 쿼리문이 정상 동작했을 때 - false가 반환됩니다..
				System.out.println(eivo);
				
//				eedao.updateActivation(eeid); // 구현해야할 부분.
				
				JOptionPane.showMessageDialog(eirv, "기본 정보가 등록되었습니다\n이제부터 구인 정보를 조회 가능합니다.");
				eirv.dispose();
			} // end if

		} catch (SQLException e) {
			JOptionPane.showMessageDialog(eirv, "DB에 문제가 발생하였습니다.");
			e.printStackTrace();
		} // DB에 추가
	}// register

//	public static void main(String[] args) {
//		System.out.println(EeDAO.getInstance().insertEeinfo(eivo););
//	}

	public void changeImg() {
		FileDialog fd = new FileDialog(eirv, "이미지 선택", FileDialog.LOAD);
		fd.setVisible(true);

		String path = fd.getDirectory();
		String name = fd.getFile();

		if (path != null) {
			if (!name.endsWith(".jpg") && !name.endsWith(".jpeg") && !name.endsWith(".png") && !name.endsWith(".bmp")
					&& !name.endsWith(".gif")) {
				JOptionPane.showMessageDialog(eirv, name + "은 사용할수 없습니다.");
			} else {
				uploadImg = path + name;
				eirv.getJlImage().setIcon(new ImageIcon(uploadImg));
			}
		}
	}// changeImg

	/**
	 * 이름, 나이, 성별
	 */
//	public void setInfo(String eeid) {
//		JTextField name = eirv.getJtfName();
//		JTextField age = eirv.getJtfAge();
//		JTextField gender = eirv.getJtfGender();
//		EeRegVO ervo = null;
//		try {
//			ervo = eedao.selectEeReg(eeid);
//			name.setText(ervo.getName());
//			age.setText(String.valueOf(ervo.getAge()));
//			gender.setText(ervo.getGender());
//
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}// end catch
//	}// setInfo.

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
		} // end if

		// 등록 버튼 눌렀을 때.
		if (ae.getSource() == eirv.getJbRegister()) {
			switch (JOptionPane.showConfirmDialog(eirv, "기본 정보를 등록 하시겠습니까?")) {
			case JOptionPane.OK_OPTION:
				register(eeId);
			}// end switch
		} // end if

	}// actionPerformed

//	public static void main(String[] args) {
//		new EeInfoRegController(eirv, eeId)
//	}
}// class
