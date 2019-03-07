package admin.controller;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
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

public class AdminMgMtController extends WindowAdapter implements MouseListener, Runnable {

	private AdminMgMtView ammv;
	private AdminDAO a_dao;
	private Thread threadRefresh; 
	
	private static final int DBL_CLICK = 2;
	
	public AdminMgMtController(AdminMgMtView ammv) {
		a_dao = AdminDAO.getInstance();
		this.ammv = ammv;
		setUser();
		
		threadRefresh = new Thread(this);
		threadRefresh.start();
	}
	
	private void msgCenter(String msg) {
		JOptionPane.showMessageDialog(ammv, msg);
	}
	
	/**
	 * DB에서 조회한 모든 유저 정보를 테이블에 등록하는 메소드
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
	 * DB에서 조회한 리스트에 담긴 유저 정보를 테이블에 등록하는 메소드
	 * @param list
	 */
	public void setUser(List<UserListVO> list) {
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
	}
	
	/**
	 * DB에서 조회한 모든 일반 사용자 기본 정보를 테이블에 등록하는 메소드
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
	 * DB에서 조회한 리스트에 담긴 일반 사용자 기본 정보를 테이블에 등록하는 메소드
	 */
	public void setEe(List<EeListVO> list) {
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
	}
	
	/**
	 * DB에서 조회한 모든 기업 사용자 구인 정보를 테이블에 등록하는 메소드
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
	 * DB에서 조회한 리시트에 담긴 기업 사용자 구인 정보를 테이블에 등록하는 메소드
	 */
	public void setEr(List<ErListVO> list) { 
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
	}
	
	/**
	 * DB에서 조회한 모든 기업 정보를 테이블에 등록하는 메소드
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
	 * DB에서 조회한 리스트에 담긴 기업 정보를 테이블에 등록하는 메소드
	 */
	public void setCo(List<CoListVO> list) {
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
	}
	
	/**
	 * 유저 정보 상세 페이지를 띄우는 메소드
	 * @param id
	 * @throws SQLException
	 */
	public void showUserModify(String id) throws SQLException {
		UserInfoVO uivo = a_dao.selectOneUser(id);
		
		if(uivo == null) {
			msgCenter("DB에 해당 ID에 대한 정보가 없습니다.");
			return;
		}
		
		new UserModifyView(ammv, uivo, this);
	}
	
	/**
	 * 일반 사용자 기본 정보 상세 페이지를 띄우는 메소드
	 * @param eeNum
	 * @throws SQLException
	 */
	public void showEeModify(String eeNum) throws SQLException {
		EeInfoVO eivo = a_dao.selectOneEe(eeNum, "eeNum");
		
		if(eivo == null) {
			msgCenter("DB에 해당 기본정보번호에 대한 정보가 없습니다.");
			return;
		}
		
		new EeModifyView(ammv, eivo, this);
	}
	
	/**
	 * 기업 사용자 구인 정보 상세 페이지를 띄우는 메소드
	 * @param erNum
	 * @throws SQLException
	 */
	public void showErModify(String erNum) throws SQLException {
		ErInfoVO eivo = a_dao.selectOneEr(erNum);
	
		if(eivo == null) {
			msgCenter("DB에 해당 구인정보번호에 대한 정보가 없습니다.");
			return;
		}
		
		new ErModifyView(ammv, eivo, this);
	}
	
	/**
	 * 기업 사용자 기업 정보 상세 페이지를 띄우는 메소드
	 * @param coNum
	 * @throws SQLException
	 */
	public void showCoModify(String coNum) throws SQLException {
		CoInfoVO civo = a_dao.selectOneCo(coNum);
	
		if(civo == null) {
			msgCenter("DB에 해당 회사번호에 대한 정보가 없습니다.");
			return;
		}
		
		new CoModifyView(ammv, civo, this);
	}
	
