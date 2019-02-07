package admin.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import admin.view.AdminMainView;

public class AdminMainController extends WindowAdapter implements ActionListener, Runnable {

	private AdminMainView amv;
	private Thread threadLog, threadSever;
	
	public AdminMainController(AdminMainView amv) {
		this.amv = amv;
	}
	
	@Override
	public void windowClosing(WindowEvent e) {
		amv.dispose();
	}
	
	@Override
	public void windowClosed(WindowEvent e) {
		System.exit(0);
	}
	
	@Override
	public void run() {

	}

	@Override
	public void actionPerformed(ActionEvent e) {

	}

}
