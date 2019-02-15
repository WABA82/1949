package user.er.controller;

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

import user.dao.ErDAO;
import user.er.dto.ErHiringCdtDTO;
import user.er.view.ErDetailEeView;
import user.er.view.ErDetailSearchView;
import user.er.view.ErHiringView;
import user.er.vo.DetailEeInfoVO;
import user.er.vo.ErHiringVO;

public class ErHiringController extends WindowAdapter implements ActionListener, MouseListener {
	private ErHiringView ehv;
	private List<ErHiringVO> list;
	private String erId;
	private ErDAO erdao;
	private ErHiringCdtDTO erhcdto;
	public ErHiringController(ErHiringView ehv,String erId) {
		this.ehv = ehv;
		this.erId = erId;
		erhcdto =ErHiringCdtDTO.getInstance();
		erdao= ErDAO.getInstance();
		erhcdto.setSort(" ");
		erhcdto.setCdt(" ");
		setDtm();
	}
	public void setDtm() {
		DefaultTableModel dtmHiring = ehv.getDtmEeInfo();
		dtmHiring.setRowCount(0);
		
		try {
			list = erdao.selectErHiring(erhcdto);
			ErHiringVO erhvo= null;
			Object[] rowData = null;
			for(int i=0; i<list.size();i++) {
				erhvo= list.get(i);
				rowData = new Object[11];
				rowData[0]= new Integer(i+1);
				rowData[1]= erhvo.getEeNum();
				rowData[2]= erhvo.getImg();
				rowData[3]= erhvo.getName();
				rowData[4]= erhvo.getRank();
				rowData[5]= erhvo.getLoc();
				rowData[6]= erhvo.getEducation();
				rowData[7]= erhvo.getAge();
				rowData[8]= erhvo.getPortfolio();
				rowData[9]= erhvo.getGender();
				rowData[10]= erhvo.getInputDate();
				
				dtmHiring.addRow(rowData);
			}
			if(list.isEmpty()) {
				JOptionPane.showMessageDialog(ehv, "조건에 맞는 결과가 없습니다.");
			}
			
		}catch(SQLException e){
			JOptionPane.showMessageDialog(ehv, "DB에러");
			e.printStackTrace();
		}
	}//setDtm
	
	public void showDetailErInfo() {
		JTable jt = ehv.getJtEeInfo();
		String eeNum= String.valueOf(jt.getValueAt(jt.getSelectedRow(), 1));
		DetailEeInfoVO devo = null;
		try {
			devo = erdao.selectDeatilEe(eeNum, erId);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println(erId);
		new ErDetailEeView(ehv,devo, eeNum,erId,devo.getInterest());
	}
	
	public void detailSearch() {
		erhcdto.setSort(String.valueOf(ehv.getJcbSort().getSelectedItem()));
		setDtm();
	}
	
	public void searchAll() {
		erhcdto.setSort(" ");
		erhcdto.setCdt(" ");
		setDtm();
	}
	
	@Override
	public void mouseClicked(MouseEvent me) {
		switch(me.getClickCount()) {
		case 2:
			if(me.getSource()==ehv.getJtEeInfo())
			{
				showDetailErInfo();
			}
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent ae) {
		if(ae.getSource()==ehv.getJbDetailSearch()) {
			new ErDetailSearchView(ehv, this); 
		}
		if(ae.getSource()==ehv.getJcbSort()) {
			//나열하는 콤보박스
			detailSearch();
		}
		if(ae.getSource()==ehv.getJbSelectAll()) {
			searchAll();
		}
		
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
