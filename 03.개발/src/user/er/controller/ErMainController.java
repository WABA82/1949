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

	private String erId;
	private ErMainVO ermvo;
//	private CoInsertVO civo;
	private CoInfoVO cvo;
	private ErDAO erdao;
	private CommonDAO C_dao;
	private ErMainView ermv;

	public ErMainController(ErMainView ermv, ErMainVO ermvo) {

		this.ermv = ermv;
		this.ermvo = ermvo;
		erdao = ErDAO.getInstance();
		C_dao = CommonDAO.getInstance();
	}// ������

	/** ȸ������ ���, ���� **/
	public void mngUser() throws SQLException {
		if (ermvo.getActivation().equals("N")) {
			new CoInfoRegView(ermv, ermvo.getErId());
			System.out.println(ermvo);

		} else if (ermvo.getActivation().equals("Y")) {
			cvo = erdao.selectCoInfo(ermvo.getErId());
			new CoInfoModifyView(ermv, cvo);
		} // end else

	}// end if

	/** �������� ����� ���� method **/
	public void showHiring() {

		if (ermvo.getActivation().equals("N")) {
			JOptionPane.showMessageDialog(ermv, "ȸ�� ������ ��ϵǾ�� �̿��ϽǼ� �ֽ��ϴ�.");
		} else if (ermvo.getActivation().equals("Y")) {
			List<ErHiringVO> ehvo = new ArrayList<ErHiringVO>();
			new ErHiringView(ermv, ehvo, ermvo.getErId());
		} // end if

	}// showHiring

	/** ȸ�������� �����ϴ� method **/
	public void mngEr() throws SQLException {
		UserInfoVO uivo = C_dao.selectUserInfo(ermvo.getErId());
		new ChangeUserInfoView(ermv, uivo);
	}// mngEr

	/** ���� ��Ȳ�� ���� �ִ� method **/
	public void showApp() {

		if (ermvo.getActivation().equals("N")) {
			JOptionPane.showMessageDialog(ermv, "ȸ�� ������ ��ϵǾ�� �̿��ϽǼ� �ֽ��ϴ�.");
		} else if (ermvo.getActivation().equals("Y")) {

			List<ErListVO> list = null;
			try {
				list = erdao.selectErList(erId);
				if (list.isEmpty()) {
					JOptionPane.showMessageDialog(ermv, "���� ����� ���ΰ��� �����ϴ�. ���� ���ΰ��� ����� �ּ���.");
					return;
				} // end if
				new ErAppView(ermv, ermvo.getErId());
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(ermv, "DB���� ��ȸ �� ������ �߻��߽��ϴ�.");
				e.printStackTrace();
			}
		} // end id

	}// showApp

	/** ���� �����ڸ� ���� �ִ� Method **/
	public void showInterestEe() {

		if (ermvo.getActivation().equals("N")) {
			JOptionPane.showMessageDialog(ermv, "ȸ�� ������ ��ϵǾ�� �̿��ϽǼ� �ֽ��ϴ�.");
		} else if (ermvo.getActivation().equals("Y")) {
			
			List<ErHiringForInterestVO> list = null;
			
			try {
				list = erdao.selectInterestEEInfoList(ermvo.getErId());
				if (list.isEmpty()) {
					JOptionPane.showMessageDialog(ermv, "���ɱ��������� �����ϴ�. ���� ������������ ��Ʈ�� �����ּ���.");
					return;
				} // end if

				new ErInterestView(ermv, ermvo.getErId());

			} catch (SQLException e) {
				JOptionPane.showMessageDialog(ermv, "DB���� ��ȸ �� ������ �߻��߽��ϴ�.");
				e.printStackTrace();
			}

		} // end id

	}// showInteresrEe

	/** ���� ���� ������ ���� �ִ� Method **/
	public void ShowErMgMtview() {

		if (ermvo.getActivation().equals("N")) {
			JOptionPane.showMessageDialog(ermv, "ȸ�� ������ ��ϵǾ�� �̿��ϽǼ� �ֽ��ϴ�.");
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
