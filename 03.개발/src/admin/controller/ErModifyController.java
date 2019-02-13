package admin.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import admin.dao.AdminDAO;
import admin.view.AdminMgMtView;
import admin.view.ErModifyView;
import admin.vo.ErInfoVO;
import admin.vo.ErModifyVO;

public class ErModifyController extends WindowAdapter implements ActionListener {
	
	private ErModifyView emv;
	private AdminMgMtView ammv;
	private ErInfoVO eivo;
	private AdminMgMtController ammc;
	
	public ErModifyController(ErModifyView emv, AdminMgMtView ammv, ErInfoVO eivo, AdminMgMtController ammc) {
		this.emv = emv;
		this.ammv = ammv;
		this.eivo = eivo;
		this.ammc = ammc;
	}
	
	private void msgCenter(String msg) {
		JOptionPane.showMessageDialog(emv, msg);
	}
	
	public void modifyEr() {
		
		// 입력값 검증
		if(emv.getJtfSubject().getText().trim().equals("")) {
			msgCenter("제목을 입력해주세요.");
			return;
		}
		
		if(emv.getJtfSal().getText().trim().equals("")) {
			msgCenter("급여를 입력해주세요.");
			return;
		}
		
		if(emv.getJtaErDesc().getText().trim().equals("")) {
			msgCenter("상세정보를 입력해주세요.");
			return;
		}
		
		String subject = emv.getJtfSubject().getText().trim();

		int sal = 0;
		try {
			sal = Integer.parseInt(emv.getJtfSal().getText().trim());
		} catch (NumberFormatException nfe) {
			msgCenter("급여는 숫자만 입력해주세요.");
			return;
		}
		
		String education = (String)emv.getJcbEducation().getSelectedItem();
		
		String rank = "";
		if (emv.getJcbRank().getSelectedItem().equals("신입")) {
			rank = "C";
		} else if (emv.getJcbRank().getSelectedItem().equals("경력")) {
			rank = "N";
		}
		
		String loc = (String)emv.getJcbLoc().getSelectedItem();
		String hireType = "";
		if (emv.getJcbHireType().getSelectedItem().equals("정규직")) {
			hireType = "C";
		} else if (emv.getJcbHireType().getSelectedItem().equals("계약직")) {
			hireType = "N";
		} else if (emv.getJcbHireType().getSelectedItem().equals("프리")) {
			hireType = "F";
		}
		
		
		String portfolio = emv.getJcbPortfolio().getSelectedItem().equals("YES") ? "Y" : "N";
		String erDesc = emv.getJtaErDesc().getText().trim();
		
		List<String> listSkill = new ArrayList<>();
		
		if(emv.getJchJava().isSelected()) {
			listSkill.add("s_01");
		} 
		if(emv.getJchJspServlet().isSelected()) {
			listSkill.add("s_02");
		}
		if(emv.getJchSpring().isSelected()) {
			listSkill.add("s_03");
		}
		if(emv.getJchOracle().isSelected()) {
			listSkill.add("s_04");
		}
		if(emv.getJchHTML().isSelected()) {
			listSkill.add("s_05");
		}
		if(emv.getJchCSS().isSelected()) {
			listSkill.add("s_06");
		}
		if(emv.getJchLinux().isSelected()) {
			listSkill.add("s_07");
		}
		if(emv.getJchJS().isSelected()) {
			listSkill.add("s_08");
		}
		
		ErModifyVO emvo = new ErModifyVO(eivo.getErNum(), subject,
				education, rank, loc, hireType, portfolio, erDesc, sal, listSkill);
				
		if (AdminDAO.getInstance().updateEr(emvo, eivo.getListSkill().size())) {
			msgCenter("구인 정보가 수정되었습니다.");
			emv.dispose();
			try {
				eivo = AdminDAO.getInstance().selectOneEr(eivo.getErNum());
				ammc.setEr();
				new ErModifyView(ammv, eivo, ammc);
			} catch (SQLException e) {
				msgCenter("DB에 문제 발생");
				e.printStackTrace();
			}
		}
	}
	
	public void removeEr() {
		
		switch(JOptionPane.showConfirmDialog(emv, "해당 구인 정보를 정말 삭제하시겠습니까?")) {
		case JOptionPane.OK_OPTION:
			
			if(AdminDAO.getInstance().deleteEr(eivo)) {
				msgCenter("구인 정보가 삭제되었습니다.");
				emv.dispose();
				ammc.setEr();
			} else {
				msgCenter("구인 정보 삭제가 실패했습니다.");
			}
			
			break;
		}
	}
	
	@Override
	public void windowClosing(WindowEvent e) {
		emv.dispose();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == emv.getJbModify()) {
			modifyEr();
		}
		
		if(e.getSource() == emv.getJbRemove()) {
			removeEr();
		}
		
		if(e.getSource() == emv.getJbCancel()) {
			emv.dispose();
			ammc.setEr();
		}
	}

}
