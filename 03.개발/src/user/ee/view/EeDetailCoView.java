package user.ee.view;

import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import user.ee.controller.EeDetailCoController;
import user.ee.vo.CoDetailVO;

@SuppressWarnings("serial")
public class EeDetailCoView extends JDialog {

	/* 인스턴스변수 */
	private JButton jbClose;
	private JLabel img1, img2, img3, img4;
	private JTextField jtfCoName, jtfEstDate, memberNum;
	private JTextArea jtaCoDesc;

	public EeDetailCoView(EeDetailErView edev, CoDetailVO cdvo) {
		super(edev, "회사상세정보", true);

		JLabel jlCoName = new JLabel("회사명");
		JLabel jlEstDate = new JLabel("설립년도");
		JLabel jlmemberNum = new JLabel(" 사원수");

		jtfCoName = new JTextField(); // 회사명
		jtfEstDate = new JTextField(); // 설립일
		memberNum = new JTextField(); // 사원수

		jtfCoName.setEditable(false);
		jtfEstDate.setEditable(false);
		memberNum.setEditable(false);

		img1 = new JLabel();
		img2 = new JLabel();
		img3 = new JLabel();
		img4 = new JLabel();

		jtaCoDesc = new JTextArea(); // 상세정보.
		jtaCoDesc.setEditable(false);
		JScrollPane jspTaDesc = new JScrollPane(jtaCoDesc);

		jbClose = new JButton("닫기");

		// 배치
		setLayout(null);

		jlCoName.setBounds(240, 45, 57, 26);
		jlEstDate.setBounds(240, 97, 57, 26);
		jlmemberNum.setBounds(240, 149, 57, 26);

		jtfCoName.setBounds(300, 45, 133, 29);
		jtfEstDate.setBounds(300, 97, 133, 29);
		memberNum.setBounds(300, 149, 133, 29);

		img1.setBounds(36, 25, 170, 170);
		img1.setBorder(new TitledBorder("회사 이미지"));

		img2.setBounds(33, 208, 50, 50);
		img3.setBounds(93, 208, 50, 50);
		img4.setBounds(158, 208, 50, 50);

		img2.setBorder(new LineBorder(Color.black));
		img3.setBorder(new LineBorder(Color.black));
		img4.setBorder(new LineBorder(Color.black));

		jspTaDesc.setBounds(27, 275, 405, 266);
		jspTaDesc.setBorder(new TitledBorder("기업 설명"));

		add(jlCoName);
		add(jlEstDate);
		add(jlmemberNum);

		add(jtfCoName);
		add(jtfEstDate);
		add(memberNum);

		add(img1);
		add(img2);
		add(img3);
		add(img4);

		add(jspTaDesc);

		add(jbClose);

		/* 이벤트 등록 */
		EeDetailCoController edcc = new EeDetailCoController(this, cdvo);
		addWindowListener(edcc);
		jbClose.addActionListener(edcc);

		/* 프레임 크기 설정 및 가시화 */
		jbClose.setBounds(338, 562, 92, 24);
		setBounds(100, 100, 480, 650);
		setResizable(false);
		setVisible(true);

	}// 생성자

	public JButton getJbClose() {
		return jbClose;
	}

	public JLabel getImg1() {
		return img1;
	}

	public JLabel getImg2() {
		return img2;
	}

	public JLabel getImg3() {
		return img3;
	}

	public JLabel getImg4() {
		return img4;
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

}// class