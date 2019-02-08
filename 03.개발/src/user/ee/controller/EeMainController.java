package user.ee.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import user.ee.view.EeMainView;

public class EeMainController extends WindowAdapter implements ActionListener, MouseListener {

	private EeMainView emv;
	
	public EeMainController(EeMainView emv) {
		this.emv = emv;
	}
	
	public void checkActivation() {
		
	}
	
	public void mngUser() {
		
	}
	
	public void mngEe() {
		
	}
	
	public void showHiring() {
		
	}
	
	public void showInterestEr() {
		
	}
	
	public void showApp() {
		
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {

	}

	@Override
	public void actionPerformed(ActionEvent e) {

	}

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
