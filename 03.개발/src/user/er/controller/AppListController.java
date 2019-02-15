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

	private final int DBL_CLICK = 2; // 더블 클릭 상수

	public AppListController(AppListView alv, String er_num) {
		this.alv = alv;
		// this.er_num = er_num;
		er_dao = ErDAO.getInstance();
		setDTM(er_num);
	}// 생성자

	private void setDTM(String er_num) {

		DefaultTableModel dtm = alv.getDtmEeInfo();
		dtm.setRowCount(0); // DTM 0으로 초기화.

		try {
			// DB에서 관심회사를 조회.
			List<DetailAppVO> list = er_dao.selectDetailApplist(er_num);

			// JTable에 조회한 정보를 출력.
			DetailAppVO davo = null;
			String imgPath = "C:/dev/1949/03.개발/가데이터/구직자사진/150x200px/";

			Object[] rowData = null;
			for (int i = 0; i < list.size(); i++) {

				/* list에 담겨진 VO객체로 EeInterestVO객체 생성하기 */
				davo = list.get(i);

				// DTM에 데이터를 추가하기 위한 일차원배열(Vector)을 생성하고 데이터를 추가
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

				// DTM에 추가
				dtm.addRow(rowData);
			} // end for

			if (list.isEmpty()) {// 등록한 메뉴가 없을 때 : 도시락 추가 버튼을 통해 메뉴를 추가 할 수 있다.
				JOptionPane.showMessageDialog(alv, "등록한 구인공고에 아직 지원한 구직자가 없습니다.");
			} // end if

		} catch (SQLException e) {
			JOptionPane.showMessageDialog(alv, "DB에서 데이터를 받아오는 중 문제 발생...");
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

	///////////////// 안쓰는 메서드/////////////////
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
	///////////////// 안쓰는 메서드/////////////////

}// class
