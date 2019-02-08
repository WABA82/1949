package admin.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import admin.view.SearchAddrView;
import admin.view.UserModifyView;

public class SearchAddrController extends WindowAdapter implements ActionListener {

	private SearchAddrView sav;
	private UserModifyView umv;
	private String addrSeq;
	
	public SearchAddrController(SearchAddrView sav, UserModifyView umv, String addrSeq) {
		this.sav = sav;
		this.umv = umv;
		this.addrSeq = addrSeq;
	}
	
	public void search(String dong) {
		
	}
	
	@Override
	public void windowClosing(WindowEvent e) {
		sav.dispose();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
	}
}
