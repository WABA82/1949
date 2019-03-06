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
import user.ee.vo.EeAppVO;

public class EeAppController extends WindowAdapter implements MouseListener {

	private EeAppView eav;
	private EeDAO ee_dao;
	private String ee_id;
	private EeAppVO eavo;
	private final int DBL_CLICK = 2;

	public EeAppController(EeAppView eav, String ee_id) {
		this.eav = eav;
		this.ee_id = ee_id;
		ee_dao = EeDAO.getInstance();
		setDTM(ee_id);
	}// 생성자

	private void setDTM(String ee_id) {
		DefaultTableModel dtm = eav.getDtmEr();
		dtm.setRowCount(0); // DTM 0으로 초기화.

		try {
			// DB에서 관심회사를 조회.
			List<EeAppVO> list = ee_dao.selectAppList(ee_id);

			// 지원 현황 갯수 화면에 보여주기.
			StringBuilder cnt = new StringBuilder();
			cnt.append("내 지원 현황 수 : ").append(String.valueOf(list.size())).append(" 개");
			eav.getJlEeAppCnt().setText(cnt.toString());

			// JTable에 조회한 정보를 출력.
			eavo = null;

			Object[] rowData = null;
			for (int i = 0; i < list.size(); i++) {
				/* list에 담겨진 VO객체로 EeInterestVO객체 생성하기 */
				eavo = list.get(i);

				// DTM에 데이터를 추가하기 위한 일차원배열(Vector)을 생성하고 데이터를 추가
				rowData = new Object[12];
				rowData[0] = new Integer(i + 1);
				rowData[1] = eavo.getApp_num();
				rowData[2] = eavo.getSubject();
				rowData[3] = eavo.getCo_name();
				rowData[4] = (eavo.getRank().equals("N") ? "신입" : "경력"); // 'N'신입, 'R'경력.
				rowData[5] = eavo.getLoc();
				rowData[6] = eavo.getEducation();
				switch (eavo.getHire_type()) {
				case "C":
					rowData[7] = "정규직";
					break;
				case "N":
					rowData[7] = "비정규직";
					break;
				case "F":
					rowData[7] = "프리랜서";
				}// end switch
				rowData[8] = new Integer(eavo.getSal());
				rowData[9] = eavo.getApp_date();
				switch (eavo.getApp_status()) {
				case "U":
					rowData[10] = "응답대기";
					break;
				case "R":
					rowData[10] = "열람";
					break;
				case "A":
					rowData[10] = "지원수락";
					break;
				case "D":
					rowData[10] = "지원거절";
				}// end switch

				/* DTM에 추가 */
				dtm.addRow(rowData);
			} // end for

		} catch (SQLException e) {
			JOptionPane.showMessageDialog(eav, "DB에서 데이터를 받아오는 중 문제 발생...");
			e.printStackTrace();
		} // end catch

	}// setDTM

	@Override
	public void windowClosing(WindowEvent e) {
		eav.dispose();
	}// windowClosing

	/**
	 * 지원현황 목록에서 한 행을 선택해 더블클릭했을 때 상세정보 보기.
	 */
	@Override
	public void mouseClicked(MouseEvent me) {
		switch (me.getClickCount()) { // 클릭 횟수 비교.
		case DBL_CLICK: // 더블 클릭 시
			if (me.getSource() == eav.getJtEr()) { // 테이블 더블클릭 시.
				showDetailErinfo();
			} // end if
		} // end switch
	}// mouseClicked

	/**
	 * 더블 클릭시 띄우는 창
	 */
	private void showDetailErinfo() {
		JTable jt = eav.getJtEr();
		String app_num = String.valueOf(jt.getValueAt(jt.getSelectedRow(), 1));
		DetailErInfoVO deivo = null;
		String appStatus = "";
		try {
			String erNum = ee_dao.selectErNumFromAppTb(app_num);
			deivo = ee_dao.selectDetail(erNum, ee_id);
			appStatus = ee_dao.selectApplication(ee_id, erNum);

			// 사용자가 상세 구직정보 창을 보기전 응답상태에 따라 메시지를 보여주는 메서드.
			appStatusMsg(appStatus);

			EeDetailErView edev = new EeDetailErView(eav, deivo, erNum, ee_id, appStatus);

			// edev.isActive() - EeDetailErView의 창이 닫혀지면 true발생.
			if (edev.isActive()) {
				setDTM(ee_id);
			} // end if

		} catch (SQLException e) {
			e.printStackTrace();
		} // end catch
	}// showDetailErinfo

	/**
	 * 상세 구직 정보 창을 보기전 응답상태에 따라 메시지를 띄워주는 메서드
	 */
	private void appStatusMsg(String appStatus) {
		switch (appStatus.toUpperCase()) {
		case "U":
			JOptionPane.showMessageDialog(eav, "인사담당자가 아직 지원정보를 확인하지 않았습니다.");
			break;
		case "R":
			JOptionPane.showMessageDialog(eav, "인사담당자가 검토중 입니다.");
			break;
		case "A":
			JOptionPane.showMessageDialog(eav, "축하합니다!\n인사담당자가 긍정적으로 서류를 확인했습니다.\n개별적인 면접 연락이 올 예정입니다.");
			break;
		case "D":
			JOptionPane.showMessageDialog(eav, "죄송합니다.\n불합격하셨습니다.");
		}// switch
	}// appStatusMsg()

	/////////////////////////////// 안 쓰는 메소드들 ///////////////////////////////
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
