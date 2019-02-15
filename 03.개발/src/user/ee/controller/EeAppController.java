package user.ee.controller;

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
import user.ee.view.EeAppView;
import user.ee.view.EeDetailErView;
import user.ee.vo.DetailErInfoVO;
import user.ee.vo.EeAppVO;

public class EeAppController extends WindowAdapter implements MouseListener {

	private EeAppView eav;
	private EeDAO ee_dao;
	private String ee_id;
	private EeAppVO eavo;
	private final int DBL_CLICK = 2;

	public EeAppController(EeAppView eav, String ee_id) {
		this.eav = eav;
		this.ee_id = ee_id;
		ee_dao = EeDAO.getInstance();
		setDTM(ee_id);
	}// ������

	private void setDTM(String ee_id) {
		DefaultTableModel dtm = eav.getDtmEr();
		dtm.setRowCount(0); // DTM 0���� �ʱ�ȭ.

		try {
			// DB���� ����ȸ�縦 ��ȸ.
			List<EeAppVO> list = ee_dao.selectAppList(ee_id);

			// JTable�� ��ȸ�� ������ ���.
			eavo = null;

			Object[] rowData = null;
			for (int i = 0; i < list.size(); i++) {
				/* list�� ����� VO��ü�� EeInterestVO��ü �����ϱ� */
				eavo = list.get(i);

				// DTM�� �����͸� �߰��ϱ� ���� �������迭(Vector)�� �����ϰ� �����͸� �߰�
				rowData = new Object[12];
				rowData[0] = new Integer(i + 1);
				rowData[1] = eavo.getEr_num(); /* ���� �ȵ�� ��. */
				rowData[2] = eavo.getApp_num();
				rowData[3] = eavo.getSubject();
				rowData[4] = eavo.getCo_name();
				rowData[5] = eavo.getRank();
				rowData[6] = eavo.getLoc();
				rowData[7] = eavo.getEducation();
				rowData[8] = eavo.getHire_type();
				rowData[9] = new Integer(eavo.getSal());
				rowData[10] = eavo.getApp_date();
				rowData[11] = eavo.getApp_status();
				System.out.println(eavo.getApp_status());

				// DTM�� �߰�
				dtm.addRow(rowData);
			} // end for

			if (list.isEmpty()) {// ����� �޴��� ���� �� : ���ö� �߰� ��ư�� ���� �޴��� �߰� �� �� �ִ�.
				JOptionPane.showMessageDialog(eav, "������ ��Ȳ�� �����ϴ�. �����������⿡�� ���� ������ �ּ���.");
			} // end if

		} catch (SQLException e) {
			JOptionPane.showMessageDialog(eav, "DB���� �����͸� �޾ƿ��� �� ���� �߻�...");
			e.printStackTrace();
		} // end catch

	}// setDTM

	@Override
	public void windowClosing(WindowEvent e) {
		eav.dispose();
	}// windowClosing

	/**
	 * ������Ȳ ��Ͽ��� �� ���� ������ ����Ŭ������ �� ������ ����.
	 */
	@Override
	public void mouseClicked(MouseEvent me) {
		switch (me.getClickCount()) { // Ŭ�� Ƚ�� ��.
		case DBL_CLICK: // ���� Ŭ�� ��
			if (me.getSource() == eav.getJtEr()) { // ���̺� ����Ŭ�� ��.
				showDetailErinfo();
			} // end if
		} // end switch
	}// mouseClicked

	/**
	 * ���� Ŭ���� ���� â
	 */
	private void showDetailErinfo() {
		JTable jt = eav.getJtEr();
		String erNum = String.valueOf(jt.getValueAt(jt.getSelectedRow(), 1));
		DetailErInfoVO deivo = null;
		try {
			deivo = ee_dao.selectDetail(erNum, ee_id);
		} catch (SQLException e) {
			e.printStackTrace();
		} // end catch
		System.out.println(deivo);
		new EeDetailErView(null, deivo, erNum, ee_id, eavo.getApp_status());
	}// showDetailErinfo

	/////////////////////////////// �� ���� �޼ҵ�� ///////////////////////////////
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

}// class
