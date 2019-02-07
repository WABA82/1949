package admin.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import admin.view.SearchAddrView;

public class SearchAddrController extends WindowAdapter implements ActionListener {

	private SearchAddrView sav;
	
	public SearchAddrController(SearchAddrView sav) {
		this.sav = sav;
	}
	
	@Override
	public void windowClosing(WindowEvent e) {
		sav.dispose();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
	}
}
