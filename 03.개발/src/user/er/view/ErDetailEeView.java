package user.er.view;

import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import user.er.controller.ErDetailEeController;

/**
 * 김건하 19.02.08 : '관심 구직자 상세 정보' 관심구직자 목록현황에서 더블클릭 했을 때 띄워지는 창.
 * 
 * @author owner
 */
@SuppressWarnings("serial")
public class ErDetailEeView extends JDialog {

	private JButton jbRsmDown, jbClose;
	private JLabel jlHeart;

	public ErDetailEeView(ErInterestView eriv, String er_id, String ee_num) {
		super(eriv, "관심 구직자 상세 정보", true);

		// image
		ImageIcon ii = new ImageIcon("C:/dev/homework/1949/1949/03.개발/가데이터/구직자사진/150x200px/각키.jpg");
		JLabel jlImage = new JLabel(ii);
		jlImage.setBorder(new TitledBorder("구직자 이미지"));
		jlImage.setBounds(38, 20, 160, 225);
		add(jlImage);

		ImageIcon iiHeart = new ImageIcon("C:/dev/homework/1949/1949/03.개발/가데이터/하트/하트2.png");
		jlHeart = new JLabel(iiHeart);
		add(jlHeart);
		jlHeart.setBounds(280, 413, 60, 60);

		jbRsmDown = new JButton("클릭하여 다운로드");
		add(jbRsmDown);
		jbRsmDown.setBounds(305, 375, 145, 27);

		jbClose = new JButton("닫기");
		add(jbClose);
		jbClose.setBounds(345, 430, 100, 30);

		// Label
		JLabel jlName = new JLabel("이름");
		jlName.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 12));

		JLabel jlTel = new JLabel("연락처");
		jlName.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 12));

		JLabel jlEmail = new JLabel("이메일");
		jlName.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 12));

		JLabel jlRank = new JLabel("직급");
		jlRank.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 12));

		JLabel jlLoc = new JLabel("근무지역");
		jlLoc.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 12));

		JLabel jlEdu = new JLabel("학력");
		jlEdu.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 12));

		JLabel jlAge = new JLabel("나이");
		jlAge.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 12));

		JLabel jlPort = new JLabel("포트폴리오 유무");
		jlPort.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 12));

		JLabel jlGender = new JLabel("성별");
		jlGender.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 12));

		JLabel jlResume = new JLabel("외부이력서");
		jlResume.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 12));

		setLayout(null);

		// 연락처 이메일,
		// Label
		add(jlName);
		jlName.setBounds(255, 20, 50, 20);
		add(jlTel);
		jlTel.setBounds(252, 60, 50, 20);
		add(jlEmail);
		jlEmail.setBounds(248, 100, 70, 20);
		add(jlRank);
		jlRank.setBounds(255, 138, 50, 30);
		add(jlLoc);
		jlLoc.setBounds(242, 175, 70, 30);
		add(jlEdu);
		jlEdu.setBounds(255, 215, 100, 30);
		add(jlAge);
		jlAge.setBounds(255, 255, 50, 30);
		add(jlPort);
		jlPort.setBounds(210, 295, 100, 30);
		add(jlGender);
		jlGender.setBounds(255, 335, 100, 30);
		add(jlResume);
		jlResume.setBounds(228, 375, 100, 30);

		// text
		JTextField jtfName = new JTextField(10);
		add(jtfName);
		jtfName.setBounds(315, 22, 130, 20);

		JTextField jtfTel = new JTextField(10);
		add(jtfTel);
		jtfTel.setBounds(315, 62, 130, 20);

		JTextField jtfEmail = new JTextField(10);
		add(jtfEmail);
		jtfEmail.setBounds(315, 102, 130, 20);

		JTextField jtfRank = new JTextField(10);
		add(jtfRank);
		jtfRank.setBounds(315, 142, 130, 20);

		JTextField jtfLoc = new JTextField(10);
		add(jtfLoc);
		jtfLoc.setBounds(315, 182, 130, 20);

		JTextField jtfEdu = new JTextField(10);
		add(jtfEdu);
		jtfEdu.setBounds(315, 222, 130, 20);

		JTextField jtfAge = new JTextField(10);
		add(jtfAge);
		jtfAge.setBounds(315, 262, 130, 20);

		JTextField jtfPort = new JTextField(10);
		add(jtfPort);
		jtfPort.setBounds(315, 302, 130, 20);

		JTextField jtfGender = new JTextField(10);
		add(jtfGender);
		jtfGender.setBounds(315, 342, 130, 20);

		/* 이벤트 등록 */
		ErDetailEeController edec = new ErDetailEeController(er_id, ee_num);
		addWindowListener(edec);
		
		/*크기 설정 및 가시화*/
		setBounds(100, 100, 490, 520);
		setVisible(true);
		setResizable(false);

	}// 생성자

//	public static void main(String[] args) {
//		new ErDetailEeView(null, "gang123", );
//	}
}// class
