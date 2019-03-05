package admin.controller;

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

import admin.dao.AdminDAO;
import admin.view.AdminMgMtView;
import admin.view.CoModifyView;
import admin.view.EeModifyView;
import admin.view.ErModifyView;
import admin.view.UserModifyView;
import admin.vo.CoInfoVO;
import admin.vo.CoListVO;
import admin.vo.EeInfoVO;
import admin.vo.EeListVO;
import admin.vo.ErInfoVO;
import admin.vo.ErListVO;
import admin.vo.UserInfoVO;
import admin.vo.UserListVO;

public class AdminMgMtController extends WindowAdapter implements MouseListener {

	private AdminMgMtView ammv;
	private AdminDAO a_dao;
	private Thread threadUser, threadEe, threadEr, threadCo; ///////////// 구현예정0306
	
	private static final int DBL_CLICK = 2;
	
	public AdminMgMtController(AdminMgMtView ammv) {
		a_dao = AdminDAO.getInstance();
		this.ammv = ammv;
		setUser();
	}
	
	private void msgCenter(String msg) {
		JOptionPane.showMessageDialog(ammv, msg);
	}
	
	/**
	 * DB에서 조회한 유저 정보를 테이블에 등록하는 메소드
	 */
	public void setUser() {
		try {
			List<UserListVO> list = a_dao.selectAllUser();
			DefaultTableModel dtm = ammv.getDtmUser();
			
			dtm.setRowCount(0);

			String[] rowData = new String[8];
			
			UserListVO ulvo = null;
			for(int i=0; i<list.size(); i++) {
				ulvo = list.get(i);
				rowData[0] = String.valueOf(i+1);
				rowData[1] = ulvo.getId();
				rowData[2] = ulvo.getName();
				rowData[3] = ulvo.getTel();
				rowData[4] = ulvo.getAddr();
				rowData[5] = ulvo.getEmail();
				rowData[6] = ulvo.getUserType().equals("E") ? "일반" : "기업";
				rowData[7] = ulvo.getInputDate();
				dtm.addRow(rowData);
			}
			
		} catch (SQLException e) {
			msgCenter("DB에 문제가 발생했습니다.");
			e.printStackTrace();
		}
	}
	
	/**
	 * DB에서 조회한 일반 사용자 기본 정보를 테이블에 등록하는 메소드
	 */
	public void setEe() {
		try {
			List<EeListVO> list = a_dao.selectAllEe();
			DefaultTableModel dtm = ammv.getDtmEe();
			
			dtm.setRowCount(0);

			Object[] rowData = new Object[13];
			
			EeListVO elvo = null;
			for(int i=0; i<list.size(); i++) {
				elvo = list.get(i);
				rowData[0] = String.valueOf(i+1);
				rowData[1] = elvo.getEeNum();
				rowData[2] = new ImageIcon("C:/dev/1949/03.개발/src/admin/img/ee/"+elvo.getImg());
				rowData[3] = elvo.getEeId();
				rowData[4] = elvo.getName();
				switch(elvo.getRank()) {
				case "N" : 
					rowData[5] = "신입";
					break;
				case "C" :
					rowData[5] = "경력";
					break;
				}
				rowData[6] = elvo.getLoc();
				rowData[7] = elvo.getEducation();
				rowData[8] = String.valueOf(elvo.getAge())+"세";
				rowData[9] = elvo.getPortfolio().equals("Y") ? "있음" : "없음";
				rowData[10] = elvo.getGender().equals("M") ? "남자" : "여자";
				rowData[11] = elvo.getExtRsm() == null ? "없음" : elvo.getExtRsm();
				rowData[12] = elvo.getInputDate();
				dtm.addRow(rowData);
			}
			
		} catch (SQLException e) {
			msgCenter("DB에 문제가 발생했습니다.");
			e.printStackTrace();
		}
	}
	
	/**
	 * DB에서 조회한 기업 사용자 구인 정보를 테이블에 등록하는 메소드
	 */
	public void setEr() { 
		try {
			List<ErListVO> list = a_dao.selectAllEr();
			DefaultTableModel dtm = ammv.getDtmEr();
			
			dtm.setRowCount(0);

			String[] rowData = new String[13];
			
			ErListVO elvo = null;
			for(int i=0; i<list.size(); i++) {
				elvo = list.get(i);
				rowData[0] = String.valueOf(i+1);
				rowData[1] = elvo.getErNum();
				rowData[2] = elvo.getSubject();
				rowData[3] = elvo.getCoName();
				rowData[4] = elvo.getErId();
				rowData[5] = elvo.getName();
				rowData[6] = elvo.getTel();
				switch(elvo.getRank()) {
				case "N" : 
					rowData[7] = "신입";
					break;
				case "C" :
					rowData[7] = "경력";
					break;
				}
				rowData[8] = elvo.getLoc();
				rowData[9] = elvo.getEducation();
				switch(elvo.getHireType()) {
				case "N" : 
					rowData[10] = "계약직";
					break;
				case "C" :
					rowData[10] = "정규직";
					break;
				case "F" :
					rowData[10] = "프리";
					break;
				}
				rowData[11] = String.valueOf(elvo.getSal());
				rowData[12] = elvo.getInputDate();
				dtm.addRow(rowData);
			}
			
		} catch (SQLException e) {
			msgCenter("DB에 문제가 발생했습니다.");
			e.printStackTrace();
		}
	}
	
