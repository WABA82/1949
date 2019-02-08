package user.er.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import user.er.view.CoInfoRegView;

public class CoInfoRegController extends WindowAdapter implements MouseListener, ActionListener {

	private CoInfoRegView cirv;
	private String erId;
	
	public CoInfoRegController(CoInfoRegView cirv/*, String erId*/) {
		this.cirv=cirv;
	}
	
	@Override
	public void windowClosing(WindowEvent e) {
		cirv.dispose();
	}//윈도우 닫기
	
	@Override
	public void actionPerformed(ActionEvent ae) {
		if(ae.getSource()==cirv.getJbReg()) {
			register();
		}else if(ae.getSource()==cirv.getJbClose()) {
			cirv.dispose();
		}
	}//버튼 동작

	@Override
	public void mouseClicked(MouseEvent e) {

	}

	public void register() {
		System.out.println("등록");
		JOptionPane.showMessageDialog(cirv, "회사 정보가 등록되었습니다 <br> 이제부터 구직 정보를 조회가능합니다.");
	}
	
	public void changeImg(JLabel jl) {
		System.out.println("이미지 수정");
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
