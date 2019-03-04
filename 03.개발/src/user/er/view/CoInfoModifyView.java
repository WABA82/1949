package user.er.view;

import java.awt.Color;

import javax.swing.ImageIcon;
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

@SuppressWarnings("serial")
public class CoInfoModifyView extends JDialog {
	private JButton jbModify, jbClose;
	private JLabel jlImg1, jlImg2, jlImg3, jlImg4;
	private JTextField jtfCoName, jtfEstDate, memberNum;
	private JTextArea jtaCoDesc;
	private CoInfoVO cvo;

	public CoInfoModifyView(ErMainView emv, CoInfoVO cvo) {
		super(emv, "회사정보수정", true);
		this.cvo = cvo;

		jtfCoName = new JTextField(cvo.getCoName());
		jtfEstDate = new JTextField(cvo.getEstDate());
		memberNum = new JTextField(String.valueOf(cvo.getMemberNum()));

		JLabel jlCoName = new JLabel("회사명");
		JLabel jlEstDate = new JLabel("설립년도");
		JLabel jlmemberNum = new JLabel(" 사원수");

		jtaCoDesc = new JTextArea(cvo.getCoDesc());
		JScrollPane jspTaDesc = new JScrollPane(jtaCoDesc);

		jbModify = new JButton("수정");
		jbClose = new JButton("닫기");

		jlImg1 = new JLabel(new ImageIcon("C:/dev/1949/03.개발/src/user/img/co/"+cvo.getImg1()));
		jlImg2 = new JLabel(new ImageIcon("C:/dev/1949/03.개발/src/user/img/co/"+cvo.getImg2()));
		jlImg3 = new JLabel(new ImageIcon("C:/dev/1949/03.개발/src/user/img/co/"+cvo.getImg3()));
		jlImg4 = new JLabel(new ImageIcon("C:/dev/1949/03.개발/src/user/img/co/"+cvo.getImg4()));
		System.out.println(cvo);
		// 배치
		setLayout(null);

		jlCoName.setBounds(240, 46, 57, 26);
		jlEstDate.setBounds(240, 98, 57, 26);
		jlmemberNum.setBounds(240, 150, 57, 26);

		jtfCoName.setBounds(300, 46, 133, 29);
		jtfEstDate.setBounds(300, 98, 133, 29);
		memberNum.setBounds(300, 150, 133, 29);

		jlImg1.setBounds(35, 25, 170, 170);
		jlImg1.setBorder(new TitledBorder("회사 이미지"));

		jlImg2.setBounds(33, 208, 50, 50);
		jlImg3.setBounds(93, 208, 50, 50);
		jlImg4.setBounds(158, 208, 50, 50);

		jlImg2.setBorder(new LineBorder(Color.black));
		jlImg3.setBorder(new LineBorder(Color.black));
		jlImg4.setBorder(new LineBorder(Color.black));

		jspTaDesc.setBounds(32, 274, 405, 133);
		jspTaDesc.setBorder(new TitledBorder("기업 설명"));

		jbModify.setBounds(233, 430, 92, 24);
		jbClose.setBounds(342, 430, 92, 24);

		
		//이벤트 처리
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

		add(jspTaDesc);

		add(jbModify);
		add(jbClose);

		setBounds(100, 100, 480, 530);
		setVisible(true);
	}// 생성자

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

	/*
	  public static void main(String[] args) { ErMainVO rm_vo = new ErMainVO("ad",
	  "sad", "sad", "asd"); ErMainView emv = new ErMainView(rm_vo); CoInfoVO cvo =
	 new CoInfoVO(); new CoInfoModifyView(emv,cvo); }
	 */
}
