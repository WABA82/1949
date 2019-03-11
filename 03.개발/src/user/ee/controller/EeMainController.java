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

import javax.swing.JOptionPane;

import user.common.view.ChangeUserInfoView;
import user.common.view.LoginView;
import user.common.vo.EeMainVO;
import user.common.vo.UserInfoVO;
import user.dao.CommonDAO;
import user.dao.EeDAO;
import user.ee.view.EeAppView;
import user.ee.view.EeHiringView;
import user.ee.view.EeInfoModifyView;
import user.ee.view.EeInfoRegView;
import user.ee.view.EeInterestView;
import user.ee.view.EeMainView;
import user.ee.vo.EeAppVO;
import user.ee.vo.EeHiringVO;
import user.ee.vo.EeInfoVO;
import user.ee.vo.EeInterestVO;
import user.ee.vo.EeRegVO;

public class EeMainController extends WindowAdapter implements ActionListener, MouseListener {

	private EeMainView emv;
	private EeMainVO emvo;
	private EeDAO eedao;
	private EeRegVO ervo;
	private EeInfoVO eivo;
	private UserInfoVO uivo;

	public EeMainController(EeMainView emv, EeMainVO emvo) {
		this.emvo = emvo;
		this.emv = emv;
		eedao = EeDAO.getInstance();
	}// 생성자

	// 첫번째 버튼 회원정보를 보여준다
	public void mngUser() throws SQLException {

		if (emvo.getActivation().equals("N")) {
			JOptionPane.showMessageDialog(emv, "개인정보가 등록되지 않았습니다.!");
			ervo = eedao.selectEeReg(emvo.getEeId());

			new EeInfoRegView(emv, ervo);

		} else if (emvo.getActivation().equals("Y")) {
			eivo = eedao.selectEeInfo(emvo.getEeId());
			new EeInfoModifyView(emv, eivo);
		} // end if

	}// checkActivation()

	public void mngEe() throws SQLException {
		uivo = CommonDAO.getInstance().selectUserInfo(emvo.getEeId());
		new ChangeUserInfoView(emv, uivo);

	}// mngEe

	/** 회사 정보를 볼수 있는 method **/
	public void showHiring() throws SQLException {

		if (emvo.getActivation().equals("N")) {
			JOptionPane.showMessageDialog(emv, "개인정보가 등록되어야 이용하실수 있습니다.");
		} else if (emvo.getActivation().equals("Y")) {
			List<EeHiringVO> ehvo = new ArrayList<EeHiringVO>();
			new EeHiringView(emv, ehvo, emvo.getEeId());
		} // end if

	}// showHiring

	/** 관심구인정보를 볼수있는 method **/
	public void showInterestEr() throws SQLException {

		if (emvo.getActivation().equals("N")) {
			JOptionPane.showMessageDialog(emv, "개인정보가 등록되어야 이용하실수 있습니다.");
		} else if (emvo.getActivation().equals("Y")) {

			List<EeInterestVO> list = eedao.selectInterestErInfoList(emvo.getEeId());

			if (list.isEmpty()) {// 등록한 메뉴가 없을 때 : 도시락 추가 버튼을 통해 메뉴를 추가 할 수 있다.
				JOptionPane.showMessageDialog(emv, "관심구인정보가 없습니다. 먼저 구인정보에서 하트를 눌러주세요.");
				return;
			} // end if

			new EeInterestView(emv, emvo.getEeId());
		} // end if

	}// showInterestEr

	/** 지원한 회사정보를 볼수 있는 method **/
	public void showApp() throws SQLException {

		if (emvo.getActivation().equals("N")) {
			JOptionPane.showMessageDialog(emv, "개인정보가 등록되어야 이용하실수 있습니다.");
		} else if (emvo.getActivation().equals("Y")) {

			List<EeAppVO> list = eedao.selectAppList(emvo.getEeId());
			if (list.isEmpty()) {// 등록한 메뉴가 없을 때 : 도시락 추가 버튼을 통해 메뉴를 추가 할 수 있다.
				JOptionPane.showMessageDialog(emv, "관심구인정보가 없습니다. 먼저 구인정보에서 하트를 눌러주세요.");
				return;
			} // end if
			new EeAppView(emv, emvo.getEeId());
		} // end else

	}// showApp

	@Override
	public void mouseClicked(MouseEvent me) {
		if (me.getSource() == emv.getJlLogOut()) {
			new LoginView();
			emv.dispose();
		} // end if

		if (me.getSource() == emv.getJlUserInfo()) {
			try {
				mngEe();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} // end if

	}// mouseClicked

	@Override
	public void actionPerformed(ActionEvent ae) {

		if (ae.getSource() == emv.getJbEeInfo()) {
			try {
				mngUser();
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(emv, "DB에서 조회 중 문제가 발생했습니다.");
				e.printStackTrace();
			}
		} // end if

		if (ae.getSource() == emv.getJbErInfo()) {
			try {
				showHiring();
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(emv, "DB에서 조회 중 문제가 발생했습니다.");
				e.printStackTrace();
			}
		} // end if

		if (ae.getSource() == emv.getJbInterestEr()) {
			try {
				showInterestEr();
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(emv, "DB에서 조회 중 문제가 발생했습니다.");
				e.printStackTrace();
			}
		} // end if

		if (ae.getSource() == emv.getJbApp()) {
			try {
				showApp();
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(emv, "DB에서 조회 중 문제가 발생했습니다.");
				e.printStackTrace();
			}
		}

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
