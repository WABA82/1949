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
	private String uploadImg1, uploadImg2, uploadImg3, uploadImg4, path, name;
	private String img1, img2, img3, img4;
	private ErDAO erdao;
	
	public CoInfoModifyController(CoInfoModifyView cimv ,String coNum) {
		this.cimv= cimv;
		uploadImg1="";
		uploadImg2="";
		uploadImg3="";
		uploadImg4="";
//		this.img1=img1;
		img2="";
		img3="";
		img4="";
		this.coNum=coNum;
		erdao=ErDAO.getInstance();
	}//������
	
	@Override
	public void windowClosing(WindowEvent e) {
		cimv.dispose();
	}//������ �ݱ�
	
	
	@Override
	public void actionPerformed(ActionEvent ae) {
		if(ae.getSource()==cimv.getJbModify()) {
			try {
				modify();
			} catch (SQLException e) {
				e.printStackTrace();
			}//end catch
			
		}else if(ae.getSource()==cimv.getJbClose()) {
			cimv.dispose();
		}//else if
		
	}//actionPerformed
	
	public void modify() throws SQLException {
		
		try {
			String coName = cimv.getJtfCoName().getText().trim();
			String estDate = cimv.getJtfEstDate().getText().trim();
			int memberNum = Integer.parseInt(cimv.getMemberNum().getText().trim());
			
			if (uploadImg1.isEmpty()) {
				JOptionPane.showMessageDialog(cimv, "���λ����� �־� �ּž��մϴ�");
				return;
			}//end if
			
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
			.append("���� ������ ȸ���� ������ ����˴ϴ�. �����Ͻðڽ��ϱ�?");
			
			switch(JOptionPane.showConfirmDialog(cimv, updateMsg.toString())) {
			case JOptionPane.OK_OPTION :
			CoInfoVO cvo = new CoInfoVO(
					coNum, 
					coName, 
					file1.getName(), file2.getName(), file3.getName(), file4.getName(), 
					estDate, 
					coDesc, 
					memberNum);
			
//			System.out.println("����");
//			System.out.println(cvo);
			
			erdao.updateCoInfo(cvo);
				JOptionPane.showMessageDialog(cimv, "ȸ�� ������ ���� �Ǿ����ϴ�");
//			System.out.println(cirv);
			}//end switch
			}catch(NumberFormatException nfe) {
				JOptionPane.showMessageDialog(cimv, "������� ���ڸ� �Է°����մϴ�.");
				return;
			}//end catch
	
		System.out.println("����");
//		JOptionPane.showMessageDialog(cimv, "ȸ�� ������ �����Ǿ����ϴ�.");
	}//modify

	
	
	public void changeImg(JLabel jl, int imgNumber) {
		boolean flag=false;
		FileDialog fd = new FileDialog(cimv, "�̹��� ����", FileDialog.LOAD);
		fd.setVisible(true);

		path = fd.getDirectory();
		name = fd.getFile();

		if (path != null) {
			String[] extFlag= { "jpg", "gif", "jpeg", "png", "bmp" };
			for(String ext : extFlag) {
				if( name.toLowerCase().endsWith(ext)) {
					flag=true;
				}//end if
			}//end for
			
			if(flag) {
				if( imgNumber == 1 ) {
					uploadImg1 = path + name;
					cimv.getJlImg1().setIcon(new ImageIcon(uploadImg1));
				}else if( imgNumber == 2 ) {
					uploadImg2 = path + name;
					cimv.getJlImg2().setIcon(new ImageIcon(uploadImg2));
				}else if( imgNumber == 3 ) {
					uploadImg3 = path + name;
					cimv.getJlImg3().setIcon(new ImageIcon(uploadImg3));
				}else if( imgNumber == 4 ) {
					uploadImg4 = path + name;
					cimv.getJlImg4().setIcon(new ImageIcon(uploadImg4));
				}//end else if
			}else {
				JOptionPane.showMessageDialog(cimv, name+"�� �̹��� ������ �ƴմϴ�.");
			}
		System.out.println("�̹��� ����");
	}//end if
		
	}//changeImg
	
	@Override
	public void mouseClicked(MouseEvent me) {
		if (me.getSource() == cimv.getJlImg1()) {
			changeImg(cimv.getJlImg1(), 1  );
			
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
