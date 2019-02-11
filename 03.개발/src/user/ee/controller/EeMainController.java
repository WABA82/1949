package user.ee.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

import user.common.vo.EeMainVO;
import user.dao.EeDAO;
import user.ee.view.EeHiringView;
import user.ee.view.EeInfoRegView;
import user.ee.view.EeInterestView;
import user.ee.view.EeMainView;
import user.ee.vo.EeHiringVO;

public class EeMainController extends WindowAdapter implements ActionListener, MouseListener {

	private EeMainView emv;
	private EeMainVO emvo;

	private EeDAO EeDAO;
	private EeHiringView ehv;

	public EeMainController(EeMainView emv, EeMainVO emvo) {
		this.emv = emv;
		EeDAO = EeDAO.getInstance();
	}

	public void checkActivation() {

	}

	public void mngUser() {
		new EeInfoRegView(emv);
	}

	public void mngEe() {

	}

	public void showHiring() {
		EeMainView emv = new EeMainView(emvo);
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
