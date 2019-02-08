package admin.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JLabel;

import admin.view.AdminMgMtView;
import admin.view.CoModifyView;
import admin.vo.CoInfoVO;

public class CoModifyController extends WindowAdapter implements MouseListener, ActionListener {
	private CoModifyView cmv;
	private AdminMgMtView ammv;
	
	public CoModifyController(CoModifyView cmv/*, AdminMgMtView ammv, CoInfoVO cifvo*/) {
		this.cmv= cmv;
		
	}//생성자 끝
	
	@Override
	public void windowClosing(WindowEvent e) {
		cmv.dispose();
	}
	
	@Override
	public void actionPerformed(ActionEvent ae) {
		if(ae.getSource()==cmv.getJbModify()) {
			modify();
		}else if(ae.getSource()==cmv.getJbRemove()){
			remove();
		}else if(ae.getSource()==cmv.getJbClose()) {
			cmv.dispose();
		}//버튼
		
	}

	public void modify() {
		System.out.println("수정");
	}//modify
	
	public void remove() {
		System.out.println("삭제");
	}//remove
	
	public void changeImg(/*JLabel jl*/) {
		
	}//changeImg
	
	@Override
	public void mouseClicked(MouseEvent e) {

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
