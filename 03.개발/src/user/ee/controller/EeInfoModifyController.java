package user.ee.controller;

import java.awt.FileDialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import user.common.vo.EeMainVO;
import user.dao.CommonDAO;
import user.dao.EeDAO;
import user.ee.view.EeInfoModifyView;
import user.ee.view.EeMainView;
import user.ee.vo.EeInfoVO;
import user.ee.vo.EeModifyVO;

public class EeInfoModifyController extends WindowAdapter implements ActionListener {

	private EeInfoVO eivo;
	private EeInfoModifyView eimv;
	private File uploadImg;
	private String eeNum;
	private EeDAO eedao;
	public EeInfoModifyController(EeInfoModifyView eimv, String eeNum, EeInfoVO eivo) {
		this.eimv = eimv;
		this.eivo=eivo;
		this.eeNum=eeNum;
//		uploadImg="";
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
		StringBuilder updateMsg= new StringBuilder();
		
		
		
		///////////���⼭ ����////////////
//		if(uploadImg == null) {
//			uploadImg= new File("C:/dev/1949/03.����/src/file/eeImg/"+eivo.getImg()); //����
//		}//end if
		
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
		EeModifyVO emvo= new EeModifyVO(eeNum, uploadImg.getName(),
				rank,loc,education, portfolio, extResume);
				System.out.println(emvo);
		try {
			eedao.updateEeInfo(emvo); //DB���̺��� update����
			JOptionPane.showMessageDialog(eimv, "���������� ����Ǿ����ϴ�.");

			
			////////////////////////////////////////////////////�̹��� ����///////////////////////////////////
			if( !uploadImg.getName().equals("")) {// ������ �̹����� �����ϴ� ���
				try {
					
					File original = new File("C:/dev/1949/03.����/src/file/eeImg/"+eimv.getJlImag());
					original.delete();
					uploadImgFile(uploadImg.getAbsoluteFile());
				} catch (IOException e) {
					e.printStackTrace();
				}//end cath
			}//end if
			
			
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
		boolean flag= false;
		
		FileDialog fd = new FileDialog(eimv, "���Ͽ���", FileDialog.LOAD);
		fd.setVisible(true);
		
		String path = fd.getDirectory();
		String name = fd.getFile();
		
		if(path != null) {
			if(name.endsWith(".jpg") || name.endsWith(".jpeg") || name.endsWith(".png") || 
					name.endsWith(".bmp") || name.endsWith(".gif")) {
				flag=true;
			}//end if
		 	if(flag) {
			uploadImg =  new File(  path + name );
				eimv.getJlImag().setIcon(new ImageIcon(uploadImg.getAbsolutePath()));
			} else {
				JOptionPane.showMessageDialog(eimv, name + "�� ����Ҽ� �����ϴ�.");
			}//else if
		}//end if
	}// changeImg

	//�̷¼� ����� ���
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
		boolean flag=false;
		String extResume="";
		extResume=file.getName();
	
		if(file.exists()) {
			if( extResume.endsWith(".txt") || extResume.endsWith(".pdf")) {
				flag=true;
			}//end if
			
			if(flag) {
				eimv.getJtfExtResume().setText(extResume);
				eimv.getJtfExtResume().setEditable(false);
				JTextField jtf=eimv.getJtfExtResume();
				jtf.setText(extResume);
			}else {
				JOptionPane.showMessageDialog(eimv, "�ܺ��̷¼��� doc, pdf�� �����մϴ�.");
				return;
			}//end else
		}//end if
	}//changeExt

	/////////////////////////////////�̹��� ���ε�///////////////////////////////////
	private void uploadImgFile(File file) throws IOException{
		FileInputStream fis=null;
		FileOutputStream fos=null;
		
		try {
		fis = new FileInputStream(file);
		byte[] readData = new byte[512];
		
		int len = 0;
		String uploadPath= "C:/dev/1949/03.����/src/user/img/ee/";
		fos = new FileOutputStream(uploadPath+file.getName());
		
		while( (len=fis.read(readData)) != -1) {
			fos.write(readData, 0, len);
			fos.flush();
		}//end while
		
		}finally {
			if(fis!=null) {
				fis.close();
			}//end if
			if(fos!=null) {
				fos.close();
			}//end if
			
		}//end finally
		
	}//uploadImg
	
	
	@Override
	public void actionPerformed(ActionEvent ae) {

		if (ae.getSource() == eimv.getJbModify()) {
				modifyEe();
				eimv.dispose();

		} // end if

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
