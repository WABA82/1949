package admin.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import admin.view.AdminMgMtView;
import admin.view.ErModifyView;
import admin.vo.ErInfoVO;
import admin.vo.ErModifyVO;

public class ErModifyController extends WindowAdapter implements ActionListener {
	
	private ErModifyView emv;
	private AdminMgMtView ammv;
	private ErInfoVO eivo;
	
	public ErModifyController(ErModifyView emv, AdminMgMtView ammv, ErInfoVO eivo) {
		this.emv = emv;
		this.ammv = ammv;
		this.eivo = eivo;
	}
	
	public void modifyEr() {
		
		
		
	}
	
	public void removeEr() {
		
	}
	
	@Override
	public void windowClosing(WindowEvent e) {
		emv.dispose();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == emv.getJbModify()) {
			modifyEr();
		}
		
		if(e.getSource() == emv.getJbRemove()) {
			removeEr();
		}
		
		if(e.getSource() == emv.getJbCancel()) {
			emv.dispose();
		}
	}

}
