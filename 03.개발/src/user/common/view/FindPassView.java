package user.common.view;

import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;

import user.common.controller.FindPassController;

@SuppressWarnings("serial")
public class FindPassView extends JDialog {

	private JTextField jtfId, jtfAnswer;
	private JComboBox<String> jcbQuestion;
	private JButton jbValidation, jbClose;
	
	public FindPassView(LoginView lv) {
		super(lv, "1949 - ��й�ȣ ã�� ����", true);
//	public FindPassView() {
		
		JLabel jlTitle = new JLabel("��й�ȣ ã��");
		jlTitle.setFont(new Font(Font.SERIF, Font.BOLD, 20));
		JLabel jlId = new JLabel("���̵�");
		JLabel jlQuestion = new JLabel("��������");
		JLabel jlAnswer = new JLabel("������");
		
		jtfId = new JTextField();
		jtfAnswer = new JTextField();
		
		String[] items = { "�� ��������?", "���� ģ�� ģ����?" };
		jcbQuestion = new JComboBox<String>(items);
		
		jbValidation = new JButton("���� ����");
		jbClose = new JButton("�ݱ�");
		
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
		jbValidation.addActionListener(fpc);
		jbClose.addActionListener(fpc);
		jtfId.addKeyListener(fpc);
		jcbQuestion.addKeyListener(fpc);
		jtfAnswer.addKeyListener(fpc);
		
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
