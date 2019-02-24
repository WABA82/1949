package user.ee.controller;

import java.awt.FileDialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;

import javax.swing.JOptionPane;
import javax.swing.JTextField;

import oracle.net.aso.e;
import user.ee.view.EeInfoRegView;
import user.ee.view.ModifyExtView;

public class ModifyExtController extends WindowAdapter implements ActionListener {

	private ModifyExtView emev;
	private EeInfoRegView eirv;
	private String extResume;

	public ModifyExtController(ModifyExtView emev, EeInfoRegView eirv, EeInfoRegController eirc) {
		this.emev = emev;
		this.eirv = eirv;
		extResume = "";
	}// ������

	private void chooseFile() {
		boolean flag = false;

		FileDialog fd = new FileDialog(emev, "������ �������ּ���", FileDialog.LOAD);
		fd.setVisible(true);
		fd.setResizable(false);
		String fileDir = fd.getDirectory();
		String fileName = fd.getFile();
		File file = new File(fileDir + fileName);

		if (file.exists()) {
			extResume = file.getName();
			if (extResume.endsWith(".txt") && !extResume.endsWith(".pdf")) {
				flag = true;
			}
			if (flag) {
				emev.getJtfPath().setText(extResume);
				emev.getJtfPath().setEditable(false);
			} else {
				JOptionPane.showMessageDialog(emev, "�ܺ��̷¼��� doc, pdf�� �����մϴ�.");
				return;
			} // end else
		} // end if

//		System.out.println(file);
	}// chooseFile

	// jtfExtResume; �ܺ��̷¼��� doc, pdf�� ÷�ΰ��� �մϴ�
	private void changeExt() {
		//�ܺ��̷¼� ��� â
		String extPath = emev.getJtfPath().getText().trim();
		//�⺻ ���� ���� â
		String eifExtPath = eirv.getJtfExtResume().getText().trim();
		
		if(!extPath.equals("") && !eifExtPath.equals(extResume)) {
			eirv.getJtfExtResume().setText(extResume);
		}// end if
		// ����ó��
		emev.dispose();
	}// changeExt

	@Override
	public void windowClosing(WindowEvent we) {
		emev.dispose();
	}// windowClosing

	@Override
	public void actionPerformed(ActionEvent ae) {
		if (ae.getSource() == emev.getJbCancel()) {
//			eirv.getJtfExtResume().setText("");
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
