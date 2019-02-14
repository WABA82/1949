package user.common.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import user.common.view.ChangeUserInfoView;
import user.common.vo.UserInfoVO;

public class ChangeUserInfoController extends WindowAdapter implements ActionListener {

	private ChangeUserInfoView culv;
	private UserInfoVO uivo;
	private String addrSeq;
	
	public ChangeUserInfoController(ChangeUserInfoView culv, UserInfoVO uivo) {
		this.culv=culv;
		this.uivo=uivo;
	}
	
	public void modifiyUser() {
		
	}
	public void removeUser() {
		
	}
	public void exRsmDown() {
		
	}
	
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
	}
	@Override
	public void windowClosing(WindowEvent e) {
		culv.dispose();
	}

}
