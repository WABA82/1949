package user.er.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import user.dao.ErDAO;
import user.er.view.ErDetailEeView;
import user.er.vo.DetailEeInfoVO;
import user.er.vo.ErInterestVO;

public class ErDetailEeController extends WindowAdapter implements ActionListener, MouseListener {
	private ErDetailEeView edev;
	private String eeNum, erId;
	private boolean mouseClickFlag; 
	private ErInterestVO eivo;
	private ErDAO erdao;
	public ErDetailEeController(ErDetailEeView edev, String eeNum, String erId,boolean flagHeart) {
		this.edev = edev;
		this.eeNum = eeNum;
		this.erId = erId;
		System.out.println("controller: "+flagHeart);
		mouseClickFlag=flagHeart;
		erdao= ErDAO.getInstance();
	}
	public void addInterestEe() {
		eivo = new ErInterestVO(eeNum,erId);
		
		try {
			erdao.insertInterestEe(eivo);
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(edev, "추가에 실패했습니다.");
			return;
		}
		edev.getJlHeart().setIcon(new ImageIcon("C:/dev/1949/03.개발/가데이터/하트/r_heart.png"));
		JOptionPane.showMessageDialog(edev, "관심 구인글에 추가되었습니다!");
		try {
			DetailEeInfoVO devo= erdao.selectDeatilEe(eeNum, erId);

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void removeInterestEe() {
		boolean deleteFlag=false;
		eivo = new ErInterestVO(eeNum, erId);
		
		try {
			deleteFlag = erdao.deleteInterestEe(eivo);
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(edev, "리스트삭제에 실패했습니다.");
			
		}
		if(deleteFlag) {
			JOptionPane.showMessageDialog(edev, "관심 구인글을 취소했습니다.");
			edev.getJlHeart().setIcon(new ImageIcon("C:/dev/1949/03.개발/가데이터/하트/b_heart.png"));
		}else {
			JOptionPane.showMessageDialog(edev, "리스트삭제에 실패했습니다.");
		}
	}//removeInterestEr
	
	public void showCoDetail() {
		
	}
	
	public void extRsmDown() {
		
	}
	
	
	@Override
	public void mouseClicked(MouseEvent me) {
		if(me.getSource()==edev.getJlHeart()) {
			System.out.println(mouseClickFlag);
			mouseClickFlag = !mouseClickFlag;
		}
		if(mouseClickFlag) {
			addInterestEe();
		}else {
			removeInterestEe();
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent ae) {
		if(ae.getSource()==edev.getJbRsmDown()) {
			extRsmDown();
		}
		if(ae.getSource()==edev.getJbClose()) {
			edev.dispose();
		}
	}
	
	@Override
	public void windowClosing(WindowEvent e) {
		edev.dispose();
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