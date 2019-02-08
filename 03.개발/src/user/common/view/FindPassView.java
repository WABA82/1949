package user.common.view;

import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import user.common.controller.FindPassController;

public class FindPassView extends JDialog {

	private JTextField jtfId, jtfAnswer;
	private JComboBox<String> jcbQuestion;
	private JButton jbValidation, jbClose;
	
	public FindPassView(LoginView lv) {
		super(lv, "1949 - 비밀번호 찾기 인증", true);
//	public FindPassView() {
		
		JLabel jlTitle = new JLabel("비밀번호 찾기");
		jlTitle.setFont(new Font(Font.SERIF, Font.BOLD, 20));
		JLabel jlId = new JLabel("아이디");
		JLabel jlQuestion = new JLabel("인증질문");
		JLabel jlAnswer = new JLabel("질문답");
		
		jtfId = new JTextField();
		jtfAnswer = new JTextField();
		
		String[] items = { "내 혈액형은?", "어릴적 가장 친했던 친구는?" };
		jcbQuestion = new JComboBox<String>(items);
		
		jbValidation = new JButton("유저 검증");
		jbClose = new JButton("닫기");
		
		setLayout(null);
		
		jlTitle.setBounds(140, 30, 150, 30);
		jlId.setBounds(50, 90, 80, 30);
		jlQuestion.setBounds(50, 130, 80, 30);
		jlAnswer.setBounds(50, 170, 80, 30);
		
		jtfId.setBounds(120, 90, 210, 30);
		jcbQuestion.setBounds(120, 130, 210, 30);
		jtfAnswer.setBounds(120, 170, 210, 30);
		jbValidation.setBounds(90, 220, 100, 30);
		jbClose.setBounds(210, 220, 90, 30);
		
		add(jlTitle);
		add(jlId);
		add(jlQuestion);
		add(jlAnswer);
		add(jtfId);
		add(jcbQuestion);
		add(jtfAnswer);
		add(jbValidation);
		add(jbClose);
		
		FindPassController fpc = new FindPassController(this);
		
		addWindowListener(fpc);
		
		setBounds(500, 200, 400, 310);
		setResizable(false);
		setVisible(true);
	}
	
	/*public static void main(String[] args) {
		new FindPassView();
	}*/
	
	public JTextField getJtfId() {
		return jtfId;
	}
	public JTextField getJtfAnswer() {
		return jtfAnswer;
	}
	public JComboBox<String> getJcbQuestion() {
		return jcbQuestion;
	}
	public JButton getJbValidation() {
		return jbValidation;
	}
	public JButton getJbClose() {
		return jbClose;
	}
}
