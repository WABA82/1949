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

			// ���� ��Ȳ ���� ȭ�鿡 �����ֱ�.
			StringBuilder cnt = new StringBuilder();
			cnt.append("�� ���� ��Ȳ �� : ").append(String.valueOf(list.size())).append(" ��");
			eav.getJlEeAppCnt().setText(cnt.toString());

			// JTable�� ��ȸ�� ������ ���.
			eavo = null;

			Object[] rowData = null;
			for (int i = 0; i < list.size(); i++) {
				/* list�� ����� VO��ü�� EeInterestVO��ü �����ϱ� */
				eavo = list.get(i);

				// DTM�� �����͸� �߰��ϱ� ���� �������迭(Vector)�� �����ϰ� �����͸� �߰�
				rowData = new Object[12];
				rowData[0] = new Integer(i + 1);
				rowData[1] = eavo.getApp_num();
				rowData[2] = eavo.getSubject();
				rowData[3] = eavo.getCo_name();
				rowData[4] = (eavo.getRank().equals("N") ? "����" : "���"); // 'N'����, 'R'���.
				rowData[5] = eavo.getLoc();
				rowData[6] = eavo.getEducation();
				switch (eavo.getHire_type()) {
				case "C":
					rowData[7] = "������";
					break;
				case "N":
					rowData[7] = "��������";
					break;
				case "F":
					rowData[7] = "��������";
				}// end switch
				rowData[8] = new Integer(eavo.getSal());
				rowData[9] = eavo.getApp_date();
				switch (eavo.getApp_status()) {
				case "U":
					rowData[10] = "������";
					break;
				case "R":
					rowData[10] = "����";
					break;
				case "A":
					rowData[10] = "��������";
					break;
				case "D":
					rowData[10] = "��������";
				}// end switch

				/* DTM�� �߰� */
				dtm.addRow(rowData);
			} // end for

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
		String app_num = String.valueOf(jt.getValueAt(jt.getSelectedRow(), 1));
		DetailErInfoVO deivo = null;
		String appStatus = "";
		try {
			String erNum = ee_dao.selectErNumFromAppTb(app_num);
			deivo = ee_dao.selectDetail(erNum, ee_id);
			appStatus = ee_dao.selectApplication(ee_id, erNum);

			// ����ڰ� �� �������� â�� ������ ������¿� ���� �޽����� �����ִ� �޼���.
			appStatusMsg(appStatus);

			EeDetailErView edev = new EeDetailErView(eav, deivo, erNum, ee_id, appStatus);

			// edev.isActive() - EeDetailErView�� â�� �������� true�߻�.
			if (edev.isActive()) {
				setDTM(ee_id);
			} // end if

		} catch (SQLException e) {
			e.printStackTrace();
		} // end catch
	}// showDetailErinfo

	/**
	 * �� ���� ���� â�� ������ ������¿� ���� �޽����� ����ִ� �޼���
	 */
	private void appStatusMsg(String appStatus) {
		switch (appStatus.toUpperCase()) {
		case "U":
			JOptionPane.showMessageDialog(eav, "�λ����ڰ� ���� ���������� Ȯ������ �ʾҽ��ϴ�.");
			break;
		case "R":
			JOptionPane.showMessageDialog(eav, "�λ����ڰ� ������ �Դϴ�.");
			break;
		case "A":
			JOptionPane.showMessageDialog(eav, "�����մϴ�!\n�λ����ڰ� ���������� ������ Ȯ���߽��ϴ�.\n�������� ���� ������ �� �����Դϴ�.");
			break;
		case "D":
			JOptionPane.showMessageDialog(eav, "�˼��մϴ�.\n���հ��ϼ̽��ϴ�.");
		}// switch
	}// appStatusMsg()

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
