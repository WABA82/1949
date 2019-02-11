package user.ee.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import user.dao.EeDAO;
import user.ee.view.EeDetailErView;
import user.ee.view.EeInterestView;
import user.ee.vo.EeHiringVO;

public class EeInterestController extends WindowAdapter implements ActionListener, MouseListener {

	private EeInterestView eiv;
	private EeDAO ee_dao;
	public static final int DBL_CLICK = 2; // ���� Ŭ�� ���.

	public EeInterestController(EeInterestView eiv, String eeid) {
		this.eiv = eiv;
		ee_dao = EeDAO.getInstance();
	}// ������

	@Override
	public void windowClosing(WindowEvent arg0) {
		eiv.dispose();
	}// windowClosing

	@Override
	public void actionPerformed(ActionEvent e) {

	}// actionPerformed

	@Override
	public void mouseClicked(MouseEvent me) {
		switch (me.getClickCount()) { // Ŭ�� Ƚ�� ��.
		case DBL_CLICK: // ���� Ŭ�� ��
			if (me.getSource() == eiv.getjtErInfo()) { // ���̺� ����Ŭ�� ��.
				JTable jt = eiv.getjtErInfo();
			} // end if
		} // end switch
//		try {
//			LunchDetailVO ldvo = la_dao.selectDetailLunch((String) jt.getValueAt(jt.getSelectedRow(), 1));
//			new LunchDetailView(lmv, ldvo, this);
//		} catch (SQLException se) {
//			JOptionPane.showMessageDialog(lmv, "DB���� ������ �߻��߽��ϴ�.");
//			se.printStackTrace();
//		} // end catch
	}// mouseClicked

	/**
	 * ���� ��������â�� table����� �����ϴ� �޼ҵ�.
	 */
	private void setDTM() {
		DefaultTableModel dtmErInfo = eiv.getdtmErInfo();
		dtmErInfo.setRowCount(0); // DTM 0���� �ʱ�ȭ.

		try {
			// DB���� ����ȸ�縦 ��ȸ.
			List<EeHiringVO> list = ee_dao.selectInterestErInfo();

//			// JTable�� ��ȸ�� ������ ���.
//			LunchVO lv = null;
//
//			Object[] rowData = null;
//			for (int i = 0; i < listLunch.size(); i++) {
//				lv = listLunch.get(i);
//				// DTM�� �����͸� �߰��ϱ� ���� �������迭(Vector)�� �����ϰ� �����͸� �߰�
//				rowData = new Object[5];
//				rowData[0] = new Integer(i + 1);
//				rowData[1] = lv.getLunchCode();
//				rowData[2] = new ImageIcon(imgPath + lv.getImg());
//				rowData[3] = lv.getLunchName();
//				rowData[4] = new Integer(lv.getPrice());
//
//				// DTM�� �߰�
//				dtmLunch.addRow(rowData);
//
//			} // end for
//
//			if (listLunch.isEmpty()) {// ����� �޴��� ���� �� : ���ö� �߰� ��ư�� ���� �޴��� �߰� �� �� �ִ�.
//				JOptionPane.showMessageDialog(lmv, "�Էµ� ��ǰ�� �����ϴ�. ���ö��� �߰��� �ּ���.");
//			} // end if
//
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(eiv, "DB���� �����͸� �޾ƿ��� �� ���� �߻�...");
			e.printStackTrace();
		} // end catch

	}// setDTM

	private void showDetailErinfo() {
		new EeDetailErView(null, null, null, null, null);
	}// showDetailErinfo

	////////// �Ⱦ��� �޼ҵ�//////////
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
	////////// �Ⱦ��� �޼ҵ�//////////

}// class
