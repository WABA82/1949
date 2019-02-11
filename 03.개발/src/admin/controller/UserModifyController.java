package admin.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import admin.view.AdminMgMtView;
import admin.view.UserModifyView;
import admin.vo.UserInfoVO;

public class UserModifyController extends WindowAdapter implements ActionListener {

	private UserModifyView umv;
	private AdminMgMtView ammv;
	private String addrSeq;
	
	public UserModifyController(UserModifyView umv, AdminMgMtView ammv, String addrSeq) {
		this.umv = umv;
		this.ammv = ammv;
		this.addrSeq = addrSeq;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == umv.getJbModify()) {
			
		}
		
		if (e.getSource() == umv.getJbRemove()) {
			
		}
		
		if(e.getSource() == umv.getJbClose()) {
			umv.dispose();
		}
	}
	
	@Override
	public void windowClosing(WindowEvent e) {
		umv.dispose();
	}

}
