package user.ee.controller;

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
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import user.common.vo.EeMainVO;
import user.dao.CommonDAO;
import user.dao.EeDAO;
import user.ee.view.EeInfoRegView;
import user.ee.view.EeMainView;
import user.ee.view.ModifyExtView;
import user.ee.vo.EeInsertVO;
import user.util.UserLog;
import user.util.UserUtil;

public class EeInfoRegController extends WindowAdapter implements ActionListener {

	private EeInfoRegView eirv;
	private File uploadImg;
	private File uploadExt;
	private EeDAO eedao;
	private String eeId;
	private EeMainView emv;
	private UserUtil uu;
	private UserLog ul;

	// ������.
	public EeInfoRegController(EeInfoRegView eirv, String eeId, EeMainView emv) {
		this.eirv = eirv;
		this.eeId = eeId;
		this.emv=emv;
		eedao = EeDAO.getInstance();
		uu = new UserUtil();
		ul = new UserLog();
	}// ������

	
	public void register() {
		boolean insertFlag =false;
		
		if (uploadImg == null) {
			JOptionPane.showMessageDialog(eirv, "�̹��� ����� �ʼ� �Դϴ�.\n(size : 150px * 200px)");
			return;
		}//end if 

		String tempRank = eirv.getJcbRank().getSelectedItem().toString();
		String rank = tempRank.equals("����") ? "C" : "N";
		String loc = eirv.getJcbLoc().getSelectedItem().toString();
		String education = eirv.getJcbEducation().getSelectedItem().toString();
		String tempPortfolio = eirv.getJcbPortfolio().getSelectedItem().toString();
		String portfolio = tempPortfolio.equals("YES") ? "Y" : "N";
		String extResume = "".equals(eirv.getJtfExtResume().getText()) ? "" : eirv.getJtfExtResume().getText();
		
		if (!"".equals(extResume)) {
			extResume = System.currentTimeMillis()+extResume;
		}
		
		String imgName = System.currentTimeMillis()+uploadImg.getName();
		
		EeInsertVO eivo = new EeInsertVO(eeId, imgName, rank, loc, education, portfolio, extResume);
		// ActivationVO, FileUser(UserUtil ���) �Ƚᵵ �ɵ�..(���� 0302)
		
		try {
			insertFlag=	eedao.updateUserInfo(eivo);
			if(insertFlag) {
				JOptionPane.showMessageDialog(eirv, "���������� ��� �Ǿ����ϴ�.");
				
				try {
					///////////////////////// 0301 ���� �̹��� FS�� �߰���� ���� ////////////////////////////////
					Socket client = null;
					DataOutputStream dos = null;
					DataInputStream dis = null;
					FileInputStream fis = null;
					FileOutputStream fos = null;
					
					// ���ο� �̹��� ���� FileServer�� �߰�
					uu.addNewFile(imgName, uploadImg, "ee", client, dos, dis, fis); // ���ο� �̹����� ����
					// FilerServer�κ��� �̹��� ��û
					uu.reqFile(imgName, "ee", client, dos, dis, fos); // ���ο� �̹����� FS���� ��û, ����
					
					// ���ο� �̷¼� ������ �����ϸ� FileServer�� �߰�
					if (!"".equals(extResume)) {
						uu.addNewFile(extResume, uploadExt, "ext", client, dos, dis, fis);
					}
					
				String userNum=eedao.selectUserNum(eeId);
					ul.sendLog(eeId, "["+userNum+"] ���");
				} catch (IOException e) {
					e.printStackTrace();
				}//end catch
				
				EeMainVO updateEmvo=CommonDAO.getInstance().selectEeMain(eeId, "Y");
				new EeMainView(updateEmvo);
				emv.dispose();
				eirv.dispose();
				
			}//end if
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(eirv, "DB���� �߻�");
			e.printStackTrace();
		}//end catch
	}// register

	public void changeImg() throws IOException {
		boolean flag = false;
		FileDialog fd = new FileDialog(eirv, "�̹��� ����", FileDialog.LOAD);
		fd.setVisible(true);

		String path = fd.getDirectory();
		String name = fd.getFile();

		if (path != null) {
			if (name.endsWith(".jpg") || name.endsWith(".jpeg") 
					|| name.endsWith(".png") || name.endsWith(".bmp")
					|| name.endsWith(".gif")) {
				flag = true;
			} // end if

			if (flag) {
				uploadImg = new File(path + name);
				eirv.getJlImage().setIcon(new ImageIcon(uploadImg.getAbsolutePath()));
			} else {
				JOptionPane.showMessageDialog(eirv, name + "�� ����Ҽ� �����ϴ�.");
				return;
			} // end else
		} // end if
	}// changeImg
	
	@Override
	public void windowClosing(WindowEvent e) {
		eirv.dispose();
	}

	@Override
	public void actionPerformed(ActionEvent ae) {

		if (ae.getSource() == eirv.getJbClose()) {
			eirv.dispose();
		} // end if

		// �̹��� ���� ��ư ������ ��.
		if (ae.getSource() == eirv.getJbRegisterImg()) {
			try {
				changeImg();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} // end if

		// �ܺ� �̷¼� ��� ��ư ������ ��.
		if (ae.getSource() == eirv.getJbRegisterExt()) {
			new ModifyExtView(eirv, eirv, this, null, null, "reg");
		}// end if

		// ��� ��ư ������ ��.
		if (ae.getSource() == eirv.getJbRegister()) {
			switch (JOptionPane.showConfirmDialog(eirv, "�⺻ ������ ��� �Ͻðڽ��ϱ�?")) {
			case JOptionPane.OK_OPTION:
				register();
			}// end switch
		} // end if

	}// actionPerformed

	public void setUploadExt(File uploadExt) {
		this.uploadExt = uploadExt;
	}
}// class
