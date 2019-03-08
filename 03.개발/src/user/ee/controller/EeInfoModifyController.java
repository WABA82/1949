package user.ee.controller;

import java.awt.FileDialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
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
import user.ee.view.EeInfoModifyView;
import user.ee.view.EeMainView;
import user.ee.view.ModifyExtView;
import user.ee.vo.EeInfoVO;
import user.ee.vo.EeModifyVO;
import user.util.UserLog;
import user.util.UserUtil;

public class EeInfoModifyController extends WindowAdapter implements ActionListener {

	private EeInfoVO eivo;
	private EeInfoModifyView eimv;
	
	private File uploadImg;
	private File uploadExt;
	private boolean imgChgFlag;
	private boolean extChgFlag;
	
	private UserUtil uu;
	private UserLog ul;
	
	private EeDAO eedao;
	private EeMainView emv;
	
	public EeInfoModifyController(EeInfoModifyView eimv, EeInfoVO eivo, EeMainView emv) {
		this.eimv = eimv;
		this.eivo=eivo;
		this.emv=emv;
		eedao=EeDAO.getInstance();
		uu = new UserUtil();
		ul = new UserLog();
	}

	// �̷¼� �����ϴ� Method
	private void modifyEe() throws IOException {

		String tempRank = eimv.getJcbRank().getSelectedItem().toString();
		String rank = tempRank.equals("����") ? "C" : "N";
		String loc = eimv.getJcbLoc().getSelectedItem().toString();
		String education = (String) eimv.getJcbEducation().getSelectedItem();
		String tempPortfolio = eimv.getJcbPortfolio().getSelectedItem().toString();
		String portfolio = tempPortfolio.equals("YES") ? "Y" : "N";
		String extResume = eimv.getJtfExtResume().getText();
		StringBuilder updateMsg= new StringBuilder();
	
		if(!imgChgFlag) {
			uploadImg = new File("C:/dev/1949/03.����/src/user/img/ee/" + eivo.getImg());
		}//end if
		
		updateMsg
		.append("�������� ���� ���� \n")
		.append("�̸� : "+eivo.getName()+"\n")
		.append("���� : "+tempRank+"\n")
		.append("�ٹ����� : "+loc+"\n")
		.append("�з� : "+education+"\n")
		.append("���� : "+eivo.getAge()+"\n")
		.append("���������� ���� : "+tempPortfolio+"\n")
		.append("���� : "+eivo.getGender()+"\n")
		.append("�ܺ��̷¼� : "+extResume+"\n")
		.append("���� ������ �����Ͻðڽ��ϱ�?");
		
		switch(JOptionPane.showConfirmDialog(eimv, updateMsg)) {
		case JOptionPane.OK_OPTION :
			System.out.println("---�̹����� : "+uploadImg.getName());
			
			String imgName = "";
			String extName = "";
			
			if(imgChgFlag) {
				imgName = System.currentTimeMillis()+uploadImg.getName();
			} else {
				imgName = eivo.getImg();
			}
			
			if(extChgFlag) {
				extName = System.currentTimeMillis()+uploadExt.getName();
			} else {
				extName = eivo.getExtResume();
			}

			EeModifyVO emvo = new EeModifyVO(eivo.getEeNum(), imgName,
					rank,loc,education, portfolio, extName);
			
			System.out.println(emvo);
			try {
				if(eedao.updateEeInfo(emvo)) {
					JOptionPane.showMessageDialog(eimv, "���������� ����Ǿ����ϴ�.");
					
					///////////////////////// 0302 ���� �̹��� FS�� �߰���� ���� ////////////////////////////////
					Socket client = null;
					DataOutputStream dos = null;
					DataInputStream dis = null;
					FileInputStream fis = null;
					FileOutputStream fos = null;

					if (imgChgFlag) {
						File original = new File("C:/dev/1949/03.����/src/user/img/ee/"+eivo.getImg());
						original.delete();
						
						System.out.println("getImg = "+eivo.getImg());
						// ���ο� �̹��� ���� FileServer�� ����, �߰�, ��û
						System.out.println("111");
						uu.deleteFile(eivo.getImg(), "ee", client, dos, dis);
						System.out.println("--- ���� �̹��� ����");
						
						uu.addNewFile(imgName, uploadImg, "ee", client, dos, dis, fis); // ���ο� �̹����� ����
						System.out.println("--- �̹������� ����");
						
						// FilerServer�κ��� �̹��� ��û
						uu.reqFile(imgName, "ee", client, dos, dis, fos); // ���ο� �̹����� FS���� ��û, ����
						System.out.println("--- �̹������� ��û");
					}
					
					if (extChgFlag && !"".equals(eimv.getJtfExtResume().getText().trim())) {
						// ���ο� �̷¼� ������ �����ϸ� FileServer�� ����, �߰�
						uu.deleteFile(eivo.getExtResume(), "ext", client, dos, dis);
						System.out.println("--- �����̷¼� ����");
						
						uu.addNewFile(extName, uploadExt, "ext", client, dos, dis, fis);
						System.out.println("--- �̷¼� ���� ����");
					}
					
					ul.sendLog(eivo.getEeId(), "�⺻���� ����");
					
					//����â ������ ������Ʈ�� ����
					EeMainVO updateEmvo=CommonDAO.getInstance().selectEeMain(eivo.getEeId(), "Y");
					new EeMainView(updateEmvo);
					emv.dispose();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		
		//������ ������ �����ִ�â.
		}//end switch
		
	}// modifyEe

	/**
	 * �̹����� �ٲٴ� method
	 */
	private void changeImg() {
		boolean flag = false;
		
		FileDialog fd = new FileDialog(eimv, "���Ͽ���", FileDialog.LOAD);
		fd.setVisible(true);
		
		String path = fd.getDirectory();
		String name = fd.getFile();
		
		if (path == null || name == null) {
			return;
		} 	
		
		if(name.endsWith(".jpg") || name.endsWith(".jpeg") || name.endsWith(".png") || 
				name.endsWith(".bmp") || name.endsWith(".gif")) {
			flag=true;
		}//end if
		
	 	if(flag) {
	 		imgChgFlag = true;
	 		uploadImg =  new File(path + name);
	 		System.out.println("upload img : "+uploadImg.getAbsolutePath());
	 		eimv.getJlImag().setIcon(new ImageIcon(uploadImg.getAbsolutePath()));
		} else {
			JOptionPane.showMessageDialog(eimv, name + "�� ����Ҽ� �����ϴ�.");
		}//else if
	}// changeImg

	@Override
	public void actionPerformed(ActionEvent ae) {

		if (ae.getSource() == eimv.getJbModify()) {
				try {
					modifyEe();
					eimv.dispose();
				} catch (IOException e) {
					e.printStackTrace();
				}
		} // end if

		if (ae.getSource() == eimv.getJbModifyImg()) {
			changeImg();
		} // end if

		if (ae.getSource() == eimv.getJbModifyExt()) {
			new ModifyExtView(eimv, null, null, eimv, this, "modi");
		}

		if(ae.getSource() == eimv.getJbClose()) {
			eimv.dispose();
		}
	}// actionPerformed

	public void setUploadExt(File uploadExt) {
		this.uploadExt = uploadExt;
	}

	public void setExtChgFlag(boolean extChgFlag) {
		this.extChgFlag = extChgFlag;
	}
}
