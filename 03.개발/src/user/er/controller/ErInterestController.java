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
import user.er.view.ErDetailEeView;
import user.er.view.ErInterestView;
import user.er.vo.DetailEeInfoVO;
import user.er.vo.ErHiringForInterestVO;
import user.util.UserUtil;

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

	@Override
	public void windowClosing(WindowEvent e) {
		eriv.dispose(); // 종료처리
	}// windowClosing(WindowEvent e)

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
			List<ErHiringForInterestVO> list = er_dao.selectInterestEEInfoList(er_id);

			StringBuffer interestCnt = new StringBuffer("내 관심 구직자 수 : ");
			interestCnt.append(String.valueOf(list.size())).append(" 개");
			eriv.getJlEeInfo().setText(interestCnt.toString());

			// JTable에 조회한 정보를 출력.
			ErHiringForInterestVO erhForInterest = null;
			String imgPath = "C:/dev/1949/03.개발/src/user/img/ee/";

			Object[] rowData = null;
			for (int i = 0; i < list.size(); i++) {

				/* list에 담겨진 VO객체로 EeInterestVO객체 생성하기 */
				erhForInterest = list.get(i);

				// DTM에 데이터를 추가하기 위한 일차원배열(Vector)을 생성하고 데이터를 추가
				rowData = new Object[11];
				rowData[0] = new Integer(i + 1);
				rowData[1] = erhForInterest.getEe_num();

				File imgFile = new File(imgPath + erhForInterest.getImg());
				// user.img.co패키지에 이미지 파일이 없다면 실행.
				if (!imgFile.exists()) {
					Socket client = null; // "211.63.89.144", 7002 : 영근컴퓨터IP, 파일서버의 포트
					DataInputStream dis = null;
					DataOutputStream dos = null;
					FileOutputStream fos = null;
					UserUtil util = new UserUtil(); // 서버와 소통할 유틸객체 생성.
					try {
						util.reqFile(imgFile.getName(), "ee", client, dos, dis, fos);
					} catch (IOException e) {
						e.printStackTrace();
					} // end try
				} // end if

				rowData[2] = new ImageIcon(imgPath + erhForInterest.getImg());
				rowData[3] = erhForInterest.getName();
				rowData[4] = (erhForInterest.getRank().equals("N") ? "경력" : "신입");
				rowData[5] = erhForInterest.getLoc();
				rowData[6] = erhForInterest.getEducation();
				rowData[7] = new Integer(erhForInterest.getAge());
				rowData[8] = (erhForInterest.getPortfolio().equals("Y") ? "있음" : "없음");
				rowData[9] = (erhForInterest.getGender().equals("M") ? "남자" : "여자");
				rowData[10] = erhForInterest.getInput_date();

				// DTM에 추가
				dtm.addRow(rowData);
			} // end for

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
				showDetailEeInfo();
			} // end if
		}// end switch
	}// mouseClicked

	private void showDetailEeInfo() {
		JTable jt = eriv.getJtEeInfo();
		String eeNum = String.valueOf(jt.getValueAt(jt.getSelectedRow(), 1));
		DetailEeInfoVO devo = null;
		try {
			devo = er_dao.selectDetailEe(eeNum, er_id);
			String ee_num = ((String) jt.getValueAt(jt.getSelectedRow(), 1));
			ErDetailEeView erdev = new ErDetailEeView(eriv, devo, ee_num, er_id, "1");

			// ErDetailEeView 객체가 동작을 멈추면 true발생
			if (erdev.isActive()) {
				setDTM(er_id);
			} // end if

		} catch (SQLException e) {
			e.printStackTrace();
		} // end catch
	}// showDetailErInfo()

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
