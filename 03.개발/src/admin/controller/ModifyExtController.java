package admin.controller;

import java.awt.FileDialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;

import javax.swing.JOptionPane;

import admin.controller.EeModifyController;
import admin.view.EeModifyView;
import admin.view.ModifyExtView;

public class ModifyExtController extends WindowAdapter implements ActionListener {

	private ModifyExtView mev;
	private EeModifyView emv;
	private EeModifyController emc;
	
	public ModifyExtController(ModifyExtView mev, EeModifyView emv, EeModifyController emc) {
		this.mev=mev;
		this.emv=emv;
		this.emc=emc;
	}//생성자
	
	private void chooseFile() {
		FileDialog fd=new FileDialog(mev, "파일을 선택해주세요", FileDialog.LOAD);
		fd.setVisible(true);
		fd.setResizable(false);
		
		String fileDir=fd.getDirectory();
		String fileName=fd.getFile();
		
		File file=new File(fileDir+fileName);
		changeExt(file);
	}//chooseFile
	
	//jtfExtResume; 외부이력서는 doc, pdf만 첨부가능 합니다
	private void changeExt(File file) {
		
		String extResume = file.getName();
		
		if( !extResume.endsWith(".doc") && !extResume.endsWith(".pdf")) {
			JOptionPane.showMessageDialog(mev, "외부이력서는 doc, pdf만 가능합니다.");
			return;
		}else {
			mev.getJtfPath().setText(extResume);
			mev.getJtfPath().setEditable(false);
			emv.getJtfExtRsm().setText(extResume);

			emc.setChangeExtFile(file);
			emc.setChangeExtFlag(true);
		}//end else
	}//changeExt
	
	@Override
		public void windowClosing(WindowEvent we) {
			mev.dispose();
		}//windowClosing
	
	@Override
	public void actionPerformed(ActionEvent ae) {
		if( ae.getSource() == mev.getJbCancel()) {
			mev.dispose();
		}//end if
		
		if(ae.getSource() == mev.getJbChoose()) {
			chooseFile();
		}//end if
		
		if(ae.getSource() == mev.getJbChange()) {
			mev.dispose();
		}
	}//actionPerformed
}
