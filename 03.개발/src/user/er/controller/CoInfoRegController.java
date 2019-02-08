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
	}//������ �ݱ�
	
	@Override
	public void actionPerformed(ActionEvent ae) {
		if(ae.getSource()==cirv.getJbReg()) {
			register();
		}else if(ae.getSource()==cirv.getJbClose()) {
			cirv.dispose();
		}
	}//��ư ����

	@Override
	public void mouseClicked(MouseEvent e) {

	}

	public void register() {
		System.out.println("���");
		JOptionPane.showMessageDialog(cirv, "ȸ�� ������ ��ϵǾ����ϴ� <br> �������� ���� ������ ��ȸ�����մϴ�.");
	}
	
	public void changeImg(JLabel jl) {
		System.out.println("�̹��� ����");
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
