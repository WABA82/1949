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
	
	private File changeImgFile; 
	private File changeExtFile;
	private boolean changeImgFlag;
	private boolean changeExtFlag;
	
	public EeModifyController(EeModifyView emv, AdminMgMtView ammv, AdminMgMtController ammc, EeInfoVO eivo) {
		this.emv = emv;
		this.ammv = ammv;
		this.ammc = ammc;
		this.eivo = eivo;
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
	
	private Socket client;
	private DataOutputStream dos;
	private DataInputStream dis;
	private FileInputStream fis;
	private FileOutputStream fos;
	
	public void closeStreams() throws IOException {
		if (fos != null) {fos.close();}
		if (fis != null) {fis.close();}
		if (dos != null) { dos.close(); }
		if (dis != null) { dis.close(); }
		if (client != null) { client.close(); }
	}
	
	public void deleteFile(String fileName, String flag) throws UnknownHostException, IOException {
		client = new Socket("localhost", 7002);
		
		dos = new DataOutputStream(client.getOutputStream());
		dis = new DataInputStream(client.getInputStream());
		
		if (flag.equals("img")) {
			dos.writeUTF("eeImg_delete"); 
		} else if (flag.equals("ext")) {
			dos.writeUTF("ee_ext_delete");
		}
		dos.flush();
		
		dos.writeUTF(fileName);  // ���� �̹����� ����
		dos.flush();
		
		dis.readUTF(); // ���� �� ���� ����
		
		closeStreams();
	}
	
	public void addNewFile(File newFile, String flag) throws IOException {
		client = new Socket("localhost", 7002);
		
		dos = new DataOutputStream(client.getOutputStream());
		dis = new DataInputStream(client.getInputStream());
		
		if (flag.equals("img")) {
			dos.writeUTF("eeImg_register"); 
		} else if (flag.equals("ext")) {
			dos.writeUTF("ee_ext_register"); 
		}
		dos.flush();
		
		dos.writeUTF(newFile.getName()); // ���ο� �̹����� ����
		dos.flush();
		
		fis = new FileInputStream(newFile);
		
		byte[] readData = new byte[512];
		int len = 0;
		int arrCnt = 0;
		while((len = fis.read(readData)) != -1) {
			arrCnt++;
		}
		
		fis.close();

		dos.writeInt(arrCnt); // ������ ũ�� ����
		dos.flush();

		fis = new FileInputStream(newFile);
		
		len = 0;
		while((len = fis.read(readData)) != -1) {
			dos.write(readData, 0, len);
			dos.flush();
		}
		
		dis.readUTF(); // ����Ϸ� ���� ���� �� ���� ����
		closeStreams();
	}
	
	public void reqImg(String newFileName) throws IOException {
		client = new Socket("localhost", 7002);
		
		dos = new DataOutputStream(client.getOutputStream());
		dis = new DataInputStream(client.getInputStream());
		
		dos.writeUTF("eeImg_request");
		dos.flush();
		
		dos.writeUTF(newFileName);
		dos.flush();
		
		int arrCnt = dis.readInt();
		
		byte[] readData = new byte[512];
		int len = 0;
		
		fos = new FileOutputStream("C:/dev/1949/03.����/src/admin/img/ee/"+newFileName);
		
		for(int i=0; i<arrCnt; i++) {
			len = dis.read(readData);
			fos.write(readData,0,len);
			fos.flush();
		}
		
		dos.writeUTF("done");
		dos.flush();
		
		closeStreams();
	}
	
	public void modifyEe() throws IOException {
		// �̹���, �̷¼����� ����Ǿ����� Ȯ�� - flag����
		// �̹����� ����Ǿ��ٸ� img��Ű���� ���� ����(���ϼ��� �ϼ� �� ���濹��)
		// �̷¼��� ����Ǿ��ٸ� ���� �̷¼����� ���� �� ���ο� �̷¼����� ����
		// �� ���� ���� ������ DB���� update�޼ҵ�ó��
		
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
			img = changeImgFile.getName();
		}
		
		String extResume = eivo.getExtResume();
		if (changeExtFlag) {
			extResume = emv.getJtfExtRsm().getText().trim();
		}
		
		EeModifyVO emvo = new EeModifyVO(eivo.getEeNum(), img, rank, loc, education, portfolio, extResume);
		
		try {
			if(AdminDAO.getInstance().updateEe(emvo)) { // ������ �����Ϸ�
				
				if (changeImgFlag) {
					// ee�� no_img�ϸ��� �����Ƿ� ������ �̹����� no_img���� üũ ���ص� ��
					File originImg = new File("C:/dev/1949/03.����/src/admin/img/ee/"+eivo.getImg());
					originImg.delete();
					deleteFile(eivo.getImg(), "img"); // ���� �̹����� FS���� ����
					addNewFile(changeImgFile, "img"); // ���ο� �̹����� ����
					reqImg(changeImgFile.getName()); // ���ο� �̹����� FS���� ��û, ����
				}
				
				if (changeExtFlag) { // �̷¼��� Admin Server�� ������ �ʿ� ����
					// ���� �̷¼��� FS���� ����
					deleteFile(eivo.getExtResume(), "ext");
					// ����� �̷¼��� FS�� �߰� 
					addNewFile(changeExtFile, "ext");
				}
				
				msgCenter("�⺻������ �����߽��ϴ�.");
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
	
	public void removeEe() throws UnknownHostException, IOException {
		if(AdminDAO.getInstance().deleteEe(eivo)) {
			
			File originImg = new File("C:/dev/1949/03.����/src/admin/img/ee/"+eivo.getImg());
			originImg.delete();
			deleteFile(eivo.getImg(), "img");
			
			msgCenter("�⺻������ �����Ǿ����ϴ�.");
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
