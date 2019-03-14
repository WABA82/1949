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
	 * DB���� ��ȸ�� ��� ���� ������ ���̺� ����ϴ� �޼ҵ�
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
				rowData[6] = ulvo.getUserType().equals("E") ? "�Ϲ�" : "���";
				rowData[7] = ulvo.getInputDate();
				dtm.addRow(rowData);
			}
			
		} catch (SQLException e) {
			msgCenter("DB�� ������ �߻��߽��ϴ�.");
			e.printStackTrace();
		}
	}
	
	/**
	 * DB���� ��ȸ�� ����Ʈ�� ��� ���� ������ ���̺� ����ϴ� �޼ҵ�
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
			rowData[6] = ulvo.getUserType().equals("E") ? "�Ϲ�" : "���";
			rowData[7] = ulvo.getInputDate();
			dtm.addRow(rowData);
		}
	}
	
	/**
	 * DB���� ��ȸ�� ��� �Ϲ� ����� �⺻ ������ ���̺� ����ϴ� �޼ҵ�
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
				rowData[2] = new ImageIcon("C:/dev/1949/03.����/src/admin/img/ee/"+elvo.getImg());
				rowData[3] = elvo.getEeId();
				rowData[4] = elvo.getName();
				switch(elvo.getRank()) {
				case "N" : 
					rowData[5] = "����";
					break;
				case "C" :
					rowData[5] = "���";
					break;
				}
				rowData[6] = elvo.getLoc();
				rowData[7] = elvo.getEducation();
				rowData[8] = String.valueOf(elvo.getAge())+"��";
				rowData[9] = elvo.getPortfolio().equals("Y") ? "����" : "����";
				rowData[10] = elvo.getGender().equals("M") ? "����" : "����";
				rowData[11] = elvo.getExtRsm() == null ? "����" : elvo.getExtRsm();
				rowData[12] = elvo.getInputDate();
				dtm.addRow(rowData);
			}
			
		} catch (SQLException e) {
			msgCenter("DB�� ������ �߻��߽��ϴ�.");
			e.printStackTrace();
		}
	}
	
	/**
	 * DB���� ��ȸ�� ����Ʈ�� ��� �Ϲ� ����� �⺻ ������ ���̺� ����ϴ� �޼ҵ�
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
			rowData[2] = new ImageIcon("C:/dev/1949/03.����/src/admin/img/ee/"+elvo.getImg());
			rowData[3] = elvo.getEeId();
			rowData[4] = elvo.getName();
			switch(elvo.getRank()) {
			case "N" : 
				rowData[5] = "����";
				break;
			case "C" :
				rowData[5] = "���";
				break;
			}
			rowData[6] = elvo.getLoc();
			rowData[7] = elvo.getEducation();
			rowData[8] = String.valueOf(elvo.getAge())+"��";
			rowData[9] = elvo.getPortfolio().equals("Y") ? "����" : "����";
			rowData[10] = elvo.getGender().equals("M") ? "����" : "����";
			rowData[11] = elvo.getExtRsm() == null ? "����" : elvo.getExtRsm();
			rowData[12] = elvo.getInputDate();
			dtm.addRow(rowData);
		}
	}
	
	/**
	 * DB���� ��ȸ�� ��� ��� ����� ���� ������ ���̺� ����ϴ� �޼ҵ�
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
					rowData[7] = "����";
					break;
				case "C" :
					rowData[7] = "���";
					break;
				}
				rowData[8] = elvo.getLoc();
				rowData[9] = elvo.getEducation();
				switch(elvo.getHireType()) {
				case "N" : 
					rowData[10] = "�����";
					break;
				case "C" :
					rowData[10] = "������";
					break;
				case "F" :
					rowData[10] = "����";
					break;
				}
				rowData[11] = String.valueOf(elvo.getSal());
				rowData[12] = elvo.getInputDate();
				dtm.addRow(rowData);
			}
			
		} catch (SQLException e) {
			msgCenter("DB�� ������ �߻��߽��ϴ�.");
			e.printStackTrace();
		}
	}
	
	/**
	 * DB���� ��ȸ�� ����Ʈ�� ��� ��� ����� ���� ������ ���̺� ����ϴ� �޼ҵ�
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
				rowData[7] = "����";
				break;
			case "C" :
				rowData[7] = "���";
				break;
			}
			rowData[8] = elvo.getLoc();
			rowData[9] = elvo.getEducation();
			switch(elvo.getHireType()) {
			case "N" : 
				rowData[10] = "�����";
				break;
			case "C" :
				rowData[10] = "������";
				break;
			case "F" :
				rowData[10] = "����";
				break;
			}
			rowData[11] = String.valueOf(elvo.getSal());
			rowData[12] = elvo.getInputDate();
			dtm.addRow(rowData);
		}
	}
	
	/**
	 * DB���� ��ȸ�� ��� ��� ������ ���̺� ����ϴ� �޼ҵ�
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
				rowData[2] = new ImageIcon("C:/dev/1949/03.����/src/admin/img/co/"+clvo.getImg1());
				rowData[3] = clvo.getCoName();
				rowData[4] = clvo.getErId();
				rowData[5] = clvo.getEstDate();
				rowData[6] = String.valueOf(clvo.getMemberNum())+"��";
				rowData[7] = clvo.getInputDate();
				dtm.addRow(rowData);
			}
			
		} catch (SQLException e) {
			msgCenter("DB�� ������ �߻��߽��ϴ�.");
			e.printStackTrace();
		}
	}
	
	/**
	 * DB���� ��ȸ�� ����Ʈ�� ��� ��� ������ ���̺� ����ϴ� �޼ҵ�
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
			rowData[2] = new ImageIcon("C:/dev/1949/03.����/src/admin/img/co/"+clvo.getImg1());
			rowData[3] = clvo.getCoName();
			rowData[4] = clvo.getErId();
			rowData[5] = clvo.getEstDate();
			rowData[6] = String.valueOf(clvo.getMemberNum())+"��";
			rowData[7] = clvo.getInputDate();
			dtm.addRow(rowData);
		}
	}
	
	/**
	 * ���� ���� �� �������� ���� �޼ҵ�
	 * @param id
	 * @throws SQLException
	 */
	public void showUserModify(String id) throws SQLException {
		UserInfoVO uivo = a_dao.selectOneUser(id);
		
		if(uivo == null) {
			msgCenter("DB�� �ش� ID�� ���� ������ �����ϴ�.");
			return;
		}
		
		new UserModifyView(ammv, uivo, this);
	}
	
	/**
	 * �Ϲ� ����� �⺻ ���� �� �������� ���� �޼ҵ�
	 * @param eeNum
	 * @throws SQLException
	 */
	public void showEeModify(String eeNum) throws SQLException {
		EeInfoVO eivo = a_dao.selectOneEe(eeNum, "eeNum");
		
		if(eivo == null) {
			msgCenter("DB�� �ش� �⺻������ȣ�� ���� ������ �����ϴ�.");
			return;
		}
		
		new EeModifyView(ammv, eivo, this);
	}
	
	/**
	 * ��� ����� ���� ���� �� �������� ���� �޼ҵ�
	 * @param erNum
	 * @throws SQLException
	 */
	public void showErModify(String erNum) throws SQLException {
		ErInfoVO eivo = a_dao.selectOneEr(erNum);
	
		if(eivo == null) {
			msgCenter("DB�� �ش� ����������ȣ�� ���� ������ �����ϴ�.");
			return;
		}
		
		new ErModifyView(ammv, eivo, this);
	}
	
	/**
	 * ��� ����� ��� ���� �� �������� ���� �޼ҵ�
	 * @param coNum
	 * @throws SQLException
	 */
	public void showCoModify(String coNum) throws SQLException {
		CoInfoVO civo = a_dao.selectOneCo(coNum);
	
		if(civo == null) {
			msgCenter("DB�� �ش� ȸ���ȣ�� ���� ������ �����ϴ�.");
			return;
		}
		
		new CoModifyView(ammv, civo, this);
	}
	
	@Override
	public void mouseClicked(MouseEvent me) {
		if (ammv.getJtb().getSelectedIndex() == 0 && me.getSource() != ammv.getJtUser()) { 
			setUser();
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
					msgCenter("DB ���� �����߽��ϴ�.");
					e.printStackTrace();
				}
			}
			
			if (me.getSource() == ammv.getJtCo()) {
				
				jt = ammv.getJtCo();
				
				String coNum = (String)jt.getValueAt(jt.rowAtPoint(me.getPoint()), 1);
				
				try {
					showCoModify(coNum);
				} catch (SQLException e) {
					msgCenter("DB ���� �����߽��ϴ�.");
					e.printStackTrace();
				}
			}
			
			if (me.getSource() == ammv.getJtEr()) {
				
				jt = ammv.getJtEr();
				
				String erNum = (String)jt.getValueAt(jt.rowAtPoint(me.getPoint()), 1);
				
				try {
					showErModify(erNum);
				} catch (SQLException e) {
					msgCenter("DB ���� �����߽��ϴ�.");
					e.printStackTrace();
				}
			}
			
			if (me.getSource() == ammv.getJtEe()) {
				
				jt = ammv.getJtEe();
				
				String eeNum = (String)jt.getValueAt(jt.rowAtPoint(me.getPoint()), 1);
				
				try {
					showEeModify(eeNum);
				} catch (SQLException e) {
					msgCenter("DB ���� �����߽��ϴ�.");
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
		////////�˾� ���ǰ˻� ��� �߰� 0307 ////////////
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
		
		// ���� - id, �̸� �˻�
		// ������ - ����������ȣ, ȸ���, id �˻�
		// ������ - �⺻������ȣ, �̸�, id �˻�
		//ȸ�� - ȸ���ȣ, ȸ���, id �˻�
		if (me.getSource() == ammv.getJmUserId()) { // ���� id �˻�, �Ѱ��� ���
			String id = JOptionPane.showInputDialog(ammv, "�˻��� ID�� �Է����ּ���.");
			
			if (id != null) {
				try {
					List<UserListVO> list = a_dao.selectUserWithNameCondition(id, "id"); 
					
					if(list.size() != 0) {
						setUser(list);
					} else {
						msgCenter("��ȸ�� ����� �����ϴ�.");
						return;
					}
					
				} catch (SQLException e) {
					e.printStackTrace();
				} 
			}
		}
		
		if (me.getSource() == ammv.getJmUserName()) { // ���� �̸� �˻�, �������� ���
			String name = JOptionPane.showInputDialog(ammv, "�˻��� �̸��� �Է����ּ���.");
			
			if (name != null) {
				try {
					List<UserListVO> list = a_dao.selectUserWithNameCondition(name, "name"); 
					
					if(list.size() != 0) {
						setUser(list);
					} else {
						msgCenter("��ȸ�� ����� �����ϴ�.");
						return;
					}
					
				} catch (SQLException e) {
					e.printStackTrace();
				} 
			}
			
		}
		
		if (me.getSource() == ammv.getJmEeId()) { // �⺻���� ���̵� �˻�, �Ѱ��� ���
			String id = JOptionPane.showInputDialog(ammv, "�˻��� ID�� �Է����ּ���.");
			
			if (id != null) {
				try {
					List<EeListVO> list = a_dao.selectEeWithNameCondition(id, "id"); 
					
					if(list.size() != 0) {
						setEe(list);
					} else {
						msgCenter("��ȸ�� ����� �����ϴ�.");
						return;
					}
					
				} catch (SQLException e) {
					e.printStackTrace();
				} 
			}
		}
		
		if (me.getSource() == ammv.getJmEeName()) { // �⺻���� �̸� �˻�, �������� ���
			String name = JOptionPane.showInputDialog(ammv, "�˻��� �̸��� �Է����ּ���.");
			
			if (name != null) {
				try {
					List<EeListVO> list = a_dao.selectEeWithNameCondition(name, "name"); 
					
					if(list.size() != 0) {
						setEe(list);
					} else {
						msgCenter("��ȸ�� ����� �����ϴ�.");
						return;
					}
					
				} catch (SQLException e) {
					e.printStackTrace();
				} 
			}
		}
		
		if (me.getSource() == ammv.getJmEeNum()) { // �⺻������ȣ �˻�, �Ѱ��� ���
			String eeNum = JOptionPane.showInputDialog(ammv, "�˻��� �⺻������ȣ�� �Է����ּ���.");
			
			if (eeNum != null) {
				try {
					showEeModify(eeNum);
				} catch (SQLException e) {
					msgCenter("DB �����߻�");
					e.printStackTrace();
				}
			}
		}
		
		if (me.getSource() == ammv.getJmErCo()) { // �������� ȸ��� �˻�, �������� ���
			String coName = JOptionPane.showInputDialog(ammv, "�˻��� ȸ����� �Է����ּ���.");
			
			if (coName != null) {
				try {
					List<ErListVO> list = a_dao.selectErWithCondition(coName, "coName");
					
					if(list.size() != 0) {
						setEr(list);
					} else {
						msgCenter("��ȸ�� ����� �����ϴ�.");
						return;
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		
		if (me.getSource() == ammv.getJmErId()) { // �������� ���̵� �˻�, �������� ���
			String id = JOptionPane.showInputDialog(ammv, "�˻��� ID�� �Է����ּ���.");
			
			if (id != null) {
				try {
					List<ErListVO> list = a_dao.selectErWithCondition(id, "id");
					
					if(list.size() != 0) {
						setEr(list);
					} else {
						msgCenter("��ȸ�� ����� �����ϴ�.");
						return;
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		
		if (me.getSource() == ammv.getJmErNum()) { // ����������ȣ �˻�, �Ѱ��� ���
			String erNum = JOptionPane.showInputDialog(ammv, "�˻��� ����������ȣ�� �Է����ּ���.");
			
			if (erNum != null) {
				try {
					showErModify(erNum);
				} catch (SQLException e) {
					msgCenter("DB �����߻�");
					e.printStackTrace();
				}
			}
		}
		
		if (me.getSource() == ammv.getJmCoId()) { // ȸ������ ���̵� �˻�, �Ѱ��� ���
			String id = JOptionPane.showInputDialog(ammv, "�˻��� ID�� �Է����ּ���.");
			
			if (id != null) {
				try {
					List<CoListVO> list = a_dao.selectCoWithCondition(id, "id");
					
					if(list.size() != 0) {
						setCo(list);
					} else {
						msgCenter("��ȸ�� ����� �����ϴ�.");
						return;
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		
		if (me.getSource() == ammv.getJmCoName()) { // ȸ������ ȸ��� �˻�, ������ ���
			String coName = JOptionPane.showInputDialog(ammv, "�˻��� ȸ����� �Է����ּ���.");
			
			if (coName != null) {
				try {
					List<CoListVO> list = a_dao.selectCoWithCondition(coName, "coName");
					
					if(list.size() != 0) {
						setCo(list);
					} else {
						msgCenter("��ȸ�� ����� �����ϴ�.");
						return;
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		
		if (me.getSource() == ammv.getJmCoNum()) { // ȸ������ ȸ���ȣ �˻�, �Ѱ��� ���
			String coNum = JOptionPane.showInputDialog(ammv, "�˻��� ȸ���ȣ�� �Է����ּ���.");
			
			if (coNum != null) {
				try {
					showCoModify(coNum);
				} catch (SQLException e) {
					msgCenter("DB �����߻�");
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
		while(true) { // 15�ʸ��� AdminMgMtView�� ����Ʈ�� ���� 
			try {
				Thread.sleep(1000*15);
				System.out.println("���̺� ����");
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
