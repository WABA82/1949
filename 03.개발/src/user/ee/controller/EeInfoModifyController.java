package user.ee.controller;

import java.awt.FileDialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.io.File;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import user.dao.EeDAO;
import user.ee.view.EeInfoModifyView;
import user.ee.vo.EeInfoVO;
import user.ee.vo.EeModifyVO;

public class EeInfoModifyController extends WindowAdapter implements ActionListener {

	private EeInfoVO eivo;
	private EeInfoModifyView eimv;
	private String originalImag;
	private String uploadImg;
	private String eeNum;
	private EeDAO eedao;
	public EeInfoModifyController(EeInfoModifyView eimv, String eeNum, EeInfoVO eivo) {
		this.eimv = eimv;
		this.eivo=eivo;
		this.eeNum=eeNum;
		uploadImg="";
		eedao=EeDAO.getInstance();
	}

	// �̷¼� �����ϴ� Method
	private void modifyEe() {

		String tempRank = eimv.getJcbRank().getSelectedItem().toString();
		String rank = tempRank.replace("����", "C").replaceAll("���", "N");

		String loc = eimv.getJcbLoc().getSelectedItem().toString();
		String education = (String) eimv.getJcbEducation().getSelectedItem();
		
		String tempPortfolio = eimv.getJcbPortfolio().getSelectedItem().toString();
		String portfolio = tempPortfolio.replaceAll("YES", "Y").replaceAll("NO", "N");
		String extResume = eimv.getJtfExtResume().getText();
		File file = new File(uploadImg);
		StringBuilder updateMsg= new StringBuilder();
		
		updateMsg
			.append("�������� \n")
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
		EeModifyVO emvo= new EeModifyVO(eeNum, file.getName(),
				rank,loc,education, portfolio, extResume);
				System.out.println(emvo);
		try {
			eedao.updateEeInfo(emvo); //DB���̺��� update����
			JOptionPane.showMessageDialog(eimv, "���������� ����Ǿ����ϴ�.");
	
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		//������ ������ �����ִ�â.
		}//end switch
		
	}// modifyEe

	/**
	 * �̹����� �ٲٴ� method
	 */
	private void ChangeImg() {
		FileDialog fd = new FileDialog(eimv, "���Ͽ���", FileDialog.LOAD);
		fd.setVisible(true);
		
		String path = fd.getDirectory();
		String name = fd.getFile();
		

		if(path != null) {
			if (!name.endsWith(".jpg") && !name.endsWith(".jpeg") && !name.endsWith(".png") && !name.endsWith(".bmp")
					&& !name.endsWith(".gif")) {
				JOptionPane.showMessageDialog(eimv, name + "�� ����Ҽ� �����ϴ�.");
			} else {
				uploadImg = path + name;
				eimv.getJlImag().setIcon(new ImageIcon(uploadImg));
			}
		}
	}// changeImg

	private void changeExt() {
		FileDialog fd = new FileDialog(eimv, "���Ͽ���", FileDialog.LOAD);
		fd.setVisible(true);
		String fdDir = fd.getDirectory();
		String fdName = fd.getFile();
		String filePath = fdDir + fdName;
		File file = new File(filePath);
		changeExt(file);
	}//changeExt
	
	private void changeExt(File file) {
		String extResume="";
		extResume=file.getName();
		
		if( !extResume.endsWith(".txt") && !extResume.endsWith(".pdf")) {
				JOptionPane.showMessageDialog(eimv, "�ܺ��̷¼��� doc, pdf�� �����մϴ�.");
				return;
			}else {
				eimv.getJtfExtResume().setText(extResume);
				eimv.getJtfExtResume().setEditable(false);
				JTextField jtf=eimv.getJtfExtResume();
				jtf.setText(extResume);
			}//end else
			
		
	}//changeExt

	@Override
	public void actionPerformed(ActionEvent ae) {

		if (ae.getSource() == eimv.getJbModify()) {
//			int temp = JOptionPane.showConfirmDialog(eimv, "�̷¼��� �����Ͻðڽ��ϱ�?");
//			switch (temp) {
//			case JOptionPane.OK_OPTION:
				modifyEe();
			}// end switch

//		} // end if

		if (ae.getSource() == eimv.getJbModifyImg()) {
			ChangeImg();
		} // end if

		if (ae.getSource() == eimv.getJbModifyExt()) {
			changeExt();
		}

		if(ae.getSource() == eimv.getJbClose()) {
			eimv.dispose();
		}
	
	}// actionPerformed

}
