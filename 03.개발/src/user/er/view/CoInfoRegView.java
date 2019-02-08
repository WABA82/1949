package user.er.view;

import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import user.er.controller.CoInfoRegController;

@SuppressWarnings("serial")
public class CoInfoRegView extends JDialog {
	private JButton jbReg, jbClose;
	private JTextField jtfCoName, jtfEstDate, memberNum;
	private JLabel jlImg1, jlImg2, jlImg3, jlImg4;
	private JTextArea jtaCoDesc;

	public CoInfoRegView(ErMainView emv, String erId) {
		
		jtfCoName = new JTextField();
		jtfEstDate = new JTextField();
		memberNum = new JTextField();
		
		JLabel jlCoName = new JLabel("회사명");
		JLabel jlEstDate = new JLabel("설립년도");
		JLabel jlmemberNum = new JLabel(" 사원수");
		
		jtaCoDesc = new JTextArea();
		JScrollPane jspTaDesc = new JScrollPane(jtaCoDesc);
		
		jbReg = new JButton("등록");
		 jbClose = new JButton("닫기");
		
		jlImg1 = new JLabel("j");
		jlImg2 = new JLabel("j");
		jlImg3 = new JLabel("j");
		jlImg4 = new JLabel("j");
		
		//배치
		setLayout(null);
		
		jlCoName.setBounds(240, 46, 57, 26);
		jlEstDate.setBounds(240, 98, 57, 26);
		jlmemberNum.setBounds(240, 150, 57, 26);
		
		jtfCoName.setBounds(300, 46, 133, 29);
		jtfEstDate.setBounds(300, 98, 133, 29);
		memberNum.setBounds(300, 150, 133, 29);
		
		jlImg1.setBounds(38, 25, 170, 170);
		jlImg1.setBorder(new TitledBorder("회사 이미지"));
		jlImg2.setBounds(38, 208, 50, 50);
		jlImg3.setBounds(98, 208, 50, 50);
		jlImg4.setBounds(158, 208, 50, 50);
		
		jlImg2.setBorder(new LineBorder(Color.black));
		jlImg3.setBorder(new LineBorder(Color.black));
		jlImg4.setBorder(new LineBorder(Color.black));
		
		jspTaDesc.setBounds(32, 289, 405, 133);
		jspTaDesc.setBorder(new TitledBorder("기업 설명"));
		
		jbReg.setBounds(233, 446, 92, 24);
		jbClose.setBounds(342, 446, 92, 24);
		
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
		
		setBounds(100, 100, 480, 540);
		setVisible(true);
		
	}//생성자

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
