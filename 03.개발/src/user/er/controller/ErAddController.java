package user.er.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;

import user.dao.ErDAO;
import user.er.view.ErAddView;
import user.er.view.ErModifyView;
import user.er.vo.ErAddVO;

public class ErAddController extends WindowAdapter implements ActionListener {
	private ErAddView eav;
	private ErAddVO eavo;
	private ErMgMtController emmc;
	private String erId;
	private ErDAO er_dao;
		
	public ErAddController(ErAddView eav, ErMgMtController emmc, String erId) {
		this.eav = eav;
		this.erId = erId;
		this.emmc = emmc;
		er_dao= ErDAO.getInstance();
	}
	public void register() {
		//등록수정완료시 eavo에 값 넣어주고 dao 에뿌려주기
		try {
			er_dao.insertErAdd(eavo);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public void refreshList() {
		
	}
	
	
	@Override
	public void actionPerformed(ActionEvent ae) {
		if(ae.getSource()==eav.getJbReg()) {
			register();
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
