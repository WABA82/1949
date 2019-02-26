package user.er.controller;

import java.awt.FileDialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import user.common.vo.ErMainVO;
import user.dao.CommonDAO;
import user.dao.EeDAO;
import user.dao.ErDAO;
import user.er.view.CoInfoModifyView;
import user.er.view.ErMainView;
import user.er.vo.CoInfoVO;
import user.er.vo.CoInsertVO;

public class CoInfoModifyController extends WindowAdapter implements ActionListener, MouseListener {

	private CoInfoModifyView cimv;
	private String coNum;
	private File uploadImg1, uploadImg2, uploadImg3, uploadImg4;
	String path, name;
	private ErDAO erdao;
	private CoInfoVO cvo;
	private ErMainView emv;

	public CoInfoModifyController(CoInfoModifyView cimv,  CoInfoVO cvo, ErMainView emv) {
		this.cimv = cimv;
		this.cvo=cvo;
		this.emv=emv;
		erdao = ErDAO.getInstance();
	}// 생성자

	@Override
	public void windowClosing(WindowEvent e) {
		cimv.dispose();
	}// 윈도우 닫기

	@Override
	public void actionPerformed(ActionEvent ae) {
		/* 수정 버튼 눌렀을 때 */
		if (ae.getSource() == cimv.getJbModify()) {
			modify();
		} else if (ae.getSource() == cimv.getJbClose()) {
			cimv.dispose();
		} // else if

	}// actionPerformed

	public void modify() {

		try {
			String erId=cvo.getErId();
			String coNum=cvo.getCoNum();
			String coName = cimv.getJtfCoName().getText().trim();
			String estDate = cimv.getJtfEstDate().getText().trim();
			int memberNum = Integer.parseInt(cimv.getMemberNum().getText().trim());
			
			if (uploadImg1 == null) {
				uploadImg1 = new File("C:/dev/1949/03.개발/src/user/img/co/"+cvo.getImg1() );
			} // end if
			
			if (uploadImg1 == null) {
				JOptionPane.showMessageDialog(cimv, "메인사진은 넣어 주셔야합니다");
				return;
			} // end if

			if (coName.equals("")) {
				JOptionPane.showMessageDialog(cimv, "회사명을 입력해주세요.");
				return;
			} // end if

			if (estDate.isEmpty()) {
				JOptionPane.showMessageDialog(cimv, "설립일을 입력해주세요.");
				return;
			} // end if

			if (memberNum == 0) {
				JOptionPane.showMessageDialog(cimv, "사원수를 입력해주세요.");
				return;
			} // end if


			if (uploadImg2 == null) {
				uploadImg2 = new File("C:/dev/1949/03.개발/src/user/img/co/no_co_img2.png");
			} // end if

			if (uploadImg3 == null) {
				uploadImg3 = new File("C:/dev/1949/03.개발/src/user/img/co/no_co_img3.png");
			} // end if

			if (uploadImg4 == null) {
				uploadImg4 = new File("C:/dev/1949/03.개발/src/user/img/co/no_co_img4.png");
			} // end if

			String coDesc = cimv.getJtaCoDesc().getText().trim();

			StringBuilder updateMsg = new StringBuilder();
			updateMsg.append("회사 수정정보 \n").append("회사 명 : ").append(cimv.getJtfCoName().getText()).append("\n")
					.append("설립 년도 : ").append(cimv.getJtfEstDate().getText()).append("\n").append("사원 수  : ")
					.append(cimv.getMemberNum().getText()).append("\n").append("기업 설명  : ")
					.append(cimv.getJtaCoDesc().getText()).append("\n").append("위의 정보로 회사의 정보가 변경됩니다. 변경하시겠습니까?");

			switch (JOptionPane.showConfirmDialog(cimv, updateMsg.toString())) {
			case JOptionPane.OK_OPTION:
				CoInfoVO cvo = new CoInfoVO(erId, coNum, coName, uploadImg1.getName(), uploadImg2.getName(),
						uploadImg3.getName(), uploadImg4.getName(), estDate, coDesc, memberNum);
				System.out.println(uploadImg1.getName());

				try {
					erdao.updateCoInfo(cvo);
					JOptionPane.showMessageDialog(cimv, "회사 정보가 수정 되었습니다");
					ErMainVO updateEmvo=CommonDAO.getInstance().selectErMain(erId, "Y");
					new ErMainView(updateEmvo);
					cimv.dispose();
					emv.dispose();
					
				} catch (SQLException e) {
					e.printStackTrace();
				}
//			System.out.println(cirv);
			}// end switch
		} catch (NumberFormatException nfe) {
			JOptionPane.showMessageDialog(cimv, "사원수는 숫자만 입력가능합니다.");
			return;
		} // end catch

		System.out.println("수정");
//		JOptionPane.showMessageDialog(cimv, "회사 정보가 수정되었습니다.");
	}// modify

	public void changeImg(JLabel jl, int imgNumber) {
		boolean flag = false;
		FileDialog fd = new FileDialog(cimv, "이미지 선택", FileDialog.LOAD);
		fd.setVisible(true);

		path = fd.getDirectory();
		name = fd.getFile();

		if (path != null) {
			String[] extFlag = { "jpg", "gif", "jpeg", "png", "bmp" };
			for (String ext : extFlag) {
				if (name.toLowerCase().endsWith(ext)) {
					flag = true;
				} // end if
			} // end for

			if (flag) {
				if (imgNumber == 1) {
					uploadImg1 = new File(path + name);
					cimv.getJlImg1().setIcon(new ImageIcon(uploadImg1.getAbsolutePath()));
				} else if (imgNumber == 2) {
					uploadImg2 = new File(path + name);
					cimv.getJlImg2().setIcon(new ImageIcon(uploadImg2.getAbsolutePath()));
				} else if (imgNumber == 3) {
					uploadImg3 = new File(path + name);
					cimv.getJlImg3().setIcon(new ImageIcon(uploadImg3.getAbsolutePath()));
				} else if (imgNumber == 4) {
					uploadImg4 = new File(path + name);
					cimv.getJlImg4().setIcon(new ImageIcon(uploadImg4.getAbsolutePath()));
				} // end else if
			} else {
				JOptionPane.showMessageDialog(cimv, name + "은 이미지 파일이 아닙니다.");
			}
			System.out.println("이미지 수정");
		} // end if

	}// changeImg

	@Override
	public void mouseClicked(MouseEvent me) {
		if (me.getSource() == cimv.getJlImg1()) {
			changeImg(cimv.getJlImg1(), 1);

		} // end if

		if (me.getSource() == cimv.getJlImg2()) {
			changeImg(cimv.getJlImg2(), 2);
		} // end if

		if (me.getSource() == cimv.getJlImg3()) {
			changeImg(cimv.getJlImg3(), 3);
		} // end if

		if (me.getSource() == cimv.getJlImg4()) {
			changeImg(cimv.getJlImg4(), 4);
		} // end if

	}// mouseClicked

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}
}// class
