package user.er.controller;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.sql.SQLException;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import user.dao.ErDAO;
import user.er.view.AppDetailView;
import user.er.view.AppListView;
import user.er.vo.DetailAppListVO;
import user.util.UserUtil;

public class AppListController extends WindowAdapter implements MouseListener {

	private AppListView alv;
	private String er_num;
	private ErDAO er_dao;

	private final int DBL_CLICK = 2; // 더블 클릭 상수

	public AppListController(AppListView alv, String er_num) {
		this.alv = alv;
		this.er_num = er_num;
		er_dao = ErDAO.getInstance();
		setDTM(er_num);
	}// 생성자

	/**
	 * 재현 : 지원현황 창 - 상세 지원 현황 창이 열릴 때 목록을 채우는 메소드.
	 * 
	 * @param er_num
	 */
	public void setDTM(String er_num) {

		DefaultTableModel dtm = alv.getDtmEeInfo();
		dtm.setRowCount(0); // DTM 0으로 초기화.

		try {
			// DB에서 관심회사를 조회.
			List<DetailAppListVO> list = er_dao.selectDetailApplist(er_num);

			StringBuilder appCnt = new StringBuilder("총 지원자 수 : ");
			appCnt.append(String.valueOf(list.size())).append(" 명");
			alv.getJlEeInfo().setText(appCnt.toString());

			// JTable에 조회한 정보를 출력.
			DetailAppListVO dalvo = null;
			String imgPath = "C:/dev/1949/03.개발/src/user/img/ee/";

			Object[] rowData = null;
			for (int i = 0; i < list.size(); i++) {

				/* list에 담겨진 VO객체로 EeInterestVO객체 생성하기 */
				dalvo = list.get(i);

				// DTM에 데이터를 추가하기 위한 일차원배열(Vector)을 생성하고 데이터를 추가
				rowData = new Object[12];
				rowData[0] = new Integer(i + 1);
				rowData[1] = dalvo.getApp_num();

				File imgFile = new File(imgPath + dalvo.getImg());
				// user.img.co패키지에 이미지 파일이 없다면 실행.
				// System.out.println(imgFile.exists());
				if (!imgFile.exists()) {
					try {
						Socket client = null; // "211.63.89.144", 7002 : 영근컴퓨터IP, 파일서버의 포트
						DataInputStream dis = null;
						DataOutputStream dos = null;
						FileOutputStream fos = null;
						UserUtil util = new UserUtil(); // 서버와 소통할 유틸객체 생성.

						util.reqFile(imgFile.getName(), "ee", client, dos, dis, fos);
					} catch (IOException e) {
						e.printStackTrace();
					} // end try
				} // end if

				rowData[2] = new ImageIcon(imgPath + dalvo.getImg());
				rowData[3] = dalvo.getName();
				rowData[4] = (dalvo.getRank().equals("N") ? "신입" : "경력");
				rowData[5] = dalvo.getLoc();
				rowData[6] = dalvo.getEducation();
				rowData[7] = dalvo.getAge();
				rowData[8] = (dalvo.getPortfolio().equals("Y") ? "존재" : "없음");
				rowData[9] = (dalvo.getGender().equals("M") ? "남자" : "여자");
				rowData[10] = dalvo.getApp_date();
				switch (dalvo.getApp_status()) {
				case "U":
					rowData[11] = "응답대기";
					break;
				case "R":
					rowData[11] = "열람";
					break;
				case "A":
					rowData[11] = "지원수락";
					break;
				case "D":
					rowData[11] = "지원거절";
				}// end switch

				// DTM에 추가
				dtm.addRow(rowData);
			} // end for

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
				showAppDetailInfo();
			} // end if
		}// end switch
	}// mouseClicked

	public void showAppDetailInfo() {
		JTable jt = alv.getJtEeInfo();
		String app_num = (String) jt.getValueAt(jt.getSelectedRow(), 1);
		AppDetailView adv = new AppDetailView(alv, app_num, this, er_num);

		// AppDetailView객체가 동작을 멈추면 true반환
		//System.out.println(adv.isActive());
		if (adv.isActive()) {
			setDTM(er_num);
		} // end if
	}// showAppDetailInfo

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
