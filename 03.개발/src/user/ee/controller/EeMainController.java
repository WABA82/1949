package user.ee.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JTextField;

import user.common.vo.EeMainVO;
import user.dao.CommonDAO;
import user.dao.EeDAO;
import user.ee.view.EeHiringView;
import user.ee.view.EeInfoRegView;
import user.ee.view.EeInterestView;
import user.ee.view.EeMainView;
import user.ee.vo.EeHiringVO;
import user.ee.vo.EeRegVO;

public class EeMainController extends WindowAdapter implements ActionListener, MouseListener {

	private EeMainView emv;
	private EeMainVO emvo;

	private EeDAO eedao;
	private CommonDAO C_dao;
	private EeHiringView ehv;

	public EeMainController(EeMainView emv, EeMainVO emvo) {
		this.emv = emv;
		eedao = EeDAO.getInstance();
//		C_dao=CommonDAO.getInstance();
//		setInfo("kun90");
	}

	public void checkActivation() {

	}

	public void mngUser() {
		EeRegVO ervo= new EeRegVO("kun90", "M", 25);
			new EeInfoRegView(emv, ervo);
	}

	public void mngEe() {

	}

	public void showHiring() {
//		EeMainView emv = new EeMainView(emvo);
		List<EeHiringVO> ehvo = new ArrayList<EeHiringVO>();
		String eeid = "testId";
		new EeHiringView(emv, ehvo, eeid);
	}

	/**
	 * 관심 구인 목록창 띄우기
	 */
	public void showInterestEr() {
		String eeid = "gong1";
		new EeInterestView(emv, eeid);
	}// showInterestEr

	public void showApp() {
//		new EeAppView();
	}

	/**
	 * private EeMainView emv; 마우스 클릭시 종료 jlLogOut
	 */
	@Override
	public void mouseClicked(MouseEvent me) {
		if (me.getSource() == emv.getJlLogOut()) {
			emv.dispose();

		} // end if

	}// mouseClicked

	public void setInfo(String eeid) {
	JLabel activation=emv.getJlActivation();
	JLabel img=emv.getJlImg();
	EeMainVO emvo= null;
	
	try {
		emvo=C_dao.selectEeMain(eeid);
		activation.setText(emvo.getActivation());
		img.setText(emvo.getImg());
		
	} catch (SQLException e) {
		e.printStackTrace();
	}
	
	}
	
	@Override
	public void actionPerformed(ActionEvent ae) {

		if (ae.getSource() == emv.getJbEeInfo()) {
			mngUser();
		} // end if

		if (ae.getSource() == emv.getJbErInfo()) {
			showHiring();
		} // end if

		if (ae.getSource() == emv.getJbInterestEr()) {
			showInterestEr();
		} // end if

	}// actionPerformed

	@Override
	public void windowClosing(WindowEvent e) {
		emv.dispose();
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}
}
