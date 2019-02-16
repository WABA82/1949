package user.ee.controller;

import java.awt.FileDialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

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
	
	//테스트용 아이디
	private String eeid = "";// 내 아이디.
//	private EeRegVO eevo;

	public EeInfoRegController(EeInfoRegView eirv, String eeid) {
		this.eirv = eirv;
		this.eeid = eeid;
		uploadImg = "";
		eedao = EeDAO.getInstance();
		setInfo(eeid);
	}// 생성자

	// 이미지전송도 같이 수행
	public void register(String eeid) {
		if (uploadImg.isEmpty()) {
			JOptionPane.showMessageDialog(eirv, "이미지는 필수 입니다.");
			return;
		}
		
		
		JComboBox<String>jcbRank= eirv.getJcbRank();
		
		String tempRank = eirv.getJcbRank().getSelectedItem().toString();
		String rank=tempRank.replace("신입", "C").replaceAll("경력", "N");
		jcbRank.setSelectedIndex(1);
		
		String loc = eirv.getJcbLoc().getSelectedItem().toString();
		String education = (String)eirv.getJcbEducation().getSelectedItem();
		String tempPortfolio = eirv.getJcbPortfolio().getSelectedItem().toString();
		String portfolio =tempPortfolio.replaceAll("YES", "Y").replaceAll("NO", "N");
		String extResume = eirv.getJtfExtResume().getText();
		File file = new File(uploadImg);

		try {	//디비에 들어갈때 순서에 맞춰서 입력
		EeInsertVO eivo= new EeInsertVO( eeid, file.getName(), rank, loc, education, portfolio, extResume);
			eedao.insertEeinfo(eivo);
			JOptionPane.showMessageDialog(eirv, "등록되었습니다");
			eirv.dispose();
			
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(eirv, "DB에 문제가 발생하였습니다.");
			e.printStackTrace();
		}//DB에 추가
	}// register

	
	public void changeImg() {
		FileDialog fd = new FileDialog(eirv, "이미지 선택", FileDialog.LOAD);
		fd.setVisible(true);

		String path = fd.getDirectory();
		String name = fd.getFile();

		if (path != null) {
			if (!name.endsWith(".jpg") && !name.endsWith(".jpeg") && !name.endsWith(".png") && !name.endsWith(".bmp") && !name.endsWith(".gif")) {
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
	public void setInfo(String eeid) {
		JTextField name = eirv.getJtfName();
		JTextField age = eirv.getJtfAge();
		JTextField gender = eirv.getJtfGender();
		EeRegVO ervo = null;
		try {
			ervo = eedao.selectEeReg(eeid);
			name.setText(ervo.getName());
			age.setText(String.valueOf(ervo.getAge()));
			gender.setText(ervo.getGender());

		} catch (SQLException e) {
			e.printStackTrace();
		}// end catch
	}// setInfo.

	@Override
	public void windowClosing(WindowEvent e) {
		eirv.dispose();
	}

	@Override
	public void actionPerformed(ActionEvent ae) {

		if (ae.getSource() == eirv.getJbClose()) {
			eirv.dispose();
		} // end if

		if (ae.getSource() == eirv.getJbRegisterImg()) {
			changeImg();
		} // end if

		if (ae.getSource() == eirv.getJbRegisterExt()) {
			new ModifyExtView(eirv);
		} // end if

		if (ae.getSource() == eirv.getJbRegister()) {
			register(eeid);
		} // end if

	}// actionPerformed

}// class
