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
import javax.swing.JOptionPane;

import user.common.view.LoginView;
import user.common.vo.EeMainVO;
import user.dao.CommonDAO;
import user.dao.EeDAO;
import user.ee.view.EeHiringView;
import user.ee.view.EeInfoModifyView;
import user.ee.view.EeInfoRegView;
import user.ee.view.EeInterestView;
import user.ee.view.EeMainView;
import user.ee.vo.EeHiringVO;
import user.ee.vo.EeInfoVO;
import user.ee.vo.EeRegVO;

public class EeMainController extends WindowAdapter implements ActionListener, MouseListener {

	private EeMainView emv;
	private EeMainVO emvo;
//	private LoginView lv;

	private EeDAO eedao;
	private CommonDAO C_dao;
	private EeHiringView ehv;
	private EeRegVO ervo;
	private EeInfoRegView eirv;
	private EeInfoVO eivo;
	
	public EeMainController(EeMainView emv, EeMainVO emvo) {
//		this.eirv=eirv;
		this.emvo = emvo;
		this.emv = emv;
		eedao = EeDAO.getInstance();
//		C_dao=CommonDAO.getInstance();
//		setInfo("kun90");
	}// ������

	public void checkActivation() throws SQLException {
		//������ ����
		if (emvo.getActivation().equals("2")) {
			JOptionPane.showMessageDialog(emv, "��ϵ� �⺻������ �����ϴ�.");
			mngUser();
		} else if(emvo.getActivation().equals("N")){
//			��ȸ ������ ��
			 eivo = eedao.selectEeInfo(emvo.getEeId());
			new EeInfoModifyView(emv, eivo);
		} // end if
	}// checkActivation()

	public void mngUser() throws SQLException {
		ervo = eedao.selectEeReg(emvo.getEeId());
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
	 * ���� ���� ���â ����
	 */
	public void showInterestEr() {
		String eeid = "gong1";
		new EeInterestView(emv, eeid);
	}// showInterestEr

	public void showApp() {
//		new EeAppView();
	}

	/**
	 * private EeMainView emv; ���콺 Ŭ���� ���� jlLogOut
	 */
	@Override
	public void mouseClicked(MouseEvent me) {
		if (me.getSource() == emv.getJlLogOut()) {
			new LoginView();
			emv.dispose();

		} // end if

	}// mouseClicked

	public void setInfo(String eeid) {
		JLabel activation = emv.getJlActivation();
		JLabel img = emv.getJlImg();
		EeMainVO emvo = null;

		try {
			emvo = C_dao.selectEeMain(eeid);
			activation.setText(emvo.getActivation());
			img.setText(emvo.getImg());

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void actionPerformed(ActionEvent ae) {

		if (ae.getSource() == emv.getJbEeInfo()) {
//			try {
//				mngUser();
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
			
			try {
				checkActivation();
			} catch (SQLException e) {
				e.printStackTrace();
			}
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
