package user.ee.view;

import java.awt.Color;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import user.dao.EeDAO;
import user.ee.controller.EeDetailCoController;
import user.ee.vo.CoDetailVO;

@SuppressWarnings("serial")
public class EeDetailCoView extends JDialog {
	private JButton jbClose;
	private JLabel img1, img2, img3, img4;

	public EeDetailCoView(EeDetailErView edev, CoDetailVO cdvo) {
		super(edev, "회사상세정보", true);

		JLabel jlCoName = new JLabel("회사명");
		JLabel jlEstDate = new JLabel("설립년도");
		JLabel jlmemberNum = new JLabel(" 사원수");

		JTextField jtfCoName = new JTextField(cdvo.getCo_name()); // 회사명
		JTextField jtfEstDate = new JTextField(cdvo.getEst_date()); // 설립일
		JTextField memberNum = new JTextField(String.valueOf(cdvo.getMember_num())); // 사원수
		jtfCoName.setEditable(false);
		jtfEstDate.setEditable(false);
		memberNum.setEditable(false);

		String imgPath = "C:/dev/1949/03.개발/src/file/coImg/";
		img1 = new JLabel(new ImageIcon(imgPath + cdvo.getImg1()));
		img2 = new JLabel(new ImageIcon(imgPath + cdvo.getImg2()));
		img3 = new JLabel(new ImageIcon(imgPath + cdvo.getImg3()));
		img4 = new JLabel(new ImageIcon(imgPath + cdvo.getImg4()));

		JTextArea jtaCoDesc = new JTextArea(cdvo.getCo_desc()); // 상세정보.
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
		EeDetailCoController edcc = new EeDetailCoController(this);
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

	/* 단위 테스트용 */
	public static void main(String[] args) {
		EeDAO ee_dao = EeDAO.getInstance();
		CoDetailVO cdvo;
		try {
			cdvo = ee_dao.selectCompany("er_000033");
			new EeDetailCoView(null, cdvo);
		} catch (SQLException e) {
			e.printStackTrace();
		} // end catch
	}// main

}// class
