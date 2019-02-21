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

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import user.dao.EeDAO;
import user.ee.view.EeDetailCoView;
import user.ee.view.EeDetailErView;
import user.ee.vo.CoDetailVO;
import user.ee.vo.DetailErInfoVO;
import user.ee.vo.EeAppVO;
import user.ee.vo.EeInterestAndAppVO;
import user.util.UserLog;

////////////0210 �����Ұ� : �����ϱ� ����(â�� �ݰ� �ٽ� ������ ��Ʈ�� ����), ���ɴ������� �� ������ ���///////////
public class EeDetailErController extends WindowAdapter implements ActionListener, MouseListener {
	private EeDetailErView edev;
	private String erNum;
	private String eeId;
	private boolean mouseClickFlag;
	private EeInterestAndAppVO eiaavo;
	private EeDAO ee_dao;
	private UserLog ul;

	public EeDetailErController(EeDetailErView edev, String erNum, String eeId, boolean flagHeart) {
		this.edev = edev;
		this.erNum = erNum;
		this.eeId = eeId;
		mouseClickFlag = flagHeart;
		ee_dao = EeDAO.getInstance();
		ul = new UserLog();
	}

	public void addUInterestEr() {
		eiaavo = new EeInterestAndAppVO(erNum, eeId);

		try {
			ee_dao.insertInterestEr(eiaavo);
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(edev, "�߰��� �����߽��ϴ�.");
			return;
		}
		edev.getJlHeart().setIcon(new ImageIcon("C:/dev/1949/03.����/��������/��Ʈ/r_heart.png"));
		JOptionPane.showMessageDialog(edev, "���� ���αۿ� �߰��Ǿ����ϴ�!");
		ul.sendLog(eeId, "���� ���α��� �߰��߽��ϴ�.");
		try {
			DetailErInfoVO devo = ee_dao.selectDetail(erNum, eeId);

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}// addUInterestEr

	public void removeInterestEr() {
		boolean deleteFlag = false;
		eiaavo = new EeInterestAndAppVO(erNum, eeId);

		try {
			deleteFlag = ee_dao.deleteInterestEr(eiaavo);
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(edev, "DB����!!");
			return;
		}
		if (deleteFlag) {
			JOptionPane.showMessageDialog(edev, "���� ���α��� ����߽��ϴ�.");
			ul.sendLog(eeId, "���� ���α��� ����߽��ϴ�.");
			edev.getJlHeart().setIcon(new ImageIcon("C:/dev/1949/03.����/��������/��Ʈ/b_heart.png"));
		} else {
			JOptionPane.showMessageDialog(edev, "����Ʈ������ �����߽��ϴ�.");
		}
	}// removeInterestEr

	/**
	 * ���� : ��ư Ŭ���� ȸ�� �� ���� â ����.
	 */
	public void showCoDetail() {
		CoDetailVO cdvo;
		try {
			cdvo = ee_dao.selectCompany(erNum);
			new EeDetailCoView(edev, cdvo);
		} catch (SQLException e) {
			e.printStackTrace();
		}// end catch
	}// showCoDetail

	public void apply() {
		// ���ɱ��������� ����(eeAppVO)
		boolean flag = false;
		List<EeAppVO> list = new ArrayList<EeAppVO>();
		eiaavo = new EeInterestAndAppVO(erNum, eeId);
		try {
			list = ee_dao.selectAppList(eeId);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		try {
			if (list.size() == 0) {
				flag = true;
			} else {
				for (int i = 0; i < list.size(); i++) {
					if (!(list.get(i).getEr_num().equals(erNum))) {
						flag = true;
					} else {
						JOptionPane.showMessageDialog(edev, "�̹� ������ �����Դϴ�.");
						return;
					}
				}
			}
			if (flag) {
				ee_dao.insertApplication(eiaavo);
				JOptionPane.showMessageDialog(edev, "������ �Ϸ�Ǿ����ϴ�!");
				ul.sendLog(eeId, "������ �Ϸ��Ͽ����ϴ�.");
			} else {
				JOptionPane.showMessageDialog(edev, "�̹� ������ �����Դϴ�.");
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(edev, "DB����");
			e.printStackTrace();
		}
	}

	@Override
	public void windowClosing(WindowEvent we) {
		edev.dispose();
	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		if (ae.getSource() == edev.getJbCoInfo()) {
			showCoDetail();
		}// end if
		
		if (ae.getSource() == edev.getJbApply()) {
			// �����ϱ�
			int apply = JOptionPane.showConfirmDialog(edev, "�����Ͻðڽ��ϱ�?");
			if (apply == 0) {
				apply();
			}
		}
		if (ae.getSource() == edev.getJbClose()) {
			edev.dispose();
		}
	}

	@Override
	public void mouseClicked(MouseEvent me) {

		if (me.getSource() == edev.getJlHeart()) {
			mouseClickFlag = !mouseClickFlag;
		}
		if (mouseClickFlag) {
			addUInterestEr();
			
		} else {
			removeInterestEr();
			
		}
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
