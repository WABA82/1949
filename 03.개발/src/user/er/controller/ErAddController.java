package user.er.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import user.er.view.ErAddView;

public class ErAddController extends WindowAdapter implements ActionListener {
	private ErAddView eav;
	public ErAddController(ErAddView eav) {
		this.eav = eav;
	}
	
	
	@Override
	public void actionPerformed(ActionEvent ae) {
		if(ae.getSource()==eav.getJbReg()) {
			
		}
		if(ae.getSource()==eav.getJbCancel()) {
			eav.dispose();
		}
		
	}
	
	@Override
	public void windowClosing(WindowEvent e) {
		eav.dispose();
	}
}
