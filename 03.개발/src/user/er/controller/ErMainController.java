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
	
	public ErMainController(ErMainView emv) {
		this.emv = emv;
	}
	
	@Override
	public void windowClosing(WindowEvent e) {
		emv.dispose();
	}

	@Override
	public void mouseClicked(MouseEvent e) {

	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
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
