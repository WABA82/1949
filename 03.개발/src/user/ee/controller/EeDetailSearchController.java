package user.ee.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

import javax.swing.JOptionPane;

import user.ee.dto.EeHiringCdtDTO;
import user.ee.view.EeDetailSearchView;
import user.ee.vo.EeHiringVO;

public class EeDetailSearchController extends WindowAdapter implements ActionListener {
	
	private EeDetailSearchView esv;
	private EeHiringController ehc;
	private String rank;
	private String education;
	private String loc;
	private String hireType;
	private EeHiringCdtDTO ehc_dto;
	private List<EeHiringVO> list;
	
	
	public EeDetailSearchController(EeDetailSearchView esv, EeHiringController ehc) {
		this.esv = esv;
		this.ehc = ehc;
		this.list = list;
		ehc_dto = EeHiringCdtDTO.getInstance();
	}
	
	public void search() {
		StringBuilder selectDetail = new StringBuilder();
		
		if(!(esv.getJcbRank().getSelectedIndex()==0)) {
			selectDetail.append(" and ( ")
			.append(" ei.rank = '");
			selectDetail.append(rank);
			selectDetail.append("') ");
		}
		
		if(!(esv.getJcbEducation().getSelectedIndex()==0)) {
			selectDetail.append(" and ( ");
			selectDetail.append(" ei.education = '");
			selectDetail.append(education);
			selectDetail.append("') ");
		}
		
		if(!(esv.getJcbLoc().getSelectedIndex()==0)) {
			selectDetail.append(" and ( ");
			selectDetail.append(" ei.loc = '");
			selectDetail.append(loc);
			selectDetail.append("') ");
		}
		
		if(!(esv.getJcbHireType().getSelectedIndex()==0)) {
			selectDetail.append(" and ( ");
			selectDetail.append(" ei.hire_type = '");
			selectDetail.append(hireType);
			selectDetail.append("') ");
		}
		
		
		ehc_dto.setCdt(selectDetail.toString());
		ehc.setDtm();

	}
	
	@Override
	public void windowClosing(WindowEvent e) {
		esv.dispose();
	}
	
	@Override
	public void actionPerformed(ActionEvent ae) {

		if(ae.getSource()==esv.getJbSearch()) {
			search();
			esv.dispose();
		}
		if(ae.getSource()==esv.getJbCancel()) {
			esv.dispose();
		}
		
		if(ae.getSource()==esv.getJcbRank()) {
			//'N' - ���� 'C' - ���
			switch (esv.getJcbRank().getSelectedIndex()) {
			case 1:
				rank="N";
				break;
			case 2:
				rank="C";
				break;
			}
		}
		if(ae.getSource()==esv.getJcbEducation()) {
			education=String.valueOf(esv.getJcbEducation().getSelectedItem());
		}
		if(ae.getSource()==esv.getJcbLoc()) {
			loc=String.valueOf(esv.getJcbLoc().getSelectedItem());
		}
		if(ae.getSource()==esv.getJcbHireType()) {
			//'C' - ������'N' - ��������'F' - ��������
			switch (esv.getJcbHireType().getSelectedIndex()) {
			case 1:
				hireType="C";
				break;
			case 2:
				hireType="N";
				break;
			case 3:
				hireType="F";
				break;
			}
		}
	}

}
