package admin.view;

import java.awt.Color;
import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import admin.controller.AdminMgMtController;
import admin.controller.CoModifyController;
import admin.vo.CoInfoVO;

@SuppressWarnings("serial")
public class CoModifyView extends JDialog {

	private JTextField jtfCoName, jtfEstDate, memberNum;
	private JTextArea jtaCoDesc;
	private JButton jbModify, jbRemove, jbClose;
	private JLabel jlImg1, jlImg2, jlImg3, jlImg4;

	public CoModifyView(AdminMgMtView ammv, CoInfoVO civo, AdminMgMtController ammc) {
		super(ammv, "회사상세정보", true); // 모달 true

		JTextField jtfCoId = new JTextField(civo.getErId());
		jtfCoId.setEditable(false);
		jtfCoName = new JTextField(civo.getCoName());
		jtfEstDate = new JTextField(civo.getEstDate());
		memberNum = new JTextField(String.valueOf(civo.getMemberNum()));

		JLabel jlCoId = new JLabel("등록 아이디");
		JLabel jlCoName = new JLabel("회사 이름");
		JLabel jlEstDate = new JLabel("설립년도");
		JLabel jlmemberNum = new JLabel("사원수");
		
		JLabel jlImgDesc = new JLabel("이미지는 더블클릭해서 변경가능합니다.");
		jlImgDesc.setFont(new Font(Font.DIALOG, Font.BOLD, 11));
		jlImgDesc.setForeground(Color.red);

		jtaCoDesc = new JTextArea(civo.getCoDesc());
		JScrollPane jspTaDesc = new JScrollPane(jtaCoDesc);

		jbModify = new JButton("수정");
		jbRemove = new JButton("삭제");
		jbClose = new JButton("닫기");

		jlImg1 = new JLabel(new ImageIcon("C:/dev/1949/03.개발/src/admin/img/co/"+civo.getImg1()));
		jlImg2 = new JLabel(new ImageIcon("C:/dev/1949/03.개발/src/admin/img/co/"+civo.getImg2()));
		jlImg3 = new JLabel(new ImageIcon("C:/dev/1949/03.개발/src/admin/img/co/"+civo.getImg3()));
		jlImg4 = new JLabel(new ImageIcon("C:/dev/1949/03.개발/src/admin/img/co/"+civo.getImg4()));

		// 배치
		setLayout(null);

		jlCoId.setBounds(240, 50, 57, 26);
		jlCoName.setBounds(240, 96, 57, 26);
		jlEstDate.setBounds(240, 138, 57, 26);
		jlmemberNum.setBounds(240, 180, 57, 26);

		jtfCoId.setBounds(300, 50, 133, 29);
		jtfCoName.setBounds(300, 96, 133, 29);
		jtfEstDate.setBounds(300, 138, 133, 29);
		memberNum.setBounds(300, 180, 133, 29);

		jlImg1.setBounds(38, 25, 170, 170);
		jlImg1.setBorder(new TitledBorder("회사 이미지"));
		
		jlImgDesc.setBounds(38, 260, 250, 30);
		add(jlImgDesc);

		jlImg2.setBounds(38, 208, 50, 50);
		jlImg3.setBounds(98, 208, 50, 50);
		jlImg4.setBounds(158, 208, 50, 50);
		jlImg2.setBorder(new LineBorder(Color.black));
		jlImg3.setBorder(new LineBorder(Color.black));
		jlImg4.setBorder(new LineBorder(Color.black));

		jspTaDesc.setBounds(32, 289, 405, 133);
		jspTaDesc.setBorder(new TitledBorder("기업 설명"));

		jbModify.setBounds(140, 446, 92, 24);
		jbRemove.setBounds(243, 446, 92, 24);
		jbClose.setBounds(342, 446, 92, 24);

		add(jlCoId);
		add(jlCoName);
		add(jlEstDate);
		add(jlmemberNum);

		add(jtfCoId);
		add(jtfCoName);
		add(jtfEstDate);
		add(memberNum);

		add(jlImg1);
		add(jlImg2);
		add(jlImg3);
		add(jlImg4);

		add(jspTaDesc);

		add(jbModify);
		add(jbRemove);
		add(jbClose);
		
		CoModifyController cmc = new CoModifyController(this, ammv, civo, ammc);
		addWindowListener(cmc);
		
		jlImg1.addMouseListener(cmc);
		jlImg2.addMouseListener(cmc);
		jlImg3.addMouseListener(cmc);
		jlImg4.addMouseListener(cmc);
		
		jbModify.addActionListener(cmc);
		jbRemove.addActionListener(cmc);
		jbClose.addActionListener(cmc);

		setBounds(100, 100, 480, 540);
		setResizable(false);
		setVisible(true);
	}// CoModifyView 생성자

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

	public JButton getJbModify() {
		return jbModify;
	}

	public JButton getJbRemove() {
		return jbRemove;
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
}// class
