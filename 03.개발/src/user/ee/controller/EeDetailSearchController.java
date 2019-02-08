package user.ee.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import user.ee.view.EeDetailSearchView;

public class EeDetailSearchController extends WindowAdapter implements ActionListener {
	
	private EeDetailSearchView esv;
	private EeHiringController ehc;
	
	public EeDetailSearchController(EeDetailSearchView esv, EeHiringController ehc) {
		this.esv = esv;
		this.ehc = ehc;
	}
	
	public void search() {
		
	}
	
	@Override
	public void windowClosing(WindowEvent e) {
		esv.dispose();
	}

	@Override
	public void actionPerformed(ActionEvent e) {

	}

}
