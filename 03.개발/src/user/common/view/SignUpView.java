package user.common.view;

import java.awt.Color;
import java.awt.Font;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import user.common.controller.SignUpController;

@SuppressWarnings("serial")
public class SignUpView extends JDialog {
	private JRadioButton jrbEe, jrbEr;
	private JTextField jtfId, jtfName, jtfSsn1, jtfTel, jtfZip, jtfAddr1, jtfAddr2,jtfEmail, jtfAnswer;
	private JPasswordField jpfPass1, jpfPass2, jpfSsn2;
	private JButton jbSignUp, jbCancel, jbAddr;
	private JComboBox<String> jcbQuestion;
	
	public SignUpView(LoginView lv) {
		//public SignUpView() {
		super(lv, "ȸ������", true);
		setTitle("ȸ�� ����"); //���� �۾��� ���� ���������� Ÿ��Ʋ�̶� ��ޱ����ϱ�(x��ǥ y��ǥ �޾Ƽ� ȭ�����)
		jrbEe = new JRadioButton("�Ϲݻ����");
		jrbEr = new JRadioButton("��������");
		
		JLabel jlTitle = new JLabel("ȸ������");
		JLabel jlId = new JLabel("���̵�");
		JLabel jlPw1 = new JLabel("��й�ȣ");
		JLabel jlPw2 = new JLabel("��й�ȣ Ȯ��");
		JLabel jlName= new JLabel("�̸�");
		JLabel jlSsn = new JLabel("�ֹι�ȣ");
		JLabel jlTel = new JLabel("����ó");
		JLabel jlAddr1 = new JLabel("�ּ�");
		JLabel jlAddr2 = new JLabel("���ּ�");
		JLabel jlEmail =new JLabel("�̸���");
		JLabel jlQuestion = new JLabel("��������");
		JLabel jlAnswer = new JLabel("������");
		JLabel jlSlash = new JLabel("-");
		
		jtfId = new JTextField();
		jpfPass1 = new JPasswordField();
		jpfPass2 = new JPasswordField();
		jtfName = new JTextField();
		jtfSsn1 = new JTextField();
		jpfSsn2 = new JPasswordField();
		jtfTel = new JTextField(); 
		jtfZip = new JTextField();
		jtfZip.setEditable(false);
		jtfZip.setBackground(Color.white);
		jtfAddr1 = new JTextField();
		jtfAddr1 = new JTextField();
        jtfAddr1.setEditable(false);
        jtfAddr1.setBackground(Color.white);
		jtfAddr2 = new JTextField();
		jtfEmail = new JTextField();
		jtfAnswer = new JTextField();
		
		jbAddr = new JButton("�ּҰ˻�");
		jbSignUp = new JButton("ȸ������");
		jbCancel = new JButton("�ݱ�");
		
		jcbQuestion = new JComboBox<String>();

		ButtonGroup bgUserType = new ButtonGroup(); 
		setLayout(null);
		
		bgUserType.add(jrbEe);
		bgUserType.add(jrbEr);
		
		jrbEe.setSelected(true);
		jrbEe.setBounds(69, 76, 121, 23);
		add(jrbEe);
		
		jrbEr.setBounds(202, 76, 121, 23);
		add(jrbEr);
		
		jlTitle.setFont(new Font("����", Font.PLAIN, 25));
		jlTitle.setBounds(144, 20, 110, 50);
		add(jlTitle);
		
		jlId.setBounds(32, 105, 80, 30);
		add(jlId);
		
		jlPw1.setBounds(32, 145, 80, 30);
		add(jlPw1);
		
		jlPw2.setBounds(32, 186, 100, 30);
		add(jlPw2);
		
		jlName.setBounds(32, 226, 80, 30);
		add(jlName);
		
		jlSsn.setBounds(32, 266, 80, 30);
		add(jlSsn);
		
		jlTel.setBounds(32, 306, 80, 30);
		add(jlTel);
		
		jlEmail.setBounds(32, 346, 80, 30);
		add(jlEmail);
		
		jlAddr1.setBounds(31, 388, 80, 30);
		add(jlAddr1);
		
		jlAddr2.setBounds(30, 468, 80, 30);
		add(jlAddr2);
		
		jlAnswer.setBounds(31, 548, 80, 30);
		add(jlAnswer);
		
		jlQuestion.setBounds(31, 509, 80, 30);
		add(jlQuestion);
		
		jlSlash.setBounds(219, 267, 16, 30);
		add(jlSlash);
		
		jtfId.setBounds(123, 106, 200, 30);
		add(jtfId);
		
		jpfPass1.setBounds(123, 146, 200, 30);
		add(jpfPass1);
		
		jpfPass2.setBounds(123, 187, 200, 30);
		add(jpfPass2);
		
		jtfName.setBounds(123, 227, 200, 30);
		add(jtfName);
		
		jtfSsn1.setBounds(123, 267, 92, 30);
		add(jtfSsn1);
		
		jpfSsn2.setBounds(231, 267, 92, 30);
		add(jpfSsn2);
		
		jtfTel.setBounds(123, 307, 200, 30);
		add(jtfTel);
		
		jtfEmail.setBounds(123, 348, 200, 30);
		add(jtfEmail);
		
		jtfZip.setBounds(123, 389, 102, 30);
		add(jtfZip);
		
		jtfAddr1.setBounds(123, 429, 200, 30);
		add(jtfAddr1);
		
		jtfAddr2.setBounds(123, 469, 200, 30);
		add(jtfAddr2);
		
		jtfAnswer.setBounds(123, 549, 200, 30);
		add(jtfAnswer);
		
		jbAddr.setBounds(231, 389, 92, 30);
		add(jbAddr);
		
		jbSignUp.setBounds(98, 625, 92, 30);
		add(jbSignUp);
		
		jbCancel.setBounds(213, 625, 92, 30);
		add(jbCancel);
		
		jcbQuestion.setBounds(123, 509, 200, 30);
		jcbQuestion.addItem("�� ��������?");
		jcbQuestion.addItem("���� ģ�� ģ����?");
		add(jcbQuestion);
		
		SignUpController suc = new SignUpController(this);
		jbAddr.addActionListener(suc);
		jbSignUp.addActionListener(suc);
		jbCancel.addActionListener(suc);
		
		addWindowListener(suc);
		
		
		//setBounds(lv.getX()+420,lv.getY()-100,390,740);
		setBounds(420,100,390,740);
		setVisible(true);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		
	}
	public JRadioButton getJrbEe() {
		return jrbEe;
	}
	public JRadioButton getJrbEr() {
		return jrbEr;
	}
	public JTextField getJtfId() {
		return jtfId;
	}
	public JTextField getJtfName() {
		return jtfName;
	}
	public JTextField getJtfSsn1() {
		return jtfSsn1;
	}
	public JTextField getJtfTel() {
		return jtfTel;
	}
	public JTextField getJtfZip() {
		return jtfZip;
	}
	public JTextField getJtfAddr1() {
		return jtfAddr1;
	}
	public JTextField getJtfAddr2() {
		return jtfAddr2;
	}
	public JTextField getJtfEmail() {
		return jtfEmail;
	}
	public JTextField getJtfAnswer() {
		return jtfAnswer;
	}
	public JPasswordField getJpfPass1() {
		return jpfPass1;
	}
	public JPasswordField getJpfPass2() {
		return jpfPass2;
	}
	public JPasswordField getJpfSsn2() {
		return jpfSsn2;
	}
	public JButton getJbSignUp() {
		return jbSignUp;
	}
	public JButton getJbCancel() {
		return jbCancel;
	}
	public JButton getJbAddr() {
		return jbAddr;
	}
	public JComboBox<String> getJcbQuestion() {
		return jcbQuestion;
	}
	/*public static void main(String[] args) {
		new SignUpView();
	}*/
}
