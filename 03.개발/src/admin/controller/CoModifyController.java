package admin.controller;

import java.awt.FileDialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import admin.dao.AdminDAO;
import admin.view.AdminMgMtView;
import admin.view.CoModifyView;
import admin.view.UserModifyView;
import admin.vo.CoInfoVO;
import admin.vo.CoModifyVO;
import admin.vo.UserInfoVO;
import sun.applet.resources.MsgAppletViewer_zh_CN;

public class CoModifyController extends WindowAdapter implements MouseListener, ActionListener {
	private CoModifyView cmv;
	private AdminMgMtView ammv;
	private CoInfoVO civo;
	
	private String newImg1, newImg2, newImg3, newImg4;
	
	private static final int DBL_CLICK = 2;
	
	public CoModifyController(CoModifyView cmv, AdminMgMtView ammv, CoInfoVO civo) {
		this.cmv= cmv;
		this.ammv = ammv;
		this.civo = civo;
		
		newImg1=""; newImg2=""; newImg3=""; newImg4="";
	}//������ ��
	
	@Override
	public void windowClosing(WindowEvent e) {
		cmv.dispose();
	}
	
	private void msgCenter(String msg) {
		JOptionPane.showMessageDialog(cmv, msg);
	}
	
	@Override
	public void actionPerformed(ActionEvent ae) {
		if(ae.getSource()==cmv.getJbModify()) {
			modify();
		}else if(ae.getSource()==cmv.getJbRemove()){
			remove();
		}else if(ae.getSource()==cmv.getJbClose()) {
			cmv.dispose();
		}//��ư
	}

	public void modify() {
		try {
			System.out.println("����");
			
			String coNum = civo.getCoNum();
			String coName = cmv.getJtfCoName().getText().trim();
			// ��¥�� ����ȭ �ϴ� �޼ҵ� �ʿ�(yyyy-mm-dd)
			// �޼ҵ� ó�� �� �� �Ҵ�
			String estDate = cmv.getJtfEstDate().getText().trim();
			String coDesc = cmv.getJtaCoDesc().getText().trim();
			
			String img1,img2,img3,img4 = "";
			// img ���� �� ���ο� �̹����� �߰�
			if (newImg1.equals("")) {
				img1 = civo.getImg1();
			} else {
				img1 = newImg1;
			}
			if (newImg2.equals("")) {
				img2 = civo.getImg2();
			} else {
				img2 = newImg2;
			}
			if (newImg3.equals("")) {
				img3 = civo.getImg3();
			} else {
				img3 = newImg3;
			}
			if (newImg4.equals("")) {
				img4 = civo.getImg4();
			} else {
				img4 = newImg4;
			}
			
			// NumberFormatException ����ó�� �ʿ�
			int memberNum = Integer.parseInt(cmv.getMemberNum().getText().trim());
			
			CoModifyVO cmvo = new CoModifyVO(coNum, coName, estDate, coDesc, 
					img1, img2, img3, img4, memberNum);
		
			// DB�� ������ �����ϰ� �̹����� fileServer�� �����ϴ� �޼ҵ� �ʿ� ///////////
			if(AdminDAO.getInstance().updateCo(cmvo)) {
				
				msgCenter("ȸ�������� �����Ǿ����ϴ�.");
				cmv.dispose();
				CoInfoVO clvo = AdminDAO.getInstance().selectOneCo(coNum);
				new CoModifyView(ammv, clvo);
			} else {
				msgCenter("ȸ������ ���濡 �����߽��ϴ�.");
			}
		} catch (SQLException e) {
			msgCenter("DB�� ������ �߻��߽��ϴ�.");
			e.printStackTrace();
		}
	}//modify
	
	public void remove() { //////////////////// ������ ������ �۾� ///////////////////////
		System.out.println("����");
	}//remove /////////////////////////////////////////////////////////////////////////////
	
	public void changeImg(JLabel jl) {
		
		// �̹��� ����, ���� �̹������ ���ο� �̹������� �ν��Ͻ��� ����
		
		FileDialog fd = new FileDialog(cmv, "�̹��� ����", FileDialog.LOAD);
		fd.setVisible(true);
		
		System.out.println(fd.getDirectory());
		System.out.println(fd.getFile());
		
		String[] arryExt = { "jpg", "png", "jpeg", "gif" };
		String selectedFileExt = fd.getFile().substring(fd.getFile().lastIndexOf(".")+1);
		
		
		
		
	}//changeImg
	
	@Override
	public void mouseClicked(MouseEvent e) {
		switch(e.getClickCount()) {
		case DBL_CLICK: // �̹��� ����Ŭ������ ����
			
			if(e.getSource() == cmv.getJlImg1()) {
				changeImg(cmv.getJlImg1());
			}
			
			if(e.getSource() == cmv.getJlImg2()) {
				changeImg(cmv.getJlImg2());
				
			}
			
			if(e.getSource() == cmv.getJlImg3()) {
				changeImg(cmv.getJlImg3());
				
			}
			
			if(e.getSource() == cmv.getJlImg4()) {
				changeImg(cmv.getJlImg4());
			}
			
			break;
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {}
	@Override
	public void mouseReleased(MouseEvent e) {}
	@Override
	public void mouseEntered(MouseEvent e) {}
	@Override
	public void mouseExited(MouseEvent e) {}
}
