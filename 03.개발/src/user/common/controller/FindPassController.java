package user.common.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import user.common.view.FindPassView;
import user.common.view.LoginView;
import user.common.view.SetNewPassView;
import user.common.vo.FindPassVO;
import user.dao.CommonDAO;

public class FindPassController extends WindowAdapter implements ActionListener {
	
	private FindPassView fpv;
	
	public FindPassController(FindPassView fpv) {
		this.fpv = fpv;
	}
	
	public void checkUserData() {
		JTextField jtfId=fpv.getJtfId();
		JComboBox<String> jcbQ=fpv.getJcbQuestion();
		JTextField jtfAnswer=fpv.getJtfAnswer();
		
		String id=jtfId.getText().trim();
		String qType=jcbQ.getSelectedItem().toString();
		String answer=jtfAnswer.getText().trim();
		
		if(id==null||id.equals("")) {
			JOptionPane.showMessageDialog(fpv, "���̵� �Է����ּ���.");
			jtfId.requestFocus();
			return;
		}
		if(qType==null||qType.equals("")) {
			JOptionPane.showMessageDialog(fpv, "���������� �������ּ���.");
			jcbQ.requestFocus();
			return;
		}
		if(answer==null||answer.equals("")) {
			JOptionPane.showMessageDialog(fpv, "�������� �Է����ּ���.");
			jtfAnswer.requestFocus();
			return;
		}
		
		FindPassVO fpvo= new FindPassVO(id, qType, answer);
		boolean	flag= false;
		LoginView lv=new LoginView();
		String ID="";
		
		try {
			flag=CommonDAO.getInstance().selectFindPass(fpvo);
			
			if(flag=false) {
				JOptionPane.showMessageDialog(fpv, "������ �ùٸ��� �ʽ��ϴ�.");
			}else{
				JOptionPane.showMessageDialog(fpv, "�Է��Ͻ� ������ ��ġ�մϴ�.");
				//����й�ȣ����â����
				SetNewPassView snpv=new SetNewPassView(lv,ID);
				
			}
			
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(fpv, "DB���� ������ �߻��߽��ϴ�.");
			e.printStackTrace();
		}
	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		
		if(ae.getSource()==fpv.getJbValidation()) {
			checkUserData();
		}
		if(ae.getSource()==fpv.getJbClose()) {
			fpv.dispose();
		}
	}
	
	@Override
	public void windowClosing(WindowEvent e) {
		fpv.dispose();
	}
}
//count(*)��1�̸����� 0�̸� ������������