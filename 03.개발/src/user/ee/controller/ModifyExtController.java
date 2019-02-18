package user.ee.controller;

import java.awt.FileDialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;

import javax.swing.JOptionPane;
import javax.swing.JTextField;

import user.ee.view.EeInfoRegView;
import user.ee.view.ModifyExtView;

public class ModifyExtController extends WindowAdapter implements ActionListener {

	private ModifyExtView emev;
	private EeInfoRegView eirv;
	
	public ModifyExtController( ModifyExtView emev, EeInfoRegView eirv, EeInfoRegController eirc) {
		this.emev=emev;
		this.eirv=eirv;
	}//������
	
	private void chooseFile() {
		FileDialog fd=new FileDialog(emev, "������ �������ּ���", FileDialog.LOAD);
		fd.setVisible(true);
		fd.setResizable(false);
		String fileDir=fd.getDirectory();
		String fileName=fd.getFile();
		File file=new File(fileDir+fileName);
		changeExt(file);
//		System.out.println(file);
	}//chooseFile
	
	//jtfExtResume; �ܺ��̷¼��� doc, pdf�� ÷�ΰ��� �մϴ�
	private void changeExt(File file) {
		String extResume="";
		extResume=file.getName();
		if( !extResume.endsWith(".txt") && !extResume.endsWith(".pdf")) {
				JOptionPane.showMessageDialog(emev, "�ܺ��̷¼��� doc, pdf�� �����մϴ�.");
				return;
			}else {
				emev.getJtfPath().setText(extResume);
				emev.getJtfPath().setEditable(false);
				JTextField jtf=eirv.getJtfExtResume();
				jtf.setText(extResume);
			}//end else
			
		// jtfsume �̺�Ʈ ��� ����..
		
	}//changeExt
	
	
	@Override
		public void windowClosing(WindowEvent we) {
			emev.dispose();
		}//windowClosing
	
	@Override
	public void actionPerformed(ActionEvent ae) {
		if( ae.getSource() == emev.getJbCancel()) {
			eirv.getJtfExtResume().setText("");
			emev.dispose();
		}//end if
		
		if(ae.getSource() == emev.getJbChoose()) {
			chooseFile();
		}//end if
		
		if(ae.getSource() == emev.getJbChange()) {
			emev.dispose();
		}
	}//actionPerformed
}
	