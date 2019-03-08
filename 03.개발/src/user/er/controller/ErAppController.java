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
	}// 생성자

	/**
	 * ErAppView의 창이 띄워 질 때 DB에서 정보를 가져와 띄워주는 일.
	 */
	public void setDTM(String er_Id) {

		DefaultTableModel dtm = erav.getDtmEeInfo();
		dtm.setRowCount(0); // DTM 0으로 초기화.

		try {
			// DB에서 관심회사를 조회.
			List<ErListVO> list = er_dao.selectErList(er_Id);

			StringBuffer erCnt = new StringBuffer("내 구인정보 수 : ");
			erCnt.append(String.valueOf(list.size())).append(" 개");
			erav.getJlEeInfo().setText(erCnt.toString());

			// JTable에 조회한 정보를 출력.
			ErListVO eivo = null;

			Object[] rowData = null;
			for (int i = 0; i < list.size(); i++) {

				/* list에 담겨진 VO객체로 EeInterestVO객체 생성하기 */
				eivo = list.get(i);

				// DTM에 데이터를 추가하기 위한 일차원배열(Vector)을 생성하고 데이터를 추가
				rowData = new Object[8];
				rowData[0] = new Integer(i + 1);
				rowData[1] = eivo.getErNum();
				rowData[2] = eivo.getSubject();
				rowData[3] = (eivo.getRank().equals("N") ? "신입" : "경력");
				rowData[4] = eivo.getLoc();
				rowData[5] = eivo.getEducation();
				switch (eivo.getHireType()) {
				case "C":
					rowData[6] = "정규직";
					break;
				case "N":
					rowData[6] = "비정규직";
					break;
				case "F":
					rowData[6] = "프리랜서";
					break;
				}// end switch
				rowData[7] = eivo.getInputDate();

				// DTM에 추가
				dtm.addRow(rowData);
			} // end for

		} catch (SQLException e) {
			JOptionPane.showMessageDialog(erav, "DB에서 데이터를 받아오는 중 문제 발생...");
			e.printStackTrace();
		} // end catch

	}// setDTM

	@Override
	public void windowClosing(WindowEvent e) {
		erav.dispose();
	}// windowClosing

	/**
	 * ErAppView에서 테이블 더블클릭시 해당 row의 지원자 상세 정보 창 띄우기.
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
				JOptionPane.showMessageDialog(erav, "등록한 구인공고에 아직 지원한 구직자가 없습니다.");
				return;
			} // end if
			
			AppListView alv = new AppListView(erav, er_num);
			
			// AppListView객체가 동작을 멈추면 true반환.
			if (alv.isActive()) {
				setDTM(er_id);
			} // end if
			
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(erav, "DB에서 조회 중 문제가 발생했습니다.");
			e.printStackTrace();
		}// end catch

		
	}// showAppList

	/////////// 안쓰는 메소드 ///////////
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
	/////////// 안쓰는 메소드 ///////////

}// class
