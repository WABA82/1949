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
import javax.swing.JTextArea;
import javax.swing.JTextField;

import user.dao.ErDAO;
import user.er.view.CoInfoRegView;
import user.er.vo.CoInsertVO;

public class CoInfoRegController extends WindowAdapter implements MouseListener, ActionListener {

	private CoInfoRegView cirv;
	private String erId;
	private String uploadImg1, uploadImg2, uploadImg3, uploadImg4;
	private String path, name;

	public CoInfoRegController(CoInfoRegView cirv, String erId) {
		this.cirv = cirv;
		this.erId = erId;
		uploadImg1 = "";
		uploadImg2 = "";
		uploadImg3 = "";
		uploadImg4 = "";
	}// ������

	@Override
	public void windowClosing(WindowEvent e) {
		cirv.dispose();
	}// ������ �ݱ�

	@Override
	public void actionPerformed(ActionEvent ae) {
		if (ae.getSource() == cirv.getJbReg()) {
			try {
				register();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else if (ae.getSource() == cirv.getJbClose()) {
			cirv.dispose();
		}
	}// ��ư ����

	@Override
	public void mouseClicked(MouseEvent me) {
		if (me.getSource() == cirv.getJlImg1()) {
			regImg(cirv.getJlImg1());
			uploadImg1 = path + name;
			cirv.getJlImg1().setIcon(new ImageIcon(uploadImg1));
		} // end if

		if (me.getSource() == cirv.getJlImg2()) {
			regImg(cirv.getJlImg2());
			uploadImg2 = path + name;
			cirv.getJlImg2().setIcon(new ImageIcon(uploadImg2));
		} // end if

		if (me.getSource() == cirv.getJlImg3()) {
			regImg(cirv.getJlImg3());
			uploadImg3 = path + name;
			cirv.getJlImg3().setIcon(new ImageIcon(uploadImg3));
		} // end if

		if (me.getSource() == cirv.getJlImg4()) {
			regImg(cirv.getJlImg4());
			uploadImg4 = path + name;
			cirv.getJlImg4().setIcon(new ImageIcon(uploadImg4));
		} // end if

	}// mouseClicked

	public void register() throws SQLException {

		if (uploadImg1.isEmpty()) {
			JOptionPane.showMessageDialog(cirv, "���λ����� �Է����ּž��մϴ�");
			return;
		}//end if
		
		try {
			String img1, img2, img3, img4 ="";
			String coName = cirv.getJtfCoName().getText().trim();
			String estDate = cirv.getJtfEstDate().getText().trim();
			int memberNum = Integer.parseInt(cirv.getMemberNum().getText().trim());
			
			if(coName.equals("")) {
				JOptionPane.showMessageDialog(cirv, "ȸ����� �Է����ּ���.");
				return;
			}//end if
			
			if(estDate.isEmpty()) {
				JOptionPane.showMessageDialog(cirv, "�������� �Է����ּ���.");
				return;
			}//end if
			
			if(memberNum == 0) {
				JOptionPane.showMessageDialog(cirv, "������� �Է����ּ���.");
				return;
			}//end if
			
			img1 = cirv.getJlImg1().getText();
		
			if(uploadImg1.isEmpty()) {
				img1=cirv.getJlImg1().getText();
			}else {
				img1=uploadImg1;
			}
			
			img2 = cirv.getJlImg2().getText();
			
			if(uploadImg2.isEmpty()) {
				img2=cirv.getJlImg2().getText();
			}else {
				img2=uploadImg2;
			}
			
			img3 = cirv.getJlImg3().getText();
			
			if(uploadImg3.isEmpty()) {
				img3=cirv.getJlImg3().getText();
			}else {
				img3=uploadImg3;
			}
			
			img4 = cirv.getJlImg4().getText();
			if(uploadImg4.isEmpty()) {
				img4=cirv.getJlImg4().getText();
			}else {
				img4=uploadImg4;
			}
			File file1=new File(uploadImg1);
			File file2=new File(uploadImg2);
			File file3=new File(uploadImg3);
			File file4=new File(uploadImg4);
			
			String coDesc = cirv.getJtaCoDesc().getText().trim();

			CoInsertVO civo = null;
			civo = new CoInsertVO(erId, file1.getName(), file2.getName(), file3.getName(), file4.getName(), coName, estDate, coDesc, memberNum);
			ErDAO.getInstance().insertCoInfo(civo);
			System.out.println("���");
			JOptionPane.showMessageDialog(cirv, "ȸ�� ������ ��ϵǾ����ϴ�\n�������� ���� ������ ��ȸ�����մϴ�.");
//			System.out.println(cirv);
			}catch(NumberFormatException nfe) {
				JOptionPane.showMessageDialog(cirv, "������� ���ڸ� �Է°����մϴ�.");
				return;
			}
	}// register

	public void regImg(JLabel jl) {

		FileDialog fd = new FileDialog(cirv, "�̹��� ����", FileDialog.LOAD);
		fd.setVisible(true);

		path = fd.getDirectory();
		name = fd.getFile();

		if (path != null) {
			if (!name.endsWith(".jpg") && !name.endsWith(".jpeg") && !name.endsWith(".png") && !name.endsWith(".bmp")
					&& !name.endsWith(".gif")) {
				JOptionPane.showMessageDialog(cirv, name + "�� ����Ҽ� �����ϴ�.");
				return;
			} // end if

//			System.out.println("�̹��� ����");
		} // end if
	}

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

}
