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
import user.er.vo.ErAddVO;
import user.util.UserLog;

public class ErAddController extends WindowAdapter implements ActionListener {
	private ErAddView eav;
	private ErAddVO eavo;
	private ErMgMtController emmc;
	private String erId;
	private ErDAO er_dao;
	private UserLog ul;
		
	public ErAddController(ErAddView eav, ErMgMtController emmc, String erId) {
		this.eav = eav;
		this.erId = erId;
		this.emmc = emmc;
		er_dao= ErDAO.getInstance();
		ul= new UserLog();
	}
	public void register() throws NullPointerException {
		List<String> listSkill = new ArrayList<String>();
		
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
		
		eavo = new ErAddVO(erId, eav.getJtfSubject().getText().trim(), String.valueOf(eav.getJcbEducation().getSelectedItem()), 
				rank, String.valueOf(eav.getJcbLoc().getSelectedItem()), 
				hireType, portfolio, 
				eav.getJtaErDesc().getText().trim(), Integer.parseInt(eav.getJtfSal().getText().trim()), listSkill);
		
		try {
			
			addFlag = er_dao.insertErAdd(eavo);
			if(addFlag) {
				JOptionPane.showMessageDialog(eav, "구인 정보가 등록되었습니다.");
				ul.sendLog(erId, "구인 정보를 등록하였습니다.");
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
			if(eav.getJtfSubject().getText().trim()==null||eav.getJtfSubject().getText().trim().equals("")){
				JOptionPane.showMessageDialog(eav, "제목은 필수 입력입니다.");
				eav.getJtfSubject().requestFocus();
				return;
			}
			if(eav.getJtfSal().getText().trim()==null||eav.getJtfSal().getText().trim().equals("")){
				JOptionPane.showMessageDialog(eav, "급여는 필수 입력입니다.");
				eav.getJtfSal().requestFocus();
				return;
			}
			if(eav.getJtaErDesc().getText().trim()==null||eav.getJtaErDesc().getText().trim().equals("")){
				JOptionPane.showMessageDialog(eav, "상세설명은 필수 입력입니다.");
				eav.getJtaErDesc().requestFocus();
				return;
			}
			try {
				register();
			}catch(NumberFormatException nfe) {
				JOptionPane.showMessageDialog(eav, "급여는 숫자형식으로 입력해야합니다.");
				eav.getJtfSal().requestFocus();
			}
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
