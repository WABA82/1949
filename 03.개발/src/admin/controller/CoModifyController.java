package admin.controller;

import java.awt.FileDialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import admin.dao.AdminDAO;
import admin.util.AdminUtil;
import admin.view.AdminMgMtView;
import admin.view.CoModifyView;
import admin.view.UserModifyView;
import admin.vo.CoInfoVO;
import admin.vo.CoModifyVO;
import admin.vo.UserInfoVO;
import sun.applet.resources.MsgAppletViewer_zh_CN;

public class CoModifyController extends WindowAdapter implements MouseListener, ActionListener {
	
	private static final int DBL_CLICK = 2;
	
	private CoModifyView cmv;
	private AdminMgMtView ammv;
	private CoInfoVO civo;
	private AdminMgMtController ammc;
	private AdminUtil au;
	
	private File newImg1, newImg2, newImg3, newImg4;
	
	private Socket client;
	private DataOutputStream dos;
	private DataInputStream dis;
	private FileInputStream fis;
	private FileOutputStream fos;
	
	public CoModifyController(CoModifyView cmv, AdminMgMtView ammv, CoInfoVO civo, AdminMgMtController ammc) {
		this.cmv= cmv;
		this.ammv = ammv;
		this.civo = civo;
		this.ammc = ammc;
		au = new AdminUtil();
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
			try {
				modify();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else if(ae.getSource()==cmv.getJbRemove()){
			try {
				remove();
			} catch (UnknownHostException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else if(ae.getSource()==cmv.getJbClose()) {
			cmv.dispose();
		}//��ư
	}
	
	/**
	 * ������ ���� �޼ҵ�
	 * @param estDate
	 * @return
	 */
	private boolean chkEstDate(String estDate) {
		boolean flag = false;

		int yyyy = 0;
		int mm = 0;
		int dd = 0;
		
		String number = estDate.replaceAll("-", "");
		
		try {
			Integer.parseInt(number);
			yyyy = Integer.parseInt(number.substring(0, 4));
			mm = Integer.parseInt(number.substring(4,6));
			dd = Integer.parseInt(number.substring(6,8));
			
		} catch (NumberFormatException npe) {
			flag = false;
		}
		
		return flag;
	}
	
	/**
	 * no_img������ ������ �������� �����ϴ� �޼ҵ�
	 * @param fileName
	 * @return
	 */
	public boolean checkNoImg(String fileName) {
		boolean flag = false;
		
		String[] noImgNames = {"no_co_img2.png", "no_co_img3.png", "no_co_img1.png", "no_co_img4.png", "no_ee_img.png" };
		for(int i=0; i<noImgNames.length; i++) {
			if(fileName.equals(noImgNames[i])) {
				flag = true;
			}
		}
		
		return flag;
	}

	public void modify() throws IOException {
		try {
			String coNum = civo.getCoNum();
			String coName = cmv.getJtfCoName().getText().trim();
			String estDate = cmv.getJtfEstDate().getText().trim();
			
			if(chkEstDate(estDate)) {
				msgCenter("�������� �Է°��� Ȯ�����ּ���.\n�Է¿�)2019-01-01");
				return;
			} 
			
			String coDesc = cmv.getJtaCoDesc().getText().trim();
			
			String img1,img2,img3,img4 = "";
			
			// img ���� �� ���ο� �̹��������� ����
			img1 = civo.getImg1();
			if (newImg1 != null) {
				img1 = System.currentTimeMillis()+newImg1.getName();
			}
			img2 = civo.getImg2();
			if (newImg2 != null) {
				img2 = System.currentTimeMillis()+newImg2.getName();
			}
			img3 = civo.getImg3();
			if (newImg3 != null) {
				img3 = System.currentTimeMillis()+newImg3.getName();
			}
			img4 = civo.getImg4();
			if (newImg4 != null) {
				img4 = System.currentTimeMillis()+newImg4.getName();
			}
			
			int memberNum = 0;
			try {
				memberNum = Integer.parseInt(cmv.getMemberNum().getText().trim());
			} catch (NumberFormatException npe) {
				msgCenter("������� ���ڸ� �Է°����մϴ�.");
				return;
			}
			
			CoModifyVO cmvo = new CoModifyVO(coNum, coName, estDate, coDesc, 
					img1, img2, img3, img4, memberNum);
		
			if(AdminDAO.getInstance().updateCo(cmvo)) {
				// ����� �̹����� �����ϰ� ���ο� �̹����� ����
				if (newImg1 != null) {
					// 1. ���� �����ϴ� �̹��� �����
					File originFile = new File("C:/dev/1949/03.����/src/admin/img/co/"+civo.getImg1());
					
					if (!checkNoImg(civo.getImg1())) { // noImg�������� �Ǵ�, noImg�� �ƴϸ� ����
						originFile.delete();
						// 2. ���� ������ �����ϴ� �̹����� �����
						au.deleteFile(civo.getImg1(), "co", client, dos, dis);;
					}
					
					// 3. ���� ������ �� ������ ����
					au.addNewFile(img1, newImg1, "co", client, dos, dis, fis);
					
					// 4. ���� ������ �� ������ ��û
					au.reqFile(img1, "co", client, dos, dis, fos);
				}
				if (newImg2 != null) {
					// 1. ���� �����ϴ� �̹��� �����
					File originFile = new File("C:/dev/1949/03.����/src/admin/img/co/"+civo.getImg2());
					
					if (!checkNoImg(civo.getImg2())) {
						originFile.delete();
						// 2. ���� ������ �����ϴ� �̹����� �����
						au.deleteFile(civo.getImg2(), "co", client, dos, dis);
					}
					
					// 3. ���� ������ �� ������ ����
					au.addNewFile(img2, newImg2, "co", client, dos, dis, fis);
					
					// 4. ���� ������ �� ������ ��û
					au.reqFile(img2, "co", client, dos, dis, fos);
				}
				if (newImg3 != null) {
					// 1. ���� �����ϴ� �̹��� �����
					File originFile = new File("C:/dev/1949/03.����/src/admin/img/co/"+civo.getImg3());
					
					if (!checkNoImg(civo.getImg3())) {
						originFile.delete();
						// 2. ���� ������ �����ϴ� �̹����� �����
						au.deleteFile(civo.getImg3(), "co", client, dos, dis);
					}
					
					// 3. ���� ������ �� ������ ����
					au.addNewFile(img3, newImg3, "co", client, dos, dis, fis);
					
					// 4. ���� ������ �� ������ ��û
					au.reqFile(img3, "co", client, dos, dis, fos);
				}
				if (newImg4 != null) {
					// 1. ���� �����ϴ� �̹��� �����
					File originFile = new File("C:/dev/1949/03.����/src/admin/img/co/"+civo.getImg4());
					
					if (!checkNoImg(civo.getImg4())) {
						originFile.delete();
						// 2. ���� ������ �����ϴ� �̹����� �����
						au.deleteFile(civo.getImg4(), "co", client, dos, dis);
					}
					
					// 3. ���� ������ �� ������ ����
					au.addNewFile(img4, newImg4, "co", client, dos, dis, fis);
					
					// 4. ���� ������ �� ������ ��û
					au.reqFile(img4, "co", client, dos, dis, fos);
				}
				
				
				msgCenter("ȸ�������� �����Ǿ����ϴ�.");
				au.sendLog(civo.getCoNum()+" ȸ�� ���� ����");
				cmv.dispose();
				
				CoInfoVO newCivo = AdminDAO.getInstance().selectOneCo(coNum);
				
				ammc.setCo();
				new CoModifyView(ammv, newCivo, ammc);
			} 
		} catch (SQLException e) {
			msgCenter("DB�� ������ �߻��߽��ϴ�.");
			e.printStackTrace();
		}
	}//modify
	

	/**
	 * ��� ������ �����ϴ� �޼ҵ�
	 * @throws UnknownHostException
	 * @throws IOException
	 */
	public void remove() throws UnknownHostException, IOException { 
		switch (JOptionPane.showConfirmDialog(cmv, "���� �����Ͻðڽ��ϱ�?")) {
		case JOptionPane.OK_OPTION:
			try {
				if (AdminDAO.getInstance().deleteCo(civo.getErId())) { // DB ������ ����
					
					File originFile1 = new File("C:/dev/1949/03.����/src/admin/img/co/"+civo.getImg1());
					File originFile2 = new File("C:/dev/1949/03.����/src/admin/img/co/"+civo.getImg2());
					File originFile3 = new File("C:/dev/1949/03.����/src/admin/img/co/"+civo.getImg3());
					File originFile4 = new File("C:/dev/1949/03.����/src/admin/img/co/"+civo.getImg4());
					
					// ���� �̹��� ���� + FS ������ ����
					if (!checkNoImg(civo.getImg1())) { // noImg�������� �Ǵ�, noImg�� �ƴϸ� ����
						originFile1.delete();
						// 2. ���� ������ �����ϴ� �̹����� �����
						au.deleteFile(civo.getImg1(), "co", client, dos, dis);
					}
					if (!checkNoImg(civo.getImg2())) { // noImg�������� �Ǵ�, noImg�� �ƴϸ� ����
						originFile2.delete();
						// 2. ���� ������ �����ϴ� �̹����� �����
						au.deleteFile(civo.getImg2(), "co", client, dos, dis);
					}
					if (!checkNoImg(civo.getImg3())) { // noImg�������� �Ǵ�, noImg�� �ƴϸ� ����
						originFile3.delete();
						// 2. ���� ������ �����ϴ� �̹����� �����
						au.deleteFile(civo.getImg3(), "co", client, dos, dis);
					}
					if (!checkNoImg(civo.getImg4())) { // noImg�������� �Ǵ�, noImg�� �ƴϸ� ����
						originFile4.delete();
						// 2. ���� ������ �����ϴ� �̹����� �����
						au.deleteFile(civo.getImg4(), "co", client, dos, dis);
					}
					
					msgCenter("ȸ�� ������ �����Ǿ����ϴ�.");
					au.sendLog(civo.getCoNum()+" ȸ�� ���� ����");
					cmv.dispose();
					ammc.setCo();
				}
			} catch (SQLException e) {
				msgCenter("DB�� ���� �߻�");
				e.printStackTrace();
			}
			break;
		}
		
	}//remove 
	
	public void changeImg(JLabel jl, int imgNum) {
		
		// �̹��� ����, ���� �̹������ ���ο� �̹������� �ν��Ͻ��� ����
		FileDialog fd = new FileDialog(cmv, "�̹��� ����", FileDialog.LOAD);
		fd.setVisible(true);
		
		String[] arrExt = { "jpg", "png", "jpeg", "gif" };
		
		String selectedFileExt = "";
		try {
			selectedFileExt = fd.getFile().substring(fd.getFile().lastIndexOf(".")+1);
		} catch (NullPointerException e) {
			// ���� ������ ������� �� NullPointException ó�� 
			return;
		}
		
		boolean flag = false;
		
		for(String ext : arrExt) {
			if (selectedFileExt.equals(ext)) {
				flag = true;
			}
		}
		
		File newImgFile = null;
		if (flag) {
			newImgFile = new File(fd.getDirectory()+fd.getFile());
			
			switch(imgNum) {
			case 1:
				cmv.getJlImg1().setIcon(new ImageIcon(newImgFile.getAbsolutePath()));
				newImg1 = newImgFile;
				break;
			case 2:
				cmv.getJlImg2().setIcon(new ImageIcon(newImgFile.getAbsolutePath()));
				newImg2 = newImgFile;
				break;
			case 3:
				cmv.getJlImg3().setIcon(new ImageIcon(newImgFile.getAbsolutePath()));
				newImg3 = newImgFile;
				break;
			case 4:
				cmv.getJlImg4().setIcon(new ImageIcon(newImgFile.getAbsolutePath()));
				newImg4 = newImgFile;
				break;
			}
		} else {
			msgCenter("Ȯ���ڰ� png, jpg, jpeg, gif�� ���ϸ� ��ϰ����մϴ�.");
		}
	}//changeImg
	
	@Override
	public void mouseClicked(MouseEvent e) {
		switch(e.getClickCount()) {
		case DBL_CLICK: // �̹��� ����Ŭ������ ����
			
			if(e.getSource() == cmv.getJlImg1()) {
				changeImg(cmv.getJlImg1(), 1);
			}
			if(e.getSource() == cmv.getJlImg2()) {
				changeImg(cmv.getJlImg2(), 2);
			}
			if(e.getSource() == cmv.getJlImg3()) {
				changeImg(cmv.getJlImg3(), 3);
			}
			if(e.getSource() == cmv.getJlImg4()) {
				changeImg(cmv.getJlImg4(), 4);
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
