package user.common.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import user.common.view.FindIdView;

public class FindIdController extends WindowAdapter implements ActionListener {

	private FindIdView fiv;
	
	public FindIdController(FindIdView fiv) {
		this.fiv = fiv;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		
	}
	
	@Override
	public void windowClosing(WindowEvent e) {
		fiv.dispose();
	}
}
