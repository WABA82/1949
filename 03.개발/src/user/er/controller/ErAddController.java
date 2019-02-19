package user.er.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import user.dao.ErDAO;
import user.er.view.ErAddView;
import user.er.view.ErModifyView;
import user.er.vo.ErAddVO;

public class ErAddController extends WindowAdapter implements ActionListener {
	private ErAddView eav;
	private ErAddVO eavo;
	private ErMgMtController emmc;
	private String erId;
	private ErDAO er_dao;
		
	public ErAddController(ErAddView eav, ErMgMtController emmc, String erId) {
		this.eav = eav;
		this.erId = erId;
		this.emmc = emmc;
		er_dao= ErDAO.getInstance();
	}
	public void register() {
		List<String> listSkill = new ArrayList<String>();
		//등록수정완료시 eavo에 값 넣어주고 dao 에뿌려주기
		//String erId, String subject, String education, String rank, String loc, String hireType,
		//String portfolio, String erDesc, int sal, List<String> listSkill
		
		String rank = String.valueOf(eav.getJcbRank().getSelectedItem());
		String hireType = String.valueOf(eav.getJcbHireType().getSelectedItem());
		String portfolio = String.valueOf(eav.getJcbPortfolio().getSelectedItem());
		boolean addFlag = false;
	
		if(eav.getJchCSS().isSelected()) {
			listSkill.add("s_06");
		}
		if(eav.getJchHTML().isSelected()) {
			listSkill.add("s_05");
		}
		if(eav.getJchJava().isSelected()) {
			listSkill.add("s_01");
		}
		if(eav.getJchJS().isSelected()) {
			listSkill.add("s_08");
		}
		if(eav.getJchJspServlet().isSelected()) {
			listSkill.add("s_02");
		}
		if(eav.getJchLinux().isSelected()) {
			listSkill.add("s_07");
		}
		if(eav.getJchOracle().isSelected()) {
			listSkill.add("s_04");
		}
		if(eav.getJchSpring().isSelected()) {
			listSkill.add("s_03");
		}
		
		if(rank.equals("신입")) {
			rank="N";
		}else if(rank.equals("경력")) {
			rank="C";
		}
		if(hireType.equals("정규직")) {
			hireType="C";
		}else if(hireType.equals("계약직")) {
			hireType="N";
		}else if(hireType.equals("프리")) {
			hireType="F";
		}
		
		if(portfolio.equals("YES")) {
			portfolio="Y";
		}else if(portfolio.equals("NO")) {
			portfolio="N";
		}
		System.out.println(listSkill);
		eavo = new ErAddVO(erId, eav.getJtfSubject().getText().trim(), String.valueOf(eav.getJcbEducation().getSelectedItem()), 
				rank, String.valueOf(eav.getJcbLoc().getSelectedItem()), 
				hireType, portfolio, 
				eav.getJtaErDesc().getText().trim(), Integer.parseInt(eav.getJtfSal().getText().trim()), listSkill);
		
		try {
			
			addFlag = er_dao.insertErAdd(eavo);
			System.out.println(addFlag);
			if(addFlag) {
				JOptionPane.showMessageDialog(eav, "등록이 완료되었습니다!");
				refreshList();
				eav.dispose();
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("DB문제");
		}
	}
	public void refreshList() {
		emmc.setDtm();
	}
	
	
	@Override
	public void actionPerformed(ActionEvent ae) {
		if(ae.getSource()==eav.getJbReg()) {
			register();
		}
		if(ae.getSource()==eav.getJbCancel()) {
			eav.dispose();
		}
		
	}
	
	@Override
	public void windowClosing(WindowEvent e) {
		eav.dispose();
	}
}
