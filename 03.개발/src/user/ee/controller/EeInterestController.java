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
	public static final int DBL_CLICK = 2; // 더블 클릭 상수.

	public EeInterestController(EeInterestView eiv, String ee_id) {
		this.eiv = eiv;
		this.ee_id = ee_id;
		ee_dao = EeDAO.getInstance();
		setDTM(ee_id);
	}// 생성자

	@Override
	public void windowClosing(WindowEvent arg0) {
		eiv.dispose();
	}// windowClosing

	@Override
	public void actionPerformed(ActionEvent e) {

	}// actionPerformed

	@Override
	public void mouseClicked(MouseEvent me) {
		switch (me.getClickCount()) { // 클릭 횟수 비교.
		case DBL_CLICK: // 더블 클릭 시
			if (me.getSource() == eiv.getjtErInfo()) { // 테이블 더블클릭 시.
				JTable jt = eiv.getjtErInfo();
				String erNum= String.valueOf(jt.getValueAt(jt.getSelectedRow(), 1));
				DetailErInfoVO deivo=null;
				try {
					deivo = ee_dao.selectDetail(erNum);
				} catch (SQLException e) {
					e.printStackTrace();
				}
				EeDetailErView edev = new EeDetailErView(null, deivo, erNum, "gong1",null, deivo.getInterest());
			} // end if
		} // end switch
	}// mouseClicked

	/**
	 * 관심 구인정보창의 table목록을 셋팅하는 메소드.
	 */
	private void setDTM(String ee_id) {
		DefaultTableModel dtmErInfo = eiv.getdtmErInfo();
		dtmErInfo.setRowCount(0); // DTM 0으로 초기화.

		try {
			// DB에서 관심회사를 조회.
			List<EeInterestVO> list = ee_dao.selectInterestErInfo(ee_id);

			// JTable에 조회한 정보를 출력.
			EeInterestVO eivo = null;

			Object[] rowData = null;
			for (int i = 0; i < list.size(); i++) {

				/* list에 담겨진 VO객체로 EeInterestVO객체 생성하기 */
				eivo = list.get(i);

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
				rowData[8] = eivo.getSal();
				rowData[9] = eivo.getInputDate();

				// DTM에 추가
				dtmErInfo.addRow(rowData);
			} // end for

			if (list.isEmpty()) {// 등록한 메뉴가 없을 때 : 도시락 추가 버튼을 통해 메뉴를 추가 할 수 있다.
				JOptionPane.showMessageDialog(eiv, "관심구인정보가 없습니다. 먼저 구인정보에서 하트를 눌러주세요.");
			} // end if

		} catch (SQLException e) {
			JOptionPane.showMessageDialog(eiv, "DB에서 데이터를 받아오는 중 문제 발생...");
			e.printStackTrace();
		} // end catch

	}// setDTM

	private void showDetailErinfo() {
	}// showDetailErinfo

	////////// 안쓰는 메소드//////////
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
	////////// 안쓰는 메소드//////////

}// class
