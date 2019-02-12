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
	}//积己磊
	
	
	@Override
	public void actionPerformed(ActionEvent ae) {
		if(ae.getSource()==sav.getJbSearch()) {
			search(addrSeq);
		}else if(ae.getSource()==sav.getJbOk()) {
			
		}else if(ae.getSource()==sav.getJbCancel()) {
			sav.dispose();
		}
	}//滚瓢 贸府
	
	@Override
	public void windowClosing(WindowEvent e) {
		sav.dispose();
	}//closing
	public void search(String dong) {
		
	}//search
}//class
