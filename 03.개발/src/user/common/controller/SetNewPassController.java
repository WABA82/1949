package user.common.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import user.common.view.SetNewPassView;

public class SetNewPassController extends WindowAdapter implements ActionListener {

	private SetNewPassView snpv;
	private String id;
	
	public SetNewPassController(SetNewPassView snpv, String id) {
		this.snpv = snpv;
		this.id = id;
	}
	
	public void changePass() {
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {

	}
	
	@Override
	public void windowClosing(WindowEvent e) {
		snpv.dispose();
	}
}
