package admin.controller;

import java.awt.FileDialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import javax.swing.JOptionPane;

import admin.dao.AdminDAO;
import admin.util.AdminUtil;
import admin.view.AdminMgMtView;
import admin.view.EeModifyView;
import admin.view.ModifyExtView;
import admin.vo.EeInfoVO;
import admin.vo.EeModifyVO;

public class EeModifyController extends WindowAdapter implements ActionListener {

	private EeModifyView emv;
	private AdminMgMtView ammv;
	private AdminMgMtController ammc;
	private EeInfoVO eivo;
	private AdminUtil au;
	
	private File changeImgFile; 
	private File changeExtFile;
	private boolean changeImgFlag;
	private boolean changeExtFlag;

	private Socket client;
	private DataOutputStream dos;
	private DataInputStream dis;
	private FileInputStream fis;
	private FileOutputStream fos;
	
	public EeModifyController(EeModifyView emv, AdminMgMtView ammv, AdminMgMtController ammc, EeInfoVO eivo) {
		this.emv = emv;
		this.ammv = ammv;
		this.ammc = ammc;
		this.eivo = eivo;
		au = new AdminUtil();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == emv.getJbCancel()) {
			emv.dispose();
			ammc.setEe();
		}
		
		if (e.getSource() == emv.getJbModify()) {
			try {
				modifyEe();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		
		if (e.getSource() == emv.getJbRemove()) {
			switch(JOptionPane.showConfirmDialog(emv, "�⺻������ ���� �����Ͻðڽ��ϱ�?")) {
			case JOptionPane.OK_OPTION:
				try {
					removeEe();
				} catch (UnknownHostException e1) {
					e1.printStackTrace();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				break;
			}
		}
		
		if (e.getSource() == emv.getJbChangeExt()) {
			changeExt();
		}
		
		if (e.getSource() == emv.getJbChangeImg()) {
			changeImg();
		}
 	}
	
	
	/**
	 * ȸ�� ���� ���� �޼ҵ�
	 * @throws IOException
	 */
	public void modifyEe() throws IOException {
		// �̹���, �̷¼����� ����Ǿ����� Ȯ�� - flag����
		String name = emv.getJtfName().getText().trim();
		
		if (name.equals("")) {
			msgCenter("�̸��� �Է����ּ���.");
			emv.getJtfName().requestFocus();
			return;
		}

		String rank = emv.getJcbRank().getSelectedItem().equals("����") ? "N" : "C";
		String loc = (String)emv.getJcbLoc().getSelectedItem();
		String education = (String)emv.getJcbEducation().getSelectedItem();
		String portfolio = emv.getJcbPortfolio().getSelectedItem().equals("YES") ? "Y" : "N";
		
		String img = eivo.getImg();
		if (changeImgFlag) { 
			img = System.currentTimeMillis()+changeImgFile.getName(); // �̸��� ����ũ�ϰ� ����
		}
		
		String extResume = eivo.getExtResume();
		if (changeExtFlag) {
			extResume = System.currentTimeMillis()+emv.getJtfExtRsm().getText().trim(); // �̸��� ����ũ�ϰ� ����
		}
		
		EeModifyVO emvo = new EeModifyVO(eivo.getEeNum(), img, rank, loc, education, portfolio, extResume);
		
		try {
			if(AdminDAO.getInstance().updateEe(emvo)) { // ������ �����Ϸ�
				
				if (changeImgFlag) {
					// ee�� no_img�ϸ��� �����Ƿ� ������ �̹����� no_img���� üũ ���ص� ��
					File originImg = new File("C:/dev/1949/03.����/src/admin/img/ee/"+eivo.getImg());
					originImg.delete();
					au.deleteFile(eivo.getImg(), "ee", client, dos, dis); // ���� �̹����� FS���� ����
					au.addNewFile(img, changeImgFile, "ee", client, dos, dis, fis); // ���ο� �̹����� ����
					au.reqFile(img, "ee", client, dos, dis, fos); // ���ο� �̹����� FS���� ��û, ����
				}
				
				if (changeExtFlag) { // �̷¼��� Admin Server�� ������ �ʿ� ����
					// ���� �̷¼��� FS���� ����
					System.out.println("---"+eivo.getExtResume());
					if (eivo.getExtResume() != null) {
						au.deleteFile(eivo.getExtResume(), "ext", client, dos, dis);
					}
					// ����� �̷¼��� FS�� �߰� 
					au.addNewFile(extResume, changeExtFile, "ext", client, dos, dis, fis);
				}
				
				msgCenter("�⺻������ �����߽��ϴ�.");
				au.sendLog(eivo.getEeNum()+" �⺻ ���� ����");
				emv.dispose();
				
				EeInfoVO newEivo = AdminDAO.getInstance().selectOneEe(eivo.getEeNum());
				
				ammc.setEe();
				new EeModifyView(ammv, newEivo, ammc);
			}
		} catch (SQLException e) {
			msgCenter("DB�� ���� �߻�");
			e.printStackTrace();
		}
	}
	
	private void msgCenter(String msg) {
		JOptionPane.showMessageDialog(emv, msg);
	}
	
	/**
	 * ȸ�� ���� ���� �޼ҵ�
	 * @throws UnknownHostException
	 * @throws IOException
	 */
	public void removeEe() throws UnknownHostException, IOException {
		if(AdminDAO.getInstance().deleteEe(eivo)) {
			
			File originImg = new File("C:/dev/1949/03.����/src/admin/img/ee/"+eivo.getImg());
			originImg.delete(); 
			au.deleteFile(eivo.getExtResume(), "ext", client, dos, dis);
			au.deleteFile(eivo.getImg(), "ee", client, dos, dis);
			
			msgCenter(eivo.getEeNum()+"�⺻������ �����Ǿ����ϴ�.");
			au.sendLog(eivo.getEeNum()+" �⺻ ���� ����");
			emv.dispose();
			ammc.setEe();
		}
	}
	
	/**
	 * �̹����� �����ϴ� �޼ҵ�
	 * ���� �� changeImgFlag�� true�� ����, changeImgFile�� ����
	 */
	public void changeImg() {
		// �̸��� ��θ� ����, modifyEe�� ����� �� �̹����� ����
		FileDialog fd = new FileDialog(emv, "�̹��� ����", FileDialog.LOAD);
		fd.setVisible(true);
		
		if (fd.getDirectory() != null && fd.getName() != null) {
			if (fd.getFile().toLowerCase().endsWith(".png") || 
					fd.getFile().toLowerCase().endsWith(".jpg") || 
					fd.getFile().toLowerCase().endsWith(".jpeg") ||
					fd.getFile().toLowerCase().endsWith(".gif")) {
				changeImgFile = new File(fd.getDirectory()+fd.getFile());
				changeImgFlag = true;
				
				emv.getJlImg().setIcon(new ImageIcon(changeImgFile.getAbsolutePath()));
			} else {
				msgCenter("Ȯ���ڰ� png, jpg, jpeg, gif�� ���ϸ� ��ϰ����մϴ�.");
			}
		}
	}
	
	/**
	 * �̷¼��� �����ϴ� �޼ҵ�
	 */
	public void changeExt() {
		new ModifyExtView(emv, this);
	}
	
	@Override
	public void windowClosing(WindowEvent e) {
		emv.dispose();
	}

	public File getChangeExtFile() {
		return changeExtFile;
	}

	public void setChangeExtFile(File changeExtFile) {
		this.changeExtFile = changeExtFile;
	}

	public boolean isChangeExtFlag() {
		return changeExtFlag;
	}
	public void setChangeExtFlag(boolean changeExtFlag) {
		this.changeExtFlag = changeExtFlag;
	}
}
