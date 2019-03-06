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
	}// ������

	// ù��° ��ư ȸ�������� �����ش�
	public void mngUser() throws SQLException {

		if (emvo.getActivation().equals("N")) {
			JOptionPane.showMessageDialog(emv, "���������� ��ϵ��� �ʾҽ��ϴ�.!");
			ervo = eedao.selectEeReg(emvo.getEeId());

			System.out.println(ervo);
			new EeInfoRegView(emv, ervo);

		} else if (emvo.getActivation().equals("Y")) {
			eivo = eedao.selectEeInfo(emvo.getEeId());
			new EeInfoModifyView(emv, eivo);
		} // end if

	}// checkActivation()

	/**
	 * ���� ���� ������ �Ҽ��ִ� method
	 * 
	 * @throws SQLException
	 **/
	public void mngEe() throws SQLException {
		uivo = CommonDAO.getInstance().selectUserInfo(emvo.getEeId());
		new ChangeUserInfoView(emv, uivo);

	}// mngEe

	/** ȸ�� ������ ���� �ִ� method **/
	public void showHiring() throws SQLException {

		if (emvo.getActivation().equals("N")) {
			JOptionPane.showMessageDialog(emv, "���������� ��ϵǾ�� �̿��ϽǼ� �ֽ��ϴ�.");
		} else if (emvo.getActivation().equals("Y")) {
			List<EeHiringVO> ehvo = new ArrayList<EeHiringVO>();
			new EeHiringView(emv, ehvo, emvo.getEeId());
		} // end if

	}// showHiring

	/** ���ɱ��������� �����ִ� method **/
	public void showInterestEr() throws SQLException {

		if (emvo.getActivation().equals("N")) {
			JOptionPane.showMessageDialog(emv, "���������� ��ϵǾ�� �̿��ϽǼ� �ֽ��ϴ�.");
		} else if (emvo.getActivation().equals("Y")) {

			List<EeInterestVO> list = eedao.selectInterestErInfoList(emvo.getEeId());

			if (list.isEmpty()) {// ����� �޴��� ���� �� : ���ö� �߰� ��ư�� ���� �޴��� �߰� �� �� �ִ�.
				JOptionPane.showMessageDialog(emv, "���ɱ��������� �����ϴ�. ���� ������������ ��Ʈ�� �����ּ���.");
				return;
			} // end if

			new EeInterestView(emv, emvo.getEeId());
		} // end if

	}// showInterestEr

	/** ������ ȸ�������� ���� �ִ� method **/
	public void showApp() throws SQLException {

		if (emvo.getActivation().equals("N")) {
			JOptionPane.showMessageDialog(emv, "���������� ��ϵǾ�� �̿��ϽǼ� �ֽ��ϴ�.");
		} else if (emvo.getActivation().equals("Y")) {

			List<EeAppVO> list = eedao.selectAppList(emvo.getEeId());

			if (list.isEmpty()) {// ����� �޴��� ���� �� : ���ö� �߰� ��ư�� ���� �޴��� �߰� �� �� �ִ�.
				JOptionPane.showMessageDialog(emv, "���ɱ��������� �����ϴ�. ���� ������������ ��Ʈ�� �����ּ���.");
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
				JOptionPane.showMessageDialog(emv, "DB���� ��ȸ �� ������ �߻��߽��ϴ�.");
				e.printStackTrace();
			}
		} // end if

		if (ae.getSource() == emv.getJbErInfo()) {
			try {
				showHiring();
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(emv, "DB���� ��ȸ �� ������ �߻��߽��ϴ�.");
				e.printStackTrace();
			}
		} // end if

		if (ae.getSource() == emv.getJbInterestEr()) {
			try {
				showInterestEr();
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(emv, "DB���� ��ȸ �� ������ �߻��߽��ϴ�.");
				e.printStackTrace();
			}
		} // end if

		if (ae.getSource() == emv.getJbApp()) {
			try {
				showApp();
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(emv, "DB���� ��ȸ �� ������ �߻��߽��ϴ�.");
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
