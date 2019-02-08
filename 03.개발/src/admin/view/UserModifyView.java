package admin.view;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class UserModifyView extends JDialog {
	private JTextField jtfId, jtfName, jtfSsn1, jtfSsn2, jtfTel, jtfZip, jtfAddr1, jtfAddr2,jtfEmail, jtfAnswer, jtfInputDate;
	private JPasswordField jpfPass;
	private JButton jbModify, jbSearchAddr, jbRemove, jbClose;
	private JComboBox<String> jcbQuestion, jcbUser;
	public UserModifyView() {
		
		setTitle("ȸ�� �� ����"); //���� �۾��� ���� ���������� Ÿ��Ʋ�̶� ��ޱ����ϱ�(x��ǥ y��ǥ �޾Ƽ� ȭ�����)
		JLabel jlId = new JLabel("���̵�");
		JLabel jlPw = new JLabel("��й�ȣ");
		JLabel jlName= new JLabel("�̸�");
		JLabel jlSsn = new JLabel("�ֹι�ȣ");
		JLabel jlTel = new JLabel("����ó");
		JLabel jlAddr1 = new JLabel("�ּ�");
		JLabel jlAddr2 = new JLabel("���ּ�");
		JLabel jlEmail =new JLabel("�̸���");
		JLabel jlQuestion = new JLabel("��������");
		JLabel jlAnswer = new JLabel("������");
		JLabel jlUser = new JLabel("ȸ��Ÿ��");
		JLabel jlInputDate = new JLabel("������");
		JLabel jlSlash = new JLabel("-");
		
		jtfId = new JTextField();
		jpfPass = new JPasswordField();
		jtfName = new JTextField();
		jtfSsn1 = new JTextField();
		jtfSsn2 = new JTextField();
		jtfTel = new JTextField(); 
		jtfZip = new JTextField();
		jtfAddr1 = new JTextField();
		jtfAddr2 = new JTextField();
		jtfEmail = new JTextField();
		jtfAnswer = new JTextField();
		jtfInputDate = new JTextField();
		
		jbModify = new JButton("����");
		jbSearchAddr = new JButton("�ּҰ˻�");
		jbRemove = new JButton("����");
		jbClose = new JButton("�ݱ�");
		
		jcbQuestion = new JComboBox<String>();
		jcbUser = new JComboBox<String>();
		
		setLayout(null);
		
		jlId.setBounds(42, 35, 80, 30);
		add(jlId);
		
		jlPw.setBounds(42, 75, 80, 30);
		add(jlPw);
		
		jlName.setBounds(42, 115, 80, 30);
		add(jlName);
		
		jlSsn.setBounds(42, 155, 80, 30);
		add(jlSsn);
		
		jlTel.setBounds(42, 195, 80, 30);
		add(jlTel);
		
		jlAddr1.setBounds(41, 235, 80, 30);
		add(jlAddr1);
		
		jlAddr2.setBounds(40, 315, 80, 30);
		add(jlAddr2);
		
		jlEmail.setBounds(41, 355, 80, 30);
		add(jlEmail);
		
		jlQuestion.setBounds(41, 396, 80, 30);
		add(jlQuestion);
		
		jlAnswer.setBounds(41, 435, 80, 30);
		add(jlAnswer);
		
		jlUser.setBounds(41, 476, 80, 30);
		add(jlUser);
		
		jlInputDate.setBounds(41, 515, 80, 30);
		add(jlInputDate);
		
		jlSlash.setBounds(229, 156, 16, 30);
		add(jlSlash);
		
		jtfId.setBounds(133, 36, 200, 30);
		add(jtfId);
		
		jpfPass.setBounds(133, 76, 200, 30);
		add(jpfPass);
		
		jtfName.setBounds(133, 116, 200, 30);
		add(jtfName);
		
		jtfSsn1.setBounds(133, 156, 92, 30);
		add(jtfSsn1);
		
		jtfSsn2.setBounds(241, 156, 92, 30);
		add(jtfSsn2);
		
		jtfTel.setBounds(133, 196, 200, 30);
		add(jtfTel);
		
		jtfZip.setBounds(133, 236, 102, 30);
		add(jtfZip);
		
		jtfAddr1.setBounds(133, 276, 200, 30);
		add(jtfAddr1);
		
		jtfAddr2.setBounds(133, 316, 200, 30);
		add(jtfAddr2);
		
		jtfEmail.setBounds(133, 356, 200, 30);
		add(jtfEmail);
		
		jtfAnswer.setBounds(133, 436, 200, 30);
		add(jtfAnswer);
		
		jtfInputDate.setBounds(133, 516, 200, 30);
		add(jtfInputDate);
		
		jcbQuestion.setBounds(133, 396, 200, 30);
		jcbQuestion.addItem("�� ��������? ");
		jcbQuestion.addItem("�ʵ��б� ���� ���� ģ�ߴ� ģ����? ");
		add(jcbQuestion);
		
		jcbUser.setBounds(133, 476, 200, 30);
		jcbUser.addItem("������");
		jcbUser.addItem("������");
		add(jcbUser);
		
		jbSearchAddr.setBounds(241, 236, 92, 30);
		add(jbSearchAddr);
		
		jbModify.setBounds(75, 576, 70, 30);
		add(jbModify);
		
		jbRemove.setBounds(157, 576, 70, 30);
		add(jbRemove);
		
		jbClose.setBounds(235, 576, 70, 30);
		add(jbClose);
		
		
		setBounds(0,0,390,680);
		setVisible(true);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		
	}
/*	public static void main(String[] args) {
		new UserModifyView();
	}*/
	public JTextField getJtfId() {
		return jtfId;
	}
	public JTextField getJtfName() {
		return jtfName;
	}
	public JTextField getJtfSsn1() {
		return jtfSsn1;
	}
	public JTextField getJtfSsn2() {
		return jtfSsn2;
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
	public JTextField getJtfInputDate() {
		return jtfInputDate;
	}
	public JPasswordField getJpfPass() {
		return jpfPass;
	}
	public JButton getJbModify() {
		return jbModify;
	}
	public JButton getJbSearchAddr() {
		return jbSearchAddr;
	}
	public JButton getJbRemove() {
		return jbRemove;
	}
	public JButton getJbClose() {
		return jbClose;
	}
	public JComboBox<String> getJcbQuestion() {
		return jcbQuestion;
	}
	public JComboBox<String> getJcbUser() {
		return jcbUser;
	}
	
}
