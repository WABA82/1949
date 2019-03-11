package admin.controller;

import java.awt.FileDialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;

import javax.swing.JOptionPane;

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
	
	/**
	 * 파일을 선택 후 changeExt() 메서드를 호출하는 메서드
	 */
	private void chooseFile() {
		FileDialog fd=new FileDialog(mev, "파일을 선택해주세요", FileDialog.LOAD);
		fd.setVisible(true);
		fd.setResizable(false);
		
		String fileDir=fd.getDirectory();
		String fileName=fd.getFile();
		
		if(fileDir != null && fileName != null) { // 선택시에만(취소 예외처리)
			File file=new File(fileDir+fileName);
			changeExt(file);
		}
	}//chooseFile
	
	/**
	 * 파일을 선택하면 emc의 이력서 변경 flag를 바꾸고 파일을 저장하는 메서드
	 * @param file
	 */
	private void changeExt(File file) {
		
		String extResume = file.getName();
		
		if( !extResume.toLowerCase().endsWith(".doc") && !extResume.toLowerCase().endsWith(".pdf")) {
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
		
		if(ae.getSource() == mev.getJbChoose() || ae.getSource() == mev.getJtfPath()) {
			chooseFile();
		}//end if
		
		if(ae.getSource() == mev.getJbChange()) {
			mev.dispose();
		}
	}//actionPerformed
}
