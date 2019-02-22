package user.er.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import user.dao.ErDAO;
import user.ee.view.EeDetailErView;
import user.ee.vo.DetailErInfoVO;
import user.ee.vo.EeHiringVO;
import user.er.view.ErAddView;
import user.er.view.ErMgMtView;
import user.er.view.ErModifyView;
import user.er.vo.ErDefaultVO;
import user.er.vo.ErDetailVO;
import user.er.vo.ErListVO;

public class ErMgMtController extends WindowAdapter implements MouseListener, ActionListener {
	private ErMgMtView emmv;
	private String erId;
	private ErDAO er_dao;
	private ErDefaultVO edfvo;
	
	public ErMgMtController(ErMgMtView emmv,String erId) {
		this.emmv = emmv;
		this.erId = erId;
		er_dao= ErDAO.getInstance();
		setDtm();
	}
	
	public void setDtm() {
		List<ErListVO> list = new ArrayList<ErListVO>();
		DefaultTableModel dtmErList = emmv.getDtmEr();
		dtmErList.setRowCount(0);
		
		try {
			list = er_dao.selectErList(erId);
			ErListVO ervo= null;
			
			Object[] rowData = null;
			for(int i=0; i<list.size();i++) {
				ervo= list.get(i);
				rowData = new Object[8];
				rowData[0] = new Integer(i+1);
				rowData[1] = ervo.getErNum();
				rowData[2] = ervo.getSubject();
				if(ervo.getRank().equals("N")) {
					rowData[3]= "신입";
				}else if(ervo.getRank().equals("C")) {
					rowData[3]="경력";
				}
				rowData[4] = ervo.getLoc();
				rowData[5] = ervo.getEducation();
				if(ervo.getHireType().equals("C")) {
					rowData[6] = "정규직";
				}else if(ervo.getHireType().equals("N")) {
					rowData[6] = "비정규직";
				}else if(ervo.getHireType().equals("F")) {
					rowData[6] = "프리";
				}
				rowData[7] = ervo.getInputDate();
				dtmErList.addRow(rowData);
			}
/*			if(list.isEmpty()) {
				JOptionPane.showMessageDialog(emmv, "등록된 구인글이 없습니다.");
			}*/
			
		}catch(SQLException e){
			JOptionPane.showMessageDialog(emmv, "DB에러");
			e.printStackTrace();
		}
	}
	public void showDetail() {
		JTable jt = emmv.getJtEr();
		String erNum= String.valueOf(jt.getValueAt(jt.getSelectedRow(), 1));
		ErDetailVO edvo = null;
		try {
			edvo = er_dao.selectErDetail(erNum);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		new ErModifyView(emmv, edvo, erNum,erId,this);
		
	}
	
	
	public void addEr() {
		try {
			edfvo = er_dao.selectErDefault(erId);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		new ErAddView(emmv, this, edfvo, erId);
	}
	
	@Override
	public void actionPerformed(ActionEvent ae) {
		if(ae.getSource()==emmv.getJbRegEr()) {
			addEr();
		}
	}

	@Override
	public void mouseClicked(MouseEvent me) {
		switch(me.getClickCount()) {
		case 2:
			if(me.getSource()==emmv.getJtEr()){
				showDetail();
			}
		}
	}
	
	@Override
	public void windowClosing(WindowEvent e) {
		emmv.dispose();
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
