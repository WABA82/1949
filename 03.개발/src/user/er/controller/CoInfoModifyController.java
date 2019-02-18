package user.er.controller;

import java.awt.FileDialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import user.er.view.CoInfoModifyView;
import user.er.vo.CoInfoVO;
import user.er.vo.CoInsertVO;

public class CoInfoModifyController extends WindowAdapter implements ActionListener, MouseListener {

	private CoInfoModifyView cimv;
	private String coNum;
	private String uploadImg1, uploadImg2, uploadImg3, uploadImg4, path, name;
	public CoInfoModifyController(CoInfoModifyView cimv ,String coNum) {
		this.cimv= cimv;
		uploadImg1="";
		uploadImg2="";
		uploadImg3="";
		uploadImg4="";
	}//������
	
	@Override
	public void windowClosing(WindowEvent e) {
		cimv.dispose();
	}//������ �ݱ�
	
	
	@Override
	public void actionPerformed(ActionEvent ae) {
		if(ae.getSource()==cimv.getJbModify()) {
			modify();
		}else if(ae.getSource()==cimv.getJbClose()) {
			cimv.dispose();
		}
	}//��ư
	
	public void modify() {

		if (uploadImg1.isEmpty()) {
			JOptionPane.showMessageDialog(cimv, "���λ����� �־� �ּž��մϴ�");
			return;
		}//end if
		
		try {
			String img1, img2, img3, img4 ="";
			String coName = cimv.getJtfCoName().getText().trim();
			String estDate = cimv.getJtfEstDate().getText().trim();
			int memberNum = Integer.parseInt(cimv.getMemberNum().getText().trim());
			
			if(coName.equals("")) {
				JOptionPane.showMessageDialog(cimv, "ȸ����� �Է����ּ���.");
				return;
			}//end if
			
			if(estDate.isEmpty()) {
				JOptionPane.showMessageDialog(cimv, "�������� �Է����ּ���.");
				return;
			}//end if
			
			if(memberNum == 0) {
				JOptionPane.showMessageDialog(cimv, "������� �Է����ּ���.");
				return;
			}//end if
			
			img1 = cimv.getJlImg1().getText();
		
			if(uploadImg1.isEmpty()) {
				img1=cimv.getJlImg1().getText();
			}else {
				img1=uploadImg1;
			}
			
			img2 = cimv.getJlImg2().getText();
			
			if(uploadImg2.isEmpty()) {
				img2=cimv.getJlImg2().getText();
			}else {
				img2=uploadImg2;
			}
			
			img3 = cimv.getJlImg3().getText();
			
			if(uploadImg3.isEmpty()) {
				img3=cimv.getJlImg3().getText();
			}else {
				img3=uploadImg3;
			}
			
			img4 = cimv.getJlImg4().getText();
			if(uploadImg4.isEmpty()) {
				img4=cimv.getJlImg4().getText();
			}else {
				img4=uploadImg4;
			}
			File file1=new File(uploadImg1);
			File file2=new File(uploadImg2);
			File file3=new File(uploadImg3);
			File file4=new File(uploadImg4);
			
			String coDesc = cimv.getJtaCoDesc().getText().trim();

			
			StringBuilder updateMsg=new StringBuilder();
			updateMsg.append("�������� \n")
			.append("ȸ�� �� : ").append( cimv.getJtfCoName().getText()).append("\n")
			.append("���� �⵵ : ").append(cimv.getJtfEstDate().getText()).append("\n")
			.append("��� ��  : ").append(cimv.getMemberNum().getText()).append("\n")
			.append("��� ����  : ").append(cimv.getJtaCoDesc().getText()).append("\n")
			.append("���� ������ ���ö��� ������ ����˴ϴ�. �����Ͻðڽ��ϱ�?");
			
			switch(JOptionPane.showConfirmDialog(cimv, updateMsg.toString())) {
			case JOptionPane.OK_OPTION :
			CoInfoVO cvo = null;
			cvo = new CoInfoVO(coNum, file1.getName(), file2.getName(), file3.getName(), file4.getName(), coName, estDate, coDesc, memberNum);
			System.out.println("����");
			JOptionPane.showMessageDialog(cimv, "ȸ�� ������ ���� �Ǿ����ϴ�");
//			System.out.println(cirv);
			}//end switch
			
			}catch(NumberFormatException nfe) {
				JOptionPane.showMessageDialog(cimv, "������� ���ڸ� �Է°����մϴ�.");
				return;
			}
	
		System.out.println("����");
//		JOptionPane.showMessageDialog(cimv, "ȸ�� ������ �����Ǿ����ϴ�.");
	}//modify

	
	public void changeImg(JLabel jl) {
		
		FileDialog fd = new FileDialog(cimv, "�̹��� ����", FileDialog.LOAD);
		fd.setVisible(true);

		path = fd.getDirectory();
		name = fd.getFile();

		if (path != null) {
			if (!name.endsWith(".jpg") && !name.endsWith(".jpeg") && !name.endsWith(".png") && !name.endsWith(".bmp")
					&& !name.endsWith(".gif")) {
				JOptionPane.showMessageDialog(cimv, name + "�� ����Ҽ� �����ϴ�.");
				return;
			} // end if
		
		System.out.println("�̹��� ����");
	}//end if
		
	}//changeImg
	
	@Override
	public void mouseClicked(MouseEvent me) {
		if (me.getSource() == cimv.getJlImg1()) {
			changeImg(cimv.getJlImg1());
			uploadImg1 = path + name;
			cimv.getJlImg1().setIcon(new ImageIcon(uploadImg1));
		} // end if

		if (me.getSource() == cimv.getJlImg2()) {
			changeImg(cimv.getJlImg2());
			uploadImg2 = path + name;
			cimv.getJlImg2().setIcon(new ImageIcon(uploadImg2));
		} // end if

		if (me.getSource() == cimv.getJlImg3()) {
			changeImg(cimv.getJlImg3());
			uploadImg3 = path + name;
			cimv.getJlImg3().setIcon(new ImageIcon(uploadImg3));
		} // end if

		if (me.getSource() == cimv.getJlImg4()) {
			changeImg(cimv.getJlImg4());
			uploadImg4 = path + name;
			cimv.getJlImg4().setIcon(new ImageIcon(uploadImg4));
		} // end if
		
	}//mouseClicked

	
	
	
	
	@Override
	public void mousePressed(MouseEvent e) {}
	@Override
	public void mouseReleased(MouseEvent e) {}
	@Override
	public void mouseEntered(MouseEvent e) {}
	@Override
	public void mouseExited(MouseEvent e) {}
}//class