	/**
	 * DB에서 조회한 기업 정보를 테이블에 등록하는 메소드
	 */
	public void setCo() {
		try {
			List<CoListVO> list = a_dao.selectAllCo();
			DefaultTableModel dtm = ammv.getDtmCo();
			
			dtm.setRowCount(0);

			Object[] rowData = new Object[8];
			
			CoListVO clvo = null;
			for(int i=0; i<list.size(); i++) {
				clvo = list.get(i);
				rowData[0] = String.valueOf(i+1);
				rowData[1] = clvo.getCoNum();
				rowData[2] = new ImageIcon("C:/dev/1949/03.개발/src/admin/img/co/"+clvo.getImg1());
				rowData[3] = clvo.getCoName();
				rowData[4] = clvo.getErId();
				rowData[5] = clvo.getEstDate();
				rowData[6] = String.valueOf(clvo.getMemberNum())+"명";
				rowData[7] = clvo.getInputDate();
				dtm.addRow(rowData);
			}
			
		} catch (SQLException e) {
			msgCenter("DB에 문제가 발생했습니다.");
			e.printStackTrace();
		}
	}
	
	/**
	 * 유저 정보 상세 페이지를 띄우는 메소드
	 * @param id
	 * @throws SQLException
	 */
	public void showUserModify(String id) throws SQLException {
		UserInfoVO uivo = a_dao.selectOneUser(id);
		new UserModifyView(ammv, uivo, this);
	}
	
	/**
	 * 일반 사용자 기본 정보 상세 페이지를 띄우는 메소드
	 * @param eeNum
	 * @throws SQLException
	 */
	public void showEeModify(String eeNum) throws SQLException {
		EeInfoVO eivo = a_dao.selectOneEe(eeNum, "eeNum");
		new EeModifyView(ammv, eivo, this);
	}
	
	/**
	 * 기업 사용자 구인 정보 상세 페이지를 띄우는 메소드
	 * @param erNum
	 * @throws SQLException
	 */
	public void showErModify(String erNum) throws SQLException {
		ErInfoVO eivo = a_dao.selectOneEr(erNum);
		new ErModifyView(ammv, eivo, this);
	}
	
	/**
	 * 기업 사용자 기업 정보 상세 페이지를 띄우는 메소드
	 * @param coNum
	 * @throws SQLException
	 */
	public void showCoModify(String coNum) throws SQLException {
		CoInfoVO civo = a_dao.selectOneCo(coNum);
		new CoModifyView(ammv, civo, this);
	}
	
	@Override
	public void mouseClicked(MouseEvent me) {
		if (ammv.getJtb().getSelectedIndex() == 0) {
			setUser();
		}
		
		if (ammv.getJtb().getSelectedIndex() == 1) {
			setEr();
		}
		
		if (ammv.getJtb().getSelectedIndex() == 2) {
			setEe();
		}
		
		if (ammv.getJtb().getSelectedIndex() == 3) {
			setCo();
		}
		
		switch(me.getClickCount()) {
		case DBL_CLICK :
			JTable jt = null;
			if (me.getSource() == ammv.getJtUser()) {
				jt = ammv.getJtUser();
				
				String id = (String)jt.getValueAt(jt.rowAtPoint(me.getPoint()), 1);
				
				try {
					showUserModify(id);
				} catch (SQLException e) {
					msgCenter("DB 접속 실패했습니다.");
					e.printStackTrace();
				}
			}
			
			if (me.getSource() == ammv.getJtCo()) {
				
				jt = ammv.getJtCo();
				
				String coNum = (String)jt.getValueAt(jt.rowAtPoint(me.getPoint()), 1);
				
				try {
					showCoModify(coNum);
				} catch (SQLException e) {
					msgCenter("DB 접속 실패했습니다.");
					e.printStackTrace();
				}
			}
			
			if (me.getSource() == ammv.getJtEr()) {
				
				jt = ammv.getJtEr();
				
				String erNum = (String)jt.getValueAt(jt.rowAtPoint(me.getPoint()), 1);
				
				try {
					showErModify(erNum);
				} catch (SQLException e) {
					msgCenter("DB 접속 실패했습니다.");
					e.printStackTrace();
				}
			}
			
			if (me.getSource() == ammv.getJtEe()) {
				
				jt = ammv.getJtEe();
				
				String eeNum = (String)jt.getValueAt(jt.rowAtPoint(me.getPoint()), 1);
				
				try {
					showEeModify(eeNum);
				} catch (SQLException e) {
					msgCenter("DB 접속 실패했습니다.");
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public void windowClosing(WindowEvent e) {
		ammv.dispose();
	}
	
	@Override
	public void mousePressed(MouseEvent e) {}
	@Override
	public void mouseReleased(MouseEvent e) {}
	@Override
	public void mouseEntered(MouseEvent e) {}
	@Override
	public void mouseExited(MouseEvent e) {}
}
