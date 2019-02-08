package user.er.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import user.er.view.ErDetailSearchView;

public class ErDetailSearchController extends WindowAdapter implements ActionListener {

	private ErDetailSearchView edsv;
	private ErHiringController ehc;
	
	public ErDetailSearchController(ErDetailSearchView edsv, ErHiringController ehc) {
		this.edsv = edsv;
		this.ehc = ehc;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {

	}

	@Override
	public void windowClosing(WindowEvent e) {
		edsv.dispose();
	}
}
