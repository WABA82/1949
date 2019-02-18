package user.er.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import user.dao.ErDAO;
import user.ee.vo.EeInterestAndAppVO;
import user.er.view.ErMgMtView;
import user.er.view.ErModifyView;
import user.er.vo.ErListVO;
import user.er.vo.ErModifyVO;

public class ErModifyController extends WindowAdapter implements ActionListener {
	private ErModifyView emv;
	private String erNum,erId;
	private ErDAO erdao;
	private ErMgMtController emmc;
	private int preSkill;
	
	public ErModifyController(ErModifyView emv, String erNum,String erId,ErMgMtController emmc, int preSkill) {
		this.emv = emv;
		this.erNum = erNum;
		this.erId = erId;
		this.emmc= emmc;
		this.preSkill= preSkill;
		erdao= ErDAO.getInstance();
	}
	public void modifyEr() {
		boolean updateflag=false;
		String rank = String.valueOf(emv.getJcbRank().getSelectedItem());
		String hireType = String.valueOf(emv.getJcbHireType().getSelectedItem());
		String portfolio = String.valueOf(emv.getJcbPortfolio().getSelectedItem());
		List<String> listSkill = new ArrayList<String>();
		
		if(emv.getJchJava().isSelected()) {
			listSkill.add("Java");
		}else if(emv.getJchJspServlet().isSelected()) {
			listSkill.add("JspServlet");
		}else if(emv.getJchSpring().isSelected()) {
			listSkill.add("Spring");
		}else if(emv.getJchOracle().isSelected()) {
			listSkill.add("Oracle");
		}else if(emv.getJchHTML().isSelected()) {
			listSkill.add("HTML");
		}else if(emv.getJchCSS().isSelected()) {
			listSkill.add("CSS");
		}else if(emv.getJchLinux().isSelected()) {
			listSkill.add("Linux");
		}else if(emv.getJchJS().isSelected()) {
			listSkill.add("JS");
		}
		
		if(rank.equals("����")) {
			rank="N";
		}else if(rank.equals("���")) {
			rank="C";
		}
		// rank: 'N' - ���� 'C' - ���
		// hiretype 'C' - ������'N' - ��������'F' - ����
		
		if(hireType.equals("������")) {
			hireType="C";
		}else if(hireType.equals("�����")) {
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
				emv.dispose();
				emmc.setDtm();
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
			emmc.setDtm();
		
		}else {
			JOptionPane.showMessageDialog(emv, "����Ʈ������ �����߽��ϴ�.");
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent ae) {
		if(ae.getSource()==emv.getJbReg()) {
			//����ϱ�
			modifyEr();
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