	@Override
	public void mouseClicked(MouseEvent me) {
		if (ammv.getJtb().getSelectedIndex() == 0 && me.getSource() != ammv.getJtUser()) { 
			setUser();
			System.out.println("???");////////////////////// 한번 클릭하면 초기화시켜버림..
		}
		if (ammv.getJtb().getSelectedIndex() == 1 && me.getSource() != ammv.getJtEr()) {
			setEr();
		}
		
		if (ammv.getJtb().getSelectedIndex() == 2 && me.getSource() != ammv.getJtEe()){
			setEe();
		}
		
		if (ammv.getJtb().getSelectedIndex() == 3 && me.getSource() != ammv.getJtCo()) {
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
	public void mousePressed(MouseEvent me) {
////////팝업 조건검색 기능 추가 0307 ////////////
		if (me.getSource() == ammv.getJtUser() && me.getButton() == MouseEvent.BUTTON3) {
			JPopupMenu jp = ammv.getJpUser();
			jp.show(ammv.getJtb(), me.getXOnScreen()-103, me.getYOnScreen()-125);
			jp.setVisible(true);
		} else {
			JPopupMenu jp = ammv.getJpUser();
			jp.setVisible(false);
		}
		if (me.getSource() == ammv.getJtEr() && me.getButton() == MouseEvent.BUTTON3) {
			JPopupMenu jp = ammv.getJpEr();
			jp.show(ammv.getJtb(), me.getXOnScreen()-103, me.getYOnScreen()-125);
			jp.setVisible(true);
		} else {
			JPopupMenu jp = ammv.getJpEr();
			jp.setVisible(false);
		}
		if (me.getSource() == ammv.getJtEe() && me.getButton() == MouseEvent.BUTTON3) {
			JPopupMenu jp = ammv.getJpEe();
			jp.show(ammv.getJtb(), me.getXOnScreen()-103, me.getYOnScreen()-125);
			jp.setVisible(true);
		} else {
			JPopupMenu jp = ammv.getJpEe();
			jp.setVisible(false);
		}
		if (me.getSource() == ammv.getJtCo() && me.getButton() == MouseEvent.BUTTON3) {
			JPopupMenu jp = ammv.getJpCo();
			jp.show(ammv.getJtb(), me.getXOnScreen()-103, me.getYOnScreen()-125);
			jp.setVisible(true);
		} else {
			JPopupMenu jp = ammv.getJpCo();
			jp.setVisible(false);
		}
		
		// 유저 - id, 이름 검색
		// 구직자 - 구인정보번호, 회사명, id 검색
		// 구인자 - 기본정보번호, 이름, id 검색
		//회사 - 회사번호, 회사명, id 검색
		if (me.getSource() == ammv.getJmUserId()) { // 유저 id 검색, 한개의 결과
			String id = JOptionPane.showInputDialog(ammv, "검색할 ID를 입력해주세요.");
			
			if (id != null) {
				try {
					List<UserListVO> list = a_dao.selectUserWithNameCondition(id, "id"); 
					
					if(list.size() != 0) {
						setUser(list);
					} else {
						msgCenter("조회된 결과가 없습니다.");
						return;
					}
					
				} catch (SQLException e) {
					e.printStackTrace();
				} 
			}
		}
		
		if (me.getSource() == ammv.getJmUserName()) { // 유저 이름 검색, 여러개의 결과
			String name = JOptionPane.showInputDialog(ammv, "검색할 이름을 입력해주세요.");
			
			if (name != null) {
				try {
					List<UserListVO> list = a_dao.selectUserWithNameCondition(name, "name"); 
					
					if(list.size() != 0) {
						setUser(list);
					} else {
						msgCenter("조회된 결과가 없습니다.");
						return;
					}
					
				} catch (SQLException e) {
					e.printStackTrace();
				} 
			}
			
		}
		
		if (me.getSource() == ammv.getJmEeId()) { // 기본정보 아이디 검색, 한개의 결과
			String id = JOptionPane.showInputDialog(ammv, "검색할 ID를 입력해주세요.");
			
			if (id != null) {
				try {
					List<EeListVO> list = a_dao.selectEeWithNameCondition(id, "id"); 
					
					if(list.size() != 0) {
						setEe(list);
					} else {
						msgCenter("조회된 결과가 없습니다.");
						return;
					}
					
				} catch (SQLException e) {
					e.printStackTrace();
				} 
			}
		}
		
		if (me.getSource() == ammv.getJmEeName()) { // 기본정보 이름 검색, 여러개의 결과
			String name = JOptionPane.showInputDialog(ammv, "검색할 이름을 입력해주세요.");
			
			if (name != null) {
				try {
					List<EeListVO> list = a_dao.selectEeWithNameCondition(name, "name"); 
					
					if(list.size() != 0) {
						setEe(list);
					} else {
						msgCenter("조회된 결과가 없습니다.");
						return;
					}
					
				} catch (SQLException e) {
					e.printStackTrace();
				} 
			}
		}
		
		if (me.getSource() == ammv.getJmEeNum()) { // 기본정보번호 검색, 한개의 결과
			String eeNum = JOptionPane.showInputDialog(ammv, "검색할 기본정보번호를 입력해주세요.");
			
			if (eeNum != null) {
				try {
					showEeModify(eeNum);
				} catch (SQLException e) {
					msgCenter("DB 문제발생");
					e.printStackTrace();
				}
			}
		}
		
		if (me.getSource() == ammv.getJmErCo()) { // 구인정보 회사명 검색, 여러개의 결과
			String coName = JOptionPane.showInputDialog(ammv, "검색할 회사명을 입력해주세요.");
			
			if (coName != null) {
				try {
					List<ErListVO> list = a_dao.selectErWithCondition(coName, "coName");
					
					if(list.size() != 0) {
						setEr(list);
					} else {
						msgCenter("조회된 결과가 없습니다.");
						return;
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		
		if (me.getSource() == ammv.getJmErId()) { // 구인정보 아이디 검색, 여러개의 결과
			String id = JOptionPane.showInputDialog(ammv, "검색할 ID를 입력해주세요.");
			
			if (id != null) {
				try {
					List<ErListVO> list = a_dao.selectErWithCondition(id, "id");
					
					if(list.size() != 0) {
						setEr(list);
					} else {
						msgCenter("조회된 결과가 없습니다.");
						return;
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		
		if (me.getSource() == ammv.getJmErNum()) { // 구인정보번호 검색, 한개의 결과
			String erNum = JOptionPane.showInputDialog(ammv, "검색할 구인정보번호를 입력해주세요.");
			
			if (erNum != null) {
				try {
					showErModify(erNum);
				} catch (SQLException e) {
					msgCenter("DB 문제발생");
					e.printStackTrace();
				}
			}
		}
		
		if (me.getSource() == ammv.getJmCoId()) { // 회사정보 아이디 검색, 한개의 결과
			String id = JOptionPane.showInputDialog(ammv, "검색할 ID를 입력해주세요.");
			
			if (id != null) {
				try {
					List<CoListVO> list = a_dao.selectCoWithCondition(id, "id");
					
					if(list.size() != 0) {
						setCo(list);
					} else {
						msgCenter("조회된 결과가 없습니다.");
						return;
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		
		if (me.getSource() == ammv.getJmCoName()) { // 회사정보 회사명 검색, 여러개 결과
			String coName = JOptionPane.showInputDialog(ammv, "검색할 회사명을 입력해주세요.");
			
			if (coName != null) {
				try {
					List<CoListVO> list = a_dao.selectCoWithCondition(coName, "coName");
					
					if(list.size() != 0) {
						setCo(list);
					} else {
						msgCenter("조회된 결과가 없습니다.");
						return;
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		
		if (me.getSource() == ammv.getJmCoNum()) { // 회사정보 회사번호 검색, 한개의 결과
			String coNum = JOptionPane.showInputDialog(ammv, "검색할 회사번호를 입력해주세요.");
			
			if (coNum != null) {
				try {
					showCoModify(coNum);
				} catch (SQLException e) {
					msgCenter("DB 문제발생");
					e.printStackTrace();
				}
			}
		}
		
		if (me.getSource() == ammv.getJmUserReset()) {
			setUser();
		}
		
		if (me.getSource() == ammv.getJmErReset()) {
			setEr();
		}
		
		if (me.getSource() == ammv.getJmEeReset()) {
			setEe();
		}
		
		if (me.getSource() == ammv.getJmCoReset()) {
			setCo();
		}
	}
	@Override
	public void mouseReleased(MouseEvent me) {}
	@Override
	public void mouseEntered(MouseEvent e) {}
	@Override
	public void mouseExited(MouseEvent e) {}

	@Override
	public void run() {
		while(true) { // 5분마다 AdminMgMtView의 리스트를 갱신 
			try {
				Thread.sleep(1000*60*5);
				setUser();
				setEe();
				setEr();
				setCo();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
