package user.er.controller;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import user.dao.ErDAO;
import user.er.view.AppListView;
import user.er.vo.DetailAppVO;
import user.er.vo.ErListVO;

public class AppListController extends WindowAdapter implements MouseListener {

	private AppListView alv;
	// private String er_num;
	private ErDAO er_dao;

	private final int DBL_CLICK = 2; // ���� Ŭ�� ���

	public AppListController(AppListView alv, String er_num) {
		this.alv = alv;
		// this.er_num = er_num;
		er_dao = ErDAO.getInstance();
		setDTM(er_num);
	}// ������

	private void setDTM(String er_num) {

		DefaultTableModel dtm = alv.getDtmEeInfo();
		dtm.setRowCount(0); // DTM 0���� �ʱ�ȭ.

		try {
			// DB���� ����ȸ�縦 ��ȸ.
			List<DetailAppVO> list = er_dao.selectDetailApplist(er_num);

			// JTable�� ��ȸ�� ������ ���.
			DetailAppVO davo = null;
			String imgPath = "C:/dev/1949/03.����/��������/�����ڻ���/150x200px/";

			Object[] rowData = null;
			for (int i = 0; i < list.size(); i++) {

				/* list�� ����� VO��ü�� EeInterestVO��ü �����ϱ� */
				davo = list.get(i);

				// DTM�� �����͸� �߰��ϱ� ���� �������迭(Vector)�� �����ϰ� �����͸� �߰�
				rowData = new Object[12];
				rowData[0] = new Integer(i + 1);
				rowData[1] = davo.getApp_num();
				rowData[2] = new ImageIcon(imgPath + davo.getImg());
				rowData[3] = davo.getName();
				rowData[4] = davo.getRank();
				rowData[5] = davo.getLoc();
				rowData[6] = davo.getEducation();
				rowData[7] = davo.getAge();
				rowData[8] = davo.getPortfolio();
				rowData[9] = davo.getGender();
				rowData[10] = davo.getApp_date();
				rowData[11] = davo.getApp_status();

				// DTM�� �߰�
				dtm.addRow(rowData);
			} // end for

			if (list.isEmpty()) {// ����� �޴��� ���� �� : ���ö� �߰� ��ư�� ���� �޴��� �߰� �� �� �ִ�.
				JOptionPane.showMessageDialog(alv, "����� ���ΰ��� ���� ������ �����ڰ� �����ϴ�.");
			} // end if

		} catch (SQLException e) {
			JOptionPane.showMessageDialog(alv, "DB���� �����͸� �޾ƿ��� �� ���� �߻�...");
			e.printStackTrace();
		} // end catch

	}// setDTM

	@Override
	public void windowClosing(WindowEvent e) {
		alv.dispose();
	}// windowClosing

	@Override
	public void mouseClicked(MouseEvent me) {
		switch (me.getClickCount()) {
		case DBL_CLICK:
			if (me.getSource() == alv.getJtEeInfo()) {
				JTable jt = alv.getJtEeInfo();
				String 
			} // end if
		}// end switch
	}// mouseClicked

	///////////////// �Ⱦ��� �޼���/////////////////
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
	///////////////// �Ⱦ��� �޼���/////////////////

}// class
