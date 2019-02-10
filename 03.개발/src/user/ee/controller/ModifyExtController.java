package user.ee.controller;

import java.awt.FileDialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import sun.awt.AWTAccessor.FileDialogAccessor;
import user.ee.view.EeInfoRegView;
import user.ee.view.ModifyExtView;

/**
 *	19.02.10 김건하
 *	외부파일 등록 이벤트 클래스
 * @author 82102
 */
public class ModifyExtController extends WindowAdapter implements ActionListener {

	private ModifyExtView mev;
	private EeInfoRegView erv;
	
	public ModifyExtController( ModifyExtView mev, EeInfoRegView erv) {
		this.mev=mev;
		this.erv=erv;
		
	}//생성자
	
	private void chooseFile() {
		FileDialog fd=new FileDialog(mev, "파일을 선택해주세요", FileDialog.LOAD);
		fd.setVisible(true);
		fd.setResizable(false);
		String fileDir=fd.getDirectory();
		String fileName=fd.getFile();
		File file=new File(fileDir+fileName);
		changeExt(file);
//		System.out.println(file);
	}//chooseFile
	
	//jtfExtResume; 외부이력서는 doc, pdf만 첨부가능 합니다
	private void changeExt(File file) {
		String extResume="";
		extResume=file.getName();
		if( !extResume.endsWith(".txt") && !extResume.endsWith(".pdf")) {
				JOptionPane.showMessageDialog(mev, "외부이력서는 doc, pdf만 가능합니다.");
				return;
			}else {
				
				mev.getJtfPath().setText(extResume);
				mev.getJtfPath().setEditable(false);
				JTextField jtf=erv.getJtfExtResume();
				jtf.setText(extResume);
			}//end else
			
		// jtfsume 이벤트 등록 실패..
		
	}//changeExt
	
	
	@Override
		public void windowClosing(WindowEvent we) {
			mev.dispose();
		}//windowClosing
	
	@Override
	public void actionPerformed(ActionEvent ae) {
		if( ae.getSource() == mev.getJbCancel()) {
			erv.getJtfExtResume().setText("");
			mev.dispose();
		}//end if
		
		if(ae.getSource() == mev.getJbChoose()) {
			chooseFile();
		}//end if
		
		if(ae.getSource() == mev.getJbChange()) {
			mev.dispose();
		}
	}//actionPerformed
	
//	public static void main(String[] args) {
//		new ModifyExtController(null, null).changeExt();
//	}
}
