package user.common.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import user.common.view.FindPassView;

public class FindPassController extends WindowAdapter implements ActionListener {
	
	private FindPassView fpv;
	
	public FindPassController(FindPassView fpv) {
		this.fpv = fpv;
	}
	
	public void checkUserData() {
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {

	}
	
	@Override
	public void windowClosing(WindowEvent e) {
		fpv.dispose();
	}
}
