package user.ee.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import user.ee.dto.EeHiringCdtDTO;
import user.ee.view.EeDetailErView;
import user.ee.view.EeDetailSearchView;
import user.ee.view.EeHiringView;
import user.ee.vo.DetailErInfoVO;
import user.ee.vo.EeHiringVO;
import user.ee.vo.EeInterestAndAppVO;

public class EeHiringController extends WindowAdapter implements ActionListener, MouseListener {
	private EeHiringView ehv;
	private List<EeHiringVO> list;
	private String eeId;
	public EeHiringCdtDTO ehc_dto;
	private EeDAO ee_dao;
	
	public EeHiringController(EeHiringView ehv,String eeId) {
		this.ehv = ehv;
		this.eeId =  eeId;
		ehc_dto = EeHiringCdtDTO.getInstance();
		ee_dao = EeDAO.getInstance();
		ehc_dto.setSort(" ");
		ehc_dto.setCdt(" ");
		ehc_dto.setCoName(" ");
		setDtm();
		
	}
	
	public void setDtm() {
		DefaultTableModel dtmHiring = ehv.getDtmErInfo();
		dtmHiring.setRowCount(0);
		
		try {
			list = ee_dao.selectEeHiring(ehc_dto);
			EeHiringVO e_vo = null;
			
			Object[] rowData = null;
			for(int i=0; i<list.size();i++) {
				e_vo= list.get(i);
				rowData = new Object[10];
				rowData[0]= new Integer(i+1);
				rowData[1]= e_vo.getErNum();
				rowData[2]= e_vo.getSubject();
				rowData[3]= e_vo.getCoName();
				if(e_vo.getRank().equals("N")) {
					rowData[4]= "신입";
				}else if(e_vo.getRank().equals("C")) {
					rowData[4]="경력";
				}
				rowData[5]= e_vo.getLoc();
				rowData[6]= e_vo.getEducation();
				if(e_vo.getHireType().equals("C")) {
					rowData[7]= "정규직";
				}else if(e_vo.getHireType().equals("N")) {
					rowData[7]= "비정규직";
				}else if(e_vo.getHireType().equals("F")) {
					rowData[7]= "프리랜서";
				}
				rowData[8]= e_vo.getSal();
				rowData[9]= e_vo.getInputDate();
				
				dtmHiring.addRow(rowData);
			}
			if(list.isEmpty()) {
				JOptionPane.showMessageDialog(ehv, "조건에 맞는 결과가 없습니다.");
			}
			
		}catch(SQLException e){
			JOptionPane.showMessageDialog(ehv, "DB에러");
			e.printStackTrace();
		}
	}
	
	public void searchCoName() {
		ehc_dto.setCoName(ehv.getJtfSearch().getText().trim()); 
		setDtm();
	}
	
	public void showDetailErInfo() {
		JTable jt = ehv.getJtErInfo();
		String erNum= String.valueOf(jt.getValueAt(jt.getSelectedRow(), 1));
		String appStatus="";
		DetailErInfoVO deivo=null;
		try {
			deivo = ee_dao.selectDetail(erNum,eeId);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			appStatus =ee_dao.selectApplication(eeId, erNum);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println(appStatus);
		new EeDetailErView(ehv, deivo, erNum, eeId , appStatus);
	}
	
	public void detailSearch() {
		ehc_dto.setSort(String.valueOf(ehv.getJcbSort().getSelectedItem()));
		setDtm();
	}
	
	public void searchAll() {
		ehc_dto.setSort(" ");
		ehc_dto.setCdt(" ");
		ehc_dto.setCoName(" ");
		setDtm();
	}
	
	@Override
	public void mouseClicked(MouseEvent me) {
		
		switch(me.getClickCount()) {
		case 2:
			if(me.getSource()==ehv.getJtErInfo())
			{
				showDetailErInfo();
			}
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent ae) {
		if(ae.getSource()==ehv.getJbDetailSearch()) {
			EeDetailSearchView edsv = new EeDetailSearchView(ehv, this);
		}
		
		if(ae.getSource()==ehv.getJbWordSearch()) {
			//검색을 누르면
			searchCoName();
		}
		if(ae.getSource()==ehv.getJcbSort()) {
			//나열하는 콤보박스
			detailSearch();
		}
		if(ae.getSource()==ehv.getJtfSearch()) {
			//텍스트필드 엔터를 누르면
			searchCoName();
		}
		if(ae.getSource()==ehv.getJbAllView()) {
			searchAll();
		}
	}
	
	@Override
	public void windowClosing(WindowEvent we) {
		ehv.dispose();
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
