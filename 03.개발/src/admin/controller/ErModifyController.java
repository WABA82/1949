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
import admin.util.AdminUtil;
import admin.view.AdminMgMtView;
import admin.view.ErModifyView;
import admin.vo.ErInfoVO;
import admin.vo.ErModifyVO;

public class ErModifyController extends WindowAdapter implements ActionListener {
	
	private ErModifyView emv;
	private AdminMgMtView ammv;
	private ErInfoVO eivo;
	private AdminMgMtController ammc;
	private AdminUtil au;
	
	public ErModifyController(ErModifyView emv, AdminMgMtView ammv, ErInfoVO eivo, AdminMgMtController ammc) {
		this.emv = emv;
		this.ammv = ammv;
		this.eivo = eivo;
		this.ammc = ammc;
		
		au = new AdminUtil();
	}
	
	private void msgCenter(String msg) {
		JOptionPane.showMessageDialog(emv, msg);
	}
	
	/**
	 * ���� ������ �����ϴ� �޼ҵ�
	 */
	public void modifyEr() {
		// �Է°� ����
		if(emv.getJtfSubject().getText().trim().equals("")) {
			msgCenter("������ �Է����ּ���.");
			emv.getJtfSubject().requestFocus();
			return;
		}
		
		if(emv.getJtfSal().getText().trim().equals("")) {
			msgCenter("�޿��� �Է����ּ���.");
			emv.getJtfSal().requestFocus();
			return;
		}
		
		if(emv.getJtaErDesc().getText().trim().equals("")) {
			msgCenter("�������� �Է����ּ���.");
			emv.getJtaErDesc().requestFocus();
			return;
		}
		
		String subject = emv.getJtfSubject().getText().trim();

		int sal = 0;
		try {
			sal = Integer.parseInt(emv.getJtfSal().getText().trim());
		} catch (NumberFormatException nfe) {
			msgCenter("�޿��� ���ڸ� �Է����ּ���.");
			emv.getJtfSal().setText("");
			emv.getJtfSal().requestFocus();
			return;
		}
		
		String education = (String)emv.getJcbEducation().getSelectedItem();
		
		String rank = "";
		if (emv.getJcbRank().getSelectedItem().equals("����")) {
			rank = "C";
		} else if (emv.getJcbRank().getSelectedItem().equals("���")) {
			rank = "N";
		}
		
		String loc = (String)emv.getJcbLoc().getSelectedItem();
		String hireType = "";
		if (emv.getJcbHireType().getSelectedItem().equals("������")) {
			hireType = "C";
		} else if (emv.getJcbHireType().getSelectedItem().equals("�����")) {
			hireType = "N";
		} else {
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
			try {
				msgCenter("���� ������ �����Ǿ����ϴ�.");
				au.sendLog("["+eivo.getErNum()+"] ���� ���� ����");
				emv.dispose();
				eivo = AdminDAO.getInstance().selectOneEr(eivo.getErNum());
				ammc.setEr();
				new ErModifyView(ammv, eivo, ammc);
			} catch (SQLException e) {
				msgCenter("DB�� ���� �߻�");
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * ���� ������ �����ϴ� �޼ҵ� 
	 */
	public void removeEr() {
		switch(JOptionPane.showConfirmDialog(emv, "�ش� ���� ������ ���� �����Ͻðڽ��ϱ�?")) {
		case JOptionPane.OK_OPTION:
			
			try {
				if(AdminDAO.getInstance().deleteEr(eivo.getErNum())) {
					msgCenter(eivo.getErNum()+"���� ������ �����Ǿ����ϴ�.");
					au.sendLog("["+eivo.getErNum()+"] ���� ���� ����");
					emv.dispose();
					ammc.setEr();
				} else {
					msgCenter("���� ���� ������ �����߽��ϴ�.");
				}
			} catch (SQLException e) {
				e.printStackTrace();
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
