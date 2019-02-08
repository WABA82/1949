package user.er.view;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import user.er.controller.CoInfoRegController;

@SuppressWarnings("serial")
public class CoInfoRegView extends JDialog {
	private JButton jbReg, jbClose;
	private JTextField jtfCoName, jtfEstDate, memberNum;
	private JLabel jlImg1, jlImg2, jlImg3, jlImg4;
	private JTextArea jtaCoDesc;

	public CoInfoRegView() {
		
		jtfCoName = new JTextField();
		jtfEstDate = new JTextField();
		memberNum = new JTextField();
		
		JLabel jlCoName = new JLabel("ȸ���");
		JLabel jlEstDate = new JLabel("�����⵵");
		JLabel jlmemberNum = new JLabel(" �����");
		
		jtaCoDesc = new JTextArea();
		JScrollPane jspTaDesc = new JScrollPane(jtaCoDesc);
		
		jbReg = new JButton("���");
		 jbClose = new JButton("�ݱ�");
		
		jlImg1 = new JLabel("j");
		jlImg2 = new JLabel("j");
		jlImg3 = new JLabel("j");
		jlImg4 = new JLabel("j");
		
		//��ġ
		setLayout(null);
		
		jlCoName.setBounds(240, 56, 57, 26);
		jlEstDate.setBounds(240, 98, 57, 26);
		jlmemberNum.setBounds(240, 140, 57, 26);
		
		jtfCoName.setBounds(300, 56, 133, 29);
		jtfEstDate.setBounds(300, 98, 133, 29);
		memberNum.setBounds(300, 140, 133, 29);
		
		jlImg1.setBounds(28, 45, 190, 120);
		jlImg1.setBorder(new TitledBorder("ȸ�� �̹���"));
		
		jlImg2.setBounds(33, 178, 50, 44);
		jlImg3.setBounds(93, 178, 50, 44);
		jlImg4.setBounds(158, 178, 50, 44);
		
		jspTaDesc.setBounds(32, 234, 405, 133);
		jspTaDesc.setBorder(new TitledBorder("��� ����"));
		
		jbReg.setBounds(233, 400, 92, 24);
		jbClose.setBounds(342, 400, 92, 24);
		
		CoInfoRegController crc= new CoInfoRegController(this);
		addWindowListener(crc);
		jbClose.addActionListener(crc);
		jbReg.addActionListener(crc);
		
		add(jlCoName);
		add(jlEstDate);
		add(jlmemberNum);
		
		add(jtfCoName);
		add(jtfEstDate);
		add(memberNum);
		
		add(jlImg1);
		add(jlImg2);
		add(jlImg3);
		add(jlImg4);
		
		add(jspTaDesc);
		
		add(jbReg);
		add(jbClose);
		
		setBounds(100, 100, 480, 510);
		setVisible(true);
		
	}//������

	public JButton getJbReg() {
		return jbReg;
	}

	public JButton getJbClose() {
		return jbClose;
	}

	public JTextField getJtfCoName() {
		return jtfCoName;
	}

	public JTextField getJtfEstDate() {
		return jtfEstDate;
	}

	public JTextField getMemberNum() {
		return memberNum;
	}

	public JLabel getJlImg1() {
		return jlImg1;
	}

	public JLabel getJlImg2() {
		return jlImg2;
	}

	public JLabel getJlImg3() {
		return jlImg3;
	}

	public JLabel getJlImg4() {
		return jlImg4;
	}

	public JTextArea getJtaCoDesc() {
		return jtaCoDesc;
	}
	
//	public static void main(String[] args) {
//		new CoInfoRegView();
//	}
	
}
