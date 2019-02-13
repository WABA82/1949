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
import user.er.view.ErModifyView;
import user.er.vo.ErListVO;
import user.er.vo.ErModifyVO;

public class ErModifyController extends WindowAdapter implements ActionListener {
	private ErModifyView emv;
	private String erNum,erId;
	private ErDAO erdao;
	private ErMgMtController emmc;
	
	
	public ErModifyController(ErModifyView emv, String erNum,String erId) {
		this.emv = emv;
		this.erNum = erNum;
		this.erId = erId;
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
		
		if(rank.equals("신입")) {
			rank="N";
		}else if(rank.equals("경력")) {
			rank="C";
		}
		// rank: 'N' - 신입 'C' - 경력
		// hiretype 'C' - 정규직'N' - 비정규직'F' - 프리
		
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
			updateflag = erdao.updateErModify(emvo);
			if(updateflag) {
				JOptionPane.showMessageDialog(emv, "구인 정보가 수정되었습니다.");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(emv, "DB오류");
		}
	}
	
	public void deleteEr() {
		boolean deleteFlag;
		List<ErListVO> list = new ArrayList<>();
		try {
			deleteFlag = erdao.deleteEr(erNum);
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(emv, "DB오류");
			return;
		}
		if(deleteFlag) {
			JOptionPane.showMessageDialog(emv, "구직 정보가 삭제되었습니다. ");
			try {
				list = erdao.selectErList(erId);
			} catch (SQLException e) {
			}
			emmc.setDtm(list);
		
		}else {
			JOptionPane.showMessageDialog(emv, "리스트삭제에 실패했습니다.");
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent ae) {
		if(ae.getSource()==emv.getJbReg()) {
			//등록하기
			modifyEr();
		}
		if(ae.getSource()==emv.getJbDelete()) {
			//삭제하기
			int confirm = JOptionPane.showConfirmDialog(emv, "해당 구인 정보를 정말 삭제하시겠습니까?");
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
