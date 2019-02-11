package user.common.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import kr.co.sist.lunch.admin.model.LunchAdminDAO;
import kr.co.sist.lunch.admin.view.LunchMainView;
import kr.co.sist.lunch.admin.vo.AdminLoginVO;
import user.common.view.FindIdView;
import user.common.vo.FindIdVO;
import user.dao.CommonDAO;

public class FindIdController extends WindowAdapter implements ActionListener {

	private FindIdView fiv;
	
	public FindIdController(FindIdView fiv) {
		this.fiv = fiv;
	}
	
	public void checkUser() {
		String userId="";

		CommonDAO c_dao=CommonDAO.getInstance();
	
		try {
			userId=c_dao.selectFindId(fivo);
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(llv, "DB���� ������ �߻��߽��ϴ�.");
			e.printStackTrace();
		}//end catch
		
		return userId;
	}
	
	
	@Override
	public void actionPerformed(ActionEvent ae) {
		String name= fiv.getJtfName().getText().trim();
		String tel= fiv.getJtfTel().getText().trim();
		
		FindIdVO fivo = new FindIdVO(name, tel);
		if(ae.getSource()==fiv.getJbValidate()) {
			if(name==null||tel.equals("")) {
				JOptionPane.showMessageDialog(fiv, "�̸��� �Է��ϼ���.");
				fiv.getJtfName().requestFocus();
				return;
			}
			if(tel==null||tel.equals("")) {
				JOptionPane.showMessageDialog(fiv, "����ó�� �Է��ϼ���.");
				fiv.getJtfTel().requestFocus();
				return;
			}
			
			//�Է��� ���̵�� ��й�ȣ�� ������ 		
			String adminName=login(alvo);// DB�α��� ������ ������ ����� �޾���.
			
			if( adminName.equals("") ) {// �������� ""���
				JOptionPane.showMessageDialog(llv, "���̵� ��й�ȣ�� Ȯ���ϼ���.");
				jtf.setText("");
				jpf.setText("");
				jtf.requestFocus();
			}else {
				new LunchMainView( adminName );
				LunchMainView.adminId=id; //�α����� ���� �ߴٸ� id��
				//��� ��ü���� ����� �� �ֵ��� static ������ �����Ѵ�. 
				llv.dispose();
			}//end else
		}//end if
		}

		if(ae.getSource()==fiv.getJbValidate()) {
			
			JOptionPane.showMessageDialog(fiv, "�Է��Ͻ� ������ ��ġ�մϴ�.");
		}
	}
	
	
	@Override
	public void windowClosing(WindowEvent e) {
		fiv.dispose();
	}
}
