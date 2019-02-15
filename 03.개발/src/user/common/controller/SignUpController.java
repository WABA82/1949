package user.common.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import user.common.view.SearchAddrView;
import user.common.view.SignUpView;

public class SignUpController extends WindowAdapter implements ActionListener {
	private SignUpView suv;
	private String addrSeq;
	
	public SignUpController(SignUpView suv) {
		this.suv=suv;
	}//积己磊

	@Override
	public void windowClosing(WindowEvent e) {
		suv.dispose();
	}//closing
	
	@Override
	public void actionPerformed(ActionEvent ae) {
		if(ae.getSource()==suv.getJbSignUp()) {
			signUp();
		}else if(ae.getSource()==suv.getJbAddr()) {
			new SearchAddrView(suv, addrSeq, suv);
		}else if(ae.getSource()==suv.getJbCancel()) {
			suv.dispose();
		}//end if

	}//滚瓢贸府

	public void signUp() {
		
	}//signUp
	
	
}//class
