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
import user.ee.vo.EeInterestVO;

public class EeAppController extends WindowAdapter implements MouseListener {

	private EeAppView eav;
	private EeDAO ee_dao;

	public EeAppController(EeAppView eav) {
		this.eav = eav;
		EeDAO ee_dao = EeDAO.getInstance();
	}// ������

	private void setDTM() {
//		DefaultTableModel dtmErInfo = eav.getDtmEr();
//		dtmErInfo.setRowCount(0); // DTM 0���� �ʱ�ȭ.
//
//		try {
//			// DB���� ����ȸ�縦 ��ȸ.
//			List<EeInterestVO> list = ee_dao.
//
////			// JTable�� ��ȸ�� ������ ���.
////			 eeappvo = null;
////
////			Object[] rowData = null;
////			for (int i = 0; i < list.size(); i++) {
////				/* list�� ����� VO��ü�� EeInterestVO��ü �����ϱ� */
////				eivo = list.get(i);
////				// DTM�� �����͸� �߰��ϱ� ���� �������迭(Vector)�� �����ϰ� �����͸� �߰�
////				rowData = new Object[11];
////
////				// DTM�� �߰�
////				dtmErInfo.addRow(rowData);
////			} // end for
//
//			if (list.isEmpty()) {// ����� �޴��� ���� �� : ���ö� �߰� ��ư�� ���� �޴��� �߰� �� �� �ִ�.
//				JOptionPane.showMessageDialog(eav, "���ɱ��������� �����ϴ�. ���� ������������ ��Ʈ�� �����ּ���.");
//			} // end if
//
//		} catch (SQLException e) {
//			JOptionPane.showMessageDialog(eav, "DB���� �����͸� �޾ƿ��� �� ���� �߻�...");
//			e.printStackTrace();
//		} // end catch

	}// setDTM

	@Override
	public void windowClosing(WindowEvent e) {
	}// windowClosing

	@Override
	public void mouseClicked(MouseEvent e) {
//		switch (me.getClickCount()) { // Ŭ�� Ƚ�� ��.
//		case DBL_CLICK: // ���� Ŭ�� ��
//			if (me.getSource() == eiv.getjtErInfo()) { // ���̺� ����Ŭ�� ��.
//				JTable jt = eiv.getjtErInfo();
//				String erNum= String.valueOf(jt.getValueAt(jt.getSelectedRow(), 1));
//				DetailErInfoVO deivo=null;
//				try {
//					deivo = ee_dao.selectDetail(erNum, ee_id);
//				} catch (SQLException e) {
//					e.printStackTrace();
//				}
//				EeDetailErView edev = new EeDetailErView(null, deivo, erNum, "gong1",null, deivo.getInterest());
//			} // end if
//		} // end switch
	}// mouseClicked

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
