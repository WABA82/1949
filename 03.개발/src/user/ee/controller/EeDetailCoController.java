package user.ee.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import user.ee.view.EeDetailCoView;

public class EeDetailCoController extends WindowAdapter implements MouseListener, ActionListener {
	private EeDetailCoView edcv;
	
	public EeDetailCoController(EeDetailCoView edcv) {
		this.edcv = edcv;
		
	}//생성자 끝
	
	
	@Override
	public void windowClosing(WindowEvent e) {
		edcv.dispose();
	}//windowClosing
	
	
	@Override
	public void actionPerformed(ActionEvent ae) {
		if(ae.getSource()==edcv.getJbClose()) {
			edcv.dispose();
		}
		
		
	}//버튼 작업

	@Override
	public void mouseClicked(MouseEvent e) {

	}

	public void changeImg() {
		System.out.println("이미지 바꾸기");
	}//changeImg
	
	@Override
	public void mousePressed(MouseEvent e) {}
	@Override
	public void mouseReleased(MouseEvent e) {}
	@Override
	public void mouseEntered(MouseEvent e) {}
	@Override
	public void mouseExited(MouseEvent e) {}
}
