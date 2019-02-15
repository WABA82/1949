package user.er.controller;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.sql.SQLException;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import user.dao.ErDAO;
//import user.er.view.ErDetailEeView;
import user.er.view.ErInterestView;
import user.er.vo.ErHiringForInterestVO;
import user.er.vo.ErInterestVO;

public class ErInterestController extends WindowAdapter implements MouseListener {

	private ErInterestView eriv;
	private String er_id;
	private ErDAO er_dao;
	private final int DBL_CLICK = 2;

	public ErInterestController(ErInterestView eriv, String er_id) {
		this.eriv = eriv;
		this.er_id = er_id;
		er_dao = ErDAO.getInstance();
		setDTM(er_id);
	}// ������

	/**
	 * �α��� �Ǿ��ִ� ���������� id�� ����� DB���� ���ɱ����� ����� ������ ȭ�鿡 ������� ����ϴ� ��.
	 * 
	 * @param er_id
	 */
	private void setDTM(String er_id) {
		DefaultTableModel dtm = eriv.getDtmEeInfo();
		dtm.setRowCount(0); // DTM 0���� �ʱ�ȭ.

		try {
			// DB���� ����ȸ�縦 ��ȸ.
			List<ErHiringForInterestVO> list = er_dao.selectInterestEEInfoList(er_id);

			// JTable�� ��ȸ�� ������ ���.
			ErHiringForInterestVO ehfivo = null;
			String imgPath = "C:/dev/1949/03.����/��������/�����ڻ���/150x200px/";

			Object[] rowData = null;
			for (int i = 0; i < list.size(); i++) {

				/* list�� ����� VO��ü�� EeInterestVO��ü �����ϱ� */
//				eivo = list.get(i);

				// DTM�� �����͸� �߰��ϱ� ���� �������迭(Vector)�� �����ϰ� �����͸� �߰�
				rowData = new Object[11];
				rowData[0] = new Integer(i + 1);
				rowData[1] = ehfivo.getEe_num();
				rowData[2] = new ImageIcon(imgPath + ehfivo.getImg());
				rowData[3] = ehfivo.getName();
				rowData[4] = ehfivo.getRank();
				rowData[5] = ehfivo.getLoc();
				rowData[6] = ehfivo.getEducation();
				rowData[7] = new Integer(ehfivo.getAge());
				rowData[8] = ehfivo.getPortfolio();
				rowData[9] = ehfivo.getGender();
				rowData[10] = ehfivo.getInput_date();

				// DTM�� �߰�
				dtm.addRow(rowData);
			} // end for

			if (list.isEmpty()) {// ����� �޴��� ���� �� : ���ö� �߰� ��ư�� ���� �޴��� �߰� �� �� �ִ�.
				JOptionPane.showMessageDialog(eriv, "���ɱ��������� �����ϴ�. ���� ������������ ��Ʈ�� �����ּ���.");
			} // end if

		} catch (SQLException e) {
			JOptionPane.showMessageDialog(eriv, "DB���� �����͸� �޾ƿ��� �� ���� �߻�...");
			e.printStackTrace();
		} // end catch

	}// setDTM

	/**
	 * ����Ŭ������ �� ����� �޼ҵ�.
	 */
	@Override
	public void mouseClicked(MouseEvent me) {
		switch (me.getClickCount()) {
		case DBL_CLICK:

			if (me.getSource() == eriv.getJtEeInfo()) {
				JTable jt = eriv.getJtEeInfo(); // ���̺� �޾ƿ���
				String ee_num = ((String) jt.getValueAt(jt.getSelectedRow(), 1));
				//new ErDetailEeView(ehv, devo, eeNum, erId, interest);
			} // end if

		}// end switch
	}// mouseClicked

	////////// �Ⱦ��� �޼ҵ� //////////
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
	////////// �Ⱦ��� �޼ҵ� //////////

}// class
