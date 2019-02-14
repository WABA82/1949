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
import user.er.view.ErDetailEeView;
import user.er.view.ErInterestView;
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
	}// 생성자

	/**
	 * 로그인 되어있는 기업사용자의 id를 사용해 DB에서 관심구직자 목록을 가져와 화면에 목록으로 출력하는 일.
	 * 
	 * @param er_id
	 */
	private void setDTM(String er_id) {
		DefaultTableModel dtm = eriv.getDtmEeInfo();
		dtm.setRowCount(0); // DTM 0으로 초기화.

		try {
			// DB에서 관심회사를 조회.
			List<ErInterestVO> list = er_dao.selectInterestEEInfoList(er_id);

			// JTable에 조회한 정보를 출력.
			ErInterestVO eivo = null;
			String imgPath = "C:/dev/1949/03.개발/가데이터/구직자사진/150x200px/";

			Object[] rowData = null;
			for (int i = 0; i < list.size(); i++) {

				/* list에 담겨진 VO객체로 EeInterestVO객체 생성하기 */
				eivo = list.get(i);

				// DTM에 데이터를 추가하기 위한 일차원배열(Vector)을 생성하고 데이터를 추가
				rowData = new Object[11];
				rowData[0] = new Integer(i + 1);
				rowData[1] = eivo.getEe_num();
				rowData[2] = new ImageIcon(imgPath + eivo.getImg());
				rowData[3] = eivo.getName();
				rowData[4] = eivo.getRank();
				rowData[5] = eivo.getLoc();
				rowData[6] = eivo.getEducation();
				rowData[7] = new Integer(eivo.getAge());
				rowData[8] = eivo.getPortfolio();
				rowData[9] = eivo.getGender();
				rowData[10] = eivo.getInput_date();

				// DTM에 추가
				dtm.addRow(rowData);
			} // end for

			if (list.isEmpty()) {// 등록한 메뉴가 없을 때 : 도시락 추가 버튼을 통해 메뉴를 추가 할 수 있다.
				JOptionPane.showMessageDialog(eriv, "관심구인정보가 없습니다. 먼저 구인정보에서 하트를 눌러주세요.");
			} // end if

		} catch (SQLException e) {
			JOptionPane.showMessageDialog(eriv, "DB에서 데이터를 받아오는 중 문제 발생...");
			e.printStackTrace();
		} // end catch

	}// setDTM

	/**
	 * 더블클릭했을 때 실행될 메소드.
	 */
	@Override
	public void mouseClicked(MouseEvent me) {
		switch (me.getClickCount()) {
		case DBL_CLICK:

			if (me.getSource() == eriv.getJtEeInfo()) {
				JTable jt = eriv.getJtEeInfo(); // 테이블 받아오기
				String ee_num = ((String) jt.getValueAt(jt.getSelectedRow(), 1));
				new ErDetailEeView(eriv, er_id, ee_num);
			} // end if

		}// end switch
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
