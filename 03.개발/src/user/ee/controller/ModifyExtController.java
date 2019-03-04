package user.ee.controller;

import java.awt.FileDialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;

import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import oracle.net.aso.e;
import user.ee.view.EeInfoModifyView;
import user.ee.view.EeInfoRegView;
import user.ee.view.ModifyExtView;

public class ModifyExtController extends WindowAdapter implements ActionListener {

	private ModifyExtView emev;
	
	private String flag;
	private EeInfoRegView eirv;
	private EeInfoRegController eirc;
	
	private EeInfoModifyView eimv;
	private EeInfoModifyController eimc;
	
	private String fileDir;
	private String fileName;

	public ModifyExtController(ModifyExtView emev, 
			EeInfoRegView eirv, EeInfoRegController eirc, 
			EeInfoModifyView eimv, EeInfoModifyController eimc, 
			String flag) {
		
		this.emev = emev;
		this.flag = flag;
		
		if(flag.equals("modi")) {
			this.eimv = eimv;
			this.eimc = eimc;
		} else {
			this.eirv = eirv;
			this.eirc = eirc;
		}
	}// ������

	private boolean chooseFile() {
		boolean flag = false;

		FileDialog fd = new FileDialog(emev, "������ �������ּ���", FileDialog.LOAD);
		fd.setVisible(true);
		fd.setResizable(false);
		
		fileDir = fd.getDirectory();
		fileName = fd.getFile();
		
		if (fileDir == null || fileName == null) {
			return flag;
		}
		
		if (fileName.endsWith(".doc") || fileName.endsWith(".pdf")) {
			emev.getJtfPath().setText(fileName);
			flag = true;
		} else {
			JOptionPane.showMessageDialog(emev, "�ܺ��̷¼��� doc, pdf�� �����մϴ�.");
		} // end else
		
		return flag;
	}// chooseFile

	private void changeExt() {
		
		if (fileName != null) {
			if (flag.equals("modi")) {
				eimc.setUploadExt(new File(fileDir + fileName));
				eimc.setExtChgFlag(true);
				eimv.getJtfExtResume().setText(fileName);
			} else {
				eirc.setUploadExt(new File(fileDir + fileName));
				eirv.getJtfExtResume().setText(fileName);
			}
		}
		
		emev.dispose();
	}// changeExt

	@Override
	public void windowClosing(WindowEvent we) {
		emev.dispose();
	}// windowClosing

	@Override
	public void actionPerformed(ActionEvent ae) {
		if (ae.getSource() == emev.getJbCancel()) {
			emev.dispose();
		} // end if

		if (ae.getSource() == emev.getJbChoose()) {
			chooseFile();
		} // end if

		if (ae.getSource() == emev.getJbChange()) { // ÷���ϱ�
			changeExt();
		}
	}// actionPerformed
}
