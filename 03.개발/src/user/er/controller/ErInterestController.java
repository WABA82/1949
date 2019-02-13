package user.er.controller;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.sql.SQLException;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import user.dao.ErDAO;
import user.ee.vo.EeInterestVO;
import user.er.view.ErInterestView;
import user.er.vo.ErInterestVO;

public class ErInterestController extends WindowAdapter implements MouseListener {

	private ErInterestView eriv;
	private String er_id;
	private ErDAO er_dao;

	public ErInterestController(ErInterestView eriv, String er_id) {
		this.eriv = eriv;
		this.er_id = er_id;
		er_dao = ErDAO.getInstance();
		setDTM(er_id);
	}// ������

	private void setDTM(String er_id) {
		DefaultTableModel dtmErInfo = eriv.getDtmEeInfo();
		dtmErInfo.setRowCount(0); // DTM 0���� �ʱ�ȭ.

		try {
			// DB���� ����ȸ�縦 ��ȸ.
			List<ErInterestVO> list = er_dao.selectInterestEEInfoList(er_id);

			// JTable�� ��ȸ�� ������ ���.
			EeInterestVO eivo = null;

			Object[] rowData = null;
			for (int i = 0; i < list.size(); i++) {

				/* list�� ����� VO��ü�� EeInterestVO��ü �����ϱ� */
				//eivo = list.get(i);

				// DTM�� �����͸� �߰��ϱ� ���� �������迭(Vector)�� �����ϰ� �����͸� �߰�
				rowData = new Object[10];
				rowData[0] = new Integer(i + 1);
				rowData[1] = eivo.getErNum();
				rowData[2] = eivo.getSubject();
				rowData[3] = eivo.getCoName();
				rowData[4] = eivo.getRank();
				rowData[5] = eivo.getLoc();
				rowData[6] = eivo.getEducation();
				rowData[7] = eivo.getHireType();
				rowData[8] = new Integer(eivo.getSal());
				rowData[9] = eivo.getInputDate();

				// DTM�� �߰�
				dtmErInfo.addRow(rowData);
			} // end for

			if (list.isEmpty()) {// ����� �޴��� ���� �� : ���ö� �߰� ��ư�� ���� �޴��� �߰� �� �� �ִ�.
				JOptionPane.showMessageDialog(eriv, "���ɱ��������� �����ϴ�. ���� ������������ ��Ʈ�� �����ּ���.");
			} // end if

		} catch (SQLException e) {
			JOptionPane.showMessageDialog(eriv, "DB���� �����͸� �޾ƿ��� �� ���� �߻�...");
			e.printStackTrace();
		} // end catch
		
	}// setDTM

	@Override
	public void mouseClicked(MouseEvent e) {
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
