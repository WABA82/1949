package user.er.view;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import user.er.controller.CoInfoModifyController;
import user.er.vo.CoInfoVO;
import user.util.JTextFieldLimit;

@SuppressWarnings("serial")
public class CoInfoModifyView extends JDialog {
	private JButton jbModify, jbClose;
	private JLabel jlImg1, jlImg2, jlImg3, jlImg4;
	private JTextField jtfCoName;
	private JTextField jtfEstDate, memberNum;
	private JTextArea jtaCoDesc;
//	private CoInfoVO cvo;

	public CoInfoModifyView(ErMainView emv, CoInfoVO cvo) {
		super(emv, "ȸ����������", true);
//		this.cvo = cvo;

		jtfCoName = new JTextField();
		jtfCoName.setDocument(new JTextFieldLimit(10));
		jtfCoName.setText(cvo.getCoName());
		
		jtfEstDate = new JTextField();
		jtfEstDate.setDocument((new JTextFieldLimit(10)));
		jtfEstDate.setText(cvo.getEstDate());
		
		memberNum = new JTextField();
		memberNum.setDocument((new JTextFieldLimit(5)));
		memberNum.setText(String.valueOf(cvo.getMemberNum()));
		
		JLabel jlCoName = new JLabel("ȸ���");
		JLabel jlEstDate = new JLabel("�����⵵");
		JLabel jlmemberNum = new JLabel(" �����");
		
		JLabel jlCoImgGuide=new JLabel("���̹������� Ŭ���ؼ� �̹��� ������ �����մϴ�.");
		jlCoImgGuide.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 12));
		jlCoImgGuide.setForeground(new Color(0xFF0000));
		
		
		jtaCoDesc = new JTextArea(cvo.getCoDesc());
		JScrollPane jspTaDesc = new JScrollPane(jtaCoDesc);

		jbModify = new JButton("����");
		jbClose = new JButton("�ݱ�");
		
		jlImg1 = new JLabel();
		jlImg2 = new JLabel();
		jlImg3 = new JLabel();
		jlImg4 = new JLabel();
		// ��ġ
		setLayout(null);

		jlCoName.setBounds(240, 46, 57, 26);
		jlEstDate.setBounds(240, 98, 57, 26);
		jlmemberNum.setBounds(240, 150, 57, 26);

		jtfCoName.setBounds(300, 46, 133, 29);
		jtfEstDate.setBounds(300, 98, 133, 29);
		memberNum.setBounds(300, 150, 133, 29);

		jlImg1.setBounds(35, 25, 170, 170);
		jlImg1.setBorder(new TitledBorder("ȸ�� �̹���"));

		jlImg2.setBounds(33, 203, 50, 50);
		jlImg3.setBounds(93, 203, 50, 50);
		jlImg4.setBounds(158, 203, 50, 50);

		jlCoImgGuide.setBounds(30, 215, 300, 100);
		
		jlImg2.setBorder(new LineBorder(Color.black));
		jlImg3.setBorder(new LineBorder(Color.black));
		jlImg4.setBorder(new LineBorder(Color.black));
		

		jspTaDesc.setBounds(32, 274, 405, 133);
		jspTaDesc.setBorder(new TitledBorder("��� ����"));

		jbModify.setBounds(233, 430, 92, 24);
		jbClose.setBounds(342, 430, 92, 24);

		
		//�̺�Ʈ ó��
		CoInfoModifyController cimc = new CoInfoModifyController(this,  cvo, emv );
		addWindowListener(cimc);
		jbClose.addActionListener(cimc);
		jbModify.addActionListener(cimc);
		jlImg1.addMouseListener(cimc);
		jlImg2.addMouseListener(cimc);
		jlImg3.addMouseListener(cimc);
		jlImg4.addMouseListener(cimc);
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
		
		add(jlCoImgGuide);
		

		add(jspTaDesc);

		add(jbModify);
		add(jbClose);

		setBounds(100, 100, 480, 530);
		setVisible(true);
	}// ������
	
	

	public void setJlImg1(JLabel jlImg1) {
		this.jlImg1 = jlImg1;
	}



	public void setJlImg2(JLabel jlImg2) {
		this.jlImg2 = jlImg2;
	}



	public void setJlImg3(JLabel jlImg3) {
		this.jlImg3 = jlImg3;
	}



	public void setJlImg4(JLabel jlImg4) {
		this.jlImg4 = jlImg4;
	}



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

}
