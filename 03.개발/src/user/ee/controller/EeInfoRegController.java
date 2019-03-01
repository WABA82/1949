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
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import sun.rmi.server.Activation;
import user.common.vo.EeMainVO;
import user.dao.CommonDAO;
import user.dao.EeDAO;
import user.ee.view.EeInfoRegView;
import user.ee.view.EeMainView;
import user.ee.view.ModifyExtView;
import user.ee.vo.ActivationVO;
import user.ee.vo.EeInsertVO;
import user.ee.vo.EeInterestAndAppVO;
import user.ee.vo.EeRegVO;
import user.file.FileUser;
import user.util.UserUtil;

public class EeInfoRegController extends WindowAdapter implements ActionListener {

	/* �ν��Ͻ����� */
	private EeInfoRegView eirv;
	private File uploadImg;
	private EeDAO eedao;
	private EeInfoRegController eirc;
	private Connection con;
	private String eeId;// �� ���̵�.
	private EeRegVO ervo;
	private EeMainVO emvo;
	private EeMainView emv;
	private UserUtil uu;

	// ������.
	public EeInfoRegController(EeInfoRegView eirv, String eeId, EeMainView emv) {
		this.eirv = eirv;
		this.eeId = eeId;
		this.emv=emv;
		eedao = EeDAO.getInstance();
		uu = new UserUtil();
	}// ������

	
	public void register() {
		boolean insertFlag =false;
		
		if (uploadImg == null) {
			JOptionPane.showMessageDialog(eirv, "�̹��� ����� �ʼ� �Դϴ�.\n(size : 150px * 200px)");
			return;
		} 

		String tempRank = eirv.getJcbRank().getSelectedItem().toString();
		String rank = tempRank.equals("����") ? "C" : "N";
		String loc = eirv.getJcbLoc().getSelectedItem().toString();
		String education = eirv.getJcbEducation().getSelectedItem().toString();
		String tempPortfolio = eirv.getJcbPortfolio().getSelectedItem().toString();
		String portfolio = tempPortfolio.equals("YES") ? "Y" : "N";
		String extResume = eirv.getJtfExtResume().getText();
		
		///////////////////////////////// �۾���
		
		String imgName = System.currentTimeMillis()+uploadImg.getName();
		
		EeInsertVO eivo = new EeInsertVO(eeId, imgName, rank, loc, education, portfolio, extResume);
		System.out.println("-----------"+eivo);
		ActivationVO avo = new ActivationVO(eeId);
		System.out.println(avo);
		
		try {
			insertFlag=	eedao.updateUserInfo(eivo, avo);
			if(insertFlag) {
				JOptionPane.showMessageDialog(eirv, "���������� ��� �Ǿ����ϴ�.");
				//â �ٽ� ����?
				
				EeMainVO updateEmvo=CommonDAO.getInstance().selectEeMain(eeId, "Y");
				new EeMainView(updateEmvo);
				emv.dispose();
				eirv.dispose();
				
			}//end if
			System.out.println("insert �� ���� true �ϱ��??"+insertFlag);
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(eirv, "DB���� �߻�");
			e.printStackTrace();
		}//end catch

		//�̹��� user.ee�� ����
		if( !uploadImg.getName().equals("")) {// ������ �̹����� �����ϴ� ���
			
			File original = new File("C:/dev/1949/03.����/src/file/eeImg/"+eirv.getJlImage());
			original.delete();
			try {
				FileUser.getInstance().uploadImgFile(uploadImg);
				FileUser.getInstance().eeInfoImgSend(uploadImg.getName());
				
				// ���� �³�? Ȯ���� ��..
				///////////////////////// 0301 ���� �̹��� FS�� �߰���� ���� ////////////////////////////////
				Socket client = null;
				DataOutputStream dos = null;
				DataInputStream dis = null;
				FileInputStream fis = null;
				FileOutputStream fos = null;
				
				// ���ο� ���� FileServer�� �߰�
				uu.addNewFile(uploadImg.getName(), uploadImg, "ee", client, dos, dis, fis); // ���ο� �̹����� ����
				// FilerServer�κ��� �̹��� ��û
				uu.reqFile(uploadImg.getName(), "ee", client, dos, dis, fos); // ���ο� �̹����� FS���� ��û, ����

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}//end catch
		}//end if
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
				
				///////////////////// �̹��� ���������� �÷��׸� ���� ////////////////////
				
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
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} // end if

		// �ܺ� �̷¼� ��� ��ư ������ ��.
		if (ae.getSource() == eirv.getJbRegisterExt()) {
			new ModifyExtView(eirv, eirc);
			}// end if

		// ��� ��ư ������ ��.
		if (ae.getSource() == eirv.getJbRegister()) {
			switch (JOptionPane.showConfirmDialog(eirv, "�⺻ ������ ��� �Ͻðڽ��ϱ�?")) {
			case JOptionPane.OK_OPTION:
				register();
			}// end switch
		} // end if

	}// actionPerformed

}// class
