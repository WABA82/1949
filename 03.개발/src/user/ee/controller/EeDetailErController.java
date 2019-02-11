package user.ee.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import user.dao.EeDAO;
import user.ee.view.EeDetailCoView;
import user.ee.view.EeDetailErView;
import user.ee.vo.CoDetailVO;
import user.ee.vo.DetailErInfoVO;
import user.ee.vo.EeInterestAndAppVO;
////////////0210 다음할것 : 지원하기 구현(창을 닫고 다시 켯을때 하트가 리셋), 관심눌렀을때 값 보내는 방법///////////
public class EeDetailErController extends WindowAdapter implements ActionListener, MouseListener {
	private EeDetailErView edev;
	private String erNum;
	private String eeId;
	private boolean mouseClickFlag; 
	private EeInterestAndAppVO eiaavo;
	private EeDAO ee_dao;
	
	public EeDetailErController(EeDetailErView edev, String erNum, String eeId,boolean flagHeart) {
		this.edev = edev;
		this.erNum = erNum;
		this.eeId = eeId;
		mouseClickFlag = flagHeart;
		ee_dao= EeDAO.getInstance();
	}

	public void addUInterestEr() {
		eiaavo = new EeInterestAndAppVO(erNum, eeId);
		
		try {
			ee_dao.insertInterestEr(eiaavo);
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(edev, "추가에 실패했습니다.");
			return;
		}
		edev.getJlHeart().setIcon(new ImageIcon("C:/dev/1949/03.개발/가데이터/하트/r_heart.png"));
		JOptionPane.showMessageDialog(edev, "관심 구인글에 추가되었습니다!");
		try {
			DetailErInfoVO devo= ee_dao.selectDetail(erNum,eeId);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}//addUInterestEr
	
	public void removeInterestEr() {
		boolean deleteFlag=false;
		eiaavo = new EeInterestAndAppVO(erNum, eeId);
		
		try {
			deleteFlag = ee_dao.deleteInterestEr(eiaavo);
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(edev, "리스트삭제에 실패했습니다.");
			return;
		}
		if(deleteFlag) {
			JOptionPane.showMessageDialog(edev, "관심 구인글을 취소했습니다.");
			edev.getJlHeart().setIcon(new ImageIcon("C:/dev/1949/03.개발/가데이터/하트/b_heart.png"));
		}else {
			JOptionPane.showMessageDialog(edev, "리스트삭제에 실패했습니다.");
		}
	}//removeInterestEr
	
	public void showCoDetail() {
		CoDetailVO cdvo = new CoDetailVO();
		EeDetailCoView edcv = new EeDetailCoView(edev, cdvo);
	}//showCoDetail
	
	public void apply(){
		//관심구인정보에 전달(eeAppVO)
	}
	
	
	@Override
	public void windowClosing(WindowEvent we) {
		edev.dispose();
	}
	@Override
	public void actionPerformed(ActionEvent ae) {
		if(ae.getSource()==edev.getJbCoInfo()) {
			//회사상세정보
			showCoDetail();
		}
		if(ae.getSource()==edev.getJbApply()) {
			//지원하기
			int apply=JOptionPane.showConfirmDialog(edev, "지원하시겠습니까?");
			if(apply==0) {
				apply();
				JOptionPane.showMessageDialog(edev, "지원이 완료되었습니다. \n 지원해주셔서 감사합니다.");
			}
		}
		if(ae.getSource()==edev.getJbClose()) {
			edev.dispose();
		}
	}
	
	@Override
	public void mouseClicked(MouseEvent me) {
		
		if(me.getSource()==edev.getJlHeart()) {
			mouseClickFlag = !mouseClickFlag;
		}
		if(mouseClickFlag) {
			addUInterestEr();
		}else {
			removeInterestEr();
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
