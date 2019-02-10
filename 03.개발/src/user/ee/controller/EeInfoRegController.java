package user.ee.controller;

import java.awt.FileDialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import sun.reflect.generics.visitor.Reifier;
import user.dao.EeDAO;
import user.ee.view.EeInfoRegView;
import user.ee.view.ModifyExtView;
import user.ee.vo.EeInsertVO;

public class EeInfoRegController extends WindowAdapter implements ActionListener {

	private EeInfoRegView eirv;
	private String uploadImg;
	
	public EeInfoRegController(EeInfoRegView eirv, String eeid) {
	this.eirv=eirv;
	uploadImg="";
	
	}//생성자
	
	//이미지전송도 같이 수행
	public void register() {
		if(uploadImg.isEmpty()) {
			JOptionPane.showMessageDialog(eirv, "이미지는 필수 입니다.");
			return;
		}
		
		String rank=eirv.getJcbRank().getSelectedItem().toString();
		String loc=	eirv.getJcbLoc().getSelectedItem().toString();
		String education=eirv.getJcbEducation().toString();
		String portfolio=eirv.getJcbPortfolio().getSelectedItem().toString();
		String extResume=eirv.getJtfExtResume().getSelectedText();
		
		//삭제할 코딩
		String name=eirv.getJtfName().getText().trim();
		String num=eirv.getEeNum().getText();
			
		
		File file =new File(uploadImg);
		
		
		//eeNum, img, rank, loc, education, portfolio, extResume, inputDate, eeId)
		EeInsertVO eivo= new EeInsertVO(num, file.getName(), rank, loc, education, portfolio, extResume, null,name);
				
		try {
			EeDAO.getInstance().insertEeinfo(eivo);
		} catch (SQLException e) {
		}
	}//register
	
	
	public void changeImg() {
		FileDialog fd=new FileDialog(eirv, "이미지 선택", FileDialog.LOAD);
		fd.setVisible(true);
		
		String path=fd.getDirectory();
		String name=fd.getFile();
		
		if(path !=null) {
			if(!name.endsWith(".jpg") && !name.endsWith(".jpeg") && !name.endsWith(".png") &&
					!name.endsWith(".bmp")) {
				JOptionPane.showMessageDialog(eirv, name+"은 사용할수 없습니다.");
			}else {
				uploadImg=path+name;
				eirv.getJlImage().setIcon(new ImageIcon(uploadImg));
			}
		}
	}//changeImg
	
	
	@Override
	public void windowClosing(WindowEvent e) {
		eirv.dispose();
	}
	
	@Override
	public void actionPerformed(ActionEvent ae) {

		if(ae.getSource() == eirv.getJbClose()) {
			eirv.dispose();
		}//end if
		
		if(ae.getSource() == eirv.getJbRegisterImg()) {
			changeImg();
		}//end if
		
		
		if(ae.getSource() == eirv.getJbRegisterExt()) {
			new ModifyExtView(eirv);
		}//end if
		
		if(ae.getSource() == eirv.getJbRegister()) {
			register();
		}//end if
		
	}//actionPerformed

}
