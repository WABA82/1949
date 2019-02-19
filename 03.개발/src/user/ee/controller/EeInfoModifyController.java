package user.ee.controller;

import java.awt.FileDialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.io.File;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import user.dao.EeDAO;
import user.ee.view.EeInfoModifyView;
import user.ee.vo.EeInfoVO;
import user.ee.vo.EeModifyVO;

public class EeInfoModifyController extends WindowAdapter implements ActionListener {

	private EeInfoVO eivo;
	private EeInfoModifyView eimv;
	private String originalImag;
	private String uploadImg;
	private String eeNum;
	private EeDAO eedao;
	public EeInfoModifyController(EeInfoModifyView eimv, String eeNum, EeInfoVO eivo) {
		this.eimv = eimv;
		this.eivo=eivo;
		this.eeNum=eeNum;
		uploadImg="";
		eedao=EeDAO.getInstance();
	}

	// 이력서 수정하는 Method
	private void modifyEe() {

		String tempRank = eimv.getJcbRank().getSelectedItem().toString();
		String rank = tempRank.replace("신입", "C").replaceAll("경력", "N");

		String loc = eimv.getJcbLoc().getSelectedItem().toString();
		String education = (String) eimv.getJcbEducation().getSelectedItem();
		
		String tempPortfolio = eimv.getJcbPortfolio().getSelectedItem().toString();
		String portfolio = tempPortfolio.replaceAll("YES", "Y").replaceAll("NO", "N");
		String extResume = eimv.getJtfExtResume().getText();
		File file = new File(uploadImg);
		StringBuilder updateMsg= new StringBuilder();
		
		updateMsg
			.append("수정정보 \n")
			.append("이름 : "+eivo.getName()+"\n")
			.append("직급 : "+tempRank+"\n")
			.append("근무지역 : "+loc+"\n")
			.append("학력 : "+education+"\n")
			.append("나이 : "+eivo.getAge()+"\n")
			.append("포토폴리오 유무 : "+tempPortfolio+"\n")
			.append("성별 : "+eivo.getGender()+"\n")
			.append("외부이력서 : "+extResume+"\n")
			.append("위의 정보로 변경하시겠습니까?");
		
		switch(JOptionPane.showConfirmDialog(eimv, updateMsg)) {
		case JOptionPane.OK_OPTION :
		EeModifyVO emvo= new EeModifyVO(eeNum, file.getName(),
				rank,loc,education, portfolio, extResume);
				System.out.println(emvo);
		try {
			eedao.updateEeInfo(emvo); //DB테이블에서 update수행
			JOptionPane.showMessageDialog(eimv, "개인정보가 변경되었습니다.");
	
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		//수정된 사항을 보여주는창.
		}//end switch
		
	}// modifyEe

	/**
	 * 이미지를 바꾸는 method
	 */
	private void ChangeImg() {
		FileDialog fd = new FileDialog(eimv, "파일열기", FileDialog.LOAD);
		fd.setVisible(true);
		
		String path = fd.getDirectory();
		String name = fd.getFile();
		

		if(path != null) {
			if (!name.endsWith(".jpg") && !name.endsWith(".jpeg") && !name.endsWith(".png") && !name.endsWith(".bmp")
					&& !name.endsWith(".gif")) {
				JOptionPane.showMessageDialog(eimv, name + "은 사용할수 없습니다.");
			} else {
				uploadImg = path + name;
				eimv.getJlImag().setIcon(new ImageIcon(uploadImg));
			}
		}
	}// changeImg

	private void changeExt() {
		FileDialog fd = new FileDialog(eimv, "파일열기", FileDialog.LOAD);
		fd.setVisible(true);
		String fdDir = fd.getDirectory();
		String fdName = fd.getFile();
		String filePath = fdDir + fdName;
		File file = new File(filePath);
		changeExt(file);
	}//changeExt
	
	private void changeExt(File file) {
		String extResume="";
		extResume=file.getName();
		
		if( !extResume.endsWith(".txt") && !extResume.endsWith(".pdf")) {
				JOptionPane.showMessageDialog(eimv, "외부이력서는 doc, pdf만 가능합니다.");
				return;
			}else {
				eimv.getJtfExtResume().setText(extResume);
				eimv.getJtfExtResume().setEditable(false);
				JTextField jtf=eimv.getJtfExtResume();
				jtf.setText(extResume);
			}//end else
			
		
	}//changeExt

	@Override
	public void actionPerformed(ActionEvent ae) {

		if (ae.getSource() == eimv.getJbModify()) {
//			int temp = JOptionPane.showConfirmDialog(eimv, "이력서를 수정하시겠습니까?");
//			switch (temp) {
//			case JOptionPane.OK_OPTION:
				modifyEe();
			}// end switch

//		} // end if

		if (ae.getSource() == eimv.getJbModifyImg()) {
			ChangeImg();
		} // end if

		if (ae.getSource() == eimv.getJbModifyExt()) {
			changeExt();
		}

		if(ae.getSource() == eimv.getJbClose()) {
			eimv.dispose();
		}
	
	}// actionPerformed

}
