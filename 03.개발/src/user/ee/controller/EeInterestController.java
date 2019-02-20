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
import user.ee.vo.DetailErInfoVO;
import user.ee.vo.EeInterestVO;

public class EeInterestController extends WindowAdapter implements ActionListener, MouseListener {

	private String ee_id = "";
	private EeInterestView eiv;
	private EeDAO ee_dao;
	private final int DBL_CLICK = 2; // ���� Ŭ�� ���.

	public EeInterestController(EeInterestView eiv, String ee_id) {
		this.eiv = eiv;
		this.ee_id = ee_id;
		ee_dao = EeDAO.getInstance();
		setDTM(ee_id);
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
				showDetailErinfo();
			} // end if
		} // end switch
	}// mouseClicked

	/**
	 * ���� ��������â�� table����� �����ϴ� �޼ҵ�.
	 */
	private void setDTM(String ee_id) {
		DefaultTableModel dtmErInfo = eiv.getdtmErInfo();
		dtmErInfo.setRowCount(0); // DTM 0���� �ʱ�ȭ.

		try {
			// DB���� ����ȸ�縦 ��ȸ.
			List<EeInterestVO> list = ee_dao.selectInterestErInfoList(ee_id);

			// JTable�� ��ȸ�� ������ ���.
			EeInterestVO eivo = null;

			Object[] rowData = null;
			for (int i = 0; i < list.size(); i++) {

				/* list�� ����� VO��ü�� EeInterestVO��ü �����ϱ� */
				eivo = list.get(i);

				// DTM�� �����͸� �߰��ϱ� ���� �������迭(Vector)�� �����ϰ� �����͸� �߰�
				rowData = new Object[10];
				rowData[0] = new Integer(i + 1);
				rowData[1] = eivo.getErNum();
				rowData[2] = eivo.getSubject();
				rowData[3] = eivo.getCoName();
				rowData[4] = (eivo.getRank().equals("N") ? "����" : "���");
				rowData[5] = eivo.getLoc();
				rowData[6] = eivo.getEducation();
				switch (eivo.getHireType()) {
				case "C":
					rowData[7] = "������";
					break;
				case "N":
					rowData[7] = "��������";
					break;
				case "F":
					rowData[7] = "��������";
				}// end switch
				rowData[8] = new Integer(eivo.getSal());
				rowData[9] = eivo.getInputDate();

				// DTM�� �߰�
				dtmErInfo.addRow(rowData);
			} // end for

			if (list.isEmpty()) {// ����� �޴��� ���� �� : ���ö� �߰� ��ư�� ���� �޴��� �߰� �� �� �ִ�.
				JOptionPane.showMessageDialog(eiv, "���ɱ��������� �����ϴ�. ���� ������������ ��Ʈ�� �����ּ���.");
			} // end if

		} catch (SQLException e) {
			JOptionPane.showMessageDialog(eiv, "DB���� �����͸� �޾ƿ��� �� ���� �߻�...");
			e.printStackTrace();
		} // end catch

	}// setDTM

	/**
	 * ���� Ŭ���� ���� â
	 */
	private void showDetailErinfo() {
		JTable jt = eiv.getjtErInfo();
		String erNum = String.valueOf(jt.getValueAt(jt.getSelectedRow(), 1));
		DetailErInfoVO deivo = null;
		try {
			deivo = ee_dao.selectDetail(erNum, ee_id);
		} catch (SQLException e) {
			e.printStackTrace();
		} // end catch

		EeDetailErView edev = new EeDetailErView(eiv, deivo, erNum, ee_id, null);

		// edev.isActive() - EeDetailErView�� â�� ���� ���� true�߻�
		if (edev.isActive()) {
			setDTM(ee_id);
		} // end if

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
