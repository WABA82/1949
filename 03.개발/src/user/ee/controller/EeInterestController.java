package user.ee.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

import user.ee.view.EeInterestView;
import user.ee.vo.EeHiringVO;
import user.er.vo.ErHiringVO;

public class EeInterestController extends WindowAdapter implements ActionListener, MouseListener {

	private EeInterestView eiv;

	public EeInterestController(EeInterestView eiv, List<EeHiringVO> ehvo) {
		this.eiv = eiv;
	}// 생성자

	@Override
	public void windowClosing(WindowEvent arg0) {
		eiv.dispose();
	}// windowClosing

	@Override
	public void actionPerformed(ActionEvent e) {
	}// actionPerformed

	@Override
	public void mouseClicked(MouseEvent e) {
	}// mouseClicked

	private void setDTM() {

	}// setDTM

	private void showDetailErinfo() {

	}// showDetailErinfo

	////////// 안쓰는 메소드//////////
	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}
	////////// 안쓰는 메소드//////////

}// class
