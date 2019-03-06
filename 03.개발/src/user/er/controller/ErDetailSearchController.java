package user.er.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import user.er.dto.ErHiringCdtDTO;
import user.er.view.ErDetailSearchView;

public class ErDetailSearchController extends WindowAdapter implements ActionListener {

	private ErDetailSearchView edsv;
	private ErHiringController ehc;
	private String rank;
	private String education;
	private String loc;
	private String age;
	private ErHiringCdtDTO erhcdto;
	
	public ErDetailSearchController(ErDetailSearchView edsv, ErHiringController ehc) {
		this.edsv = edsv;
		this.ehc = ehc;
		erhcdto=ErHiringCdtDTO.getInstance();
	}
	
	public void search() {
StringBuilder selectDetail = new StringBuilder();
		
		if(!(edsv.getJcbRank().getSelectedIndex()==0)) {
			selectDetail.append(" and ( ")
			.append(" ei.rank = '");
			selectDetail.append(rank);
			selectDetail.append("') ");
		}
		
		if(!(edsv.getJcbEducation().getSelectedIndex()==0)) {
			selectDetail.append(" and ( ");
			selectDetail.append(" ei.education = '");
			selectDetail.append(education);
			selectDetail.append("') ");
		}
		
		if(!(edsv.getJcbLoc().getSelectedIndex()==0)) {
			selectDetail.append(" and ( ");
			selectDetail.append(" ei.loc = '");
			selectDetail.append(loc);
			selectDetail.append("') ");
		}
		if(!(edsv.getJcbAge().getSelectedIndex()==0)) {
			selectDetail.append(" and ( ");
			selectDetail.append(" (FLOOR(ut.age/10))*10 = '");
			selectDetail.append(age);
			selectDetail.append("') ");
		}
		
		erhcdto.setCdt(selectDetail.toString());
		ehc.setDtm();
	}
	
	@Override
	public void actionPerformed(ActionEvent ae) {
		if(ae.getSource()==edsv.getJbSearch()) {
			search();
			edsv.dispose();
		}
		if(ae.getSource()==edsv.getJbCancel()) {
			edsv.dispose();
		}
		
		if(ae.getSource()==edsv.getJcbRank()) {
			//'N' - 신입 'C' - 경력
			switch (edsv.getJcbRank().getSelectedIndex()) {
			case 1:
				rank="N";
				break;
			case 2:
				rank="C";
				break;
			}
		}
		if(ae.getSource()==edsv.getJcbEducation()) {
			education=String.valueOf(edsv.getJcbEducation().getSelectedItem());
		}
		if(ae.getSource()==edsv.getJcbLoc()) {
			loc=String.valueOf(edsv.getJcbLoc().getSelectedItem());
		}
		if(ae.getSource()==edsv.getJcbAge()) {
			age=String.valueOf(edsv.getJcbAge().getSelectedItem()).replace("대","");
		}
	}

	@Override
	public void windowClosing(WindowEvent e) {
		edsv.dispose();
	}
}
