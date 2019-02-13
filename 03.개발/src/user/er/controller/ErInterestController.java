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
	}// 생성자

	private void setDTM(String er_id) {
		DefaultTableModel dtmErInfo = eriv.getDtmEeInfo();
		dtmErInfo.setRowCount(0); // DTM 0으로 초기화.

		try {
			// DB에서 관심회사를 조회.
			List<ErInterestVO> list = er_dao.selectInterestEEInfoList(er_id);

			// JTable에 조회한 정보를 출력.
			EeInterestVO eivo = null;

			Object[] rowData = null;
			for (int i = 0; i < list.size(); i++) {

				/* list에 담겨진 VO객체로 EeInterestVO객체 생성하기 */
				//eivo = list.get(i);

				// DTM에 데이터를 추가하기 위한 일차원배열(Vector)을 생성하고 데이터를 추가
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

				// DTM에 추가
				dtmErInfo.addRow(rowData);
			} // end for

			if (list.isEmpty()) {// 등록한 메뉴가 없을 때 : 도시락 추가 버튼을 통해 메뉴를 추가 할 수 있다.
				JOptionPane.showMessageDialog(eriv, "관심구인정보가 없습니다. 먼저 구인정보에서 하트를 눌러주세요.");
			} // end if

		} catch (SQLException e) {
			JOptionPane.showMessageDialog(eriv, "DB에서 데이터를 받아오는 중 문제 발생...");
			e.printStackTrace();
		} // end catch
		
	}// setDTM

	@Override
	public void mouseClicked(MouseEvent e) {
	}// mouseClicked

	////////// 안쓰는 메소드 //////////
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
	////////// 안쓰는 메소드 //////////

}// class
