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
	}// 생성자

	private void chooseFile() {
		boolean flag = false;

		FileDialog fd = new FileDialog(emev, "파일을 선택해주세요", FileDialog.LOAD);
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
				JOptionPane.showMessageDialog(emev, "외부이력서는 doc, pdf만 가능합니다.");
				return;
			} // end else
		} // end if

//		System.out.println(file);
	}// chooseFile

	// jtfExtResume; 외부이력서는 doc, pdf만 첨부가능 합니다
	private void changeExt() {
		//외부이력서 등록 창
		String extPath = emev.getJtfPath().getText().trim();
		//기본 정보 관리 창
		String eifExtPath = eirv.getJtfExtResume().getText().trim();
		
		if(!extPath.equals("") && !eifExtPath.equals(extResume)) {
			eirv.getJtfExtResume().setText(extResume);
		}// end if
		// 종료처리
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

		if (ae.getSource() == emev.getJbChange()) { // 첨부하기
			changeExt();
		}
	}// actionPerformed
}
