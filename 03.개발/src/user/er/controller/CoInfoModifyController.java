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

import user.dao.EeDAO;
import user.dao.ErDAO;
import user.er.view.CoInfoModifyView;
import user.er.vo.CoInfoVO;
import user.er.vo.CoInsertVO;

public class CoInfoModifyController extends WindowAdapter implements ActionListener, MouseListener {

	private CoInfoModifyView cimv;
	private String coNum;
	private File uploadImg1, uploadImg2, uploadImg3, uploadImg4;
	String path, name;
	private ErDAO erdao;

	public CoInfoModifyController(CoInfoModifyView cimv, String coNum) {
		this.cimv = cimv;
		this.coNum = coNum;
		erdao = ErDAO.getInstance();
	}// ������

	@Override
	public void windowClosing(WindowEvent e) {
		cimv.dispose();
	}// ������ �ݱ�

	@Override
	public void actionPerformed(ActionEvent ae) {
		/* ���� ��ư ������ �� */
		if (ae.getSource() == cimv.getJbModify()) {
			modify();
		} else if (ae.getSource() == cimv.getJbClose()) {
			cimv.dispose();
		} // else if

	}// actionPerformed

	public void modify() {

		try {
			String coName = cimv.getJtfCoName().getText().trim();
			String estDate = cimv.getJtfEstDate().getText().trim();
			int memberNum = Integer.parseInt(cimv.getMemberNum().getText().trim());

			if (uploadImg1 == null) {
				JOptionPane.showMessageDialog(cimv, "���λ����� �־� �ּž��մϴ�");
				return;
			} // end if

			if (coName.equals("")) {
				JOptionPane.showMessageDialog(cimv, "ȸ����� �Է����ּ���.");
				return;
			} // end if

			if (estDate.isEmpty()) {
				JOptionPane.showMessageDialog(cimv, "�������� �Է����ּ���.");
				return;
			} // end if

			if (memberNum == 0) {
				JOptionPane.showMessageDialog(cimv, "������� �Է����ּ���.");
				return;
			} // end if

			if (uploadImg1 == null) {
				uploadImg1 = new File("C:/dev/1949/03.����/src/user/img/co/no_co_img1.png");
			} // end if

			if (uploadImg2 == null) {
				uploadImg2 = new File("C:/dev/1949/03.����/src/user/img/co/no_co_img2.png");
			} // end if

			if (uploadImg3 == null) {
				uploadImg3 = new File("C:/dev/1949/03.����/src/user/img/co/no_co_img3.png");
			} // end if

			if (uploadImg4 == null) {
				uploadImg4 = new File("C:/dev/1949/03.����/src/user/img/co/no_co_img4.png");
			} // end if

			String coDesc = cimv.getJtaCoDesc().getText().trim();

			StringBuilder updateMsg = new StringBuilder();
			updateMsg.append("ȸ�� �������� \n").append("ȸ�� �� : ").append(cimv.getJtfCoName().getText()).append("\n")
					.append("���� �⵵ : ").append(cimv.getJtfEstDate().getText()).append("\n").append("��� ��  : ")
					.append(cimv.getMemberNum().getText()).append("\n").append("��� ����  : ")
					.append(cimv.getJtaCoDesc().getText()).append("\n").append("���� ������ ȸ���� ������ ����˴ϴ�. �����Ͻðڽ��ϱ�?");

			switch (JOptionPane.showConfirmDialog(cimv, updateMsg.toString())) {
			case JOptionPane.OK_OPTION:
				CoInfoVO cvo = new CoInfoVO(coNum, coName, uploadImg1.getName(), uploadImg2.getName(),
						uploadImg3.getName(), uploadImg4.getName(), estDate, coDesc, memberNum);

//			System.out.println("����");
//			System.out.println(cvo);

				try {
					erdao.updateCoInfo(cvo);
					JOptionPane.showMessageDialog(cimv, "ȸ�� ������ ���� �Ǿ����ϴ�");
				} catch (SQLException e) {
					e.printStackTrace();
				}
//			System.out.println(cirv);
			}// end switch
		} catch (NumberFormatException nfe) {
			JOptionPane.showMessageDialog(cimv, "������� ���ڸ� �Է°����մϴ�.");
			return;
		} // end catch

		System.out.println("����");
//		JOptionPane.showMessageDialog(cimv, "ȸ�� ������ �����Ǿ����ϴ�.");
	}// modify

	public void changeImg(JLabel jl, int imgNumber) {
		boolean flag = false;
		FileDialog fd = new FileDialog(cimv, "�̹��� ����", FileDialog.LOAD);
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
				JOptionPane.showMessageDialog(cimv, name + "�� �̹��� ������ �ƴմϴ�.");
			}
			System.out.println("�̹��� ����");
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
