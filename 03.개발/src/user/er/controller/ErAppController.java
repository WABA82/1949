package user.er.controller;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import user.dao.ErDAO;
import user.er.view.AppListView;
import user.er.view.ErAppView;
import user.er.vo.DetailAppListVO;
import user.er.vo.ErListVO;

public class ErAppController extends WindowAdapter implements MouseListener {

	private ErAppView erav;
	private ErDAO er_dao;
	private String er_id;

	private final int DBL_CLICK = 2;

	public ErAppController(ErAppView erav, String er_id) {
		this.erav = erav;
		this.er_id = er_id;
		er_dao = ErDAO.getInstance();
		setDTM(er_id);
	}// ������

	/**
	 * ErAppView�� â�� ��� �� �� DB���� ������ ������ ����ִ� ��.
	 */
	public void setDTM(String er_Id) {

		DefaultTableModel dtm = erav.getDtmEeInfo();
		dtm.setRowCount(0); // DTM 0���� �ʱ�ȭ.

		try {
			// DB���� ����ȸ�縦 ��ȸ.
			List<ErListVO> list = er_dao.selectErList(er_Id);

			StringBuffer erCnt = new StringBuffer("�� �������� �� : ");
			erCnt.append(String.valueOf(list.size())).append(" ��");
			erav.getJlEeInfo().setText(erCnt.toString());

			// JTable�� ��ȸ�� ������ ���.
			ErListVO eivo = null;

			Object[] rowData = null;
			for (int i = 0; i < list.size(); i++) {

				/* list�� ����� VO��ü�� EeInterestVO��ü �����ϱ� */
				eivo = list.get(i);

				// DTM�� �����͸� �߰��ϱ� ���� �������迭(Vector)�� �����ϰ� �����͸� �߰�
				rowData = new Object[8];
				rowData[0] = new Integer(i + 1);
				rowData[1] = eivo.getErNum();
				rowData[2] = eivo.getSubject();
				rowData[3] = (eivo.getRank().equals("N") ? "����" : "���");
				rowData[4] = eivo.getLoc();
				rowData[5] = eivo.getEducation();
				switch (eivo.getHireType()) {
				case "C":
					rowData[6] = "������";
					break;
				case "N":
					rowData[6] = "��������";
					break;
				case "F":
					rowData[6] = "��������";
					break;
				}// end switch
				rowData[7] = eivo.getInputDate();

				// DTM�� �߰�
				dtm.addRow(rowData);
			} // end for

		} catch (SQLException e) {
			JOptionPane.showMessageDialog(erav, "DB���� �����͸� �޾ƿ��� �� ���� �߻�...");
			e.printStackTrace();
		} // end catch

	}// setDTM

	@Override
	public void windowClosing(WindowEvent e) {
		erav.dispose();
	}// windowClosing

	/**
	 * ErAppView���� ���̺� ����Ŭ���� �ش� row�� ������ �� ���� â ����.
	 */
	@Override
	public void mouseClicked(MouseEvent me) {

		switch (me.getClickCount()) {
		case DBL_CLICK:
			if (me.getSource() == erav.getJtEeInfo()) {
				showAppList();
			} // end if
		}// end switch

	}// mouseClicked

	private void showAppList() {
		JTable jt = erav.getJtEeInfo();
		String er_num = (String) (jt.getValueAt(jt.getSelectedRow(), 1));

		List<DetailAppListVO> list = null;

		try {
			
			list = er_dao.selectDetailApplist(er_num);
			if (list.isEmpty()) {
				JOptionPane.showMessageDialog(erav, "����� ���ΰ��� ���� ������ �����ڰ� �����ϴ�.");
				return;
			} // end if
			
			AppListView alv = new AppListView(erav, er_num);
			
			// AppListView��ü�� ������ ���߸� true��ȯ.
			// System.out.println(alv.isActive());
			if (alv.isActive()) {
				// System.out.println("���� ����");
				setDTM(er_id);
			} // end if
			
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(erav, "DB���� ��ȸ �� ������ �߻��߽��ϴ�.");
			e.printStackTrace();
		}// end catch

		
	}// showAppList

	/////////// �Ⱦ��� �޼ҵ� ///////////
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
	/////////// �Ⱦ��� �޼ҵ� ///////////

}// class
