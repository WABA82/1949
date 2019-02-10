package user.er.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import user.er.view.ErMainView;

public class ErMainController extends WindowAdapter implements ActionListener, MouseListener {
	
	private ErMainView emv;
	private String erId;
	
	public ErMainController(ErMainView emv, String erId) {
		this.emv = emv;
	}
	
	public void mngUser() {
		
	}
	
	public void showHiring() {
		
	}
	
	public void mngEr() {
		
	}
	
	public void showApp() {
		
	}
	
	public void showInterestEe() {
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {

	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
	}//actionPerformed

	@Override
	public void windowClosing(WindowEvent e) {
		emv.dispose();
	}
	@Override
	public void mousePressed(MouseEvent e) {}
	@Override
	public void mouseReleased(MouseEvent e) {}
	@Override
	public void mouseEntered(MouseEvent e) {}
	@Override
	public void mouseExited(MouseEvent e) {}
}
