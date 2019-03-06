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
import user.er.view.ErMgMtView;
import user.er.view.ErModifyView;
import user.er.vo.ErDetailVO;
import user.er.vo.ErModifyVO;
import user.util.UserLog;

public class ErModifyController extends WindowAdapter implements ActionListener {
	private ErModifyView emv;
	private String erNum,erId;
	private ErDAO erdao;
	private ErMgMtController emmc;
	private int preSkill;
	private UserLog ul;
	private ErMgMtView emmv;
	
	public ErModifyController(ErMgMtView emmv, ErModifyView emv, String erNum,String erId,ErMgMtController emmc, int preSkill) {
		this.emv = emv;
		this.erNum = erNum;
		this.erId = erId;
		this.emmc= emmc;
		this.preSkill= preSkill;
		this.emmv = emmv;
		ul = new UserLog();
		erdao= ErDAO.getInstance();
	}
	public void modifyEr()throws NullPointerException {
		boolean updateflag=false;
		String rank = String.valueOf(emv.getJcbRank().getSelectedItem());
		String hireType = String.valueOf(emv.getJcbHireType().getSelectedItem());
		String portfolio = String.valueOf(emv.getJcbPortfolio().getSelectedItem());
		List<String> listSkill = new ArrayList<String>();
	
		if(emv.getJchCSS().isSelected()) {
			listSkill.add("s_06");
		}
		if(emv.getJchHTML().isSelected()) {
			listSkill.add("s_05");
		}
		if(emv.getJchJava().isSelected()) {
			listSkill.add("s_01");
		}
		if(emv.getJchJS().isSelected()) {
			listSkill.add("s_08");
		}
		if(emv.getJchJspServlet().isSelected()) {
			listSkill.add("s_02");
		}
		if(emv.getJchLinux().isSelected()) {
			listSkill.add("s_07");
		}
		if(emv.getJchOracle().isSelected()) {
			listSkill.add("s_04");
		}
		if(emv.getJchSpring().isSelected()) {
			listSkill.add("s_03");
		}
		
		if(rank.equals("����")) {
			rank="N";
		}else if(rank.equals("���")) {
			rank="C";
		}
		
		if(hireType.equals("������")) {
			hireType="C";
		}else if(hireType.equals("��������")) {
			hireType="N";
		}else if(hireType.equals("����")) {
			hireType="F";
		}
		
		if(portfolio.equals("YES")) {
			portfolio="Y";
		}else if(portfolio.equals("NO")) {
			portfolio="N";
		}
		
		ErModifyVO emvo = new ErModifyVO(
				erNum, 				
				emv.getJtfSubject().getText().trim(), 
				String.valueOf(emv.getJcbEducation().getSelectedItem()), 
				rank, 
				String.valueOf(emv.getJcbLoc().getSelectedItem()), 
				hireType, 
				portfolio, 
				emv.getJtaErDesc().getText(),
				Integer.parseInt(emv.getJtfSal().getText()), 
				listSkill);
		try {
			updateflag = erdao.updateErModify(emvo,preSkill);
			if(updateflag) {
				JOptionPane.showMessageDialog(emv, "���� ������ �����Ǿ����ϴ�.");
				ul.sendLog(erId, "���� ������ �����߽��ϴ�.");
				emv.dispose();
				//ErMgMtView emmv, ErDetailVO edvo, String erNum, String erId, ErMgMtController emmc
				ErDetailVO edvo = erdao.selectErDetail(erNum);
				emmc.setDtm();
				new ErModifyView(emmv, edvo, erNum, erId, emmc);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(emv, "DB����");
		}
	}
	
	public void deleteEr() {
		boolean deleteFlag;
		try {
			deleteFlag = erdao.deleteEr(erNum);
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(emv, "DB����");
			return;
		}
		if(deleteFlag) {
			JOptionPane.showMessageDialog(emv, "���� ������ �����Ǿ����ϴ�. ");
			ul.sendLog(erId, "���� ������ �����߽��ϴ�.");
			emmc.setDtm();
			emv.dispose();
		}else {
			JOptionPane.showMessageDialog(emv, "�������� ������ �����߽��ϴ�. \n �ٽ� �ѹ� �������ּ���!");
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent ae) {
		if(ae.getSource()==emv.getJbReg()) {
			//����ϱ�
			if(emv.getJtfSubject().getText().trim()==null||emv.getJtfSubject().getText().trim().equals("")){
				JOptionPane.showMessageDialog(emv, "������ �ʼ� �Է��Դϴ�.");
				emv.getJtfSubject().requestFocus();
				return;
			}
			if(emv.getJtfSal().getText().trim()==null||emv.getJtfSal().getText().trim().equals("")){
				JOptionPane.showMessageDialog(emv, "�޿��� �ʼ� �Է��Դϴ�.");
				emv.getJtfSal().requestFocus();
				return;
			}
			if(emv.getJtaErDesc().getText().trim()==null||emv.getJtaErDesc().getText().trim().equals("")){
				JOptionPane.showMessageDialog(emv, "�󼼼����� �ʼ� �Է��Դϴ�.");
				emv.getJtaErDesc().requestFocus();
				return;
			}
			try {
				modifyEr();
			}catch(NumberFormatException nfe) {
				JOptionPane.showMessageDialog(emv, "�޿��� ������������ �Է��ؾ��մϴ�.");
				emv.getJtfSal().requestFocus();
			}
			
		}
		if(ae.getSource()==emv.getJbDelete()) {
			//�����ϱ�
			int confirm = JOptionPane.showConfirmDialog(emv, "�ش� ���� ������ ���� �����Ͻðڽ��ϱ�?");
			if(confirm==0) {
				deleteEr();
			}
		}
		if(ae.getSource()==emv.getJbCancel()) {
			emv.dispose();
		}
	}
	@Override
	public void windowClosing(WindowEvent e) {
		emv.dispose();
	}

}
