package user.ee.controller;

import java.awt.FileDialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
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

	// ������.
	public EeInfoRegController(EeInfoRegView eirv, String eeId, EeMainView emv) {
		this.eirv = eirv;
		this.eeId = eeId;
		this.emv=emv;
		eedao = EeDAO.getInstance();
		
	}// ������

	
	public void register() {
		boolean insertFlag =false;
		
		// �̹��� ��ȿ�� ���� ��Ͼ��ҽ� �⺻�������� ���
		if (uploadImg == null) {
			uploadImg=new File("C:/dev/1949/03.����/src/user/img/ee/no_ee_img.png");
			//JOptionPane.showMessageDialog(eirv, "�̹����� �ʼ� �Դϴ�.");
		} // register(String eeid)

		String tempRank = eirv.getJcbRank().getSelectedItem().toString();
		String rank = tempRank.replace("����", "C").replaceAll("���", "N");
		String loc = eirv.getJcbLoc().getSelectedItem().toString();
		String education = eirv.getJcbEducation().getSelectedItem().toString();
		String tempPortfolio = eirv.getJcbPortfolio().getSelectedItem().toString();
		String portfolio = tempPortfolio.replaceAll("YES", "Y").replaceAll("NO", "N");
		String extResume = eirv.getJtfExtResume().getText();
		
			
		EeInsertVO eivo = new EeInsertVO(eeId, uploadImg.getName(), rank, loc, education, portfolio, extResume);
		System.out.println("-----------"+eivo);
			ActivationVO avo=new ActivationVO(eeId);
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
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}//end catch
			}//end if
			
			
	}// register

	public void changeImg() {
		boolean flag = false;
		FileDialog fd = new FileDialog(eirv, "�̹��� ����", FileDialog.LOAD);
		fd.setVisible(true);

		String path = fd.getDirectory();
		String name = fd.getFile();

		if (path != null) {
			if (name.endsWith(".jpg") || name.endsWith(".jpeg") || name.endsWith(".png") || name.endsWith(".bmp")
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
			changeImg();
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
