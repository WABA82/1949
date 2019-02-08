package user.er.view;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import user.er.controller.CoInfoModifyController;
import user.er.controller.CoInfoRegController;

@SuppressWarnings("serial")
public class CoInfoModifyView extends JDialog {
	private JButton jbModify, jbClose;
	private JLabel jlImg1, jlImg2, jlImg3, jlImg4;
	private JTextField jtfCoName, jtfEstDate, memberNum;
	private JTextArea jtaCoDesc;
	
	public CoInfoModifyView() {
		
		jtfCoName = new JTextField();
		jtfEstDate = new JTextField();
		memberNum = new JTextField();
		
		JLabel jlCoName = new JLabel("회사명");
		JLabel jlEstDate = new JLabel("설립년도");
		JLabel jlmemberNum = new JLabel(" 사원수");
		
		jtaCoDesc = new JTextArea();
		JScrollPane jspTaDesc = new JScrollPane(jtaCoDesc);
		
		 jbModify = new JButton("수정");
		 jbClose = new JButton("닫기");
		
		jlImg1 = new JLabel("j");
		jlImg2 = new JLabel("j");
		jlImg3 = new JLabel("j");
		jlImg4 = new JLabel("j");
		
		//배치
		setLayout(null);
		
		jlCoName.setBounds(240, 56, 57, 26);
		jlEstDate.setBounds(240, 98, 57, 26);
		jlmemberNum.setBounds(240, 140, 57, 26);
		
		jtfCoName.setBounds(300, 56, 133, 29);
		jtfEstDate.setBounds(300, 98, 133, 29);
		memberNum.setBounds(300, 140, 133, 29);
		
		jlImg1.setBounds(28, 45, 190, 120);
		jlImg1.setBorder(new TitledBorder("회사 이미지"));
		
		jlImg2.setBounds(33, 178, 50, 44);
		jlImg3.setBounds(93, 178, 50, 44);
		jlImg4.setBounds(158, 178, 50, 44);
		
		jspTaDesc.setBounds(32, 234, 405, 133);
		jspTaDesc.setBorder(new TitledBorder("기업 설명"));
		
		jbModify.setBounds(233, 400, 92, 24);
		jbClose.setBounds(342, 400, 92, 24);
		
		CoInfoModifyController cimc= new CoInfoModifyController(this);
		addWindowListener(cimc);
		jbClose.addActionListener(cimc);
		jbModify.addActionListener(cimc);
		
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
		
		add(jbModify);
		add(jbClose);
		
		setBounds(100, 100, 480, 510);
		setVisible(true);
	}//생성자

	public JButton getJbModify() {
		return jbModify;
	}

	public JButton getJbClose() {
		return jbClose;
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

	public JTextField getJtfCoName() {
		return jtfCoName;
	}

	public JTextField getJtfEstDate() {
		return jtfEstDate;
	}

	public JTextField getMemberNum() {
		return memberNum;
	}

	public JTextArea getJtaCoDesc() {
		return jtaCoDesc;
	}
	
//	public static void main(String[] args) {
//		new CoInfoModifyView();
//	}
}
