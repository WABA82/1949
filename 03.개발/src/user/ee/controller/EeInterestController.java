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

import user.dao.JaeHyunEEDAO;
import user.ee.view.EeDetailErView;
import user.ee.view.EeInterestView;
import user.ee.vo.EeHiringVO;

public class EeInterestController extends WindowAdapter implements ActionListener, MouseListener {

	private EeInterestView eiv;
	private JaeHyunEEDAO jhee_dao;
	public static final int DBL_CLICK = 2; // 더블 클릭 상수.

	public EeInterestController(EeInterestView eiv, List<EeHiringVO> ehvo) {
		this.eiv = eiv;
		jhee_dao = JaeHyunEEDAO.getInstance();
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
			} // end if
		} // end switch
//		try {
//			LunchDetailVO ldvo = la_dao.selectDetailLunch((String) jt.getValueAt(jt.getSelectedRow(), 1));
//			new LunchDetailView(lmv, ldvo, this);
//		} catch (SQLException se) {
//			JOptionPane.showMessageDialog(lmv, "DB에서 문제가 발생했습니다.");
//			se.printStackTrace();
//		} // end catch
	}// mouseClicked

	/**
	 * 관심 구인정보창의 table목록을 셋팅하는 메소드.
	 */
	private void setDTM() {
		DefaultTableModel dtmErInfo = eiv.getdtmErInfo();
		dtmErInfo.setRowCount(0); // DTM 0으로 초기화.

		try {
			// DB에서 관심회사를 조회.
			List<EeHiringVO> list = jhee_dao.selectInterestErInfo();

//			// JTable에 조회한 정보를 출력.
//			LunchVO lv = null;
//
//			Object[] rowData = null;
//			for (int i = 0; i < listLunch.size(); i++) {
//				lv = listLunch.get(i);
//				// DTM에 데이터를 추가하기 위한 일차원배열(Vector)을 생성하고 데이터를 추가
//				rowData = new Object[5];
//				rowData[0] = new Integer(i + 1);
//				rowData[1] = lv.getLunchCode();
//				rowData[2] = new ImageIcon(imgPath + lv.getImg());
//				rowData[3] = lv.getLunchName();
//				rowData[4] = new Integer(lv.getPrice());
//
//				// DTM에 추가
//				dtmLunch.addRow(rowData);
//
//			} // end for
//
//			if (listLunch.isEmpty()) {// 등록한 메뉴가 없을 때 : 도시락 추가 버튼을 통해 메뉴를 추가 할 수 있다.
//				JOptionPane.showMessageDialog(lmv, "입력된 제품이 없습니다. 도시락을 추가해 주세요.");
//			} // end if
//
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(eiv, "DB에서 데이터를 받아오는 중 문제 발생...");
			e.printStackTrace();
		} // end catch

	}// setDTM

	private void showDetailErinfo() {
		new EeDetailErView(null, null, null, null);
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
