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
	}//������
	
	@Override
	public void windowClosing(WindowEvent e) {
		cimv.dispose();
	}//������ �ݱ�
	
	
	@Override
	public void actionPerformed(ActionEvent ae) {
		if(ae.getSource()==cimv.getJbModify()) {
			modify();
		}else if(ae.getSource()==cimv.getJbClose()) {
			cimv.dispose();
		}
	}//��ư
	
	public void modify() {
		System.out.println("����");
		JOptionPane.showMessageDialog(cimv, "ȸ�� ������ �����Ǿ����ϴ�.");
	}//modify

	public void changeImg(JLabel jl) {
		System.out.println("�̹��� ����");
	}
}
