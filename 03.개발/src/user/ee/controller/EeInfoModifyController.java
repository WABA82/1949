package user.ee.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;

import user.dao.EeDAO;
import user.ee.view.EeInfoModifyView;

public class EeInfoModifyController extends WindowAdapter implements ActionListener {

	private EeInfoModifyView eimv;
	private EeInfoRegController erc;
	private EeDAO eedao;
	
	public EeInfoModifyController(EeInfoModifyView eimv, String eeid) {
		this.eimv=eimv;
		this.erc=erc;
		eedao=EeDAO.getInstance();
		
		
		
	}//»ý¼ºÀÚ
	
	
	@Override
	public void actionPerformed(ActionEvent e) {

	}//actionPerformed

}//class
