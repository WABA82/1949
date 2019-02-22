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
	private LoginView lv;
	
	public FindPassController(FindPassView fpv) {
		this.fpv = fpv;
	}
	
	public void checkUserData() {
		JTextField jtfId=fpv.getJtfId();
		JComboBox<String> jcbQ=fpv.getJcbQuestion();
		JTextField jtfAnswer=fpv.getJtfAnswer();
		
		String id=jtfId.getText().trim();
		String qType=String.valueOf((jcbQ.getSelectedIndex()));///
		System.out.println(qType);
		String answer=jtfAnswer.getText().trim();
		
		if(id==null||id.equals("")) {
			JOptionPane.showMessageDialog(fpv, "아이디를 입력해주세요.");
			jtfId.requestFocus();
			return;
		}
		if(qType==null||qType.equals("")) {
			JOptionPane.showMessageDialog(fpv, "인증질문을 선택해주세요.");
			jcbQ.requestFocus();
			return;
		}
		if(answer==null||answer.equals("")) {
			JOptionPane.showMessageDialog(fpv, "질문답을 입력해주세요.");
			jtfAnswer.requestFocus();
			return;
		}
		
		
		try {
			FindPassVO fpvo= new FindPassVO(id, qType, answer);
			
			if(CommonDAO.getInstance().selectFindPass(fpvo)) {
				JOptionPane.showMessageDialog(fpv, "입력하신 정보가 일치합니다.");
				fpv.dispose();//비밀번호찾기창닫기
				new SetNewPassView(lv, id);
			}else{
				JOptionPane.showMessageDialog(fpv, "정보가 올바르지 않습니다.");
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(fpv, "DB에서 문제가 발생했습니다.");
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
//count(*)가1이면존재 0이면 존재하지않음