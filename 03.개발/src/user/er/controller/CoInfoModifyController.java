package user.er.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import user.er.view.CoInfoModifyView;

public class CoInfoModifyController extends WindowAdapter implements ActionListener {

	private CoInfoModifyView cimv;
	private String coNum;
	
	public CoInfoModifyController(CoInfoModifyView cimv /*,String coNum*/) {
		this.cimv= cimv;
	}//생성자
	
	@Override
	public void windowClosing(WindowEvent e) {
		cimv.dispose();
	}//윈도우 닫기
	
	
	@Override
	public void actionPerformed(ActionEvent ae) {
		if(ae.getSource()==cimv.getJbModify()) {
			modify();
		}else if(ae.getSource()==cimv.getJbClose()) {
			cimv.dispose();
		}
	}//버튼
	
	public void modify() {
		System.out.println("수정");
		JOptionPane.showMessageDialog(cimv, "회사 정보가 수정되었습니다.");
	}//modify

	public void changeImg(JLabel jl) {
		System.out.println("이미지 수정");
	}
}
