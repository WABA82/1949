package user.ee.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JLabel;

import user.ee.view.EeDetailCoView;

/**
 * 회사 상세 정보의 컨트롤러.
 * 
 * @author 재현
 *
 */
public class EeDetailCoController extends WindowAdapter implements ActionListener {

	private EeDetailCoView edcv;

	public EeDetailCoController(EeDetailCoView edcv) {
		this.edcv = edcv;
	}// 생성자

	@Override
	public void windowClosing(WindowEvent e) {
		edcv.dispose();
	}// windowClosing

	@Override
	public void actionPerformed(ActionEvent ae) {
		if (ae.getSource() == edcv.getJbClose()) {
			edcv.dispose();
		} // end if
	}// 버튼 작업

}// class
