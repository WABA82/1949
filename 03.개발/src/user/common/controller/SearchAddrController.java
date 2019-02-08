package user.common.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JDialog;

import user.common.view.SearchAddrView;

public class SearchAddrController extends WindowAdapter implements ActionListener {

	private SearchAddrView sav;
	private JDialog jd;
	private String addrSeq;

	public SearchAddrController(SearchAddrView sav, JDialog jd, String addrSeq) {
		this.sav = sav;
		this.jd = jd;
		this.addrSeq = addrSeq;
	}
	
	public void search(String dong) {
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
	}
	
	@Override
	public void windowClosing(WindowEvent e) {
		sav.dispose();
	}
}
