package user.er.controller;

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
import user.common.vo.ErMainVO;
import user.common.vo.UserInfoVO;
import user.dao.CommonDAO;
import user.dao.ErDAO;
import user.er.view.CoInfoModifyView;
import user.er.view.CoInfoRegView;
import user.er.view.ErAppView;
import user.er.view.ErHiringView;
import user.er.view.ErInterestView;
import user.er.view.ErMainView;
import user.er.view.ErMgMtView;
import user.er.vo.CoInfoVO;
import user.er.vo.ErHiringForInterestVO;
import user.er.vo.ErHiringVO;
import user.er.vo.ErListVO;

public class ErMainController extends WindowAdapter implements ActionListener, MouseListener {

	private ErMainVO ermvo;
	private CoInfoVO cvo;
	private ErDAO erdao;
	private CommonDAO C_dao;
	private ErMainView ermv;

	public ErMainController(ErMainView ermv, ErMainVO ermvo) {

		this.ermv = ermv;
		this.ermvo = ermvo;
		erdao = ErDAO.getInstance();
		C_dao = CommonDAO.getInstance();
	}// 생성자

	/** 회사정보 등록, 관리 **/
	public void mngUser() throws SQLException {
		if (ermvo.getActivation().equals("N")) {
			new CoInfoRegView(ermv, ermvo.getErId());

		} else if (ermvo.getActivation().equals("Y")) {
			cvo = erdao.selectCoInfo(ermvo.getErId());
			new CoInfoModifyView(ermv, cvo);
		} // end else

	}// end if

	/** 구직자의 목록을 보는 method **/
	public void showHiring() {

		if (ermvo.getActivation().equals("N")) {
			JOptionPane.showMessageDialog(ermv, "회사 정보가 등록되어야 이용하실수 있습니다.");
		} else if (ermvo.getActivation().equals("Y")) {
			List<ErHiringVO> ehvo = new ArrayList<ErHiringVO>();
			new ErHiringView(ermv, ehvo, ermvo.getErId());
		} // end if

	}// showHiring

	/** 회원정보를 수정하는 method **/
	public void mngEr() throws SQLException {
		UserInfoVO uivo = C_dao.selectUserInfo(ermvo.getErId());
		new ChangeUserInfoView(ermv, uivo);
	}// mngEr

	/** 지원 현황을 볼수 있는 method **/
	public void showApp() {

		if (ermvo.getActivation().equals("N")) {
			JOptionPane.showMessageDialog(ermv, "회사 정보가 등록되어야 이용하실수 있습니다.");
		} else if (ermvo.getActivation().equals("Y")) {

			List<ErListVO> list = null;
			try {
				list = erdao.selectErList(ermvo.getErId());
				if (list.isEmpty()) {
					JOptionPane.showMessageDialog(ermv, "현재 등록한 구인공고가 없습니다. 먼저 구인공고를 등록해 주세요.");
					return;
				} // end if
				new ErAppView(ermv, ermvo.getErId());
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(ermv, "DB에서 조회 중 문제가 발생했습니다.");
				e.printStackTrace();
			}
		} // end id

	}// showApp

	/** 관심 구직자를 볼수 있는 Method **/
	public void showInterestEe() {

		if (ermvo.getActivation().equals("N")) {
			JOptionPane.showMessageDialog(ermv, "회사 정보가 등록되어야 이용하실수 있습니다.");
		} else if (ermvo.getActivation().equals("Y")) {
			
			List<ErHiringForInterestVO> list = null;
			
			try {
				list = erdao.selectInterestEEInfoList(ermvo.getErId());
				
				if (list.isEmpty()) {
					JOptionPane.showMessageDialog(ermv, "관심구인정보가 없습니다. 먼저 구인정보에서 하트를 눌러주세요.");
					return;
				} // end if

				new ErInterestView(ermv, ermvo.getErId());

			} catch (SQLException e) {
				JOptionPane.showMessageDialog(ermv, "DB에서 조회 중 문제가 발생했습니다.");
				e.printStackTrace();
			}

		} // end id

	}// showInteresrEe

	/** 구인 정보 관리를 볼수 있는 Method **/
	public void ShowErMgMtview() {

		if (ermvo.getActivation().equals("N")) {
			JOptionPane.showMessageDialog(ermv, "회사 정보가 등록되어야 이용하실수 있습니다.");
		} else if (ermvo.getActivation().equals("Y")) {
			List<ErListVO> elvo = new ArrayList<ErListVO>();
			new ErMgMtView(ermv, elvo, ermvo.getErId());
		} // end if

	}// ShowErMgView

	@Override
	public void mouseClicked(MouseEvent me) {

		if (me.getSource() == ermv.getJlUserInfo()) {
			try {
				mngEr();
			} catch (SQLException e) {
				e.printStackTrace();
			} // end catch
		} // end if

		if (me.getSource() == ermv.getJlLogOut()) {
			ermv.dispose();
			new LoginView();
		} // end if

	}// mouseClicked

	@Override
	public void actionPerformed(ActionEvent ae) {

		if (ae.getSource() == ermv.getJbCoMgmt()) {
			try {
				mngUser();
			} catch (SQLException e) {
				e.printStackTrace();
			} // end catch
		} // end if

		if (ae.getSource() == ermv.getJbEeInfo()) {
			showHiring();
		} // end if

		if (ae.getSource() == ermv.getJbInterestEe()) {
			showInterestEe();
		} // end if

		if (ae.getSource() == ermv.getJbApp()) {
			showApp();
		} // end if

		if (ae.getSource() == ermv.getJbErMgmt()) {
			ShowErMgMtview();
		} // end if

	}// actionPerformed

	@Override
	public void windowClosing(WindowEvent e) {
		ermv.dispose();
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
