package admin.controller;

import java.awt.FileDialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import admin.dao.AdminDAO;
import admin.view.AdminMgMtView;
import admin.view.EeModifyView;
import admin.view.ModifyExtView;
import admin.vo.EeInfoVO;
import user.ee.vo.EeModifyVO;

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
				removeEe();
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
			if(AdminDAO.getInstance().updateEe(emvo)) {
				
				msgCenter("�⺻������ �����߽��ϴ�.");
				
				// DB ������Ʈ �� �̹���, �̷¼� ���� �� ����
				if (changeImgFlag) { // �̹����� ����Ǿ��ٸ� ���� �̹��� ����, �� �̹��� ���ε�
					
					FileInputStream fis = null;
					FileOutputStream fos = null;
					try {
						
						// ���� ���� ���� oh99�� �׽�Ʈ �Ϸ�
						File originFile = new File("C:/dev/1949/03.����/src/file/eeImg/"+eivo.getImg());
						originFile.delete();
						
						// ���� ���� �߰� // ���ϼ��� �ϼ� �� ���� ���� ///////////////////////////////////////
						fis = new FileInputStream(changeImgFile);
						fos = new FileOutputStream("C:/dev/1949/03.����/src/file/eeImg/"+changeImgFile.getName());
						
						byte[] readData = new byte[512];
						int len = 0;
						while((len = fis.read(readData)) != -1) { 
							fos.write(readData,0,len);
							fos.flush();
						}
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					} finally {
						if (fos != null) { fos.close(); }
						if (fis != null) { fis.close(); }
					}
				}
				
				if (changeExtFlag) { // �̷¼��� ����Ǿ��ٸ� ���� �̷¼� ����� �� �̷¼� ����
					FileInputStream fis = null;
					FileOutputStream fos = null;
					try {
						
						// ���� ���� ���� 
						File originFile = new File("C:/dev/1949/03.����/src/file/resume/"+eivo.getExtResume());
						originFile.delete();
						
						// ���� ���� �߰� 
						fis = new FileInputStream(changeExtFile);
						fos = new FileOutputStream("C:/dev/1949/03.����/src/file/resume/"+changeExtFile.getName());
						
						byte[] readData = new byte[512];
						int len = 0;
						while((len = fis.read(readData)) != -1) { 
							fos.write(readData,0,len);
							fos.flush();
						}
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					} finally {
						if (fos != null) { fos.close(); }
						if (fis != null) { fis.close(); }
					}
				}
			}
		} catch (SQLException e) {
			msgCenter("DB�� ���� �߻�");
			e.printStackTrace();
		}
	}
	
	private void msgCenter(String msg) {
		JOptionPane.showMessageDialog(emv, msg);
	}
	
	public void removeEe() {
////////////���ϼ��� �ϼ� �� ���� ������ûó���ؾ� �� //////////////////////////////////////////////////
		if(AdminDAO.getInstance().deleteEe(eivo)) {
			msgCenter("�⺻������ �����Ǿ����ϴ�.");
			emv.dispose();
			ammc.setEe();
		} else {
			msgCenter("�⺻���� ������ �����߽��ϴ�.");
		}
	}
	
	public void changeImg() {
		// �̸��� ��θ� ����, modifyEe�� ����� �� �̹����� ����
		FileDialog fd = new FileDialog(emv, "�̹��� ����", FileDialog.LOAD);
		fd.setVisible(true);

		if (fd.getFile().toLowerCase().endsWith(".png") || 
				fd.getFile().toLowerCase().endsWith(".jpg") || 
				fd.getFile().toLowerCase().endsWith(".jpeg")) {
			changeImgFile = new File(fd.getDirectory()+fd.getFile());
			changeImgFlag = true;
			
			emv.getJlImg().setIcon(new ImageIcon(changeImgFile.getAbsolutePath()));
		} else {
			msgCenter("Ȯ���ڰ� png, jpg, jpeg�� ���ϸ� ��ϰ����մϴ�.");
		}
	}
	
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
